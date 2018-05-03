/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class EntityAIFleeSun
extends EntityAIBase {
    private EntityCreature theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private double movementSpeed;
    private World theWorld;
    private static final String __OBFID = "CL_00001583";

    public EntityAIFleeSun(EntityCreature par1EntityCreature, double par2) {
        this.theCreature = par1EntityCreature;
        this.movementSpeed = par2;
        this.theWorld = par1EntityCreature.worldObj;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }
        if (!this.theCreature.isBurning()) {
            return false;
        }
        if (!this.theWorld.canBlockSeeTheSky(MathHelper.floor_double(this.theCreature.posX), (int)this.theCreature.boundingBox.minY, MathHelper.floor_double(this.theCreature.posZ))) {
            return false;
        }
        Vec3 var1 = this.findPossibleShelter();
        if (var1 == null) {
            return false;
        }
        this.shelterX = var1.xCoord;
        this.shelterY = var1.yCoord;
        this.shelterZ = var1.zCoord;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        return !this.theCreature.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    private Vec3 findPossibleShelter() {
        Random var1 = this.theCreature.getRNG();
        int var2 = 0;
        while (var2 < 10) {
            int var5;
            int var4;
            int var3 = MathHelper.floor_double(this.theCreature.posX + (double)var1.nextInt(20) - 10.0);
            if (!this.theWorld.canBlockSeeTheSky(var3, var4 = MathHelper.floor_double(this.theCreature.boundingBox.minY + (double)var1.nextInt(6) - 3.0), var5 = MathHelper.floor_double(this.theCreature.posZ + (double)var1.nextInt(20) - 10.0)) && this.theCreature.getBlockPathWeight(var3, var4, var5) < 0.0f) {
                return this.theWorld.getWorldVec3Pool().getVecFromPool(var3, var4, var5);
            }
            ++var2;
        }
        return null;
    }
}

