/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C13PacketPlayerAbilities
extends Packet {
    private boolean field_149500_a;
    private boolean field_149498_b;
    private boolean field_149499_c;
    private boolean field_149496_d;
    private float field_149497_e;
    private float field_149495_f;
    private static final String __OBFID = "CL_00001364";

    public C13PacketPlayerAbilities() {
    }

    public C13PacketPlayerAbilities(PlayerCapabilities p_i45257_1_) {
        this.func_149490_a(p_i45257_1_.disableDamage);
        this.func_149483_b(p_i45257_1_.isFlying);
        this.func_149491_c(p_i45257_1_.allowFlying);
        this.func_149493_d(p_i45257_1_.isCreativeMode);
        this.func_149485_a(p_i45257_1_.getFlySpeed());
        this.func_149492_b(p_i45257_1_.getWalkSpeed());
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        byte var2 = p_148837_1_.readByte();
        this.func_149490_a((var2 & 1) > 0);
        this.func_149483_b((var2 & 2) > 0);
        this.func_149491_c((var2 & 4) > 0);
        this.func_149493_d((var2 & 8) > 0);
        this.func_149485_a(p_148837_1_.readFloat());
        this.func_149492_b(p_148837_1_.readFloat());
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        byte var2 = 0;
        if (this.func_149494_c()) {
            var2 |= true;
        }
        if (this.func_149488_d()) {
            var2 = (byte)(var2 | 2);
        }
        if (this.func_149486_e()) {
            var2 = (byte)(var2 | 4);
        }
        if (this.func_149484_f()) {
            var2 = (byte)(var2 | 8);
        }
        p_148840_1_.writeByte(var2);
        p_148840_1_.writeFloat(this.field_149497_e);
        p_148840_1_.writeFloat(this.field_149495_f);
    }

    public void processPacket(INetHandlerPlayServer p_149487_1_) {
        p_149487_1_.processPlayerAbilities(this);
    }

    @Override
    public String serialize() {
        return String.format("invuln=%b, flying=%b, canfly=%b, instabuild=%b, flyspeed=%.4f, walkspped=%.4f", this.func_149494_c(), this.func_149488_d(), this.func_149486_e(), this.func_149484_f(), Float.valueOf(this.func_149482_g()), Float.valueOf(this.func_149489_h()));
    }

    public boolean func_149494_c() {
        return this.field_149500_a;
    }

    public void func_149490_a(boolean p_149490_1_) {
        this.field_149500_a = p_149490_1_;
    }

    public boolean func_149488_d() {
        return this.field_149498_b;
    }

    public void func_149483_b(boolean p_149483_1_) {
        this.field_149498_b = p_149483_1_;
    }

    public boolean func_149486_e() {
        return this.field_149499_c;
    }

    public void func_149491_c(boolean p_149491_1_) {
        this.field_149499_c = p_149491_1_;
    }

    public boolean func_149484_f() {
        return this.field_149496_d;
    }

    public void func_149493_d(boolean p_149493_1_) {
        this.field_149496_d = p_149493_1_;
    }

    public float func_149482_g() {
        return this.field_149497_e;
    }

    public void func_149485_a(float p_149485_1_) {
        this.field_149497_e = p_149485_1_;
    }

    public float func_149489_h() {
        return this.field_149495_f;
    }

    public void func_149492_b(float p_149492_1_) {
        this.field_149495_f = p_149492_1_;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}

