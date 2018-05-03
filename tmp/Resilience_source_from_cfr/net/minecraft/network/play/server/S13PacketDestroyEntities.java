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

public class S13PacketDestroyEntities
extends Packet {
    private int[] field_149100_a;
    private static final String __OBFID = "CL_00001320";

    public S13PacketDestroyEntities() {
    }

    public /* varargs */ S13PacketDestroyEntities(int ... p_i45211_1_) {
        this.field_149100_a = p_i45211_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149100_a = new int[p_148837_1_.readByte()];
        int var2 = 0;
        while (var2 < this.field_149100_a.length) {
            this.field_149100_a[var2] = p_148837_1_.readInt();
            ++var2;
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149100_a.length);
        int var2 = 0;
        while (var2 < this.field_149100_a.length) {
            p_148840_1_.writeInt(this.field_149100_a[var2]);
            ++var2;
        }
    }

    public void processPacket(INetHandlerPlayClient p_149099_1_) {
        p_149099_1_.handleDestroyEntities(this);
    }

    @Override
    public String serialize() {
        StringBuilder var1 = new StringBuilder();
        int var2 = 0;
        while (var2 < this.field_149100_a.length) {
            if (var2 > 0) {
                var1.append(", ");
            }
            var1.append(this.field_149100_a[var2]);
            ++var2;
        }
        return String.format("entities=%d[%s]", this.field_149100_a.length, var1);
    }

    public int[] func_149098_c() {
        return this.field_149100_a;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

