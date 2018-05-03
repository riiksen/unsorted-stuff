/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAISwimming
extends EntityAIBase {
    private EntityLiving theEntity;
    private static final String __OBFID = "CL_00001584";

    public EntityAISwimming(EntityLiving par1EntityLiving) {
        this.theEntity = par1EntityLiving;
        this.setMutexBits(4);
        par1EntityLiving.getNavigator().setCanSwim(true);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isInWater() && !this.theEntity.handleLavaMovement()) {
            return false;
        }
        return true;
    }

    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}

