/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLeashKnot
extends ModelBase {
    public ModelRenderer field_110723_a;
    private static final String __OBFID = "CL_00000843";

    public ModelLeashKnot() {
        this(0, 0, 32, 32);
    }

    public ModelLeashKnot(int par1, int par2, int par3, int par4) {
        this.textureWidth = par3;
        this.textureHeight = par4;
        this.field_110723_a = new ModelRenderer(this, par1, par2);
        this.field_110723_a.addBox(-3.0f, -6.0f, -3.0f, 6, 8, 6, 0.0f);
        this.field_110723_a.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.field_110723_a.render(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.field_110723_a.rotateAngleY = par4 / 57.295776f;
        this.field_110723_a.rotateAngleX = par5 / 57.295776f;
    }
}

