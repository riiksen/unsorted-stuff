/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.interfaces;

public interface Toggleable {
    public void onEnable();

    public void onDisable();

    public void onToggle();

    public void toggle();

    public void setEnabled(boolean var1);
}

