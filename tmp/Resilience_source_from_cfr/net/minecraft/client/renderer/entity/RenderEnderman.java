/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEnderman
extends RenderLiving {
    private static final ResourceLocation endermanEyesTexture = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    private ModelEnderman endermanModel;
    private Random rnd = new Random();
    private static final String __OBFID = "CL_00000989";

    public RenderEnderman() {
        super(new ModelEnderman(), 0.5f);
        this.endermanModel = (ModelEnderman)this.mainModel;
        this.setRenderPassModel(this.endermanModel);
    }

    public void doRender(EntityEnderman par1EntityEnderman, double par2, double par4, double par6, float par8, float par9) {
        this.endermanModel.isCarrying = par1EntityEnderman.func_146080_bZ().getMaterial() != Material.air;
        this.endermanModel.isAttacking = par1EntityEnderman.isScreaming();
        if (par1EntityEnderman.isScreaming()) {
            double var10 = 0.02;
            par2 += this.rnd.nextGaussian() * var10;
            par6 += this.rnd.nextGaussian() * var10;
        }
        super.doRender(par1EntityEnderman, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityEnderman par1EntityEnderman) {
        return endermanTextures;
    }

    protected void renderEquippedItems(EntityEnderman par1EntityEnderman, float par2) {
        super.renderEquippedItems(par1EntityEnderman, par2);
        if (par1EntityEnderman.func_146080_bZ().getMaterial() != Material.air) {
            GL11.glEnable((int)32826);
            GL11.glPushMatrix();
            float var3 = 0.5f;
            GL11.glTranslatef((float)0.0f, (float)0.6875f, (float)-0.75f);
            GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)(- var3), (float)(- var3), (float)(var3 *= 1.0f));
            int var4 = par1EntityEnderman.getBrightnessForRender(par2);
            int var5 = var4 % 65536;
            int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0f, (float)var6 / 1.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.field_147909_c.renderBlockAsItem(par1EntityEnderman.func_146080_bZ(), par1EntityEnderman.getCarryingData(), 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable((int)32826);
        }
    }

    protected int shouldRenderPass(EntityEnderman par1EntityEnderman, int par2, float par3) {
        if (par2 != 0) {
            return -1;
        }
        this.bindTexture(endermanEyesTexture);
        float var4 = 1.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)1, (int)1);
        GL11.glDisable((int)2896);
        if (par1EntityEnderman.isInvisible()) {
            GL11.glDepthMask((boolean)false);
        } else {
            GL11.glDepthMask((boolean)true);
        }
        int var5 = 61680;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0f, (float)var7 / 1.0f);
        GL11.glEnable((int)2896);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)var4);
        return 1;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityEnderman)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityEnderman)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityEnderman)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityEnderman)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
    }
}

