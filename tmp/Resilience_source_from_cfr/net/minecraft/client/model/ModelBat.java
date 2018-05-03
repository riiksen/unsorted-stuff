/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;

public class ModelBat
extends ModelBase {
    private ModelRenderer batHead;
    private ModelRenderer batBody;
    private ModelRenderer batRightWing;
    private ModelRenderer batLeftWing;
    private ModelRenderer batOuterRightWing;
    private ModelRenderer batOuterLeftWing;
    private static final String __OBFID = "CL_00000830";

    public ModelBat() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.batHead = new ModelRenderer(this, 0, 0);
        this.batHead.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6);
        ModelRenderer var1 = new ModelRenderer(this, 24, 0);
        var1.addBox(-4.0f, -6.0f, -2.0f, 3, 4, 1);
        this.batHead.addChild(var1);
        ModelRenderer var2 = new ModelRenderer(this, 24, 0);
        var2.mirror = true;
        var2.addBox(1.0f, -6.0f, -2.0f, 3, 4, 1);
        this.batHead.addChild(var2);
        this.batBody = new ModelRenderer(this, 0, 16);
        this.batBody.addBox(-3.0f, 4.0f, -3.0f, 6, 12, 6);
        this.batBody.setTextureOffset(0, 34).addBox(-5.0f, 16.0f, 0.0f, 10, 6, 1);
        this.batRightWing = new ModelRenderer(this, 42, 0);
        this.batRightWing.addBox(-12.0f, 1.0f, 1.5f, 10, 16, 1);
        this.batOuterRightWing = new ModelRenderer(this, 24, 16);
        this.batOuterRightWing.setRotationPoint(-12.0f, 1.0f, 1.5f);
        this.batOuterRightWing.addBox(-8.0f, 1.0f, 0.0f, 8, 12, 1);
        this.batLeftWing = new ModelRenderer(this, 42, 0);
        this.batLeftWing.mirror = true;
        this.batLeftWing.addBox(2.0f, 1.0f, 1.5f, 10, 16, 1);
        this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
        this.batOuterLeftWing.mirror = true;
        this.batOuterLeftWing.setRotationPoint(12.0f, 1.0f, 1.5f);
        this.batOuterLeftWing.addBox(0.0f, 1.0f, 0.0f, 8, 12, 1);
        this.batBody.addChild(this.batRightWing);
        this.batBody.addChild(this.batLeftWing);
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
    }

    public int getBatSize() {
        return 36;
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        EntityBat var8 = (EntityBat)par1Entity;
        if (var8.getIsBatHanging()) {
            float var9 = 57.295776f;
            this.batHead.rotateAngleX = par6 / 57.295776f;
            this.batHead.rotateAngleY = 3.1415927f - par5 / 57.295776f;
            this.batHead.rotateAngleZ = 3.1415927f;
            this.batHead.setRotationPoint(0.0f, -2.0f, 0.0f);
            this.batRightWing.setRotationPoint(-3.0f, 0.0f, 3.0f);
            this.batLeftWing.setRotationPoint(3.0f, 0.0f, 3.0f);
            this.batBody.rotateAngleX = 3.1415927f;
            this.batRightWing.rotateAngleX = -0.15707964f;
            this.batRightWing.rotateAngleY = -1.2566371f;
            this.batOuterRightWing.rotateAngleY = -1.7278761f;
            this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
            this.batLeftWing.rotateAngleY = - this.batRightWing.rotateAngleY;
            this.batOuterLeftWing.rotateAngleY = - this.batOuterRightWing.rotateAngleY;
        } else {
            float var9 = 57.295776f;
            this.batHead.rotateAngleX = par6 / 57.295776f;
            this.batHead.rotateAngleY = par5 / 57.295776f;
            this.batHead.rotateAngleZ = 0.0f;
            this.batHead.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batRightWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batLeftWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batBody.rotateAngleX = 0.7853982f + MathHelper.cos(par4 * 0.1f) * 0.15f;
            this.batBody.rotateAngleY = 0.0f;
            this.batRightWing.rotateAngleY = MathHelper.cos(par4 * 1.3f) * 3.1415927f * 0.25f;
            this.batLeftWing.rotateAngleY = - this.batRightWing.rotateAngleY;
            this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5f;
            this.batOuterLeftWing.rotateAngleY = (- this.batRightWing.rotateAngleY) * 0.5f;
        }
        this.batHead.render(par7);
        this.batBody.render(par7);
    }
}

