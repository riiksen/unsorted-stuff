/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget
extends EntityAIBase {
    protected EntityCreature taskOwner;
    protected boolean shouldCheckSight;
    private boolean nearbyOnly;
    private int targetSearchStatus;
    private int targetSearchDelay;
    private int field_75298_g;
    private static final String __OBFID = "CL_00001626";

    public EntityAITarget(EntityCreature par1EntityCreature, boolean par2) {
        this(par1EntityCreature, par2, false);
    }

    public EntityAITarget(EntityCreature par1EntityCreature, boolean par2, boolean par3) {
        this.taskOwner = par1EntityCreature;
        this.shouldCheckSight = par2;
        this.nearbyOnly = par3;
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase var1 = this.taskOwner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            return false;
        }
        double var2 = this.getTargetDistance();
        if (this.taskOwner.getDistanceSqToEntity(var1) > var2 * var2) {
            return false;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(var1)) {
                this.field_75298_g = 0;
            } else if (++this.field_75298_g > 60) {
                return false;
            }
        }
        if (var1 instanceof EntityPlayerMP && ((EntityPlayerMP)var1).theItemInWorldManager.isCreative()) {
            return false;
        }
        return true;
    }

    protected double getTargetDistance() {
        IAttributeInstance var1 = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        return var1 == null ? 16.0 : var1.getAttributeValue();
    }

    @Override
    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.field_75298_g = 0;
    }

    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget(null);
    }

    protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2) {
        if (par1EntityLivingBase == null) {
            return false;
        }
        if (par1EntityLivingBase == this.taskOwner) {
            return false;
        }
        if (!par1EntityLivingBase.isEntityAlive()) {
            return false;
        }
        if (!this.taskOwner.canAttackClass(par1EntityLivingBase.getClass())) {
            return false;
        }
        if (this.taskOwner instanceof IEntityOwnable && StringUtils.isNotEmpty((CharSequence)((IEntityOwnable)((Object)this.taskOwner)).getOwnerName())) {
            if (par1EntityLivingBase instanceof IEntityOwnable && ((IEntityOwnable)((Object)this.taskOwner)).getOwnerName().equals(((IEntityOwnable)((Object)par1EntityLivingBase)).getOwnerName())) {
                return false;
            }
            if (par1EntityLivingBase == ((IEntityOwnable)((Object)this.taskOwner)).getOwner()) {
                return false;
            }
        } else if (par1EntityLivingBase instanceof EntityPlayer && !par2 && ((EntityPlayer)par1EntityLivingBase).capabilities.disableDamage) {
            return false;
        }
        if (!this.taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ))) {
            return false;
        }
        if (this.shouldCheckSight && !this.taskOwner.getEntitySenses().canSee(par1EntityLivingBase)) {
            return false;
        }
        if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
                this.targetSearchStatus = 0;
            }
            if (this.targetSearchStatus == 0) {
                int n = this.targetSearchStatus = this.canEasilyReach(par1EntityLivingBase) ? 1 : 2;
            }
            if (this.targetSearchStatus == 2) {
                return false;
            }
        }
        return true;
    }

    private boolean canEasilyReach(EntityLivingBase par1EntityLivingBase) {
        int var5;
        this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
        PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLivingBase);
        if (var2 == null) {
            return false;
        }
        PathPoint var3 = var2.getFinalPathPoint();
        if (var3 == null) {
            return false;
        }
        int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLivingBase.posX);
        if ((double)(var4 * var4 + (var5 = var3.zCoord - MathHelper.floor_double(par1EntityLivingBase.posZ)) * var5) <= 2.25) {
            return true;
        }
        return false;
    }
}

