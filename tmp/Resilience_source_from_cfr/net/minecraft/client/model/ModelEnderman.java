/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEnderman
extends ModelBiped {
    public boolean isCarrying;
    public boolean isAttacking;
    private static final String __OBFID = "CL_00000838";

    public ModelEnderman() {
        super(0.0f, -14.0f, 64, 32);
        float var1 = -14.0f;
        float var2 = 0.0f;
        this.bipedHeadwear = new ModelRenderer(this, 0, 16);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, var2 - 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + var1, 0.0f);
        this.bipedBody = new ModelRenderer(this, 32, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, var2);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + var1, 0.0f);
        this.bipedRightArm = new ModelRenderer(this, 56, 0);
        this.bipedRightArm.addBox(-1.0f, -2.0f, -1.0f, 2, 30, 2, var2);
        this.bipedRightArm.setRotationPoint(-3.0f, 2.0f + var1, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 56, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 30, 2, var2);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + var1, 0.0f);
        this.bipedRightLeg = new ModelRenderer(this, 56, 0);
        this.bipedRightLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, var2);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f + var1, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, var2);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f + var1, 0.0f);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.bipedHead.showModel = true;
        float var8 = -14.0f;
        this.bipedBody.rotateAngleX = 0.0f;
        this.bipedBody.rotationPointY = var8;
        this.bipedBody.rotationPointZ = -0.0f;
        this.bipedRightLeg.rotateAngleX -= 0.0f;
        this.bipedLeftLeg.rotateAngleX -= 0.0f;
        this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX * 0.5);
        this.bipedLeftArm.rotateAngleX = (float)((double)this.bipedLeftArm.rotateAngleX * 0.5);
        this.bipedRightLeg.rotateAngleX = (float)((double)this.bipedRightLeg.rotateAngleX * 0.5);
        this.bipedLeftLeg.rotateAngleX = (float)((double)this.bipedLeftLeg.rotateAngleX * 0.5);
        float var9 = 0.4f;
        if (this.bipedRightArm.rotateAngleX > var9) {
            this.bipedRightArm.rotateAngleX = var9;
        }
        if (this.bipedLeftArm.rotateAngleX > var9) {
            this.bipedLeftArm.rotateAngleX = var9;
        }
        if (this.bipedRightArm.rotateAngleX < - var9) {
            this.bipedRightArm.rotateAngleX = - var9;
        }
        if (this.bipedLeftArm.rotateAngleX < - var9) {
            this.bipedLeftArm.rotateAngleX = - var9;
        }
        if (this.bipedRightLeg.rotateAngleX > var9) {
            this.bipedRightLeg.rotateAngleX = var9;
        }
        if (this.bipedLeftLeg.rotateAngleX > var9) {
            this.bipedLeftLeg.rotateAngleX = var9;
        }
        if (this.bipedRightLeg.rotateAngleX < - var9) {
            this.bipedRightLeg.rotateAngleX = - var9;
        }
        if (this.bipedLeftLeg.rotateAngleX < - var9) {
            this.bipedLeftLeg.rotateAngleX = - var9;
        }
        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5f;
            this.bipedLeftArm.rotateAngleX = -0.5f;
            this.bipedRightArm.rotateAngleZ = 0.05f;
            this.bipedLeftArm.rotateAngleZ = -0.05f;
        }
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointZ = 0.0f;
        this.bipedLeftLeg.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointY = 9.0f + var8;
        this.bipedLeftLeg.rotationPointY = 9.0f + var8;
        this.bipedHead.rotationPointZ = -0.0f;
        this.bipedHead.rotationPointY = var8 + 1.0f;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            float var10 = 1.0f;
            this.bipedHead.rotationPointY -= var10 * 5.0f;
        }
    }
}

