/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockLog;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemDye
extends Item {
    public static final String[] field_150923_a = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final String[] field_150921_b = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
    public static final int[] field_150922_c = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
    private IIcon[] field_150920_d;
    private static final String __OBFID = "CL_00000022";

    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        int var2 = MathHelper.clamp_int(par1, 0, 15);
        return this.field_150920_d[var2];
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return String.valueOf(super.getUnlocalizedName()) + "." + field_150923_a[var2];
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (par1ItemStack.getItemDamage() == 15) {
            if (ItemDye.func_150919_a(par1ItemStack, par3World, par4, par5, par6)) {
                if (!par3World.isClient) {
                    par3World.playAuxSFX(2005, par4, par5, par6, 0);
                }
                return true;
            }
        } else if (par1ItemStack.getItemDamage() == 3) {
            Block var11 = par3World.getBlock(par4, par5, par6);
            int var12 = par3World.getBlockMetadata(par4, par5, par6);
            if (var11 == Blocks.log && BlockLog.func_150165_c(var12) == 3) {
                if (par7 == 0) {
                    return false;
                }
                if (par7 == 1) {
                    return false;
                }
                if (par7 == 2) {
                    --par6;
                }
                if (par7 == 3) {
                    ++par6;
                }
                if (par7 == 4) {
                    --par4;
                }
                if (par7 == 5) {
                    ++par4;
                }
                if (par3World.isAirBlock(par4, par5, par6)) {
                    int var13 = Blocks.cocoa.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
                    par3World.setBlock(par4, par5, par6, Blocks.cocoa, var13, 2);
                    if (!par2EntityPlayer.capabilities.isCreativeMode) {
                        --par1ItemStack.stackSize;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean func_150919_a(ItemStack p_150919_0_, World p_150919_1_, int p_150919_2_, int p_150919_3_, int p_150919_4_) {
        IGrowable var6;
        Block var5 = p_150919_1_.getBlock(p_150919_2_, p_150919_3_, p_150919_4_);
        if (var5 instanceof IGrowable && (var6 = (IGrowable)((Object)var5)).func_149851_a(p_150919_1_, p_150919_2_, p_150919_3_, p_150919_4_, p_150919_1_.isClient)) {
            if (!p_150919_1_.isClient) {
                if (var6.func_149852_a(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_)) {
                    var6.func_149853_b(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_);
                }
                --p_150919_0_.stackSize;
            }
            return true;
        }
        return false;
    }

    public static void func_150918_a(World p_150918_0_, int p_150918_1_, int p_150918_2_, int p_150918_3_, int p_150918_4_) {
        Block var5;
        if (p_150918_4_ == 0) {
            p_150918_4_ = 15;
        }
        if ((var5 = p_150918_0_.getBlock(p_150918_1_, p_150918_2_, p_150918_3_)).getMaterial() != Material.air) {
            var5.setBlockBoundsBasedOnState(p_150918_0_, p_150918_1_, p_150918_2_, p_150918_3_);
            int var6 = 0;
            while (var6 < p_150918_4_) {
                double var7 = itemRand.nextGaussian() * 0.02;
                double var9 = itemRand.nextGaussian() * 0.02;
                double var11 = itemRand.nextGaussian() * 0.02;
                p_150918_0_.spawnParticle("happyVillager", (float)p_150918_1_ + itemRand.nextFloat(), (double)p_150918_2_ + (double)itemRand.nextFloat() * var5.getBlockBoundsMaxY(), (float)p_150918_3_ + itemRand.nextFloat(), var7, var9, var11);
                ++var6;
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
        if (par3EntityLivingBase instanceof EntitySheep) {
            EntitySheep var4 = (EntitySheep)par3EntityLivingBase;
            int var5 = BlockColored.func_150032_b(par1ItemStack.getItemDamage());
            if (!var4.getSheared() && var4.getFleeceColor() != var5) {
                var4.setFleeceColor(var5);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        int var4 = 0;
        while (var4 < 16) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.field_150920_d = new IIcon[field_150921_b.length];
        int var2 = 0;
        while (var2 < field_150921_b.length) {
            this.field_150920_d[var2] = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + field_150921_b[var2]);
            ++var2;
        }
    }
}

