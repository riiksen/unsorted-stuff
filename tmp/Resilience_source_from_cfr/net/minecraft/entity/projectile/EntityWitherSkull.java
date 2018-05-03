/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityWitherSkull
extends EntityFireball {
    private static final String __OBFID = "CL_00001728";

    public EntityWitherSkull(World par1World) {
        super(par1World);
        this.setSize(0.3125f, 0.3125f);
    }

    public EntityWitherSkull(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7) {
        super(par1World, par2EntityLivingBase, par3, par5, par7);
        this.setSize(0.3125f, 0.3125f);
    }

    @Override
    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73f : super.getMotionFactor();
    }

    public EntityWitherSkull(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.setSize(0.3125f, 0.3125f);
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_) {
        float var7 = super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
        if (this.isInvulnerable() && p_145772_6_ != Blocks.bedrock && p_145772_6_ != Blocks.end_portal && p_145772_6_ != Blocks.end_portal_frame && p_145772_6_ != Blocks.command_block) {
            var7 = Math.min(0.8f, var7);
        }
        return var7;
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isClient) {
            if (par1MovingObjectPosition.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f) && !par1MovingObjectPosition.entityHit.isEntityAlive()) {
                        this.shootingEntity.heal(5.0f);
                    }
                } else {
                    par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0f);
                }
                if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
                    int var2 = 0;
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                        var2 = 10;
                    } else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        var2 = 40;
                    }
                    if (var2 > 0) {
                        ((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
                    }
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return false;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, Byte.valueOf(0));
    }

    public boolean isInvulnerable() {
        if (this.dataWatcher.getWatchableObjectByte(10) == 1) {
            return true;
        }
        return false;
    }

    public void setInvulnerable(boolean par1) {
        this.dataWatcher.updateObject(10, Byte.valueOf(par1 ? 1 : 0));
    }
}

