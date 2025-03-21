/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0EPacketClickWindow
extends Packet {
    private int field_149554_a;
    private int field_149552_b;
    private int field_149553_c;
    private short field_149550_d;
    private ItemStack field_149551_e;
    private int field_149549_f;
    private static final String __OBFID = "CL_00001353";

    public C0EPacketClickWindow() {
    }

    public C0EPacketClickWindow(int p_i45246_1_, int p_i45246_2_, int p_i45246_3_, int p_i45246_4_, ItemStack p_i45246_5_, short p_i45246_6_) {
        this.field_149554_a = p_i45246_1_;
        this.field_149552_b = p_i45246_2_;
        this.field_149553_c = p_i45246_3_;
        this.field_149551_e = p_i45246_5_ != null ? p_i45246_5_.copy() : null;
        this.field_149550_d = p_i45246_6_;
        this.field_149549_f = p_i45246_4_;
    }

    public void processPacket(INetHandlerPlayServer p_149545_1_) {
        p_149545_1_.processClickWindow(this);
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149554_a = p_148837_1_.readByte();
        this.field_149552_b = p_148837_1_.readShort();
        this.field_149553_c = p_148837_1_.readByte();
        this.field_149550_d = p_148837_1_.readShort();
        this.field_149549_f = p_148837_1_.readByte();
        this.field_149551_e = p_148837_1_.readItemStackFromBuffer();
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149554_a);
        p_148840_1_.writeShort(this.field_149552_b);
        p_148840_1_.writeByte(this.field_149553_c);
        p_148840_1_.writeShort(this.field_149550_d);
        p_148840_1_.writeByte(this.field_149549_f);
        p_148840_1_.writeItemStackToBuffer(this.field_149551_e);
    }

    @Override
    public String serialize() {
        return this.field_149551_e != null ? String.format("id=%d, slot=%d, button=%d, type=%d, itemid=%d, itemcount=%d, itemaux=%d", this.field_149554_a, this.field_149552_b, this.field_149553_c, this.field_149549_f, Item.getIdFromItem(this.field_149551_e.getItem()), this.field_149551_e.stackSize, this.field_149551_e.getItemDamage()) : String.format("id=%d, slot=%d, button=%d, type=%d, itemid=-1", this.field_149554_a, this.field_149552_b, this.field_149553_c, this.field_149549_f);
    }

    public int func_149548_c() {
        return this.field_149554_a;
    }

    public int func_149544_d() {
        return this.field_149552_b;
    }

    public int func_149543_e() {
        return this.field_149553_c;
    }

    public short func_149547_f() {
        return this.field_149550_d;
    }

    public ItemStack func_149546_g() {
        return this.field_149551_e;
    }

    public int func_149542_h() {
        return this.field_149549_f;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

