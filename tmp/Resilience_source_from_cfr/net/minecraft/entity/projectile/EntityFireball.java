/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public abstract class EntityFireball
extends Entity {
    private int field_145795_e = -1;
    private int field_145793_f = -1;
    private int field_145794_g = -1;
    private Block field_145796_h;
    private boolean inGround;
    public EntityLivingBase shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    private static final String __OBFID = "CL_00001717";

    public EntityFireball(World par1World) {
        super(par1World);
        this.setSize(1.0f, 1.0f);
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

    public EntityFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World);
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(par2, par4, par6, this.rotationYaw, this.rotationPitch);
        this.setPosition(par2, par4, par6);
        double var14 = MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
        this.accelerationX = par8 / var14 * 0.1;
        this.accelerationY = par10 / var14 * 0.1;
        this.accelerationZ = par12 / var14 * 0.1;
    }

    public EntityFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7) {
        super(par1World);
        this.shootingEntity = par2EntityLivingBase;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY, par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        double var9 = MathHelper.sqrt_double(par3 * (par3 += this.rand.nextGaussian() * 0.4) + par5 * (par5 += this.rand.nextGaussian() * 0.4) + par7 * (par7 += this.rand.nextGaussian() * 0.4));
        this.accelerationX = par3 / var9 * 0.1;
        this.accelerationY = par5 / var9 * 0.1;
        this.accelerationZ = par7 / var9 * 0.1;
    }

    @Override
    public void onUpdate() {
        if (!this.worldObj.isClient && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ))) {
            this.setDead();
        } else {
            super.onUpdate();
            this.setFire(1);
            if (this.inGround) {
                if (this.worldObj.getBlock(this.field_145795_e, this.field_145793_f, this.field_145794_g) == this.field_145796_h) {
                    ++this.ticksAlive;
                    if (this.ticksAlive == 600) {
                        this.setDead();
                    }
                    return;
                }
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.ticksAlive = 0;
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
            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;
            int var8 = 0;
            while (var8 < var5.size()) {
                float var10;
                AxisAlignedBB var11;
                MovingObjectPosition var12;
                double var13;
                Entity var9 = (Entity)var5.get(var8);
                if (!(!var9.canBeCollidedWith() || var9.isEntityEqual(this.shootingEntity) && this.ticksInAir < 25 || (var12 = (var11 = var9.boundingBox.expand(var10 = 0.3f, var10, var10)).calculateIntercept(var1, var2)) == null || (var13 = var1.distanceTo(var12.hitVec)) >= var6 && var6 != 0.0)) {
                    var4 = var9;
                    var6 = var13;
                }
                ++var8;
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
            if (var3 != null) {
                this.onImpact(var3);
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) + 90.0f;
            this.rotationPitch = (float)(Math.atan2(var15, this.motionY) * 180.0 / 3.141592653589793) - 90.0f;
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
            float var16 = this.getMotionFactor();
            if (this.isInWater()) {
                int var18 = 0;
                while (var18 < 4) {
                    float var17 = 0.25f;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var17, this.posY - this.motionY * (double)var17, this.posZ - this.motionZ * (double)var17, this.motionX, this.motionY, this.motionZ);
                    ++var18;
                }
                var16 = 0.8f;
            }
            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= (double)var16;
            this.motionY *= (double)var16;
            this.motionZ *= (double)var16;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    protected float getMotionFactor() {
        return 0.95f;
    }

    protected abstract void onImpact(MovingObjectPosition var1);

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.field_145795_e);
        par1NBTTagCompound.setShort("yTile", (short)this.field_145793_f);
        par1NBTTagCompound.setShort("zTile", (short)this.field_145794_g);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145796_h));
        par1NBTTagCompound.setByte("inGround", this.inGround ? 1 : 0);
        par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_145795_e = par1NBTTagCompound.getShort("xTile");
        this.field_145793_f = par1NBTTagCompound.getShort("yTile");
        this.field_145794_g = par1NBTTagCompound.getShort("zTile");
        this.field_145796_h = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
        boolean bl = this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
        if (par1NBTTagCompound.func_150297_b("direction", 9)) {
            NBTTagList var2 = par1NBTTagCompound.getTagList("direction", 6);
            this.motionX = var2.func_150309_d(0);
            this.motionY = var2.func_150309_d(1);
            this.motionZ = var2.func_150309_d(2);
        } else {
            this.setDead();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        if (par1DamageSource.getEntity() != null) {
            Vec3 var3 = par1DamageSource.getEntity().getLookVec();
            if (var3 != null) {
                this.motionX = var3.xCoord;
                this.motionY = var3.yCoord;
                this.motionZ = var3.zCoord;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }
            if (par1DamageSource.getEntity() instanceof EntityLivingBase) {
                this.shootingEntity = (EntityLivingBase)par1DamageSource.getEntity();
            }
            return true;
        }
        return false;
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
}

