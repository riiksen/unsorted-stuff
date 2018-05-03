/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide
extends EntityAIBase {
    World worldObj;
    EntityCreature attacker;
    int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;
    private double field_151497_i;
    private double field_151495_j;
    private double field_151496_k;
    private static final String __OBFID = "CL_00001595";

    public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, Class par2Class, double par3, boolean par5) {
        this(par1EntityCreature, par3, par5);
        this.classTarget = par2Class;
    }

    public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, double par2, boolean par4) {
        this.attacker = par1EntityCreature;
        this.worldObj = par1EntityCreature.worldObj;
        this.speedTowardsTarget = par2;
        this.longMemory = par4;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase var1 = this.attacker.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            return false;
        }
        if (this.classTarget != null && !this.classTarget.isAssignableFrom(var1.getClass())) {
            return false;
        }
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(var1);
        if (this.entityPathEntity != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase var1 = this.attacker.getAttackTarget();
        return var1 == null ? false : (!var1.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ))));
    }

    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.field_75445_i = 0;
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        EntityLivingBase var1 = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0f, 30.0f);
        double var2 = this.attacker.getDistanceSq(var1.posX, var1.boundingBox.minY, var1.posZ);
        double var4 = this.attacker.width * 2.0f * this.attacker.width * 2.0f + var1.width;
        --this.field_75445_i;
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(var1)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0 && this.field_151495_j == 0.0 && this.field_151496_k == 0.0 || var1.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0 || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.field_151497_i = var1.posX;
            this.field_151495_j = var1.boundingBox.minY;
            this.field_151496_k = var1.posZ;
            this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
            if (var2 > 1024.0) {
                this.field_75445_i += 10;
            } else if (var2 > 256.0) {
                this.field_75445_i += 5;
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(var1, this.speedTowardsTarget)) {
                this.field_75445_i += 15;
            }
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (var2 <= var4 && this.attackTick <= 20) {
            this.attackTick = 20;
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }
            this.attacker.attackEntityAsMob(var1);
        }
    }
}

