/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class PathEntity {
    private final PathPoint[] points;
    private int currentPathIndex;
    private int pathLength;
    private static final String __OBFID = "CL_00000575";

    public PathEntity(PathPoint[] par1ArrayOfPathPoint) {
        this.points = par1ArrayOfPathPoint;
        this.pathLength = par1ArrayOfPathPoint.length;
    }

    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }

    public boolean isFinished() {
        if (this.currentPathIndex >= this.pathLength) {
            return true;
        }
        return false;
    }

    public PathPoint getFinalPathPoint() {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }

    public PathPoint getPathPointFromIndex(int par1) {
        return this.points[par1];
    }

    public int getCurrentPathLength() {
        return this.pathLength;
    }

    public void setCurrentPathLength(int par1) {
        this.pathLength = par1;
    }

    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }

    public void setCurrentPathIndex(int par1) {
        this.currentPathIndex = par1;
    }

    public Vec3 getVectorFromIndex(Entity par1Entity, int par2) {
        double var3 = (double)this.points[par2].xCoord + (double)((int)(par1Entity.width + 1.0f)) * 0.5;
        double var5 = this.points[par2].yCoord;
        double var7 = (double)this.points[par2].zCoord + (double)((int)(par1Entity.width + 1.0f)) * 0.5;
        return par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(var3, var5, var7);
    }

    public Vec3 getPosition(Entity par1Entity) {
        return this.getVectorFromIndex(par1Entity, this.currentPathIndex);
    }

    public boolean isSamePath(PathEntity par1PathEntity) {
        if (par1PathEntity == null) {
            return false;
        }
        if (par1PathEntity.points.length != this.points.length) {
            return false;
        }
        int var2 = 0;
        while (var2 < this.points.length) {
            if (this.points[var2].xCoord != par1PathEntity.points[var2].xCoord || this.points[var2].yCoord != par1PathEntity.points[var2].yCoord || this.points[var2].zCoord != par1PathEntity.points[var2].zCoord) {
                return false;
            }
            ++var2;
        }
        return true;
    }

    public boolean isDestinationSame(Vec3 par1Vec3) {
        PathPoint var2 = this.getFinalPathPoint();
        return var2 == null ? false : var2.xCoord == (int)par1Vec3.xCoord && var2.zCoord == (int)par1Vec3.zCoord;
    }
}

