/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSlime
extends ModelBase {
    ModelRenderer slimeBodies;
    ModelRenderer slimeRightEye;
    ModelRenderer slimeLeftEye;
    ModelRenderer slimeMouth;
    private static final String __OBFID = "CL_00000858";

    public ModelSlime(int par1) {
        this.slimeBodies = new ModelRenderer(this, 0, par1);
        this.slimeBodies.addBox(-4.0f, 16.0f, -4.0f, 8, 8, 8);
        if (par1 > 0) {
            this.slimeBodies = new ModelRenderer(this, 0, par1);
            this.slimeBodies.addBox(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            this.slimeRightEye = new ModelRenderer(this, 32, 0);
            this.slimeRightEye.addBox(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeLeftEye = new ModelRenderer(this, 32, 4);
            this.slimeLeftEye.addBox(1.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeMouth = new ModelRenderer(this, 32, 8);
            this.slimeMouth.addBox(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.slimeBodies.render(par7);
        if (this.slimeRightEye != null) {
            this.slimeRightEye.render(par7);
            this.slimeLeftEye.render(par7);
            this.slimeMouth.render(par7);
        }
    }
}

