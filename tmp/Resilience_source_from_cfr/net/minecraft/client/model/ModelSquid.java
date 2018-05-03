/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSquid
extends ModelBase {
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles = new ModelRenderer[8];
    private static final String __OBFID = "CL_00000861";

    public ModelSquid() {
        int var1 = -16;
        this.squidBody = new ModelRenderer(this, 0, 0);
        this.squidBody.addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        this.squidBody.rotationPointY += (float)(24 + var1);
        int var2 = 0;
        while (var2 < this.squidTentacles.length) {
            this.squidTentacles[var2] = new ModelRenderer(this, 48, 0);
            double var3 = (double)var2 * 3.141592653589793 * 2.0 / (double)this.squidTentacles.length;
            float var5 = (float)Math.cos(var3) * 5.0f;
            float var6 = (float)Math.sin(var3) * 5.0f;
            this.squidTentacles[var2].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[var2].rotationPointX = var5;
            this.squidTentacles[var2].rotationPointZ = var6;
            this.squidTentacles[var2].rotationPointY = 31 + var1;
            var3 = (double)var2 * 3.141592653589793 * -2.0 / (double)this.squidTentacles.length + 1.5707963267948966;
            this.squidTentacles[var2].rotateAngleY = (float)var3;
            ++var2;
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        ModelRenderer[] var8 = this.squidTentacles;
        int var9 = var8.length;
        int var10 = 0;
        while (var10 < var9) {
            ModelRenderer var11 = var8[var10];
            var11.rotateAngleX = par3;
            ++var10;
        }
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.squidBody.render(par7);
        int var8 = 0;
        while (var8 < this.squidTentacles.length) {
            this.squidTentacles[var8].render(par7);
            ++var8;
        }
    }
}

