/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagIntArray;

public class NBTTagList
extends NBTBase {
    private List tagList = new ArrayList();
    private byte tagType = 0;
    private static final String __OBFID = "CL_00001224";

    @Override
    void write(DataOutput par1DataOutput) throws IOException {
        this.tagType = !this.tagList.isEmpty() ? ((NBTBase)this.tagList.get(0)).getId() : 0;
        par1DataOutput.writeByte(this.tagType);
        par1DataOutput.writeInt(this.tagList.size());
        int var2 = 0;
        while (var2 < this.tagList.size()) {
            ((NBTBase)this.tagList.get(var2)).write(par1DataOutput);
            ++var2;
        }
    }

    @Override
    void load(DataInput par1DataInput, int par2) throws IOException {
        if (par2 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagType = par1DataInput.readByte();
        int var3 = par1DataInput.readInt();
        this.tagList = new ArrayList();
        int var4 = 0;
        while (var4 < var3) {
            NBTBase var5 = NBTBase.func_150284_a(this.tagType);
            var5.load(par1DataInput, par2 + 1);
            this.tagList.add(var5);
            ++var4;
        }
    }

    @Override
    public byte getId() {
        return 9;
    }

    @Override
    public String toString() {
        String var1 = "[";
        int var2 = 0;
        for (NBTBase var4 : this.tagList) {
            var1 = String.valueOf(var1) + var2 + ':' + var4 + ',';
            ++var2;
        }
        return String.valueOf(var1) + "]";
    }

    public void appendTag(NBTBase par1NBTBase) {
        if (this.tagType == 0) {
            this.tagType = par1NBTBase.getId();
        } else if (this.tagType != par1NBTBase.getId()) {
            System.err.println("WARNING: Adding mismatching tag types to tag list");
            return;
        }
        this.tagList.add(par1NBTBase);
    }

    public void func_150304_a(int p_150304_1_, NBTBase p_150304_2_) {
        if (p_150304_1_ >= 0 && p_150304_1_ < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = p_150304_2_.getId();
            } else if (this.tagType != p_150304_2_.getId()) {
                System.err.println("WARNING: Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.set(p_150304_1_, p_150304_2_);
        } else {
            System.err.println("WARNING: index out of bounds to set tag in tag list");
        }
    }

    public NBTBase removeTag(int par1) {
        return (NBTBase)this.tagList.remove(par1);
    }

    public NBTTagCompound getCompoundTagAt(int p_150305_1_) {
        if (p_150305_1_ >= 0 && p_150305_1_ < this.tagList.size()) {
            NBTBase var2 = (NBTBase)this.tagList.get(p_150305_1_);
            return var2.getId() == 10 ? (NBTTagCompound)var2 : new NBTTagCompound();
        }
        return new NBTTagCompound();
    }

    public int[] func_150306_c(int p_150306_1_) {
        if (p_150306_1_ >= 0 && p_150306_1_ < this.tagList.size()) {
            NBTBase var2 = (NBTBase)this.tagList.get(p_150306_1_);
            return var2.getId() == 11 ? ((NBTTagIntArray)var2).func_150302_c() : new int[]{};
        }
        return new int[0];
    }

    public double func_150309_d(int p_150309_1_) {
        if (p_150309_1_ >= 0 && p_150309_1_ < this.tagList.size()) {
            NBTBase var2 = (NBTBase)this.tagList.get(p_150309_1_);
            return var2.getId() == 6 ? ((NBTTagDouble)var2).func_150286_g() : 0.0;
        }
        return 0.0;
    }

    public float func_150308_e(int p_150308_1_) {
        if (p_150308_1_ >= 0 && p_150308_1_ < this.tagList.size()) {
            NBTBase var2 = (NBTBase)this.tagList.get(p_150308_1_);
            return var2.getId() == 5 ? ((NBTTagFloat)var2).func_150288_h() : 0.0f;
        }
        return 0.0f;
    }

    public String getStringTagAt(int p_150307_1_) {
        if (p_150307_1_ >= 0 && p_150307_1_ < this.tagList.size()) {
            NBTBase var2 = (NBTBase)this.tagList.get(p_150307_1_);
            return var2.getId() == 8 ? var2.func_150285_a_() : var2.toString();
        }
        return "";
    }

    public int tagCount() {
        return this.tagList.size();
    }

    @Override
    public NBTBase copy() {
        NBTTagList var1 = new NBTTagList();
        var1.tagType = this.tagType;
        for (NBTBase var3 : this.tagList) {
            NBTBase var4 = var3.copy();
            var1.tagList.add(var4);
        }
        return var1;
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (super.equals(par1Obj)) {
            NBTTagList var2 = (NBTTagList)par1Obj;
            if (this.tagType == var2.tagType) {
                return this.tagList.equals(var2.tagList);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }

    public int func_150303_d() {
        return this.tagType;
    }
}

