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
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWitch
extends RenderLiving {
    private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");
    private final ModelWitch witchModel;
    private static final String __OBFID = "CL_00001033";

    public RenderWitch() {
        super(new ModelWitch(0.0f), 0.5f);
        this.witchModel = (ModelWitch)this.mainModel;
    }

    public void doRender(EntityWitch par1EntityWitch, double par2, double par4, double par6, float par8, float par9) {
        ItemStack var10 = par1EntityWitch.getHeldItem();
        this.witchModel.field_82900_g = var10 != null;
        super.doRender(par1EntityWitch, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityWitch par1EntityWitch) {
        return witchTextures;
    }

    protected void renderEquippedItems(EntityWitch par1EntityWitch, float par2) {
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        super.renderEquippedItems(par1EntityWitch, par2);
        ItemStack var3 = par1EntityWitch.getHeldItem();
        if (var3 != null) {
            float var4;
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                var4 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.625f, (float)0.0f);
                GL11.glRotatef((float)-20.0f, (float)-1.0f, (float)0.0f, (float)0.0f);
                GL11.glScalef((float)var4, (float)var4, (float)var4);
            }
            this.witchModel.villagerNose.postRender(0.0625f);
            GL11.glTranslatef((float)-0.0625f, (float)0.53125f, (float)0.21875f);
            if (var3.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                var4 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var4, (float)(- var4), (float)(var4 *= 0.75f));
            } else if (var3.getItem() == Items.bow) {
                var4 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var4, (float)(- var4), (float)var4);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (var3.getItem().isFull3D()) {
                var4 = 0.625f;
                if (var3.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                this.func_82410_b();
                GL11.glScalef((float)var4, (float)(- var4), (float)var4);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var4 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var4, (float)var4, (float)var4);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glRotatef((float)-15.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)40.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            this.renderManager.itemRenderer.renderItem(par1EntityWitch, var3, 0);
            if (var3.getItem().requiresMultipleRenderPasses()) {
                this.renderManager.itemRenderer.renderItem(par1EntityWitch, var3, 1);
            }
            GL11.glPopMatrix();
        }
    }

    protected void func_82410_b() {
        GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
    }

    protected void preRenderCallback(EntityWitch par1EntityWitch, float par2) {
        float var3 = 0.9375f;
        GL11.glScalef((float)var3, (float)var3, (float)var3);
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWitch)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityWitch)par1EntityLivingBase, par2);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityWitch)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWitch)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityWitch)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWitch)par1Entity, par2, par4, par6, par8, par9);
    }
}

