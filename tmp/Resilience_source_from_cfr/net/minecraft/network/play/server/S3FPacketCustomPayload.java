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

public class S3FPacketCustomPayload
extends Packet {
    private String field_149172_a;
    private byte[] field_149171_b;
    private static final String __OBFID = "CL_00001297";

    public S3FPacketCustomPayload() {
    }

    public S3FPacketCustomPayload(String p_i45189_1_, ByteBuf p_i45189_2_) {
        this(p_i45189_1_, p_i45189_2_.array());
    }

    public S3FPacketCustomPayload(String p_i45190_1_, byte[] p_i45190_2_) {
        this.field_149172_a = p_i45190_1_;
        this.field_149171_b = p_i45190_2_;
        if (p_i45190_2_.length >= 32767) {
            throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
        }
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149172_a = p_148837_1_.readStringFromBuffer(20);
        this.field_149171_b = new byte[p_148837_1_.readUnsignedShort()];
        p_148837_1_.readBytes(this.field_149171_b);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149172_a);
        p_148840_1_.writeShort(this.field_149171_b.length);
        p_148840_1_.writeBytes(this.field_149171_b);
    }

    public void processPacket(INetHandlerPlayClient p_149170_1_) {
        p_149170_1_.handleCustomPayload(this);
    }

    public String func_149169_c() {
        return this.field_149172_a;
    }

    public byte[] func_149168_d() {
        return this.field_149171_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

