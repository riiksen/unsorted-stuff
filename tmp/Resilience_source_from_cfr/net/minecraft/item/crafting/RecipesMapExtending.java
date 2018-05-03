/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesMapExtending
extends ShapedRecipes {
    private static final String __OBFID = "CL_00000088";

    public RecipesMapExtending() {
        super(3, 3, new ItemStack[]{new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper)}, new ItemStack(Items.map, 0, 0));
    }

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        if (!super.matches(par1InventoryCrafting, par2World)) {
            return false;
        }
        ItemStack var3 = null;
        int var4 = 0;
        while (var4 < par1InventoryCrafting.getSizeInventory() && var3 == null) {
            ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
            if (var5 != null && var5.getItem() == Items.filled_map) {
                var3 = var5;
            }
            ++var4;
        }
        if (var3 == null) {
            return false;
        }
        MapData var6 = Items.filled_map.getMapData(var3, par2World);
        return var6 == null ? false : var6.scale < 4;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        ItemStack var2 = null;
        int var3 = 0;
        while (var3 < par1InventoryCrafting.getSizeInventory() && var2 == null) {
            ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
            if (var4 != null && var4.getItem() == Items.filled_map) {
                var2 = var4;
            }
            ++var3;
        }
        var2 = var2.copy();
        var2.stackSize = 1;
        if (var2.getTagCompound() == null) {
            var2.setTagCompound(new NBTTagCompound());
        }
        var2.getTagCompound().setBoolean("map_is_scaling", true);
        return var2;
    }
}

