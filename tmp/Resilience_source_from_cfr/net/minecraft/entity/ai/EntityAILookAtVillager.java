/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAILookAtVillager
extends EntityAIBase {
    private EntityIronGolem theGolem;
    private EntityVillager theVillager;
    private int lookTime;
    private static final String __OBFID = "CL_00001602";

    public EntityAILookAtVillager(EntityIronGolem par1EntityIronGolem) {
        this.theGolem = par1EntityIronGolem;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theGolem.worldObj.isDaytime()) {
            return false;
        }
        if (this.theGolem.getRNG().nextInt(8000) != 0) {
            return false;
        }
        this.theVillager = (EntityVillager)this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.boundingBox.expand(6.0, 2.0, 6.0), this.theGolem);
        if (this.theVillager != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if (this.lookTime > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.lookTime = 400;
        this.theGolem.setHoldingRose(true);
    }

    @Override
    public void resetTask() {
        this.theGolem.setHoldingRose(false);
        this.theVillager = null;
    }

    @Override
    public void updateTask() {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0f, 30.0f);
        --this.lookTime;
    }
}

