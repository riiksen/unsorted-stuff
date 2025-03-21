/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelVillager
extends ModelBase {
    public ModelRenderer villagerHead;
    public ModelRenderer villagerBody;
    public ModelRenderer villagerArms;
    public ModelRenderer rightVillagerLeg;
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer villagerNose;
    private static final String __OBFID = "CL_00000864";

    public ModelVillager(float par1) {
        this(par1, 0.0f, 64, 64);
    }

    public ModelVillager(float par1, float par2, int par3, int par4) {
        this.villagerHead = new ModelRenderer(this).setTextureSize(par3, par4);
        this.villagerHead.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, par1);
        this.villagerNose = new ModelRenderer(this).setTextureSize(par3, par4);
        this.villagerNose.setRotationPoint(0.0f, par2 - 2.0f, 0.0f);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2, 4, 2, par1);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = new ModelRenderer(this).setTextureSize(par3, par4);
        this.villagerBody.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6, par1);
        this.villagerBody.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8, 18, 6, par1 + 0.5f);
        this.villagerArms = new ModelRenderer(this).setTextureSize(par3, par4);
        this.villagerArms.setRotationPoint(0.0f, 0.0f + par2 + 2.0f, 0.0f);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4, 8, 4, par1);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0f, -2.0f, -2.0f, 4, 8, 4, par1);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8, 4, 4, par1);
        this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4);
        this.rightVillagerLeg.setRotationPoint(-2.0f, 12.0f + par2, 0.0f);
        this.rightVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0f, 12.0f + par2, 0.0f);
        this.leftVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.villagerHead.render(par7);
        this.villagerBody.render(par7);
        this.rightVillagerLeg.render(par7);
        this.leftVillagerLeg.render(par7);
        this.villagerArms.render(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.villagerHead.rotateAngleY = par4 / 57.295776f;
        this.villagerHead.rotateAngleX = par5 / 57.295776f;
        this.villagerArms.rotationPointY = 3.0f;
        this.villagerArms.rotationPointZ = -1.0f;
        this.villagerArms.rotateAngleX = -0.75f;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2 * 0.5f;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2 * 0.5f;
        this.rightVillagerLeg.rotateAngleY = 0.0f;
        this.leftVillagerLeg.rotateAngleY = 0.0f;
    }
}

