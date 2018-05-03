/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;

public class EntityAIVillagerMate
extends EntityAIBase {
    private EntityVillager villagerObj;
    private EntityVillager mate;
    private World worldObj;
    private int matingTimeout;
    Village villageObj;
    private static final String __OBFID = "CL_00001594";

    public EntityAIVillagerMate(EntityVillager par1EntityVillager) {
        this.villagerObj = par1EntityVillager;
        this.worldObj = par1EntityVillager.worldObj;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() != 0) {
            return false;
        }
        if (this.villagerObj.getRNG().nextInt(500) != 0) {
            return false;
        }
        this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.villagerObj.posX), MathHelper.floor_double(this.villagerObj.posY), MathHelper.floor_double(this.villagerObj.posZ), 0);
        if (this.villageObj == null) {
            return false;
        }
        if (!this.checkSufficientDoorsPresentForNewVillager()) {
            return false;
        }
        Entity var1 = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.boundingBox.expand(8.0, 3.0, 8.0), this.villagerObj);
        if (var1 == null) {
            return false;
        }
        this.mate = (EntityVillager)var1;
        if (this.mate.getGrowingAge() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.matingTimeout = 300;
        this.villagerObj.setMating(true);
    }

    @Override
    public void resetTask() {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating(false);
    }

    @Override
    public boolean continueExecuting() {
        if (this.matingTimeout >= 0 && this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateTask() {
        --this.matingTimeout;
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0f, 30.0f);
        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25) {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25);
        } else if (this.matingTimeout == 0 && this.mate.isMating()) {
            this.giveBirth();
        }
        if (this.villagerObj.getRNG().nextInt(35) == 0) {
            this.worldObj.setEntityState(this.villagerObj, 12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager() {
        if (!this.villageObj.isMatingSeason()) {
            return false;
        }
        int var1 = (int)((double)this.villageObj.getNumVillageDoors() * 0.35);
        if (this.villageObj.getNumVillagers() < var1) {
            return true;
        }
        return false;
    }

    private void giveBirth() {
        EntityVillager var1 = this.villagerObj.createChild(this.mate);
        this.mate.setGrowingAge(6000);
        this.villagerObj.setGrowingAge(6000);
        var1.setGrowingAge(-24000);
        var1.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(var1);
        this.worldObj.setEntityState(var1, 12);
    }
}

