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
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelOcelot
extends ModelBase {
    ModelRenderer ocelotBackLeftLeg;
    ModelRenderer ocelotBackRightLeg;
    ModelRenderer ocelotFrontLeftLeg;
    ModelRenderer ocelotFrontRightLeg;
    ModelRenderer ocelotTail;
    ModelRenderer ocelotTail2;
    ModelRenderer ocelotHead;
    ModelRenderer ocelotBody;
    int field_78163_i = 1;
    private static final String __OBFID = "CL_00000848";

    public ModelOcelot() {
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        this.ocelotHead = new ModelRenderer(this, "head");
        this.ocelotHead.addBox("main", -2.5f, -2.0f, -3.0f, 5, 4, 5);
        this.ocelotHead.addBox("nose", -1.5f, 0.0f, -4.0f, 3, 2, 2);
        this.ocelotHead.addBox("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.addBox("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.setRotationPoint(0.0f, 15.0f, -9.0f);
        this.ocelotBody = new ModelRenderer(this, 20, 0);
        this.ocelotBody.addBox(-2.0f, 3.0f, -8.0f, 4, 16, 6, 0.0f);
        this.ocelotBody.setRotationPoint(0.0f, 12.0f, -10.0f);
        this.ocelotTail = new ModelRenderer(this, 0, 15);
        this.ocelotTail.addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail.rotateAngleX = 0.9f;
        this.ocelotTail.setRotationPoint(0.0f, 15.0f, 8.0f);
        this.ocelotTail2 = new ModelRenderer(this, 4, 15);
        this.ocelotTail2.addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail2.setRotationPoint(0.0f, 20.0f, 14.0f);
        this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackLeftLeg.addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackLeftLeg.setRotationPoint(1.1f, 18.0f, 5.0f);
        this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackRightLeg.addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackRightLeg.setRotationPoint(-1.1f, 18.0f, 5.0f);
        this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontLeftLeg.addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontLeftLeg.setRotationPoint(1.2f, 13.8f, -5.0f);
        this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontRightLeg.addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontRightLeg.setRotationPoint(-1.2f, 13.8f, -5.0f);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef((float)(1.5f / var8), (float)(1.5f / var8), (float)(1.5f / var8));
            GL11.glTranslatef((float)0.0f, (float)(10.0f * par7), (float)(4.0f * par7));
            this.ocelotHead.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)(1.0f / var8), (float)(1.0f / var8), (float)(1.0f / var8));
            GL11.glTranslatef((float)0.0f, (float)(24.0f * par7), (float)0.0f);
            this.ocelotBody.render(par7);
            this.ocelotBackLeftLeg.render(par7);
            this.ocelotBackRightLeg.render(par7);
            this.ocelotFrontLeftLeg.render(par7);
            this.ocelotFrontRightLeg.render(par7);
            this.ocelotTail.render(par7);
            this.ocelotTail2.render(par7);
            GL11.glPopMatrix();
        } else {
            this.ocelotHead.render(par7);
            this.ocelotBody.render(par7);
            this.ocelotTail.render(par7);
            this.ocelotTail2.render(par7);
            this.ocelotBackLeftLeg.render(par7);
            this.ocelotBackRightLeg.render(par7);
            this.ocelotFrontLeftLeg.render(par7);
            this.ocelotFrontRightLeg.render(par7);
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.ocelotHead.rotateAngleX = par5 / 57.295776f;
        this.ocelotHead.rotateAngleY = par4 / 57.295776f;
        if (this.field_78163_i != 3) {
            this.ocelotBody.rotateAngleX = 1.5707964f;
            if (this.field_78163_i == 2) {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 0.3f) * 1.0f * par2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f + 0.3f) * 1.0f * par2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotTail2.rotateAngleX = 1.7278761f + 0.31415927f * MathHelper.cos(par1) * par2;
            } else {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                this.ocelotTail2.rotateAngleX = this.field_78163_i == 1 ? 1.7278761f + 0.7853982f * MathHelper.cos(par1) * par2 : 1.7278761f + 0.47123894f * MathHelper.cos(par1) * par2;
            }
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        EntityOcelot var5 = (EntityOcelot)par1EntityLivingBase;
        this.ocelotBody.rotationPointY = 12.0f;
        this.ocelotBody.rotationPointZ = -10.0f;
        this.ocelotHead.rotationPointY = 15.0f;
        this.ocelotHead.rotationPointZ = -9.0f;
        this.ocelotTail.rotationPointY = 15.0f;
        this.ocelotTail.rotationPointZ = 8.0f;
        this.ocelotTail2.rotationPointY = 20.0f;
        this.ocelotTail2.rotationPointZ = 14.0f;
        this.ocelotFrontRightLeg.rotationPointY = 13.8f;
        this.ocelotFrontLeftLeg.rotationPointY = 13.8f;
        this.ocelotFrontRightLeg.rotationPointZ = -5.0f;
        this.ocelotFrontLeftLeg.rotationPointZ = -5.0f;
        this.ocelotBackRightLeg.rotationPointY = 18.0f;
        this.ocelotBackLeftLeg.rotationPointY = 18.0f;
        this.ocelotBackRightLeg.rotationPointZ = 5.0f;
        this.ocelotBackLeftLeg.rotationPointZ = 5.0f;
        this.ocelotTail.rotateAngleX = 0.9f;
        if (var5.isSneaking()) {
            this.ocelotBody.rotationPointY += 1.0f;
            this.ocelotHead.rotationPointY += 2.0f;
            this.ocelotTail.rotationPointY += 1.0f;
            this.ocelotTail2.rotationPointY += -4.0f;
            this.ocelotTail2.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 0;
        } else if (var5.isSprinting()) {
            this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
            this.ocelotTail2.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 2;
        } else if (var5.isSitting()) {
            this.ocelotBody.rotateAngleX = 0.7853982f;
            this.ocelotBody.rotationPointY += -4.0f;
            this.ocelotBody.rotationPointZ += 5.0f;
            this.ocelotHead.rotationPointY += -3.3f;
            this.ocelotHead.rotationPointZ += 1.0f;
            this.ocelotTail.rotationPointY += 8.0f;
            this.ocelotTail.rotationPointZ += -2.0f;
            this.ocelotTail2.rotationPointY += 2.0f;
            this.ocelotTail2.rotationPointZ += -0.8f;
            this.ocelotTail.rotateAngleX = 1.7278761f;
            this.ocelotTail2.rotateAngleX = 2.670354f;
            this.ocelotFrontRightLeg.rotateAngleX = -0.15707964f;
            this.ocelotFrontLeftLeg.rotateAngleX = -0.15707964f;
            this.ocelotFrontRightLeg.rotationPointY = 15.8f;
            this.ocelotFrontLeftLeg.rotationPointY = 15.8f;
            this.ocelotFrontRightLeg.rotationPointZ = -7.0f;
            this.ocelotFrontLeftLeg.rotationPointZ = -7.0f;
            this.ocelotBackRightLeg.rotateAngleX = -1.5707964f;
            this.ocelotBackLeftLeg.rotateAngleX = -1.5707964f;
            this.ocelotBackRightLeg.rotationPointY = 21.0f;
            this.ocelotBackLeftLeg.rotationPointY = 21.0f;
            this.ocelotBackRightLeg.rotationPointZ = 1.0f;
            this.ocelotBackLeftLeg.rotationPointZ = 1.0f;
            this.field_78163_i = 3;
        } else {
            this.field_78163_i = 1;
        }
    }
}

