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

public class S2BPacketChangeGameState
extends Packet {
    public static final String[] field_149142_a;
    private int field_149140_b;
    private float field_149141_c;
    private static final String __OBFID = "CL_00001301";

    static {
        String[] arrstring = new String[4];
        arrstring[0] = "tile.bed.notValid";
        arrstring[3] = "gameMode.changed";
        field_149142_a = arrstring;
    }

    public S2BPacketChangeGameState() {
    }

    public S2BPacketChangeGameState(int p_i45194_1_, float p_i45194_2_) {
        this.field_149140_b = p_i45194_1_;
        this.field_149141_c = p_i45194_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149140_b = p_148837_1_.readUnsignedByte();
        this.field_149141_c = p_148837_1_.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149140_b);
        p_148840_1_.writeFloat(this.field_149141_c);
    }

    public void processPacket(INetHandlerPlayClient p_149139_1_) {
        p_149139_1_.handleChangeGameState(this);
    }

    public int func_149138_c() {
        return this.field_149140_b;
    }

    public float func_149137_d() {
        return this.field_149141_c;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

