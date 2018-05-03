/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.world.World;

public class EntityAIOpenDoor
extends EntityAIDoorInteract {
    boolean field_75361_i;
    int field_75360_j;
    private static final String __OBFID = "CL_00001603";

    public EntityAIOpenDoor(EntityLiving par1EntityLiving, boolean par2) {
        super(par1EntityLiving);
        this.theEntity = par1EntityLiving;
        this.field_75361_i = par2;
    }

    @Override
    public boolean continueExecuting() {
        if (this.field_75361_i && this.field_75360_j > 0 && super.continueExecuting()) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.field_75360_j = 20;
        this.field_151504_e.func_150014_a(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ, true);
    }

    @Override
    public void resetTask() {
        if (this.field_75361_i) {
            this.field_151504_e.func_150014_a(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ, false);
        }
    }

    @Override
    public void updateTask() {
        --this.field_75360_j;
        super.updateTask();
    }
}

