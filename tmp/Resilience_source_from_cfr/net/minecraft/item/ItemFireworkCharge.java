/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge
extends Item {
    private IIcon field_150904_a;
    private static final String __OBFID = "CL_00000030";

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return par2 > 0 ? this.field_150904_a : super.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        if (par2 != 1) {
            return super.getColorFromItemStack(par1ItemStack, par2);
        }
        NBTBase var3 = ItemFireworkCharge.func_150903_a(par1ItemStack, "Colors");
        if (var3 != null && var3 instanceof NBTTagIntArray) {
            NBTTagIntArray var4 = (NBTTagIntArray)var3;
            int[] var5 = var4.func_150302_c();
            if (var5.length == 1) {
                return var5[0];
            }
            int var6 = 0;
            int var7 = 0;
            int var8 = 0;
            int[] var9 = var5;
            int var10 = var5.length;
            int var11 = 0;
            while (var11 < var10) {
                int var12 = var9[var11];
                var6 += (var12 & 16711680) >> 16;
                var7 += (var12 & 65280) >> 8;
                var8 += (var12 & 255) >> 0;
                ++var11;
            }
            return (var6 /= var5.length) << 16 | (var7 /= var5.length) << 8 | (var8 /= var5.length);
        }
        return 9079434;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public static NBTBase func_150903_a(ItemStack p_150903_0_, String p_150903_1_) {
        NBTTagCompound var2;
        if (p_150903_0_.hasTagCompound() && (var2 = p_150903_0_.getTagCompound().getCompoundTag("Explosion")) != null) {
            return var2.getTag(p_150903_1_);
        }
        return null;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        NBTTagCompound var5;
        if (par1ItemStack.hasTagCompound() && (var5 = par1ItemStack.getTagCompound().getCompoundTag("Explosion")) != null) {
            ItemFireworkCharge.func_150902_a(var5, par3List);
        }
    }

    public static void func_150902_a(NBTTagCompound p_150902_0_, List p_150902_1_) {
        int[] var13;
        int var9;
        boolean var16;
        int var8;
        boolean var17;
        byte var2 = p_150902_0_.getByte("Type");
        if (var2 >= 0 && var2 <= 4) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
        } else {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        int[] var3 = p_150902_0_.getIntArray("Colors");
        if (var3.length > 0) {
            boolean var4 = true;
            String var5 = "";
            int[] var6 = var3;
            int var7 = var3.length;
            var8 = 0;
            while (var8 < var7) {
                var9 = var6[var8];
                if (!var4) {
                    var5 = String.valueOf(var5) + ", ";
                }
                var4 = false;
                boolean var10 = false;
                int var11 = 0;
                while (var11 < 16) {
                    if (var9 == ItemDye.field_150922_c[var11]) {
                        var10 = true;
                        var5 = String.valueOf(var5) + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(ItemDye.field_150923_a[var11]).toString());
                        break;
                    }
                    ++var11;
                }
                if (!var10) {
                    var5 = String.valueOf(var5) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                ++var8;
            }
            p_150902_1_.add(var5);
        }
        if ((var13 = p_150902_0_.getIntArray("FadeColors")).length > 0) {
            boolean var162 = true;
            String var14 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " ";
            int[] var15 = var13;
            var8 = var13.length;
            var9 = 0;
            while (var9 < var8) {
                int var18 = var15[var9];
                if (!var162) {
                    var14 = String.valueOf(var14) + ", ";
                }
                var162 = false;
                boolean var19 = false;
                int var12 = 0;
                while (var12 < 16) {
                    if (var18 == ItemDye.field_150922_c[var12]) {
                        var19 = true;
                        var14 = String.valueOf(var14) + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(ItemDye.field_150923_a[var12]).toString());
                        break;
                    }
                    ++var12;
                }
                if (!var19) {
                    var14 = String.valueOf(var14) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                ++var9;
            }
            p_150902_1_.add(var14);
        }
        if (var16 = p_150902_0_.getBoolean("Trail")) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        if (var17 = p_150902_0_.getBoolean("Flicker")) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.field_150904_a = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_overlay");
    }
}

