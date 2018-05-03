/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerChest
extends Container {
    private IInventory lowerChestInventory;
    private int numRows;
    private static final String __OBFID = "CL_00001742";

    public ContainerChest(IInventory par1IInventory, IInventory par2IInventory) {
        int var5;
        this.lowerChestInventory = par2IInventory;
        this.numRows = par2IInventory.getSizeInventory() / 9;
        par2IInventory.openInventory();
        int var3 = (this.numRows - 4) * 18;
        int var4 = 0;
        while (var4 < this.numRows) {
            var5 = 0;
            while (var5 < 9) {
                this.addSlotToContainer(new Slot(par2IInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 3) {
            var5 = 0;
            while (var5 < 9) {
                this.addSlotToContainer(new Slot(par1IInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 9) {
            this.addSlotToContainer(new Slot(par1IInventory, var4, 8 + var4 * 18, 161 + var3));
            ++var4;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < this.numRows * 9 ? !this.mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true) : !this.mergeItemStack(var5, 0, this.numRows * 9, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeInventory();
    }

    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
}

