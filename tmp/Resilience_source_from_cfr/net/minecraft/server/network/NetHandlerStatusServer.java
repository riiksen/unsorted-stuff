/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.util.concurrent.GenericFutureListener
 */
package net.minecraft.server.network;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer
implements INetHandlerStatusServer {
    private final MinecraftServer field_147314_a;
    private final NetworkManager field_147313_b;
    private static final String __OBFID = "CL_00001464";

    public NetHandlerStatusServer(MinecraftServer p_i45299_1_, NetworkManager p_i45299_2_) {
        this.field_147314_a = p_i45299_1_;
        this.field_147313_b = p_i45299_2_;
    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {
    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
        if (p_147232_2_ != EnumConnectionState.STATUS) {
            throw new UnsupportedOperationException("Unexpected change in protocol to " + (Object)((Object)p_147232_2_));
        }
    }

    @Override
    public void onNetworkTick() {
    }

    @Override
    public void processServerQuery(C00PacketServerQuery p_147312_1_) {
        this.field_147313_b.scheduleOutboundPacket(new S00PacketServerInfo(this.field_147314_a.func_147134_at()), new GenericFutureListener[0]);
    }

    @Override
    public void processPing(C01PacketPing p_147311_1_) {
        this.field_147313_b.scheduleOutboundPacket(new S01PacketPong(p_147311_1_.func_149289_c()), new GenericFutureListener[0]);
    }
}

