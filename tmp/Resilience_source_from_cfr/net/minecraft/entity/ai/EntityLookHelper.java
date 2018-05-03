/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class EntityLookHelper {
    private EntityLiving entity;
    private float deltaLookYaw;
    private float deltaLookPitch;
    private boolean isLooking;
    private double posX;
    private double posY;
    private double posZ;
    private static final String __OBFID = "CL_00001572";

    public EntityLookHelper(EntityLiving par1EntityLiving) {
        this.entity = par1EntityLiving;
    }

    public void setLookPositionWithEntity(Entity par1Entity, float par2, float par3) {
        this.posX = par1Entity.posX;
        this.posY = par1Entity instanceof EntityLivingBase ? par1Entity.posY + (double)par1Entity.getEyeHeight() : (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0;
        this.posZ = par1Entity.posZ;
        this.deltaLookYaw = par2;
        this.deltaLookPitch = par3;
        this.isLooking = true;
    }

    public void setLookPosition(double par1, double par3, double par5, float par7, float par8) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        this.deltaLookYaw = par7;
        this.deltaLookPitch = par8;
        this.isLooking = true;
    }

    public void onUpdateLook() {
        this.entity.rotationPitch = 0.0f;
        if (this.isLooking) {
            this.isLooking = false;
            double var1 = this.posX - this.entity.posX;
            double var3 = this.posY - (this.entity.posY + (double)this.entity.getEyeHeight());
            double var5 = this.posZ - this.entity.posZ;
            double var7 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
            float var9 = (float)(Math.atan2(var5, var1) * 180.0 / 3.141592653589793) - 90.0f;
            float var10 = (float)(- Math.atan2(var3, var7) * 180.0 / 3.141592653589793);
            this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, var10, this.deltaLookPitch);
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, var9, this.deltaLookYaw);
        } else {
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0f);
        }
        float var11 = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
        if (!this.entity.getNavigator().noPath()) {
            if (var11 < -75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0f;
            }
            if (var11 > 75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0f;
            }
        }
    }

    private float updateRotation(float par1, float par2, float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < - par3) {
            var4 = - par3;
        }
        return par1 + var4;
    }
}

