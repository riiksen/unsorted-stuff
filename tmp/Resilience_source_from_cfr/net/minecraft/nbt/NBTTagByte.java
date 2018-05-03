/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;

public class NBTTagByte
extends NBTBase.NBTPrimitive {
    private byte data;
    private static final String __OBFID = "CL_00001214";

    NBTTagByte() {
    }

    public NBTTagByte(byte p_i45129_1_) {
        this.data = p_i45129_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeByte(this.data);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        this.data = par1DataInput.readByte();
    }

    @Override
    public byte getId() {
        return 1;
    }

    @Override
    public String toString() {
        return "" + this.data + "b";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (super.equals(par1Obj)) {
            NBTTagByte var2 = (NBTTagByte)par1Obj;
            if (this.data == var2.data) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    @Override
    public long func_150291_c() {
        return this.data;
    }

    @Override
    public int func_150287_d() {
        return this.data;
    }

    @Override
    public short func_150289_e() {
        return this.data;
    }

    @Override
    public byte func_150290_f() {
        return this.data;
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

