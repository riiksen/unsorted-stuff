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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderXPOrb
extends Render {
    private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
    private static final String __OBFID = "CL_00000993";

    public RenderXPOrb() {
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }

    public void doRender(EntityXPOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        this.bindEntityTexture(par1EntityXPOrb);
        int var10 = par1EntityXPOrb.getTextureByXP();
        float var11 = (float)(var10 % 4 * 16 + 0) / 64.0f;
        float var12 = (float)(var10 % 4 * 16 + 16) / 64.0f;
        float var13 = (float)(var10 / 4 * 16 + 0) / 64.0f;
        float var14 = (float)(var10 / 4 * 16 + 16) / 64.0f;
        float var15 = 1.0f;
        float var16 = 0.5f;
        float var17 = 0.25f;
        int var18 = par1EntityXPOrb.getBrightnessForRender(par9);
        int var19 = var18 % 65536;
        int var20 = var18 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var19 / 1.0f, (float)var20 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var27 = 255.0f;
        float var26 = ((float)par1EntityXPOrb.xpColor + par9) / 2.0f;
        var20 = (int)((MathHelper.sin(var26 + 0.0f) + 1.0f) * 0.5f * var27);
        int var21 = (int)var27;
        int var22 = (int)((MathHelper.sin(var26 + 4.1887903f) + 1.0f) * 0.1f * var27);
        int var23 = var20 << 16 | var21 << 8 | var22;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        float var24 = 0.3f;
        GL11.glScalef((float)var24, (float)var24, (float)var24);
        Tessellator var25 = Tessellator.instance;
        var25.startDrawingQuads();
        var25.setColorRGBA_I(var23, 128);
        var25.setNormal(0.0f, 1.0f, 0.0f);
        var25.addVertexWithUV(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
        var25.addVertexWithUV(var15 - var16, 0.0f - var17, 0.0, var12, var14);
        var25.addVertexWithUV(var15 - var16, 1.0f - var17, 0.0, var12, var13);
        var25.addVertexWithUV(0.0f - var16, 1.0f - var17, 0.0, var11, var13);
        var25.draw();
        GL11.glDisable((int)3042);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityXPOrb par1EntityXPOrb) {
        return experienceOrbTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityXPOrb)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityXPOrb)par1Entity, par2, par4, par6, par8, par9);
    }
}

