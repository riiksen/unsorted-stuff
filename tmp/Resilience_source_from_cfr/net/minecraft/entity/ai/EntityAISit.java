/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAISit
extends EntityAIBase {
    private EntityTameable theEntity;
    private boolean isSitting;
    private static final String __OBFID = "CL_00001613";

    public EntityAISit(EntityTameable par1EntityTameable) {
        this.theEntity = par1EntityTameable;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }
        if (this.theEntity.isInWater()) {
            return false;
        }
        if (!this.theEntity.onGround) {
            return false;
        }
        EntityLivingBase var1 = this.theEntity.getOwner();
        return var1 == null ? true : (this.theEntity.getDistanceSqToEntity(var1) < 144.0 && var1.getAITarget() != null ? false : this.isSitting);
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }

    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }

    public void setSitting(boolean par1) {
        this.isSitting = par1;
    }
}

