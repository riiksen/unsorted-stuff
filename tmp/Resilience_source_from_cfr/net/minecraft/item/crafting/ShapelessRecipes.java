/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ShapelessRecipes
implements IRecipe {
    private final ItemStack recipeOutput;
    private final List recipeItems;
    private static final String __OBFID = "CL_00000094";

    public ShapelessRecipes(ItemStack par1ItemStack, List par2List) {
        this.recipeOutput = par1ItemStack;
        this.recipeItems = par2List;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ArrayList var3 = new ArrayList(this.recipeItems);
        int var4 = 0;
        while (var4 < 3) {
            int var5 = 0;
            while (var5 < 3) {
                ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);
                if (var6 != null) {
                    boolean var7 = false;
                    for (ItemStack var9 : var3) {
                        if (var6.getItem() != var9.getItem() || var9.getItemDamage() != 32767 && var6.getItemDamage() != var9.getItemDamage()) continue;
                        var7 = true;
                        var3.remove(var9);
                        break;
                    }
                    if (!var7) {
                        return false;
                    }
                }
                ++var5;
            }
            ++var4;
        }
        return var3.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}

