/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentOxygen
extends Enchantment {
    private static final String __OBFID = "CL_00000120";

    public EnchantmentOxygen(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.armor_head);
        this.setName("oxygen");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 10 * par1;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}

