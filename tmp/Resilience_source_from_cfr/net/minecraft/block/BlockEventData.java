/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;

public class BlockEventData {
    private int coordX;
    private int coordY;
    private int coordZ;
    private Block field_151344_d;
    private int eventID;
    private int eventParameter;
    private static final String __OBFID = "CL_00000131";

    public BlockEventData(int p_i45362_1_, int p_i45362_2_, int p_i45362_3_, Block p_i45362_4_, int p_i45362_5_, int p_i45362_6_) {
        this.coordX = p_i45362_1_;
        this.coordY = p_i45362_2_;
        this.coordZ = p_i45362_3_;
        this.eventID = p_i45362_5_;
        this.eventParameter = p_i45362_6_;
        this.field_151344_d = p_i45362_4_;
    }

    public int func_151340_a() {
        return this.coordX;
    }

    public int func_151342_b() {
        return this.coordY;
    }

    public int func_151341_c() {
        return this.coordZ;
    }

    public int getEventID() {
        return this.eventID;
    }

    public int getEventParameter() {
        return this.eventParameter;
    }

    public Block getBlock() {
        return this.field_151344_d;
    }

    public boolean equals(Object par1Obj) {
        if (!(par1Obj instanceof BlockEventData)) {
            return false;
        }
        BlockEventData var2 = (BlockEventData)par1Obj;
        if (this.coordX == var2.coordX && this.coordY == var2.coordY && this.coordZ == var2.coordZ && this.eventID == var2.eventID && this.eventParameter == var2.eventParameter && this.field_151344_d == var2.field_151344_d) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "TE(" + this.coordX + "," + this.coordY + "," + this.coordZ + ")," + this.eventID + "," + this.eventParameter + "," + this.field_151344_d;
    }
}

