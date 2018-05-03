/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public abstract class RenderLiving
extends RendererLivingEntity {
    private static final String __OBFID = "CL_00001015";

    public RenderLiving(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    protected boolean func_110813_b(EntityLiving par1EntityLiving) {
        if (super.func_110813_b(par1EntityLiving) && (par1EntityLiving.getAlwaysRenderNameTagForRender() || par1EntityLiving.hasCustomNameTag() && par1EntityLiving == this.renderManager.field_147941_i)) {
            return true;
        }
        return false;
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
        this.func_110827_b(par1EntityLiving, par2, par4, par6, par8, par9);
    }

    private double func_110828_a(double par1, double par3, double par5) {
        return par1 + (par3 - par1) * par5;
    }

    protected void func_110827_b(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        Entity var10 = par1EntityLiving.getLeashedToEntity();
        if (var10 != null) {
            float var48;
            par4 -= (1.6 - (double)par1EntityLiving.height) * 0.5;
            Tessellator var11 = Tessellator.instance;
            double var12 = this.func_110828_a(var10.prevRotationYaw, var10.rotationYaw, par9 * 0.5f) * 0.01745329238474369;
            double var14 = this.func_110828_a(var10.prevRotationPitch, var10.rotationPitch, par9 * 0.5f) * 0.01745329238474369;
            double var16 = Math.cos(var12);
            double var18 = Math.sin(var12);
            double var20 = Math.sin(var14);
            if (var10 instanceof EntityHanging) {
                var16 = 0.0;
                var18 = 0.0;
                var20 = -1.0;
            }
            double var22 = Math.cos(var14);
            double var24 = this.func_110828_a(var10.prevPosX, var10.posX, par9) - var16 * 0.7 - var18 * 0.5 * var22;
            double var26 = this.func_110828_a(var10.prevPosY + (double)var10.getEyeHeight() * 0.7, var10.posY + (double)var10.getEyeHeight() * 0.7, par9) - var20 * 0.5 - 0.25;
            double var28 = this.func_110828_a(var10.prevPosZ, var10.posZ, par9) - var18 * 0.7 + var16 * 0.5 * var22;
            double var30 = this.func_110828_a(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9) * 0.01745329238474369 + 1.5707963267948966;
            var16 = Math.cos(var30) * (double)par1EntityLiving.width * 0.4;
            var18 = Math.sin(var30) * (double)par1EntityLiving.width * 0.4;
            double var32 = this.func_110828_a(par1EntityLiving.prevPosX, par1EntityLiving.posX, par9) + var16;
            double var34 = this.func_110828_a(par1EntityLiving.prevPosY, par1EntityLiving.posY, par9);
            double var36 = this.func_110828_a(par1EntityLiving.prevPosZ, par1EntityLiving.posZ, par9) + var18;
            par2 += var16;
            par6 += var18;
            double var38 = (float)(var24 - var32);
            double var40 = (float)(var26 - var34);
            double var42 = (float)(var28 - var36);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2884);
            boolean var44 = true;
            double var45 = 0.025;
            var11.startDrawing(5);
            int var47 = 0;
            while (var47 <= 24) {
                if (var47 % 2 == 0) {
                    var11.setColorRGBA_F(0.5f, 0.4f, 0.3f, 1.0f);
                } else {
                    var11.setColorRGBA_F(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                var48 = (float)var47 / 24.0f;
                var11.addVertex(par2 + var38 * (double)var48 + 0.0, par4 + var40 * (double)(var48 * var48 + var48) * 0.5 + (double)((24.0f - (float)var47) / 18.0f + 0.125f), par6 + var42 * (double)var48);
                var11.addVertex(par2 + var38 * (double)var48 + 0.025, par4 + var40 * (double)(var48 * var48 + var48) * 0.5 + (double)((24.0f - (float)var47) / 18.0f + 0.125f) + 0.025, par6 + var42 * (double)var48);
                ++var47;
            }
            var11.draw();
            var11.startDrawing(5);
            var47 = 0;
            while (var47 <= 24) {
                if (var47 % 2 == 0) {
                    var11.setColorRGBA_F(0.5f, 0.4f, 0.3f, 1.0f);
                } else {
                    var11.setColorRGBA_F(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                var48 = (float)var47 / 24.0f;
                var11.addVertex(par2 + var38 * (double)var48 + 0.0, par4 + var40 * (double)(var48 * var48 + var48) * 0.5 + (double)((24.0f - (float)var47) / 18.0f + 0.125f) + 0.025, par6 + var42 * (double)var48);
                var11.addVertex(par2 + var38 * (double)var48 + 0.025, par4 + var40 * (double)(var48 * var48 + var48) * 0.5 + (double)((24.0f - (float)var47) / 18.0f + 0.125f), par6 + var42 * (double)var48 + 0.025);
                ++var47;
            }
            var11.draw();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2884);
        }
    }

    @Override
    protected boolean func_110813_b(EntityLivingBase par1EntityLivingBase) {
        return this.func_110813_b((EntityLiving)par1EntityLivingBase);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }
}

