/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderArrow
extends Render {
    private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    private static final String __OBFID = "CL_00000978";

    public void doRender(EntityArrow par1EntityArrow, double par2, double par4, double par6, float par8, float par9) {
        this.bindEntityTexture(par1EntityArrow);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glRotatef((float)(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9), (float)0.0f, (float)0.0f, (float)1.0f);
        Tessellator var10 = Tessellator.instance;
        int var11 = 0;
        float var12 = 0.0f;
        float var13 = 0.5f;
        float var14 = (float)(0 + var11 * 10) / 32.0f;
        float var15 = (float)(5 + var11 * 10) / 32.0f;
        float var16 = 0.0f;
        float var17 = 0.15625f;
        float var18 = (float)(5 + var11 * 10) / 32.0f;
        float var19 = (float)(10 + var11 * 10) / 32.0f;
        float var20 = 0.05625f;
        GL11.glEnable((int)32826);
        float var21 = (float)par1EntityArrow.arrowShake - par9;
        if (var21 > 0.0f) {
            float var22 = (- MathHelper.sin(var21 * 3.0f)) * var21;
            GL11.glRotatef((float)var22, (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glRotatef((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)var20, (float)var20, (float)var20);
        GL11.glTranslatef((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)var20, (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var19);
        var10.draw();
        GL11.glNormal3f((float)(- var20), (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var19);
        var10.draw();
        int var23 = 0;
        while (var23 < 4) {
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)var20);
            var10.startDrawingQuads();
            var10.addVertexWithUV(-8.0, -2.0, 0.0, var12, var14);
            var10.addVertexWithUV(8.0, -2.0, 0.0, var13, var14);
            var10.addVertexWithUV(8.0, 2.0, 0.0, var13, var15);
            var10.addVertexWithUV(-8.0, 2.0, 0.0, var12, var15);
            var10.draw();
            ++var23;
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityArrow par1EntityArrow) {
        return arrowTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityArrow)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityArrow)par1Entity, par2, par4, par6, par8, par9);
    }
}

