/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PathNavigate {
    private EntityLiving theEntity;
    private World worldObj;
    private PathEntity currentPath;
    private double speed;
    private IAttributeInstance pathSearchRange;
    private boolean noSunPathfind;
    private int totalTicks;
    private int ticksAtLastPos;
    private Vec3 lastPosCheck = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    private boolean canPassOpenWoodenDoors = true;
    private boolean canPassClosedWoodenDoors;
    private boolean avoidsWater;
    private boolean canSwim;
    private static final String __OBFID = "CL_00001627";

    public PathNavigate(EntityLiving par1EntityLiving, World par2World) {
        this.theEntity = par1EntityLiving;
        this.worldObj = par2World;
        this.pathSearchRange = par1EntityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
    }

    public void setAvoidsWater(boolean par1) {
        this.avoidsWater = par1;
    }

    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }

    public void setBreakDoors(boolean par1) {
        this.canPassClosedWoodenDoors = par1;
    }

    public void setEnterDoors(boolean par1) {
        this.canPassOpenWoodenDoors = par1;
    }

    public boolean getCanBreakDoors() {
        return this.canPassClosedWoodenDoors;
    }

    public void setAvoidSun(boolean par1) {
        this.noSunPathfind = par1;
    }

    public void setSpeed(double par1) {
        this.speed = par1;
    }

    public void setCanSwim(boolean par1) {
        this.canSwim = par1;
    }

    public float func_111269_d() {
        return (float)this.pathSearchRange.getAttributeValue();
    }

    public PathEntity getPathToXYZ(double par1, double par3, double par5) {
        return !this.canNavigate() ? null : this.worldObj.getEntityPathToXYZ(this.theEntity, MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5), this.func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
    }

    public boolean tryMoveToXYZ(double par1, double par3, double par5, double par7) {
        PathEntity var9 = this.getPathToXYZ(MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5));
        return this.setPath(var9, par7);
    }

    public PathEntity getPathToEntityLiving(Entity par1Entity) {
        return !this.canNavigate() ? null : this.worldObj.getPathEntityToEntity(this.theEntity, par1Entity, this.func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
    }

    public boolean tryMoveToEntityLiving(Entity par1Entity, double par2) {
        PathEntity var4 = this.getPathToEntityLiving(par1Entity);
        return var4 != null ? this.setPath(var4, par2) : false;
    }

    public boolean setPath(PathEntity par1PathEntity, double par2) {
        if (par1PathEntity == null) {
            this.currentPath = null;
            return false;
        }
        if (!par1PathEntity.isSamePath(this.currentPath)) {
            this.currentPath = par1PathEntity;
        }
        if (this.noSunPathfind) {
            this.removeSunnyPath();
        }
        if (this.currentPath.getCurrentPathLength() == 0) {
            return false;
        }
        this.speed = par2;
        Vec3 var4 = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck.xCoord = var4.xCoord;
        this.lastPosCheck.yCoord = var4.yCoord;
        this.lastPosCheck.zCoord = var4.zCoord;
        return true;
    }

    public PathEntity getPath() {
        return this.currentPath;
    }

    public void onUpdateNavigation() {
        ++this.totalTicks;
        if (!this.noPath()) {
            Vec3 var1;
            if (this.canNavigate()) {
                this.pathFollow();
            }
            if (!this.noPath() && (var1 = this.currentPath.getPosition(this.theEntity)) != null) {
                this.theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, this.speed);
            }
        }
    }

    private void pathFollow() {
        Vec3 var1 = this.getEntityPosition();
        int var2 = this.currentPath.getCurrentPathLength();
        int var3 = this.currentPath.getCurrentPathIndex();
        while (var3 < this.currentPath.getCurrentPathLength()) {
            if (this.currentPath.getPathPointFromIndex((int)var3).yCoord != (int)var1.yCoord) {
                var2 = var3;
                break;
            }
            ++var3;
        }
        float var8 = this.theEntity.width * this.theEntity.width;
        int var4 = this.currentPath.getCurrentPathIndex();
        while (var4 < var2) {
            if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var4)) < (double)var8) {
                this.currentPath.setCurrentPathIndex(var4 + 1);
            }
            ++var4;
        }
        var4 = MathHelper.ceiling_float_int(this.theEntity.width);
        int var5 = (int)this.theEntity.height + 1;
        int var6 = var4;
        int var7 = var2 - 1;
        while (var7 >= this.currentPath.getCurrentPathIndex()) {
            if (this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var7), var4, var5, var6)) {
                this.currentPath.setCurrentPathIndex(var7);
                break;
            }
            --var7;
        }
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (var1.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck.xCoord = var1.xCoord;
            this.lastPosCheck.yCoord = var1.yCoord;
            this.lastPosCheck.zCoord = var1.zCoord;
        }
    }

    public boolean noPath() {
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            return false;
        }
        return true;
    }

    public void clearPathEntity() {
        this.currentPath = null;
    }

    private Vec3 getEntityPosition() {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(this.theEntity.posX, this.getPathableYPos(), this.theEntity.posZ);
    }

    private int getPathableYPos() {
        if (this.theEntity.isInWater() && this.canSwim) {
            int var1 = (int)this.theEntity.boundingBox.minY;
            Block var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
            int var3 = 0;
            do {
                if (var2 != Blocks.flowing_water && var2 != Blocks.water) {
                    return var1;
                }
                var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), ++var1, MathHelper.floor_double(this.theEntity.posZ));
            } while (++var3 <= 16);
            return (int)this.theEntity.boundingBox.minY;
        }
        return (int)(this.theEntity.boundingBox.minY + 0.5);
    }

    private boolean canNavigate() {
        if (!(this.theEntity.onGround || this.canSwim && this.isInFluid())) {
            return false;
        }
        return true;
    }

    private boolean isInFluid() {
        if (!this.theEntity.isInWater() && !this.theEntity.handleLavaMovement()) {
            return false;
        }
        return true;
    }

    private void removeSunnyPath() {
        if (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5), MathHelper.floor_double(this.theEntity.posZ))) {
            int var1 = 0;
            while (var1 < this.currentPath.getCurrentPathLength()) {
                PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
                if (this.worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord)) {
                    this.currentPath.setCurrentPathLength(var1 - 1);
                    return;
                }
                ++var1;
            }
        }
    }

    private boolean isDirectPathBetweenPoints(Vec3 par1Vec3, Vec3 par2Vec3, int par3, int par4, int par5) {
        int var6 = MathHelper.floor_double(par1Vec3.xCoord);
        int var7 = MathHelper.floor_double(par1Vec3.zCoord);
        double var8 = par2Vec3.xCoord - par1Vec3.xCoord;
        double var10 = par2Vec3.zCoord - par1Vec3.zCoord;
        double var12 = var8 * var8 + var10 * var10;
        if (var12 < 1.0E-8) {
            return false;
        }
        double var14 = 1.0 / Math.sqrt(var12);
        if (!this.isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3 += 2, par4, par5 += 2, par1Vec3, var8 *= var14, var10 *= var14)) {
            return false;
        }
        par3 -= 2;
        par5 -= 2;
        double var16 = 1.0 / Math.abs(var8);
        double var18 = 1.0 / Math.abs(var10);
        double var20 = (double)(var6 * 1) - par1Vec3.xCoord;
        double var22 = (double)(var7 * 1) - par1Vec3.zCoord;
        if (var8 >= 0.0) {
            var20 += 1.0;
        }
        if (var10 >= 0.0) {
            var22 += 1.0;
        }
        var20 /= var8;
        var22 /= var10;
        int var24 = var8 < 0.0 ? -1 : 1;
        int var25 = var10 < 0.0 ? -1 : 1;
        int var26 = MathHelper.floor_double(par2Vec3.xCoord);
        int var27 = MathHelper.floor_double(par2Vec3.zCoord);
        int var28 = var26 - var6;
        int var29 = var27 - var7;
        do {
            if (var28 * var24 <= 0 && var29 * var25 <= 0) {
                return true;
            }
            if (var20 < var22) {
                var20 += var16;
                var28 = var26 - (var6 += var24);
                continue;
            }
            var22 += var18;
            var29 = var27 - (var7 += var25);
        } while (this.isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3, par4, par5, par1Vec3, var8, var10));
        return false;
    }

    private boolean isSafeToStandAt(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10) {
        int var12 = par1 - par4 / 2;
        int var13 = par3 - par6 / 2;
        if (!this.isPositionClear(var12, par2, var13, par4, par5, par6, par7Vec3, par8, par10)) {
            return false;
        }
        int var14 = var12;
        while (var14 < var12 + par4) {
            int var15 = var13;
            while (var15 < var13 + par6) {
                double var16 = (double)var14 + 0.5 - par7Vec3.xCoord;
                double var18 = (double)var15 + 0.5 - par7Vec3.zCoord;
                if (var16 * par8 + var18 * par10 >= 0.0) {
                    Block var20 = this.worldObj.getBlock(var14, par2 - 1, var15);
                    Material var21 = var20.getMaterial();
                    if (var21 == Material.air) {
                        return false;
                    }
                    if (var21 == Material.water && !this.theEntity.isInWater()) {
                        return false;
                    }
                    if (var21 == Material.lava) {
                        return false;
                    }
                }
                ++var15;
            }
            ++var14;
        }
        return true;
    }

    private boolean isPositionClear(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10) {
        int var12 = par1;
        while (var12 < par1 + par4) {
            int var13 = par2;
            while (var13 < par2 + par5) {
                int var14 = par3;
                while (var14 < par3 + par6) {
                    Block var19;
                    double var15 = (double)var12 + 0.5 - par7Vec3.xCoord;
                    double var17 = (double)var14 + 0.5 - par7Vec3.zCoord;
                    if (var15 * par8 + var17 * par10 >= 0.0 && !(var19 = this.worldObj.getBlock(var12, var13, var14)).getBlocksMovement(this.worldObj, var12, var13, var14)) {
                        return false;
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
        return true;
    }
}

