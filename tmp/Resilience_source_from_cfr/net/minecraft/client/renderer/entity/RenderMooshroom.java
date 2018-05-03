/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderMooshroom
extends RenderLiving {
    private static final ResourceLocation mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
    private static final String __OBFID = "CL_00001016";

    public RenderMooshroom(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    public void doRender(EntityMooshroom par1EntityMooshroom, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityMooshroom, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityMooshroom par1EntityMooshroom) {
        return mooshroomTextures;
    }

    protected void renderEquippedItems(EntityMooshroom par1EntityMooshroom, float par2) {
        super.renderEquippedItems(par1EntityMooshroom, par2);
        if (!par1EntityMooshroom.isChild()) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glEnable((int)2884);
            GL11.glPushMatrix();
            GL11.glScalef((float)1.0f, (float)-1.0f, (float)1.0f);
            GL11.glTranslatef((float)0.2f, (float)0.4f, (float)0.5f);
            GL11.glRotatef((float)42.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glTranslatef((float)0.1f, (float)0.0f, (float)-0.6f);
            GL11.glRotatef((float)42.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            ((ModelQuadruped)this.mainModel).head.postRender(0.0625f);
            GL11.glScalef((float)1.0f, (float)-1.0f, (float)1.0f);
            GL11.glTranslatef((float)0.0f, (float)0.75f, (float)-0.2f);
            GL11.glRotatef((float)12.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable((int)2884);
        }
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityMooshroom)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityMooshroom)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityMooshroom)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityMooshroom)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityMooshroom)par1Entity, par2, par4, par6, par8, par9);
    }
}

