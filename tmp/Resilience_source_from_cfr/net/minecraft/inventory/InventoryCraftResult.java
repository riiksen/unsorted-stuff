/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryCraftResult
implements IInventory {
    private ItemStack[] stackResult = new ItemStack[1];
    private static final String __OBFID = "CL_00001760";

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.stackResult[0];
    }

    @Override
    public String getInventoryName() {
        return "Result";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.stackResult[0] != null) {
            ItemStack var3 = this.stackResult[0];
            this.stackResult[0] = null;
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.stackResult[0] != null) {
            ItemStack var2 = this.stackResult[0];
            this.stackResult[0] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.stackResult[0] = par2ItemStack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}

