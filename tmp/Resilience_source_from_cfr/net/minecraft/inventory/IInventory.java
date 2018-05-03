/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IInventory {
    public int getSizeInventory();

    public ItemStack getStackInSlot(int var1);

    public ItemStack decrStackSize(int var1, int var2);

    public ItemStack getStackInSlotOnClosing(int var1);

    public void setInventorySlotContents(int var1, ItemStack var2);

    public String getInventoryName();

    public boolean isInventoryNameLocalized();

    public int getInventoryStackLimit();

    public void onInventoryChanged();

    public boolean isUseableByPlayer(EntityPlayer var1);

    public void openInventory();

    public void closeInventory();

    public boolean isItemValidForSlot(int var1, ItemStack var2);
}

