/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentArrowDamage
extends Enchantment {
    private static final String __OBFID = "CL_00000098";

    public EnchantmentArrowDamage(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowDamage");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 1 + (par1 - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}

