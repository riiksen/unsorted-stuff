/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.world.ChunkPosition;

public class ChunkCoordIntPair {
    public final int chunkXPos;
    public final int chunkZPos;
    private int cachedHashCode = 0;
    private static final String __OBFID = "CL_00000133";

    public ChunkCoordIntPair(int par1, int par2) {
        this.chunkXPos = par1;
        this.chunkZPos = par2;
    }

    public static long chunkXZ2Int(int par0, int par1) {
        return (long)par0 & 0xFFFFFFFFL | ((long)par1 & 0xFFFFFFFFL) << 32;
    }

    public int hashCode() {
        if (this.cachedHashCode == 0) {
            int var1 = 1664525 * this.chunkXPos + 1013904223;
            int var2 = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
            this.cachedHashCode = var1 ^ var2;
        }
        return this.cachedHashCode;
    }

    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (!(par1Obj instanceof ChunkCoordIntPair)) {
            return false;
        }
        ChunkCoordIntPair var2 = (ChunkCoordIntPair)par1Obj;
        if (this.chunkXPos == var2.chunkXPos && this.chunkZPos == var2.chunkZPos) {
            return true;
        }
        return false;
    }

    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }

    public ChunkPosition func_151349_a(int p_151349_1_) {
        return new ChunkPosition(this.getCenterXPos(), p_151349_1_, this.getCenterZPosition());
    }

    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}

