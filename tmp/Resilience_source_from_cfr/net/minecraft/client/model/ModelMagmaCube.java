/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube
extends ModelBase {
    ModelRenderer[] field_78109_a = new ModelRenderer[8];
    ModelRenderer field_78108_b;
    private static final String __OBFID = "CL_00000842";

    public ModelMagmaCube() {
        int var1 = 0;
        while (var1 < this.field_78109_a.length) {
            int var2 = 0;
            int var3 = var1;
            if (var1 == 2) {
                var2 = 24;
                var3 = 10;
            } else if (var1 == 3) {
                var2 = 24;
                var3 = 19;
            }
            this.field_78109_a[var1] = new ModelRenderer(this, var2, var3);
            this.field_78109_a[var1].addBox(-4.0f, 16 + var1, -4.0f, 8, 1, 8);
            ++var1;
        }
        this.field_78108_b = new ModelRenderer(this, 0, 16);
        this.field_78108_b.addBox(-2.0f, 18.0f, -2.0f, 4, 4, 4);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        EntityMagmaCube var5 = (EntityMagmaCube)par1EntityLivingBase;
        float var6 = var5.prevSquishFactor + (var5.squishFactor - var5.prevSquishFactor) * par4;
        if (var6 < 0.0f) {
            var6 = 0.0f;
        }
        int var7 = 0;
        while (var7 < this.field_78109_a.length) {
            this.field_78109_a[var7].rotationPointY = (float)(- 4 - var7) * var6 * 1.7f;
            ++var7;
        }
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.field_78108_b.render(par7);
        int var8 = 0;
        while (var8 < this.field_78109_a.length) {
            this.field_78109_a[var8].render(par7);
            ++var8;
        }
    }
}

