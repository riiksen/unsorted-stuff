/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityLargeFireball
extends EntityFireball {
    public int field_92057_e = 1;
    private static final String __OBFID = "CL_00001719";

    public EntityLargeFireball(World par1World) {
        super(par1World);
    }

    public EntityLargeFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
    }

    public EntityLargeFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7) {
        super(par1World, par2EntityLivingBase, par3, par5, par7);
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isClient) {
            if (par1MovingObjectPosition.entityHit != null) {
                par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
            }
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.field_92057_e, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ExplosionPower", this.field_92057_e);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("ExplosionPower", 99)) {
            this.field_92057_e = par1NBTTagCompound.getInteger("ExplosionPower");
        }
    }
}

