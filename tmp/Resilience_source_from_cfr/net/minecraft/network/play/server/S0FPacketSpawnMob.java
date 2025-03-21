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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0FPacketSpawnMob
extends Packet {
    private int field_149042_a;
    private int field_149040_b;
    private int field_149041_c;
    private int field_149038_d;
    private int field_149039_e;
    private int field_149036_f;
    private int field_149037_g;
    private int field_149047_h;
    private byte field_149048_i;
    private byte field_149045_j;
    private byte field_149046_k;
    private DataWatcher field_149043_l;
    private List field_149044_m;
    private static final String __OBFID = "CL_00001279";

    public S0FPacketSpawnMob() {
    }

    public S0FPacketSpawnMob(EntityLivingBase p_i45192_1_) {
        this.field_149042_a = p_i45192_1_.getEntityId();
        this.field_149040_b = (byte)EntityList.getEntityID(p_i45192_1_);
        this.field_149041_c = p_i45192_1_.myEntitySize.multiplyBy32AndRound(p_i45192_1_.posX);
        this.field_149038_d = MathHelper.floor_double(p_i45192_1_.posY * 32.0);
        this.field_149039_e = p_i45192_1_.myEntitySize.multiplyBy32AndRound(p_i45192_1_.posZ);
        this.field_149048_i = (byte)(p_i45192_1_.rotationYaw * 256.0f / 360.0f);
        this.field_149045_j = (byte)(p_i45192_1_.rotationPitch * 256.0f / 360.0f);
        this.field_149046_k = (byte)(p_i45192_1_.rotationYawHead * 256.0f / 360.0f);
        double var2 = 3.9;
        double var4 = p_i45192_1_.motionX;
        double var6 = p_i45192_1_.motionY;
        double var8 = p_i45192_1_.motionZ;
        if (var4 < - var2) {
            var4 = - var2;
        }
        if (var6 < - var2) {
            var6 = - var2;
        }
        if (var8 < - var2) {
            var8 = - var2;
        }
        if (var4 > var2) {
            var4 = var2;
        }
        if (var6 > var2) {
            var6 = var2;
        }
        if (var8 > var2) {
            var8 = var2;
        }
        this.field_149036_f = (int)(var4 * 8000.0);
        this.field_149037_g = (int)(var6 * 8000.0);
        this.field_149047_h = (int)(var8 * 8000.0);
        this.field_149043_l = p_i45192_1_.getDataWatcher();
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149042_a = p_148837_1_.readVarIntFromBuffer();
        this.field_149040_b = p_148837_1_.readByte() & 255;
        this.field_149041_c = p_148837_1_.readInt();
        this.field_149038_d = p_148837_1_.readInt();
        this.field_149039_e = p_148837_1_.readInt();
        this.field_149048_i = p_148837_1_.readByte();
        this.field_149045_j = p_148837_1_.readByte();
        this.field_149046_k = p_148837_1_.readByte();
        this.field_149036_f = p_148837_1_.readShort();
        this.field_149037_g = p_148837_1_.readShort();
        this.field_149047_h = p_148837_1_.readShort();
        this.field_149044_m = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149042_a);
        p_148840_1_.writeByte(this.field_149040_b & 255);
        p_148840_1_.writeInt(this.field_149041_c);
        p_148840_1_.writeInt(this.field_149038_d);
        p_148840_1_.writeInt(this.field_149039_e);
        p_148840_1_.writeByte(this.field_149048_i);
        p_148840_1_.writeByte(this.field_149045_j);
        p_148840_1_.writeByte(this.field_149046_k);
        p_148840_1_.writeShort(this.field_149036_f);
        p_148840_1_.writeShort(this.field_149037_g);
        p_148840_1_.writeShort(this.field_149047_h);
        this.field_149043_l.func_151509_a(p_148840_1_);
    }

    public void processPacket(INetHandlerPlayClient p_149035_1_) {
        p_149035_1_.handleSpawnMob(this);
    }

    public List func_149027_c() {
        if (this.field_149044_m == null) {
            this.field_149044_m = this.field_149043_l.getAllWatched();
        }
        return this.field_149044_m;
    }

    @Override
    public String serialize() {
        return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f, xd=%.2f, yd=%.2f, zd=%.2f", this.field_149042_a, this.field_149040_b, Float.valueOf((float)this.field_149041_c / 32.0f), Float.valueOf((float)this.field_149038_d / 32.0f), Float.valueOf((float)this.field_149039_e / 32.0f), Float.valueOf((float)this.field_149036_f / 8000.0f), Float.valueOf((float)this.field_149037_g / 8000.0f), Float.valueOf((float)this.field_149047_h / 8000.0f));
    }

    public int func_149024_d() {
        return this.field_149042_a;
    }

    public int func_149025_e() {
        return this.field_149040_b;
    }

    public int func_149023_f() {
        return this.field_149041_c;
    }

    public int func_149034_g() {
        return this.field_149038_d;
    }

    public int func_149029_h() {
        return this.field_149039_e;
    }

    public int func_149026_i() {
        return this.field_149036_f;
    }

    public int func_149033_j() {
        return this.field_149037_g;
    }

    public int func_149031_k() {
        return this.field_149047_h;
    }

    public byte func_149028_l() {
        return this.field_149048_i;
    }

    public byte func_149030_m() {
        return this.field_149045_j;
    }

    public byte func_149032_n() {
        return this.field_149046_k;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

