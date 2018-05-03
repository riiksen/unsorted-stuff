/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye
extends Entity {
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;
    private static final String __OBFID = "CL_00001716";

    public EntityEnderEye(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double par1) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        if (par1 < (var3 *= 64.0) * var3) {
            return true;
        }
        return false;
    }

    public EntityEnderEye(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }

    public void moveTowards(double par1, int par3, double par4) {
        double var6 = par1 - this.posX;
        double var8 = par4 - this.posZ;
        float var10 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
        if (var10 > 12.0f) {
            this.targetX = this.posX + var6 / (double)var10 * 12.0;
            this.targetZ = this.posZ + var8 / (double)var10 * 12.0;
            this.targetY = this.posY + 8.0;
        } else {
            this.targetX = par1;
            this.targetY = par3;
            this.targetZ = par4;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = this.rand.nextInt(5) > 0;
    }

    @Override
    public void setVelocity(double par1, double par3, double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0 / 3.141592653589793);
        }
    }

    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (!this.worldObj.isClient) {
            double var2 = this.targetX - this.posX;
            double var4 = this.targetZ - this.posZ;
            float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
            float var7 = (float)Math.atan2(var4, var2);
            double var8 = (double)var1 + (double)(var6 - var1) * 0.0025;
            if (var6 < 1.0f) {
                var8 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(var7) * var8;
            this.motionZ = Math.sin(var7) * var8;
            this.motionY = this.posY < this.targetY ? (this.motionY += (1.0 - this.motionY) * 0.014999999664723873) : (this.motionY += (-1.0 - this.motionY) * 0.014999999664723873);
        }
        float var10 = 0.25f;
        if (this.isInWater()) {
            int var3 = 0;
            while (var3 < 4) {
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var10, this.posY - this.motionY * (double)var10, this.posZ - this.motionZ * (double)var10, this.motionX, this.motionY, this.motionZ);
                ++var3;
            }
        } else {
            this.worldObj.spawnParticle("portal", this.posX - this.motionX * (double)var10 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * (double)var10 - 0.5, this.posZ - this.motionZ * (double)var10 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
        }
        if (!this.worldObj.isClient) {
            this.setPosition(this.posX, this.posY, this.posZ);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.worldObj.isClient) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
                } else {
                    this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public float getBrightness(float par1) {
        return 1.0f;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}

