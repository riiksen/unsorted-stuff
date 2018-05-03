/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event.events;

public class Cancellable {
    private boolean isCancelled = false;

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean flag) {
        this.isCancelled = flag;
    }
}

