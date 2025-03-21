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

public class S1EPacketRemoveEntityEffect
extends Packet {
    private int field_149079_a;
    private int field_149078_b;
    private static final String __OBFID = "CL_00001321";

    public S1EPacketRemoveEntityEffect() {
    }

    public S1EPacketRemoveEntityEffect(int p_i45212_1_, PotionEffect p_i45212_2_) {
        this.field_149079_a = p_i45212_1_;
        this.field_149078_b = p_i45212_2_.getPotionID();
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149079_a = p_148837_1_.readInt();
        this.field_149078_b = p_148837_1_.readUnsignedByte();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149079_a);
        p_148840_1_.writeByte(this.field_149078_b);
    }

    public void processPacket(INetHandlerPlayClient p_149077_1_) {
        p_149077_1_.handleRemoveEntityEffect(this);
    }

    public int func_149076_c() {
        return this.field_149079_a;
    }

    public int func_149075_d() {
        return this.field_149078_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

