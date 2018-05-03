/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.Drawable
 *  org.lwjgl.opengl.Pbuffer
 *  org.lwjgl.opengl.PixelFormat
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.CompactArrayList;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdater;
import net.minecraft.src.WorldRendererThreaded;
import net.minecraft.src.WrUpdateThread;
import net.minecraft.world.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

public class WrUpdaterThreaded
implements IWrUpdater {
    private WrUpdateThread updateThread = null;
    private float timePerUpdateMs = 10.0f;
    private long updateStartTimeNs = 0;
    private boolean firstUpdate = true;
    private int updateTargetNum = 0;

    @Override
    public void terminate() {
        if (this.updateThread != null) {
            this.updateThread.terminate();
            this.updateThread.unpauseToEndOfUpdate();
        }
    }

    @Override
    public void initialize() {
    }

    private void delayedInit() {
        if (this.updateThread == null) {
            this.createUpdateThread(Display.getDrawable());
        }
    }

    @Override
    public WorldRenderer makeWorldRenderer(World worldObj, List tileEntities, int x, int y, int z, int glRenderListBase) {
        return new WorldRendererThreaded(worldObj, tileEntities, x, y, z, glRenderListBase);
    }

    public WrUpdateThread createUpdateThread(Drawable displayDrawable) {
        if (this.updateThread != null) {
            throw new IllegalStateException("UpdateThread is already existing");
        }
        try {
            Pbuffer e = new Pbuffer(1, 1, new PixelFormat(), displayDrawable);
            this.updateThread = new WrUpdateThread(e);
            this.updateThread.setPriority(1);
            this.updateThread.start();
            this.updateThread.pause();
            return this.updateThread;
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public boolean isUpdateThread() {
        if (Thread.currentThread() == this.updateThread) {
            return true;
        }
        return false;
    }

    public static boolean isBackgroundChunkLoading() {
        return true;
    }

    @Override
    public void preRender(RenderGlobal rg, EntityLivingBase player) {
        this.updateTargetNum = 0;
        if (this.updateThread != null) {
            if (this.updateStartTimeNs == 0) {
                this.updateStartTimeNs = System.nanoTime();
            }
            if (this.updateThread.hasWorkToDo()) {
                this.updateTargetNum = Config.getUpdatesPerFrame();
                if (Config.isDynamicUpdates() && !rg.isMoving(player)) {
                    this.updateTargetNum *= 3;
                }
                this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());
                if (this.updateTargetNum > 0) {
                    this.updateThread.unpause();
                }
            }
        }
    }

    @Override
    public void postRender() {
        if (this.updateThread != null) {
            float sleepTimeMs = 0.0f;
            if (this.updateTargetNum > 0) {
                long deltaTime = System.nanoTime() - this.updateStartTimeNs;
                float targetRunTime = this.timePerUpdateMs * (1.0f + (float)(this.updateTargetNum - 1) / 2.0f);
                if (targetRunTime > 0.0f) {
                    int sleepTimeMsInt = (int)targetRunTime;
                    Config.sleep(sleepTimeMsInt);
                }
                this.updateThread.pause();
            }
            float deltaTime1 = 0.2f;
            if (this.updateTargetNum > 0) {
                int updateCount = this.updateThread.resetUpdateCount();
                if (updateCount < this.updateTargetNum) {
                    this.timePerUpdateMs += deltaTime1;
                }
                if (updateCount > this.updateTargetNum) {
                    this.timePerUpdateMs -= deltaTime1;
                }
                if (updateCount == this.updateTargetNum) {
                    this.timePerUpdateMs -= deltaTime1;
                }
            } else {
                this.timePerUpdateMs -= deltaTime1 / 5.0f;
            }
            if (this.timePerUpdateMs < 0.0f) {
                this.timePerUpdateMs = 0.0f;
            }
            this.updateStartTimeNs = System.nanoTime();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean updateRenderers(RenderGlobal rg, EntityLivingBase entityliving, boolean flag) {
        this.delayedInit();
        if (rg.worldRenderersToUpdate.size() <= 0) {
            return true;
        }
        num = 0;
        NOT_IN_FRUSTRUM_MUL = 4;
        numValid = 0;
        wrBest = null;
        distSqBest = Float.MAX_VALUE;
        indexBest = -1;
        maxUpdateNum = 0;
        while (maxUpdateNum < rg.worldRenderersToUpdate.size()) {
            block24 : {
                block25 : {
                    turboMode = (WorldRenderer)rg.worldRenderersToUpdate.get(maxUpdateNum);
                    if (turboMode == null) break block24;
                    ++numValid;
                    if (turboMode.isUpdating) break block24;
                    if (turboMode.needsUpdate) break block25;
                    rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                    break block24;
                }
                dstIndex = turboMode.distanceToEntitySquared(entityliving);
                if (dstIndex >= 512.0f) ** GOTO lbl-1000
                if (dstIndex < 256.0f && rg.isActingNow() && turboMode.isInFrustum || this.firstUpdate) {
                    if (this.updateThread != null) {
                        this.updateThread.unpauseToEndOfUpdate();
                    }
                    turboMode.updateRenderer(entityliving);
                    turboMode.needsUpdate = false;
                    rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                    ++num;
                } else if (this.updateThread != null) {
                    this.updateThread.addRendererToUpdate(turboMode, true);
                    turboMode.needsUpdate = false;
                    rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                    ++num;
                } else lbl-1000: // 2 sources:
                {
                    if (!turboMode.isInFrustum) {
                        dstIndex *= (float)NOT_IN_FRUSTRUM_MUL;
                    }
                    if (wrBest == null) {
                        wrBest = turboMode;
                        distSqBest = dstIndex;
                        indexBest = maxUpdateNum;
                    } else if (dstIndex < distSqBest) {
                        wrBest = turboMode;
                        distSqBest = dstIndex;
                        indexBest = maxUpdateNum;
                    }
                }
            }
            ++maxUpdateNum;
        }
        maxUpdateNum = Config.getUpdatesPerFrame();
        var17 = false;
        if (Config.isDynamicUpdates() && !rg.isMoving(entityliving)) {
            maxUpdateNum *= 3;
            var17 = true;
        }
        if (this.updateThread != null && (maxUpdateNum = this.updateThread.getUpdateCapacity()) <= 0) {
            return true;
        }
        if (wrBest != null) {
            this.updateRenderer(wrBest, entityliving);
            rg.worldRenderersToUpdate.set(indexBest, null);
            dstIndex = distSqBest / 5.0f;
            i = 0;
            while (i < rg.worldRenderersToUpdate.size() && ++num < maxUpdateNum) {
                wr = (WorldRenderer)rg.worldRenderersToUpdate.get(i);
                if (wr != null && !wr.isUpdating) {
                    distSq = wr.distanceToEntitySquared(entityliving);
                    if (!wr.isInFrustum) {
                        distSq *= (float)NOT_IN_FRUSTRUM_MUL;
                    }
                    if ((diffDistSq = Math.abs(distSq - distSqBest)) < dstIndex) {
                        this.updateRenderer(wr, entityliving);
                        rg.worldRenderersToUpdate.set(i, null);
                        ++num;
                    }
                }
                ++i;
            }
        }
        if (numValid == 0) {
            rg.worldRenderersToUpdate.clear();
        }
        if (rg.worldRenderersToUpdate.size() > 100 && numValid < rg.worldRenderersToUpdate.size() * 4 / 5) {
            var18 = 0;
            i = 0;
            while (i < rg.worldRenderersToUpdate.size()) {
                var19 = rg.worldRenderersToUpdate.get(i);
                if (var19 != null) {
                    if (i != var18) {
                        rg.worldRenderersToUpdate.set(var18, var19);
                    }
                    ++var18;
                }
                ++i;
            }
            i = rg.worldRenderersToUpdate.size() - 1;
            while (i >= var18) {
                rg.worldRenderersToUpdate.remove(i);
                --i;
            }
        }
        this.firstUpdate = false;
        return true;
    }

    private void updateRenderer(WorldRenderer wr, EntityLivingBase entityLiving) {
        WrUpdateThread ut = this.updateThread;
        if (ut != null) {
            ut.addRendererToUpdate(wr, false);
            wr.needsUpdate = false;
        } else {
            wr.updateRenderer(entityLiving);
            wr.needsUpdate = false;
            wr.isUpdating = false;
        }
    }

    @Override
    public void finishCurrentUpdate() {
        if (this.updateThread != null) {
            this.updateThread.unpauseToEndOfUpdate();
        }
    }

    @Override
    public void resumeBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.unpause();
        }
    }

    @Override
    public void pauseBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.pause();
        }
    }

    @Override
    public void clearAllUpdates() {
        if (this.updateThread != null) {
            this.updateThread.clearAllUpdates();
        }
        this.firstUpdate = true;
    }
}

