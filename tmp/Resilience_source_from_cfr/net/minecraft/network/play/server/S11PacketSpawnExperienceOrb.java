/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S11PacketSpawnExperienceOrb
extends Packet {
    private int field_148992_a;
    private int field_148990_b;
    private int field_148991_c;
    private int field_148988_d;
    private int field_148989_e;
    private static final String __OBFID = "CL_00001277";

    public S11PacketSpawnExperienceOrb() {
    }

    public S11PacketSpawnExperienceOrb(EntityXPOrb p_i45167_1_) {
        this.field_148992_a = p_i45167_1_.getEntityId();
        this.field_148990_b = MathHelper.floor_double(p_i45167_1_.posX * 32.0);
        this.field_148991_c = MathHelper.floor_double(p_i45167_1_.posY * 32.0);
        this.field_148988_d = MathHelper.floor_double(p_i45167_1_.posZ * 32.0);
        this.field_148989_e = p_i45167_1_.getXpValue();
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_148992_a = p_148837_1_.readVarIntFromBuffer();
        this.field_148990_b = p_148837_1_.readInt();
        this.field_148991_c = p_148837_1_.readInt();
        this.field_148988_d = p_148837_1_.readInt();
        this.field_148989_e = p_148837_1_.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148992_a);
        p_148840_1_.writeInt(this.field_148990_b);
        p_148840_1_.writeInt(this.field_148991_c);
        p_148840_1_.writeInt(this.field_148988_d);
        p_148840_1_.writeShort(this.field_148989_e);
    }

    public void processPacket(INetHandlerPlayClient p_148987_1_) {
        p_148987_1_.handleSpawnExperienceOrb(this);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, value=%d, x=%.2f, y=%.2f, z=%.2f", this.field_148992_a, this.field_148989_e, Float.valueOf((float)this.field_148990_b / 32.0f), Float.valueOf((float)this.field_148991_c / 32.0f), Float.valueOf((float)this.field_148988_d / 32.0f));
    }

    public int func_148985_c() {
        return this.field_148992_a;
    }

    public int func_148984_d() {
        return this.field_148990_b;
    }

    public int func_148983_e() {
        return this.field_148991_c;
    }

    public int func_148982_f() {
        return this.field_148988_d;
    }

    public int func_148986_g() {
        return this.field_148989_e;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

