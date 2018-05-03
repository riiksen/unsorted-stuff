/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed
extends EntityAINearestAttackableTarget {
    private EntityTameable theTameable;
    private static final String __OBFID = "CL_00001623";

    public EntityAITargetNonTamed(EntityTameable par1EntityTameable, Class par2Class, int par3, boolean par4) {
        super(par1EntityTameable, par2Class, par3, par4);
        this.theTameable = par1EntityTameable;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theTameable.isTamed() && super.shouldExecute()) {
            return true;
        }
        return false;
    }
}

