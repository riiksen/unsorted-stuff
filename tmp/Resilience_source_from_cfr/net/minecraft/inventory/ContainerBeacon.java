/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;

public class ContainerBeacon
extends Container {
    private TileEntityBeacon theBeacon;
    private final BeaconSlot beaconSlot;
    private int field_82865_g;
    private int field_82867_h;
    private int field_82868_i;
    private static final String __OBFID = "CL_00001735";

    public ContainerBeacon(InventoryPlayer par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon) {
        this.theBeacon = par2TileEntityBeacon;
        this.beaconSlot = new BeaconSlot(par2TileEntityBeacon, 0, 136, 110);
        this.addSlotToContainer(this.beaconSlot);
        int var3 = 36;
        int var4 = 137;
        int var5 = 0;
        while (var5 < 3) {
            int var6 = 0;
            while (var6 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
                ++var6;
            }
            ++var5;
        }
        var5 = 0;
        while (var5 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var5, var3 + var5 * 18, 58 + var4));
            ++var5;
        }
        this.field_82865_g = par2TileEntityBeacon.func_145998_l();
        this.field_82867_h = par2TileEntityBeacon.func_146007_j();
        this.field_82868_i = par2TileEntityBeacon.func_146006_k();
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.field_82865_g);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.field_82867_h);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.field_82868_i);
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.theBeacon.func_146005_c(par2);
        }
        if (par1 == 1) {
            this.theBeacon.func_146001_d(par2);
        }
        if (par1 == 2) {
            this.theBeacon.func_146004_e(par2);
        }
    }

    public TileEntityBeacon func_148327_e() {
        return this.theBeacon;
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.theBeacon.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 1, 37, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            } else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(var5) && var5.stackSize == 1 ? !this.mergeItemStack(var5, 0, 1, false) : (par2 >= 1 && par2 < 28 ? !this.mergeItemStack(var5, 28, 37, false) : (par2 >= 28 && par2 < 37 ? !this.mergeItemStack(var5, 1, 28, false) : !this.mergeItemStack(var5, 1, 37, false)))) {
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

    class BeaconSlot
    extends Slot {
        private static final String __OBFID = "CL_00001736";

        public BeaconSlot(IInventory par2IInventory, int par3, int par4, int par5) {
            super(par2IInventory, par3, par4, par5);
        }

        @Override
        public boolean isItemValid(ItemStack par1ItemStack) {
            return par1ItemStack == null ? false : par1ItemStack.getItem() == Items.emerald || par1ItemStack.getItem() == Items.diamond || par1ItemStack.getItem() == Items.gold_ingot || par1ItemStack.getItem() == Items.iron_ingot;
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

}

