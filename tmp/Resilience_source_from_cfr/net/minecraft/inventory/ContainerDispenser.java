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
import net.minecraft.tileentity.TileEntityDispenser;

public class ContainerDispenser
extends Container {
    private TileEntityDispenser tileEntityDispenser;
    private static final String __OBFID = "CL_00001763";

    public ContainerDispenser(IInventory par1IInventory, TileEntityDispenser par2TileEntityDispenser) {
        int var4;
        this.tileEntityDispenser = par2TileEntityDispenser;
        int var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 3) {
                this.addSlotToContainer(new Slot(par2TileEntityDispenser, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 9) {
                this.addSlotToContainer(new Slot(par1IInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 9) {
            this.addSlotToContainer(new Slot(par1IInventory, var3, 8 + var3 * 18, 142));
            ++var3;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileEntityDispenser.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < 9 ? !this.mergeItemStack(var5, 9, 45, true) : !this.mergeItemStack(var5, 0, 9, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        return var3;
    }
}

