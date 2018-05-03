/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete
extends Packet {
    private String field_149420_a;
    private static final String __OBFID = "CL_00001346";

    public C14PacketTabComplete() {
    }

    public C14PacketTabComplete(String p_i45239_1_) {
        this.field_149420_a = p_i45239_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149420_a = p_148837_1_.readStringFromBuffer(32767);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(StringUtils.substring((String)this.field_149420_a, (int)0, (int)32767));
    }

    public void processPacket(INetHandlerPlayServer p_149418_1_) {
        p_149418_1_.processTabComplete(this);
    }

    public String func_149419_c() {
        return this.field_149420_a;
    }

    @Override
    public String serialize() {
        return String.format("message='%s'", this.field_149420_a);
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

