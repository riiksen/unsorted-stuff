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

public class S12PacketEntityVelocity
extends Packet {
    private int field_149417_a;
    private int field_149415_b;
    private int field_149416_c;
    private int field_149414_d;
    private static final String __OBFID = "CL_00001328";

    public S12PacketEntityVelocity() {
    }

    public S12PacketEntityVelocity(Entity p_i45219_1_) {
        this(p_i45219_1_.getEntityId(), p_i45219_1_.motionX, p_i45219_1_.motionY, p_i45219_1_.motionZ);
    }

    public S12PacketEntityVelocity(int p_i45220_1_, double p_i45220_2_, double p_i45220_4_, double p_i45220_6_) {
        this.field_149417_a = p_i45220_1_;
        double var8 = 3.9;
        if (p_i45220_2_ < - var8) {
            p_i45220_2_ = - var8;
        }
        if (p_i45220_4_ < - var8) {
            p_i45220_4_ = - var8;
        }
        if (p_i45220_6_ < - var8) {
            p_i45220_6_ = - var8;
        }
        if (p_i45220_2_ > var8) {
            p_i45220_2_ = var8;
        }
        if (p_i45220_4_ > var8) {
            p_i45220_4_ = var8;
        }
        if (p_i45220_6_ > var8) {
            p_i45220_6_ = var8;
        }
        this.field_149415_b = (int)(p_i45220_2_ * 8000.0);
        this.field_149416_c = (int)(p_i45220_4_ * 8000.0);
        this.field_149414_d = (int)(p_i45220_6_ * 8000.0);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149417_a = p_148837_1_.readInt();
        this.field_149415_b = p_148837_1_.readShort();
        this.field_149416_c = p_148837_1_.readShort();
        this.field_149414_d = p_148837_1_.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149417_a);
        p_148840_1_.writeShort(this.field_149415_b);
        p_148840_1_.writeShort(this.field_149416_c);
        p_148840_1_.writeShort(this.field_149414_d);
    }

    public void processPacket(INetHandlerPlayClient p_149413_1_) {
        p_149413_1_.handleEntityVelocity(this);
    }

    @Override
    public String serialize() {
        return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", this.field_149417_a, Float.valueOf((float)this.field_149415_b / 8000.0f), Float.valueOf((float)this.field_149416_c / 8000.0f), Float.valueOf((float)this.field_149414_d / 8000.0f));
    }

    public int func_149412_c() {
        return this.field_149417_a;
    }

    public int func_149411_d() {
        return this.field_149415_b;
    }

    public int func_149410_e() {
        return this.field_149416_c;
    }

    public int func_149409_f() {
        return this.field_149414_d;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

