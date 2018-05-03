/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderVillager
extends RenderLiving {
    private static final ResourceLocation villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
    private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
    private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
    private static final ResourceLocation priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
    private static final ResourceLocation smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
    private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");
    protected ModelVillager villagerModel;
    private static final String __OBFID = "CL_00001032";

    public RenderVillager() {
        super(new ModelVillager(0.0f), 0.5f);
        this.villagerModel = (ModelVillager)this.mainModel;
    }

    protected int shouldRenderPass(EntityVillager par1EntityVillager, int par2, float par3) {
        return -1;
    }

    public void doRender(EntityVillager par1EntityVillager, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntityVillager, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityVillager par1EntityVillager) {
        switch (par1EntityVillager.getProfession()) {
            case 0: {
                return farmerVillagerTextures;
            }
            case 1: {
                return librarianVillagerTextures;
            }
            case 2: {
                return priestVillagerTextures;
            }
            case 3: {
                return smithVillagerTextures;
            }
            case 4: {
                return butcherVillagerTextures;
            }
        }
        return villagerTextures;
    }

    protected void renderEquippedItems(EntityVillager par1EntityVillager, float par2) {
        super.renderEquippedItems(par1EntityVillager, par2);
    }

    protected void preRenderCallback(EntityVillager par1EntityVillager, float par2) {
        float var3 = 0.9375f;
        if (par1EntityVillager.getGrowingAge() < 0) {
            var3 = (float)((double)var3 * 0.5);
            this.shadowSize = 0.25f;
        } else {
            this.shadowSize = 0.5f;
        }
        GL11.glScalef((float)var3, (float)var3, (float)var3);
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityVillager)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityVillager)par1EntityLivingBase, par2);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityVillager)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityVillager)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityVillager)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
    }
}

