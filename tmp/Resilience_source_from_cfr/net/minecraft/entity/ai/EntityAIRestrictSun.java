/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityAIRestrictSun
extends EntityAIBase {
    private EntityCreature theEntity;
    private static final String __OBFID = "CL_00001611";

    public EntityAIRestrictSun(EntityCreature par1EntityCreature) {
        this.theEntity = par1EntityCreature;
    }

    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setAvoidSun(true);
    }

    @Override
    public void resetTask() {
        this.theEntity.getNavigator().setAvoidSun(false);
    }
}

