/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAITradePlayer
extends EntityAIBase {
    private EntityVillager villager;
    private static final String __OBFID = "CL_00001617";

    public EntityAITradePlayer(EntityVillager par1EntityVillager) {
        this.villager = par1EntityVillager;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.villager.isEntityAlive()) {
            return false;
        }
        if (this.villager.isInWater()) {
            return false;
        }
        if (!this.villager.onGround) {
            return false;
        }
        if (this.villager.velocityChanged) {
            return false;
        }
        EntityPlayer var1 = this.villager.getCustomer();
        return var1 == null ? false : (this.villager.getDistanceSqToEntity(var1) > 16.0 ? false : var1.openContainer instanceof Container);
    }

    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPathEntity();
    }

    @Override
    public void resetTask() {
        this.villager.setCustomer(null);
    }
}

