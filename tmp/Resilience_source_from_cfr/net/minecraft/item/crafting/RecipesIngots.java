/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesIngots {
    private Object[][] recipeItems = new Object[][]{{Blocks.gold_block, new ItemStack(Items.gold_ingot, 9)}, {Blocks.iron_block, new ItemStack(Items.iron_ingot, 9)}, {Blocks.diamond_block, new ItemStack(Items.diamond, 9)}, {Blocks.emerald_block, new ItemStack(Items.emerald, 9)}, {Blocks.lapis_block, new ItemStack(Items.dye, 9, 4)}, {Blocks.redstone_block, new ItemStack(Items.redstone, 9)}, {Blocks.coal_block, new ItemStack(Items.coal, 9, 0)}, {Blocks.hay_block, new ItemStack(Items.wheat, 9)}};
    private static final String __OBFID = "CL_00000089";

    public void addRecipes(CraftingManager par1CraftingManager) {
        int var2 = 0;
        while (var2 < this.recipeItems.length) {
            Block var3 = (Block)this.recipeItems[var2][0];
            ItemStack var4 = (ItemStack)this.recipeItems[var2][1];
            par1CraftingManager.addRecipe(new ItemStack(var3), "###", "###", "###", Character.valueOf('#'), var4);
            par1CraftingManager.addRecipe(var4, "#", Character.valueOf('#'), var3);
            ++var2;
        }
        par1CraftingManager.addRecipe(new ItemStack(Items.gold_ingot), "###", "###", "###", Character.valueOf('#'), Items.gold_nugget);
        par1CraftingManager.addRecipe(new ItemStack(Items.gold_nugget, 9), "#", Character.valueOf('#'), Items.gold_ingot);
    }
}

