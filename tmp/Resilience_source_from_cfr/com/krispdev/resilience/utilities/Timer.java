/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.utilities;

public class Timer {
    private long prevTime = 0;

    public boolean hasTimePassed(long milSec) {
        if ((float)(this.getTime() - this.prevTime) >= (float)milSec) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.prevTime = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }
}

