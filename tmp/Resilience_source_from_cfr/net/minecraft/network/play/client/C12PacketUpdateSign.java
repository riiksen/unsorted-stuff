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

public class C12PacketUpdateSign
extends Packet {
    private int field_149593_a;
    private int field_149591_b;
    private int field_149592_c;
    private String[] field_149590_d;
    private static final String __OBFID = "CL_00001370";

    public C12PacketUpdateSign() {
    }

    public C12PacketUpdateSign(int p_i45264_1_, int p_i45264_2_, int p_i45264_3_, String[] p_i45264_4_) {
        this.field_149593_a = p_i45264_1_;
        this.field_149591_b = p_i45264_2_;
        this.field_149592_c = p_i45264_3_;
        this.field_149590_d = new String[]{p_i45264_4_[0], p_i45264_4_[1], p_i45264_4_[2], p_i45264_4_[3]};
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149593_a = p_148837_1_.readInt();
        this.field_149591_b = p_148837_1_.readShort();
        this.field_149592_c = p_148837_1_.readInt();
        this.field_149590_d = new String[4];
        int var2 = 0;
        while (var2 < 4) {
            this.field_149590_d[var2] = p_148837_1_.readStringFromBuffer(15);
            ++var2;
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149593_a);
        p_148840_1_.writeShort(this.field_149591_b);
        p_148840_1_.writeInt(this.field_149592_c);
        int var2 = 0;
        while (var2 < 4) {
            p_148840_1_.writeStringToBuffer(this.field_149590_d[var2]);
            ++var2;
        }
    }

    public void processPacket(INetHandlerPlayServer p_149587_1_) {
        p_149587_1_.processUpdateSign(this);
    }

    public int func_149588_c() {
        return this.field_149593_a;
    }

    public int func_149586_d() {
        return this.field_149591_b;
    }

    public int func_149585_e() {
        return this.field_149592_c;
    }

    public String[] func_149589_f() {
        return this.field_149590_d;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

