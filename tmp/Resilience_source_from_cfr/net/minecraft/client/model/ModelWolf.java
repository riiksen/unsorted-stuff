/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelWolf
extends ModelBase {
    public ModelRenderer wolfHeadMain;
    public ModelRenderer wolfBody;
    public ModelRenderer wolfLeg1;
    public ModelRenderer wolfLeg2;
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    ModelRenderer wolfTail;
    ModelRenderer wolfMane;
    private static final String __OBFID = "CL_00000868";

    public ModelWolf() {
        float var1 = 0.0f;
        float var2 = 13.5f;
        this.wolfHeadMain = new ModelRenderer(this, 0, 0);
        this.wolfHeadMain.addBox(-3.0f, -3.0f, -2.0f, 6, 6, 4, var1);
        this.wolfHeadMain.setRotationPoint(-1.0f, var2, -7.0f);
        this.wolfBody = new ModelRenderer(this, 18, 14);
        this.wolfBody.addBox(-4.0f, -2.0f, -3.0f, 6, 9, 6, var1);
        this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
        this.wolfMane = new ModelRenderer(this, 21, 0);
        this.wolfMane.addBox(-4.0f, -3.0f, -3.0f, 8, 6, 7, var1);
        this.wolfMane.setRotationPoint(-1.0f, 14.0f, 2.0f);
        this.wolfLeg1 = new ModelRenderer(this, 0, 18);
        this.wolfLeg1.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
        this.wolfLeg2 = new ModelRenderer(this, 0, 18);
        this.wolfLeg2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
        this.wolfLeg3 = new ModelRenderer(this, 0, 18);
        this.wolfLeg3.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
        this.wolfLeg4 = new ModelRenderer(this, 0, 18);
        this.wolfLeg4.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
        this.wolfTail = new ModelRenderer(this, 9, 18);
        this.wolfTail.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5f, 0.0f, -5.0f, 3, 3, 4, var1);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)(5.0f * par7), (float)(2.0f * par7));
            this.wolfHeadMain.renderWithRotation(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)(1.0f / var8), (float)(1.0f / var8), (float)(1.0f / var8));
            GL11.glTranslatef((float)0.0f, (float)(24.0f * par7), (float)0.0f);
            this.wolfBody.render(par7);
            this.wolfLeg1.render(par7);
            this.wolfLeg2.render(par7);
            this.wolfLeg3.render(par7);
            this.wolfLeg4.render(par7);
            this.wolfTail.renderWithRotation(par7);
            this.wolfMane.render(par7);
            GL11.glPopMatrix();
        } else {
            this.wolfHeadMain.renderWithRotation(par7);
            this.wolfBody.render(par7);
            this.wolfLeg1.render(par7);
            this.wolfLeg2.render(par7);
            this.wolfLeg3.render(par7);
            this.wolfLeg4.render(par7);
            this.wolfTail.renderWithRotation(par7);
            this.wolfMane.render(par7);
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        EntityWolf var5 = (EntityWolf)par1EntityLivingBase;
        this.wolfTail.rotateAngleY = var5.isAngry() ? 0.0f : MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
        if (var5.isSitting()) {
            this.wolfMane.setRotationPoint(-1.0f, 16.0f, -3.0f);
            this.wolfMane.rotateAngleX = 1.2566371f;
            this.wolfMane.rotateAngleY = 0.0f;
            this.wolfBody.setRotationPoint(0.0f, 18.0f, 0.0f);
            this.wolfBody.rotateAngleX = 0.7853982f;
            this.wolfTail.setRotationPoint(-1.0f, 21.0f, 6.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 22.0f, 2.0f);
            this.wolfLeg1.rotateAngleX = 4.712389f;
            this.wolfLeg2.setRotationPoint(0.5f, 22.0f, 2.0f);
            this.wolfLeg2.rotateAngleX = 4.712389f;
            this.wolfLeg3.rotateAngleX = 5.811947f;
            this.wolfLeg3.setRotationPoint(-2.49f, 17.0f, -4.0f);
            this.wolfLeg4.rotateAngleX = 5.811947f;
            this.wolfLeg4.setRotationPoint(0.51f, 17.0f, -4.0f);
        } else {
            this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
            this.wolfBody.rotateAngleX = 1.5707964f;
            this.wolfMane.setRotationPoint(-1.0f, 14.0f, -3.0f);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
            this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
            this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
            this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662f + 3.1415927f) * 1.4f * par3;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662f + 3.1415927f) * 1.4f * par3;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
        }
        this.wolfHeadMain.rotateAngleZ = var5.getInterestedAngle(par4) + var5.getShakeAngle(par4, 0.0f);
        this.wolfMane.rotateAngleZ = var5.getShakeAngle(par4, -0.08f);
        this.wolfBody.rotateAngleZ = var5.getShakeAngle(par4, -0.16f);
        this.wolfTail.rotateAngleZ = var5.getShakeAngle(par4, -0.2f);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.wolfHeadMain.rotateAngleX = par5 / 57.295776f;
        this.wolfHeadMain.rotateAngleY = par4 / 57.295776f;
        this.wolfTail.rotateAngleX = par3;
    }
}

