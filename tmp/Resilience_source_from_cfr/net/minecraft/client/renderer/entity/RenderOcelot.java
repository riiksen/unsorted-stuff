/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderOcelot
extends RenderLiving {
    private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
    private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
    private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
    private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
    private static final String __OBFID = "CL_00001017";

    public RenderOcelot(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    public void doRender(EntityOcelot par1EntityOcelot, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityOcelot, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityOcelot par1EntityOcelot) {
        switch (par1EntityOcelot.getTameSkin()) {
            default: {
                return ocelotTextures;
            }
            case 1: {
                return blackOcelotTextures;
            }
            case 2: {
                return redOcelotTextures;
            }
            case 3: 
        }
        return siameseOcelotTextures;
    }

    protected void preRenderCallback(EntityOcelot par1EntityOcelot, float par2) {
        super.preRenderCallback(par1EntityOcelot, par2);
        if (par1EntityOcelot.isTamed()) {
            GL11.glScalef((float)0.8f, (float)0.8f, (float)0.8f);
        }
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityOcelot)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityOcelot)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityOcelot)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
    }
}

