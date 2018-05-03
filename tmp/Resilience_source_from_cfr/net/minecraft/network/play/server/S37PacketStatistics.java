/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class S37PacketStatistics
extends Packet {
    private Map field_148976_a;
    private static final String __OBFID = "CL_00001283";

    public S37PacketStatistics() {
    }

    public S37PacketStatistics(Map p_i45173_1_) {
        this.field_148976_a = p_i45173_1_;
    }

    public void processPacket(INetHandlerPlayClient p_148975_1_) {
        p_148975_1_.handleStatistics(this);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        int var2 = p_148837_1_.readVarIntFromBuffer();
        this.field_148976_a = Maps.newHashMap();
        int var3 = 0;
        while (var3 < var2) {
            StatBase var4 = StatList.func_151177_a(p_148837_1_.readStringFromBuffer(32767));
            int var5 = p_148837_1_.readVarIntFromBuffer();
            if (var4 != null) {
                this.field_148976_a.put(var4, var5);
            }
            ++var3;
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148976_a.size());
        for (Map.Entry var3 : this.field_148976_a.entrySet()) {
            p_148840_1_.writeStringToBuffer(((StatBase)var3.getKey()).statId);
            p_148840_1_.writeVarIntToBuffer((Integer)var3.getValue());
        }
    }

    @Override
    public String serialize() {
        return String.format("count=%d", this.field_148976_a.size());
    }

    public Map func_148974_c() {
        return this.field_148976_a;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

