/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public abstract class EntityThrowable
extends Entity
implements IProjectile {
    private int field_145788_c = -1;
    private int field_145786_d = -1;
    private int field_145787_e = -1;
    private Block field_145785_f;
    protected boolean inGround;
    public int throwableShake;
    private EntityLivingBase thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    private static final String __OBFID = "CL_00001723";

    public EntityThrowable(World par1World) {
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

    public EntityThrowable(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World);
        this.thrower = par2EntityLivingBase;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.posY -= 0.10000000149011612;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float var3 = 0.4f;
        this.motionX = (- MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = (- MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0f * 3.1415927f)) * var3;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0f);
    }

    public EntityThrowable(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.ticksInGround = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }

    protected float func_70182_d() {
        return 1.5f;
    }

    protected float func_70183_g() {
        return 0.0f;
    }

    @Override
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937 * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937 * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937 * (double)par8;
        this.motionX = par1 *= (double)par7;
        this.motionY = par3 *= (double)par7;
        this.motionZ = par5 *= (double)par7;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0 / 3.141592653589793);
        this.ticksInGround = 0;
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
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        if (this.inGround) {
            if (this.worldObj.getBlock(this.field_145788_c, this.field_145786_d, this.field_145787_e) == this.field_145785_f) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        } else {
            ++this.ticksInAir;
        }
        Vec3 var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
        var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (var3 != null) {
            var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
        }
        if (!this.worldObj.isClient) {
            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;
            EntityLivingBase var8 = this.getThrower();
            int var9 = 0;
            while (var9 < var5.size()) {
                MovingObjectPosition var13;
                AxisAlignedBB var12;
                float var11;
                double var14;
                Entity var10 = (Entity)var5.get(var9);
                if (!(!var10.canBeCollidedWith() || var10 == var8 && this.ticksInAir < 5 || (var13 = (var12 = var10.boundingBox.expand(var11 = 0.3f, var11, var11)).calculateIntercept(var1, var2)) == null || (var14 = var1.distanceTo(var13.hitVec)) >= var6 && var6 != 0.0)) {
                    var4 = var10;
                    var6 = var14;
                }
                ++var9;
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
        }
        if (var3 != null) {
            if (var3.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(var3.blockX, var3.blockY, var3.blockZ) == Blocks.portal) {
                this.setInPortal();
            } else {
                this.onImpact(var3);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var16) * 180.0 / 3.141592653589793);
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
        float var17 = 0.99f;
        float var18 = this.getGravityVelocity();
        if (this.isInWater()) {
            int var7 = 0;
            while (var7 < 4) {
                float var19 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var19, this.posY - this.motionY * (double)var19, this.posZ - this.motionZ * (double)var19, this.motionX, this.motionY, this.motionZ);
                ++var7;
            }
            var17 = 0.8f;
        }
        this.motionX *= (double)var17;
        this.motionY *= (double)var17;
        this.motionZ *= (double)var17;
        this.motionY -= (double)var18;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    protected float getGravityVelocity() {
        return 0.03f;
    }

    protected abstract void onImpact(MovingObjectPosition var1);

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.field_145788_c);
        par1NBTTagCompound.setShort("yTile", (short)this.field_145786_d);
        par1NBTTagCompound.setShort("zTile", (short)this.field_145787_e);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145785_f));
        par1NBTTagCompound.setByte("shake", (byte)this.throwableShake);
        par1NBTTagCompound.setByte("inGround", this.inGround ? 1 : 0);
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getCommandSenderName();
        }
        par1NBTTagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_145788_c = par1NBTTagCompound.getShort("xTile");
        this.field_145786_d = par1NBTTagCompound.getShort("yTile");
        this.field_145787_e = par1NBTTagCompound.getShort("zTile");
        this.field_145785_f = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
        this.throwableShake = par1NBTTagCompound.getByte("shake") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
        this.throwerName = par1NBTTagCompound.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public EntityLivingBase getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
        }
        return this.thrower;
    }
}

