/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderChicken
extends RenderLiving {
    private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    private static final String __OBFID = "CL_00000983";

    public RenderChicken(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    public void doRender(EntityChicken par1EntityChicken, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityChicken, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityChicken par1EntityChicken) {
        return chickenTextures;
    }

    protected float handleRotationFloat(EntityChicken par1EntityChicken, float par2) {
        float var3 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
        float var4 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
        return (MathHelper.sin(var3) + 1.0f) * var4;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityChicken)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
        return this.handleRotationFloat((EntityChicken)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityChicken)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
    }
}

