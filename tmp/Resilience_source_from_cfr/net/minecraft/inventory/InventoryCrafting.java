/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryCrafting
implements IInventory {
    private ItemStack[] stackList;
    private int inventoryWidth;
    private Container eventHandler;
    private static final String __OBFID = "CL_00001743";

    public InventoryCrafting(Container par1Container, int par2, int par3) {
        int var4 = par2 * par3;
        this.stackList = new ItemStack[var4];
        this.eventHandler = par1Container;
        this.inventoryWidth = par2;
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return par1 >= this.getSizeInventory() ? null : this.stackList[par1];
    }

    public ItemStack getStackInRowAndColumn(int par1, int par2) {
        if (par1 >= 0 && par1 < this.inventoryWidth) {
            int var3 = par1 + par2 * this.inventoryWidth;
            return this.getStackInSlot(var3);
        }
        return null;
    }

    @Override
    public String getInventoryName() {
        return "container.crafting";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.stackList[par1] != null) {
            ItemStack var2 = this.stackList[par1];
            this.stackList[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.stackList[par1] != null) {
            if (this.stackList[par1].stackSize <= par2) {
                ItemStack var3 = this.stackList[par1];
                this.stackList[par1] = null;
                this.eventHandler.onCraftMatrixChanged(this);
                return var3;
            }
            ItemStack var3 = this.stackList[par1].splitStack(par2);
            if (this.stackList[par1].stackSize == 0) {
                this.stackList[par1] = null;
            }
            this.eventHandler.onCraftMatrixChanged(this);
            return var3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.stackList[par1] = par2ItemStack;
        this.eventHandler.onCraftMatrixChanged(this);
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

