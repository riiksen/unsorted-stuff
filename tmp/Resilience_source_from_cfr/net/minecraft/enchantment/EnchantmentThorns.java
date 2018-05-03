/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentThorns
extends Enchantment {
    private static final String __OBFID = "CL_00000122";

    public EnchantmentThorns(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.armor_torso);
        this.setName("thorns");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 10 + 20 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() instanceof ItemArmor ? true : super.canApply(par1ItemStack);
    }

    @Override
    public void func_151367_b(EntityLivingBase p_151367_1_, Entity p_151367_2_, int p_151367_3_) {
        Random var4 = p_151367_1_.getRNG();
        ItemStack var5 = EnchantmentHelper.func_92099_a(Enchantment.thorns, p_151367_1_);
        if (EnchantmentThorns.func_92094_a(p_151367_3_, var4)) {
            p_151367_2_.attackEntityFrom(DamageSource.causeThornsDamage(p_151367_1_), EnchantmentThorns.func_92095_b(p_151367_3_, var4));
            p_151367_2_.playSound("damage.thorns", 0.5f, 1.0f);
            if (var5 != null) {
                var5.damageItem(3, p_151367_1_);
            }
        } else if (var5 != null) {
            var5.damageItem(1, p_151367_1_);
        }
    }

    public static boolean func_92094_a(int par0, Random par1Random) {
        return par0 <= 0 ? false : par1Random.nextFloat() < 0.15f * (float)par0;
    }

    public static int func_92095_b(int par0, Random par1Random) {
        return par0 > 10 ? par0 - 10 : 1 + par1Random.nextInt(4);
    }
}

