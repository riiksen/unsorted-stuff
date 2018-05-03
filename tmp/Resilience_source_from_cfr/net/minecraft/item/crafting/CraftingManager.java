/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCloning;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraft.item.crafting.RecipesArmor;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.item.crafting.RecipesCrafting;
import net.minecraft.item.crafting.RecipesDyes;
import net.minecraft.item.crafting.RecipesFood;
import net.minecraft.item.crafting.RecipesIngots;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraft.item.crafting.RecipesMapExtending;
import net.minecraft.item.crafting.RecipesTools;
import net.minecraft.item.crafting.RecipesWeapons;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class CraftingManager {
    private static final CraftingManager instance = new CraftingManager();
    private List recipes = new ArrayList();
    private static final String __OBFID = "CL_00000090";

    public static final CraftingManager getInstance() {
        return instance;
    }

    private CraftingManager() {
        new RecipesTools().addRecipes(this);
        new RecipesWeapons().addRecipes(this);
        new RecipesIngots().addRecipes(this);
        new RecipesFood().addRecipes(this);
        new RecipesCrafting().addRecipes(this);
        new RecipesArmor().addRecipes(this);
        new RecipesDyes().addRecipes(this);
        this.recipes.add(new RecipesArmorDyes());
        this.recipes.add(new RecipeBookCloning());
        this.recipes.add(new RecipesMapCloning());
        this.recipes.add(new RecipesMapExtending());
        this.recipes.add(new RecipeFireworks());
        this.addRecipe(new ItemStack(Items.paper, 3), "###", Character.valueOf('#'), Items.reeds);
        this.addShapelessRecipe(new ItemStack(Items.book, 1), Items.paper, Items.paper, Items.paper, Items.leather);
        this.addShapelessRecipe(new ItemStack(Items.writable_book, 1), Items.book, new ItemStack(Items.dye, 1, 0), Items.feather);
        this.addRecipe(new ItemStack(Blocks.fence, 2), "###", "###", Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, 0), "###", "###", Character.valueOf('#'), Blocks.cobblestone);
        this.addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, 1), "###", "###", Character.valueOf('#'), Blocks.mossy_cobblestone);
        this.addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), "###", "###", Character.valueOf('#'), Blocks.nether_brick);
        this.addRecipe(new ItemStack(Blocks.fence_gate, 1), "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.jukebox, 1), "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.diamond);
        this.addRecipe(new ItemStack(Items.lead, 2), "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.string, Character.valueOf('O'), Items.slime_ball);
        this.addRecipe(new ItemStack(Blocks.noteblock, 1), "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.redstone);
        this.addRecipe(new ItemStack(Blocks.bookshelf, 1), "###", "XXX", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.book);
        this.addRecipe(new ItemStack(Blocks.snow, 1), "##", "##", Character.valueOf('#'), Items.snowball);
        this.addRecipe(new ItemStack(Blocks.snow_layer, 6), "###", Character.valueOf('#'), Blocks.snow);
        this.addRecipe(new ItemStack(Blocks.clay, 1), "##", "##", Character.valueOf('#'), Items.clay_ball);
        this.addRecipe(new ItemStack(Blocks.brick_block, 1), "##", "##", Character.valueOf('#'), Items.brick);
        this.addRecipe(new ItemStack(Blocks.glowstone, 1), "##", "##", Character.valueOf('#'), Items.glowstone_dust);
        this.addRecipe(new ItemStack(Blocks.quartz_block, 1), "##", "##", Character.valueOf('#'), Items.quartz);
        this.addRecipe(new ItemStack(Blocks.wool, 1), "##", "##", Character.valueOf('#'), Items.string);
        this.addRecipe(new ItemStack(Blocks.tnt, 1), "X#X", "#X#", "X#X", Character.valueOf('X'), Items.gunpowder, Character.valueOf('#'), Blocks.sand);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 3), "###", Character.valueOf('#'), Blocks.cobblestone);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "###", Character.valueOf('#'), Blocks.stone);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 1), "###", Character.valueOf('#'), Blocks.sandstone);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 4), "###", Character.valueOf('#'), Blocks.brick_block);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "###", Character.valueOf('#'), Blocks.stonebrick);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 6), "###", Character.valueOf('#'), Blocks.nether_brick);
        this.addRecipe(new ItemStack(Blocks.stone_slab, 6, 7), "###", Character.valueOf('#'), Blocks.quartz_block);
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 0), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 0));
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 2), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 2));
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 1), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 1));
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 3), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 3));
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4));
        this.addRecipe(new ItemStack(Blocks.wooden_slab, 6, 5), "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 5));
        this.addRecipe(new ItemStack(Blocks.ladder, 3), "# #", "###", "# #", Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Items.wooden_door, 1), "##", "##", "##", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.trapdoor, 2), "###", "###", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Items.iron_door, 1), "##", "##", "##", Character.valueOf('#'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.sign, 3), "###", "###", " X ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.stick);
        this.addRecipe(new ItemStack(Items.cake, 1), "AAA", "BEB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), Items.sugar, Character.valueOf('C'), Items.wheat, Character.valueOf('E'), Items.egg);
        this.addRecipe(new ItemStack(Items.sugar, 1), "#", Character.valueOf('#'), Items.reeds);
        this.addRecipe(new ItemStack(Blocks.planks, 4, 0), "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 0));
        this.addRecipe(new ItemStack(Blocks.planks, 4, 1), "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 1));
        this.addRecipe(new ItemStack(Blocks.planks, 4, 2), "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 2));
        this.addRecipe(new ItemStack(Blocks.planks, 4, 3), "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 3));
        this.addRecipe(new ItemStack(Blocks.planks, 4, 4), "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, 0));
        this.addRecipe(new ItemStack(Blocks.planks, 4, 5), "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, 1));
        this.addRecipe(new ItemStack(Items.stick, 4), "#", "#", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.torch, 4), "X", "#", Character.valueOf('X'), Items.coal, Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.torch, 4), "X", "#", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1), Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Items.bowl, 4), "# #", " # ", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Items.glass_bottle, 3), "# #", " # ", Character.valueOf('#'), Blocks.glass);
        this.addRecipe(new ItemStack(Blocks.rail, 16), "X X", "X#X", "X X", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.golden_rail, 6), "X X", "X#X", "XRX", Character.valueOf('X'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.activator_rail, 6), "XSX", "X#X", "XSX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('S'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.detector_rail, 6), "X X", "X#X", "XRX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Blocks.stone_pressure_plate);
        this.addRecipe(new ItemStack(Items.minecart, 1), "# #", "###", Character.valueOf('#'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.cauldron, 1), "# #", "# #", "###", Character.valueOf('#'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.brewing_stand, 1), " B ", "###", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('B'), Items.blaze_rod);
        this.addRecipe(new ItemStack(Blocks.lit_pumpkin, 1), "A", "B", Character.valueOf('A'), Blocks.pumpkin, Character.valueOf('B'), Blocks.torch);
        this.addRecipe(new ItemStack(Items.chest_minecart, 1), "A", "B", Character.valueOf('A'), Blocks.chest, Character.valueOf('B'), Items.minecart);
        this.addRecipe(new ItemStack(Items.furnace_minecart, 1), "A", "B", Character.valueOf('A'), Blocks.furnace, Character.valueOf('B'), Items.minecart);
        this.addRecipe(new ItemStack(Items.tnt_minecart, 1), "A", "B", Character.valueOf('A'), Blocks.tnt, Character.valueOf('B'), Items.minecart);
        this.addRecipe(new ItemStack(Items.hopper_minecart, 1), "A", "B", Character.valueOf('A'), Blocks.hopper, Character.valueOf('B'), Items.minecart);
        this.addRecipe(new ItemStack(Items.boat, 1), "# #", "###", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Items.bucket, 1), "# #", " # ", Character.valueOf('#'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.flower_pot, 1), "# #", " # ", Character.valueOf('#'), Items.brick);
        this.addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1), new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1));
        this.addRecipe(new ItemStack(Items.bread, 1), "###", Character.valueOf('#'), Items.wheat);
        this.addRecipe(new ItemStack(Blocks.oak_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 0));
        this.addRecipe(new ItemStack(Blocks.birch_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 2));
        this.addRecipe(new ItemStack(Blocks.spruce_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 1));
        this.addRecipe(new ItemStack(Blocks.jungle_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 3));
        this.addRecipe(new ItemStack(Blocks.acacia_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4));
        this.addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 5));
        this.addRecipe(new ItemStack(Items.fishing_rod, 1), "  #", " #X", "# X", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.string);
        this.addRecipe(new ItemStack(Items.carrot_on_a_stick, 1), "# ", " X", Character.valueOf('#'), Items.fishing_rod, Character.valueOf('X'), Items.carrot).func_92100_c();
        this.addRecipe(new ItemStack(Blocks.stone_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.cobblestone);
        this.addRecipe(new ItemStack(Blocks.brick_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.brick_block);
        this.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.stonebrick);
        this.addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.nether_brick);
        this.addRecipe(new ItemStack(Blocks.sandstone_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.sandstone);
        this.addRecipe(new ItemStack(Blocks.quartz_stairs, 4), "#  ", "## ", "###", Character.valueOf('#'), Blocks.quartz_block);
        this.addRecipe(new ItemStack(Items.painting, 1), "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Blocks.wool);
        this.addRecipe(new ItemStack(Items.item_frame, 1), "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.leather);
        this.addRecipe(new ItemStack(Items.golden_apple, 1, 0), "###", "#X#", "###", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.apple);
        this.addRecipe(new ItemStack(Items.golden_apple, 1, 1), "###", "#X#", "###", Character.valueOf('#'), Blocks.gold_block, Character.valueOf('X'), Items.apple);
        this.addRecipe(new ItemStack(Items.golden_carrot, 1, 0), "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.carrot);
        this.addRecipe(new ItemStack(Items.speckled_melon, 1), "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.melon);
        this.addRecipe(new ItemStack(Blocks.lever, 1), "X", "#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.stick);
        this.addRecipe(new ItemStack(Blocks.tripwire_hook, 2), "I", "S", "#", Character.valueOf('#'), Blocks.planks, Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Blocks.redstone_torch, 1), "X", "#", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.redstone);
        this.addRecipe(new ItemStack(Items.repeater, 1), "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.redstone, Character.valueOf('I'), Blocks.stone);
        this.addRecipe(new ItemStack(Items.comparator, 1), " # ", "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.quartz, Character.valueOf('I'), Blocks.stone);
        this.addRecipe(new ItemStack(Items.clock, 1), " # ", "#X#", " # ", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.redstone);
        this.addRecipe(new ItemStack(Items.compass, 1), " # ", "#X#", " # ", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.redstone);
        this.addRecipe(new ItemStack(Items.map, 1), "###", "#X#", "###", Character.valueOf('#'), Items.paper, Character.valueOf('X'), Items.compass);
        this.addRecipe(new ItemStack(Blocks.stone_button, 1), "#", Character.valueOf('#'), Blocks.stone);
        this.addRecipe(new ItemStack(Blocks.wooden_button, 1), "#", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), "##", Character.valueOf('#'), Blocks.stone);
        this.addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), "##", Character.valueOf('#'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), "##", Character.valueOf('#'), Items.iron_ingot);
        this.addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), "##", Character.valueOf('#'), Items.gold_ingot);
        this.addRecipe(new ItemStack(Blocks.dispenser, 1), "###", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.bow, Character.valueOf('R'), Items.redstone);
        this.addRecipe(new ItemStack(Blocks.dropper, 1), "###", "# #", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('R'), Items.redstone);
        this.addRecipe(new ItemStack(Blocks.piston, 1), "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('T'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.sticky_piston, 1), "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), Blocks.piston);
        this.addRecipe(new ItemStack(Items.bed, 1), "###", "XXX", Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks);
        this.addRecipe(new ItemStack(Blocks.enchanting_table, 1), " B ", "D#D", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('B'), Items.book, Character.valueOf('D'), Items.diamond);
        this.addRecipe(new ItemStack(Blocks.anvil, 1), "III", " i ", "iii", Character.valueOf('I'), Blocks.iron_block, Character.valueOf('i'), Items.iron_ingot);
        this.addShapelessRecipe(new ItemStack(Items.ender_eye, 1), Items.ender_pearl, Items.blaze_powder);
        this.addShapelessRecipe(new ItemStack(Items.fire_charge, 3), Items.gunpowder, Items.blaze_powder, Items.coal);
        this.addShapelessRecipe(new ItemStack(Items.fire_charge, 3), Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1));
        this.addRecipe(new ItemStack(Blocks.daylight_detector), "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.glass, Character.valueOf('Q'), Items.quartz, Character.valueOf('W'), Blocks.wooden_slab);
        this.addRecipe(new ItemStack(Blocks.hopper), "I I", "ICI", " I ", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest);
        Collections.sort(this.recipes, new Comparator(){
            private static final String __OBFID = "CL_00000091";

            public int compare(IRecipe par1IRecipe, IRecipe par2IRecipe) {
                return par1IRecipe instanceof ShapelessRecipes && par2IRecipe instanceof ShapedRecipes ? 1 : (par2IRecipe instanceof ShapelessRecipes && par1IRecipe instanceof ShapedRecipes ? -1 : (par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize() ? -1 : (par2IRecipe.getRecipeSize() > par1IRecipe.getRecipeSize() ? 1 : 0)));
            }

            public int compare(Object par1Obj, Object par2Obj) {
                return this.compare((IRecipe)par1Obj, (IRecipe)par2Obj);
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    /* varargs */ ShapedRecipes addRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
        block9 : {
            var3 = "";
            var4 = 0;
            var5 = 0;
            var6 = 0;
            if (!(par2ArrayOfObj[var4] instanceof String[])) ** GOTO lbl20
            var7 = (String[])par2ArrayOfObj[var4++];
            var8 = 0;
            while (var8 < var7.length) {
                var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = String.valueOf(var3) + var9;
                ++var8;
            }
            break block9;
lbl-1000: // 1 sources:
            {
                var11 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var11.length();
                var3 = String.valueOf(var3) + var11;
lbl20: // 2 sources:
                ** while (par2ArrayOfObj[var4] instanceof String)
            }
        }
        var12 = new HashMap<Character, ItemStack>();
        while (var4 < par2ArrayOfObj.length) {
            var13 = (Character)par2ArrayOfObj[var4];
            var14 = null;
            if (par2ArrayOfObj[var4 + 1] instanceof Item) {
                var14 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            } else if (par2ArrayOfObj[var4 + 1] instanceof Block) {
                var14 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, 32767);
            } else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack) {
                var14 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }
            var12.put(var13, var14);
            var4 += 2;
        }
        var15 = new ItemStack[var5 * var6];
        var16 = 0;
        while (var16 < var5 * var6) {
            var10 = var3.charAt(var16);
            var15[var16] = var12.containsKey(Character.valueOf(var10)) != false ? ((ItemStack)var12.get(Character.valueOf(var10))).copy() : null;
            ++var16;
        }
        var17 = new ShapedRecipes(var5, var6, var15, par1ItemStack);
        this.recipes.add(var17);
        return var17;
    }

    /* varargs */ void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
        ArrayList<ItemStack> var3 = new ArrayList<ItemStack>();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;
        int var6 = 0;
        while (var6 < var5) {
            Object var7 = var4[var6];
            if (var7 instanceof ItemStack) {
                var3.add(((ItemStack)var7).copy());
            } else if (var7 instanceof Item) {
                var3.add(new ItemStack((Item)var7));
            } else {
                if (!(var7 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }
                var3.add(new ItemStack((Block)var7));
            }
            ++var6;
        }
        this.recipes.add(new ShapelessRecipes(par1ItemStack, var3));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World) {
        int var3 = 0;
        ItemStack var4 = null;
        ItemStack var5 = null;
        int var6 = 0;
        while (var6 < par1InventoryCrafting.getSizeInventory()) {
            ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);
            if (var7 != null) {
                if (var3 == 0) {
                    var4 = var7;
                }
                if (var3 == 1) {
                    var5 = var7;
                }
                ++var3;
            }
            ++var6;
        }
        if (var3 == 2 && var4.getItem() == var5.getItem() && var4.stackSize == 1 && var5.stackSize == 1 && var4.getItem().isDamageable()) {
            Item var11 = var4.getItem();
            int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
            int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
            int var10 = var11.getMaxDamage() - var9;
            if (var10 < 0) {
                var10 = 0;
            }
            return new ItemStack(var4.getItem(), 1, var10);
        }
        var6 = 0;
        while (var6 < this.recipes.size()) {
            IRecipe var12 = (IRecipe)this.recipes.get(var6);
            if (var12.matches(par1InventoryCrafting, par2World)) {
                return var12.getCraftingResult(par1InventoryCrafting);
            }
            ++var6;
        }
        return null;
    }

    public List getRecipeList() {
        return this.recipes;
    }

}

