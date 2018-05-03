/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import java.util.ArrayList;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesArmorDyes
implements IRecipe {
    private static final String __OBFID = "CL_00000079";

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ItemStack var3 = null;
        ArrayList<ItemStack> var4 = new ArrayList<ItemStack>();
        int var5 = 0;
        while (var5 < par1InventoryCrafting.getSizeInventory()) {
            ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
            if (var6 != null) {
                if (var6.getItem() instanceof ItemArmor) {
                    ItemArmor var7 = (ItemArmor)var6.getItem();
                    if (var7.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH || var3 != null) {
                        return false;
                    }
                    var3 = var6;
                } else {
                    if (var6.getItem() != Items.dye) {
                        return false;
                    }
                    var4.add(var6);
                }
            }
            ++var5;
        }
        if (var3 != null && !var4.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        float var10;
        float var11;
        int var9;
        int var17;
        ItemStack var2 = null;
        int[] var3 = new int[3];
        int var4 = 0;
        int var5 = 0;
        ItemArmor var6 = null;
        int var7 = 0;
        while (var7 < par1InventoryCrafting.getSizeInventory()) {
            ItemStack var8 = par1InventoryCrafting.getStackInSlot(var7);
            if (var8 != null) {
                if (var8.getItem() instanceof ItemArmor) {
                    var6 = (ItemArmor)var8.getItem();
                    if (var6.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH || var2 != null) {
                        return null;
                    }
                    var2 = var8.copy();
                    var2.stackSize = 1;
                    if (var6.hasColor(var8)) {
                        var9 = var6.getColor(var2);
                        var10 = (float)(var9 >> 16 & 255) / 255.0f;
                        var11 = (float)(var9 >> 8 & 255) / 255.0f;
                        float var12 = (float)(var9 & 255) / 255.0f;
                        var4 = (int)((float)var4 + Math.max(var10, Math.max(var11, var12)) * 255.0f);
                        var3[0] = (int)((float)var3[0] + var10 * 255.0f);
                        var3[1] = (int)((float)var3[1] + var11 * 255.0f);
                        var3[2] = (int)((float)var3[2] + var12 * 255.0f);
                        ++var5;
                    }
                } else {
                    if (var8.getItem() != Items.dye) {
                        return null;
                    }
                    float[] var14 = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(var8.getItemDamage())];
                    int var16 = (int)(var14[0] * 255.0f);
                    int var15 = (int)(var14[1] * 255.0f);
                    var17 = (int)(var14[2] * 255.0f);
                    var4 += Math.max(var16, Math.max(var15, var17));
                    int[] arrn = var3;
                    arrn[0] = arrn[0] + var16;
                    int[] arrn2 = var3;
                    arrn2[1] = arrn2[1] + var15;
                    int[] arrn3 = var3;
                    arrn3[2] = arrn3[2] + var17;
                    ++var5;
                }
            }
            ++var7;
        }
        if (var6 == null) {
            return null;
        }
        var7 = var3[0] / var5;
        int var13 = var3[1] / var5;
        var9 = var3[2] / var5;
        var10 = (float)var4 / (float)var5;
        var11 = Math.max(var7, Math.max(var13, var9));
        var7 = (int)((float)var7 * var10 / var11);
        var13 = (int)((float)var13 * var10 / var11);
        var9 = (int)((float)var9 * var10 / var11);
        var17 = (var7 << 8) + var13;
        var17 = (var17 << 8) + var9;
        var6.func_82813_b(var2, var17);
        return var2;
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}

