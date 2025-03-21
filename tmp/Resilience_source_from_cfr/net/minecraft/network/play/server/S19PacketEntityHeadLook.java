/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook
extends Packet {
    private int field_149384_a;
    private byte field_149383_b;
    private static final String __OBFID = "CL_00001323";

    public S19PacketEntityHeadLook() {
    }

    public S19PacketEntityHeadLook(Entity p_i45214_1_, byte p_i45214_2_) {
        this.field_149384_a = p_i45214_1_.getEntityId();
        this.field_149383_b = p_i45214_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149384_a = p_148837_1_.readInt();
        this.field_149383_b = p_148837_1_.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149384_a);
        p_148840_1_.writeByte(this.field_149383_b);
    }

    public void processPacket(INetHandlerPlayClient p_149382_1_) {
        p_149382_1_.handleEntityHeadLook(this);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, rot=%d", this.field_149384_a, Byte.valueOf(this.field_149383_b));
    }

    public Entity func_149381_a(World p_149381_1_) {
        return p_149381_1_.getEntityByID(this.field_149384_a);
    }

    public byte func_149380_c() {
        return this.field_149383_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

