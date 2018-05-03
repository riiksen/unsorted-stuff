/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import org.apache.commons.lang3.ArrayUtils;

public class S3APacketTabComplete
extends Packet {
    private String[] field_149632_a;
    private static final String __OBFID = "CL_00001288";

    public S3APacketTabComplete() {
    }

    public S3APacketTabComplete(String[] p_i45178_1_) {
        this.field_149632_a = p_i45178_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149632_a = new String[p_148837_1_.readVarIntFromBuffer()];
        int var2 = 0;
        while (var2 < this.field_149632_a.length) {
            this.field_149632_a[var2] = p_148837_1_.readStringFromBuffer(32767);
            ++var2;
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149632_a.length);
        String[] var2 = this.field_149632_a;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            String var5 = var2[var4];
            p_148840_1_.writeStringToBuffer(var5);
            ++var4;
        }
    }

    public void processPacket(INetHandlerPlayClient p_149631_1_) {
        p_149631_1_.handleTabComplete(this);
    }

    public String[] func_149630_c() {
        return this.field_149632_a;
    }

    @Override
    public String serialize() {
        return String.format("candidates='%s'", ArrayUtils.toString((Object)this.field_149632_a));
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

