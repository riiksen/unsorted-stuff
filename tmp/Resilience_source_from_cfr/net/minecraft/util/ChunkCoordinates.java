/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public class ChunkCoordinates
implements Comparable {
    public int posX;
    public int posY;
    public int posZ;
    private static final String __OBFID = "CL_00001555";

    public ChunkCoordinates() {
    }

    public ChunkCoordinates(int par1, int par2, int par3) {
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
    }

    public ChunkCoordinates(ChunkCoordinates par1ChunkCoordinates) {
        this.posX = par1ChunkCoordinates.posX;
        this.posY = par1ChunkCoordinates.posY;
        this.posZ = par1ChunkCoordinates.posZ;
    }

    public boolean equals(Object par1Obj) {
        if (!(par1Obj instanceof ChunkCoordinates)) {
            return false;
        }
        ChunkCoordinates var2 = (ChunkCoordinates)par1Obj;
        if (this.posX == var2.posX && this.posY == var2.posY && this.posZ == var2.posZ) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.posX + this.posZ << 8 + this.posY << 16;
    }

    public int compareTo(ChunkCoordinates par1ChunkCoordinates) {
        return this.posY == par1ChunkCoordinates.posY ? (this.posZ == par1ChunkCoordinates.posZ ? this.posX - par1ChunkCoordinates.posX : this.posZ - par1ChunkCoordinates.posZ) : this.posY - par1ChunkCoordinates.posY;
    }

    public void set(int par1, int par2, int par3) {
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
    }

    public float getDistanceSquared(int par1, int par2, int par3) {
        float var4 = this.posX - par1;
        float var5 = this.posY - par2;
        float var6 = this.posZ - par3;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }

    public float getDistanceSquaredToChunkCoordinates(ChunkCoordinates par1ChunkCoordinates) {
        return this.getDistanceSquared(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
    }

    public String toString() {
        return "Pos{x=" + this.posX + ", y=" + this.posY + ", z=" + this.posZ + '}';
    }

    public int compareTo(Object par1Obj) {
        return this.compareTo((ChunkCoordinates)par1Obj);
    }
}

