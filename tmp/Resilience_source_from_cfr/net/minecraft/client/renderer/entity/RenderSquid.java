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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSquid
extends RenderLiving {
    private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");
    private static final String __OBFID = "CL_00001028";

    public RenderSquid(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    public void doRender(EntitySquid par1EntitySquid, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(par1EntitySquid, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntitySquid par1EntitySquid) {
        return squidTextures;
    }

    protected void rotateCorpse(EntitySquid par1EntitySquid, float par2, float par3, float par4) {
        float var5 = par1EntitySquid.prevSquidPitch + (par1EntitySquid.squidPitch - par1EntitySquid.prevSquidPitch) * par4;
        float var6 = par1EntitySquid.prevSquidYaw + (par1EntitySquid.squidYaw - par1EntitySquid.prevSquidYaw) * par4;
        GL11.glTranslatef((float)0.0f, (float)0.5f, (float)0.0f);
        GL11.glRotatef((float)(180.0f - par3), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)var5, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)var6, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)-1.2f, (float)0.0f);
    }

    protected float handleRotationFloat(EntitySquid par1EntitySquid, float par2) {
        return par1EntitySquid.lastTentacleAngle + (par1EntitySquid.tentacleAngle - par1EntitySquid.lastTentacleAngle) * par2;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySquid)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
        return this.handleRotationFloat((EntitySquid)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.rotateCorpse((EntitySquid)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySquid)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
    }
}

