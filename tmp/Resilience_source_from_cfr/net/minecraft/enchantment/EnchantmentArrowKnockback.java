/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentArrowKnockback
extends Enchantment {
    private static final String __OBFID = "CL_00000101";

    public EnchantmentArrowKnockback(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowKnockback");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 12 + (par1 - 1) * 20;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}

