/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle
extends EntityThrowable {
    private static final String __OBFID = "CL_00001726";

    public EntityExpBottle(World par1World) {
        super(par1World);
    }

    public EntityExpBottle(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    public EntityExpBottle(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }

    @Override
    protected float func_70182_d() {
        return 0.7f;
    }

    @Override
    protected float func_70183_g() {
        return -20.0f;
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isClient) {
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            int var2 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
            while (var2 > 0) {
                int var3 = EntityXPOrb.getXPSplit(var2);
                var2 -= var3;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
            }
            this.setDead();
        }
    }
}

