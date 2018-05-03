/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget
extends EntityAITarget {
    EntityTameable theDefendingTameable;
    EntityLivingBase theOwnerAttacker;
    private int field_142051_e;
    private static final String __OBFID = "CL_00001624";

    public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable) {
        super(par1EntityTameable, false);
        this.theDefendingTameable = par1EntityTameable;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return false;
        }
        EntityLivingBase var1 = this.theDefendingTameable.getOwner();
        if (var1 == null) {
            return false;
        }
        this.theOwnerAttacker = var1.getAITarget();
        int var2 = var1.func_142015_aE();
        if (var2 != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.func_142018_a(this.theOwnerAttacker, var1)) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        EntityLivingBase var1 = this.theDefendingTameable.getOwner();
        if (var1 != null) {
            this.field_142051_e = var1.func_142015_aE();
        }
        super.startExecuting();
    }
}

