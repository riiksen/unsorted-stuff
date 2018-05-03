/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying
extends EntityLiving {
    private static final String __OBFID = "CL_00001545";

    public EntityFlying(World par1World) {
        super(par1World);
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
    }

    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        if (this.isInWater()) {
            this.moveFlying(par1, par2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
        } else if (this.handleLavaMovement()) {
            this.moveFlying(par1, par2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        } else {
            float var3 = 0.91f;
            if (this.onGround) {
                var3 = this.worldObj.getBlock((int)MathHelper.floor_double((double)this.posX), (int)(MathHelper.floor_double((double)this.boundingBox.minY) - 1), (int)MathHelper.floor_double((double)this.posZ)).slipperiness * 0.91f;
            }
            float var4 = 0.16277136f / (var3 * var3 * var3);
            this.moveFlying(par1, par2, this.onGround ? 0.1f * var4 : 0.02f);
            var3 = 0.91f;
            if (this.onGround) {
                var3 = this.worldObj.getBlock((int)MathHelper.floor_double((double)this.posX), (int)(MathHelper.floor_double((double)this.boundingBox.minY) - 1), (int)MathHelper.floor_double((double)this.posZ)).slipperiness * 0.91f;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)var3;
            this.motionY *= (double)var3;
            this.motionZ *= (double)var3;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double var8 = this.posX - this.prevPosX;
        double var5 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}

