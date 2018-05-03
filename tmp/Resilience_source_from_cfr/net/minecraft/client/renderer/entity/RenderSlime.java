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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSlime
extends RenderLiving {
    private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
    private ModelBase scaleAmount;
    private static final String __OBFID = "CL_00001024";

    public RenderSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
    }

    protected int shouldRenderPass(EntitySlime par1EntitySlime, int par2, float par3) {
        if (par1EntitySlime.isInvisible()) {
            return 0;
        }
        if (par2 == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable((int)2977);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            return 1;
        }
        if (par2 == 1) {
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        return -1;
    }

    protected void preRenderCallback(EntitySlime par1EntitySlime, float par2) {
        float var3 = par1EntitySlime.getSlimeSize();
        float var4 = (par1EntitySlime.prevSquishFactor + (par1EntitySlime.squishFactor - par1EntitySlime.prevSquishFactor) * par2) / (var3 * 0.5f + 1.0f);
        float var5 = 1.0f / (var4 + 1.0f);
        GL11.glScalef((float)(var5 * var3), (float)(1.0f / var5 * var3), (float)(var5 * var3));
    }

    protected ResourceLocation getEntityTexture(EntitySlime par1EntitySlime) {
        return slimeTextures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntitySlime)par1EntityLivingBase, par2);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntitySlime)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySlime)par1Entity);
    }
}

