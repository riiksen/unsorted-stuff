/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;

public class NBTTagLong
extends NBTBase.NBTPrimitive {
    private long data;
    private static final String __OBFID = "CL_00001225";

    NBTTagLong() {
    }

    public NBTTagLong(long p_i45134_1_) {
        this.data = p_i45134_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeLong(this.data);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        this.data = par1DataInput.readLong();
    }

    @Override
    public byte getId() {
        return 4;
    }

    @Override
    public String toString() {
        return "" + this.data + "L";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (super.equals(par1Obj)) {
            NBTTagLong var2 = (NBTTagLong)par1Obj;
            if (this.data == var2.data) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }

    @Override
    public long func_150291_c() {
        return this.data;
    }

    @Override
    public int func_150287_d() {
        return (int)(this.data & -1);
    }

    @Override
    public short func_150289_e() {
        return (short)(this.data & 65535);
    }

    @Override
    public byte func_150290_f() {
        return (byte)(this.data & 255);
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

