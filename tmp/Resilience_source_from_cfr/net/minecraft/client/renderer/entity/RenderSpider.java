/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSpider
extends RenderLiving {
    private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
    private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
    private static final String __OBFID = "CL_00001027";

    public RenderSpider() {
        super(new ModelSpider(), 1.0f);
        this.setRenderPassModel(new ModelSpider());
    }

    protected float getDeathMaxRotation(EntitySpider par1EntitySpider) {
        return 180.0f;
    }

    protected int shouldRenderPass(EntitySpider par1EntitySpider, int par2, float par3) {
        if (par2 != 0) {
            return -1;
        }
        this.bindTexture(spiderEyesTextures);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)1, (int)1);
        if (par1EntitySpider.isInvisible()) {
            GL11.glDepthMask((boolean)false);
        } else {
            GL11.glDepthMask((boolean)true);
        }
        int var4 = 61680;
        int var5 = var4 % 65536;
        int var6 = var4 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0f, (float)var6 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        return 1;
    }

    protected ResourceLocation getEntityTexture(EntitySpider par1EntitySpider) {
        return spiderTextures;
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
        return this.getDeathMaxRotation((EntitySpider)par1EntityLivingBase);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntitySpider)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySpider)par1Entity);
    }
}

