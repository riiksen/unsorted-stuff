/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart
extends Entity {
    private boolean isInReverse;
    private String entityName;
    private static final int[][][] matrix;
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID = "CL_00001670";

    static {
        int[][][] arrarrn = new int[10][][];
        int[][] arrarrn2 = new int[2][];
        int[] arrn = new int[3];
        arrn[2] = -1;
        arrarrn2[0] = arrn;
        int[] arrn2 = new int[3];
        arrn2[2] = 1;
        arrarrn2[1] = arrn2;
        arrarrn[0] = arrarrn2;
        int[][] arrarrn3 = new int[2][];
        int[] arrn3 = new int[3];
        arrn3[0] = -1;
        arrarrn3[0] = arrn3;
        int[] arrn4 = new int[3];
        arrn4[0] = 1;
        arrarrn3[1] = arrn4;
        arrarrn[1] = arrarrn3;
        int[][] arrarrn4 = new int[2][];
        int[] arrn5 = new int[3];
        arrn5[0] = -1;
        arrn5[1] = -1;
        arrarrn4[0] = arrn5;
        int[] arrn6 = new int[3];
        arrn6[0] = 1;
        arrarrn4[1] = arrn6;
        arrarrn[2] = arrarrn4;
        int[][] arrarrn5 = new int[2][];
        int[] arrn7 = new int[3];
        arrn7[0] = -1;
        arrarrn5[0] = arrn7;
        int[] arrn8 = new int[3];
        arrn8[0] = 1;
        arrn8[1] = -1;
        arrarrn5[1] = arrn8;
        arrarrn[3] = arrarrn5;
        int[][] arrarrn6 = new int[2][];
        int[] arrn9 = new int[3];
        arrn9[2] = -1;
        arrarrn6[0] = arrn9;
        int[] arrn10 = new int[3];
        arrn10[1] = -1;
        arrn10[2] = 1;
        arrarrn6[1] = arrn10;
        arrarrn[4] = arrarrn6;
        int[][] arrarrn7 = new int[2][];
        int[] arrn11 = new int[3];
        arrn11[1] = -1;
        arrn11[2] = -1;
        arrarrn7[0] = arrn11;
        int[] arrn12 = new int[3];
        arrn12[2] = 1;
        arrarrn7[1] = arrn12;
        arrarrn[5] = arrarrn7;
        int[][] arrarrn8 = new int[2][];
        int[] arrn13 = new int[3];
        arrn13[2] = 1;
        arrarrn8[0] = arrn13;
        int[] arrn14 = new int[3];
        arrn14[0] = 1;
        arrarrn8[1] = arrn14;
        arrarrn[6] = arrarrn8;
        int[][] arrarrn9 = new int[2][];
        int[] arrn15 = new int[3];
        arrn15[2] = 1;
        arrarrn9[0] = arrn15;
        int[] arrn16 = new int[3];
        arrn16[0] = -1;
        arrarrn9[1] = arrn16;
        arrarrn[7] = arrarrn9;
        int[][] arrarrn10 = new int[2][];
        int[] arrn17 = new int[3];
        arrn17[2] = -1;
        arrarrn10[0] = arrn17;
        int[] arrn18 = new int[3];
        arrn18[0] = -1;
        arrarrn10[1] = arrn18;
        arrarrn[8] = arrarrn10;
        int[][] arrarrn11 = new int[2][];
        int[] arrn19 = new int[3];
        arrn19[2] = -1;
        arrarrn11[0] = arrn19;
        int[] arrn20 = new int[3];
        arrn20[0] = 1;
        arrarrn11[1] = arrn20;
        arrarrn[9] = arrarrn11;
        matrix = arrarrn;
    }

    public EntityMinecart(World par1World) {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
        this.yOffset = this.height / 2.0f;
    }

    public static EntityMinecart createMinecart(World par0World, double par1, double par3, double par5, int par7) {
        switch (par7) {
            case 1: {
                return new EntityMinecartChest(par0World, par1, par3, par5);
            }
            case 2: {
                return new EntityMinecartFurnace(par0World, par1, par3, par5);
            }
            case 3: {
                return new EntityMinecartTNT(par0World, par1, par3, par5);
            }
            case 4: {
                return new EntityMinecartMobSpawner(par0World, par1, par3, par5);
            }
            case 5: {
                return new EntityMinecartHopper(par0World, par1, par3, par5);
            }
            case 6: {
                return new EntityMinecartCommandBlock(par0World, par1, par3, par5);
            }
        }
        return new EntityMinecartEmpty(par0World, par1, par3, par5);
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
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, Byte.valueOf(0));
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return par1Entity.canBePushed() ? par1Entity.boundingBox : null;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityMinecart(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4, par6);
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
        if (!this.worldObj.isClient && !this.isDead) {
            boolean var3;
            if (this.isEntityInvulnerable()) {
                return false;
            }
            this.setRollingDirection(- this.getRollingDirection());
            this.setRollingAmplitude(10);
            this.setBeenAttacked();
            this.setDamage(this.getDamage() + par2 * 10.0f);
            boolean bl = var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;
            if (var3 || this.getDamage() > 40.0f) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                if (var3 && !this.isInventoryNameLocalized()) {
                    this.setDead();
                } else {
                    this.killMinecart(par1DamageSource);
                }
            }
            return true;
        }
        return true;
    }

    public void killMinecart(DamageSource par1DamageSource) {
        this.setDead();
        ItemStack var2 = new ItemStack(Items.minecart, 1);
        if (this.entityName != null) {
            var2.setStackDisplayName(this.entityName);
        }
        this.entityDropItem(var2, 0.0f);
    }

    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(- this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setDead() {
        super.setDead();
    }

    @Override
    public void onUpdate() {
        int var2;
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isClient && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            MinecraftServer var1 = ((WorldServer)this.worldObj).func_73046_m();
            var2 = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (var1.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= var2) {
                        this.portalCounter = var2;
                        this.timeUntilPortal = this.getPortalCooldown();
                        int var3 = this.worldObj.provider.dimensionId == -1 ? 0 : -1;
                        this.travelToDimension(var3);
                    }
                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.worldObj.isClient) {
            if (this.turnProgress > 0) {
                double var19 = this.posX + (this.minecartX - this.posX) / (double)this.turnProgress;
                double var21 = this.posY + (this.minecartY - this.posY) / (double)this.turnProgress;
                double var5 = this.posZ + (this.minecartZ - this.posZ) / (double)this.turnProgress;
                double var7 = MathHelper.wrapAngleTo180_double(this.minecartYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.turnProgress);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.minecartPitch - (double)this.rotationPitch) / (double)this.turnProgress);
                --this.turnProgress;
                this.setPosition(var19, var21, var5);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        } else {
            int var20;
            double var13;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            int var18 = MathHelper.floor_double(this.posX);
            if (BlockRailBase.func_150049_b_(this.worldObj, var18, (var2 = MathHelper.floor_double(this.posY)) - 1, var20 = MathHelper.floor_double(this.posZ))) {
                --var2;
            }
            double var4 = 0.4;
            double var6 = 0.0078125;
            Block var8 = this.worldObj.getBlock(var18, var2, var20);
            if (BlockRailBase.func_150051_a(var8)) {
                int var9 = this.worldObj.getBlockMetadata(var18, var2, var20);
                this.func_145821_a(var18, var2, var20, var4, var6, var8, var9);
                if (var8 == Blocks.activator_rail) {
                    this.onActivatorRailPass(var18, var2, var20, (var9 & 8) != 0);
                }
            } else {
                this.func_94088_b(var4);
            }
            this.func_145775_I();
            this.rotationPitch = 0.0f;
            double var22 = this.prevPosX - this.posX;
            double var11 = this.prevPosZ - this.posZ;
            if (var22 * var22 + var11 * var11 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(var11, var22) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            if ((var13 = (double)MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw)) < -170.0 || var13 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List var15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
            if (var15 != null && !var15.isEmpty()) {
                int var16 = 0;
                while (var16 < var15.size()) {
                    Entity var17 = (Entity)var15.get(var16);
                    if (var17 != this.riddenByEntity && var17.canBePushed() && var17 instanceof EntityMinecart) {
                        var17.applyEntityCollision(this);
                    }
                    ++var16;
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
        }
    }

    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {
    }

    protected void func_94088_b(double par1) {
        if (this.motionX < - par1) {
            this.motionX = - par1;
        }
        if (this.motionX > par1) {
            this.motionX = par1;
        }
        if (this.motionZ < - par1) {
            this.motionZ = - par1;
        }
        if (this.motionZ > par1) {
            this.motionZ = par1;
        }
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }

    protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_) {
        double var26;
        double var24;
        double var34;
        double var36;
        double var22;
        double var30;
        double var28;
        this.fallDistance = 0.0f;
        Vec3 var10 = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = p_145821_2_;
        boolean var11 = false;
        boolean var12 = false;
        if (p_145821_8_ == Blocks.golden_rail) {
            var11 = (p_145821_9_ & 8) != 0;
            boolean bl = var12 = !var11;
        }
        if (((BlockRailBase)p_145821_8_).func_150050_e()) {
            p_145821_9_ &= 7;
        }
        if (p_145821_9_ >= 2 && p_145821_9_ <= 5) {
            this.posY = p_145821_2_ + 1;
        }
        if (p_145821_9_ == 2) {
            this.motionX -= p_145821_6_;
        }
        if (p_145821_9_ == 3) {
            this.motionX += p_145821_6_;
        }
        if (p_145821_9_ == 4) {
            this.motionZ += p_145821_6_;
        }
        if (p_145821_9_ == 5) {
            this.motionZ -= p_145821_6_;
        }
        int[][] var13 = matrix[p_145821_9_];
        double var14 = var13[1][0] - var13[0][0];
        double var16 = var13[1][2] - var13[0][2];
        double var18 = Math.sqrt(var14 * var14 + var16 * var16);
        double var20 = this.motionX * var14 + this.motionZ * var16;
        if (var20 < 0.0) {
            var14 = - var14;
            var16 = - var16;
        }
        if ((var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) > 2.0) {
            var22 = 2.0;
        }
        this.motionX = var22 * var14 / var18;
        this.motionZ = var22 * var16 / var18;
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && (var24 = (double)((EntityLivingBase)this.riddenByEntity).moveForward) > 0.0) {
            var26 = - Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            var28 = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            var30 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (var30 < 0.01) {
                this.motionX += var26 * 0.1;
                this.motionZ += var28 * 0.1;
                var12 = false;
            }
        }
        if (var12) {
            var24 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var24 < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
            } else {
                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }
        var24 = 0.0;
        var26 = (double)p_145821_1_ + 0.5 + (double)var13[0][0] * 0.5;
        var28 = (double)p_145821_3_ + 0.5 + (double)var13[0][2] * 0.5;
        var30 = (double)p_145821_1_ + 0.5 + (double)var13[1][0] * 0.5;
        double var32 = (double)p_145821_3_ + 0.5 + (double)var13[1][2] * 0.5;
        var14 = var30 - var26;
        var16 = var32 - var28;
        if (var14 == 0.0) {
            this.posX = (double)p_145821_1_ + 0.5;
            var24 = this.posZ - (double)p_145821_3_;
        } else if (var16 == 0.0) {
            this.posZ = (double)p_145821_3_ + 0.5;
            var24 = this.posX - (double)p_145821_1_;
        } else {
            var34 = this.posX - var26;
            var36 = this.posZ - var28;
            var24 = (var34 * var14 + var36 * var16) * 2.0;
        }
        this.posX = var26 + var14 * var24;
        this.posZ = var28 + var16 * var24;
        this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
        var34 = this.motionX;
        var36 = this.motionZ;
        if (this.riddenByEntity != null) {
            var34 *= 0.75;
            var36 *= 0.75;
        }
        if (var34 < - p_145821_4_) {
            var34 = - p_145821_4_;
        }
        if (var34 > p_145821_4_) {
            var34 = p_145821_4_;
        }
        if (var36 < - p_145821_4_) {
            var36 = - p_145821_4_;
        }
        if (var36 > p_145821_4_) {
            var36 = p_145821_4_;
        }
        this.moveEntity(var34, 0.0, var36);
        if (var13[0][1] != 0 && MathHelper.floor_double(this.posX) - p_145821_1_ == var13[0][0] && MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[0][2]) {
            this.setPosition(this.posX, this.posY + (double)var13[0][1], this.posZ);
        } else if (var13[1][1] != 0 && MathHelper.floor_double(this.posX) - p_145821_1_ == var13[1][0] && MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[1][2]) {
            this.setPosition(this.posX, this.posY + (double)var13[1][1], this.posZ);
        }
        this.applyDrag();
        Vec3 var38 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (var38 != null && var10 != null) {
            double var39 = (var10.yCoord - var38.yCoord) * 0.05;
            var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var22 > 0.0) {
                this.motionX = this.motionX / var22 * (var22 + var39);
                this.motionZ = this.motionZ / var22 * (var22 + var39);
            }
            this.setPosition(this.posX, var38.yCoord, this.posZ);
        }
        int var45 = MathHelper.floor_double(this.posX);
        int var40 = MathHelper.floor_double(this.posZ);
        if (var45 != p_145821_1_ || var40 != p_145821_3_) {
            var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = var22 * (double)(var45 - p_145821_1_);
            this.motionZ = var22 * (double)(var40 - p_145821_3_);
        }
        if (var11) {
            double var41 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var41 > 0.01) {
                double var43 = 0.06;
                this.motionX += this.motionX / var41 * var43;
                this.motionZ += this.motionZ / var41 * var43;
            } else if (p_145821_9_ == 1) {
                if (this.worldObj.getBlock(p_145821_1_ - 1, p_145821_2_, p_145821_3_).isNormalCube()) {
                    this.motionX = 0.02;
                } else if (this.worldObj.getBlock(p_145821_1_ + 1, p_145821_2_, p_145821_3_).isNormalCube()) {
                    this.motionX = -0.02;
                }
            } else if (p_145821_9_ == 0) {
                if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ - 1).isNormalCube()) {
                    this.motionZ = 0.02;
                } else if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ + 1).isNormalCube()) {
                    this.motionZ = -0.02;
                }
            }
        }
    }

    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
        } else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }

    public Vec3 func_70495_a(double par1, double par3, double par5, double par7) {
        int var11;
        int var10;
        Block var12;
        int var9 = MathHelper.floor_double(par1);
        if (BlockRailBase.func_150049_b_(this.worldObj, var9, (var10 = MathHelper.floor_double(par3)) - 1, var11 = MathHelper.floor_double(par5))) {
            --var10;
        }
        if (!BlockRailBase.func_150051_a(var12 = this.worldObj.getBlock(var9, var10, var11))) {
            return null;
        }
        int var13 = this.worldObj.getBlockMetadata(var9, var10, var11);
        if (((BlockRailBase)var12).func_150050_e()) {
            var13 &= 7;
        }
        par3 = var10;
        if (var13 >= 2 && var13 <= 5) {
            par3 = var10 + 1;
        }
        int[][] var14 = matrix[var13];
        double var15 = var14[1][0] - var14[0][0];
        double var17 = var14[1][2] - var14[0][2];
        double var19 = Math.sqrt(var15 * var15 + var17 * var17);
        if (var14[0][1] != 0 && MathHelper.floor_double(par1 += (var15 /= var19) * par7) - var9 == var14[0][0] && MathHelper.floor_double(par5 += (var17 /= var19) * par7) - var11 == var14[0][2]) {
            par3 += (double)var14[0][1];
        } else if (var14[1][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[1][0] && MathHelper.floor_double(par5) - var11 == var14[1][2]) {
            par3 += (double)var14[1][1];
        }
        return this.func_70489_a(par1, par3, par5);
    }

    public Vec3 func_70489_a(double par1, double par3, double par5) {
        int var8;
        int var9;
        Block var10;
        int var7 = MathHelper.floor_double(par1);
        if (BlockRailBase.func_150049_b_(this.worldObj, var7, (var8 = MathHelper.floor_double(par3)) - 1, var9 = MathHelper.floor_double(par5))) {
            --var8;
        }
        if (BlockRailBase.func_150051_a(var10 = this.worldObj.getBlock(var7, var8, var9))) {
            int var11 = this.worldObj.getBlockMetadata(var7, var8, var9);
            par3 = var8;
            if (((BlockRailBase)var10).func_150050_e()) {
                var11 &= 7;
            }
            if (var11 >= 2 && var11 <= 5) {
                par3 = var8 + 1;
            }
            int[][] var12 = matrix[var11];
            double var13 = 0.0;
            double var15 = (double)var7 + 0.5 + (double)var12[0][0] * 0.5;
            double var17 = (double)var8 + 0.5 + (double)var12[0][1] * 0.5;
            double var19 = (double)var9 + 0.5 + (double)var12[0][2] * 0.5;
            double var21 = (double)var7 + 0.5 + (double)var12[1][0] * 0.5;
            double var23 = (double)var8 + 0.5 + (double)var12[1][1] * 0.5;
            double var25 = (double)var9 + 0.5 + (double)var12[1][2] * 0.5;
            double var27 = var21 - var15;
            double var29 = (var23 - var17) * 2.0;
            double var31 = var25 - var19;
            if (var27 == 0.0) {
                par1 = (double)var7 + 0.5;
                var13 = par5 - (double)var9;
            } else if (var31 == 0.0) {
                par5 = (double)var9 + 0.5;
                var13 = par1 - (double)var7;
            } else {
                double var33 = par1 - var15;
                double var35 = par5 - var19;
                var13 = (var33 * var27 + var35 * var31) * 2.0;
            }
            par1 = var15 + var27 * var13;
            par3 = var17 + var29 * var13;
            par5 = var19 + var31 * var13;
            if (var29 < 0.0) {
                par3 += 1.0;
            }
            if (var29 > 0.0) {
                par3 += 0.5;
            }
            return this.worldObj.getWorldVec3Pool().getVecFromPool(par1, par3, par5);
        }
        return null;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.getBoolean("CustomDisplayTile")) {
            this.func_145819_k(par1NBTTagCompound.getInteger("DisplayTile"));
            this.setDisplayTileData(par1NBTTagCompound.getInteger("DisplayData"));
            this.setDisplayTileOffset(par1NBTTagCompound.getInteger("DisplayOffset"));
        }
        if (par1NBTTagCompound.func_150297_b("CustomName", 8) && par1NBTTagCompound.getString("CustomName").length() > 0) {
            this.entityName = par1NBTTagCompound.getString("CustomName");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.hasDisplayTile()) {
            par1NBTTagCompound.setBoolean("CustomDisplayTile", true);
            par1NBTTagCompound.setInteger("DisplayTile", this.func_145820_n().getMaterial() == Material.air ? 0 : Block.getIdFromBlock(this.func_145820_n()));
            par1NBTTagCompound.setInteger("DisplayData", this.getDisplayTileData());
            par1NBTTagCompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }
        if (this.entityName != null && this.entityName.length() > 0) {
            par1NBTTagCompound.setString("CustomName", this.entityName);
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public void applyEntityCollision(Entity par1Entity) {
        if (!this.worldObj.isClient && par1Entity != this.riddenByEntity) {
            double var2;
            double var6;
            double var4;
            if (par1Entity instanceof EntityLivingBase && !(par1Entity instanceof EntityPlayer) && !(par1Entity instanceof EntityIronGolem) && this.getMinecartType() == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && par1Entity.ridingEntity == null) {
                par1Entity.mountEntity(this);
            }
            if ((var6 = (var2 = par1Entity.posX - this.posX) * var2 + (var4 = par1Entity.posZ - this.posZ) * var4) >= 9.999999747378752E-5) {
                var6 = MathHelper.sqrt_double(var6);
                var2 /= var6;
                var4 /= var6;
                double var8 = 1.0 / var6;
                if (var8 > 1.0) {
                    var8 = 1.0;
                }
                var2 *= var8;
                var4 *= var8;
                var2 *= 0.10000000149011612;
                var4 *= 0.10000000149011612;
                var2 *= (double)(1.0f - this.entityCollisionReduction);
                var4 *= (double)(1.0f - this.entityCollisionReduction);
                var2 *= 0.5;
                var4 *= 0.5;
                if (par1Entity instanceof EntityMinecart) {
                    Vec3 var15;
                    double var10 = par1Entity.posX - this.posX;
                    double var12 = par1Entity.posZ - this.posZ;
                    Vec3 var14 = this.worldObj.getWorldVec3Pool().getVecFromPool(var10, 0.0, var12).normalize();
                    double var16 = Math.abs(var14.dotProduct(var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize()));
                    if (var16 < 0.800000011920929) {
                        return;
                    }
                    double var18 = par1Entity.motionX + this.motionX;
                    double var20 = par1Entity.motionZ + this.motionZ;
                    if (((EntityMinecart)par1Entity).getMinecartType() == 2 && this.getMinecartType() != 2) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(par1Entity.motionX - var2, 0.0, par1Entity.motionZ - var4);
                        par1Entity.motionX *= 0.949999988079071;
                        par1Entity.motionZ *= 0.949999988079071;
                    } else if (((EntityMinecart)par1Entity).getMinecartType() != 2 && this.getMinecartType() == 2) {
                        par1Entity.motionX *= 0.20000000298023224;
                        par1Entity.motionZ *= 0.20000000298023224;
                        par1Entity.addVelocity(this.motionX + var2, 0.0, this.motionZ + var4);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                    } else {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity((var18 /= 2.0) - var2, 0.0, (var20 /= 2.0) - var4);
                        par1Entity.motionX *= 0.20000000298023224;
                        par1Entity.motionZ *= 0.20000000298023224;
                        par1Entity.addVelocity(var18 + var2, 0.0, var20 + var4);
                    }
                } else {
                    this.addVelocity(- var2, 0.0, - var4);
                    par1Entity.addVelocity(var2 / 4.0, 0.0, var4 / 4.0);
                }
            }
        }
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.minecartX = par1;
        this.minecartY = par3;
        this.minecartZ = par5;
        this.minecartYaw = par7;
        this.minecartPitch = par8;
        this.turnProgress = par9 + 2;
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

    public void setDamage(float par1) {
        this.dataWatcher.updateObject(19, Float.valueOf(par1));
    }

    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    public void setRollingAmplitude(int par1) {
        this.dataWatcher.updateObject(17, par1);
    }

    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void setRollingDirection(int par1) {
        this.dataWatcher.updateObject(18, par1);
    }

    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public abstract int getMinecartType();

    public Block func_145820_n() {
        if (!this.hasDisplayTile()) {
            return this.func_145817_o();
        }
        int var1 = this.getDataWatcher().getWatchableObjectInt(20) & 65535;
        return Block.getBlockById(var1);
    }

    public Block func_145817_o() {
        return Blocks.air;
    }

    public int getDisplayTileData() {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTileData() : this.getDataWatcher().getWatchableObjectInt(20) >> 16;
    }

    public int getDefaultDisplayTileData() {
        return 0;
    }

    public int getDisplayTileOffset() {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.getDataWatcher().getWatchableObjectInt(21);
    }

    public int getDefaultDisplayTileOffset() {
        return 6;
    }

    public void func_145819_k(int p_145819_1_) {
        this.getDataWatcher().updateObject(20, p_145819_1_ & 65535 | this.getDisplayTileData() << 16);
        this.setHasDisplayTile(true);
    }

    public void setDisplayTileData(int par1) {
        this.getDataWatcher().updateObject(20, Block.getIdFromBlock(this.func_145820_n()) & 65535 | par1 << 16);
        this.setHasDisplayTile(true);
    }

    public void setDisplayTileOffset(int par1) {
        this.getDataWatcher().updateObject(21, par1);
        this.setHasDisplayTile(true);
    }

    public boolean hasDisplayTile() {
        if (this.getDataWatcher().getWatchableObjectByte(22) == 1) {
            return true;
        }
        return false;
    }

    public void setHasDisplayTile(boolean par1) {
        this.getDataWatcher().updateObject(22, Byte.valueOf(par1 ? 1 : 0));
    }

    public void setMinecartName(String par1Str) {
        this.entityName = par1Str;
    }

    @Override
    public String getCommandSenderName() {
        return this.entityName != null ? this.entityName : super.getCommandSenderName();
    }

    public boolean isInventoryNameLocalized() {
        if (this.entityName != null) {
            return true;
        }
        return false;
    }

    public String func_95999_t() {
        return this.entityName;
    }
}

