/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBoat
extends Entity {
    private boolean isBoatEmpty = true;
    private double speedMultiplier = 0.07;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID = "CL_00001667";

    public EntityBoat(World par1World) {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return par1Entity.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityBoat(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - 0.30000001192092896;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isClient && !this.isDead) {
            boolean var3;
            this.setForwardDirection(- this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10.0f);
            this.setBeenAttacked();
            boolean bl = var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;
            if (var3 || this.getDamageTaken() > 40.0f) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                if (!var3) {
                    this.func_145778_a(Items.boat, 1, 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(- this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        if (this.isBoatEmpty) {
            this.boatPosRotationIncrements = par9 + 5;
        } else {
            double var10 = par1 - this.posX;
            double var12 = par3 - this.posY;
            double var14 = par5 - this.posZ;
            double var16 = var10 * var10 + var12 * var12 + var14 * var14;
            if (var16 <= 1.0) {
                return;
            }
            this.boatPosRotationIncrements = 3;
        }
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @Override
    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    @Override
    public void onUpdate() {
        double var24;
        double var8;
        double var6;
        double var26;
        int var10;
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        int var1 = 5;
        double var2 = 0.0;
        int var4 = 0;
        while (var4 < var1) {
            double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125;
            double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125;
            AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var9, Material.water)) {
                var2 += 1.0 / (double)var1;
            }
            ++var4;
        }
        double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var19 > 0.26249999999999996) {
            var6 = Math.cos((double)this.rotationYaw * 3.141592653589793 / 180.0);
            var8 = Math.sin((double)this.rotationYaw * 3.141592653589793 / 180.0);
            var10 = 0;
            while ((double)var10 < 1.0 + var19 * 60.0) {
                double var15;
                double var17;
                double var11 = this.rand.nextFloat() * 2.0f - 1.0f;
                double var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    var15 = this.posX - var6 * var11 * 0.8 + var8 * var13;
                    var17 = this.posZ - var8 * var11 * 0.8 - var6 * var13;
                    this.worldObj.spawnParticle("splash", var15, this.posY - 0.125, var17, this.motionX, this.motionY, this.motionZ);
                } else {
                    var15 = this.posX + var6 + var8 * var11 * 0.7;
                    var17 = this.posZ + var8 - var6 * var11 * 0.7;
                    this.worldObj.spawnParticle("splash", var15, this.posY - 0.125, var17, this.motionX, this.motionY, this.motionZ);
                }
                ++var10;
            }
        }
        if (this.worldObj.isClient && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                var6 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                var8 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                var24 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                var26 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + var26 / (double)this.boatPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var6, var8, var24);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                var6 = this.posX + this.motionX;
                var8 = this.posY + this.motionY;
                var24 = this.posZ + this.motionZ;
                this.setPosition(var6, var8, var24);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
        } else {
            double var14;
            if (var2 < 1.0) {
                var6 = var2 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var6;
            } else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
                EntityLivingBase var20 = (EntityLivingBase)this.riddenByEntity;
                float var21 = this.riddenByEntity.rotationYaw + (- var20.moveStrafing) * 90.0f;
                this.motionX += (- Math.sin(var21 * 3.1415927f / 180.0f)) * this.speedMultiplier * (double)var20.moveForward * 0.05000000074505806;
                this.motionZ += Math.cos(var21 * 3.1415927f / 180.0f) * this.speedMultiplier * (double)var20.moveForward * 0.05000000074505806;
            }
            if ((var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) > 0.35) {
                var8 = 0.35 / var6;
                this.motionX *= var8;
                this.motionZ *= var8;
                var6 = 0.35;
            }
            if (var6 > var19 && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            } else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
            int var22 = 0;
            while (var22 < 4) {
                int var23 = MathHelper.floor_double(this.posX + ((double)(var22 % 2) - 0.5) * 0.8);
                var10 = MathHelper.floor_double(this.posZ + ((double)(var22 / 2) - 0.5) * 0.8);
                int var25 = 0;
                while (var25 < 2) {
                    int var12 = MathHelper.floor_double(this.posY) + var25;
                    Block var27 = this.worldObj.getBlock(var23, var12, var10);
                    if (var27 == Blocks.snow_layer) {
                        this.worldObj.setBlockToAir(var23, var12, var10);
                        this.isCollidedHorizontally = false;
                    } else if (var27 == Blocks.waterlily) {
                        this.worldObj.func_147480_a(var23, var12, var10, true);
                        this.isCollidedHorizontally = false;
                    }
                    ++var25;
                }
                ++var22;
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && var19 > 0.2) {
                if (!this.worldObj.isClient && !this.isDead) {
                    this.setDead();
                    var22 = 0;
                    while (var22 < 3) {
                        this.func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0f);
                        ++var22;
                    }
                    var22 = 0;
                    while (var22 < 2) {
                        this.func_145778_a(Items.stick, 1, 0.0f);
                        ++var22;
                    }
                }
            } else {
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
            this.rotationPitch = 0.0f;
            var8 = this.rotationYaw;
            var24 = this.prevPosX - this.posX;
            var26 = this.prevPosZ - this.posZ;
            if (var24 * var24 + var26 * var26 > 0.001) {
                var8 = (float)(Math.atan2(var26, var24) * 180.0 / 3.141592653589793);
            }
            if ((var14 = MathHelper.wrapAngleTo180_double(var8 - (double)this.rotationYaw)) > 20.0) {
                var14 = 20.0;
            }
            if (var14 < -20.0) {
                var14 = -20.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var14);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (!this.worldObj.isClient) {
                List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (var16 != null && !var16.isEmpty()) {
                    int var28 = 0;
                    while (var28 < var16.size()) {
                        Entity var18 = (Entity)var16.get(var28);
                        if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat) {
                            var18.applyEntityCollision(this);
                        }
                        ++var28;
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double var1 = Math.cos((double)this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            double var3 = Math.sin((double)this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
            return true;
        }
        if (!this.worldObj.isClient) {
            par1EntityPlayer.mountEntity(this);
        }
        return true;
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
        int var4 = MathHelper.floor_double(this.posX);
        int var5 = MathHelper.floor_double(this.posY);
        int var6 = MathHelper.floor_double(this.posZ);
        if (par3) {
            if (this.fallDistance > 3.0f) {
                this.fall(this.fallDistance);
                if (!this.worldObj.isClient && !this.isDead) {
                    this.setDead();
                    int var7 = 0;
                    while (var7 < 3) {
                        this.func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0f);
                        ++var7;
                    }
                    var7 = 0;
                    while (var7 < 2) {
                        this.func_145778_a(Items.stick, 1, 0.0f);
                        ++var7;
                    }
                }
                this.fallDistance = 0.0f;
            }
        } else if (this.worldObj.getBlock(var4, var5 - 1, var6).getMaterial() != Material.water && par1 < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - par1);
        }
    }

    public void setDamageTaken(float par1) {
        this.dataWatcher.updateObject(19, Float.valueOf(par1));
    }

    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    public void setTimeSinceHit(int par1) {
        this.dataWatcher.updateObject(17, par1);
    }

    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void setForwardDirection(int par1) {
        this.dataWatcher.updateObject(18, par1);
    }

    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setIsBoatEmpty(boolean par1) {
        this.isBoatEmpty = par1;
    }
}

