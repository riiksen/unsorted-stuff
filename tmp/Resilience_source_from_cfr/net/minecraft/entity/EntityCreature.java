/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityCreature
extends EntityLiving {
    public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
    public static final AttributeModifier field_110181_i = new AttributeModifier(field_110179_h, "Fleeing speed bonus", 2.0, 2).setSaved(false);
    private PathEntity pathToEntity;
    protected Entity entityToAttack;
    protected boolean hasAttacked;
    protected int fleeingTick;
    private ChunkCoordinates homePosition = new ChunkCoordinates(0, 0, 0);
    private float maximumHomeDistance = -1.0f;
    private EntityAIBase field_110178_bs;
    private boolean field_110180_bt;
    private static final String __OBFID = "CL_00001558";

    public EntityCreature(World par1World) {
        super(par1World);
        this.field_110178_bs = new EntityAIMoveTowardsRestriction(this, 1.0);
    }

    protected boolean isMovementCeased() {
        return false;
    }

    @Override
    protected void updateEntityActionState() {
        this.worldObj.theProfiler.startSection("ai");
        if (this.fleeingTick > 0 && --this.fleeingTick == 0) {
            IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(field_110181_i);
        }
        this.hasAttacked = this.isMovementCeased();
        float var21 = 16.0f;
        if (this.entityToAttack == null) {
            this.entityToAttack = this.findPlayerToAttack();
            if (this.entityToAttack != null) {
                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var21, true, false, false, true);
            }
        } else if (this.entityToAttack.isEntityAlive()) {
            float var2 = this.entityToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.entityToAttack)) {
                this.attackEntity(this.entityToAttack, var2);
            }
        } else {
            this.entityToAttack = null;
        }
        if (this.entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative()) {
            this.entityToAttack = null;
        }
        this.worldObj.theProfiler.endSection();
        if (!(this.hasAttacked || this.entityToAttack == null || this.pathToEntity != null && this.rand.nextInt(20) != 0)) {
            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var21, true, false, false, true);
        } else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
            this.updateWanderPath();
        }
        int var22 = MathHelper.floor_double(this.boundingBox.minY + 0.5);
        boolean var3 = this.isInWater();
        boolean var4 = this.handleLavaMovement();
        this.rotationPitch = 0.0f;
        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 var5 = this.pathToEntity.getPosition(this);
            double var6 = this.width * 2.0f;
            while (var5 != null && var5.squareDistanceTo(this.posX, var5.yCoord, this.posZ) < var6 * var6) {
                this.pathToEntity.incrementPathIndex();
                if (this.pathToEntity.isFinished()) {
                    var5 = null;
                    this.pathToEntity = null;
                    continue;
                }
                var5 = this.pathToEntity.getPosition(this);
            }
            this.isJumping = false;
            if (var5 != null) {
                double var8 = var5.xCoord - this.posX;
                double var10 = var5.zCoord - this.posZ;
                double var12 = var5.yCoord - (double)var22;
                float var14 = (float)(Math.atan2(var10, var8) * 180.0 / 3.141592653589793) - 90.0f;
                float var15 = MathHelper.wrapAngleTo180_float(var14 - this.rotationYaw);
                this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                if (var15 > 30.0f) {
                    var15 = 30.0f;
                }
                if (var15 < -30.0f) {
                    var15 = -30.0f;
                }
                this.rotationYaw += var15;
                if (this.hasAttacked && this.entityToAttack != null) {
                    double var16 = this.entityToAttack.posX - this.posX;
                    double var18 = this.entityToAttack.posZ - this.posZ;
                    float var20 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(var18, var16) * 180.0 / 3.141592653589793) - 90.0f;
                    var15 = (var20 - this.rotationYaw + 90.0f) * 3.1415927f / 180.0f;
                    this.moveStrafing = (- MathHelper.sin(var15)) * this.moveForward * 1.0f;
                    this.moveForward = MathHelper.cos(var15) * this.moveForward * 1.0f;
                }
                if (var12 > 0.0) {
                    this.isJumping = true;
                }
            }
            if (this.entityToAttack != null) {
                this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
            }
            if (this.isCollidedHorizontally && !this.hasPath()) {
                this.isJumping = true;
            }
            if (this.rand.nextFloat() < 0.8f && (var3 || var4)) {
                this.isJumping = true;
            }
            this.worldObj.theProfiler.endSection();
        } else {
            super.updateEntityActionState();
            this.pathToEntity = null;
        }
    }

    protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean var1 = false;
        int var2 = -1;
        int var3 = -1;
        int var4 = -1;
        float var5 = -99999.0f;
        int var6 = 0;
        while (var6 < 10) {
            int var9;
            int var8;
            int var7 = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0);
            float var10 = this.getBlockPathWeight(var7, var8 = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0), var9 = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0));
            if (var10 > var5) {
                var5 = var10;
                var2 = var7;
                var3 = var8;
                var4 = var9;
                var1 = true;
            }
            ++var6;
        }
        if (var1) {
            this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, var2, var3, var4, 10.0f, true, false, false, true);
        }
        this.worldObj.theProfiler.endSection();
    }

    protected void attackEntity(Entity par1Entity, float par2) {
    }

    public float getBlockPathWeight(int par1, int par2, int par3) {
        return 0.0f;
    }

    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        if (super.getCanSpawnHere() && this.getBlockPathWeight(var1, var2, var3) >= 0.0f) {
            return true;
        }
        return false;
    }

    public boolean hasPath() {
        if (this.pathToEntity != null) {
            return true;
        }
        return false;
    }

    public void setPathToEntity(PathEntity par1PathEntity) {
        this.pathToEntity = par1PathEntity;
    }

    public Entity getEntityToAttack() {
        return this.entityToAttack;
    }

    public void setTarget(Entity par1Entity) {
        this.entityToAttack = par1Entity;
    }

    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistance(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    }

    public boolean isWithinHomeDistance(int par1, int par2, int par3) {
        return this.maximumHomeDistance == -1.0f ? true : this.homePosition.getDistanceSquared(par1, par2, par3) < this.maximumHomeDistance * this.maximumHomeDistance;
    }

    public void setHomeArea(int par1, int par2, int par3, int par4) {
        this.homePosition.set(par1, par2, par3);
        this.maximumHomeDistance = par4;
    }

    public ChunkCoordinates getHomePosition() {
        return this.homePosition;
    }

    public float func_110174_bM() {
        return this.maximumHomeDistance;
    }

    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }

    public boolean hasHome() {
        if (this.maximumHomeDistance != -1.0f) {
            return true;
        }
        return false;
    }

    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            Entity var1 = this.getLeashedToEntity();
            this.setHomeArea((int)var1.posX, (int)var1.posY, (int)var1.posZ, 5);
            float var2 = this.getDistanceToEntity(var1);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (var2 > 10.0f) {
                    this.clearLeashed(true, true);
                }
                return;
            }
            if (!this.field_110180_bt) {
                this.tasks.addTask(2, this.field_110178_bs);
                this.getNavigator().setAvoidsWater(false);
                this.field_110180_bt = true;
            }
            this.func_142017_o(var2);
            if (var2 > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(var1, 1.0);
            }
            if (var2 > 6.0f) {
                double var3 = (var1.posX - this.posX) / (double)var2;
                double var5 = (var1.posY - this.posY) / (double)var2;
                double var7 = (var1.posZ - this.posZ) / (double)var2;
                this.motionX += var3 * Math.abs(var3) * 0.4;
                this.motionY += var5 * Math.abs(var5) * 0.4;
                this.motionZ += var7 * Math.abs(var7) * 0.4;
            }
            if (var2 > 10.0f) {
                this.clearLeashed(true, true);
            }
        } else if (!this.getLeashed() && this.field_110180_bt) {
            this.field_110180_bt = false;
            this.tasks.removeTask(this.field_110178_bs);
            this.getNavigator().setAvoidsWater(true);
            this.detachHome();
        }
    }

    protected void func_142017_o(float par1) {
    }
}

