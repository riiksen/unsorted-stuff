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

public class S09PacketHeldItemChange
extends Packet {
    private int field_149387_a;
    private static final String __OBFID = "CL_00001324";

    public S09PacketHeldItemChange() {
    }

    public S09PacketHeldItemChange(int p_i45215_1_) {
        this.field_149387_a = p_i45215_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149387_a = p_148837_1_.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149387_a);
    }

    public void processPacket(INetHandlerPlayClient p_149386_1_) {
        p_149386_1_.handleHeldItemChange(this);
    }

    public int func_149385_c() {
        return this.field_149387_a;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

