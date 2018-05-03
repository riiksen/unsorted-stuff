/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.World;

public class C02PacketUseEntity
extends Packet {
    private int field_149567_a;
    private Action field_149566_b;
    private static final String __OBFID = "CL_00001357";

    public C02PacketUseEntity() {
    }

    public C02PacketUseEntity(Entity p_i45251_1_, Action p_i45251_2_) {
        this.field_149567_a = p_i45251_1_.getEntityId();
        this.field_149566_b = p_i45251_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149567_a = p_148837_1_.readInt();
        this.field_149566_b = field_151421_c[p_148837_1_.readByte() % field_151421_c.length];
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149567_a);
        p_148840_1_.writeByte(this.field_149566_b.field_151418_d);
    }

    public void processPacket(INetHandlerPlayServer p_149563_1_) {
        p_149563_1_.processUseEntity(this);
    }

    public Entity func_149564_a(World p_149564_1_) {
        return p_149564_1_.getEntityByID(this.field_149567_a);
    }

    public Action func_149565_c() {
        return this.field_149566_b;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }

    public static enum Action {
        INTERACT("INTERACT", 0, 0),
        ATTACK("ATTACK", 1, 1);
        
        private static final Action[] field_151421_c;
        private final int field_151418_d;
        private static final Action[] $VALUES;
        private static final String __OBFID = "CL_00001358";

        static {
            field_151421_c = new Action[Action.values().length];
            $VALUES = new Action[]{INTERACT, ATTACK};
            Action[] var0 = Action.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                Action var3;
                Action.field_151421_c[var3.field_151418_d] = var3 = var0[var2];
                ++var2;
            }
        }

        private Action(String p_i45250_1_, int p_i45250_2_, String p_i45250_3_, int n2, int n3) {
            this.field_151418_d = p_i45250_3_;
        }
    }

}

