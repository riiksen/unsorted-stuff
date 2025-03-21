/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnace
extends Container {
    private TileEntityFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private static final String __OBFID = "CL_00001748";

    public ContainerFurnace(InventoryPlayer par1InventoryPlayer, TileEntityFurnace par2TileEntityFurnace) {
        this.furnace = par2TileEntityFurnace;
        this.addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 17));
        this.addSlotToContainer(new Slot(par2TileEntityFurnace, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 116, 35));
        int var3 = 0;
        while (var3 < 3) {
            int var4 = 0;
            while (var4 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
            ++var3;
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.field_145961_j);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.field_145956_a);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.field_145963_i);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int var1 = 0;
        while (var1 < this.crafters.size()) {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.lastCookTime != this.furnace.field_145961_j) {
                var2.sendProgressBarUpdate(this, 0, this.furnace.field_145961_j);
            }
            if (this.lastBurnTime != this.furnace.field_145956_a) {
                var2.sendProgressBarUpdate(this, 1, this.furnace.field_145956_a);
            }
            if (this.lastItemBurnTime != this.furnace.field_145963_i) {
                var2.sendProgressBarUpdate(this, 2, this.furnace.field_145963_i);
            }
            ++var1;
        }
        this.lastCookTime = this.furnace.field_145961_j;
        this.lastBurnTime = this.furnace.field_145956_a;
        this.lastItemBurnTime = this.furnace.field_145963_i;
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.furnace.field_145961_j = par2;
        }
        if (par1 == 1) {
            this.furnace.field_145956_a = par2;
        }
        if (par1 == 2) {
            this.furnace.field_145963_i = par2;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.furnace.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 2) {
                if (!this.mergeItemStack(var5, 3, 39, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            } else if (par2 != 1 && par2 != 0 ? (FurnaceRecipes.smelting().func_151395_a(var5) != null ? !this.mergeItemStack(var5, 0, 1, false) : (TileEntityFurnace.func_145954_b(var5) ? !this.mergeItemStack(var5, 1, 2, false) : (par2 >= 3 && par2 < 30 ? !this.mergeItemStack(var5, 30, 39, false) : par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false)))) : !this.mergeItemStack(var5, 3, 39, false)) {
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

