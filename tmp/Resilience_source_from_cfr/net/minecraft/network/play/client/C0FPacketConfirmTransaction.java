/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0FPacketConfirmTransaction
extends Packet {
    private int field_149536_a;
    private short field_149534_b;
    private boolean field_149535_c;
    private static final String __OBFID = "CL_00001351";

    public C0FPacketConfirmTransaction() {
    }

    public C0FPacketConfirmTransaction(int p_i45244_1_, short p_i45244_2_, boolean p_i45244_3_) {
        this.field_149536_a = p_i45244_1_;
        this.field_149534_b = p_i45244_2_;
        this.field_149535_c = p_i45244_3_;
    }

    public void processPacket(INetHandlerPlayServer p_149531_1_) {
        p_149531_1_.processConfirmTransaction(this);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149536_a = p_148837_1_.readByte();
        this.field_149534_b = p_148837_1_.readShort();
        this.field_149535_c = p_148837_1_.readByte() != 0;
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149536_a);
        p_148840_1_.writeShort(this.field_149534_b);
        p_148840_1_.writeByte(this.field_149535_c ? 1 : 0);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, uid=%d, accepted=%b", this.field_149536_a, this.field_149534_b, this.field_149535_c);
    }

    public int func_149532_c() {
        return this.field_149536_a;
    }

    public short func_149533_d() {
        return this.field_149534_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

