/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipes
implements IRecipe {
    private int recipeWidth;
    private int recipeHeight;
    private ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    private boolean field_92101_f;
    private static final String __OBFID = "CL_00000093";

    public ShapedRecipes(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack) {
        this.recipeWidth = par1;
        this.recipeHeight = par2;
        this.recipeItems = par3ArrayOfItemStack;
        this.recipeOutput = par4ItemStack;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        int var3 = 0;
        while (var3 <= 3 - this.recipeWidth) {
            int var4 = 0;
            while (var4 <= 3 - this.recipeHeight) {
                if (this.checkMatch(par1InventoryCrafting, var3, var4, true)) {
                    return true;
                }
                if (this.checkMatch(par1InventoryCrafting, var3, var4, false)) {
                    return true;
                }
                ++var4;
            }
            ++var3;
        }
        return false;
    }

    private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4) {
        int var5 = 0;
        while (var5 < 3) {
            int var6 = 0;
            while (var6 < 3) {
                ItemStack var10;
                int var7 = var5 - par2;
                int var8 = var6 - par3;
                ItemStack var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    var9 = par4 ? this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth] : this.recipeItems[var7 + var8 * this.recipeWidth];
                }
                if ((var10 = par1InventoryCrafting.getStackInRowAndColumn(var5, var6)) != null || var9 != null) {
                    if (var10 == null && var9 != null || var10 != null && var9 == null) {
                        return false;
                    }
                    if (var9.getItem() != var10.getItem()) {
                        return false;
                    }
                    if (var9.getItemDamage() != 32767 && var9.getItemDamage() != var10.getItemDamage()) {
                        return false;
                    }
                }
                ++var6;
            }
            ++var5;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        ItemStack var2 = this.getRecipeOutput().copy();
        if (this.field_92101_f) {
            int var3 = 0;
            while (var3 < par1InventoryCrafting.getSizeInventory()) {
                ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
                if (var4 != null && var4.hasTagCompound()) {
                    var2.setTagCompound((NBTTagCompound)var4.stackTagCompound.copy());
                }
                ++var3;
            }
        }
        return var2;
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    public ShapedRecipes func_92100_c() {
        this.field_92101_f = true;
        return this;
    }
}

