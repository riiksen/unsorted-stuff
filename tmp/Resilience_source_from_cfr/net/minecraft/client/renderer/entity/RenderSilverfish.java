/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish
extends RenderLiving {
    private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
    private static final String __OBFID = "CL_00001022";

    public RenderSilverfish() {
        super(new ModelSilverfish(), 0.3f);
    }

    protected float getDeathMaxRotation(EntitySilverfish par1EntitySilverfish) {
        return 180.0f;
    }

    public void doRender(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntitySilverfish, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntitySilverfish par1EntitySilverfish) {
        return silverfishTextures;
    }

    protected int shouldRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3) {
        return -1;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
        return this.getDeathMaxRotation((EntitySilverfish)par1EntityLivingBase);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntitySilverfish)par1EntityLivingBase, par2, par3);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySilverfish)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
    }
}

