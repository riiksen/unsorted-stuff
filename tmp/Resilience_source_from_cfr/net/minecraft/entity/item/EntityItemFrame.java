/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Map;
import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame
extends EntityHanging {
    private float itemDropChance = 1.0f;
    private static final String __OBFID = "CL_00001547";

    public EntityItemFrame(World par1World) {
        super(par1World);
    }

    public EntityItemFrame(World par1World, int par2, int par3, int par4, int par5) {
        super(par1World, par2, par3, par4, par5);
        this.setDirection(par5);
    }

    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(2, 5);
        this.getDataWatcher().addObject(3, Byte.valueOf(0));
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.getDisplayedItem() != null) {
            if (!this.worldObj.isClient) {
                this.func_146065_b(par1DamageSource.getEntity(), false);
                this.setDisplayedItem(null);
            }
            return true;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
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
        double var3 = 16.0;
        if (par1 < (var3 *= 64.0 * this.renderDistanceWeight) * var3) {
            return true;
        }
        return false;
    }

    @Override
    public void onBroken(Entity par1Entity) {
        this.func_146065_b(par1Entity, true);
    }

    public void func_146065_b(Entity p_146065_1_, boolean p_146065_2_) {
        ItemStack var3 = this.getDisplayedItem();
        if (p_146065_1_ instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)p_146065_1_;
            if (var4.capabilities.isCreativeMode) {
                this.removeFrameFromMap(var3);
                return;
            }
        }
        if (p_146065_2_) {
            this.entityDropItem(new ItemStack(Items.item_frame), 0.0f);
        }
        if (var3 != null && this.rand.nextFloat() < this.itemDropChance) {
            var3 = var3.copy();
            this.removeFrameFromMap(var3);
            this.entityDropItem(var3, 0.0f);
        }
    }

    private void removeFrameFromMap(ItemStack par1ItemStack) {
        if (par1ItemStack != null) {
            if (par1ItemStack.getItem() == Items.filled_map) {
                MapData var2 = ((ItemMap)par1ItemStack.getItem()).getMapData(par1ItemStack, this.worldObj);
                var2.playersVisibleOnMap.remove("frame-" + this.getEntityId());
            }
            par1ItemStack.setItemFrame(null);
        }
    }

    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(2);
    }

    public void setDisplayedItem(ItemStack par1ItemStack) {
        if (par1ItemStack != null) {
            par1ItemStack = par1ItemStack.copy();
            par1ItemStack.stackSize = 1;
            par1ItemStack.setItemFrame(this);
        }
        this.getDataWatcher().updateObject(2, par1ItemStack);
        this.getDataWatcher().setObjectWatched(2);
    }

    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(3);
    }

    public void setItemRotation(int par1) {
        this.getDataWatcher().updateObject(3, Byte.valueOf((byte)(par1 % 4)));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.getDisplayedItem() != null) {
            par1NBTTagCompound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setByte("ItemRotation", (byte)this.getRotation());
            par1NBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
        }
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        if (var2 != null && !var2.hasNoTags()) {
            this.setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
            this.setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));
            if (par1NBTTagCompound.func_150297_b("ItemDropChance", 99)) {
                this.itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
            }
        }
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.getDisplayedItem() == null) {
            ItemStack var2 = par1EntityPlayer.getHeldItem();
            if (var2 != null && !this.worldObj.isClient) {
                this.setDisplayedItem(var2);
                if (!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                }
            }
        } else if (!this.worldObj.isClient) {
            this.setItemRotation(this.getRotation() + 1);
        }
        return true;
    }
}

