/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;

public class NBTTagString
extends NBTBase {
    private String data;
    private static final String __OBFID = "CL_00001228";

    public NBTTagString() {
        this.data = "";
    }

    public NBTTagString(String par1Str) {
        this.data = par1Str;
        if (par1Str == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeUTF(this.data);
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        this.data = par1DataInput.readUTF();
    }

    @Override
    public byte getId() {
        return 8;
    }

    @Override
    public String toString() {
        return "\"" + this.data + "\"";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagString(this.data);
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (!super.equals(par1Obj)) {
            return false;
        }
        NBTTagString var2 = (NBTTagString)par1Obj;
        if (!(this.data == null && var2.data == null || this.data != null && this.data.equals(var2.data))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    @Override
    public String func_150285_a_() {
        return this.data;
    }
}

