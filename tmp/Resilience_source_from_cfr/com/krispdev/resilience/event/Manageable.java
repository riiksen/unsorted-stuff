/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event;

import com.krispdev.resilience.event.listeners.Listener;

public interface Manageable {
    public void register(Listener var1);

    public void unregister(Listener var1);

    public void clear();
}

