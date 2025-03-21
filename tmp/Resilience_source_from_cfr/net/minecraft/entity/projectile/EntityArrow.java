/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityArrow
extends Entity
implements IProjectile {
    private int field_145791_d = -1;
    private int field_145792_e = -1;
    private int field_145789_f = -1;
    private Block field_145790_g;
    private int inData;
    private boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0;
    private int knockbackStrength;
    private static final String __OBFID = "CL_00001715";

    public EntityArrow(World par1World) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }

    public EntityArrow(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }

    public EntityArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLivingBase;
        if (par2EntityLivingBase instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.posY = par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() - 0.10000000149011612;
        double var6 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
        double var8 = par3EntityLivingBase.boundingBox.minY + (double)(par3EntityLivingBase.height / 3.0f) - this.posY;
        double var10 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
        double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
        if (var12 >= 1.0E-7) {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0 / 3.141592653589793) - 90.0f;
            float var15 = (float)(- Math.atan2(var8, var12) * 180.0 / 3.141592653589793);
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setLocationAndAngles(par2EntityLivingBase.posX + var16, this.posY, par2EntityLivingBase.posZ + var18, var14, var15);
            this.yOffset = 0.0f;
            float var20 = (float)var12 * 0.2f;
            this.setThrowableHeading(var6, var8 + (double)var20, var10, par4, par5);
        }
    }

    public EntityArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLivingBase;
        if (par2EntityLivingBase instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.posY -= 0.10000000149011612;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = (- MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = - MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5f, 1.0f);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf(0));
    }

    @Override
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * (double)par8;
        par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * (double)par8;
        par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * (double)par8;
        this.motionX = par1 *= (double)par7;
        this.motionY = par3 *= (double)par7;
        this.motionZ = par5 *= (double)par7;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0 / 3.141592653589793);
        this.ticksInGround = 0;
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
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
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    @Override
    public void onUpdate() {
        Block var16;
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
        }
        if ((var16 = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f)).getMaterial() != Material.air) {
            var16.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (var2 != null && var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            int var18 = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (var16 == this.field_145790_g && var18 == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            float var11;
            ++this.ticksInAir;
            Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var4 = this.worldObj.func_147447_a(var17, var3, false, true, false);
            var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var4 != null) {
                var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
            }
            Entity var5 = null;
            List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var7 = 0.0;
            int var9 = 0;
            while (var9 < var6.size()) {
                double var14;
                MovingObjectPosition var13;
                AxisAlignedBB var12;
                Entity var10 = (Entity)var6.get(var9);
                if (!(!var10.canBeCollidedWith() || var10 == this.shootingEntity && this.ticksInAir < 5 || (var13 = (var12 = var10.boundingBox.expand(var11 = 0.3f, var11, var11)).calculateIntercept(var17, var3)) == null || (var14 = var17.distanceTo(var13.hitVec)) >= var7 && var7 != 0.0)) {
                    var5 = var10;
                    var7 = var14;
                }
                ++var9;
            }
            if (var5 != null) {
                var4 = new MovingObjectPosition(var5);
            }
            if (var4 != null && var4.entityHit != null && var4.entityHit instanceof EntityPlayer) {
                EntityPlayer var20 = (EntityPlayer)var4.entityHit;
                if (var20.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(var20)) {
                    var4 = null;
                }
            }
            if (var4 != null) {
                if (var4.entityHit != null) {
                    float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var23 = MathHelper.ceiling_double_int((double)var19 * this.damage);
                    if (this.getIsCritical()) {
                        var23 += this.rand.nextInt(var23 / 2 + 2);
                    }
                    DamageSource var21 = null;
                    var21 = this.shootingEntity == null ? DamageSource.causeArrowDamage(this, this) : DamageSource.causeArrowDamage(this, this.shootingEntity);
                    if (this.isBurning() && !(var4.entityHit instanceof EntityEnderman)) {
                        var4.entityHit.setFire(5);
                    }
                    if (var4.entityHit.attackEntityFrom(var21, var23)) {
                        if (var4.entityHit instanceof EntityLivingBase) {
                            float var26;
                            EntityLivingBase var24 = (EntityLivingBase)var4.entityHit;
                            if (!this.worldObj.isClient) {
                                var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
                            }
                            if (this.knockbackStrength > 0 && (var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)) > 0.0f) {
                                var4.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579 / (double)var26, 0.1, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579 / (double)var26);
                            }
                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(var24, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, var24);
                            }
                            if (this.shootingEntity != null && var4.entityHit != this.shootingEntity && var4.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0f));
                            }
                        }
                        this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                        if (!(var4.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    } else {
                        this.motionX *= -0.10000000149011612;
                        this.motionY *= -0.10000000149011612;
                        this.motionZ *= -0.10000000149011612;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.field_145791_d = var4.blockX;
                    this.field_145792_e = var4.blockY;
                    this.field_145789_f = var4.blockZ;
                    this.field_145790_g = var16;
                    this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.motionX = (float)(var4.hitVec.xCoord - this.posX);
                    this.motionY = (float)(var4.hitVec.yCoord - this.posY);
                    this.motionZ = (float)(var4.hitVec.zCoord - this.posZ);
                    float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)var19 * 0.05000000074505806;
                    this.posY -= this.motionY / (double)var19 * 0.05000000074505806;
                    this.posZ -= this.motionZ / (double)var19 * 0.05000000074505806;
                    this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                    if (this.field_145790_g.getMaterial() != Material.air) {
                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
                    }
                }
            }
            if (this.getIsCritical()) {
                var9 = 0;
                while (var9 < 4) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)var9 / 4.0, this.posY + this.motionY * (double)var9 / 4.0, this.posZ + this.motionZ * (double)var9 / 4.0, - this.motionX, - this.motionY + 0.2, - this.motionZ);
                    ++var9;
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float)(Math.atan2(this.motionY, var19) * 180.0 / 3.141592653589793);
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
            float var22 = 0.99f;
            var11 = 0.05f;
            if (this.isInWater()) {
                int var25 = 0;
                while (var25 < 4) {
                    float var26 = 0.25f;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var26, this.posY - this.motionY * (double)var26, this.posZ - this.motionZ * (double)var26, this.motionX, this.motionY, this.motionZ);
                    ++var25;
                }
                var22 = 0.8f;
            }
            if (this.isWet()) {
                this.extinguish();
            }
            this.motionX *= (double)var22;
            this.motionY *= (double)var22;
            this.motionZ *= (double)var22;
            this.motionY -= (double)var11;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.field_145791_d);
        par1NBTTagCompound.setShort("yTile", (short)this.field_145792_e);
        par1NBTTagCompound.setShort("zTile", (short)this.field_145789_f);
        par1NBTTagCompound.setShort("life", (short)this.ticksInGround);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
        par1NBTTagCompound.setByte("inData", (byte)this.inData);
        par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
        par1NBTTagCompound.setByte("inGround", this.inGround ? 1 : 0);
        par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
        par1NBTTagCompound.setDouble("damage", this.damage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_145791_d = par1NBTTagCompound.getShort("xTile");
        this.field_145792_e = par1NBTTagCompound.getShort("yTile");
        this.field_145789_f = par1NBTTagCompound.getShort("zTile");
        this.ticksInGround = par1NBTTagCompound.getShort("life");
        this.field_145790_g = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
        this.inData = par1NBTTagCompound.getByte("inData") & 255;
        this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
        boolean bl = this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
        if (par1NBTTagCompound.func_150297_b("damage", 99)) {
            this.damage = par1NBTTagCompound.getDouble("damage");
        }
        if (par1NBTTagCompound.func_150297_b("pickup", 99)) {
            this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
        } else if (par1NBTTagCompound.func_150297_b("player", 99)) {
            this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isClient && this.inGround && this.arrowShake <= 0) {
            boolean var2;
            boolean bl = var2 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;
            if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
                var2 = false;
            }
            if (var2) {
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public void setDamage(double par1) {
        this.damage = par1;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setKnockbackStrength(int par1) {
        this.knockbackStrength = par1;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    public void setIsCritical(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    public boolean getIsCritical() {
        byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        if ((var1 & 1) != 0) {
            return true;
        }
        return false;
    }
}

