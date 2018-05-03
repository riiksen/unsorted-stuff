/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.storage.IThreadedFileIO;

public class ThreadedFileIOBase
implements Runnable {
    public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
    private List threadedIOQueue = Collections.synchronizedList(new ArrayList());
    private volatile long writeQueuedCounter;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;
    private static final String __OBFID = "CL_00000605";

    private ThreadedFileIOBase() {
        Thread var1 = new Thread((Runnable)this, "File IO Thread");
        var1.setPriority(1);
        var1.start();
    }

    @Override
    public void run() {
        do {
            this.processQueue();
        } while (true);
    }

    private void processQueue() {
        int var1 = 0;
        while (var1 < this.threadedIOQueue.size()) {
            IThreadedFileIO var2 = (IThreadedFileIO)this.threadedIOQueue.get(var1);
            boolean var3 = var2.writeNextIO();
            if (!var3) {
                this.threadedIOQueue.remove(var1--);
                ++this.savedIOCounter;
            }
            try {
                Thread.sleep(this.isThreadWaiting ? 0 : 10);
            }
            catch (InterruptedException var6) {
                var6.printStackTrace();
            }
            ++var1;
        }
        if (this.threadedIOQueue.isEmpty()) {
            try {
                Thread.sleep(25);
            }
            catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }
    }

    public void queueIO(IThreadedFileIO par1IThreadedFileIO) {
        if (!this.threadedIOQueue.contains(par1IThreadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(par1IThreadedFileIO);
        }
    }

    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10);
        }
        this.isThreadWaiting = false;
    }
}

