/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0BPacketEntityAction
extends Packet {
    private int field_149517_a;
    private int field_149515_b;
    private int field_149516_c;
    private static final String __OBFID = "CL_00001366";

    public C0BPacketEntityAction() {
    }

    public C0BPacketEntityAction(Entity p_i45259_1_, int p_i45259_2_) {
        this(p_i45259_1_, p_i45259_2_, 0);
    }

    public C0BPacketEntityAction(Entity p_i45260_1_, int p_i45260_2_, int p_i45260_3_) {
        this.field_149517_a = p_i45260_1_.getEntityId();
        this.field_149515_b = p_i45260_2_;
        this.field_149516_c = p_i45260_3_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149517_a = p_148837_1_.readInt();
        this.field_149515_b = p_148837_1_.readByte();
        this.field_149516_c = p_148837_1_.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149517_a);
        p_148840_1_.writeByte(this.field_149515_b);
        p_148840_1_.writeInt(this.field_149516_c);
    }

    public void processPacket(INetHandlerPlayServer p_149514_1_) {
        p_149514_1_.processEntityAction(this);
    }

    public int func_149513_d() {
        return this.field_149515_b;
    }

    public int func_149512_e() {
        return this.field_149516_c;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

