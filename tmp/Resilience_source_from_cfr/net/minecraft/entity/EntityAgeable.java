/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityAgeable
extends EntityCreature {
    private float field_98056_d = -1.0f;
    private float field_98057_e;
    private static final String __OBFID = "CL_00001530";

    public EntityAgeable(World par1World) {
        super(par1World);
    }

    public abstract EntityAgeable createChild(EntityAgeable var1);

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.spawn_egg) {
            EntityAgeable var4;
            Class var3;
            if (!this.worldObj.isClient && (var3 = EntityList.getClassFromID(var2.getItemDamage())) != null && var3.isAssignableFrom(this.getClass()) && (var4 = this.createChild(this)) != null) {
                var4.setGrowingAge(-24000);
                var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(var4);
                if (var2.hasDisplayName()) {
                    var4.setCustomNameTag(var2.getDisplayName());
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, new Integer(0));
    }

    public int getGrowingAge() {
        return this.dataWatcher.getWatchableObjectInt(12);
    }

    public void addGrowth(int par1) {
        int var2 = this.getGrowingAge();
        if ((var2 += par1 * 20) > 0) {
            var2 = 0;
        }
        this.setGrowingAge(var2);
    }

    public void setGrowingAge(int par1) {
        this.dataWatcher.updateObject(12, par1);
        this.setScaleForAge(this.isChild());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Age", this.getGrowingAge());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setGrowingAge(par1NBTTagCompound.getInteger("Age"));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isClient) {
            this.setScaleForAge(this.isChild());
        } else {
            int var1 = this.getGrowingAge();
            if (var1 < 0) {
                this.setGrowingAge(++var1);
            } else if (var1 > 0) {
                this.setGrowingAge(--var1);
            }
        }
    }

    @Override
    public boolean isChild() {
        if (this.getGrowingAge() < 0) {
            return true;
        }
        return false;
    }

    public void setScaleForAge(boolean par1) {
        this.setScale(par1 ? 0.5f : 1.0f);
    }

    @Override
    protected final void setSize(float par1, float par2) {
        boolean var3 = this.field_98056_d > 0.0f;
        this.field_98056_d = par1;
        this.field_98057_e = par2;
        if (!var3) {
            this.setScale(1.0f);
        }
    }

    protected final void setScale(float par1) {
        super.setSize(this.field_98056_d * par1, this.field_98057_e * par1);
    }
}

