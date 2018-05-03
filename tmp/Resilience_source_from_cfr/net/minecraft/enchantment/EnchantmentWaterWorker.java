/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentWaterWorker
extends Enchantment {
    private static final String __OBFID = "CL_00000124";

    public EnchantmentWaterWorker(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.armor_head);
        this.setName("waterWorker");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 1;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 40;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}

