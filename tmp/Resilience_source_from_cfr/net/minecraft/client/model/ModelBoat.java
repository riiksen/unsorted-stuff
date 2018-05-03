/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoat
extends ModelBase {
    public ModelRenderer[] boatSides = new ModelRenderer[5];
    private static final String __OBFID = "CL_00000832";

    public ModelBoat() {
        this.boatSides[0] = new ModelRenderer(this, 0, 8);
        this.boatSides[1] = new ModelRenderer(this, 0, 0);
        this.boatSides[2] = new ModelRenderer(this, 0, 0);
        this.boatSides[3] = new ModelRenderer(this, 0, 0);
        this.boatSides[4] = new ModelRenderer(this, 0, 0);
        int var1 = 24;
        int var2 = 6;
        int var3 = 20;
        int var4 = 4;
        this.boatSides[0].addBox((- var1) / 2, (float)((- var3) / 2 + 2), -3.0f, var1, var3 - 4, 4, 0.0f);
        this.boatSides[0].setRotationPoint(0.0f, var4, 0.0f);
        this.boatSides[1].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[1].setRotationPoint((- var1) / 2 + 1, var4, 0.0f);
        this.boatSides[2].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[2].setRotationPoint(var1 / 2 - 1, var4, 0.0f);
        this.boatSides[3].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[3].setRotationPoint(0.0f, var4, (- var3) / 2 + 1);
        this.boatSides[4].addBox((- var1) / 2 + 2, (float)(- var2 - 1), -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[4].setRotationPoint(0.0f, var4, var3 / 2 - 1);
        this.boatSides[0].rotateAngleX = 1.5707964f;
        this.boatSides[1].rotateAngleY = 4.712389f;
        this.boatSides[2].rotateAngleY = 1.5707964f;
        this.boatSides[3].rotateAngleY = 3.1415927f;
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        int var8 = 0;
        while (var8 < 5) {
            this.boatSides[var8].render(par7);
            ++var8;
        }
    }
}

