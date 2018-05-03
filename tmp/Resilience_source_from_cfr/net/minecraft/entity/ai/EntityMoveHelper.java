/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class EntityMoveHelper {
    private EntityLiving entity;
    private double posX;
    private double posY;
    private double posZ;
    private double speed;
    private boolean update;
    private static final String __OBFID = "CL_00001573";

    public EntityMoveHelper(EntityLiving par1EntityLiving) {
        this.entity = par1EntityLiving;
        this.posX = par1EntityLiving.posX;
        this.posY = par1EntityLiving.posY;
        this.posZ = par1EntityLiving.posZ;
    }

    public boolean isUpdating() {
        return this.update;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setMoveTo(double par1, double par3, double par5, double par7) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        this.speed = par7;
        this.update = true;
    }

    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0f);
        if (this.update) {
            double var4;
            this.update = false;
            double var2 = this.posX - this.entity.posX;
            int var1 = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5);
            double var6 = this.posY - (double)var1;
            double var8 = var2 * var2 + var6 * var6 + (var4 = this.posZ - this.entity.posZ) * var4;
            if (var8 >= 2.500000277905201E-7) {
                float var10 = (float)(Math.atan2(var4, var2) * 180.0 / 3.141592653589793) - 90.0f;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, var10, 30.0f);
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                if (var6 > 0.0 && var2 * var2 + var4 * var4 < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }

    private float limitAngle(float par1, float par2, float par3) {
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

