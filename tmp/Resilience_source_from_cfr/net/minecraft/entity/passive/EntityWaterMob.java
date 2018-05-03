/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob
extends EntityCreature
implements IAnimals {
    private static final String __OBFID = "CL_00001653";

    public EntityWaterMob(World par1World) {
        super(par1World);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox);
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }

    @Override
    public void onEntityUpdate() {
        int var1 = this.getAir();
        super.onEntityUpdate();
        if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--var1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.drown, 2.0f);
            }
        } else {
            this.setAir(300);
        }
    }
}

