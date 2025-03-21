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

public class S34PacketMaps
extends Packet {
    private int field_149191_a;
    private byte[] field_149190_b;
    private static final String __OBFID = "CL_00001311";

    public S34PacketMaps() {
    }

    public S34PacketMaps(int p_i45202_1_, byte[] p_i45202_2_) {
        this.field_149191_a = p_i45202_1_;
        this.field_149190_b = p_i45202_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149191_a = p_148837_1_.readVarIntFromBuffer();
        this.field_149190_b = new byte[p_148837_1_.readUnsignedShort()];
        p_148837_1_.readBytes(this.field_149190_b);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149191_a);
        p_148840_1_.writeShort(this.field_149190_b.length);
        p_148840_1_.writeBytes(this.field_149190_b);
    }

    public void processPacket(INetHandlerPlayClient p_149189_1_) {
        p_149189_1_.handleMaps(this);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, length=%d", this.field_149191_a, this.field_149190_b.length);
    }

    public int func_149188_c() {
        return this.field_149191_a;
    }

    public byte[] func_149187_d() {
        return this.field_149190_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

