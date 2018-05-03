/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityAIMoveIndoors
extends EntityAIBase {
    private EntityCreature entityObj;
    private VillageDoorInfo doorInfo;
    private int insidePosX = -1;
    private int insidePosZ = -1;
    private static final String __OBFID = "CL_00001596";

    public EntityAIMoveIndoors(EntityCreature par1EntityCreature) {
        this.entityObj = par1EntityCreature;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        int var1 = MathHelper.floor_double(this.entityObj.posX);
        int var2 = MathHelper.floor_double(this.entityObj.posY);
        int var3 = MathHelper.floor_double(this.entityObj.posZ);
        if (!(this.entityObj.worldObj.isDaytime() && !this.entityObj.worldObj.isRaining() && this.entityObj.worldObj.getBiomeGenForCoords(var1, var3).canSpawnLightningBolt() || this.entityObj.worldObj.provider.hasNoSky)) {
            if (this.entityObj.getRNG().nextInt(50) != 0) {
                return false;
            }
            if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
                return false;
            }
            Village var4 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(var1, var2, var3, 14);
            if (var4 == null) {
                return false;
            }
            this.doorInfo = var4.findNearestDoorUnrestricted(var1, var2, var3);
            if (this.doorInfo != null) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.insidePosX = -1;
        if (this.entityObj.getDistanceSq(this.doorInfo.getInsidePosX(), this.doorInfo.posY, this.doorInfo.getInsidePosZ()) > 256.0) {
            Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, this.entityObj.worldObj.getWorldVec3Pool().getVecFromPool((double)this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), (double)this.doorInfo.getInsidePosZ() + 0.5));
            if (var1 != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, 1.0);
            }
        } else {
            this.entityObj.getNavigator().tryMoveToXYZ((double)this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), (double)this.doorInfo.getInsidePosZ() + 0.5, 1.0);
        }
    }

    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsidePosX();
        this.insidePosZ = this.doorInfo.getInsidePosZ();
        this.doorInfo = null;
    }
}

