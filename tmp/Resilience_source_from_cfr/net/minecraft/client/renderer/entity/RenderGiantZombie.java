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
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGiantZombie
extends RenderLiving {
    private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    private float scale;
    private static final String __OBFID = "CL_00000998";

    public RenderGiantZombie(ModelBase par1ModelBase, float par2, float par3) {
        super(par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    protected void preRenderCallback(EntityGiantZombie par1EntityGiantZombie, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected ResourceLocation getEntityTexture(EntityGiantZombie par1EntityGiantZombie) {
        return zombieTextures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityGiantZombie)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityGiantZombie)par1Entity);
    }
}

