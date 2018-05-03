/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;

public class NBTTagEnd
extends NBTBase {
    private static final String __OBFID = "CL_00001219";

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
    }

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
    }

    @Override
    public byte getId() {
        return 0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
}

