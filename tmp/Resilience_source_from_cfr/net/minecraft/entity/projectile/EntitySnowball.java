/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowball
extends EntityThrowable {
    private static final String __OBFID = "CL_00001722";

    public EntitySnowball(World par1World) {
        super(par1World);
    }

    public EntitySnowball(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    public EntitySnowball(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            int var2 = 0;
            if (par1MovingObjectPosition.entityHit instanceof EntityBlaze) {
                var2 = 3;
            }
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
        }
        int var3 = 0;
        while (var3 < 8) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            ++var3;
        }
        if (!this.worldObj.isClient) {
            this.setDead();
        }
    }
}

