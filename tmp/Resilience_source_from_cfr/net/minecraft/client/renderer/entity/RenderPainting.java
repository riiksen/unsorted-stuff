/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderPainting
extends Render {
    private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
    private static final String __OBFID = "CL_00001018";

    public void doRender(EntityPainting par1EntityPainting, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)par2, (double)par4, (double)par6);
        GL11.glRotatef((float)par8, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glEnable((int)32826);
        this.bindEntityTexture(par1EntityPainting);
        EntityPainting.EnumArt var10 = par1EntityPainting.art;
        float var11 = 0.0625f;
        GL11.glScalef((float)var11, (float)var11, (float)var11);
        this.func_77010_a(par1EntityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityPainting par1EntityPainting) {
        return field_110807_a;
    }

    private void func_77010_a(EntityPainting par1EntityPainting, int par2, int par3, int par4, int par5) {
        float var6 = (float)(- par2) / 2.0f;
        float var7 = (float)(- par3) / 2.0f;
        float var8 = 0.5f;
        float var9 = 0.75f;
        float var10 = 0.8125f;
        float var11 = 0.0f;
        float var12 = 0.0625f;
        float var13 = 0.75f;
        float var14 = 0.8125f;
        float var15 = 0.001953125f;
        float var16 = 0.001953125f;
        float var17 = 0.7519531f;
        float var18 = 0.7519531f;
        float var19 = 0.0f;
        float var20 = 0.0625f;
        int var21 = 0;
        while (var21 < par2 / 16) {
            int var22 = 0;
            while (var22 < par3 / 16) {
                float var23 = var6 + (float)((var21 + 1) * 16);
                float var24 = var6 + (float)(var21 * 16);
                float var25 = var7 + (float)((var22 + 1) * 16);
                float var26 = var7 + (float)(var22 * 16);
                this.func_77008_a(par1EntityPainting, (var23 + var24) / 2.0f, (var25 + var26) / 2.0f);
                float var27 = (float)(par4 + par2 - var21 * 16) / 256.0f;
                float var28 = (float)(par4 + par2 - (var21 + 1) * 16) / 256.0f;
                float var29 = (float)(par5 + par3 - var22 * 16) / 256.0f;
                float var30 = (float)(par5 + par3 - (var22 + 1) * 16) / 256.0f;
                Tessellator var31 = Tessellator.instance;
                var31.startDrawingQuads();
                var31.setNormal(0.0f, 0.0f, -1.0f);
                var31.addVertexWithUV(var23, var26, - var8, var28, var29);
                var31.addVertexWithUV(var24, var26, - var8, var27, var29);
                var31.addVertexWithUV(var24, var25, - var8, var27, var30);
                var31.addVertexWithUV(var23, var25, - var8, var28, var30);
                var31.setNormal(0.0f, 0.0f, 1.0f);
                var31.addVertexWithUV(var23, var25, var8, var9, var11);
                var31.addVertexWithUV(var24, var25, var8, var10, var11);
                var31.addVertexWithUV(var24, var26, var8, var10, var12);
                var31.addVertexWithUV(var23, var26, var8, var9, var12);
                var31.setNormal(0.0f, 1.0f, 0.0f);
                var31.addVertexWithUV(var23, var25, - var8, var13, var15);
                var31.addVertexWithUV(var24, var25, - var8, var14, var15);
                var31.addVertexWithUV(var24, var25, var8, var14, var16);
                var31.addVertexWithUV(var23, var25, var8, var13, var16);
                var31.setNormal(0.0f, -1.0f, 0.0f);
                var31.addVertexWithUV(var23, var26, var8, var13, var15);
                var31.addVertexWithUV(var24, var26, var8, var14, var15);
                var31.addVertexWithUV(var24, var26, - var8, var14, var16);
                var31.addVertexWithUV(var23, var26, - var8, var13, var16);
                var31.setNormal(-1.0f, 0.0f, 0.0f);
                var31.addVertexWithUV(var23, var25, var8, var18, var19);
                var31.addVertexWithUV(var23, var26, var8, var18, var20);
                var31.addVertexWithUV(var23, var26, - var8, var17, var20);
                var31.addVertexWithUV(var23, var25, - var8, var17, var19);
                var31.setNormal(1.0f, 0.0f, 0.0f);
                var31.addVertexWithUV(var24, var25, - var8, var18, var19);
                var31.addVertexWithUV(var24, var26, - var8, var18, var20);
                var31.addVertexWithUV(var24, var26, var8, var17, var20);
                var31.addVertexWithUV(var24, var25, var8, var17, var19);
                var31.draw();
                ++var22;
            }
            ++var21;
        }
    }

    private void func_77008_a(EntityPainting par1EntityPainting, float par2, float par3) {
        int var4 = MathHelper.floor_double(par1EntityPainting.posX);
        int var5 = MathHelper.floor_double(par1EntityPainting.posY + (double)(par3 / 16.0f));
        int var6 = MathHelper.floor_double(par1EntityPainting.posZ);
        if (par1EntityPainting.hangingDirection == 2) {
            var4 = MathHelper.floor_double(par1EntityPainting.posX + (double)(par2 / 16.0f));
        }
        if (par1EntityPainting.hangingDirection == 1) {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ - (double)(par2 / 16.0f));
        }
        if (par1EntityPainting.hangingDirection == 0) {
            var4 = MathHelper.floor_double(par1EntityPainting.posX - (double)(par2 / 16.0f));
        }
        if (par1EntityPainting.hangingDirection == 3) {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ + (double)(par2 / 16.0f));
        }
        int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
        int var8 = var7 % 65536;
        int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityPainting)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityPainting)par1Entity, par2, par4, par6, par8, par9);
    }
}

