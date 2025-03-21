/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S02PacketChat
extends Packet {
    private IChatComponent field_148919_a;
    private boolean field_148918_b = true;
    private static final String __OBFID = "CL_00001289";

    public S02PacketChat() {
    }

    public S02PacketChat(IChatComponent p_i45179_1_) {
        this(p_i45179_1_, true);
    }

    public S02PacketChat(IChatComponent p_i45180_1_, boolean p_i45180_2_) {
        this.field_148919_a = p_i45180_1_;
        this.field_148918_b = p_i45180_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_148919_a = IChatComponent.Serializer.func_150699_a(p_148837_1_.readStringFromBuffer(32767));
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(IChatComponent.Serializer.func_150696_a(this.field_148919_a));
    }

    public void processPacket(INetHandlerPlayClient p_148917_1_) {
        p_148917_1_.handleChat(this);
    }

    @Override
    public String serialize() {
        return String.format("message='%s'", this.field_148919_a);
    }

    public IChatComponent func_148915_c() {
        return this.field_148919_a;
    }

    public boolean func_148916_d() {
        return this.field_148918_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

