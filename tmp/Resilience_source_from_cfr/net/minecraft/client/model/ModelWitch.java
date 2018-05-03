/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWitch
extends ModelVillager {
    public boolean field_82900_g;
    private ModelRenderer field_82901_h;
    private ModelRenderer witchHat;
    private static final String __OBFID = "CL_00000866";

    public ModelWitch(float par1) {
        super(par1, 0.0f, 64, 128);
        this.field_82901_h = new ModelRenderer(this).setTextureSize(64, 128);
        this.field_82901_h.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.field_82901_h.setTextureOffset(0, 0).addBox(0.0f, 3.0f, -6.75f, 1, 1, 1, -0.25f);
        this.villagerNose.addChild(this.field_82901_h);
        this.witchHat = new ModelRenderer(this).setTextureSize(64, 128);
        this.witchHat.setRotationPoint(-5.0f, -10.03125f, -5.0f);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0f, 0.0f, 0.0f, 10, 2, 10);
        this.villagerHead.addChild(this.witchHat);
        ModelRenderer var2 = new ModelRenderer(this).setTextureSize(64, 128);
        var2.setRotationPoint(1.75f, -4.0f, 2.0f);
        var2.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7, 4, 7);
        var2.rotateAngleX = -0.05235988f;
        var2.rotateAngleZ = 0.02617994f;
        this.witchHat.addChild(var2);
        ModelRenderer var3 = new ModelRenderer(this).setTextureSize(64, 128);
        var3.setRotationPoint(1.75f, -4.0f, 2.0f);
        var3.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4);
        var3.rotateAngleX = -0.10471976f;
        var3.rotateAngleZ = 0.05235988f;
        var2.addChild(var3);
        ModelRenderer var4 = new ModelRenderer(this).setTextureSize(64, 128);
        var4.setRotationPoint(1.75f, -2.0f, 2.0f);
        var4.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        var4.rotateAngleX = -0.20943952f;
        var4.rotateAngleZ = 0.10471976f;
        var3.addChild(var4);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.villagerNose.offsetZ = 0.0f;
        this.villagerNose.offsetY = 0.0f;
        this.villagerNose.offsetX = 0.0f;
        float var8 = 0.01f * (float)(par7Entity.getEntityId() % 10);
        this.villagerNose.rotateAngleX = MathHelper.sin((float)par7Entity.ticksExisted * var8) * 4.5f * 3.1415927f / 180.0f;
        this.villagerNose.rotateAngleY = 0.0f;
        this.villagerNose.rotateAngleZ = MathHelper.cos((float)par7Entity.ticksExisted * var8) * 2.5f * 3.1415927f / 180.0f;
        if (this.field_82900_g) {
            this.villagerNose.rotateAngleX = -0.9f;
            this.villagerNose.offsetZ = -0.09375f;
            this.villagerNose.offsetY = 0.1875f;
        }
    }
}

