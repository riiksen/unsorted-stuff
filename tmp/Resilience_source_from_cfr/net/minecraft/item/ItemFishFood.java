/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFishFood
extends ItemFood {
    private final boolean field_150907_b;
    private static final String __OBFID = "CL_00000032";

    public ItemFishFood(boolean p_i45338_1_) {
        super(0, 0.0f, false);
        this.field_150907_b = p_i45338_1_;
    }

    @Override
    public int func_150905_g(ItemStack p_150905_1_) {
        FishType var2 = FishType.func_150978_a(p_150905_1_);
        return this.field_150907_b && var2.func_150973_i() ? var2.func_150970_e() : var2.func_150975_c();
    }

    @Override
    public float func_150906_h(ItemStack p_150906_1_) {
        FishType var2 = FishType.func_150978_a(p_150906_1_);
        return this.field_150907_b && var2.func_150973_i() ? var2.func_150977_f() : var2.func_150967_d();
    }

    @Override
    public String getPotionEffect(ItemStack p_150896_1_) {
        return FishType.func_150978_a(p_150896_1_) == FishType.PUFFERFISH ? PotionHelper.field_151423_m : null;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        FishType[] var2 = FishType.values();
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            FishType var5 = var2[var4];
            var5.func_150968_a(par1IconRegister);
            ++var4;
        }
    }

    @Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        FishType var4 = FishType.func_150978_a(par1ItemStack);
        if (var4 == FishType.PUFFERFISH) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
        }
        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        FishType var2 = FishType.func_150974_a(par1);
        return this.field_150907_b && var2.func_150973_i() ? var2.func_150979_h() : var2.func_150971_g();
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        FishType[] var4 = FishType.values();
        int var5 = var4.length;
        int var6 = 0;
        while (var6 < var5) {
            FishType var7 = var4[var6];
            if (!this.field_150907_b || var7.func_150973_i()) {
                p_150895_3_.add(new ItemStack(this, 1, var7.func_150976_a()));
            }
            ++var6;
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        FishType var2 = FishType.func_150978_a(par1ItemStack);
        return String.valueOf(this.getUnlocalizedName()) + "." + var2.func_150972_b() + "." + (this.field_150907_b && var2.func_150973_i() ? "cooked" : "raw");
    }

    public static enum FishType {
        COD("COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f),
        SALMON("SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f),
        CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1f),
        PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
        
        private static final Map field_150983_e;
        private final int field_150980_f;
        private final String field_150981_g;
        private IIcon field_150993_h;
        private IIcon field_150994_i;
        private final int field_150991_j;
        private final float field_150992_k;
        private final int field_150989_l;
        private final float field_150990_m;
        private boolean field_150987_n = false;
        private static final FishType[] $VALUES;
        private static final String __OBFID = "CL_00000033";

        static {
            field_150983_e = Maps.newHashMap();
            $VALUES = new FishType[]{COD, SALMON, CLOWNFISH, PUFFERFISH};
            FishType[] var0 = FishType.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                FishType var3 = var0[var2];
                field_150983_e.put(var3.func_150976_a(), var3);
                ++var2;
            }
        }

        private FishType(String p_i45336_1_, int p_i45336_2_, String p_i45336_3_, int p_i45336_4_, int p_i45336_5_, String p_i45336_6_, int p_i45336_7_, float p_i45336_8_, int n2, float f) {
            this.field_150980_f = p_i45336_3_;
            this.field_150981_g = (String)p_i45336_4_;
            this.field_150991_j = p_i45336_5_;
            this.field_150992_k = p_i45336_6_;
            this.field_150989_l = p_i45336_7_;
            this.field_150990_m = p_i45336_8_;
            this.field_150987_n = true;
        }

        private FishType(String p_i45337_1_, int p_i45337_2_, String p_i45337_3_, int p_i45337_4_, int p_i45337_5_, String p_i45337_6_, int n2, float f) {
            this.field_150980_f = p_i45337_3_;
            this.field_150981_g = (String)p_i45337_4_;
            this.field_150991_j = p_i45337_5_;
            this.field_150992_k = p_i45337_6_;
            this.field_150989_l = 0;
            this.field_150990_m = 0.0f;
            this.field_150987_n = false;
        }

        public int func_150976_a() {
            return this.field_150980_f;
        }

        public String func_150972_b() {
            return this.field_150981_g;
        }

        public int func_150975_c() {
            return this.field_150991_j;
        }

        public float func_150967_d() {
            return this.field_150992_k;
        }

        public int func_150970_e() {
            return this.field_150989_l;
        }

        public float func_150977_f() {
            return this.field_150990_m;
        }

        public void func_150968_a(IIconRegister p_150968_1_) {
            this.field_150993_h = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_raw");
            if (this.field_150987_n) {
                this.field_150994_i = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_cooked");
            }
        }

        public IIcon func_150971_g() {
            return this.field_150993_h;
        }

        public IIcon func_150979_h() {
            return this.field_150994_i;
        }

        public boolean func_150973_i() {
            return this.field_150987_n;
        }

        public static FishType func_150974_a(int p_150974_0_) {
            FishType var1 = (FishType)((Object)field_150983_e.get(p_150974_0_));
            return var1 == null ? COD : var1;
        }

        public static FishType func_150978_a(ItemStack p_150978_0_) {
            return p_150978_0_.getItem() instanceof ItemFishFood ? FishType.func_150974_a(p_150978_0_.getItemDamage()) : COD;
        }
    }

}

