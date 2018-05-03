/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.MathHelper;

public class NBTTagFloat
extends NBTBase.NBTPrimitive {
    private float data;
    private static final String __OBFID = "CL_00001220";

    NBTTagFloat() {
    }

    public NBTTagFloat(float p_i45131_1_) {
        this.data = p_i45131_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeFloat(this.data);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        this.data = par1DataInput.readFloat();
    }

    @Override
    public byte getId() {
        return 5;
    }

    @Override
    public String toString() {
        return "" + this.data + "f";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (super.equals(par1Obj)) {
            NBTTagFloat var2 = (NBTTagFloat)par1Obj;
            if (this.data == var2.data) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    @Override
    public long func_150291_c() {
        return (long)this.data;
    }

    @Override
    public int func_150287_d() {
        return MathHelper.floor_float(this.data);
    }

    @Override
    public short func_150289_e() {
        return (short)(MathHelper.floor_float(this.data) & 65535);
    }

    @Override
    public byte func_150290_f() {
        return (byte)(MathHelper.floor_float(this.data) & 255);
    }

    @Override
    public double func_150286_g() {
        return this.data;
    }

    @Override
    public float func_150288_h() {
        return this.data;
    }
}

