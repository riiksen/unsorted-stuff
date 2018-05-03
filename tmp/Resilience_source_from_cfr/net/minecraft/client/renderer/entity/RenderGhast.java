/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGhast
extends RenderLiving {
    private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
    private static final ResourceLocation ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
    private static final String __OBFID = "CL_00000997";

    public RenderGhast() {
        super(new ModelGhast(), 0.5f);
    }

    protected ResourceLocation getEntityTexture(EntityGhast par1EntityGhast) {
        return par1EntityGhast.func_110182_bF() ? ghastShootingTextures : ghastTextures;
    }

    protected void preRenderCallback(EntityGhast par1EntityGhast, float par2) {
        float var4 = ((float)par1EntityGhast.prevAttackCounter + (float)(par1EntityGhast.attackCounter - par1EntityGhast.prevAttackCounter) * par2) / 20.0f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        var4 = 1.0f / (var4 * var4 * var4 * var4 * var4 * 2.0f + 1.0f);
        float var5 = (8.0f + var4) / 2.0f;
        float var6 = (8.0f + 1.0f / var4) / 2.0f;
        GL11.glScalef((float)var6, (float)var5, (float)var6);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityGhast)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityGhast)par1Entity);
    }
}

