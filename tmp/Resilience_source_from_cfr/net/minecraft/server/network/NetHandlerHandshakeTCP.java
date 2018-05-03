/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.util.concurrent.GenericFutureListener
 */
package net.minecraft.server.network;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.server.network.NetHandlerStatusServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeTCP
implements INetHandlerHandshakeServer {
    private final MinecraftServer field_147387_a;
    private final NetworkManager field_147386_b;
    private static final String __OBFID = "CL_00001456";

    public NetHandlerHandshakeTCP(MinecraftServer p_i45295_1_, NetworkManager p_i45295_2_) {
        this.field_147387_a = p_i45295_1_;
        this.field_147386_b = p_i45295_2_;
    }

    @Override
    public void processHandshake(C00Handshake p_147383_1_) {
        switch (SwitchEnumConnectionState.field_151291_a[p_147383_1_.func_149594_c().ordinal()]) {
            case 1: {
                this.field_147386_b.setConnectionState(EnumConnectionState.LOGIN);
                if (p_147383_1_.func_149595_d() > 4) {
                    ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.7.2");
                    this.field_147386_b.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
                    this.field_147386_b.closeChannel(var2);
                    break;
                }
                if (p_147383_1_.func_149595_d() < 4) {
                    ChatComponentText var2 = new ChatComponentText("Outdated client! Please use 1.7.2");
                    this.field_147386_b.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
                    this.field_147386_b.closeChannel(var2);
                    break;
                }
                this.field_147386_b.setNetHandler(new NetHandlerLoginServer(this.field_147387_a, this.field_147386_b));
                break;
            }
            case 2: {
                this.field_147386_b.setConnectionState(EnumConnectionState.STATUS);
                this.field_147386_b.setNetHandler(new NetHandlerStatusServer(this.field_147387_a, this.field_147386_b));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + (Object)((Object)p_147383_1_.func_149594_c()));
            }
        }
    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {
    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
        if (p_147232_2_ != EnumConnectionState.LOGIN && p_147232_2_ != EnumConnectionState.STATUS) {
            throw new UnsupportedOperationException("Invalid state " + (Object)((Object)p_147232_2_));
        }
    }

    @Override
    public void onNetworkTick() {
    }

    static final class SwitchEnumConnectionState {
        static final int[] field_151291_a = new int[EnumConnectionState.values().length];
        private static final String __OBFID = "CL_00001457";

        static {
            try {
                SwitchEnumConnectionState.field_151291_a[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumConnectionState.field_151291_a[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchEnumConnectionState() {
        }
    }

}

