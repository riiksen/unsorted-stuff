/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant
implements IMerchant {
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    private MerchantRecipeList recipeList;
    private static final String __OBFID = "CL_00001705";

    public NpcMerchant(EntityPlayer par1EntityPlayer) {
        this.customer = par1EntityPlayer;
        this.theMerchantInventory = new InventoryMerchant(par1EntityPlayer, this);
    }

    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }

    @Override
    public void setCustomer(EntityPlayer par1EntityPlayer) {
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer) {
        return this.recipeList;
    }

    @Override
    public void setRecipes(MerchantRecipeList par1MerchantRecipeList) {
        this.recipeList = par1MerchantRecipeList;
    }

    @Override
    public void useRecipe(MerchantRecipe par1MerchantRecipe) {
    }

    @Override
    public void func_110297_a_(ItemStack par1ItemStack) {
    }
}

