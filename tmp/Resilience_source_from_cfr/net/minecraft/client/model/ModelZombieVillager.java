/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombieVillager
extends ModelBiped {
    private static final String __OBFID = "CL_00000865";

    public ModelZombieVillager() {
        this(0.0f, 0.0f, false);
    }

    public ModelZombieVillager(float par1, float par2, boolean par3) {
        super(par1, 0.0f, 64, par3 ? 32 : 64);
        if (par3) {
            this.bipedHead = new ModelRenderer(this, 0, 0);
            this.bipedHead.addBox(-4.0f, -10.0f, -4.0f, 8, 6, 8, par1);
            this.bipedHead.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        } else {
            this.bipedHead = new ModelRenderer(this);
            this.bipedHead.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
            this.bipedHead.setTextureOffset(0, 32).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, par1);
            this.bipedHead.setTextureOffset(24, 32).addBox(-1.0f, -3.0f, -6.0f, 2, 4, 2, par1);
        }
    }

    public int func_82897_a() {
        return 10;
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        float var8 = MathHelper.sin(this.onGround * 3.1415927f);
        float var9 = MathHelper.sin((1.0f - (1.0f - this.onGround) * (1.0f - this.onGround)) * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = - 0.1f - var8 * 0.6f;
        this.bipedLeftArm.rotateAngleY = 0.1f - var8 * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        this.bipedRightArm.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
        this.bipedLeftArm.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067f) * 0.05f;
    }
}

