/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PathFinder {
    private IBlockAccess worldMap;
    private Path path = new Path();
    private IntHashMap pointMap = new IntHashMap();
    private PathPoint[] pathOptions = new PathPoint[32];
    private boolean isWoddenDoorAllowed;
    private boolean isMovementBlockAllowed;
    private boolean isPathingInWater;
    private boolean canEntityDrown;
    private static final String __OBFID = "CL_00000576";

    public PathFinder(IBlockAccess par1IBlockAccess, boolean par2, boolean par3, boolean par4, boolean par5) {
        this.worldMap = par1IBlockAccess;
        this.isWoddenDoorAllowed = par2;
        this.isMovementBlockAllowed = par3;
        this.isPathingInWater = par4;
        this.canEntityDrown = par5;
    }

    public PathEntity createEntityPathTo(Entity par1Entity, Entity par2Entity, float par3) {
        return this.createEntityPathTo(par1Entity, par2Entity.posX, par2Entity.boundingBox.minY, par2Entity.posZ, par3);
    }

    public PathEntity createEntityPathTo(Entity par1Entity, int par2, int par3, int par4, float par5) {
        return this.createEntityPathTo(par1Entity, (float)par2 + 0.5f, (float)par3 + 0.5f, (float)par4 + 0.5f, par5);
    }

    private PathEntity createEntityPathTo(Entity par1Entity, double par2, double par4, double par6, float par8) {
        this.path.clearPath();
        this.pointMap.clearMap();
        boolean var9 = this.isPathingInWater;
        int var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5);
        if (this.canEntityDrown && par1Entity.isInWater()) {
            var10 = (int)par1Entity.boundingBox.minY;
            Block var11 = this.worldMap.getBlock(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ));
            while (var11 == Blocks.flowing_water || var11 == Blocks.water) {
                var11 = this.worldMap.getBlock(MathHelper.floor_double(par1Entity.posX), ++var10, MathHelper.floor_double(par1Entity.posZ));
            }
            var9 = this.isPathingInWater;
            this.isPathingInWater = false;
        } else {
            var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5);
        }
        PathPoint var15 = this.openPoint(MathHelper.floor_double(par1Entity.boundingBox.minX), var10, MathHelper.floor_double(par1Entity.boundingBox.minZ));
        PathPoint var12 = this.openPoint(MathHelper.floor_double(par2 - (double)(par1Entity.width / 2.0f)), MathHelper.floor_double(par4), MathHelper.floor_double(par6 - (double)(par1Entity.width / 2.0f)));
        PathPoint var13 = new PathPoint(MathHelper.floor_float(par1Entity.width + 1.0f), MathHelper.floor_float(par1Entity.height + 1.0f), MathHelper.floor_float(par1Entity.width + 1.0f));
        PathEntity var14 = this.addToPath(par1Entity, var15, var12, var13, par8);
        this.isPathingInWater = var9;
        return var14;
    }

    private PathEntity addToPath(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5) {
        par2PathPoint.totalPathDistance = 0.0f;
        par2PathPoint.distanceToTarget = par2PathPoint.distanceToNext = par2PathPoint.distanceToSquared(par3PathPoint);
        this.path.clearPath();
        this.path.addPoint(par2PathPoint);
        PathPoint var6 = par2PathPoint;
        while (!this.path.isPathEmpty()) {
            PathPoint var7 = this.path.dequeue();
            if (var7.equals(par3PathPoint)) {
                return this.createEntityPath(par2PathPoint, par3PathPoint);
            }
            if (var7.distanceToSquared(par3PathPoint) < var6.distanceToSquared(par3PathPoint)) {
                var6 = var7;
            }
            var7.isFirst = true;
            int var8 = this.findPathOptions(par1Entity, var7, par4PathPoint, par3PathPoint, par5);
            int var9 = 0;
            while (var9 < var8) {
                PathPoint var10 = this.pathOptions[var9];
                float var11 = var7.totalPathDistance + var7.distanceToSquared(var10);
                if (!var10.isAssigned() || var11 < var10.totalPathDistance) {
                    var10.previous = var7;
                    var10.totalPathDistance = var11;
                    var10.distanceToNext = var10.distanceToSquared(par3PathPoint);
                    if (var10.isAssigned()) {
                        this.path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
                    } else {
                        var10.distanceToTarget = var10.totalPathDistance + var10.distanceToNext;
                        this.path.addPoint(var10);
                    }
                }
                ++var9;
            }
        }
        if (var6 == par2PathPoint) {
            return null;
        }
        return this.createEntityPath(par2PathPoint, var6);
    }

    private int findPathOptions(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5) {
        int var6 = 0;
        int var7 = 0;
        if (this.getVerticalOffset(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord + 1, par2PathPoint.zCoord, par3PathPoint) == 1) {
            var7 = 1;
        }
        PathPoint var8 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord + 1, par3PathPoint, var7);
        PathPoint var9 = this.getSafePoint(par1Entity, par2PathPoint.xCoord - 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
        PathPoint var10 = this.getSafePoint(par1Entity, par2PathPoint.xCoord + 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
        PathPoint var11 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord - 1, par3PathPoint, var7);
        if (var8 != null && !var8.isFirst && var8.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var8;
        }
        if (var9 != null && !var9.isFirst && var9.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var9;
        }
        if (var10 != null && !var10.isFirst && var10.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var10;
        }
        if (var11 != null && !var11.isFirst && var11.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var11;
        }
        return var6;
    }

    private PathPoint getSafePoint(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint, int par6) {
        PathPoint var7 = null;
        int var8 = this.getVerticalOffset(par1Entity, par2, par3, par4, par5PathPoint);
        if (var8 == 2) {
            return this.openPoint(par2, par3, par4);
        }
        if (var8 == 1) {
            var7 = this.openPoint(par2, par3, par4);
        }
        if (var7 == null && par6 > 0 && var8 != -3 && var8 != -4 && this.getVerticalOffset(par1Entity, par2, par3 + par6, par4, par5PathPoint) == 1) {
            var7 = this.openPoint(par2, par3 + par6, par4);
            par3 += par6;
        }
        if (var7 != null) {
            int var9 = 0;
            int var10 = 0;
            while (par3 > 0) {
                var10 = this.getVerticalOffset(par1Entity, par2, par3 - 1, par4, par5PathPoint);
                if (this.isPathingInWater && var10 == -1) {
                    return null;
                }
                if (var10 != 1) break;
                if (var9++ >= par1Entity.getMaxSafePointTries()) {
                    return null;
                }
                if (--par3 <= 0) continue;
                var7 = this.openPoint(par2, par3, par4);
            }
            if (var10 == -2) {
                return null;
            }
        }
        return var7;
    }

    private final PathPoint openPoint(int par1, int par2, int par3) {
        int var4 = PathPoint.makeHash(par1, par2, par3);
        PathPoint var5 = (PathPoint)this.pointMap.lookup(var4);
        if (var5 == null) {
            var5 = new PathPoint(par1, par2, par3);
            this.pointMap.addKey(var4, var5);
        }
        return var5;
    }

    public int getVerticalOffset(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint) {
        return PathFinder.func_82565_a(par1Entity, par2, par3, par4, par5PathPoint, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
    }

    public static int func_82565_a(Entity par0Entity, int par1, int par2, int par3, PathPoint par4PathPoint, boolean par5, boolean par6, boolean par7) {
        boolean var8 = false;
        int var9 = par1;
        while (var9 < par1 + par4PathPoint.xCoord) {
            int var10 = par2;
            while (var10 < par2 + par4PathPoint.yCoord) {
                int var11 = par3;
                while (var11 < par3 + par4PathPoint.zCoord) {
                    Block var12 = par0Entity.worldObj.getBlock(var9, var10, var11);
                    if (var12.getMaterial() != Material.air) {
                        if (var12 == Blocks.trapdoor) {
                            var8 = true;
                        } else if (var12 != Blocks.flowing_water && var12 != Blocks.water) {
                            if (!par7 && var12 == Blocks.wooden_door) {
                                return 0;
                            }
                        } else {
                            if (par5) {
                                return -1;
                            }
                            var8 = true;
                        }
                        int var13 = var12.getRenderType();
                        if (par0Entity.worldObj.getBlock(var9, var10, var11).getRenderType() == 9) {
                            int var15;
                            int var16;
                            int var17 = MathHelper.floor_double(par0Entity.posX);
                            if (par0Entity.worldObj.getBlock(var17, var15 = MathHelper.floor_double(par0Entity.posY), var16 = MathHelper.floor_double(par0Entity.posZ)).getRenderType() != 9 && par0Entity.worldObj.getBlock(var17, var15 - 1, var16).getRenderType() != 9) {
                                return -3;
                            }
                        } else if (!(var12.getBlocksMovement(par0Entity.worldObj, var9, var10, var11) || par6 && var12 == Blocks.wooden_door)) {
                            if (var13 == 11 || var12 == Blocks.fence_gate || var13 == 32) {
                                return -3;
                            }
                            if (var12 == Blocks.trapdoor) {
                                return -4;
                            }
                            Material var14 = var12.getMaterial();
                            if (var14 != Material.lava) {
                                return 0;
                            }
                            if (!par0Entity.handleLavaMovement()) {
                                return -2;
                            }
                        }
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return var8 ? 2 : 1;
    }

    private PathEntity createEntityPath(PathPoint par1PathPoint, PathPoint par2PathPoint) {
        int var3 = 1;
        PathPoint var4 = par2PathPoint;
        while (var4.previous != null) {
            ++var3;
            var4 = var4.previous;
        }
        PathPoint[] var5 = new PathPoint[var3];
        var4 = par2PathPoint;
        var5[--var3] = par2PathPoint;
        while (var4.previous != null) {
            var4 = var4.previous;
            var5[--var3] = var4;
        }
        return new PathEntity(var5);
    }
}

