/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.TypeAdapterFactory
 */
package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

public class S00PacketServerInfo
extends Packet {
    private static final Gson field_149297_a = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, (Object)new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, (Object)new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, (Object)new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, (Object)new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, (Object)new ChatStyle.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
    private ServerStatusResponse field_149296_b;
    private static final String __OBFID = "CL_00001384";

    public S00PacketServerInfo() {
    }

    public S00PacketServerInfo(ServerStatusResponse p_i45273_1_) {
        this.field_149296_b = p_i45273_1_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149296_b = (ServerStatusResponse)field_149297_a.fromJson(p_148837_1_.readStringFromBuffer(32767), ServerStatusResponse.class);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(field_149297_a.toJson((Object)this.field_149296_b));
    }

    public void processPacket(INetHandlerStatusClient p_149295_1_) {
        p_149295_1_.handleServerInfo(this);
    }

    public ServerStatusResponse func_149294_c() {
        return this.field_149296_b;
    }

    @Override
    public boolean hasPriority() {
        return true;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerStatusClient)p_148833_1_);
    }
}

