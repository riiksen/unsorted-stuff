/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;

public class EntityAIMoveThroughVillage
extends EntityAIBase {
    private EntityCreature theEntity;
    private double movementSpeed;
    private PathEntity entityPathNavigate;
    private VillageDoorInfo doorInfo;
    private boolean isNocturnal;
    private List doorList = new ArrayList();
    private static final String __OBFID = "CL_00001597";

    public EntityAIMoveThroughVillage(EntityCreature par1EntityCreature, double par2, boolean par4) {
        this.theEntity = par1EntityCreature;
        this.movementSpeed = par2;
        this.isNocturnal = par4;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        this.func_75414_f();
        if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
            return false;
        }
        Village var1 = this.theEntity.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ), 0);
        if (var1 == null) {
            return false;
        }
        this.doorInfo = this.func_75412_a(var1);
        if (this.doorInfo == null) {
            return false;
        }
        boolean var2 = this.theEntity.getNavigator().getCanBreakDoors();
        this.theEntity.getNavigator().setBreakDoors(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ);
        this.theEntity.getNavigator().setBreakDoors(var2);
        if (this.entityPathNavigate != null) {
            return true;
        }
        Vec3 var3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ));
        if (var3 == null) {
            return false;
        }
        this.theEntity.getNavigator().setBreakDoors(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(var3.xCoord, var3.yCoord, var3.zCoord);
        this.theEntity.getNavigator().setBreakDoors(var2);
        if (this.entityPathNavigate != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }
        float var1 = this.theEntity.width + 4.0f;
        if (this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) > (double)(var1 * var1)) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }

    @Override
    public void resetTask() {
        if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }

    private VillageDoorInfo func_75412_a(Village par1Village) {
        VillageDoorInfo var2 = null;
        int var3 = Integer.MAX_VALUE;
        List var4 = par1Village.getVillageDoorInfoList();
        for (VillageDoorInfo var6 : var4) {
            int var7 = var6.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
            if (var7 >= var3 || this.func_75413_a(var6)) continue;
            var2 = var6;
            var3 = var7;
        }
        return var2;
    }

    private boolean func_75413_a(VillageDoorInfo par1VillageDoorInfo) {
        Iterator var2 = this.doorList.iterator();
        do {
            if (!var2.hasNext()) {
                return false;
            }
            VillageDoorInfo var3 = (VillageDoorInfo)var2.next();
        } while (par1VillageDoorInfo.posX != var3.posX || par1VillageDoorInfo.posY != var3.posY || par1VillageDoorInfo.posZ != var3.posZ);
        return true;
    }

    private void func_75414_f() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }
}

