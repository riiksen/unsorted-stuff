/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.enchantment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;

public class EnchantmentHelper {
    private static final Random enchantmentRand = new Random();
    private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage(null);
    private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving(null);
    private static final HurtIterator field_151388_d = new HurtIterator(null);
    private static final DamageIterator field_151389_e = new DamageIterator(null);
    private static final String __OBFID = "CL_00000107";

    public static int getEnchantmentLevel(int par0, ItemStack par1ItemStack) {
        if (par1ItemStack == null) {
            return 0;
        }
        NBTTagList var2 = par1ItemStack.getEnchantmentTagList();
        if (var2 == null) {
            return 0;
        }
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            short var4 = var2.getCompoundTagAt(var3).getShort("id");
            short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
            if (var4 == par0) {
                return var5;
            }
            ++var3;
        }
        return 0;
    }

    public static Map getEnchantments(ItemStack par0ItemStack) {
        NBTTagList var2;
        LinkedHashMap<Integer, Integer> var1 = new LinkedHashMap<Integer, Integer>();
        NBTTagList nBTTagList = var2 = par0ItemStack.getItem() == Items.enchanted_book ? Items.enchanted_book.func_92110_g(par0ItemStack) : par0ItemStack.getEnchantmentTagList();
        if (var2 != null) {
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                short var4 = var2.getCompoundTagAt(var3).getShort("id");
                short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
                var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
                ++var3;
            }
        }
        return var1;
    }

    public static void setEnchantments(Map par0Map, ItemStack par1ItemStack) {
        NBTTagList var2 = new NBTTagList();
        Iterator var3 = par0Map.keySet().iterator();
        while (var3.hasNext()) {
            int var4 = (Integer)var3.next();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setShort("id", (short)var4);
            var5.setShort("lvl", (short)((Integer)par0Map.get(var4)).intValue());
            var2.appendTag(var5);
            if (par1ItemStack.getItem() != Items.enchanted_book) continue;
            Items.enchanted_book.addEnchantment(par1ItemStack, new EnchantmentData(var4, (int)((Integer)par0Map.get(var4))));
        }
        if (var2.tagCount() > 0) {
            if (par1ItemStack.getItem() != Items.enchanted_book) {
                par1ItemStack.setTagInfo("ench", var2);
            }
        } else if (par1ItemStack.hasTagCompound()) {
            par1ItemStack.getTagCompound().removeTag("ench");
        }
    }

    public static int getMaxEnchantmentLevel(int par0, ItemStack[] par1ArrayOfItemStack) {
        if (par1ArrayOfItemStack == null) {
            return 0;
        }
        int var2 = 0;
        ItemStack[] var3 = par1ArrayOfItemStack;
        int var4 = par1ArrayOfItemStack.length;
        int var5 = 0;
        while (var5 < var4) {
            ItemStack var6 = var3[var5];
            int var7 = EnchantmentHelper.getEnchantmentLevel(par0, var6);
            if (var7 > var2) {
                var2 = var7;
            }
            ++var5;
        }
        return var2;
    }

    private static void applyEnchantmentModifier(IModifier par0IEnchantmentModifier, ItemStack par1ItemStack) {
        NBTTagList var2;
        if (par1ItemStack != null && (var2 = par1ItemStack.getEnchantmentTagList()) != null) {
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                short var4 = var2.getCompoundTagAt(var3).getShort("id");
                short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
                if (Enchantment.enchantmentsList[var4] != null) {
                    par0IEnchantmentModifier.calculateModifier(Enchantment.enchantmentsList[var4], var5);
                }
                ++var3;
            }
        }
    }

    private static void applyEnchantmentModifierArray(IModifier par0IEnchantmentModifier, ItemStack[] par1ArrayOfItemStack) {
        ItemStack[] var2 = par1ArrayOfItemStack;
        int var3 = par1ArrayOfItemStack.length;
        int var4 = 0;
        while (var4 < var3) {
            ItemStack var5 = var2[var4];
            EnchantmentHelper.applyEnchantmentModifier(par0IEnchantmentModifier, var5);
            ++var4;
        }
    }

    public static int getEnchantmentModifierDamage(ItemStack[] par0ArrayOfItemStack, DamageSource par1DamageSource) {
        EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        EnchantmentHelper.enchantmentModifierDamage.source = par1DamageSource;
        EnchantmentHelper.applyEnchantmentModifierArray(enchantmentModifierDamage, par0ArrayOfItemStack);
        if (EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;
        }
        return (EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1) + 1);
    }

    public static float getEnchantmentModifierLiving(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase) {
        EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0.0f;
        EnchantmentHelper.enchantmentModifierLiving.entityLiving = par1EntityLivingBase;
        EnchantmentHelper.applyEnchantmentModifier(enchantmentModifierLiving, par0EntityLivingBase.getHeldItem());
        return EnchantmentHelper.enchantmentModifierLiving.livingModifier;
    }

    public static void func_151384_a(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
        EnchantmentHelper.field_151388_d.field_151363_b = p_151384_1_;
        EnchantmentHelper.field_151388_d.field_151364_a = p_151384_0_;
        EnchantmentHelper.applyEnchantmentModifierArray(field_151388_d, p_151384_0_.getLastActiveItems());
        if (p_151384_1_ instanceof EntityPlayer) {
            EnchantmentHelper.applyEnchantmentModifier(field_151388_d, p_151384_0_.getHeldItem());
        }
    }

    public static void func_151385_b(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
        EnchantmentHelper.field_151389_e.field_151366_a = p_151385_0_;
        EnchantmentHelper.field_151389_e.field_151365_b = p_151385_1_;
        EnchantmentHelper.applyEnchantmentModifierArray(field_151389_e, p_151385_0_.getLastActiveItems());
        if (p_151385_0_ instanceof EntityPlayer) {
            EnchantmentHelper.applyEnchantmentModifier(field_151389_e, p_151385_0_.getHeldItem());
        }
    }

    public static int getKnockbackModifier(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int getFireAspectModifier(EntityLivingBase par0EntityLivingBase) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int getRespiration(EntityLivingBase par0EntityLivingBase) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.respiration.effectId, par0EntityLivingBase.getLastActiveItems());
    }

    public static int getEfficiencyModifier(EntityLivingBase par0EntityLivingBase) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static boolean getSilkTouchModifier(EntityLivingBase par0EntityLivingBase) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, par0EntityLivingBase.getHeldItem()) > 0) {
            return true;
        }
        return false;
    }

    public static int getFortuneModifier(EntityLivingBase par0EntityLivingBase) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int func_151386_g(EntityLivingBase p_151386_0_) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, p_151386_0_.getHeldItem());
    }

    public static int func_151387_h(EntityLivingBase p_151387_0_) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151369_A.effectId, p_151387_0_.getHeldItem());
    }

    public static int getLootingModifier(EntityLivingBase par0EntityLivingBase) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static boolean getAquaAffinityModifier(EntityLivingBase par0EntityLivingBase) {
        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, par0EntityLivingBase.getLastActiveItems()) > 0) {
            return true;
        }
        return false;
    }

    public static ItemStack func_92099_a(Enchantment par0Enchantment, EntityLivingBase par1EntityLivingBase) {
        ItemStack[] var2 = par1EntityLivingBase.getLastActiveItems();
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            ItemStack var5 = var2[var4];
            if (var5 != null && EnchantmentHelper.getEnchantmentLevel(par0Enchantment.effectId, var5) > 0) {
                return var5;
            }
            ++var4;
        }
        return null;
    }

    public static int calcItemStackEnchantability(Random par0Random, int par1, int par2, ItemStack par3ItemStack) {
        Item var4 = par3ItemStack.getItem();
        int var5 = var4.getItemEnchantability();
        if (var5 <= 0) {
            return 0;
        }
        if (par2 > 15) {
            par2 = 15;
        }
        int var6 = par0Random.nextInt(8) + 1 + (par2 >> 1) + par0Random.nextInt(par2 + 1);
        return par1 == 0 ? Math.max(var6 / 3, 1) : (par1 == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, par2 * 2));
    }

    public static ItemStack addRandomEnchantment(Random par0Random, ItemStack par1ItemStack, int par2) {
        boolean var4;
        List var3 = EnchantmentHelper.buildEnchantmentList(par0Random, par1ItemStack, par2);
        boolean bl = var4 = par1ItemStack.getItem() == Items.book;
        if (var4) {
            par1ItemStack.func_150996_a(Items.enchanted_book);
        }
        if (var3 != null) {
            for (EnchantmentData var6 : var3) {
                if (var4) {
                    Items.enchanted_book.addEnchantment(par1ItemStack, var6);
                    continue;
                }
                par1ItemStack.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
            }
        }
        return par1ItemStack;
    }

    public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2) {
        float var6;
        EnchantmentData var10;
        Item var3 = par1ItemStack.getItem();
        int var4 = var3.getItemEnchantability();
        if (var4 <= 0) {
            return null;
        }
        var4 /= 2;
        int var5 = (var4 = 1 + par0Random.nextInt((var4 >> 1) + 1) + par0Random.nextInt((var4 >> 1) + 1)) + par2;
        int var7 = (int)((float)var5 * (1.0f + (var6 = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0f) * 0.15f)) + 0.5f);
        if (var7 < 1) {
            var7 = 1;
        }
        ArrayList<EnchantmentData> var8 = null;
        Map var9 = EnchantmentHelper.mapEnchantmentData(var7, par1ItemStack);
        if (var9 != null && !var9.isEmpty() && (var10 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values())) != null) {
            var8 = new ArrayList<EnchantmentData>();
            var8.add(var10);
            int var11 = var7;
            while (par0Random.nextInt(50) <= var11) {
                Iterator var12 = var9.keySet().iterator();
                while (var12.hasNext()) {
                    Integer var13 = (Integer)var12.next();
                    boolean var14 = true;
                    for (EnchantmentData var16 : var8) {
                        if (var16.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[var13])) continue;
                        var14 = false;
                        break;
                    }
                    if (var14) continue;
                    var12.remove();
                }
                if (!var9.isEmpty()) {
                    EnchantmentData var17 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
                    var8.add(var17);
                }
                var11 >>= 1;
            }
        }
        return var8;
    }

    public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack) {
        Item var2 = par1ItemStack.getItem();
        HashMap<Integer, EnchantmentData> var3 = null;
        boolean var4 = par1ItemStack.getItem() == Items.book;
        Enchantment[] var5 = Enchantment.enchantmentsList;
        int var6 = var5.length;
        int var7 = 0;
        while (var7 < var6) {
            Enchantment var8 = var5[var7];
            if (var8 != null && (var8.type.canEnchantItem(var2) || var4)) {
                int var9 = var8.getMinLevel();
                while (var9 <= var8.getMaxLevel()) {
                    if (par0 >= var8.getMinEnchantability(var9) && par0 <= var8.getMaxEnchantability(var9)) {
                        if (var3 == null) {
                            var3 = new HashMap<Integer, EnchantmentData>();
                        }
                        var3.put(var8.effectId, new EnchantmentData(var8, var9));
                    }
                    ++var9;
                }
            }
            ++var7;
        }
        return var3;
    }

    static final class DamageIterator
    implements IModifier {
        public EntityLivingBase field_151366_a;
        public Entity field_151365_b;
        private static final String __OBFID = "CL_00000109";

        private DamageIterator() {
        }

        @Override
        public void calculateModifier(Enchantment par1Enchantment, int par2) {
            par1Enchantment.func_151368_a(this.field_151366_a, this.field_151365_b, par2);
        }

        DamageIterator(Object p_i45359_1_) {
            this();
        }
    }

    static final class HurtIterator
    implements IModifier {
        public EntityLivingBase field_151364_a;
        public Entity field_151363_b;
        private static final String __OBFID = "CL_00000110";

        private HurtIterator() {
        }

        @Override
        public void calculateModifier(Enchantment par1Enchantment, int par2) {
            par1Enchantment.func_151367_b(this.field_151364_a, this.field_151363_b, par2);
        }

        HurtIterator(Object p_i45360_1_) {
            this();
        }
    }

    static interface IModifier {
        public void calculateModifier(Enchantment var1, int var2);
    }

    static final class ModifierDamage
    implements IModifier {
        public int damageModifier;
        public DamageSource source;
        private static final String __OBFID = "CL_00000114";

        private ModifierDamage() {
        }

        @Override
        public void calculateModifier(Enchantment par1Enchantment, int par2) {
            this.damageModifier += par1Enchantment.calcModifierDamage(par2, this.source);
        }

        ModifierDamage(Object par1Empty3) {
            this();
        }
    }

    static final class ModifierLiving
    implements IModifier {
        public float livingModifier;
        public EntityLivingBase entityLiving;
        private static final String __OBFID = "CL_00000112";

        private ModifierLiving() {
        }

        @Override
        public void calculateModifier(Enchantment par1Enchantment, int par2) {
            this.livingModifier += par1Enchantment.calcModifierLiving(par2, this.entityLiving);
        }

        ModifierLiving(Object par1Empty3) {
            this();
        }
    }

}

