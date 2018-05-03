/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.util.IChatComponent;

public interface INetHandler {
    public void onDisconnect(IChatComponent var1);

    public void onConnectionStateTransition(EnumConnectionState var1, EnumConnectionState var2);

    public void onNetworkTick();
}

