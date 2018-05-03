/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.boss;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityDragon
extends EntityLiving
implements IBossDisplayData,
IEntityMultiPart,
IMob {
    public double targetX;
    public double targetY;
    public double targetZ;
    public double[][] ringBuffer = new double[64][3];
    public int ringBufferIndex = -1;
    public EntityDragonPart[] dragonPartArray;
    public EntityDragonPart dragonPartHead;
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart dragonPartTail1;
    public EntityDragonPart dragonPartTail2;
    public EntityDragonPart dragonPartTail3;
    public EntityDragonPart dragonPartWing1;
    public EntityDragonPart dragonPartWing2;
    public float prevAnimTime;
    public float animTime;
    public boolean forceNewTarget;
    public boolean slowed;
    private Entity target;
    public int deathTicks;
    public EntityEnderCrystal healingEnderCrystal;
    private static final String __OBFID = "CL_00001659";

    public EntityDragon(World par1World) {
        super(par1World);
        this.dragonPartHead = new EntityDragonPart(this, "head", 6.0f, 6.0f);
        this.dragonPartBody = new EntityDragonPart(this, "body", 8.0f, 8.0f);
        this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0f, 4.0f);
        this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0f, 4.0f);
        this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0f, 4.0f);
        this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0f, 4.0f);
        this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0f, 4.0f);
        EntityDragonPart[] arrentityDragonPart = new EntityDragonPart[]{this.dragonPartHead, this.dragonPartBody, this.dragonPartTail1, this.dragonPartTail2, this.dragonPartTail3, this.dragonPartWing1, this.dragonPartWing2};
        this.dragonPartArray = arrentityDragonPart;
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0f, 8.0f);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.targetY = 100.0;
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    public double[] getMovementOffsets(int par1, float par2) {
        if (this.getHealth() <= 0.0f) {
            par2 = 0.0f;
        }
        par2 = 1.0f - par2;
        int var3 = this.ringBufferIndex - par1 * 1 & 63;
        int var4 = this.ringBufferIndex - par1 * 1 - 1 & 63;
        double[] var5 = new double[3];
        double var6 = this.ringBuffer[var3][0];
        double var8 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
        var5[0] = var6 + var8 * (double)par2;
        var6 = this.ringBuffer[var3][1];
        var8 = this.ringBuffer[var4][1] - var6;
        var5[1] = var6 + var8 * (double)par2;
        var5[2] = this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * (double)par2;
        return var5;
    }

    @Override
    public void onLivingUpdate() {
        float var1;
        float var2;
        if (this.worldObj.isClient) {
            var1 = MathHelper.cos(this.animTime * 3.1415927f * 2.0f);
            var2 = MathHelper.cos(this.prevAnimTime * 3.1415927f * 2.0f);
            if (var2 <= -0.3f && var1 >= -0.3f) {
                this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0f, 0.8f + this.rand.nextFloat() * 0.3f, false);
            }
        }
        this.prevAnimTime = this.animTime;
        if (this.getHealth() <= 0.0f) {
            var1 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            var2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            float var3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.worldObj.spawnParticle("largeexplode", this.posX + (double)var1, this.posY + 2.0 + (double)var2, this.posZ + (double)var3, 0.0, 0.0, 0.0);
        } else {
            double var8;
            double var6;
            float var33;
            double var26;
            this.updateDragonEnderCrystal();
            var1 = 0.2f / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0f + 1.0f);
            this.animTime = this.slowed ? (this.animTime += var1 * 0.5f) : (this.animTime += (var1 *= (float)Math.pow(2.0, this.motionY)));
            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
            if (this.ringBufferIndex < 0) {
                int var25 = 0;
                while (var25 < this.ringBuffer.length) {
                    this.ringBuffer[var25][0] = this.rotationYaw;
                    this.ringBuffer[var25][1] = this.posY;
                    ++var25;
                }
            }
            if (++this.ringBufferIndex == this.ringBuffer.length) {
                this.ringBufferIndex = 0;
            }
            this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
            this.ringBuffer[this.ringBufferIndex][1] = this.posY;
            if (this.worldObj.isClient) {
                if (this.newPosRotationIncrements > 0) {
                    var26 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
                    double var4 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
                    var6 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
                    var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
                    this.rotationYaw = (float)((double)this.rotationYaw + var8 / (double)this.newPosRotationIncrements);
                    this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
                    --this.newPosRotationIncrements;
                    this.setPosition(var26, var4, var6);
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                }
            } else {
                var26 = this.targetX - this.posX;
                double var4 = this.targetY - this.posY;
                var6 = this.targetZ - this.posZ;
                var8 = var26 * var26 + var4 * var4 + var6 * var6;
                if (this.target != null) {
                    this.targetX = this.target.posX;
                    this.targetZ = this.target.posZ;
                    double var10 = this.targetX - this.posX;
                    double var12 = this.targetZ - this.posZ;
                    double var14 = Math.sqrt(var10 * var10 + var12 * var12);
                    double var16 = 0.4000000059604645 + var14 / 80.0 - 1.0;
                    if (var16 > 10.0) {
                        var16 = 10.0;
                    }
                    this.targetY = this.target.boundingBox.minY + var16;
                } else {
                    this.targetX += this.rand.nextGaussian() * 2.0;
                    this.targetZ += this.rand.nextGaussian() * 2.0;
                }
                if (this.forceNewTarget || var8 < 100.0 || var8 > 22500.0 || this.isCollidedHorizontally || this.isCollidedVertically) {
                    this.setNewTarget();
                }
                if ((var4 /= (double)MathHelper.sqrt_double(var26 * var26 + var6 * var6)) < (double)(- (var33 = 0.6f))) {
                    var4 = - var33;
                }
                if (var4 > (double)var33) {
                    var4 = var33;
                }
                this.motionY += var4 * 0.10000000149011612;
                this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
                double var11 = 180.0 - Math.atan2(var26, var6) * 180.0 / 3.141592653589793;
                double var13 = MathHelper.wrapAngleTo180_double(var11 - (double)this.rotationYaw);
                if (var13 > 50.0) {
                    var13 = 50.0;
                }
                if (var13 < -50.0) {
                    var13 = -50.0;
                }
                Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
                Vec3 var40 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f), this.motionY, - MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f)).normalize();
                float var17 = (float)(var40.dotProduct(var15) + 0.5) / 1.5f;
                if (var17 < 0.0f) {
                    var17 = 0.0f;
                }
                this.randomYawVelocity *= 0.8f;
                float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0f + 1.0f;
                double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0 + 1.0;
                if (var19 > 40.0) {
                    var19 = 40.0;
                }
                this.randomYawVelocity = (float)((double)this.randomYawVelocity + var13 * (0.699999988079071 / var19 / (double)var18));
                this.rotationYaw += this.randomYawVelocity * 0.1f;
                float var21 = (float)(2.0 / (var19 + 1.0));
                float var22 = 0.06f;
                this.moveFlying(0.0f, -1.0f, var22 * (var17 * var21 + (1.0f - var21)));
                if (this.slowed) {
                    this.moveEntity(this.motionX * 0.800000011920929, this.motionY * 0.800000011920929, this.motionZ * 0.800000011920929);
                } else {
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                }
                Vec3 var23 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.motionX, this.motionY, this.motionZ).normalize();
                float var24 = (float)(var23.dotProduct(var40) + 1.0) / 2.0f;
                var24 = 0.8f + 0.15f * var24;
                this.motionX *= (double)var24;
                this.motionZ *= (double)var24;
                this.motionY *= 0.9100000262260437;
            }
            this.renderYawOffset = this.rotationYaw;
            this.dragonPartHead.height = 3.0f;
            this.dragonPartHead.width = 3.0f;
            this.dragonPartTail1.height = 2.0f;
            this.dragonPartTail1.width = 2.0f;
            this.dragonPartTail2.height = 2.0f;
            this.dragonPartTail2.width = 2.0f;
            this.dragonPartTail3.height = 2.0f;
            this.dragonPartTail3.width = 2.0f;
            this.dragonPartBody.height = 3.0f;
            this.dragonPartBody.width = 5.0f;
            this.dragonPartWing1.height = 2.0f;
            this.dragonPartWing1.width = 4.0f;
            this.dragonPartWing2.height = 3.0f;
            this.dragonPartWing2.width = 4.0f;
            var2 = (float)(this.getMovementOffsets(5, 1.0f)[1] - this.getMovementOffsets(10, 1.0f)[1]) * 10.0f / 180.0f * 3.1415927f;
            float var3 = MathHelper.cos(var2);
            float var28 = - MathHelper.sin(var2);
            float var5 = this.rotationYaw * 3.1415927f / 180.0f;
            float var27 = MathHelper.sin(var5);
            float var7 = MathHelper.cos(var5);
            this.dragonPartBody.onUpdate();
            this.dragonPartBody.setLocationAndAngles(this.posX + (double)(var27 * 0.5f), this.posY, this.posZ - (double)(var7 * 0.5f), 0.0f, 0.0f);
            this.dragonPartWing1.onUpdate();
            this.dragonPartWing1.setLocationAndAngles(this.posX + (double)(var7 * 4.5f), this.posY + 2.0, this.posZ + (double)(var27 * 4.5f), 0.0f, 0.0f);
            this.dragonPartWing2.onUpdate();
            this.dragonPartWing2.setLocationAndAngles(this.posX - (double)(var7 * 4.5f), this.posY + 2.0, this.posZ - (double)(var27 * 4.5f), 0.0f, 0.0f);
            if (!this.worldObj.isClient && this.hurtTime == 0) {
                this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.boundingBox.expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.boundingBox.expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                this.attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.boundingBox.expand(1.0, 1.0, 1.0)));
            }
            double[] var29 = this.getMovementOffsets(5, 1.0f);
            double[] var9 = this.getMovementOffsets(0, 1.0f);
            var33 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
            float var32 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
            this.dragonPartHead.onUpdate();
            this.dragonPartHead.setLocationAndAngles(this.posX + (double)(var33 * 5.5f * var3), this.posY + (var9[1] - var29[1]) * 1.0 + (double)(var28 * 5.5f), this.posZ - (double)(var32 * 5.5f * var3), 0.0f, 0.0f);
            int var30 = 0;
            while (var30 < 3) {
                EntityDragonPart var31 = null;
                if (var30 == 0) {
                    var31 = this.dragonPartTail1;
                }
                if (var30 == 1) {
                    var31 = this.dragonPartTail2;
                }
                if (var30 == 2) {
                    var31 = this.dragonPartTail3;
                }
                double[] var35 = this.getMovementOffsets(12 + var30 * 2, 1.0f);
                float var34 = this.rotationYaw * 3.1415927f / 180.0f + this.simplifyAngle(var35[0] - var29[0]) * 3.1415927f / 180.0f * 1.0f;
                float var38 = MathHelper.sin(var34);
                float var37 = MathHelper.cos(var34);
                float var36 = 1.5f;
                float var39 = (float)(var30 + 1) * 2.0f;
                var31.onUpdate();
                var31.setLocationAndAngles(this.posX - (double)((var27 * var36 + var38 * var39) * var3), this.posY + (var35[1] - var29[1]) * 1.0 - (double)((var39 + var36) * var28) + 1.5, this.posZ + (double)((var7 * var36 + var37 * var39) * var3), 0.0f, 0.0f);
                ++var30;
            }
            if (!this.worldObj.isClient) {
                this.slowed = this.destroyBlocksInAABB(this.dragonPartHead.boundingBox) | this.destroyBlocksInAABB(this.dragonPartBody.boundingBox);
            }
        }
    }

    private void updateDragonEnderCrystal() {
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.worldObj.isClient) {
                    this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0f);
                }
                this.healingEnderCrystal = null;
            } else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.rand.nextInt(10) == 0) {
            float var1 = 32.0f;
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.boundingBox.expand(var1, var1, var1));
            EntityEnderCrystal var3 = null;
            double var4 = Double.MAX_VALUE;
            for (EntityEnderCrystal var7 : var2) {
                double var8 = var7.getDistanceSqToEntity(this);
                if (var8 >= var4) continue;
                var4 = var8;
                var3 = var7;
            }
            this.healingEnderCrystal = var3;
        }
    }

    private void collideWithEntities(List par1List) {
        double var2 = (this.dragonPartBody.boundingBox.minX + this.dragonPartBody.boundingBox.maxX) / 2.0;
        double var4 = (this.dragonPartBody.boundingBox.minZ + this.dragonPartBody.boundingBox.maxZ) / 2.0;
        for (Entity var7 : par1List) {
            if (!(var7 instanceof EntityLivingBase)) continue;
            double var8 = var7.posX - var2;
            double var10 = var7.posZ - var4;
            double var12 = var8 * var8 + var10 * var10;
            var7.addVelocity(var8 / var12 * 4.0, 0.20000000298023224, var10 / var12 * 4.0);
        }
    }

    private void attackEntitiesInList(List par1List) {
        int var2 = 0;
        while (var2 < par1List.size()) {
            Entity var3 = (Entity)par1List.get(var2);
            if (var3 instanceof EntityLivingBase) {
                var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0f);
            }
            ++var2;
        }
    }

    private void setNewTarget() {
        this.forceNewTarget = false;
        if (this.rand.nextInt(2) == 0 && !this.worldObj.playerEntities.isEmpty()) {
            this.target = (Entity)this.worldObj.playerEntities.get(this.rand.nextInt(this.worldObj.playerEntities.size()));
        } else {
            double var2;
            double var6;
            double var4;
            boolean var1 = false;
            do {
                this.targetX = 0.0;
                this.targetY = 70.0f + this.rand.nextFloat() * 50.0f;
                this.targetZ = 0.0;
                this.targetX += (double)(this.rand.nextFloat() * 120.0f - 60.0f);
                this.targetZ += (double)(this.rand.nextFloat() * 120.0f - 60.0f);
            } while (!(var1 = (var2 = this.posX - this.targetX) * var2 + (var4 = this.posY - this.targetY) * var4 + (var6 = this.posZ - this.targetZ) * var6 > 100.0));
            this.target = null;
        }
    }

    private float simplifyAngle(double par1) {
        return (float)MathHelper.wrapAngleTo180_double(par1);
    }

    private boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB) {
        int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var3 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean var8 = false;
        boolean var9 = false;
        int var10 = var2;
        while (var10 <= var5) {
            int var11 = var3;
            while (var11 <= var6) {
                int var12 = var4;
                while (var12 <= var7) {
                    Block var13 = this.worldObj.getBlock(var10, var11, var12);
                    if (var13.getMaterial() != Material.air) {
                        if (var13 != Blocks.obsidian && var13 != Blocks.end_stone && var13 != Blocks.bedrock && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                            var9 = this.worldObj.setBlockToAir(var10, var11, var12) || var9;
                        } else {
                            var8 = true;
                        }
                    }
                    ++var12;
                }
                ++var11;
            }
            ++var10;
        }
        if (var9) {
            double var16 = par1AxisAlignedBB.minX + (par1AxisAlignedBB.maxX - par1AxisAlignedBB.minX) * (double)this.rand.nextFloat();
            double var17 = par1AxisAlignedBB.minY + (par1AxisAlignedBB.maxY - par1AxisAlignedBB.minY) * (double)this.rand.nextFloat();
            double var14 = par1AxisAlignedBB.minZ + (par1AxisAlignedBB.maxZ - par1AxisAlignedBB.minZ) * (double)this.rand.nextFloat();
            this.worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0, 0.0, 0.0);
        }
        return var8;
    }

    @Override
    public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, float par3) {
        if (par1EntityDragonPart != this.dragonPartHead) {
            par3 = par3 / 4.0f + 1.0f;
        }
        float var4 = this.rotationYaw * 3.1415927f / 180.0f;
        float var5 = MathHelper.sin(var4);
        float var6 = MathHelper.cos(var4);
        this.targetX = this.posX + (double)(var5 * 5.0f) + (double)((this.rand.nextFloat() - 0.5f) * 2.0f);
        this.targetY = this.posY + (double)(this.rand.nextFloat() * 3.0f) + 1.0;
        this.targetZ = this.posZ - (double)(var6 * 5.0f) + (double)((this.rand.nextFloat() - 0.5f) * 2.0f);
        this.target = null;
        if (par2DamageSource.getEntity() instanceof EntityPlayer || par2DamageSource.isExplosion()) {
            this.func_82195_e(par2DamageSource, par3);
        }
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return false;
    }

    protected boolean func_82195_e(DamageSource par1DamageSource, float par2) {
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void onDeathUpdate() {
        ++this.deathTicks;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            float var1 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            float var2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            float var3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.worldObj.spawnParticle("hugeexplosion", this.posX + (double)var1, this.posY + 2.0 + (double)var2, this.posZ + (double)var3, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isClient) {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0) {
                int var4 = 1000;
                while (var4 > 0) {
                    int var5 = EntityXPOrb.getXPSplit(var4);
                    var4 -= var5;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
                }
            }
            if (this.deathTicks == 1) {
                this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }
        }
        this.moveEntity(0.0, 0.10000000149011612, 0.0);
        this.renderYawOffset = this.rotationYaw += 20.0f;
        if (this.deathTicks == 200 && !this.worldObj.isClient) {
            int var4 = 2000;
            while (var4 > 0) {
                int var5 = EntityXPOrb.getXPSplit(var4);
                var4 -= var5;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
            }
            this.createEnderPortal(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            this.setDead();
        }
    }

    private void createEnderPortal(int par1, int par2) {
        int var3 = 64;
        BlockEndPortal.field_149948_a = true;
        int var4 = 4;
        int var5 = var3 - 1;
        while (var5 <= var3 + 32) {
            int var6 = par1 - var4;
            while (var6 <= par1 + var4) {
                int var7 = par2 - var4;
                while (var7 <= par2 + var4) {
                    double var8 = var6 - par1;
                    double var10 = var7 - par2;
                    double var12 = var8 * var8 + var10 * var10;
                    if (var12 <= ((double)var4 - 0.5) * ((double)var4 - 0.5)) {
                        if (var5 < var3) {
                            if (var12 <= ((double)(var4 - 1) - 0.5) * ((double)(var4 - 1) - 0.5)) {
                                this.worldObj.setBlock(var6, var5, var7, Blocks.bedrock);
                            }
                        } else if (var5 > var3) {
                            this.worldObj.setBlock(var6, var5, var7, Blocks.air);
                        } else if (var12 > ((double)(var4 - 1) - 0.5) * ((double)(var4 - 1) - 0.5)) {
                            this.worldObj.setBlock(var6, var5, var7, Blocks.bedrock);
                        } else {
                            this.worldObj.setBlock(var6, var5, var7, Blocks.end_portal);
                        }
                    }
                    ++var7;
                }
                ++var6;
            }
            ++var5;
        }
        this.worldObj.setBlock(par1, var3 + 0, par2, Blocks.bedrock);
        this.worldObj.setBlock(par1, var3 + 1, par2, Blocks.bedrock);
        this.worldObj.setBlock(par1, var3 + 2, par2, Blocks.bedrock);
        this.worldObj.setBlock(par1 - 1, var3 + 2, par2, Blocks.torch);
        this.worldObj.setBlock(par1 + 1, var3 + 2, par2, Blocks.torch);
        this.worldObj.setBlock(par1, var3 + 2, par2 - 1, Blocks.torch);
        this.worldObj.setBlock(par1, var3 + 2, par2 + 1, Blocks.torch);
        this.worldObj.setBlock(par1, var3 + 3, par2, Blocks.bedrock);
        this.worldObj.setBlock(par1, var3 + 4, par2, Blocks.dragon_egg);
        BlockEndPortal.field_149948_a = false;
    }

    @Override
    public void despawnEntity() {
    }

    @Override
    public Entity[] getParts() {
        return this.dragonPartArray;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public World func_82194_d() {
        return this.worldObj;
    }

    @Override
    protected String getLivingSound() {
        return "mob.enderdragon.growl";
    }

    @Override
    protected String getHurtSound() {
        return "mob.enderdragon.hit";
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
}

