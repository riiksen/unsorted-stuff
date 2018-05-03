/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesWeapons {
    private String[][] recipePatterns = new String[][]{{"X", "X", "#"}};
    private Object[][] recipeItems = new Object[][]{{Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot}, {Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword, Items.golden_sword}};
    private static final String __OBFID = "CL_00000097";

    public void addRecipes(CraftingManager par1CraftingManager) {
        int var2 = 0;
        while (var2 < this.recipeItems[0].length) {
            Object var3 = this.recipeItems[0][var2];
            int var4 = 0;
            while (var4 < this.recipeItems.length - 1) {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), this.recipePatterns[var4], Character.valueOf('#'), Items.stick, Character.valueOf('X'), var3);
                ++var4;
            }
            ++var2;
        }
        par1CraftingManager.addRecipe(new ItemStack(Items.bow, 1), " #X", "# X", " #X", Character.valueOf('X'), Items.string, Character.valueOf('#'), Items.stick);
        par1CraftingManager.addRecipe(new ItemStack(Items.arrow, 4), "X", "#", "Y", Character.valueOf('Y'), Items.feather, Character.valueOf('X'), Items.flint, Character.valueOf('#'), Items.stick);
    }
}

