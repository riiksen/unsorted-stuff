/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentArrowInfinite
extends Enchantment {
    private static final String __OBFID = "CL_00000100";

    public EnchantmentArrowInfinite(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowInfinite");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 20;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}

