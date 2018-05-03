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

public class EnchantmentUntouching
extends Enchantment {
    private static final String __OBFID = "CL_00000123";

    protected EnchantmentUntouching(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("untouching");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment) {
        if (super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != EnchantmentUntouching.fortune.effectId) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == Items.shears ? true : super.canApply(par1ItemStack);
    }
}

