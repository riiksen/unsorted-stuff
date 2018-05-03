/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Pbuffer
 */
package net.minecraft.src;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdateControl;
import net.minecraft.src.IWrUpdateListener;
import net.minecraft.src.WorldRendererThreaded;
import org.lwjgl.opengl.Pbuffer;

public class WrUpdateThread
extends Thread {
    private Pbuffer pbuffer = null;
    private Object lock = new Object();
    private List updateList = new LinkedList();
    private List updatedList = new LinkedList();
    private int updateCount = 0;
    private Tessellator mainTessellator = Tessellator.instance;
    private Tessellator threadTessellator = new Tessellator(2097152);
    private boolean working = false;
    private WorldRendererThreaded currentRenderer = null;
    private boolean canWork = false;
    private boolean canWorkToEndOfUpdate = false;
    private boolean terminated = false;
    private static final int MAX_UPDATE_CAPACITY = 10;

    public WrUpdateThread(Pbuffer pbuffer) {
        super("WrUpdateThread");
        this.pbuffer = pbuffer;
    }

    @Override
    public void run() {
        try {
            this.pbuffer.makeCurrent();
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        ThreadUpdateListener updateListener = new ThreadUpdateListener(null);
        while (!Thread.interrupted() && !this.terminated) {
            try {
                WorldRendererThreaded e;
                e = this.getRendererToUpdate();
                if (e == null) {
                    return;
                }
                this.checkCanWork(null);
                try {
                    this.currentRenderer = e;
                    Tessellator.instance = this.threadTessellator;
                    e.updateRenderer(updateListener);
                }
                finally {
                    Tessellator.instance = this.mainTessellator;
                }
                this.rendererUpdated(e);
            }
            catch (Exception var9) {
                var9.printStackTrace();
                if (this.currentRenderer != null) {
                    this.currentRenderer.isUpdating = false;
                    this.currentRenderer.needsUpdate = true;
                }
                this.currentRenderer = null;
                this.working = false;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addRendererToUpdate(WorldRenderer wr, boolean first) {
        Object var3 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            if (wr.isUpdating) {
                throw new IllegalArgumentException("Renderer already updating");
            }
            if (first) {
                this.updateList.add(0, wr);
            } else {
                this.updateList.add(wr);
            }
            wr.isUpdating = true;
            this.lock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private WorldRendererThreaded getRendererToUpdate() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            do {
                if (this.updateList.size() > 0) {
                    WorldRendererThreaded wrt = (WorldRendererThreaded)this.updateList.remove(0);
                    this.lock.notifyAll();
                    return wrt;
                }
                try {
                    this.lock.wait(2000);
                    if (!this.terminated) continue;
                    Object var10000 = null;
                    return var10000;
                }
                catch (InterruptedException var10000) {
                    // empty catch block
                    continue;
                }
                break;
            } while (true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean hasWorkToDo() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            return this.updateList.size() > 0 ? true : (this.currentRenderer != null ? true : this.working);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getUpdateCapacity() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            return this.updateList.size() > 10 ? 0 : 10 - this.updateList.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void rendererUpdated(WorldRenderer wr) {
        Object var2 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            this.updatedList.add(wr);
            ++this.updateCount;
            this.currentRenderer = null;
            this.working = false;
            this.lock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void finishUpdatedRenderers() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            int i = 0;
            while (i < this.updatedList.size()) {
                WorldRendererThreaded wr = (WorldRendererThreaded)this.updatedList.get(i);
                wr.finishUpdate();
                wr.isUpdating = false;
                ++i;
            }
            this.updatedList.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pause() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            this.canWork = false;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
            while (this.working) {
                try {
                    this.lock.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            this.finishUpdatedRenderers();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unpause() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            if (this.working) {
                Config.warn("UpdateThread still working in unpause()!!!");
            }
            this.canWork = true;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unpauseToEndOfUpdate() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            if (this.working) {
                Config.warn("UpdateThread still working in unpause()!!!");
            }
            if (this.currentRenderer != null) {
                while (this.currentRenderer != null) {
                    this.canWork = false;
                    this.canWorkToEndOfUpdate = true;
                    this.lock.notifyAll();
                    try {
                        this.lock.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                this.pause();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkCanWork(IWrUpdateControl uc) {
        Thread.yield();
        Object var2 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            do {
                if (this.canWork || this.canWorkToEndOfUpdate && this.currentRenderer != null) {
                    this.working = true;
                    if (uc != null) {
                        uc.resume();
                    }
                    this.lock.notifyAll();
                    return;
                }
                if (uc != null) {
                    uc.pause();
                }
                this.working = false;
                this.lock.notifyAll();
                try {
                    this.lock.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            } while (true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearAllUpdates() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            this.unpauseToEndOfUpdate();
            int i = 0;
            while (i < this.updateList.size()) {
                WorldRenderer wr = (WorldRenderer)this.updateList.get(i);
                wr.needsUpdate = true;
                wr.isUpdating = false;
                ++i;
            }
            this.updateList.clear();
            this.lock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getPendingUpdatesCount() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            int count = this.updateList.size();
            if (this.currentRenderer != null) {
                ++count;
            }
            return count;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int resetUpdateCount() {
        Object var1 = this.lock;
        Object object = this.lock;
        synchronized (object) {
            int count = this.updateCount;
            this.updateCount = 0;
            return count;
        }
    }

    public void terminate() {
        this.terminated = true;
    }

    static class NamelessClass895855811 {
        NamelessClass895855811() {
        }
    }

    private class ThreadUpdateControl
    implements IWrUpdateControl {
        private IWrUpdateControl updateControl;
        private boolean paused;

        private ThreadUpdateControl() {
            this.updateControl = null;
            this.paused = false;
        }

        @Override
        public void pause() {
            if (!this.paused) {
                this.paused = true;
                this.updateControl.pause();
                Tessellator.instance = WrUpdateThread.this.mainTessellator;
            }
        }

        @Override
        public void resume() {
            if (this.paused) {
                this.paused = false;
                Tessellator.instance = WrUpdateThread.this.threadTessellator;
                this.updateControl.resume();
            }
        }

        public void setUpdateControl(IWrUpdateControl updateControl) {
            this.updateControl = updateControl;
        }

        ThreadUpdateControl(NamelessClass895855811 x1) {
            this();
        }
    }

    private class ThreadUpdateListener
    implements IWrUpdateListener {
        private ThreadUpdateControl tuc;

        private ThreadUpdateListener() {
            WrUpdateThread wrUpdateThread2 = WrUpdateThread.this;
            wrUpdateThread2.getClass();
            this.tuc = wrUpdateThread2.new ThreadUpdateControl(null);
        }

        @Override
        public void updating(IWrUpdateControl uc) {
            this.tuc.setUpdateControl(uc);
            WrUpdateThread.this.checkCanWork(this.tuc);
        }

        ThreadUpdateListener(NamelessClass895855811 x1) {
            this();
        }
    }

}

