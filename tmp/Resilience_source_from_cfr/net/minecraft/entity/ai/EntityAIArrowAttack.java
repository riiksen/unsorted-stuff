/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack
extends EntityAIBase {
    private final EntityLiving entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime = -1;
    private double entityMoveSpeed;
    private int field_75318_f;
    private int field_96561_g;
    private int maxRangedAttackTime;
    private float field_96562_i;
    private float field_82642_h;
    private static final String __OBFID = "CL_00001609";

    public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, float par5) {
        this(par1IRangedAttackMob, par2, par4, par4, par5);
    }

    public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, int par5, float par6) {
        if (!(par1IRangedAttackMob instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = par1IRangedAttackMob;
        this.entityHost = (EntityLiving)((Object)par1IRangedAttackMob);
        this.entityMoveSpeed = par2;
        this.field_96561_g = par4;
        this.maxRangedAttackTime = par5;
        this.field_96562_i = par6;
        this.field_82642_h = par6 * par6;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase var1 = this.entityHost.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        this.attackTarget = var1;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if (!this.shouldExecute() && this.entityHost.getNavigator().noPath()) {
            return false;
        }
        return true;
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }

    @Override
    public void updateTask() {
        double var1 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean var3 = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        this.field_75318_f = var3 ? ++this.field_75318_f : 0;
        if (var1 <= (double)this.field_82642_h && this.field_75318_f >= 20) {
            this.entityHost.getNavigator().clearPathEntity();
        } else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }
        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        if (--this.rangedAttackTime == 0) {
            float var4;
            if (var1 > (double)this.field_82642_h || !var3) {
                return;
            }
            float var5 = var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
            if (var4 < 0.1f) {
                var5 = 0.1f;
            }
            if (var5 > 1.0f) {
                var5 = 1.0f;
            }
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, var5);
            this.rangedAttackTime = MathHelper.floor_float(var4 * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
        } else if (this.rangedAttackTime < 0) {
            float var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
            this.rangedAttackTime = MathHelper.floor_float(var4 * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
        }
    }
}

