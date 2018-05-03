/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig
extends RenderLiving {
    private static final ResourceLocation saddledPigTextures = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
    private static final String __OBFID = "CL_00001019";

    public RenderPig(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }

    protected int shouldRenderPass(EntityPig par1EntityPig, int par2, float par3) {
        if (par2 == 0 && par1EntityPig.getSaddled()) {
            this.bindTexture(saddledPigTextures);
            return 1;
        }
        return -1;
    }

    protected ResourceLocation getEntityTexture(EntityPig par1EntityPig) {
        return pigTextures;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityPig)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityPig)par1Entity);
    }
}

