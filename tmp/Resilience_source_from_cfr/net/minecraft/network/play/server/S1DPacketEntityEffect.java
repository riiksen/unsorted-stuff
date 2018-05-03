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
import net.minecraft.potion.PotionEffect;

public class S1DPacketEntityEffect
extends Packet {
    private int field_149434_a;
    private byte field_149432_b;
    private byte field_149433_c;
    private short field_149431_d;
    private static final String __OBFID = "CL_00001343";

    public S1DPacketEntityEffect() {
    }

    public S1DPacketEntityEffect(int p_i45237_1_, PotionEffect p_i45237_2_) {
        this.field_149434_a = p_i45237_1_;
        this.field_149432_b = (byte)(p_i45237_2_.getPotionID() & 255);
        this.field_149433_c = (byte)(p_i45237_2_.getAmplifier() & 255);
        this.field_149431_d = p_i45237_2_.getDuration() > 32767 ? 32767 : (short)p_i45237_2_.getDuration();
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149434_a = p_148837_1_.readInt();
        this.field_149432_b = p_148837_1_.readByte();
        this.field_149433_c = p_148837_1_.readByte();
        this.field_149431_d = p_148837_1_.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149434_a);
        p_148840_1_.writeByte(this.field_149432_b);
        p_148840_1_.writeByte(this.field_149433_c);
        p_148840_1_.writeShort(this.field_149431_d);
    }

    public boolean func_149429_c() {
        if (this.field_149431_d == 32767) {
            return true;
        }
        return false;
    }

    public void processPacket(INetHandlerPlayClient p_149430_1_) {
        p_149430_1_.handleEntityEffect(this);
    }

    public int func_149426_d() {
        return this.field_149434_a;
    }

    public byte func_149427_e() {
        return this.field_149432_b;
    }

    public byte func_149428_f() {
        return this.field_149433_c;
    }

    public short func_149425_g() {
        return this.field_149431_d;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

