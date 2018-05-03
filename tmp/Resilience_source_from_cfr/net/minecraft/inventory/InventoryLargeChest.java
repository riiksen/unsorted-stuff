/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryLargeChest
implements IInventory {
    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;
    private static final String __OBFID = "CL_00001507";

    public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory) {
        this.name = par1Str;
        if (par2IInventory == null) {
            par2IInventory = par3IInventory;
        }
        if (par3IInventory == null) {
            par3IInventory = par2IInventory;
        }
        this.upperChest = par2IInventory;
        this.lowerChest = par3IInventory;
    }

    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    public boolean isPartOfLargeChest(IInventory par1IInventory) {
        if (this.upperChest != par1IInventory && this.lowerChest != par1IInventory) {
            return false;
        }
        return true;
    }

    @Override
    public String getInventoryName() {
        return this.upperChest.isInventoryNameLocalized() ? this.upperChest.getInventoryName() : (this.lowerChest.isInventoryNameLocalized() ? this.lowerChest.getInventoryName() : this.name);
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (!this.upperChest.isInventoryNameLocalized() && !this.lowerChest.isInventoryNameLocalized()) {
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(par1);
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(par1 - this.upperChest.getSizeInventory(), par2) : this.upperChest.decrStackSize(par1, par2);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlotOnClosing(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(par1);
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        if (par1 >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(par1 - this.upperChest.getSizeInventory(), par2ItemStack);
        } else {
            this.upperChest.setInventorySlotContents(par1, par2ItemStack);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }

    @Override
    public void onInventoryChanged() {
        this.upperChest.onInventoryChanged();
        this.lowerChest.onInventoryChanged();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        if (this.upperChest.isUseableByPlayer(par1EntityPlayer) && this.lowerChest.isUseableByPlayer(par1EntityPlayer)) {
            return true;
        }
        return false;
    }

    @Override
    public void openInventory() {
        this.upperChest.openInventory();
        this.lowerChest.openInventory();
    }

    @Override
    public void closeInventory() {
        this.upperChest.closeInventory();
        this.lowerChest.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}

