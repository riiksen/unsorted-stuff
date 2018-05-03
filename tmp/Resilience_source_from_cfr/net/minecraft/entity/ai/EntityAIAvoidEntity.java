/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class EntityAIAvoidEntity
extends EntityAIBase {
    public final IEntitySelector field_98218_a;
    private EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;
    private Class targetEntityClass;
    private static final String __OBFID = "CL_00001574";

    public EntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6) {
        this.field_98218_a = new IEntitySelector(){
            private static final String __OBFID = "CL_00001575";

            @Override
            public boolean isEntityApplicable(Entity par1Entity) {
                if (par1Entity.isEntityAlive() && EntityAIAvoidEntity.this.theEntity.getEntitySenses().canSee(par1Entity)) {
                    return true;
                }
                return false;
            }
        };
        this.theEntity = par1EntityCreature;
        this.targetEntityClass = par2Class;
        this.distanceFromEntity = par3;
        this.farSpeed = par4;
        this.nearSpeed = par6;
        this.entityPathNavigate = par1EntityCreature.getNavigator();
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Vec3 var2;
        if (this.targetEntityClass == EntityPlayer.class) {
            if (this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed()) {
                return false;
            }
            this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        } else {
            List var1 = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand(this.distanceFromEntity, 3.0, this.distanceFromEntity), this.field_98218_a);
            if (var1.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = (Entity)var1.get(0);
        }
        if ((var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ))) == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
        return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(var2);
    }

    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }

}

