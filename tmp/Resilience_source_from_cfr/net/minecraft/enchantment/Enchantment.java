/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import java.util.ArrayList;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentArrowFire;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.enchantment.EnchantmentFishingSpeed;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.enchantment.EnchantmentLootBonus;
import net.minecraft.enchantment.EnchantmentOxygen;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.enchantment.EnchantmentUntouching;
import net.minecraft.enchantment.EnchantmentWaterWorker;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public abstract class Enchantment {
    public static final Enchantment[] enchantmentsList = new Enchantment[256];
    public static final Enchantment[] enchantmentsBookList;
    public static final Enchantment protection;
    public static final Enchantment fireProtection;
    public static final Enchantment featherFalling;
    public static final Enchantment blastProtection;
    public static final Enchantment projectileProtection;
    public static final Enchantment respiration;
    public static final Enchantment aquaAffinity;
    public static final Enchantment thorns;
    public static final Enchantment sharpness;
    public static final Enchantment smite;
    public static final Enchantment baneOfArthropods;
    public static final Enchantment knockback;
    public static final Enchantment fireAspect;
    public static final Enchantment looting;
    public static final Enchantment efficiency;
    public static final Enchantment silkTouch;
    public static final Enchantment unbreaking;
    public static final Enchantment fortune;
    public static final Enchantment power;
    public static final Enchantment punch;
    public static final Enchantment flame;
    public static final Enchantment infinity;
    public static final Enchantment field_151370_z;
    public static final Enchantment field_151369_A;
    public final int effectId;
    private final int weight;
    public EnumEnchantmentType type;
    protected String name;
    private static final String __OBFID = "CL_00000105";

    static {
        protection = new EnchantmentProtection(0, 10, 0);
        fireProtection = new EnchantmentProtection(1, 5, 1);
        featherFalling = new EnchantmentProtection(2, 5, 2);
        blastProtection = new EnchantmentProtection(3, 2, 3);
        projectileProtection = new EnchantmentProtection(4, 5, 4);
        respiration = new EnchantmentOxygen(5, 2);
        aquaAffinity = new EnchantmentWaterWorker(6, 2);
        thorns = new EnchantmentThorns(7, 1);
        sharpness = new EnchantmentDamage(16, 10, 0);
        smite = new EnchantmentDamage(17, 5, 1);
        baneOfArthropods = new EnchantmentDamage(18, 5, 2);
        knockback = new EnchantmentKnockback(19, 5);
        fireAspect = new EnchantmentFireAspect(20, 2);
        looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);
        efficiency = new EnchantmentDigging(32, 10);
        silkTouch = new EnchantmentUntouching(33, 1);
        unbreaking = new EnchantmentDurability(34, 5);
        fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);
        power = new EnchantmentArrowDamage(48, 10);
        punch = new EnchantmentArrowKnockback(49, 2);
        flame = new EnchantmentArrowFire(50, 2);
        infinity = new EnchantmentArrowInfinite(51, 1);
        field_151370_z = new EnchantmentLootBonus(61, 2, EnumEnchantmentType.fishing_rod);
        field_151369_A = new EnchantmentFishingSpeed(62, 2, EnumEnchantmentType.fishing_rod);
        ArrayList<Enchantment> var0 = new ArrayList<Enchantment>();
        Enchantment[] var1 = enchantmentsList;
        int var2 = var1.length;
        int var3 = 0;
        while (var3 < var2) {
            Enchantment var4 = var1[var3];
            if (var4 != null) {
                var0.add(var4);
            }
            ++var3;
        }
        enchantmentsBookList = var0.toArray(new Enchantment[0]);
    }

    protected Enchantment(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType) {
        this.effectId = par1;
        this.weight = par2;
        this.type = par3EnumEnchantmentType;
        if (enchantmentsList[par1] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.enchantmentsList[par1] = this;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getMinLevel() {
        return 1;
    }

    public int getMaxLevel() {
        return 1;
    }

    public int getMinEnchantability(int par1) {
        return 1 + par1 * 10;
    }

    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 5;
    }

    public int calcModifierDamage(int par1, DamageSource par2DamageSource) {
        return 0;
    }

    public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase) {
        return 0.0f;
    }

    public boolean canApplyTogether(Enchantment par1Enchantment) {
        if (this != par1Enchantment) {
            return true;
        }
        return false;
    }

    public Enchantment setName(String par1Str) {
        this.name = par1Str;
        return this;
    }

    public String getName() {
        return "enchantment." + this.name;
    }

    public String getTranslatedName(int par1) {
        String var2 = StatCollector.translateToLocal(this.getName());
        return String.valueOf(var2) + " " + StatCollector.translateToLocal(new StringBuilder("enchantment.level.").append(par1).toString());
    }

    public boolean canApply(ItemStack par1ItemStack) {
        return this.type.canEnchantItem(par1ItemStack.getItem());
    }

    public void func_151368_a(EntityLivingBase p_151368_1_, Entity p_151368_2_, int p_151368_3_) {
    }

    public void func_151367_b(EntityLivingBase p_151367_1_, Entity p_151367_2_, int p_151367_3_) {
    }
}

