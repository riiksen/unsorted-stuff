/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {
    public static float objectX;
    public static float objectY;
    public static float objectZ;
    private static IntBuffer viewport;
    private static FloatBuffer modelview;
    private static FloatBuffer projection;
    private static FloatBuffer objectCoords;
    public static float rotationX;
    public static float rotationXZ;
    public static float rotationZ;
    public static float rotationYZ;
    public static float rotationXY;
    private static final String __OBFID = "CL_00000626";

    static {
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        objectCoords = GLAllocation.createDirectFloatBuffer(3);
    }

    public static void updateRenderInfo(EntityPlayer par0EntityPlayer, boolean par1) {
        GL11.glGetFloat((int)2982, (FloatBuffer)modelview);
        GL11.glGetFloat((int)2983, (FloatBuffer)projection);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        float var2 = (viewport.get(0) + viewport.get(2)) / 2;
        float var3 = (viewport.get(1) + viewport.get(3)) / 2;
        GLU.gluUnProject((float)var2, (float)var3, (float)0.0f, (FloatBuffer)modelview, (FloatBuffer)projection, (IntBuffer)viewport, (FloatBuffer)objectCoords);
        objectX = objectCoords.get(0);
        objectY = objectCoords.get(1);
        objectZ = objectCoords.get(2);
        int var4 = par1 ? 1 : 0;
        float var5 = par0EntityPlayer.rotationPitch;
        float var6 = par0EntityPlayer.rotationYaw;
        rotationX = MathHelper.cos(var6 * 3.1415927f / 180.0f) * (float)(1 - var4 * 2);
        rotationZ = MathHelper.sin(var6 * 3.1415927f / 180.0f) * (float)(1 - var4 * 2);
        rotationYZ = (- rotationZ) * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (float)(1 - var4 * 2);
        rotationXY = rotationX * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (float)(1 - var4 * 2);
        rotationXZ = MathHelper.cos(var5 * 3.1415927f / 180.0f);
    }

    public static Vec3 projectViewFromEntity(EntityLivingBase par0EntityLivingBase, double par1) {
        double var3 = par0EntityLivingBase.prevPosX + (par0EntityLivingBase.posX - par0EntityLivingBase.prevPosX) * par1;
        double var5 = par0EntityLivingBase.prevPosY + (par0EntityLivingBase.posY - par0EntityLivingBase.prevPosY) * par1 + (double)par0EntityLivingBase.getEyeHeight();
        double var7 = par0EntityLivingBase.prevPosZ + (par0EntityLivingBase.posZ - par0EntityLivingBase.prevPosZ) * par1;
        double var9 = var3 + (double)(objectX * 1.0f);
        double var11 = var5 + (double)(objectY * 1.0f);
        double var13 = var7 + (double)(objectZ * 1.0f);
        return par0EntityLivingBase.worldObj.getWorldVec3Pool().getVecFromPool(var9, var11, var13);
    }

    public static Block getBlockAtEntityViewpoint(World p_151460_0_, EntityLivingBase p_151460_1_, float p_151460_2_) {
        float var7;
        float var6;
        Vec3 var3 = ActiveRenderInfo.projectViewFromEntity(p_151460_1_, p_151460_2_);
        ChunkPosition var4 = new ChunkPosition(var3);
        Block var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c);
        if (var5.getMaterial().isLiquid() && var3.yCoord >= (double)(var7 = (float)(var4.field_151327_b + 1) - (var6 = BlockLiquid.func_149801_b(p_151460_0_.getBlockMetadata(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c)) - 0.11111111f))) {
            var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b + 1, var4.field_151328_c);
        }
        return var5;
    }
}

