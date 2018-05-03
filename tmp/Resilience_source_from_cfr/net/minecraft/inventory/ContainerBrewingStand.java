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
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntityBrewingStand;

public class ContainerBrewingStand
extends Container {
    private TileEntityBrewingStand tileBrewingStand;
    private final Slot theSlot;
    private int brewTime;
    private static final String __OBFID = "CL_00001737";

    public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand) {
        this.tileBrewingStand = par2TileEntityBrewingStand;
        this.addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        this.addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        this.addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new Ingredient(par2TileEntityBrewingStand, 3, 79, 17));
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
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int var1 = 0;
        while (var1 < this.crafters.size()) {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.brewTime != this.tileBrewingStand.func_145935_i()) {
                var2.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
            }
            ++var1;
        }
        this.brewTime = this.tileBrewingStand.func_145935_i();
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.tileBrewingStand.func_145938_d(par2);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if ((par2 < 0 || par2 > 2) && par2 != 3) {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(var5) ? !this.mergeItemStack(var5, 3, 4, false) : (Potion.canHoldPotion(var3) ? !this.mergeItemStack(var5, 0, 3, false) : (par2 >= 4 && par2 < 31 ? !this.mergeItemStack(var5, 31, 40, false) : (par2 >= 31 && par2 < 40 ? !this.mergeItemStack(var5, 4, 31, false) : !this.mergeItemStack(var5, 4, 40, false))))) {
                    return null;
                }
            } else {
                if (!this.mergeItemStack(var5, 4, 40, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
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

    class Ingredient
    extends Slot {
        private static final String __OBFID = "CL_00001738";

        public Ingredient(IInventory par2IInventory, int par3, int par4, int par5) {
            super(par2IInventory, par3, par4, par5);
        }

        @Override
        public boolean isItemValid(ItemStack par1ItemStack) {
            return par1ItemStack != null ? par1ItemStack.getItem().isPotionIngredient(par1ItemStack) : false;
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }

    static class Potion
    extends Slot {
        private EntityPlayer player;
        private static final String __OBFID = "CL_00001740";

        public Potion(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5) {
            super(par2IInventory, par3, par4, par5);
            this.player = par1EntityPlayer;
        }

        @Override
        public boolean isItemValid(ItemStack par1ItemStack) {
            return Potion.canHoldPotion(par1ItemStack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
            if (par2ItemStack.getItem() == Items.potionitem && par2ItemStack.getItemDamage() > 0) {
                this.player.addStat(AchievementList.potion, 1);
            }
            super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        }

        public static boolean canHoldPotion(ItemStack par0ItemStack) {
            if (par0ItemStack != null && (par0ItemStack.getItem() == Items.potionitem || par0ItemStack.getItem() == Items.glass_bottle)) {
                return true;
            }
            return false;
        }
    }

}

