/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHopper
extends Container {
    private final IInventory field_94538_a;
    private static final String __OBFID = "CL_00001750";

    public ContainerHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory) {
        this.field_94538_a = par2IInventory;
        par2IInventory.openInventory();
        int var3 = 51;
        int var4 = 0;
        while (var4 < par2IInventory.getSizeInventory()) {
            this.addSlotToContainer(new Slot(par2IInventory, var4, 44 + var4 * 18, 20));
            ++var4;
        }
        var4 = 0;
        while (var4 < 3) {
            int var5 = 0;
            while (var5 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + var3));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 58 + var3));
            ++var4;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.field_94538_a.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < this.field_94538_a.getSizeInventory() ? !this.mergeItemStack(var5, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true) : !this.mergeItemStack(var5, 0, this.field_94538_a.getSizeInventory(), false)) {
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
        this.field_94538_a.closeInventory();
    }
}

