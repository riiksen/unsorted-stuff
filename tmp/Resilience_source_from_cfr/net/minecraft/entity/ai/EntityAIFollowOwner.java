/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner
extends EntityAIBase {
    private EntityTameable thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private double field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;
    private static final String __OBFID = "CL_00001585";

    public EntityAIFollowOwner(EntityTameable par1EntityTameable, double par2, float par4, float par5) {
        this.thePet = par1EntityTameable;
        this.theWorld = par1EntityTameable.worldObj;
        this.field_75336_f = par2;
        this.petPathfinder = par1EntityTameable.getNavigator();
        this.minDist = par4;
        this.maxDist = par5;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase var1 = this.thePet.getOwner();
        if (var1 == null) {
            return false;
        }
        if (this.thePet.isSitting()) {
            return false;
        }
        if (this.thePet.getDistanceSqToEntity(var1) < (double)(this.minDist * this.minDist)) {
            return false;
        }
        this.theOwner = var1;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if (!this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.thePet.isSitting()) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.field_75343_h = 0;
        this.field_75344_i = this.thePet.getNavigator().getAvoidsWater();
        this.thePet.getNavigator().setAvoidsWater(false);
    }

    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        this.thePet.getNavigator().setAvoidsWater(this.field_75344_i);
    }

    @Override
    public void updateTask() {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0f, this.thePet.getVerticalFaceSpeed());
        if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f) && !this.thePet.getLeashed() && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                int var1 = MathHelper.floor_double(this.theOwner.posX) - 2;
                int var2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
                int var3 = MathHelper.floor_double(this.theOwner.boundingBox.minY);
                int var4 = 0;
                while (var4 <= 4) {
                    int var5 = 0;
                    while (var5 <= 4) {
                        if (!(var4 >= 1 && var5 >= 1 && var4 <= 3 && var5 <= 3 || !World.doesBlockHaveSolidTopSurface(this.theWorld, var1 + var4, var3 - 1, var2 + var5) || this.theWorld.getBlock(var1 + var4, var3, var2 + var5).isNormalCube() || this.theWorld.getBlock(var1 + var4, var3 + 1, var2 + var5).isNormalCube())) {
                            this.thePet.setLocationAndAngles((float)(var1 + var4) + 0.5f, var3, (float)(var2 + var5) + 0.5f, this.thePet.rotationYaw, this.thePet.rotationPitch);
                            this.petPathfinder.clearPathEntity();
                            return;
                        }
                        ++var5;
                    }
                    ++var4;
                }
            }
        }
    }
}

