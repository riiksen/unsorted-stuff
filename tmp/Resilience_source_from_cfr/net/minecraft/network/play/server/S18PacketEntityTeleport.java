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
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport
extends Packet {
    private int field_149458_a;
    private int field_149456_b;
    private int field_149457_c;
    private int field_149454_d;
    private byte field_149455_e;
    private byte field_149453_f;
    private static final String __OBFID = "CL_00001340";

    public S18PacketEntityTeleport() {
    }

    public S18PacketEntityTeleport(Entity p_i45233_1_) {
        this.field_149458_a = p_i45233_1_.getEntityId();
        this.field_149456_b = MathHelper.floor_double(p_i45233_1_.posX * 32.0);
        this.field_149457_c = MathHelper.floor_double(p_i45233_1_.posY * 32.0);
        this.field_149454_d = MathHelper.floor_double(p_i45233_1_.posZ * 32.0);
        this.field_149455_e = (byte)(p_i45233_1_.rotationYaw * 256.0f / 360.0f);
        this.field_149453_f = (byte)(p_i45233_1_.rotationPitch * 256.0f / 360.0f);
    }

    public S18PacketEntityTeleport(int p_i45234_1_, int p_i45234_2_, int p_i45234_3_, int p_i45234_4_, byte p_i45234_5_, byte p_i45234_6_) {
        this.field_149458_a = p_i45234_1_;
        this.field_149456_b = p_i45234_2_;
        this.field_149457_c = p_i45234_3_;
        this.field_149454_d = p_i45234_4_;
        this.field_149455_e = p_i45234_5_;
        this.field_149453_f = p_i45234_6_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149458_a = p_148837_1_.readInt();
        this.field_149456_b = p_148837_1_.readInt();
        this.field_149457_c = p_148837_1_.readInt();
        this.field_149454_d = p_148837_1_.readInt();
        this.field_149455_e = p_148837_1_.readByte();
        this.field_149453_f = p_148837_1_.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149458_a);
        p_148840_1_.writeInt(this.field_149456_b);
        p_148840_1_.writeInt(this.field_149457_c);
        p_148840_1_.writeInt(this.field_149454_d);
        p_148840_1_.writeByte(this.field_149455_e);
        p_148840_1_.writeByte(this.field_149453_f);
    }

    public void processPacket(INetHandlerPlayClient p_149452_1_) {
        p_149452_1_.handleEntityTeleport(this);
    }

    public int func_149451_c() {
        return this.field_149458_a;
    }

    public int func_149449_d() {
        return this.field_149456_b;
    }

    public int func_149448_e() {
        return this.field_149457_c;
    }

    public int func_149446_f() {
        return this.field_149454_d;
    }

    public byte func_149450_g() {
        return this.field_149455_e;
    }

    public byte func_149447_h() {
        return this.field_149453_f;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

