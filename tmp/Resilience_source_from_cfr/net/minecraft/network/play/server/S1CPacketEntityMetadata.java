/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1CPacketEntityMetadata
extends Packet {
    private int field_149379_a;
    private List field_149378_b;
    private static final String __OBFID = "CL_00001326";

    public S1CPacketEntityMetadata() {
    }

    public S1CPacketEntityMetadata(int p_i45217_1_, DataWatcher p_i45217_2_, boolean p_i45217_3_) {
        this.field_149379_a = p_i45217_1_;
        this.field_149378_b = p_i45217_3_ ? p_i45217_2_.getAllWatched() : p_i45217_2_.getChanged();
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149379_a = p_148837_1_.readInt();
        this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149379_a);
        DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, p_148840_1_);
    }

    public void processPacket(INetHandlerPlayClient p_149377_1_) {
        p_149377_1_.handleEntityMetadata(this);
    }

    public List func_149376_c() {
        return this.field_149378_b;
    }

    public int func_149375_d() {
        return this.field_149379_a;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

