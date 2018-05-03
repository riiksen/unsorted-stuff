/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class EntityAIMoveTowardsRestriction
extends EntityAIBase {
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    private static final String __OBFID = "CL_00001598";

    public EntityAIMoveTowardsRestriction(EntityCreature par1EntityCreature, double par2) {
        this.theEntity = par1EntityCreature;
        this.movementSpeed = par2;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        }
        ChunkCoordinates var1 = this.theEntity.getHomePosition();
        Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY, var1.posZ));
        if (var2 == null) {
            return false;
        }
        this.movePosX = var2.xCoord;
        this.movePosY = var2.yCoord;
        this.movePosZ = var2.zCoord;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}

