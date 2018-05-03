/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAIWatchClosest
extends EntityAIBase {
    private EntityLiving theWatcher;
    protected Entity closestEntity;
    private float maxDistanceForPlayer;
    private int lookTime;
    private float field_75331_e;
    private Class watchedClass;
    private static final String __OBFID = "CL_00001592";

    public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.maxDistanceForPlayer = par3;
        this.field_75331_e = 0.02f;
        this.setMutexBits(2);
    }

    public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.maxDistanceForPlayer = par3;
        this.field_75331_e = par4;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e) {
            return false;
        }
        if (this.theWatcher.getAttackTarget() != null) {
            this.closestEntity = this.theWatcher.getAttackTarget();
        }
        this.closestEntity = this.watchedClass == EntityPlayer.class ? this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.maxDistanceForPlayer) : this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand(this.maxDistanceForPlayer, 3.0, this.maxDistanceForPlayer), this.theWatcher);
        if (this.closestEntity != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.closestEntity.isEntityAlive() ? false : (this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (double)(this.maxDistanceForPlayer * this.maxDistanceForPlayer) ? false : this.lookTime > 0);
    }

    @Override
    public void startExecuting() {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }

    @Override
    public void resetTask() {
        this.closestEntity = null;
    }

    @Override
    public void updateTask() {
        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0f, this.theWatcher.getVerticalFaceSpeed());
        --this.lookTime;
    }
}

