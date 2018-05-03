/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSnowMan
extends RenderLiving {
    private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");
    private ModelSnowMan snowmanModel;
    private static final String __OBFID = "CL_00001025";

    public RenderSnowMan() {
        super(new ModelSnowMan(), 0.5f);
        this.snowmanModel = (ModelSnowMan)this.mainModel;
        this.setRenderPassModel(this.snowmanModel);
    }

    protected void renderEquippedItems(EntitySnowman par1EntitySnowman, float par2) {
        super.renderEquippedItems(par1EntitySnowman, par2);
        ItemStack var3 = new ItemStack(Blocks.pumpkin, 1);
        if (var3.getItem() instanceof ItemBlock) {
            GL11.glPushMatrix();
            this.snowmanModel.head.postRender(0.0625f);
            if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                float var4 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)-0.34375f, (float)0.0f);
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var4, (float)(- var4), (float)var4);
            }
            this.renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(EntitySnowman par1EntitySnowman) {
        return snowManTextures;
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntitySnowman)par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySnowman)par1Entity);
    }
}

