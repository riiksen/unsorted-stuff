/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCaveSpider
extends RenderSpider {
    private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
    private static final String __OBFID = "CL_00000982";

    public RenderCaveSpider() {
        this.shadowSize *= 0.7f;
    }

    protected void preRenderCallback(EntityCaveSpider par1EntityCaveSpider, float par2) {
        GL11.glScalef((float)0.7f, (float)0.7f, (float)0.7f);
    }

    protected ResourceLocation getEntityTexture(EntityCaveSpider par1EntityCaveSpider) {
        return caveSpiderTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySpider par1EntitySpider) {
        return this.getEntityTexture((EntityCaveSpider)par1EntitySpider);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityCaveSpider)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityCaveSpider)par1Entity);
    }
}

