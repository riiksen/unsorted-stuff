/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;

public class SlotMerchantResult
extends Slot {
    private final InventoryMerchant theMerchantInventory;
    private EntityPlayer thePlayer;
    private int field_75231_g;
    private final IMerchant theMerchant;
    private static final String __OBFID = "CL_00001758";

    public SlotMerchantResult(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant, InventoryMerchant par3InventoryMerchant, int par4, int par5, int par6) {
        super(par3InventoryMerchant, par4, par5, par6);
        this.thePlayer = par1EntityPlayer;
        this.theMerchant = par2IMerchant;
        this.theMerchantInventory = par3InventoryMerchant;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        if (this.getHasStack()) {
            this.field_75231_g += Math.min(par1, this.getStack().stackSize);
        }
        return super.decrStackSize(par1);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2) {
        this.field_75231_g += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
        this.field_75231_g = 0;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        ItemStack var5;
        ItemStack var4;
        this.onCrafting(par2ItemStack);
        MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
        if (var3 != null && (this.func_75230_a(var3, var4 = this.theMerchantInventory.getStackInSlot(0), var5 = this.theMerchantInventory.getStackInSlot(1)) || this.func_75230_a(var3, var5, var4))) {
            this.theMerchant.useRecipe(var3);
            if (var4 != null && var4.stackSize <= 0) {
                var4 = null;
            }
            if (var5 != null && var5.stackSize <= 0) {
                var5 = null;
            }
            this.theMerchantInventory.setInventorySlotContents(0, var4);
            this.theMerchantInventory.setInventorySlotContents(1, var5);
        }
    }

    private boolean func_75230_a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack) {
        ItemStack var4 = par1MerchantRecipe.getItemToBuy();
        ItemStack var5 = par1MerchantRecipe.getSecondItemToBuy();
        if (par2ItemStack != null && par2ItemStack.getItem() == var4.getItem()) {
            if (var5 != null && par3ItemStack != null && var5.getItem() == par3ItemStack.getItem()) {
                par2ItemStack.stackSize -= var4.stackSize;
                par3ItemStack.stackSize -= var5.stackSize;
                return true;
            }
            if (var5 == null && par3ItemStack == null) {
                par2ItemStack.stackSize -= var4.stackSize;
                return true;
            }
        }
        return false;
    }
}

