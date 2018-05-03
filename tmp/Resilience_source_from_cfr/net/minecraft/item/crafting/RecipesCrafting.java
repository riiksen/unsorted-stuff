/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesCrafting {
    private static final String __OBFID = "CL_00000095";

    public void addRecipes(CraftingManager par1CraftingManager) {
        par1CraftingManager.addRecipe(new ItemStack(Blocks.chest), "###", "# #", "###", Character.valueOf('#'), Blocks.planks);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.trapped_chest), "#-", Character.valueOf('#'), Blocks.chest, Character.valueOf('-'), Blocks.tripwire_hook);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.ender_chest), "###", "#E#", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('E'), Items.ender_eye);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.furnace), "###", "# #", "###", Character.valueOf('#'), Blocks.cobblestone);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.crafting_table), "##", "##", Character.valueOf('#'), Blocks.planks);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone), "##", "##", Character.valueOf('#'), new ItemStack(Blocks.sand, 1, 0));
        par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 4, 2), "##", "##", Character.valueOf('#'), Blocks.sandstone);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 1, 1), "#", "#", Character.valueOf('#'), new ItemStack(Blocks.stone_slab, 1, 1));
        par1CraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 1, 1), "#", "#", Character.valueOf('#'), new ItemStack(Blocks.stone_slab, 1, 7));
        par1CraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 2, 2), "#", "#", Character.valueOf('#'), new ItemStack(Blocks.quartz_block, 1, 0));
        par1CraftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 4), "##", "##", Character.valueOf('#'), Blocks.stone);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.iron_bars, 16), "###", "###", Character.valueOf('#'), Items.iron_ingot);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.glass_pane, 16), "###", "###", Character.valueOf('#'), Blocks.glass);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), " R ", "RGR", " R ", Character.valueOf('R'), Items.redstone, Character.valueOf('G'), Blocks.glowstone);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.beacon, 1), "GGG", "GSG", "OOO", Character.valueOf('G'), Blocks.glass, Character.valueOf('S'), Items.nether_star, Character.valueOf('O'), Blocks.obsidian);
        par1CraftingManager.addRecipe(new ItemStack(Blocks.nether_brick, 1), "NN", "NN", Character.valueOf('N'), Items.netherbrick);
    }
}

