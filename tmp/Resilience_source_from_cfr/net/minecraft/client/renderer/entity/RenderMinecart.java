/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderMinecart
extends Render {
    private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
    protected ModelBase modelMinecart = new ModelMinecart();
    protected final RenderBlocks field_94145_f;
    private static final String __OBFID = "CL_00001013";

    public RenderMinecart() {
        this.shadowSize = 0.5f;
        this.field_94145_f = new RenderBlocks();
    }

    public void doRender(EntityMinecart par1EntityMinecart, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        this.bindEntityTexture(par1EntityMinecart);
        long var10 = (long)par1EntityMinecart.getEntityId() * 493286711;
        var10 = var10 * var10 * 4392167121L + var10 * 98761;
        float var12 = (((float)(var10 >> 16 & 7) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float var13 = (((float)(var10 >> 20 & 7) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float var14 = (((float)(var10 >> 24 & 7) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        GL11.glTranslatef((float)var12, (float)var13, (float)var14);
        double var15 = par1EntityMinecart.lastTickPosX + (par1EntityMinecart.posX - par1EntityMinecart.lastTickPosX) * (double)par9;
        double var17 = par1EntityMinecart.lastTickPosY + (par1EntityMinecart.posY - par1EntityMinecart.lastTickPosY) * (double)par9;
        double var19 = par1EntityMinecart.lastTickPosZ + (par1EntityMinecart.posZ - par1EntityMinecart.lastTickPosZ) * (double)par9;
        double var21 = 0.30000001192092896;
        Vec3 var23 = par1EntityMinecart.func_70489_a(var15, var17, var19);
        float var24 = par1EntityMinecart.prevRotationPitch + (par1EntityMinecart.rotationPitch - par1EntityMinecart.prevRotationPitch) * par9;
        if (var23 != null) {
            Vec3 var25 = par1EntityMinecart.func_70495_a(var15, var17, var19, var21);
            Vec3 var26 = par1EntityMinecart.func_70495_a(var15, var17, var19, - var21);
            if (var25 == null) {
                var25 = var23;
            }
            if (var26 == null) {
                var26 = var23;
            }
            par2 += var23.xCoord - var15;
            par4 += (var25.yCoord + var26.yCoord) / 2.0 - var17;
            par6 += var23.zCoord - var19;
            Vec3 var27 = var26.addVector(- var25.xCoord, - var25.yCoord, - var25.zCoord);
            if (var27.lengthVector() != 0.0) {
                var27 = var27.normalize();
                par8 = (float)(Math.atan2(var27.zCoord, var27.xCoord) * 180.0 / 3.141592653589793);
                var24 = (float)(Math.atan(var27.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glRotatef((float)(180.0f - par8), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- var24), (float)0.0f, (float)0.0f, (float)1.0f);
        float var31 = (float)par1EntityMinecart.getRollingAmplitude() - par9;
        float var33 = par1EntityMinecart.getDamage() - par9;
        if (var33 < 0.0f) {
            var33 = 0.0f;
        }
        if (var31 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(var31) * var31 * var33 / 10.0f * (float)par1EntityMinecart.getRollingDirection()), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        int var32 = par1EntityMinecart.getDisplayTileOffset();
        Block var28 = par1EntityMinecart.func_145820_n();
        int var29 = par1EntityMinecart.getDisplayTileData();
        if (var28.getRenderType() != -1) {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float var30 = 0.75f;
            GL11.glScalef((float)var30, (float)var30, (float)var30);
            GL11.glTranslatef((float)0.0f, (float)((float)var32 / 16.0f), (float)0.0f);
            this.func_147910_a(par1EntityMinecart, par9, var28, var29);
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.bindEntityTexture(par1EntityMinecart);
        }
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelMinecart.render(par1EntityMinecart, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityMinecart par1EntityMinecart) {
        return minecartTextures;
    }

    protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_) {
        float var5 = p_147910_1_.getBrightness(p_147910_2_);
        GL11.glPushMatrix();
        this.field_94145_f.renderBlockAsItem(p_147910_3_, p_147910_4_, var5);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityMinecart)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityMinecart)par1Entity, par2, par4, par6, par8, par9);
    }
}

