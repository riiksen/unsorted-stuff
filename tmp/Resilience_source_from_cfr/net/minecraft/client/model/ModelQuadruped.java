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
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelQuadruped
extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    protected float field_78145_g;
    protected float field_78151_h;
    private static final String __OBFID = "CL_00000851";

    public ModelQuadruped(int par1, float par2) {
        this.head = new ModelRenderer(this, 0, 0);
        this.field_78145_g = 8.0f;
        this.field_78151_h = 4.0f;
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, par2);
        this.head.setRotationPoint(0.0f, 18 - par1, -6.0f);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, par2);
        this.body.setRotationPoint(0.0f, 17 - par1, 2.0f);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg1.setRotationPoint(-3.0f, 24 - par1, 7.0f);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg2.setRotationPoint(3.0f, 24 - par1, 7.0f);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg3.setRotationPoint(-3.0f, 24 - par1, -5.0f);
        this.leg4 = new ModelRenderer(this, 0, 16);
        this.leg4.addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg4.setRotationPoint(3.0f, 24 - par1, -5.0f);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)(this.field_78145_g * par7), (float)(this.field_78151_h * par7));
            this.head.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)(1.0f / var8), (float)(1.0f / var8), (float)(1.0f / var8));
            GL11.glTranslatef((float)0.0f, (float)(24.0f * par7), (float)0.0f);
            this.body.render(par7);
            this.leg1.render(par7);
            this.leg2.render(par7);
            this.leg3.render(par7);
            this.leg4.render(par7);
            GL11.glPopMatrix();
        } else {
            this.head.render(par7);
            this.body.render(par7);
            this.leg1.render(par7);
            this.leg2.render(par7);
            this.leg3.render(par7);
            this.leg4.render(par7);
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        float var8 = 57.295776f;
        this.head.rotateAngleX = par5 / 57.295776f;
        this.head.rotateAngleY = par4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
    }
}

