/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg
extends EntityThrowable {
    private static final String __OBFID = "CL_00001724";

    public EntityEgg(World par1World) {
        super(par1World);
    }

    public EntityEgg(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    public EntityEgg(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        if (!this.worldObj.isClient && this.rand.nextInt(8) == 0) {
            int var2 = 1;
            if (this.rand.nextInt(32) == 0) {
                var2 = 4;
            }
            int var3 = 0;
            while (var3 < var2) {
                EntityChicken var4 = new EntityChicken(this.worldObj);
                var4.setGrowingAge(-24000);
                var4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                this.worldObj.spawnEntityInWorld(var4);
                ++var3;
            }
        }
        int var5 = 0;
        while (var5 < 8) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            ++var5;
        }
        if (!this.worldObj.isClient) {
            this.setDead();
        }
    }
}

