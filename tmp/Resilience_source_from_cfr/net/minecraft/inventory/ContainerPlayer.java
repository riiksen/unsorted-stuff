/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ContainerPlayer
extends Container {
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001754";

    public ContainerPlayer(InventoryPlayer par1InventoryPlayer, boolean par2, EntityPlayer par3EntityPlayer) {
        int var5;
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = par2;
        this.thePlayer = par3EntityPlayer;
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        int var4 = 0;
        while (var4 < 2) {
            var5 = 0;
            while (var5 < 2) {
                this.addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 4) {
            final int var44 = var4;
            this.addSlotToContainer(new Slot(par1InventoryPlayer, par1InventoryPlayer.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18){
                private static final String __OBFID = "CL_00001755";

                @Override
                public int getSlotStackLimit() {
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack par1ItemStack) {
                    return par1ItemStack == null ? false : (par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor)par1ItemStack.getItem()).armorType == var44 : (par1ItemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && par1ItemStack.getItem() != Items.skull ? false : var44 == 0));
                }

                @Override
                public IIcon getBackgroundIconIndex() {
                    return ItemArmor.func_94602_b(var44);
                }
            });
            ++var4;
        }
        var4 = 0;
        while (var4 < 3) {
            var5 = 0;
            while (var5 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
                ++var5;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
            ++var4;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        int var2 = 0;
        while (var2 < 4) {
            ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
            if (var3 != null) {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
            }
            ++var2;
        }
        this.craftResult.setInventorySlotContents(0, null);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            int var6;
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 9, 45, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            } else if (par2 >= 1 && par2 < 5 ? !this.mergeItemStack(var5, 9, 45, false) : (par2 >= 5 && par2 < 9 ? !this.mergeItemStack(var5, 9, 45, false) : (var3.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)var3.getItem()).armorType)).getHasStack() ? !this.mergeItemStack(var5, var6 = 5 + ((ItemArmor)var3.getItem()).armorType, var6 + 1, false) : (par2 >= 9 && par2 < 36 ? !this.mergeItemStack(var5, 36, 45, false) : (par2 >= 36 && par2 < 45 ? !this.mergeItemStack(var5, 9, 36, false) : !this.mergeItemStack(var5, 9, 45, false)))))) {
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
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
        if (par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot)) {
            return true;
        }
        return false;
    }

}

