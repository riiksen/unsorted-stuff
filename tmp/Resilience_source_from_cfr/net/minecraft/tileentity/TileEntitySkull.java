/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySkull
extends TileEntity {
    private int field_145908_a;
    private int field_145910_i;
    private String field_145909_j = "";
    private static final String __OBFID = "CL_00000364";

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setByte("SkullType", (byte)(this.field_145908_a & 255));
        p_145841_1_.setByte("Rot", (byte)(this.field_145910_i & 255));
        p_145841_1_.setString("ExtraType", this.field_145909_j);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_145908_a = p_145839_1_.getByte("SkullType");
        this.field_145910_i = p_145839_1_.getByte("Rot");
        if (p_145839_1_.func_150297_b("ExtraType", 8)) {
            this.field_145909_j = p_145839_1_.getString("ExtraType");
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 4, var1);
    }

    public void func_145905_a(int p_145905_1_, String p_145905_2_) {
        this.field_145908_a = p_145905_1_;
        this.field_145909_j = p_145905_2_;
    }

    public int func_145904_a() {
        return this.field_145908_a;
    }

    public int func_145906_b() {
        return this.field_145910_i;
    }

    public void func_145903_a(int p_145903_1_) {
        this.field_145910_i = p_145903_1_;
    }

    public String func_145907_c() {
        return this.field_145909_j;
    }
}

