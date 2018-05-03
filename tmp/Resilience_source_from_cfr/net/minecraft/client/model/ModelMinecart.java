/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMinecart
extends ModelBase {
    public ModelRenderer[] sideModels = new ModelRenderer[7];
    private static final String __OBFID = "CL_00000844";

    public ModelMinecart() {
        this.sideModels[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        int var1 = 20;
        int var2 = 8;
        int var3 = 16;
        int var4 = 4;
        this.sideModels[0].addBox((- var1) / 2, (float)((- var3) / 2), -1.0f, var1, var3, 2, 0.0f);
        this.sideModels[0].setRotationPoint(0.0f, var4, 0.0f);
        this.sideModels[5].addBox((- var1) / 2 + 1, (float)((- var3) / 2 + 1), -1.0f, var1 - 2, var3 - 2, 1, 0.0f);
        this.sideModels[5].setRotationPoint(0.0f, var4, 0.0f);
        this.sideModels[1].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[1].setRotationPoint((- var1) / 2 + 1, var4, 0.0f);
        this.sideModels[2].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[2].setRotationPoint(var1 / 2 - 1, var4, 0.0f);
        this.sideModels[3].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[3].setRotationPoint(0.0f, var4, (- var3) / 2 + 1);
        this.sideModels[4].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[4].setRotationPoint(0.0f, var4, var3 / 2 - 1);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = 3.1415927f;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.sideModels[5].rotationPointY = 4.0f - par4;
        int var8 = 0;
        while (var8 < 6) {
            this.sideModels[var8].render(par7);
            ++var8;
        }
    }
}

