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
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWolf
extends RenderLiving {
    private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
    private static final ResourceLocation wolfCollarTextures = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    private static final String __OBFID = "CL_00001036";

    public RenderWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }

    protected float handleRotationFloat(EntityWolf par1EntityWolf, float par2) {
        return par1EntityWolf.getTailRotation();
    }

    protected int shouldRenderPass(EntityWolf par1EntityWolf, int par2, float par3) {
        if (par2 == 0 && par1EntityWolf.getWolfShaking()) {
            float var5 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
            this.bindTexture(wolfTextures);
            GL11.glColor3f((float)var5, (float)var5, (float)var5);
            return 1;
        }
        if (par2 == 1 && par1EntityWolf.isTamed()) {
            this.bindTexture(wolfCollarTextures);
            int var4 = par1EntityWolf.getCollarColor();
            GL11.glColor3f((float)EntitySheep.fleeceColorTable[var4][0], (float)EntitySheep.fleeceColorTable[var4][1], (float)EntitySheep.fleeceColorTable[var4][2]);
            return 1;
        }
        return -1;
    }

    protected ResourceLocation getEntityTexture(EntityWolf par1EntityWolf) {
        return par1EntityWolf.isTamed() ? tamedWolfTextures : (par1EntityWolf.isAngry() ? anrgyWolfTextures : wolfTextures);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityWolf)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
        return this.handleRotationFloat((EntityWolf)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityWolf)par1Entity);
    }
}

