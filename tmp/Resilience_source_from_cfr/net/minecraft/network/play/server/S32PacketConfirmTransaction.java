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

public class S32PacketConfirmTransaction
extends Packet {
    private int field_148894_a;
    private short field_148892_b;
    private boolean field_148893_c;
    private static final String __OBFID = "CL_00001291";

    public S32PacketConfirmTransaction() {
    }

    public S32PacketConfirmTransaction(int p_i45182_1_, short p_i45182_2_, boolean p_i45182_3_) {
        this.field_148894_a = p_i45182_1_;
        this.field_148892_b = p_i45182_2_;
        this.field_148893_c = p_i45182_3_;
    }

    public void processPacket(INetHandlerPlayClient p_148891_1_) {
        p_148891_1_.handleConfirmTransaction(this);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_148894_a = p_148837_1_.readUnsignedByte();
        this.field_148892_b = p_148837_1_.readShort();
        this.field_148893_c = p_148837_1_.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_148894_a);
        p_148840_1_.writeShort(this.field_148892_b);
        p_148840_1_.writeBoolean(this.field_148893_c);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, uid=%d, accepted=%b", this.field_148894_a, this.field_148892_b, this.field_148893_c);
    }

    public int func_148889_c() {
        return this.field_148894_a;
    }

    public short func_148890_d() {
        return this.field_148892_b;
    }

    public boolean func_148888_e() {
        return this.field_148893_c;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

