/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

public abstract class Entity {
    private static int nextEntityID;
    private int field_145783_c = nextEntityID++;
    public double renderDistanceWeight = 1.0;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public boolean forceSpawn;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    protected boolean isInWeb;
    public boolean field_70135_K = true;
    public boolean isDead;
    public float yOffset;
    public float width = 0.6f;
    public float height = 1.8f;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    private int nextStepDistance = 1;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected Random rand = new Random();
    public int ticksExisted;
    public int fireResistance = 1;
    private int fire;
    protected boolean inWater;
    public int hurtResistantTime;
    private boolean firstUpdate = true;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    protected boolean inPortal;
    protected int portalCounter;
    public int dimension;
    protected int teleportDirection;
    private boolean invulnerable;
    protected UUID entityUniqueID = UUID.randomUUID();
    public EnumEntitySize myEntitySize = EnumEntitySize.SIZE_2;
    private static final String __OBFID = "CL_00001533";

    public int getEntityId() {
        return this.field_145783_c;
    }

    public void setEntityId(int p_145769_1_) {
        this.field_145783_c = p_145769_1_;
    }

    public Entity(World par1World) {
        this.worldObj = par1World;
        this.setPosition(0.0, 0.0, 0.0);
        if (par1World != null) {
            this.dimension = par1World.provider.dimensionId;
        }
        this.dataWatcher = new DataWatcher(this);
        this.dataWatcher.addObject(0, Byte.valueOf(0));
        this.dataWatcher.addObject(1, 300);
        this.entityInit();
    }

    protected abstract void entityInit();

    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    public boolean equals(Object par1Obj) {
        return par1Obj instanceof Entity ? ((Entity)par1Obj).field_145783_c == this.field_145783_c : false;
    }

    public int hashCode() {
        return this.field_145783_c;
    }

    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) break;
                this.posY += 1.0;
            }
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.rotationPitch = 0.0f;
        }
    }

    public void setDead() {
        this.isDead = true;
    }

    protected void setSize(float par1, float par2) {
        float var3;
        if (par1 != this.width || par2 != this.height) {
            var3 = this.width;
            this.width = par1;
            this.height = par2;
            this.boundingBox.maxX = this.boundingBox.minX + (double)this.width;
            this.boundingBox.maxZ = this.boundingBox.minZ + (double)this.width;
            this.boundingBox.maxY = this.boundingBox.minY + (double)this.height;
            if (this.width > var3 && !this.firstUpdate && !this.worldObj.isClient) {
                this.moveEntity(var3 - this.width, 0.0, var3 - this.width);
            }
        }
        this.myEntitySize = (double)(var3 = par1 % 2.0f) < 0.375 ? EnumEntitySize.SIZE_1 : ((double)var3 < 0.75 ? EnumEntitySize.SIZE_2 : ((double)var3 < 1.0 ? EnumEntitySize.SIZE_3 : ((double)var3 < 1.375 ? EnumEntitySize.SIZE_4 : ((double)var3 < 1.75 ? EnumEntitySize.SIZE_5 : EnumEntitySize.SIZE_6))));
    }

    protected void setRotation(float par1, float par2) {
        this.rotationYaw = par1 % 360.0f;
        this.rotationPitch = par2 % 360.0f;
    }

    public void setPosition(double par1, double par3, double par5) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        float var7 = this.width / 2.0f;
        float var8 = this.height;
        this.boundingBox.setBounds(par1 - (double)var7, par3 - (double)this.yOffset + (double)this.ySize, par5 - (double)var7, par1 + (double)var7, par3 - (double)this.yOffset + (double)this.ySize + (double)var8, par5 + (double)var7);
    }

    public void setAngles(float par1, float par2) {
        float var3 = this.rotationPitch;
        float var4 = this.rotationYaw;
        this.rotationYaw = (float)((double)this.rotationYaw + (double)par1 * 0.15);
        this.rotationPitch = (float)((double)this.rotationPitch - (double)par2 * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - var3;
        this.prevRotationYaw += this.rotationYaw - var4;
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        int var5;
        int var2;
        Block var4;
        int var6;
        this.worldObj.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
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
        if (this.isSprinting() && !this.isInWater() && (var4 = this.worldObj.getBlock(var5 = MathHelper.floor_double(this.posX), var2 = MathHelper.floor_double(this.posY - 0.20000000298023224 - (double)this.yOffset), var6 = MathHelper.floor_double(this.posZ))).getMaterial() != Material.air) {
            this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(var4) + "_" + this.worldObj.getBlockMetadata(var5, var2, var6), this.posX + ((double)this.rand.nextFloat() - 0.5) * (double)this.width, this.boundingBox.minY + 0.1, this.posZ + ((double)this.rand.nextFloat() - 0.5) * (double)this.width, (- this.motionX) * 4.0, 1.5, (- this.motionZ) * 4.0);
        }
        this.handleWaterMovement();
        if (this.worldObj.isClient) {
            this.fire = 0;
        } else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            } else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                --this.fire;
            }
        }
        if (this.handleLavaMovement()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isClient) {
            this.setFlag(0, this.fire > 0);
        }
        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();
    }

    public int getMaxInPortalTime() {
        return 0;
    }

    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(15);
        }
    }

    public void setFire(int par1) {
        int var2 = par1 * 20;
        if (this.fire < (var2 = EnchantmentProtection.getFireTimeForEntity(this, var2))) {
            this.fire = var2;
        }
    }

    public void extinguish() {
        this.fire = 0;
    }

    protected void kill() {
        this.setDead();
    }

    public boolean isOffsetPositionInLiquid(double par1, double par3, double par5) {
        AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(par1, par3, par5);
        List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
        return !var8.isEmpty() ? false : !this.worldObj.isAnyLiquid(var7);
    }

    public void moveEntity(double par1, double par3, double par5) {
        if (this.noClip) {
            this.boundingBox.offset(par1, par3, par5);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        } else {
            double var38;
            int var30;
            double var25;
            double var27;
            boolean var20;
            this.worldObj.theProfiler.startSection("move");
            this.ySize *= 0.4f;
            double var7 = this.posX;
            double var9 = this.posY;
            double var11 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                par1 *= 0.25;
                par3 *= 0.05000000074505806;
                par5 *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double var13 = par1;
            double var15 = par3;
            double var17 = par5;
            AxisAlignedBB var19 = this.boundingBox.copy();
            boolean bl = var20 = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
            if (var20 || Resilience.getInstance().getWrapper().getPlayer() != null && Resilience.getInstance().getValues().safeWalkEnabled && this instanceof EntityPlayer) {
                double var21 = 0.05;
                while (par1 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0, 0.0)).isEmpty()) {
                    par1 = par1 < var21 && par1 >= - var21 ? 0.0 : (par1 > 0.0 ? (par1 -= var21) : (par1 += var21));
                    var13 = par1;
                }
                while (par5 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0, -1.0, par5)).isEmpty()) {
                    par5 = par5 < var21 && par5 >= - var21 ? 0.0 : (par5 > 0.0 ? (par5 -= var21) : (par5 += var21));
                    var17 = par5;
                }
                while (par1 != 0.0 && par5 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0, par5)).isEmpty()) {
                    par1 = par1 < var21 && par1 >= - var21 ? 0.0 : (par1 > 0.0 ? (par1 -= var21) : (par1 += var21));
                    par5 = par5 < var21 && par5 >= - var21 ? 0.0 : (par5 > 0.0 ? (par5 -= var21) : (par5 += var21));
                    var13 = par1;
                    var17 = par5;
                }
            }
            List var37 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(par1, par3, par5));
            int var22 = 0;
            while (var22 < var37.size()) {
                par3 = ((AxisAlignedBB)var37.get(var22)).calculateYOffset(this.boundingBox, par3);
                ++var22;
            }
            this.boundingBox.offset(0.0, par3, 0.0);
            if (!this.field_70135_K && var15 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            boolean var36 = this.onGround || var15 != par3 && var15 < 0.0;
            int var23 = 0;
            while (var23 < var37.size()) {
                par1 = ((AxisAlignedBB)var37.get(var23)).calculateXOffset(this.boundingBox, par1);
                ++var23;
            }
            this.boundingBox.offset(par1, 0.0, 0.0);
            if (!this.field_70135_K && var13 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            var23 = 0;
            while (var23 < var37.size()) {
                par5 = ((AxisAlignedBB)var37.get(var23)).calculateZOffset(this.boundingBox, par5);
                ++var23;
            }
            this.boundingBox.offset(0.0, 0.0, par5);
            if (!this.field_70135_K && var17 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (this.stepHeight > 0.0f && var36 && (var20 || this.ySize < 0.05f) && (var13 != par1 || var17 != par5)) {
                var38 = par1;
                var25 = par3;
                var27 = par5;
                par1 = var13;
                par3 = this.stepHeight;
                par5 = var17;
                AxisAlignedBB var29 = this.boundingBox.copy();
                this.boundingBox.setBB(var19);
                var37 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var13, par3, var17));
                var30 = 0;
                while (var30 < var37.size()) {
                    par3 = ((AxisAlignedBB)var37.get(var30)).calculateYOffset(this.boundingBox, par3);
                    ++var30;
                }
                this.boundingBox.offset(0.0, par3, 0.0);
                if (!this.field_70135_K && var15 != par3) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                var30 = 0;
                while (var30 < var37.size()) {
                    par1 = ((AxisAlignedBB)var37.get(var30)).calculateXOffset(this.boundingBox, par1);
                    ++var30;
                }
                this.boundingBox.offset(par1, 0.0, 0.0);
                if (!this.field_70135_K && var13 != par1) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                var30 = 0;
                while (var30 < var37.size()) {
                    par5 = ((AxisAlignedBB)var37.get(var30)).calculateZOffset(this.boundingBox, par5);
                    ++var30;
                }
                this.boundingBox.offset(0.0, 0.0, par5);
                if (!this.field_70135_K && var17 != par5) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                }
                if (!this.field_70135_K && var15 != par3) {
                    par5 = 0.0;
                    par3 = 0.0;
                    par1 = 0.0;
                } else {
                    par3 = - this.stepHeight;
                    var30 = 0;
                    while (var30 < var37.size()) {
                        par3 = ((AxisAlignedBB)var37.get(var30)).calculateYOffset(this.boundingBox, par3);
                        ++var30;
                    }
                    this.boundingBox.offset(0.0, par3, 0.0);
                }
                if (var38 * var38 + var27 * var27 >= par1 * par1 + par5 * par5) {
                    par1 = var38;
                    par3 = var25;
                    par5 = var27;
                    this.boundingBox.setBB(var29);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = var13 != par1 || var17 != par5;
            this.isCollidedVertically = var15 != par3;
            this.onGround = var15 != par3 && var15 < 0.0;
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            this.updateFallState(par3, this.onGround);
            if (var13 != par1) {
                this.motionX = 0.0;
            }
            if (var15 != par3) {
                this.motionY = 0.0;
            }
            if (var17 != par5) {
                this.motionZ = 0.0;
            }
            var38 = this.posX - var7;
            var25 = this.posY - var9;
            var27 = this.posZ - var11;
            if (this.canTriggerWalking() && !var20 && this.ridingEntity == null) {
                int var40 = MathHelper.floor_double(this.posX);
                var30 = MathHelper.floor_double(this.posY - 0.20000000298023224 - (double)this.yOffset);
                int var31 = MathHelper.floor_double(this.posZ);
                Block var32 = this.worldObj.getBlock(var40, var30, var31);
                int var33 = this.worldObj.getBlock(var40, var30 - 1, var31).getRenderType();
                if (var33 == 11 || var33 == 32 || var33 == 21) {
                    var32 = this.worldObj.getBlock(var40, var30 - 1, var31);
                }
                if (var32 != Blocks.ladder) {
                    var25 = 0.0;
                }
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt_double(var38 * var38 + var27 * var27) * 0.6);
                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt_double(var38 * var38 + var25 * var25 + var27 * var27) * 0.6);
                if (this.distanceWalkedOnStepModified > (float)this.nextStepDistance && var32.getMaterial() != Material.air) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float var34 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (var34 > 1.0f) {
                            var34 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), var34, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.func_145780_a(var40, var30, var31, var32);
                    var32.onEntityWalking(this.worldObj, var40, var30, var31, this);
                }
            }
            try {
                this.func_145775_I();
            }
            catch (Throwable var35) {
                CrashReport var42 = CrashReport.makeCrashReport(var35, "Checking entity block collision");
                CrashReportCategory var39 = var42.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(var39);
                throw new ReportedException(var42);
            }
            boolean var41 = this.isWet();
            if (this.worldObj.func_147470_e(this.boundingBox.contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(1);
                if (!var41) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = - this.fireResistance;
            }
            if (var41 && this.fire > 0) {
                this.playSound("random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = - this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }

    protected String getSwimSound() {
        return "game.neutral.swim";
    }

    protected void func_145775_I() {
        int var3;
        int var4;
        int var2;
        int var5;
        int var6;
        int var1 = MathHelper.floor_double(this.boundingBox.minX + 0.001);
        if (this.worldObj.checkChunksExist(var1, var2 = MathHelper.floor_double(this.boundingBox.minY + 0.001), var3 = MathHelper.floor_double(this.boundingBox.minZ + 0.001), var4 = MathHelper.floor_double(this.boundingBox.maxX - 0.001), var5 = MathHelper.floor_double(this.boundingBox.maxY - 0.001), var6 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001))) {
            int var7 = var1;
            while (var7 <= var4) {
                int var8 = var2;
                while (var8 <= var5) {
                    int var9 = var3;
                    while (var9 <= var6) {
                        Block var10 = this.worldObj.getBlock(var7, var8, var9);
                        try {
                            var10.onEntityCollidedWithBlock(this.worldObj, var7, var8, var9, this);
                        }
                        catch (Throwable var14) {
                            CrashReport var12 = CrashReport.makeCrashReport(var14, "Colliding entity with block");
                            CrashReportCategory var13 = var12.makeCategory("Block being collided with");
                            CrashReportCategory.func_147153_a(var13, var7, var8, var9, var10, this.worldObj.getBlockMetadata(var7, var8, var9));
                            throw new ReportedException(var12);
                        }
                        ++var9;
                    }
                    ++var8;
                }
                ++var7;
            }
        }
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        Block.SoundType var5 = p_145780_4_.stepSound;
        if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer) {
            var5 = Blocks.snow_layer.stepSound;
            this.playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15f, var5.func_150494_d());
        } else if (!p_145780_4_.getMaterial().isLiquid()) {
            this.playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15f, var5.func_150494_d());
        }
    }

    public void playSound(String par1Str, float par2, float par3) {
        this.worldObj.playSoundAtEntity(this, par1Str, par2, par3);
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void updateFallState(double par1, boolean par3) {
        if (par3) {
            if (this.fallDistance > 0.0f) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        } else if (par1 < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - par1);
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    protected void dealFireDamage(int par1) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, par1);
        }
    }

    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }

    protected void fall(float par1) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(par1);
        }
    }

    public boolean isWet() {
        if (!(this.inWater || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + (double)this.height), MathHelper.floor_double(this.posZ)))) {
            return false;
        }
        return true;
    }

    public boolean isInWater() {
        return this.inWater;
    }

    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                float var5;
                float var4;
                float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
                if (var1 > 1.0f) {
                    var1 = 1.0f;
                }
                this.playSound(this.getSplashSound(), var1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                float var2 = MathHelper.floor_double(this.boundingBox.minY);
                int var3 = 0;
                while ((float)var3 < 1.0f + this.width * 20.0f) {
                    var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + (double)var4, var2 + 1.0f, this.posZ + (double)var5, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ);
                    ++var3;
                }
                var3 = 0;
                while ((float)var3 < 1.0f + this.width * 20.0f) {
                    var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + (double)var4, var2 + 1.0f, this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
                    ++var3;
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        } else {
            this.inWater = false;
        }
        return this.inWater;
    }

    protected String getSplashSound() {
        return "game.neutral.swim.splash";
    }

    public boolean isInsideOfMaterial(Material par1Material) {
        int var5;
        int var6;
        double var2 = this.posY + (double)this.getEyeHeight();
        int var4 = MathHelper.floor_double(this.posX);
        Block var7 = this.worldObj.getBlock(var4, var5 = MathHelper.floor_float(MathHelper.floor_double(var2)), var6 = MathHelper.floor_double(this.posZ));
        if (var7.getMaterial() == par1Material) {
            float var8 = BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(var4, var5, var6)) - 0.11111111f;
            float var9 = (float)(var5 + 1) - var8;
            if (var2 < (double)var9) {
                return true;
            }
            return false;
        }
        return false;
    }

    public float getEyeHeight() {
        return 0.0f;
    }

    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }

    public void moveFlying(float par1, float par2, float par3) {
        float var4 = par1 * par1 + par2 * par2;
        if (var4 >= 1.0E-4f) {
            if ((var4 = MathHelper.sqrt_float(var4)) < 1.0f) {
                var4 = 1.0f;
            }
            var4 = par3 / var4;
            float var5 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            float var6 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += (double)((par1 *= var4) * var6 - (par2 *= var4) * var5);
            this.motionZ += (double)(par2 * var6 + par1 * var5);
        }
    }

    public int getBrightnessForRender(float par1) {
        int var3;
        int var2 = MathHelper.floor_double(this.posX);
        if (this.worldObj.blockExists(var2, 0, var3 = MathHelper.floor_double(this.posZ))) {
            double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            int var6 = MathHelper.floor_double(this.posY - (double)this.yOffset + var4);
            return this.worldObj.getLightBrightnessForSkyBlocks(var2, var6, var3, 0);
        }
        return 0;
    }

    public float getBrightness(float par1) {
        int var3;
        int var2 = MathHelper.floor_double(this.posX);
        if (this.worldObj.blockExists(var2, 0, var3 = MathHelper.floor_double(this.posZ))) {
            double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            int var6 = MathHelper.floor_double(this.posY - (double)this.yOffset + var4);
            return this.worldObj.getLightBrightness(var2, var6, var3);
        }
        return 0.0f;
    }

    public void setWorld(World par1World) {
        this.worldObj = par1World;
    }

    public void setPositionAndRotation(double par1, double par3, double par5, float par7, float par8) {
        this.prevPosX = this.posX = par1;
        this.prevPosY = this.posY = par3;
        this.prevPosZ = this.posZ = par5;
        this.prevRotationYaw = this.rotationYaw = par7;
        this.prevRotationPitch = this.rotationPitch = par8;
        this.ySize = 0.0f;
        double var9 = this.prevRotationYaw - par7;
        if (var9 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (var9 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(par7, par8);
    }

    public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8) {
        this.prevPosX = this.posX = par1;
        this.lastTickPosX = this.posX;
        this.prevPosY = this.posY = par3 + (double)this.yOffset;
        this.lastTickPosY = this.posY;
        this.prevPosZ = this.posZ = par5;
        this.lastTickPosZ = this.posZ;
        this.rotationYaw = par7;
        this.rotationPitch = par8;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public float getDistanceToEntity(Entity par1Entity) {
        float var2 = (float)(this.posX - par1Entity.posX);
        float var3 = (float)(this.posY - par1Entity.posY);
        float var4 = (float)(this.posZ - par1Entity.posZ);
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }

    public double getDistanceSq(double par1, double par3, double par5) {
        double var7 = this.posX - par1;
        double var9 = this.posY - par3;
        double var11 = this.posZ - par5;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double getDistance(double par1, double par3, double par5) {
        double var7 = this.posX - par1;
        double var9 = this.posY - par3;
        double var11 = this.posZ - par5;
        return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }

    public double getDistanceSqToEntity(Entity par1Entity) {
        double var2 = this.posX - par1Entity.posX;
        double var4 = this.posY - par1Entity.posY;
        double var6 = this.posZ - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
    }

    public void applyEntityCollision(Entity par1Entity) {
        double var2;
        double var6;
        double var4;
        if (par1Entity.riddenByEntity != this && par1Entity.ridingEntity != this && (var6 = MathHelper.abs_max(var2 = par1Entity.posX - this.posX, var4 = par1Entity.posZ - this.posZ)) >= 0.009999999776482582) {
            var6 = MathHelper.sqrt_double(var6);
            var2 /= var6;
            var4 /= var6;
            double var8 = 1.0 / var6;
            if (var8 > 1.0) {
                var8 = 1.0;
            }
            var2 *= var8;
            var4 *= var8;
            var2 *= 0.05000000074505806;
            var4 *= 0.05000000074505806;
            this.addVelocity(- (var2 *= (double)(1.0f - this.entityCollisionReduction)), 0.0, - (var4 *= (double)(1.0f - this.entityCollisionReduction)));
            par1Entity.addVelocity(var2, 0.0, var4);
        }
    }

    public void addVelocity(double par1, double par3, double par5) {
        this.motionX += par1;
        this.motionY += par3;
        this.motionZ += par5;
        this.isAirBorne = true;
    }

    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void addToPlayerScore(Entity par1Entity, int par2) {
    }

    public boolean isInRangeToRender3d(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        double var7 = this.posX - p_145770_1_;
        double var9 = this.posY - p_145770_3_;
        double var11 = this.posZ - p_145770_5_;
        double var13 = var7 * var7 + var9 * var9 + var11 * var11;
        return this.isInRangeToRenderDist(var13);
    }

    public boolean isInRangeToRenderDist(double par1) {
        double var3 = this.boundingBox.getAverageEdgeLength();
        if (par1 < (var3 *= 64.0 * this.renderDistanceWeight) * var3) {
            return true;
        }
        return false;
    }

    public boolean writeMountToNBT(NBTTagCompound par1NBTTagCompound) {
        String var2 = this.getEntityString();
        if (!this.isDead && var2 != null) {
            par1NBTTagCompound.setString("id", var2);
            this.writeToNBT(par1NBTTagCompound);
            return true;
        }
        return false;
    }

    public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound) {
        String var2 = this.getEntityString();
        if (!this.isDead && var2 != null && this.riddenByEntity == null) {
            par1NBTTagCompound.setString("id", var2);
            this.writeToNBT(par1NBTTagCompound);
            return true;
        }
        return false;
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        try {
            NBTTagCompound var2;
            par1NBTTagCompound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY + (double)this.ySize, this.posZ));
            par1NBTTagCompound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
            par1NBTTagCompound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            par1NBTTagCompound.setFloat("FallDistance", this.fallDistance);
            par1NBTTagCompound.setShort("Fire", (short)this.fire);
            par1NBTTagCompound.setShort("Air", (short)this.getAir());
            par1NBTTagCompound.setBoolean("OnGround", this.onGround);
            par1NBTTagCompound.setInteger("Dimension", this.dimension);
            par1NBTTagCompound.setBoolean("Invulnerable", this.invulnerable);
            par1NBTTagCompound.setInteger("PortalCooldown", this.timeUntilPortal);
            par1NBTTagCompound.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
            par1NBTTagCompound.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
            this.writeEntityToNBT(par1NBTTagCompound);
            if (this.ridingEntity != null && this.ridingEntity.writeMountToNBT(var2 = new NBTTagCompound())) {
                par1NBTTagCompound.setTag("Riding", var2);
            }
        }
        catch (Throwable var5) {
            CrashReport var3 = CrashReport.makeCrashReport(var5, "Saving entity NBT");
            CrashReportCategory var4 = var3.makeCategory("Entity being saved");
            this.addEntityCrashInfo(var4);
            throw new ReportedException(var3);
        }
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        try {
            NBTTagList var2 = par1NBTTagCompound.getTagList("Pos", 6);
            NBTTagList var6 = par1NBTTagCompound.getTagList("Motion", 6);
            NBTTagList var7 = par1NBTTagCompound.getTagList("Rotation", 5);
            this.motionX = var6.func_150309_d(0);
            this.motionY = var6.func_150309_d(1);
            this.motionZ = var6.func_150309_d(2);
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }
            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }
            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }
            this.lastTickPosX = this.posX = var2.func_150309_d(0);
            this.prevPosX = this.posX;
            this.lastTickPosY = this.posY = var2.func_150309_d(1);
            this.prevPosY = this.posY;
            this.lastTickPosZ = this.posZ = var2.func_150309_d(2);
            this.prevPosZ = this.posZ;
            this.prevRotationYaw = this.rotationYaw = var7.func_150308_e(0);
            this.prevRotationPitch = this.rotationPitch = var7.func_150308_e(1);
            this.fallDistance = par1NBTTagCompound.getFloat("FallDistance");
            this.fire = par1NBTTagCompound.getShort("Fire");
            this.setAir(par1NBTTagCompound.getShort("Air"));
            this.onGround = par1NBTTagCompound.getBoolean("OnGround");
            this.dimension = par1NBTTagCompound.getInteger("Dimension");
            this.invulnerable = par1NBTTagCompound.getBoolean("Invulnerable");
            this.timeUntilPortal = par1NBTTagCompound.getInteger("PortalCooldown");
            if (par1NBTTagCompound.func_150297_b("UUIDMost", 4) && par1NBTTagCompound.func_150297_b("UUIDLeast", 4)) {
                this.entityUniqueID = new UUID(par1NBTTagCompound.getLong("UUIDMost"), par1NBTTagCompound.getLong("UUIDLeast"));
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.readEntityFromNBT(par1NBTTagCompound);
            if (this.shouldSetPosAfterLoading()) {
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
        catch (Throwable var5) {
            CrashReport var3 = CrashReport.makeCrashReport(var5, "Loading entity NBT");
            CrashReportCategory var4 = var3.makeCategory("Entity being loaded");
            this.addEntityCrashInfo(var4);
            throw new ReportedException(var3);
        }
    }

    protected boolean shouldSetPosAfterLoading() {
        return true;
    }

    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound var1);

    protected abstract void writeEntityToNBT(NBTTagCompound var1);

    public void onChunkLoad() {
    }

    protected /* varargs */ NBTTagList newDoubleNBTList(double ... par1ArrayOfDouble) {
        NBTTagList var2 = new NBTTagList();
        double[] var3 = par1ArrayOfDouble;
        int var4 = par1ArrayOfDouble.length;
        int var5 = 0;
        while (var5 < var4) {
            double var6 = var3[var5];
            var2.appendTag(new NBTTagDouble(var6));
            ++var5;
        }
        return var2;
    }

    protected /* varargs */ NBTTagList newFloatNBTList(float ... par1ArrayOfFloat) {
        NBTTagList var2 = new NBTTagList();
        float[] var3 = par1ArrayOfFloat;
        int var4 = par1ArrayOfFloat.length;
        int var5 = 0;
        while (var5 < var4) {
            float var6 = var3[var5];
            var2.appendTag(new NBTTagFloat(var6));
            ++var5;
        }
        return var2;
    }

    public float getShadowSize() {
        return this.height / 2.0f;
    }

    public EntityItem func_145779_a(Item p_145779_1_, int p_145779_2_) {
        return this.func_145778_a(p_145779_1_, p_145779_2_, 0.0f);
    }

    public EntityItem func_145778_a(Item p_145778_1_, int p_145778_2_, float p_145778_3_) {
        return this.entityDropItem(new ItemStack(p_145778_1_, p_145778_2_, 0), p_145778_3_);
    }

    public EntityItem entityDropItem(ItemStack par1ItemStack, float par2) {
        if (par1ItemStack.stackSize != 0 && par1ItemStack.getItem() != null) {
            EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY + (double)par2, this.posZ, par1ItemStack);
            var3.delayBeforeCanPickup = 10;
            this.worldObj.spawnEntityInWorld(var3);
            return var3;
        }
        return null;
    }

    public boolean isEntityAlive() {
        return !this.isDead;
    }

    public boolean isEntityInsideOpaqueBlock() {
        int var1 = 0;
        while (var1 < 8) {
            int var6;
            int var7;
            float var2 = ((float)((var1 >> 0) % 2) - 0.5f) * this.width * 0.8f;
            float var3 = ((float)((var1 >> 1) % 2) - 0.5f) * 0.1f;
            float var4 = ((float)((var1 >> 2) % 2) - 0.5f) * this.width * 0.8f;
            int var5 = MathHelper.floor_double(this.posX + (double)var2);
            if (this.worldObj.getBlock(var5, var6 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight() + (double)var3), var7 = MathHelper.floor_double(this.posZ + (double)var4)).isNormalCube()) {
                return true;
            }
            ++var1;
        }
        return false;
    }

    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        return false;
    }

    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return null;
    }

    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.ridingEntity != null) {
                this.ridingEntity.updateRiderPosition();
                this.entityRiderYawDelta += (double)(this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
                this.entityRiderPitchDelta += (double)(this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch);
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double var1 = this.entityRiderYawDelta * 0.5;
                double var3 = this.entityRiderPitchDelta * 0.5;
                float var5 = 10.0f;
                if (var1 > (double)var5) {
                    var1 = var5;
                }
                if (var1 < (double)(- var5)) {
                    var1 = - var5;
                }
                if (var3 > (double)var5) {
                    var3 = var5;
                }
                if (var3 < (double)(- var5)) {
                    var3 = - var5;
                }
                this.entityRiderYawDelta -= var1;
                this.entityRiderPitchDelta -= var3;
            }
        }
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.75;
    }

    public void mountEntity(Entity par1Entity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (par1Entity == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + (double)this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        } else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = par1Entity;
            par1Entity.riddenByEntity = this;
        }
    }

    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
        List var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125, 0.0, 0.03125));
        if (!var10.isEmpty()) {
            double var11 = 0.0;
            int var13 = 0;
            while (var13 < var10.size()) {
                AxisAlignedBB var14 = (AxisAlignedBB)var10.get(var13);
                if (var14.maxY > var11) {
                    var11 = var14.maxY;
                }
                ++var13;
            }
            this.setPosition(par1, par3 += var11 - this.boundingBox.minY, par5);
        }
    }

    public float getCollisionBorderSize() {
        return 0.1f;
    }

    public Vec3 getLookVec() {
        return null;
    }

    public void setInPortal() {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        } else {
            double var1 = this.prevPosX - this.posX;
            double var3 = this.prevPosZ - this.posZ;
            if (!this.worldObj.isClient && !this.inPortal) {
                this.teleportDirection = Direction.getMovementDirection(var1, var3);
            }
            this.inPortal = true;
        }
    }

    public int getPortalCooldown() {
        return 300;
    }

    public void setVelocity(double par1, double par3, double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
    }

    public void handleHealthUpdate(byte par1) {
    }

    public void performHurtAnimation() {
    }

    public ItemStack[] getLastActiveItems() {
        return null;
    }

    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
    }

    public boolean isBurning() {
        boolean var1;
        boolean bl = var1 = this.worldObj != null && this.worldObj.isClient;
        if (!this.isImmuneToFire && (this.fire > 0 || var1 && this.getFlag(0))) {
            return true;
        }
        return false;
    }

    public boolean isRiding() {
        if (this.ridingEntity != null) {
            return true;
        }
        return false;
    }

    public boolean isSneaking() {
        return this.getFlag(1);
    }

    public void setSneaking(boolean par1) {
        this.setFlag(1, par1);
    }

    public boolean isSprinting() {
        return this.getFlag(3);
    }

    public void setSprinting(boolean par1) {
        this.setFlag(3, par1);
    }

    public boolean isInvisible() {
        return this.getFlag(5);
    }

    public boolean isInvisibleToPlayer(EntityPlayer par1EntityPlayer) {
        return this.isInvisible();
    }

    public void setInvisible(boolean par1) {
        this.setFlag(5, par1);
    }

    public boolean isEating() {
        return this.getFlag(4);
    }

    public void setEating(boolean par1) {
        this.setFlag(4, par1);
    }

    protected boolean getFlag(int par1) {
        if ((this.dataWatcher.getWatchableObjectByte(0) & 1 << par1) != 0) {
            return true;
        }
        return false;
    }

    protected void setFlag(int par1, boolean par2) {
        byte var3 = this.dataWatcher.getWatchableObjectByte(0);
        if (par2) {
            this.dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 | 1 << par1)));
        } else {
            this.dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 & ~ (1 << par1))));
        }
    }

    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }

    public void setAir(int par1) {
        this.dataWatcher.updateObject(1, (short)par1);
    }

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
        this.dealFireDamage(5);
        ++this.fire;
        if (this.fire == 0) {
            this.setFire(8);
        }
    }

    public void onKillEntity(EntityLivingBase par1EntityLivingBase) {
    }

    protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_) {
        int var7 = MathHelper.floor_double(p_145771_1_);
        int var8 = MathHelper.floor_double(p_145771_3_);
        int var9 = MathHelper.floor_double(p_145771_5_);
        double var10 = p_145771_1_ - (double)var7;
        double var12 = p_145771_3_ - (double)var8;
        double var14 = p_145771_5_ - (double)var9;
        List var16 = this.worldObj.func_147461_a(this.boundingBox);
        if (var16.isEmpty() && !this.worldObj.func_147469_q(var7, var8, var9)) {
            return false;
        }
        boolean var17 = !this.worldObj.func_147469_q(var7 - 1, var8, var9);
        boolean var18 = !this.worldObj.func_147469_q(var7 + 1, var8, var9);
        boolean var19 = !this.worldObj.func_147469_q(var7, var8 - 1, var9);
        boolean var20 = !this.worldObj.func_147469_q(var7, var8 + 1, var9);
        boolean var21 = !this.worldObj.func_147469_q(var7, var8, var9 - 1);
        boolean var22 = !this.worldObj.func_147469_q(var7, var8, var9 + 1);
        int var23 = 3;
        double var24 = 9999.0;
        if (var17 && var10 < var24) {
            var24 = var10;
            var23 = 0;
        }
        if (var18 && 1.0 - var10 < var24) {
            var24 = 1.0 - var10;
            var23 = 1;
        }
        if (var20 && 1.0 - var12 < var24) {
            var24 = 1.0 - var12;
            var23 = 3;
        }
        if (var21 && var14 < var24) {
            var24 = var14;
            var23 = 4;
        }
        if (var22 && 1.0 - var14 < var24) {
            var24 = 1.0 - var14;
            var23 = 5;
        }
        float var26 = this.rand.nextFloat() * 0.2f + 0.1f;
        if (var23 == 0) {
            this.motionX = - var26;
        }
        if (var23 == 1) {
            this.motionX = var26;
        }
        if (var23 == 2) {
            this.motionY = - var26;
        }
        if (var23 == 3) {
            this.motionY = var26;
        }
        if (var23 == 4) {
            this.motionZ = - var26;
        }
        if (var23 == 5) {
            this.motionZ = var26;
        }
        return true;
    }

    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }

    public String getCommandSenderName() {
        String var1 = EntityList.getEntityString(this);
        if (var1 == null) {
            var1 = "generic";
        }
        return StatCollector.translateToLocal("entity." + var1 + ".name");
    }

    public Entity[] getParts() {
        return null;
    }

    public boolean isEntityEqual(Entity par1Entity) {
        if (this == par1Entity) {
            return true;
        }
        return false;
    }

    public float getRotationYawHead() {
        return 0.0f;
    }

    public void setRotationYawHead(float par1) {
    }

    public boolean canAttackWithItem() {
        return true;
    }

    public boolean hitByEntity(Entity par1Entity) {
        return false;
    }

    public String toString() {
        Object[] arrobject = new Object[7];
        arrobject[0] = this.getClass().getSimpleName();
        arrobject[1] = this.getCommandSenderName();
        arrobject[2] = this.field_145783_c;
        arrobject[3] = this.worldObj == null ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName();
        arrobject[4] = this.posX;
        arrobject[5] = this.posY;
        arrobject[6] = this.posZ;
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", arrobject);
    }

    public boolean isEntityInvulnerable() {
        return this.invulnerable;
    }

    public void copyLocationAndAnglesFrom(Entity par1Entity) {
        this.setLocationAndAngles(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par1Entity.rotationYaw, par1Entity.rotationPitch);
    }

    public void copyDataFrom(Entity par1Entity, boolean par2) {
        NBTTagCompound var3 = new NBTTagCompound();
        par1Entity.writeToNBT(var3);
        this.readFromNBT(var3);
        this.timeUntilPortal = par1Entity.timeUntilPortal;
        this.teleportDirection = par1Entity.teleportDirection;
    }

    public void travelToDimension(int par1) {
        if (!this.worldObj.isClient && !this.isDead) {
            this.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer var2 = MinecraftServer.getServer();
            int var3 = this.dimension;
            WorldServer var4 = var2.worldServerForDimension(var3);
            WorldServer var5 = var2.worldServerForDimension(par1);
            this.dimension = par1;
            if (var3 == 1 && par1 == 1) {
                var5 = var2.worldServerForDimension(0);
                this.dimension = 0;
            }
            this.worldObj.removeEntity(this);
            this.isDead = false;
            this.worldObj.theProfiler.startSection("reposition");
            var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
            this.worldObj.theProfiler.endStartSection("reloading");
            Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
            if (var6 != null) {
                var6.copyDataFrom(this, true);
                if (var3 == 1 && par1 == 1) {
                    ChunkCoordinates var7 = var5.getSpawnPoint();
                    var7.posY = this.worldObj.getTopSolidOrLiquidBlock(var7.posX, var7.posZ);
                    var6.setLocationAndAngles(var7.posX, var7.posY, var7.posZ, var6.rotationYaw, var6.rotationPitch);
                }
                var5.spawnEntityInWorld(var6);
            }
            this.isDead = true;
            this.worldObj.theProfiler.endSection();
            var4.resetUpdateEntityTick();
            var5.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }

    public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_) {
        return p_145772_6_.getExplosionResistance(this);
    }

    public boolean func_145774_a(Explosion p_145774_1_, World p_145774_2_, int p_145774_3_, int p_145774_4_, int p_145774_5_, Block p_145774_6_, float p_145774_7_) {
        return true;
    }

    public int getMaxSafePointTries() {
        return 3;
    }

    public int getTeleportDirection() {
        return this.teleportDirection;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory) {
        par1CrashReportCategory.addCrashSectionCallable("Entity Type", new Callable(){
            private static final String __OBFID = "CL_00001534";

            public String call() {
                return String.valueOf(EntityList.getEntityString(Entity.this)) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }
        });
        par1CrashReportCategory.addCrashSection("Entity ID", this.field_145783_c);
        par1CrashReportCategory.addCrashSectionCallable("Entity Name", new Callable(){
            private static final String __OBFID = "CL_00001535";

            public String call() {
                return Entity.this.getCommandSenderName();
            }
        });
        par1CrashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        par1CrashReportCategory.addCrashSection("Entity's Block location", CrashReportCategory.getLocationInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        par1CrashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
    }

    public boolean canRenderOnFire() {
        return this.isBurning();
    }

    public UUID getUniqueID() {
        return this.entityUniqueID;
    }

    public boolean isPushedByWater() {
        return true;
    }

    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }

    public void func_145781_i(int p_145781_1_) {
    }

    public static enum EnumEntitySize {
        SIZE_1("SIZE_1", 0),
        SIZE_2("SIZE_2", 1),
        SIZE_3("SIZE_3", 2),
        SIZE_4("SIZE_4", 3),
        SIZE_5("SIZE_5", 4),
        SIZE_6("SIZE_6", 5);
        
        private static final EnumEntitySize[] $VALUES;
        private static final String __OBFID = "CL_00001537";

        static {
            $VALUES = new EnumEntitySize[]{SIZE_1, SIZE_2, SIZE_3, SIZE_4, SIZE_5, SIZE_6};
        }

        private EnumEntitySize(String par1Str, int par2, String string2, int n2) {
        }

        public int multiplyBy32AndRound(double par1) {
            double var3 = par1 - ((double)MathHelper.floor_double(par1) + 0.5);
            switch (this) {
                case SIZE_1: {
                    if (var3 < 0.0 ? var3 < -0.3125 : var3 < 0.3125) {
                        return MathHelper.ceiling_double_int(par1 * 32.0);
                    }
                    return MathHelper.floor_double(par1 * 32.0);
                }
                case SIZE_2: {
                    if (var3 < 0.0 ? var3 < -0.3125 : var3 < 0.3125) {
                        return MathHelper.floor_double(par1 * 32.0);
                    }
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
                case SIZE_3: {
                    if (var3 > 0.0) {
                        return MathHelper.floor_double(par1 * 32.0);
                    }
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
                case SIZE_4: {
                    if (var3 < 0.0 ? var3 < -0.1875 : var3 < 0.1875) {
                        return MathHelper.ceiling_double_int(par1 * 32.0);
                    }
                    return MathHelper.floor_double(par1 * 32.0);
                }
                case SIZE_5: {
                    if (var3 < 0.0 ? var3 < -0.1875 : var3 < 0.1875) {
                        return MathHelper.floor_double(par1 * 32.0);
                    }
                    return MathHelper.ceiling_double_int(par1 * 32.0);
                }
            }
            if (var3 > 0.0) {
                return MathHelper.ceiling_double_int(par1 * 32.0);
            }
            return MathHelper.floor_double(par1 * 32.0);
        }
    }

}

