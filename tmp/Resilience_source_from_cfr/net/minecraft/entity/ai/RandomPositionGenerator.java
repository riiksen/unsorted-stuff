/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class RandomPositionGenerator {
    private static Vec3 staticVector = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    private static final String __OBFID = "CL_00001629";

    public static Vec3 findRandomTarget(EntityCreature par0EntityCreature, int par1, int par2) {
        return RandomPositionGenerator.findRandomTargetBlock(par0EntityCreature, par1, par2, null);
    }

    public static Vec3 findRandomTargetBlockTowards(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3) {
        RandomPositionGenerator.staticVector.xCoord = par3Vec3.xCoord - par0EntityCreature.posX;
        RandomPositionGenerator.staticVector.yCoord = par3Vec3.yCoord - par0EntityCreature.posY;
        RandomPositionGenerator.staticVector.zCoord = par3Vec3.zCoord - par0EntityCreature.posZ;
        return RandomPositionGenerator.findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3) {
        RandomPositionGenerator.staticVector.xCoord = par0EntityCreature.posX - par3Vec3.xCoord;
        RandomPositionGenerator.staticVector.yCoord = par0EntityCreature.posY - par3Vec3.yCoord;
        RandomPositionGenerator.staticVector.zCoord = par0EntityCreature.posZ - par3Vec3.zCoord;
        return RandomPositionGenerator.findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    private static Vec3 findRandomTargetBlock(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3) {
        double var11;
        double var13;
        Random var4 = par0EntityCreature.getRNG();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0f;
        boolean var10 = par0EntityCreature.hasHome() ? (var11 = (double)(par0EntityCreature.getHomePosition().getDistanceSquared(MathHelper.floor_double(par0EntityCreature.posX), MathHelper.floor_double(par0EntityCreature.posY), MathHelper.floor_double(par0EntityCreature.posZ)) + 4.0f)) < (var13 = (double)(par0EntityCreature.func_110174_bM() + (float)par1)) * var13 : false;
        int var16 = 0;
        while (var16 < 10) {
            float var15;
            int var12 = var4.nextInt(2 * par1) - par1;
            int var17 = var4.nextInt(2 * par2) - par2;
            int var14 = var4.nextInt(2 * par1) - par1;
            if (!(par3Vec3 != null && (double)var12 * par3Vec3.xCoord + (double)var14 * par3Vec3.zCoord < 0.0 || var10 && !par0EntityCreature.isWithinHomeDistance(var12, var17, var14) || (var15 = par0EntityCreature.getBlockPathWeight(var12 += MathHelper.floor_double(par0EntityCreature.posX), var17 += MathHelper.floor_double(par0EntityCreature.posY), var14 += MathHelper.floor_double(par0EntityCreature.posZ))) <= var9)) {
                var9 = var15;
                var6 = var12;
                var7 = var17;
                var8 = var14;
                var5 = true;
            }
            ++var16;
        }
        if (var5) {
            return par0EntityCreature.worldObj.getWorldVec3Pool().getVecFromPool(var6, var7, var8);
        }
        return null;
    }
}

