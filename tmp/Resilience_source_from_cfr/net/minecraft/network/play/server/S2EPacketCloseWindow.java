/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2EPacketCloseWindow
extends Packet {
    private int field_148896_a;
    private static final String __OBFID = "CL_00001292";

    public S2EPacketCloseWindow() {
    }

    public S2EPacketCloseWindow(int p_i45183_1_) {
        this.field_148896_a = p_i45183_1_;
    }

    public void processPacket(INetHandlerPlayClient p_148895_1_) {
        p_148895_1_.handleCloseWindow(this);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_148896_a = p_148837_1_.readUnsignedByte();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_148896_a);
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

