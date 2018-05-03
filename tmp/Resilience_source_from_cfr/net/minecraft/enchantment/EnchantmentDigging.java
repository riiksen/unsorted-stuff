/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class EnchantmentDigging
extends Enchantment {
    private static final String __OBFID = "CL_00000104";

    protected EnchantmentDigging(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("digging");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 1 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == Items.shears ? true : super.canApply(par1ItemStack);
    }
}

