/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.nbt.NBTBase;

public class NBTTagIntArray
extends NBTBase {
    private int[] intArray;
    private static final String __OBFID = "CL_00001221";

    NBTTagIntArray() {
    }

    public NBTTagIntArray(int[] p_i45132_1_) {
        this.intArray = p_i45132_1_;
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.intArray.length);
        int var2 = 0;
        while (var2 < this.intArray.length) {
            par1DataOutput.writeInt(this.intArray[var2]);
            ++var2;
        }
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        int var3 = par1DataInput.readInt();
        this.intArray = new int[var3];
        int var4 = 0;
        while (var4 < var3) {
            this.intArray[var4] = par1DataInput.readInt();
            ++var4;
        }
    }

    @Override
    public byte getId() {
        return 11;
    }

    @Override
    public String toString() {
        String var1 = "[";
        int[] var2 = this.intArray;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            int var5 = var2[var4];
            var1 = String.valueOf(var1) + var5 + ",";
            ++var4;
        }
        return String.valueOf(var1) + "]";
    }

    @Override
    public NBTBase copy() {
        int[] var1 = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
        return new NBTTagIntArray(var1);
    }

    @Override
    public boolean equals(Object par1Obj) {
        return super.equals(par1Obj) ? Arrays.equals(this.intArray, ((NBTTagIntArray)par1Obj).intArray) : false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] func_150302_c() {
        return this.intArray;
    }
}

