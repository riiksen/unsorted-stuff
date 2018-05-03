/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze
extends ModelBase {
    private ModelRenderer[] blazeSticks = new ModelRenderer[12];
    private ModelRenderer blazeHead;
    private static final String __OBFID = "CL_00000831";

    public ModelBlaze() {
        int var1 = 0;
        while (var1 < this.blazeSticks.length) {
            this.blazeSticks[var1] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[var1].addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
            ++var1;
        }
        this.blazeHead = new ModelRenderer(this, 0, 0);
        this.blazeHead.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
    }

    public int func_78104_a() {
        return 8;
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.blazeHead.render(par7);
        int var8 = 0;
        while (var8 < this.blazeSticks.length) {
            this.blazeSticks[var8].render(par7);
            ++var8;
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        float var8 = par3 * 3.1415927f * -0.1f;
        int var9 = 0;
        while (var9 < 4) {
            this.blazeSticks[var9].rotationPointY = -2.0f + MathHelper.cos(((float)(var9 * 2) + par3) * 0.25f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0f;
            var8 += 1.0f;
            ++var9;
        }
        var8 = 0.7853982f + par3 * 3.1415927f * 0.03f;
        var9 = 4;
        while (var9 < 8) {
            this.blazeSticks[var9].rotationPointY = 2.0f + MathHelper.cos(((float)(var9 * 2) + par3) * 0.25f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 7.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 7.0f;
            var8 += 1.0f;
            ++var9;
        }
        var8 = 0.47123894f + par3 * 3.1415927f * -0.05f;
        var9 = 8;
        while (var9 < 12) {
            this.blazeSticks[var9].rotationPointY = 11.0f + MathHelper.cos(((float)var9 * 1.5f + par3) * 0.5f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 5.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 5.0f;
            var8 += 1.0f;
            ++var9;
        }
        this.blazeHead.rotateAngleY = par4 / 57.295776f;
        this.blazeHead.rotateAngleX = par5 / 57.295776f;
    }
}

