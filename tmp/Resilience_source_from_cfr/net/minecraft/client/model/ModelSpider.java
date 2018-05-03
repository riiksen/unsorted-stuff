/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpider
extends ModelBase {
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;
    private static final String __OBFID = "CL_00000860";

    public ModelSpider() {
        float var1 = 0.0f;
        int var2 = 15;
        this.spiderHead = new ModelRenderer(this, 32, 4);
        this.spiderHead.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, var1);
        this.spiderHead.setRotationPoint(0.0f, var2, -3.0f);
        this.spiderNeck = new ModelRenderer(this, 0, 0);
        this.spiderNeck.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, var1);
        this.spiderNeck.setRotationPoint(0.0f, var2, 0.0f);
        this.spiderBody = new ModelRenderer(this, 0, 12);
        this.spiderBody.addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, var1);
        this.spiderBody.setRotationPoint(0.0f, var2, 9.0f);
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.spiderLeg1.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg1.setRotationPoint(-4.0f, var2, 2.0f);
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.spiderLeg2.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg2.setRotationPoint(4.0f, var2, 2.0f);
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.spiderLeg3.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg3.setRotationPoint(-4.0f, var2, 1.0f);
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.spiderLeg4.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg4.setRotationPoint(4.0f, var2, 1.0f);
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.spiderLeg5.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg5.setRotationPoint(-4.0f, var2, 0.0f);
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.spiderLeg6.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg6.setRotationPoint(4.0f, var2, 0.0f);
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.spiderLeg7.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg7.setRotationPoint(-4.0f, var2, -1.0f);
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.spiderLeg8.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg8.setRotationPoint(4.0f, var2, -1.0f);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.spiderHead.render(par7);
        this.spiderNeck.render(par7);
        this.spiderBody.render(par7);
        this.spiderLeg1.render(par7);
        this.spiderLeg2.render(par7);
        this.spiderLeg3.render(par7);
        this.spiderLeg4.render(par7);
        this.spiderLeg5.render(par7);
        this.spiderLeg6.render(par7);
        this.spiderLeg7.render(par7);
        this.spiderLeg8.render(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.spiderHead.rotateAngleY = par4 / 57.295776f;
        this.spiderHead.rotateAngleX = par5 / 57.295776f;
        float var8 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = - var8;
        this.spiderLeg2.rotateAngleZ = var8;
        this.spiderLeg3.rotateAngleZ = (- var8) * 0.74f;
        this.spiderLeg4.rotateAngleZ = var8 * 0.74f;
        this.spiderLeg5.rotateAngleZ = (- var8) * 0.74f;
        this.spiderLeg6.rotateAngleZ = var8 * 0.74f;
        this.spiderLeg7.rotateAngleZ = - var8;
        this.spiderLeg8.rotateAngleZ = var8;
        float var9 = -0.0f;
        float var10 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = var10 * 2.0f + var9;
        this.spiderLeg2.rotateAngleY = (- var10) * 2.0f - var9;
        this.spiderLeg3.rotateAngleY = var10 * 1.0f + var9;
        this.spiderLeg4.rotateAngleY = (- var10) * 1.0f - var9;
        this.spiderLeg5.rotateAngleY = (- var10) * 1.0f + var9;
        this.spiderLeg6.rotateAngleY = var10 * 1.0f - var9;
        this.spiderLeg7.rotateAngleY = (- var10) * 2.0f + var9;
        this.spiderLeg8.rotateAngleY = var10 * 2.0f - var9;
        float var11 = (- MathHelper.cos(par1 * 0.6662f * 2.0f + 0.0f) * 0.4f) * par2;
        float var12 = (- MathHelper.cos(par1 * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * par2;
        float var13 = (- MathHelper.cos(par1 * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * par2;
        float var14 = (- MathHelper.cos(par1 * 0.6662f * 2.0f + 4.712389f) * 0.4f) * par2;
        float var15 = Math.abs(MathHelper.sin(par1 * 0.6662f + 0.0f) * 0.4f) * par2;
        float var16 = Math.abs(MathHelper.sin(par1 * 0.6662f + 3.1415927f) * 0.4f) * par2;
        float var17 = Math.abs(MathHelper.sin(par1 * 0.6662f + 1.5707964f) * 0.4f) * par2;
        float var18 = Math.abs(MathHelper.sin(par1 * 0.6662f + 4.712389f) * 0.4f) * par2;
        this.spiderLeg1.rotateAngleY += var11;
        this.spiderLeg2.rotateAngleY += - var11;
        this.spiderLeg3.rotateAngleY += var12;
        this.spiderLeg4.rotateAngleY += - var12;
        this.spiderLeg5.rotateAngleY += var13;
        this.spiderLeg6.rotateAngleY += - var13;
        this.spiderLeg7.rotateAngleY += var14;
        this.spiderLeg8.rotateAngleY += - var14;
        this.spiderLeg1.rotateAngleZ += var15;
        this.spiderLeg2.rotateAngleZ += - var15;
        this.spiderLeg3.rotateAngleZ += var16;
        this.spiderLeg4.rotateAngleZ += - var16;
        this.spiderLeg5.rotateAngleZ += var17;
        this.spiderLeg6.rotateAngleZ += - var17;
        this.spiderLeg7.rotateAngleZ += var18;
        this.spiderLeg8.rotateAngleZ += - var18;
    }
}

