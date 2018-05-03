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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelHorse
extends ModelBase {
    private ModelRenderer head;
    private ModelRenderer mouthTop;
    private ModelRenderer mouthBottom;
    private ModelRenderer horseLeftEar;
    private ModelRenderer horseRightEar;
    private ModelRenderer muleLeftEar;
    private ModelRenderer muleRightEar;
    private ModelRenderer neck;
    private ModelRenderer horseFaceRopes;
    private ModelRenderer mane;
    private ModelRenderer body;
    private ModelRenderer tailBase;
    private ModelRenderer tailMiddle;
    private ModelRenderer tailTip;
    private ModelRenderer backLeftLeg;
    private ModelRenderer backLeftShin;
    private ModelRenderer backLeftHoof;
    private ModelRenderer backRightLeg;
    private ModelRenderer backRightShin;
    private ModelRenderer backRightHoof;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer frontLeftShin;
    private ModelRenderer frontLeftHoof;
    private ModelRenderer frontRightLeg;
    private ModelRenderer frontRightShin;
    private ModelRenderer frontRightHoof;
    private ModelRenderer muleLeftChest;
    private ModelRenderer muleRightChest;
    private ModelRenderer horseSaddleBottom;
    private ModelRenderer horseSaddleFront;
    private ModelRenderer horseSaddleBack;
    private ModelRenderer horseLeftSaddleRope;
    private ModelRenderer horseLeftSaddleMetal;
    private ModelRenderer horseRightSaddleRope;
    private ModelRenderer horseRightSaddleMetal;
    private ModelRenderer horseLeftFaceMetal;
    private ModelRenderer horseRightFaceMetal;
    private ModelRenderer horseLeftRein;
    private ModelRenderer horseRightRein;
    private static final String __OBFID = "CL_00000846";

    public ModelHorse() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.body = new ModelRenderer(this, 0, 34);
        this.body.addBox(-5.0f, -8.0f, -19.0f, 10, 10, 24);
        this.body.setRotationPoint(0.0f, 11.0f, 9.0f);
        this.tailBase = new ModelRenderer(this, 44, 0);
        this.tailBase.addBox(-1.0f, -1.0f, 0.0f, 2, 2, 3);
        this.tailBase.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailBase, -1.134464f, 0.0f, 0.0f);
        this.tailMiddle = new ModelRenderer(this, 38, 7);
        this.tailMiddle.addBox(-1.5f, -2.0f, 3.0f, 3, 4, 7);
        this.tailMiddle.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailMiddle, -1.134464f, 0.0f, 0.0f);
        this.tailTip = new ModelRenderer(this, 24, 3);
        this.tailTip.addBox(-1.5f, -4.5f, 9.0f, 3, 4, 7);
        this.tailTip.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailTip, -1.40215f, 0.0f, 0.0f);
        this.backLeftLeg = new ModelRenderer(this, 78, 29);
        this.backLeftLeg.addBox(-2.5f, -2.0f, -2.5f, 4, 9, 5);
        this.backLeftLeg.setRotationPoint(4.0f, 9.0f, 11.0f);
        this.backLeftShin = new ModelRenderer(this, 78, 43);
        this.backLeftShin.addBox(-2.0f, 0.0f, -1.5f, 3, 5, 3);
        this.backLeftShin.setRotationPoint(4.0f, 16.0f, 11.0f);
        this.backLeftHoof = new ModelRenderer(this, 78, 51);
        this.backLeftHoof.addBox(-2.5f, 5.1f, -2.0f, 4, 3, 4);
        this.backLeftHoof.setRotationPoint(4.0f, 16.0f, 11.0f);
        this.backRightLeg = new ModelRenderer(this, 96, 29);
        this.backRightLeg.addBox(-1.5f, -2.0f, -2.5f, 4, 9, 5);
        this.backRightLeg.setRotationPoint(-4.0f, 9.0f, 11.0f);
        this.backRightShin = new ModelRenderer(this, 96, 43);
        this.backRightShin.addBox(-1.0f, 0.0f, -1.5f, 3, 5, 3);
        this.backRightShin.setRotationPoint(-4.0f, 16.0f, 11.0f);
        this.backRightHoof = new ModelRenderer(this, 96, 51);
        this.backRightHoof.addBox(-1.5f, 5.1f, -2.0f, 4, 3, 4);
        this.backRightHoof.setRotationPoint(-4.0f, 16.0f, 11.0f);
        this.frontLeftLeg = new ModelRenderer(this, 44, 29);
        this.frontLeftLeg.addBox(-1.9f, -1.0f, -2.1f, 3, 8, 4);
        this.frontLeftLeg.setRotationPoint(4.0f, 9.0f, -8.0f);
        this.frontLeftShin = new ModelRenderer(this, 44, 41);
        this.frontLeftShin.addBox(-1.9f, 0.0f, -1.6f, 3, 5, 3);
        this.frontLeftShin.setRotationPoint(4.0f, 16.0f, -8.0f);
        this.frontLeftHoof = new ModelRenderer(this, 44, 51);
        this.frontLeftHoof.addBox(-2.4f, 5.1f, -2.1f, 4, 3, 4);
        this.frontLeftHoof.setRotationPoint(4.0f, 16.0f, -8.0f);
        this.frontRightLeg = new ModelRenderer(this, 60, 29);
        this.frontRightLeg.addBox(-1.1f, -1.0f, -2.1f, 3, 8, 4);
        this.frontRightLeg.setRotationPoint(-4.0f, 9.0f, -8.0f);
        this.frontRightShin = new ModelRenderer(this, 60, 41);
        this.frontRightShin.addBox(-1.1f, 0.0f, -1.6f, 3, 5, 3);
        this.frontRightShin.setRotationPoint(-4.0f, 16.0f, -8.0f);
        this.frontRightHoof = new ModelRenderer(this, 60, 51);
        this.frontRightHoof.addBox(-1.6f, 5.1f, -2.1f, 4, 3, 4);
        this.frontRightHoof.setRotationPoint(-4.0f, 16.0f, -8.0f);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5f, -10.0f, -1.5f, 5, 5, 7);
        this.head.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.head, 0.5235988f, 0.0f, 0.0f);
        this.mouthTop = new ModelRenderer(this, 24, 18);
        this.mouthTop.addBox(-2.0f, -10.0f, -7.0f, 4, 3, 6);
        this.mouthTop.setRotationPoint(0.0f, 3.95f, -10.0f);
        this.setBoxRotation(this.mouthTop, 0.5235988f, 0.0f, 0.0f);
        this.mouthBottom = new ModelRenderer(this, 24, 27);
        this.mouthBottom.addBox(-2.0f, -7.0f, -6.5f, 4, 2, 5);
        this.mouthBottom.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.mouthBottom, 0.5235988f, 0.0f, 0.0f);
        this.head.addChild(this.mouthTop);
        this.head.addChild(this.mouthBottom);
        this.horseLeftEar = new ModelRenderer(this, 0, 0);
        this.horseLeftEar.addBox(0.45f, -12.0f, 4.0f, 2, 3, 1);
        this.horseLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftEar, 0.5235988f, 0.0f, 0.0f);
        this.horseRightEar = new ModelRenderer(this, 0, 0);
        this.horseRightEar.addBox(-2.45f, -12.0f, 4.0f, 2, 3, 1);
        this.horseRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightEar, 0.5235988f, 0.0f, 0.0f);
        this.muleLeftEar = new ModelRenderer(this, 0, 12);
        this.muleLeftEar.addBox(-2.0f, -16.0f, 4.0f, 2, 7, 1);
        this.muleLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleLeftEar, 0.5235988f, 0.0f, 0.2617994f);
        this.muleRightEar = new ModelRenderer(this, 0, 12);
        this.muleRightEar.addBox(0.0f, -16.0f, 4.0f, 2, 7, 1);
        this.muleRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleRightEar, 0.5235988f, 0.0f, -0.2617994f);
        this.neck = new ModelRenderer(this, 0, 12);
        this.neck.addBox(-2.05f, -9.8f, -2.0f, 4, 14, 8);
        this.neck.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.neck, 0.5235988f, 0.0f, 0.0f);
        this.muleLeftChest = new ModelRenderer(this, 0, 34);
        this.muleLeftChest.addBox(-3.0f, 0.0f, 0.0f, 8, 8, 3);
        this.muleLeftChest.setRotationPoint(-7.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleLeftChest, 0.0f, 1.5707964f, 0.0f);
        this.muleRightChest = new ModelRenderer(this, 0, 47);
        this.muleRightChest.addBox(-3.0f, 0.0f, 0.0f, 8, 8, 3);
        this.muleRightChest.setRotationPoint(4.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleRightChest, 0.0f, 1.5707964f, 0.0f);
        this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
        this.horseSaddleBottom.addBox(-5.0f, 0.0f, -3.0f, 10, 1, 8);
        this.horseSaddleBottom.setRotationPoint(0.0f, 2.0f, 2.0f);
        this.horseSaddleFront = new ModelRenderer(this, 106, 9);
        this.horseSaddleFront.addBox(-1.5f, -1.0f, -3.0f, 3, 1, 2);
        this.horseSaddleFront.setRotationPoint(0.0f, 2.0f, 2.0f);
        this.horseSaddleBack = new ModelRenderer(this, 80, 9);
        this.horseSaddleBack.addBox(-4.0f, -1.0f, 3.0f, 8, 1, 2);
        this.horseSaddleBack.setRotationPoint(0.0f, 2.0f, 2.0f);
        this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
        this.horseLeftSaddleMetal.addBox(-0.5f, 6.0f, -1.0f, 1, 2, 2);
        this.horseLeftSaddleMetal.setRotationPoint(5.0f, 3.0f, 2.0f);
        this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0);
        this.horseLeftSaddleRope.addBox(-0.5f, 0.0f, -0.5f, 1, 6, 1);
        this.horseLeftSaddleRope.setRotationPoint(5.0f, 3.0f, 2.0f);
        this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
        this.horseRightSaddleMetal.addBox(-0.5f, 6.0f, -1.0f, 1, 2, 2);
        this.horseRightSaddleMetal.setRotationPoint(-5.0f, 3.0f, 2.0f);
        this.horseRightSaddleRope = new ModelRenderer(this, 80, 0);
        this.horseRightSaddleRope.addBox(-0.5f, 0.0f, -0.5f, 1, 6, 1);
        this.horseRightSaddleRope.setRotationPoint(-5.0f, 3.0f, 2.0f);
        this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13);
        this.horseLeftFaceMetal.addBox(1.5f, -8.0f, -4.0f, 1, 2, 2);
        this.horseLeftFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftFaceMetal, 0.5235988f, 0.0f, 0.0f);
        this.horseRightFaceMetal = new ModelRenderer(this, 74, 13);
        this.horseRightFaceMetal.addBox(-2.5f, -8.0f, -4.0f, 1, 2, 2);
        this.horseRightFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightFaceMetal, 0.5235988f, 0.0f, 0.0f);
        this.horseLeftRein = new ModelRenderer(this, 44, 10);
        this.horseLeftRein.addBox(2.6f, -6.0f, -6.0f, 0, 3, 16);
        this.horseLeftRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.horseRightRein = new ModelRenderer(this, 44, 5);
        this.horseRightRein.addBox(-2.6f, -6.0f, -6.0f, 0, 3, 16);
        this.horseRightRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.mane = new ModelRenderer(this, 58, 0);
        this.mane.addBox(-1.0f, -11.5f, 5.0f, 2, 16, 4);
        this.mane.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.mane, 0.5235988f, 0.0f, 0.0f);
        this.horseFaceRopes = new ModelRenderer(this, 80, 12);
        this.horseFaceRopes.addBox(-2.5f, -10.1f, -7.0f, 5, 5, 12, 0.2f);
        this.horseFaceRopes.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseFaceRopes, 0.5235988f, 0.0f, 0.0f);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        boolean var16;
        EntityHorse var8 = (EntityHorse)par1Entity;
        int var9 = var8.getHorseType();
        float var10 = var8.getGrassEatingAmount(0.0f);
        boolean var11 = var8.isAdultHorse();
        boolean var12 = var11 && var8.isHorseSaddled();
        boolean var13 = var11 && var8.isChested();
        boolean var14 = var9 == 1 || var9 == 2;
        float var15 = var8.getHorseSize();
        boolean bl = var16 = var8.riddenByEntity != null;
        if (var12) {
            this.horseFaceRopes.render(par7);
            this.horseSaddleBottom.render(par7);
            this.horseSaddleFront.render(par7);
            this.horseSaddleBack.render(par7);
            this.horseLeftSaddleRope.render(par7);
            this.horseLeftSaddleMetal.render(par7);
            this.horseRightSaddleRope.render(par7);
            this.horseRightSaddleMetal.render(par7);
            this.horseLeftFaceMetal.render(par7);
            this.horseRightFaceMetal.render(par7);
            if (var16) {
                this.horseLeftRein.render(par7);
                this.horseRightRein.render(par7);
            }
        }
        if (!var11) {
            GL11.glPushMatrix();
            GL11.glScalef((float)var15, (float)(0.5f + var15 * 0.5f), (float)var15);
            GL11.glTranslatef((float)0.0f, (float)(0.95f * (1.0f - var15)), (float)0.0f);
        }
        this.backLeftLeg.render(par7);
        this.backLeftShin.render(par7);
        this.backLeftHoof.render(par7);
        this.backRightLeg.render(par7);
        this.backRightShin.render(par7);
        this.backRightHoof.render(par7);
        this.frontLeftLeg.render(par7);
        this.frontLeftShin.render(par7);
        this.frontLeftHoof.render(par7);
        this.frontRightLeg.render(par7);
        this.frontRightShin.render(par7);
        this.frontRightHoof.render(par7);
        if (!var11) {
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)var15, (float)var15, (float)var15);
            GL11.glTranslatef((float)0.0f, (float)(1.35f * (1.0f - var15)), (float)0.0f);
        }
        this.body.render(par7);
        this.tailBase.render(par7);
        this.tailMiddle.render(par7);
        this.tailTip.render(par7);
        this.neck.render(par7);
        this.mane.render(par7);
        if (!var11) {
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            float var17 = 0.5f + var15 * var15 * 0.5f;
            GL11.glScalef((float)var17, (float)var17, (float)var17);
            if (var10 <= 0.0f) {
                GL11.glTranslatef((float)0.0f, (float)(1.35f * (1.0f - var15)), (float)0.0f);
            } else {
                GL11.glTranslatef((float)0.0f, (float)(0.9f * (1.0f - var15) * var10 + 1.35f * (1.0f - var15) * (1.0f - var10)), (float)(0.15f * (1.0f - var15) * var10));
            }
        }
        if (var14) {
            this.muleLeftEar.render(par7);
            this.muleRightEar.render(par7);
        } else {
            this.horseLeftEar.render(par7);
            this.horseRightEar.render(par7);
        }
        this.head.render(par7);
        if (!var11) {
            GL11.glPopMatrix();
        }
        if (var13) {
            this.muleLeftChest.render(par7);
            this.muleRightChest.render(par7);
        }
    }

    private void setBoxRotation(ModelRenderer par1ModelRenderer, float par2, float par3, float par4) {
        par1ModelRenderer.rotateAngleX = par2;
        par1ModelRenderer.rotateAngleY = par3;
        par1ModelRenderer.rotateAngleZ = par4;
    }

    private float updateHorseRotation(float par1, float par2, float par3) {
        float var4 = par2 - par1;
        while (var4 < -180.0f) {
            var4 += 360.0f;
        }
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return par1 + par3 * var4;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
        float var5 = this.updateHorseRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par4);
        float var6 = this.updateHorseRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par4);
        float var7 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par4;
        float var8 = var6 - var5;
        float var9 = var7 / 57.295776f;
        if (var8 > 20.0f) {
            var8 = 20.0f;
        }
        if (var8 < -20.0f) {
            var8 = -20.0f;
        }
        if (par3 > 0.2f) {
            var9 += MathHelper.cos(par2 * 0.4f) * 0.15f * par3;
        }
        EntityHorse var10 = (EntityHorse)par1EntityLivingBase;
        float var11 = var10.getGrassEatingAmount(par4);
        float var12 = var10.getRearingAmount(par4);
        float var13 = 1.0f - var12;
        float var14 = var10.func_110201_q(par4);
        boolean var15 = var10.field_110278_bp != 0;
        boolean var16 = var10.isHorseSaddled();
        boolean var17 = var10.riddenByEntity != null;
        float var18 = (float)par1EntityLivingBase.ticksExisted + par4;
        float var19 = MathHelper.cos(par2 * 0.6662f + 3.1415927f);
        float var20 = var19 * 0.8f * par3;
        this.head.rotationPointY = 4.0f;
        this.head.rotationPointZ = -10.0f;
        this.tailBase.rotationPointY = 3.0f;
        this.tailMiddle.rotationPointZ = 14.0f;
        this.muleRightChest.rotationPointY = 3.0f;
        this.muleRightChest.rotationPointZ = 10.0f;
        this.body.rotateAngleX = 0.0f;
        this.head.rotateAngleX = 0.5235988f + var9;
        this.head.rotateAngleY = var8 / 57.295776f;
        this.head.rotateAngleX = var12 * (0.2617994f + var9) + var11 * 2.18166f + (1.0f - Math.max(var12, var11)) * this.head.rotateAngleX;
        this.head.rotateAngleY = var12 * (var8 / 57.295776f) + (1.0f - Math.max(var12, var11)) * this.head.rotateAngleY;
        this.head.rotationPointY = var12 * -6.0f + var11 * 11.0f + (1.0f - Math.max(var12, var11)) * this.head.rotationPointY;
        this.head.rotationPointZ = var12 * -1.0f + var11 * -10.0f + (1.0f - Math.max(var12, var11)) * this.head.rotationPointZ;
        this.tailBase.rotationPointY = var12 * 9.0f + var13 * this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = var12 * 18.0f + var13 * this.tailMiddle.rotationPointZ;
        this.muleRightChest.rotationPointY = var12 * 5.5f + var13 * this.muleRightChest.rotationPointY;
        this.muleRightChest.rotationPointZ = var12 * 15.0f + var13 * this.muleRightChest.rotationPointZ;
        this.body.rotateAngleX = var12 * -0.7853982f + var13 * this.body.rotateAngleX;
        this.horseLeftEar.rotationPointY = this.head.rotationPointY;
        this.horseRightEar.rotationPointY = this.head.rotationPointY;
        this.muleLeftEar.rotationPointY = this.head.rotationPointY;
        this.muleRightEar.rotationPointY = this.head.rotationPointY;
        this.neck.rotationPointY = this.head.rotationPointY;
        this.mouthTop.rotationPointY = 0.02f;
        this.mouthBottom.rotationPointY = 0.0f;
        this.mane.rotationPointY = this.head.rotationPointY;
        this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
        this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
        this.neck.rotationPointZ = this.head.rotationPointZ;
        this.mouthTop.rotationPointZ = 0.02f - var14 * 1.0f;
        this.mouthBottom.rotationPointZ = 0.0f + var14 * 1.0f;
        this.mane.rotationPointZ = this.head.rotationPointZ;
        this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
        this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.mouthTop.rotateAngleX = 0.0f - 0.09424778f * var14;
        this.mouthBottom.rotateAngleX = 0.0f + 0.15707964f * var14;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.mouthTop.rotateAngleY = 0.0f;
        this.mouthBottom.rotateAngleY = 0.0f;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftChest.rotateAngleX = var20 / 5.0f;
        this.muleRightChest.rotateAngleX = (- var20) / 5.0f;
        float var21 = 1.5707964f;
        float var22 = 4.712389f;
        float var23 = -1.0471976f;
        float var24 = 0.2617994f * var12;
        float var25 = MathHelper.cos(var18 * 0.6f + 3.1415927f);
        this.frontLeftLeg.rotationPointY = -2.0f * var12 + 9.0f * var13;
        this.frontLeftLeg.rotationPointZ = -2.0f * var12 + -8.0f * var13;
        this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
        this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
        this.backLeftShin.rotationPointY = this.backLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + var24 + var13 * (- var19) * 0.5f * par3) * 7.0f;
        this.backLeftShin.rotationPointZ = this.backLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + var24 + var13 * (- var19) * 0.5f * par3) * 7.0f;
        this.backRightShin.rotationPointY = this.backRightLeg.rotationPointY + MathHelper.sin(1.5707964f + var24 + var13 * var19 * 0.5f * par3) * 7.0f;
        this.backRightShin.rotationPointZ = this.backRightLeg.rotationPointZ + MathHelper.cos(4.712389f + var24 + var13 * var19 * 0.5f * par3) * 7.0f;
        float var26 = (-1.0471976f + var25) * var12 + var20 * var13;
        float var27 = (-1.0471976f + (- var25)) * var12 + (- var20) * var13;
        this.frontLeftShin.rotationPointY = this.frontLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + var26) * 7.0f;
        this.frontLeftShin.rotationPointZ = this.frontLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + var26) * 7.0f;
        this.frontRightShin.rotationPointY = this.frontRightLeg.rotationPointY + MathHelper.sin(1.5707964f + var27) * 7.0f;
        this.frontRightShin.rotationPointZ = this.frontRightLeg.rotationPointZ + MathHelper.cos(4.712389f + var27) * 7.0f;
        this.backLeftLeg.rotateAngleX = var24 + (- var19) * 0.5f * par3 * var13;
        this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX = -0.08726646f * var12 + ((- var19) * 0.5f * par3 - Math.max(0.0f, var19 * 0.5f * par3)) * var13;
        this.backRightLeg.rotateAngleX = var24 + var19 * 0.5f * par3 * var13;
        this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX = -0.08726646f * var12 + (var19 * 0.5f * par3 - Math.max(0.0f, (- var19) * 0.5f * par3)) * var13;
        this.frontLeftLeg.rotateAngleX = var26;
        this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f + var25 * 0.2f)) * var12 + (var20 + Math.max(0.0f, var19 * 0.5f * par3)) * var13;
        this.frontRightLeg.rotateAngleX = var27;
        this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f - var25 * 0.2f)) * var12 + (- var20 + Math.max(0.0f, (- var19) * 0.5f * par3)) * var13;
        this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
        this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
        this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
        this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
        this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
        this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
        this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
        this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
        if (var16) {
            this.horseSaddleBottom.rotationPointY = var12 * 0.5f + var13 * 2.0f;
            this.horseSaddleBottom.rotationPointZ = var12 * 11.0f + var13 * 2.0f;
            this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
            this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
            this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
            this.horseLeftRein.rotationPointY = this.head.rotationPointY;
            this.horseRightRein.rotationPointY = this.head.rotationPointY;
            this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
            this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
            this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
            this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftRein.rotateAngleX = var9;
            this.horseRightRein.rotateAngleX = var9;
            this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
            this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
            this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
            if (var17) {
                this.horseLeftSaddleRope.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseRightSaddleRope.rotateAngleX = -1.0471976f;
                this.horseRightSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleRope.rotateAngleZ = 0.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = 0.0f;
                this.horseRightSaddleRope.rotateAngleZ = 0.0f;
                this.horseRightSaddleMetal.rotateAngleZ = 0.0f;
            } else {
                this.horseLeftSaddleRope.rotateAngleX = var20 / 3.0f;
                this.horseLeftSaddleMetal.rotateAngleX = var20 / 3.0f;
                this.horseRightSaddleRope.rotateAngleX = var20 / 3.0f;
                this.horseRightSaddleMetal.rotateAngleX = var20 / 3.0f;
                this.horseLeftSaddleRope.rotateAngleZ = var20 / 5.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = var20 / 5.0f;
                this.horseRightSaddleRope.rotateAngleZ = (- var20) / 5.0f;
                this.horseRightSaddleMetal.rotateAngleZ = (- var20) / 5.0f;
            }
        }
        if ((var21 = -1.3089f + par3 * 1.5f) > 0.0f) {
            var21 = 0.0f;
        }
        if (var15) {
            this.tailBase.rotateAngleY = MathHelper.cos(var18 * 0.7f);
            var21 = 0.0f;
        } else {
            this.tailBase.rotateAngleY = 0.0f;
        }
        this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
        this.tailTip.rotationPointY = this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailBase.rotateAngleX = var21;
        this.tailMiddle.rotateAngleX = var21;
        this.tailTip.rotateAngleX = -0.2618f + var21;
    }
}

