/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAIFollowGolem
extends EntityAIBase {
    private EntityVillager theVillager;
    private EntityIronGolem theGolem;
    private int takeGolemRoseTick;
    private boolean tookGolemRose;
    private static final String __OBFID = "CL_00001615";

    public EntityAIFollowGolem(EntityVillager par1EntityVillager) {
        this.theVillager = par1EntityVillager;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (this.theVillager.getGrowingAge() >= 0) {
            return false;
        }
        if (!this.theVillager.worldObj.isDaytime()) {
            return false;
        }
        List var1 = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.boundingBox.expand(6.0, 2.0, 6.0));
        if (var1.isEmpty()) {
            return false;
        }
        for (EntityIronGolem var3 : var1) {
            if (var3.getHoldRoseTick() <= 0) continue;
            this.theGolem = var3;
            break;
        }
        if (this.theGolem != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if (this.theGolem.getHoldRoseTick() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
        this.tookGolemRose = false;
        this.theGolem.getNavigator().clearPathEntity();
    }

    @Override
    public void resetTask() {
        this.theGolem = null;
        this.theVillager.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0f, 30.0f);
        if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
            this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5);
            this.tookGolemRose = true;
        }
        if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity(this.theGolem) < 4.0) {
            this.theGolem.setHoldingRose(false);
            this.theVillager.getNavigator().clearPathEntity();
        }
    }
}

