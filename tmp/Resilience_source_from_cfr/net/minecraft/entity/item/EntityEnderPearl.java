/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl
extends EntityThrowable {
    private static final String __OBFID = "CL_00001725";

    public EntityEnderPearl(World par1World) {
        super(par1World);
    }

    public EntityEnderPearl(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    public EntityEnderPearl(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        int var2 = 0;
        while (var2 < 32) {
            this.worldObj.spawnParticle("portal", this.posX, this.posY + this.rand.nextDouble() * 2.0, this.posZ, this.rand.nextGaussian(), 0.0, this.rand.nextGaussian());
            ++var2;
        }
        if (!this.worldObj.isClient) {
            if (this.getThrower() != null && this.getThrower() instanceof EntityPlayerMP) {
                EntityPlayerMP var3 = (EntityPlayerMP)this.getThrower();
                if (var3.playerNetServerHandler.func_147362_b().isChannelOpen() && var3.worldObj == this.worldObj) {
                    if (this.getThrower().isRiding()) {
                        this.getThrower().mountEntity(null);
                    }
                    this.getThrower().setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    this.getThrower().fallDistance = 0.0f;
                    this.getThrower().attackEntityFrom(DamageSource.fall, 5.0f);
                }
            }
            this.setDead();
        }
    }
}

