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

public class C16PacketClientStatus
extends Packet {
    private EnumState field_149437_a;
    private static final String __OBFID = "CL_00001348";

    public C16PacketClientStatus() {
    }

    public C16PacketClientStatus(EnumState p_i45242_1_) {
        this.field_149437_a = p_i45242_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149437_a = field_151404_e[p_148837_1_.readByte() % field_151404_e.length];
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149437_a.field_151403_d);
    }

    public void processPacket(INetHandlerPlayServer p_149436_1_) {
        p_149436_1_.processClientStatus(this);
    }

    public EnumState func_149435_c() {
        return this.field_149437_a;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }

    public static enum EnumState {
        PERFORM_RESPAWN("PERFORM_RESPAWN", 0, 0),
        REQUEST_STATS("REQUEST_STATS", 1, 1),
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2, 2);
        
        private final int field_151403_d;
        private static final EnumState[] field_151404_e;
        private static final EnumState[] $VALUES;
        private static final String __OBFID = "CL_00001349";

        static {
            field_151404_e = new EnumState[EnumState.values().length];
            $VALUES = new EnumState[]{PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT};
            EnumState[] var0 = EnumState.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                EnumState var3;
                EnumState.field_151404_e[var3.field_151403_d] = var3 = var0[var2];
                ++var2;
            }
        }

        private EnumState(String p_i45241_1_, int p_i45241_2_, String p_i45241_3_, int n2, int n3) {
            this.field_151403_d = p_i45241_3_;
        }
    }

}

