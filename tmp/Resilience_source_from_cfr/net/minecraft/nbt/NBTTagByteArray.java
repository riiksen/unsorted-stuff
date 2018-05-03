/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.nbt.NBTBase;

public class NBTTagByteArray
extends NBTBase {
    private byte[] byteArray;
    private static final String __OBFID = "CL_00001213";

    NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] p_i45128_1_) {
        this.byteArray = p_i45128_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.byteArray.length);
        par1DataOutput.write(this.byteArray);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        int var3 = par1DataInput.readInt();
        this.byteArray = new byte[var3];
        par1DataInput.readFully(this.byteArray);
    }

    @Override
    public byte getId() {
        return 7;
    }

    @Override
    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        byte[] var1 = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, var1, 0, this.byteArray.length);
        return new NBTTagByteArray(var1);
    }

    @Override
    public boolean equals(Object par1Obj) {
        return super.equals(par1Obj) ? Arrays.equals(this.byteArray, ((NBTTagByteArray)par1Obj).byteArray) : false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }

    public byte[] func_150292_c() {
        return this.byteArray;
    }
}

