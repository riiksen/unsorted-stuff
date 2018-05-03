/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;

public interface INetHandlerLoginClient
extends INetHandler {
    public void handleEncryptionRequest(S01PacketEncryptionRequest var1);

    public void handleLoginSuccess(S02PacketLoginSuccess var1);

    public void handleDisconnect(S00PacketDisconnect var1);
}

