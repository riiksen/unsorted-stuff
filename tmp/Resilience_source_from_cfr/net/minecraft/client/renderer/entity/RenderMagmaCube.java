/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderMagmaCube
extends RenderLiving {
    private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
    private static final String __OBFID = "CL_00001009";

    public RenderMagmaCube() {
        super(new ModelMagmaCube(), 0.25f);
    }

    protected ResourceLocation getEntityTexture(EntityMagmaCube par1EntityMagmaCube) {
        return magmaCubeTextures;
    }

    protected void preRenderCallback(EntityMagmaCube par1EntityMagmaCube, float par2) {
        int var3 = par1EntityMagmaCube.getSlimeSize();
        float var4 = (par1EntityMagmaCube.prevSquishFactor + (par1EntityMagmaCube.squishFactor - par1EntityMagmaCube.prevSquishFactor) * par2) / ((float)var3 * 0.5f + 1.0f);
        float var5 = 1.0f / (var4 + 1.0f);
        float var6 = var3;
        GL11.glScalef((float)(var5 * var6), (float)(1.0f / var5 * var6), (float)(var5 * var6));
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityMagmaCube)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityMagmaCube)par1Entity);
    }
}

