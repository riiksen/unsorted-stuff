/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;

public class EntityAILookIdle
extends EntityAIBase {
    private EntityLiving idleEntity;
    private double lookX;
    private double lookZ;
    private int idleTime;
    private static final String __OBFID = "CL_00001607";

    public EntityAILookIdle(EntityLiving par1EntityLiving) {
        this.idleEntity = par1EntityLiving;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (this.idleEntity.getRNG().nextFloat() < 0.02f) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if (this.idleTime >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        double var1 = 6.283185307179586 * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(var1);
        this.lookZ = Math.sin(var1);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    @Override
    public void updateTask() {
        --this.idleTime;
        this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0f, this.idleEntity.getVerticalFaceSpeed());
    }
}

