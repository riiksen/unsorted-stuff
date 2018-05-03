/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBook
extends ModelBase {
    public ModelRenderer coverRight;
    public ModelRenderer coverLeft;
    public ModelRenderer pagesRight;
    public ModelRenderer pagesLeft;
    public ModelRenderer flippingPageRight;
    public ModelRenderer flippingPageLeft;
    public ModelRenderer bookSpine;
    private static final String __OBFID = "CL_00000833";

    public ModelBook() {
        this.coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0f, -5.0f, 0.0f, 6, 10, 0);
        this.coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0f, -5.0f, 0.0f, 6, 10, 0);
        this.pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0f, -4.0f, -0.99f, 5, 8, 1);
        this.pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0f, -4.0f, -0.01f, 5, 8, 1);
        this.flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0f, -5.0f, 0.0f, 2, 10, 0);
        this.coverRight.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.coverLeft.setRotationPoint(0.0f, 0.0f, 1.0f);
        this.bookSpine.rotateAngleY = 1.5707964f;
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.coverRight.render(par7);
        this.coverLeft.render(par7);
        this.bookSpine.render(par7);
        this.pagesRight.render(par7);
        this.pagesLeft.render(par7);
        this.flippingPageRight.render(par7);
        this.flippingPageLeft.render(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        float var8 = (MathHelper.sin(par1 * 0.02f) * 0.1f + 1.25f) * par4;
        this.coverRight.rotateAngleY = 3.1415927f + var8;
        this.coverLeft.rotateAngleY = - var8;
        this.pagesRight.rotateAngleY = var8;
        this.pagesLeft.rotateAngleY = - var8;
        this.flippingPageRight.rotateAngleY = var8 - var8 * 2.0f * par2;
        this.flippingPageLeft.rotateAngleY = var8 - var8 * 2.0f * par3;
        this.pagesRight.rotationPointX = MathHelper.sin(var8);
        this.pagesLeft.rotationPointX = MathHelper.sin(var8);
        this.flippingPageRight.rotationPointX = MathHelper.sin(var8);
        this.flippingPageLeft.rotationPointX = MathHelper.sin(var8);
    }
}

