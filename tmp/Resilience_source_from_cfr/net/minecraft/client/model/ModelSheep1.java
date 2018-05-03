/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep1
extends ModelQuadruped {
    private float field_78152_i;
    private static final String __OBFID = "CL_00000852";

    public ModelSheep1() {
        super(12, 0.0f);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0f, -4.0f, -4.0f, 6, 6, 6, 0.6f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 1.75f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        float var1 = 0.5f;
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg1.setRotationPoint(-3.0f, 12.0f, 7.0f);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg2.setRotationPoint(3.0f, 12.0f, 7.0f);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg3.setRotationPoint(-3.0f, 12.0f, -5.0f);
        this.leg4 = new ModelRenderer(this, 0, 16);
        this.leg4.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg4.setRotationPoint(3.0f, 12.0f, -5.0f);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
        this.head.rotationPointY = 6.0f + ((EntitySheep)par1EntityLivingBase).func_70894_j(par4) * 9.0f;
        this.field_78152_i = ((EntitySheep)par1EntityLivingBase).func_70890_k(par4);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.head.rotateAngleX = this.field_78152_i;
    }
}

