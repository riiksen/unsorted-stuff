/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSkeleton
extends RenderBiped {
    private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final String __OBFID = "CL_00001023";

    public RenderSkeleton() {
        super(new ModelSkeleton(), 0.5f);
    }

    protected void preRenderCallback(EntitySkeleton par1EntitySkeleton, float par2) {
        if (par1EntitySkeleton.getSkeletonType() == 1) {
            GL11.glScalef((float)1.2f, (float)1.2f, (float)1.2f);
        }
    }

    @Override
    protected void func_82422_c() {
        GL11.glTranslatef((float)0.09375f, (float)0.1875f, (float)0.0f);
    }

    protected ResourceLocation getEntityTexture(EntitySkeleton par1EntitySkeleton) {
        return par1EntitySkeleton.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) {
        return this.getEntityTexture((EntitySkeleton)par1EntityLiving);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntitySkeleton)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySkeleton)par1Entity);
    }
}

