/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderIronGolem
extends RenderLiving {
    private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
    private final ModelIronGolem ironGolemModel;
    private static final String __OBFID = "CL_00001031";

    public RenderIronGolem() {
        super(new ModelIronGolem(), 0.5f);
        this.ironGolemModel = (ModelIronGolem)this.mainModel;
    }

    public void doRender(EntityIronGolem par1EntityIronGolem, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityIronGolem, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityIronGolem par1EntityIronGolem) {
        return ironGolemTextures;
    }

    protected void rotateCorpse(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4) {
        super.rotateCorpse(par1EntityIronGolem, par2, par3, par4);
        if ((double)par1EntityIronGolem.limbSwingAmount >= 0.01) {
            float var5 = 13.0f;
            float var6 = par1EntityIronGolem.limbSwing - par1EntityIronGolem.limbSwingAmount * (1.0f - par4) + 6.0f;
            float var7 = (Math.abs(var6 % var5 - var5 * 0.5f) - var5 * 0.25f) / (var5 * 0.25f);
            GL11.glRotatef((float)(6.5f * var7), (float)0.0f, (float)0.0f, (float)1.0f);
        }
    }

    protected void renderEquippedItems(EntityIronGolem par1EntityIronGolem, float par2) {
        super.renderEquippedItems(par1EntityIronGolem, par2);
        if (par1EntityIronGolem.getHoldRoseTick() != 0) {
            GL11.glEnable((int)32826);
            GL11.glPushMatrix();
            GL11.glRotatef((float)(5.0f + 180.0f * this.ironGolemModel.ironGolemRightArm.rotateAngleX / 3.1415927f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)-0.6875f, (float)1.25f, (float)-0.9375f);
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            float var3 = 0.8f;
            GL11.glScalef((float)var3, (float)(- var3), (float)var3);
            int var4 = par1EntityIronGolem.getBrightnessForRender(par2);
            int var5 = var4 % 65536;
            int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0f, (float)var6 / 1.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.field_147909_c.renderBlockAsItem(Blocks.red_flower, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable((int)32826);
        }
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityIronGolem)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityIronGolem)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.rotateCorpse((EntityIronGolem)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityIronGolem)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityIronGolem)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityIronGolem)par1Entity, par2, par4, par6, par8, par9);
    }
}

