/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.pathfinding;

import net.minecraft.util.MathHelper;

public class PathPoint {
    public final int xCoord;
    public final int yCoord;
    public final int zCoord;
    private final int hash;
    int index = -1;
    float totalPathDistance;
    float distanceToNext;
    float distanceToTarget;
    PathPoint previous;
    public boolean isFirst;
    private static final String __OBFID = "CL_00000574";

    public PathPoint(int par1, int par2, int par3) {
        this.xCoord = par1;
        this.yCoord = par2;
        this.zCoord = par3;
        this.hash = PathPoint.makeHash(par1, par2, par3);
    }

    public static int makeHash(int par0, int par1, int par2) {
        return par1 & 255 | (par0 & 32767) << 8 | (par2 & 32767) << 24 | (par0 < 0 ? Integer.MIN_VALUE : 0) | (par2 < 0 ? 32768 : 0);
    }

    public float distanceTo(PathPoint par1PathPoint) {
        float var2 = par1PathPoint.xCoord - this.xCoord;
        float var3 = par1PathPoint.yCoord - this.yCoord;
        float var4 = par1PathPoint.zCoord - this.zCoord;
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }

    public float distanceToSquared(PathPoint par1PathPoint) {
        float var2 = par1PathPoint.xCoord - this.xCoord;
        float var3 = par1PathPoint.yCoord - this.yCoord;
        float var4 = par1PathPoint.zCoord - this.zCoord;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }

    public boolean equals(Object par1Obj) {
        if (!(par1Obj instanceof PathPoint)) {
            return false;
        }
        PathPoint var2 = (PathPoint)par1Obj;
        if (this.hash == var2.hash && this.xCoord == var2.xCoord && this.yCoord == var2.yCoord && this.zCoord == var2.zCoord) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean isAssigned() {
        if (this.index >= 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.valueOf(this.xCoord) + ", " + this.yCoord + ", " + this.zCoord;
    }
}

