/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant
implements IInventory {
    private final IMerchant theMerchant;
    private ItemStack[] theInventory = new ItemStack[3];
    private final EntityPlayer thePlayer;
    private MerchantRecipe currentRecipe;
    private int currentRecipeIndex;
    private static final String __OBFID = "CL_00001756";

    public InventoryMerchant(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant) {
        this.thePlayer = par1EntityPlayer;
        this.theMerchant = par2IMerchant;
    }

    @Override
    public int getSizeInventory() {
        return this.theInventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.theInventory[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.theInventory[par1] != null) {
            if (par1 == 2) {
                ItemStack var3 = this.theInventory[par1];
                this.theInventory[par1] = null;
                return var3;
            }
            if (this.theInventory[par1].stackSize <= par2) {
                ItemStack var3 = this.theInventory[par1];
                this.theInventory[par1] = null;
                if (this.inventoryResetNeededOnSlotChange(par1)) {
                    this.resetRecipeAndSlots();
                }
                return var3;
            }
            ItemStack var3 = this.theInventory[par1].splitStack(par2);
            if (this.theInventory[par1].stackSize == 0) {
                this.theInventory[par1] = null;
            }
            if (this.inventoryResetNeededOnSlotChange(par1)) {
                this.resetRecipeAndSlots();
            }
            return var3;
        }
        return null;
    }

    private boolean inventoryResetNeededOnSlotChange(int par1) {
        if (par1 != 0 && par1 != 1) {
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.theInventory[par1] != null) {
            ItemStack var2 = this.theInventory[par1];
            this.theInventory[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.theInventory[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(par1)) {
            this.resetRecipeAndSlots();
        }
    }

    @Override
    public String getInventoryName() {
        return "mob.villager";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        if (this.theMerchant.getCustomer() == par1EntityPlayer) {
            return true;
        }
        return false;
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

    @Override
    public void onInventoryChanged() {
        this.resetRecipeAndSlots();
    }

    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack var1 = this.theInventory[0];
        ItemStack var2 = this.theInventory[1];
        if (var1 == null) {
            var1 = var2;
            var2 = null;
        }
        if (var1 == null) {
            this.setInventorySlotContents(2, null);
        } else {
            MerchantRecipeList var3 = this.theMerchant.getRecipes(this.thePlayer);
            if (var3 != null) {
                MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, this.currentRecipeIndex);
                if (var4 != null && !var4.isRecipeDisabled()) {
                    this.currentRecipe = var4;
                    this.setInventorySlotContents(2, var4.getItemToSell().copy());
                } else if (var2 != null) {
                    var4 = var3.canRecipeBeUsed(var2, var1, this.currentRecipeIndex);
                    if (var4 != null && !var4.isRecipeDisabled()) {
                        this.currentRecipe = var4;
                        this.setInventorySlotContents(2, var4.getItemToSell().copy());
                    } else {
                        this.setInventorySlotContents(2, null);
                    }
                } else {
                    this.setInventorySlotContents(2, null);
                }
            }
        }
        this.theMerchant.func_110297_a_(this.getStackInSlot(2));
    }

    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }

    public void setCurrentRecipeIndex(int par1) {
        this.currentRecipeIndex = par1;
        this.resetRecipeAndSlots();
    }
}

