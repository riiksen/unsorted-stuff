/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLeashKnot
extends EntityHanging {
    private static final String __OBFID = "CL_00001548";

    public EntityLeashKnot(World par1World) {
        super(par1World);
    }

    public EntityLeashKnot(World par1World, int par2, int par3, int par4) {
        super(par1World, par2, par3, par4, 0);
        this.setPosition((double)par2 + 0.5, (double)par3 + 0.5, (double)par4 + 0.5);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void setDirection(int par1) {
    }

    @Override
    public int getWidthPixels() {
        return 9;
    }

    @Override
    public int getHeightPixels() {
        return 9;
    }

    @Override
    public boolean isInRangeToRenderDist(double par1) {
        if (par1 < 1024.0) {
            return true;
        }
        return false;
    }

    @Override
    public void onBroken(Entity par1Entity) {
    }

    @Override
    public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound) {
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        double var4;
        List var6;
        ItemStack var2 = par1EntityPlayer.getHeldItem();
        boolean var3 = false;
        if (var2 != null && var2.getItem() == Items.lead && !this.worldObj.isClient) {
            var4 = 7.0;
            var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
            if (var6 != null) {
                for (EntityLiving var8 : var6) {
                    if (!var8.getLeashed() || var8.getLeashedToEntity() != par1EntityPlayer) continue;
                    var8.setLeashedToEntity(this, true);
                    var3 = true;
                }
            }
        }
        if (!this.worldObj.isClient && !var3) {
            this.setDead();
            if (par1EntityPlayer.capabilities.isCreativeMode) {
                var4 = 7.0;
                var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
                if (var6 != null) {
                    for (EntityLiving var8 : var6) {
                        if (!var8.getLeashed() || var8.getLeashedToEntity() != this) continue;
                        var8.clearLeashed(true, false);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean onValidSurface() {
        if (this.worldObj.getBlock(this.field_146063_b, this.field_146064_c, this.field_146062_d).getRenderType() == 11) {
            return true;
        }
        return false;
    }

    public static EntityLeashKnot func_110129_a(World par0World, int par1, int par2, int par3) {
        EntityLeashKnot var4 = new EntityLeashKnot(par0World, par1, par2, par3);
        var4.forceSpawn = true;
        par0World.spawnEntityInWorld(var4);
        return var4;
    }

    public static EntityLeashKnot getKnotForBlock(World par0World, int par1, int par2, int par3) {
        List var4 = par0World.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getAABBPool().getAABB((double)par1 - 1.0, (double)par2 - 1.0, (double)par3 - 1.0, (double)par1 + 1.0, (double)par2 + 1.0, (double)par3 + 1.0));
        if (var4 != null) {
            for (EntityLeashKnot var6 : var4) {
                if (var6.field_146063_b != par1 || var6.field_146064_c != par2 || var6.field_146062_d != par3) continue;
                return var6;
            }
        }
        return null;
    }
}

