/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.MathHelper;

public class NBTTagDouble
extends NBTBase.NBTPrimitive {
    private double data;
    private static final String __OBFID = "CL_00001218";

    NBTTagDouble() {
    }

    public NBTTagDouble(double p_i45130_1_) {
        this.data = p_i45130_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeDouble(this.data);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        this.data = par1DataInput.readDouble();
    }

    @Override
    public byte getId() {
        return 6;
    }

    @Override
    public String toString() {
        return "" + this.data + "d";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (super.equals(par1Obj)) {
            NBTTagDouble var2 = (NBTTagDouble)par1Obj;
            if (this.data == var2.data) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long var1 = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
    }

    @Override
    public long func_150291_c() {
        return (long)Math.floor(this.data);
    }

    @Override
    public int func_150287_d() {
        return MathHelper.floor_double(this.data);
    }

    @Override
    public short func_150289_e() {
        return (short)(MathHelper.floor_double(this.data) & 65535);
    }

    @Override
    public byte func_150290_f() {
        return (byte)(MathHelper.floor_double(this.data) & 255);
    }

    @Override
    public double func_150286_g() {
        return this.data;
    }

    @Override
    public float func_150288_h() {
        return (float)this.data;
    }
}

