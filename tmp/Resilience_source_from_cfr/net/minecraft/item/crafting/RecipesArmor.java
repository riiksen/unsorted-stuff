/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesArmor {
    private String[][] recipePatterns = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] recipeItems = new Object[][]{{Items.leather, Blocks.fire, Items.iron_ingot, Items.diamond, Items.gold_ingot}, {Items.leather_helmet, Items.chainmail_helmet, Items.iron_helmet, Items.diamond_helmet, Items.golden_helmet}, {Items.leather_chestplate, Items.chainmail_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate}, {Items.leather_leggings, Items.chainmail_leggings, Items.iron_leggings, Items.diamond_leggings, Items.golden_leggings}, {Items.leather_boots, Items.chainmail_boots, Items.iron_boots, Items.diamond_boots, Items.golden_boots}};
    private static final String __OBFID = "CL_00000080";

    public void addRecipes(CraftingManager par1CraftingManager) {
        int var2 = 0;
        while (var2 < this.recipeItems[0].length) {
            Object var3 = this.recipeItems[0][var2];
            int var4 = 0;
            while (var4 < this.recipeItems.length - 1) {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), this.recipePatterns[var4], Character.valueOf('X'), var3);
                ++var4;
            }
            ++var2;
        }
    }
}

