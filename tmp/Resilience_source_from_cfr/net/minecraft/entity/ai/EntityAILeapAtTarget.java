/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget
extends EntityAIBase {
    EntityLiving leaper;
    EntityLivingBase leapTarget;
    float leapMotionY;
    private static final String __OBFID = "CL_00001591";

    public EntityAILeapAtTarget(EntityLiving par1EntityLiving, float par2) {
        this.leaper = par1EntityLiving;
        this.leapMotionY = par2;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return false;
        }
        double var1 = this.leaper.getDistanceSqToEntity(this.leapTarget);
        return var1 >= 4.0 && var1 <= 16.0 ? (!this.leaper.onGround ? false : this.leaper.getRNG().nextInt(5) == 0) : false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.leaper.onGround;
    }

    @Override
    public void startExecuting() {
        double var1 = this.leapTarget.posX - this.leaper.posX;
        double var3 = this.leapTarget.posZ - this.leaper.posZ;
        float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        this.leaper.motionX += var1 / (double)var5 * 0.5 * 0.800000011920929 + this.leaper.motionX * 0.20000000298023224;
        this.leaper.motionZ += var3 / (double)var5 * 0.5 * 0.800000011920929 + this.leaper.motionZ * 0.20000000298023224;
        this.leaper.motionY = this.leapMotionY;
    }
}

