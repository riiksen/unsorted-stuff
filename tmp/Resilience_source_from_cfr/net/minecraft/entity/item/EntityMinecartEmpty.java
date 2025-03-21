/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty
extends EntityMinecart {
    private static final String __OBFID = "CL_00001677";

    public EntityMinecartEmpty(World par1World) {
        super(par1World);
    }

    public EntityMinecartEmpty(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity != par1EntityPlayer) {
            return false;
        }
        if (!this.worldObj.isClient) {
            par1EntityPlayer.mountEntity(this);
        }
        return true;
    }

    @Override
    public int getMinecartType() {
        return 0;
    }
}

