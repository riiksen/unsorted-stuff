/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant
extends Container {
    private IMerchant theMerchant;
    private InventoryMerchant merchantInventory;
    private final World theWorld;
    private static final String __OBFID = "CL_00001757";

    public ContainerMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World) {
        this.theMerchant = par2IMerchant;
        this.theWorld = par3World;
        this.merchantInventory = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
        this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
        this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
        this.addSlotToContainer(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, this.merchantInventory, 2, 120, 53));
        int var4 = 0;
        while (var4 < 3) {
            int var5 = 0;
            while (var5 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
            ++var4;
        }
    }

    public InventoryMerchant getMerchantInventory() {
        return this.merchantInventory;
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(par1IInventory);
    }

    public void setCurrentRecipeIndex(int par1) {
        this.merchantInventory.setCurrentRecipeIndex(par1);
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        if (this.theMerchant.getCustomer() == par1EntityPlayer) {
            return true;
        }
        return false;
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
            } else if (par2 != 0 && par2 != 1 ? (par2 >= 3 && par2 < 30 ? !this.mergeItemStack(var5, 30, 39, false) : par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false)) : !this.mergeItemStack(var5, 3, 39, false)) {
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

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.theMerchant.setCustomer(null);
        super.onContainerClosed(par1EntityPlayer);
        if (!this.theWorld.isClient) {
            ItemStack var2 = this.merchantInventory.getStackInSlotOnClosing(0);
            if (var2 != null) {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
            }
            if ((var2 = this.merchantInventory.getStackInSlotOnClosing(1)) != null) {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
            }
        }
    }
}

