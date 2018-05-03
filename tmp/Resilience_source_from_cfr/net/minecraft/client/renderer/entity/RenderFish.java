/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderFish
extends Render {
    private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
    private static final String __OBFID = "CL_00000996";

    public void doRender(EntityFishHook p_147922_1_, double p_147922_2_, double p_147922_4_, double p_147922_6_, float p_147922_8_, float p_147922_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)p_147922_2_), (float)((float)p_147922_4_), (float)((float)p_147922_6_));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.bindEntityTexture(p_147922_1_);
        Tessellator var10 = Tessellator.instance;
        int var11 = 1;
        int var12 = 2;
        float var13 = (float)(var11 * 8 + 0) / 128.0f;
        float var14 = (float)(var11 * 8 + 8) / 128.0f;
        float var15 = (float)(var12 * 8 + 0) / 128.0f;
        float var16 = (float)(var12 * 8 + 8) / 128.0f;
        float var17 = 1.0f;
        float var18 = 0.5f;
        float var19 = 0.5f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.setNormal(0.0f, 1.0f, 0.0f);
        var10.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var10.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var10.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var10.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var10.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        if (p_147922_1_.field_146042_b != null) {
            double var29;
            float var20 = p_147922_1_.field_146042_b.getSwingProgress(p_147922_9_);
            float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * 3.1415927f);
            Vec3 var22 = p_147922_1_.worldObj.getWorldVec3Pool().getVecFromPool(-0.5, 0.03, 0.8);
            var22.rotateAroundX((- p_147922_1_.field_146042_b.prevRotationPitch + (p_147922_1_.field_146042_b.rotationPitch - p_147922_1_.field_146042_b.prevRotationPitch) * p_147922_9_) * 3.1415927f / 180.0f);
            var22.rotateAroundY((- p_147922_1_.field_146042_b.prevRotationYaw + (p_147922_1_.field_146042_b.rotationYaw - p_147922_1_.field_146042_b.prevRotationYaw) * p_147922_9_) * 3.1415927f / 180.0f);
            var22.rotateAroundY(var21 * 0.5f);
            var22.rotateAroundX((- var21) * 0.7f);
            double var23 = p_147922_1_.field_146042_b.prevPosX + (p_147922_1_.field_146042_b.posX - p_147922_1_.field_146042_b.prevPosX) * (double)p_147922_9_ + var22.xCoord;
            double var25 = p_147922_1_.field_146042_b.prevPosY + (p_147922_1_.field_146042_b.posY - p_147922_1_.field_146042_b.prevPosY) * (double)p_147922_9_ + var22.yCoord;
            double var27 = p_147922_1_.field_146042_b.prevPosZ + (p_147922_1_.field_146042_b.posZ - p_147922_1_.field_146042_b.prevPosZ) * (double)p_147922_9_ + var22.zCoord;
            double d = var29 = p_147922_1_.field_146042_b == Minecraft.getMinecraft().thePlayer ? 0.0 : (double)p_147922_1_.field_146042_b.getEyeHeight();
            if (this.renderManager.options.thirdPersonView > 0 || p_147922_1_.field_146042_b != Minecraft.getMinecraft().thePlayer) {
                float var31 = (p_147922_1_.field_146042_b.prevRenderYawOffset + (p_147922_1_.field_146042_b.renderYawOffset - p_147922_1_.field_146042_b.prevRenderYawOffset) * p_147922_9_) * 3.1415927f / 180.0f;
                double var32 = MathHelper.sin(var31);
                double var34 = MathHelper.cos(var31);
                var23 = p_147922_1_.field_146042_b.prevPosX + (p_147922_1_.field_146042_b.posX - p_147922_1_.field_146042_b.prevPosX) * (double)p_147922_9_ - var34 * 0.35 - var32 * 0.85;
                var25 = p_147922_1_.field_146042_b.prevPosY + var29 + (p_147922_1_.field_146042_b.posY - p_147922_1_.field_146042_b.prevPosY) * (double)p_147922_9_ - 0.45;
                var27 = p_147922_1_.field_146042_b.prevPosZ + (p_147922_1_.field_146042_b.posZ - p_147922_1_.field_146042_b.prevPosZ) * (double)p_147922_9_ - var32 * 0.35 + var34 * 0.85;
            }
            double var46 = p_147922_1_.prevPosX + (p_147922_1_.posX - p_147922_1_.prevPosX) * (double)p_147922_9_;
            double var33 = p_147922_1_.prevPosY + (p_147922_1_.posY - p_147922_1_.prevPosY) * (double)p_147922_9_ + 0.25;
            double var35 = p_147922_1_.prevPosZ + (p_147922_1_.posZ - p_147922_1_.prevPosZ) * (double)p_147922_9_;
            double var37 = (float)(var23 - var46);
            double var39 = (float)(var25 - var33);
            double var41 = (float)(var27 - var35);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            var10.startDrawing(3);
            var10.setColorOpaque_I(0);
            int var43 = 16;
            int var44 = 0;
            while (var44 <= var43) {
                float var45 = (float)var44 / (float)var43;
                var10.addVertex(p_147922_2_ + var37 * (double)var45, p_147922_4_ + var39 * (double)(var45 * var45 + var45) * 0.5 + 0.25, p_147922_6_ + var41 * (double)var45);
                ++var44;
            }
            var10.draw();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
    }

    protected ResourceLocation getEntityTexture(EntityFishHook p_147921_1_) {
        return field_110792_a;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityFishHook)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityFishHook)par1Entity, par2, par4, par6, par8, par9);
    }
}

