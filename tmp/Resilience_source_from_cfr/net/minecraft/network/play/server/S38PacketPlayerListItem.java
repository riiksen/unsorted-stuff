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

public class S38PacketPlayerListItem
extends Packet {
    private String field_149126_a;
    private boolean field_149124_b;
    private int field_149125_c;
    private static final String __OBFID = "CL_00001318";

    public S38PacketPlayerListItem() {
    }

    public S38PacketPlayerListItem(String p_i45209_1_, boolean p_i45209_2_, int p_i45209_3_) {
        this.field_149126_a = p_i45209_1_;
        this.field_149124_b = p_i45209_2_;
        this.field_149125_c = p_i45209_3_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149126_a = p_148837_1_.readStringFromBuffer(16);
        this.field_149124_b = p_148837_1_.readBoolean();
        this.field_149125_c = p_148837_1_.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149126_a);
        p_148840_1_.writeBoolean(this.field_149124_b);
        p_148840_1_.writeShort(this.field_149125_c);
    }

    public void processPacket(INetHandlerPlayClient p_149123_1_) {
        p_149123_1_.handlePlayerListItem(this);
    }

    public String func_149122_c() {
        return this.field_149126_a;
    }

    public boolean func_149121_d() {
        return this.field_149124_b;
    }

    public int func_149120_e() {
        return this.field_149125_c;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

