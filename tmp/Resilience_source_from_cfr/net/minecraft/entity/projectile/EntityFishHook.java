/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityFishHook
extends Entity {
    private static final List field_146039_d = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, 0), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10));
    private static final List field_146041_e = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.book), 1).func_150707_a());
    private static final List field_146036_f = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13));
    private int field_146037_g = -1;
    private int field_146048_h = -1;
    private int field_146050_i = -1;
    private Block field_146046_j;
    private boolean field_146051_au;
    public int field_146044_a;
    public EntityPlayer field_146042_b;
    private int field_146049_av;
    private int field_146047_aw;
    private int field_146045_ax;
    private int field_146040_ay;
    private int field_146038_az;
    private float field_146054_aA;
    public Entity field_146043_c;
    private int field_146055_aB;
    private double field_146056_aC;
    private double field_146057_aD;
    private double field_146058_aE;
    private double field_146059_aF;
    private double field_146060_aG;
    private double field_146061_aH;
    private double field_146052_aI;
    private double field_146053_aJ;
    private static final String __OBFID = "CL_00001663";

    public EntityFishHook(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }

    public EntityFishHook(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.ignoreFrustumCheck = true;
        this.field_146042_b = par8EntityPlayer;
        par8EntityPlayer.fishEntity = this;
    }

    public EntityFishHook(World par1World, EntityPlayer par2EntityPlayer) {
        super(par1World);
        this.ignoreFrustumCheck = true;
        this.field_146042_b = par2EntityPlayer;
        this.field_146042_b.fishEntity = this;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62 - (double)par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.posY -= 0.10000000149011612;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float var3 = 0.4f;
        this.motionX = (- MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = (- MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f)) * var3;
        this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
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

    public void func_146035_c(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
        float var9 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
        p_146035_1_ /= (double)var9;
        p_146035_3_ /= (double)var9;
        p_146035_5_ /= (double)var9;
        p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        this.motionX = p_146035_1_ *= (double)p_146035_7_;
        this.motionY = p_146035_3_ *= (double)p_146035_7_;
        this.motionZ = p_146035_5_ *= (double)p_146035_7_;
        float var10 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / 3.141592653589793);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_146035_3_, var10) * 180.0 / 3.141592653589793);
        this.field_146049_av = 0;
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.field_146056_aC = par1;
        this.field_146057_aD = par3;
        this.field_146058_aE = par5;
        this.field_146059_aF = par7;
        this.field_146060_aG = par8;
        this.field_146055_aB = par9;
        this.motionX = this.field_146061_aH;
        this.motionY = this.field_146052_aI;
        this.motionZ = this.field_146053_aJ;
    }

    @Override
    public void setVelocity(double par1, double par3, double par5) {
        this.field_146061_aH = this.motionX = par1;
        this.field_146052_aI = this.motionY = par3;
        this.field_146053_aJ = this.motionZ = par5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_146055_aB > 0) {
            double var27 = this.posX + (this.field_146056_aC - this.posX) / (double)this.field_146055_aB;
            double var28 = this.posY + (this.field_146057_aD - this.posY) / (double)this.field_146055_aB;
            double var29 = this.posZ + (this.field_146058_aE - this.posZ) / (double)this.field_146055_aB;
            double var7 = MathHelper.wrapAngleTo180_double(this.field_146059_aF - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_146055_aB);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.field_146060_aG - (double)this.rotationPitch) / (double)this.field_146055_aB);
            --this.field_146055_aB;
            this.setPosition(var27, var28, var29);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else {
            double var13;
            if (!this.worldObj.isClient) {
                ItemStack var1 = this.field_146042_b.getCurrentEquippedItem();
                if (this.field_146042_b.isDead || !this.field_146042_b.isEntityAlive() || var1 == null || var1.getItem() != Items.fishing_rod || this.getDistanceSqToEntity(this.field_146042_b) > 1024.0) {
                    this.setDead();
                    this.field_146042_b.fishEntity = null;
                    return;
                }
                if (this.field_146043_c != null) {
                    if (!this.field_146043_c.isDead) {
                        this.posX = this.field_146043_c.posX;
                        this.posY = this.field_146043_c.boundingBox.minY + (double)this.field_146043_c.height * 0.8;
                        this.posZ = this.field_146043_c.posZ;
                        return;
                    }
                    this.field_146043_c = null;
                }
            }
            if (this.field_146044_a > 0) {
                --this.field_146044_a;
            }
            if (this.field_146051_au) {
                if (this.worldObj.getBlock(this.field_146037_g, this.field_146048_h, this.field_146050_i) == this.field_146046_j) {
                    ++this.field_146049_av;
                    if (this.field_146049_av == 1200) {
                        this.setDead();
                    }
                    return;
                }
                this.field_146051_au = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.field_146049_av = 0;
                this.field_146047_aw = 0;
            } else {
                ++this.field_146047_aw;
            }
            Vec3 var26 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var26, var2);
            var26 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
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
                Entity var9 = (Entity)var5.get(var8);
                if (!(!var9.canBeCollidedWith() || var9 == this.field_146042_b && this.field_146047_aw < 5 || (var12 = (var11 = var9.boundingBox.expand(var10 = 0.3f, var10, var10)).calculateIntercept(var26, var2)) == null || (var13 = var26.distanceTo(var12.hitVec)) >= var6 && var6 != 0.0)) {
                    var4 = var9;
                    var6 = var13;
                }
                ++var8;
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
            if (var3 != null) {
                if (var3.entityHit != null) {
                    if (var3.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_146042_b), 0.0f)) {
                        this.field_146043_c = var3.entityHit;
                    }
                } else {
                    this.field_146051_au = true;
                }
            }
            if (!this.field_146051_au) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                float var30 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
                this.rotationPitch = (float)(Math.atan2(this.motionY, var30) * 180.0 / 3.141592653589793);
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
                float var31 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    var31 = 0.5f;
                }
                int var33 = 5;
                double var32 = 0.0;
                int var35 = 0;
                while (var35 < var33) {
                    double var14 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var35 + 0) / (double)var33 - 0.125 + 0.125;
                    double var16 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var35 + 1) / (double)var33 - 0.125 + 0.125;
                    AxisAlignedBB var18 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var14, this.boundingBox.minZ, this.boundingBox.maxX, var16, this.boundingBox.maxZ);
                    if (this.worldObj.isAABBInMaterial(var18, Material.water)) {
                        var32 += 1.0 / (double)var33;
                    }
                    ++var35;
                }
                if (!this.worldObj.isClient && var32 > 0.0) {
                    WorldServer var34 = (WorldServer)this.worldObj;
                    int var36 = 1;
                    if (this.rand.nextFloat() < 0.25f && this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
                        var36 = 2;
                    }
                    if (this.rand.nextFloat() < 0.5f && !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
                        --var36;
                    }
                    if (this.field_146045_ax > 0) {
                        --this.field_146045_ax;
                        if (this.field_146045_ax <= 0) {
                            this.field_146040_ay = 0;
                            this.field_146038_az = 0;
                        }
                    } else if (this.field_146038_az > 0) {
                        this.field_146038_az -= var36;
                        if (this.field_146038_az <= 0) {
                            this.motionY -= 0.20000000298023224;
                            this.playSound("random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                            float var15 = MathHelper.floor_double(this.boundingBox.minY);
                            var34.func_147487_a("bubble", this.posX, var15 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224);
                            var34.func_147487_a("wake", this.posX, var15 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224);
                            this.field_146045_ax = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
                        } else {
                            this.field_146054_aA = (float)((double)this.field_146054_aA + this.rand.nextGaussian() * 4.0);
                            float var15 = this.field_146054_aA * 0.017453292f;
                            float var37 = MathHelper.sin(var15);
                            float var17 = MathHelper.cos(var15);
                            double var38 = this.posX + (double)(var37 * (float)this.field_146038_az * 0.1f);
                            double var20 = (float)MathHelper.floor_double(this.boundingBox.minY) + 1.0f;
                            double var22 = this.posZ + (double)(var17 * (float)this.field_146038_az * 0.1f);
                            if (this.rand.nextFloat() < 0.15f) {
                                var34.func_147487_a("bubble", var38, var20 - 0.10000000149011612, var22, 1, var37, 0.1, var17, 0.0);
                            }
                            float var24 = var37 * 0.04f;
                            float var25 = var17 * 0.04f;
                            var34.func_147487_a("wake", var38, var20, var22, 0, var25, 0.01, - var24, 1.0);
                            var34.func_147487_a("wake", var38, var20, var22, 0, - var25, 0.01, var24, 1.0);
                        }
                    } else if (this.field_146040_ay > 0) {
                        this.field_146040_ay -= var36;
                        float var15 = 0.15f;
                        if (this.field_146040_ay < 20) {
                            var15 = (float)((double)var15 + (double)(20 - this.field_146040_ay) * 0.05);
                        } else if (this.field_146040_ay < 40) {
                            var15 = (float)((double)var15 + (double)(40 - this.field_146040_ay) * 0.02);
                        } else if (this.field_146040_ay < 60) {
                            var15 = (float)((double)var15 + (double)(60 - this.field_146040_ay) * 0.01);
                        }
                        if (this.rand.nextFloat() < var15) {
                            float var37 = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f) * 0.017453292f;
                            float var17 = MathHelper.randomFloatClamp(this.rand, 25.0f, 60.0f);
                            double var38 = this.posX + (double)(MathHelper.sin(var37) * var17 * 0.1f);
                            double var20 = (float)MathHelper.floor_double(this.boundingBox.minY) + 1.0f;
                            double var22 = this.posZ + (double)(MathHelper.cos(var37) * var17 * 0.1f);
                            var34.func_147487_a("splash", var38, var20, var22, 2 + this.rand.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                        }
                        if (this.field_146040_ay <= 0) {
                            this.field_146054_aA = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f);
                            this.field_146038_az = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
                        }
                    } else {
                        this.field_146040_ay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
                        this.field_146040_ay -= EnchantmentHelper.func_151387_h(this.field_146042_b) * 20 * 5;
                    }
                    if (this.field_146045_ax > 0) {
                        this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
                    }
                }
                var13 = var32 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var13;
                if (var32 > 0.0) {
                    var31 = (float)((double)var31 * 0.9);
                    this.motionY *= 0.8;
                }
                this.motionX *= (double)var31;
                this.motionY *= (double)var31;
                this.motionZ *= (double)var31;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.field_146037_g);
        par1NBTTagCompound.setShort("yTile", (short)this.field_146048_h);
        par1NBTTagCompound.setShort("zTile", (short)this.field_146050_i);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_146046_j));
        par1NBTTagCompound.setByte("shake", (byte)this.field_146044_a);
        par1NBTTagCompound.setByte("inGround", this.field_146051_au ? 1 : 0);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_146037_g = par1NBTTagCompound.getShort("xTile");
        this.field_146048_h = par1NBTTagCompound.getShort("yTile");
        this.field_146050_i = par1NBTTagCompound.getShort("zTile");
        this.field_146046_j = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
        this.field_146044_a = par1NBTTagCompound.getByte("shake") & 255;
        this.field_146051_au = par1NBTTagCompound.getByte("inGround") == 1;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public int func_146034_e() {
        if (this.worldObj.isClient) {
            return 0;
        }
        int var1 = 0;
        if (this.field_146043_c != null) {
            double var2 = this.field_146042_b.posX - this.posX;
            double var4 = this.field_146042_b.posY - this.posY;
            double var6 = this.field_146042_b.posZ - this.posZ;
            double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
            double var10 = 0.1;
            this.field_146043_c.motionX += var2 * var10;
            this.field_146043_c.motionY += var4 * var10 + (double)MathHelper.sqrt_double(var8) * 0.08;
            this.field_146043_c.motionZ += var6 * var10;
            var1 = 3;
        } else if (this.field_146045_ax > 0) {
            EntityItem var13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.func_146033_f());
            double var3 = this.field_146042_b.posX - this.posX;
            double var5 = this.field_146042_b.posY - this.posY;
            double var7 = this.field_146042_b.posZ - this.posZ;
            double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 0.1;
            var13.motionX = var3 * var11;
            var13.motionY = var5 * var11 + (double)MathHelper.sqrt_double(var9) * 0.08;
            var13.motionZ = var7 * var11;
            this.worldObj.spawnEntityInWorld(var13);
            this.field_146042_b.worldObj.spawnEntityInWorld(new EntityXPOrb(this.field_146042_b.worldObj, this.field_146042_b.posX, this.field_146042_b.posY + 0.5, this.field_146042_b.posZ + 0.5, this.rand.nextInt(6) + 1));
            var1 = 1;
        }
        if (this.field_146051_au) {
            var1 = 2;
        }
        this.setDead();
        this.field_146042_b.fishEntity = null;
        return var1;
    }

    private ItemStack func_146033_f() {
        float var1 = this.worldObj.rand.nextFloat();
        int var2 = EnchantmentHelper.func_151386_g(this.field_146042_b);
        int var3 = EnchantmentHelper.func_151387_h(this.field_146042_b);
        float var4 = 0.1f - (float)var2 * 0.025f - (float)var3 * 0.01f;
        float var5 = 0.05f + (float)var2 * 0.01f - (float)var3 * 0.01f;
        var4 = MathHelper.clamp_float(var4, 0.0f, 1.0f);
        var5 = MathHelper.clamp_float(var5, 0.0f, 1.0f);
        if (var1 < var4) {
            this.field_146042_b.addStat(StatList.field_151183_A, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146039_d)).func_150708_a(this.rand);
        }
        if ((var1 -= var4) < var5) {
            this.field_146042_b.addStat(StatList.field_151184_B, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146041_e)).func_150708_a(this.rand);
        }
        float var10000 = var1 - var5;
        this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146036_f)).func_150708_a(this.rand);
    }

    @Override
    public void setDead() {
        super.setDead();
        if (this.field_146042_b != null) {
            this.field_146042_b.fishEntity = null;
        }
    }
}

