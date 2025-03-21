/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSkull
extends Item {
    private static final String[] skullTypes = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};
    public static final String[] field_94587_a = new String[]{"skeleton", "wither", "zombie", "steve", "creeper"};
    private IIcon[] field_94586_c;
    private static final String __OBFID = "CL_00000067";

    public ItemSkull() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        TileEntity var12;
        if (par7 == 0) {
            return false;
        }
        if (!par3World.getBlock(par4, par5, par6).getMaterial().isSolid()) {
            return false;
        }
        if (par7 == 1) {
            ++par5;
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
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (!Blocks.skull.canPlaceBlockAt(par3World, par4, par5, par6)) {
            return false;
        }
        par3World.setBlock(par4, par5, par6, Blocks.skull, par7, 2);
        int var11 = 0;
        if (par7 == 1) {
            var11 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 16.0f / 360.0f) + 0.5) & 15;
        }
        if ((var12 = par3World.getTileEntity(par4, par5, par6)) != null && var12 instanceof TileEntitySkull) {
            String var13 = "";
            if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().func_150297_b("SkullOwner", 8)) {
                var13 = par1ItemStack.getTagCompound().getString("SkullOwner");
            }
            ((TileEntitySkull)var12).func_145905_a(par1ItemStack.getItemDamage(), var13);
            ((TileEntitySkull)var12).func_145903_a(var11);
            ((BlockSkull)Blocks.skull).func_149965_a(par3World, par4, par5, par6, (TileEntitySkull)var12);
        }
        --par1ItemStack.stackSize;
        return true;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        int var4 = 0;
        while (var4 < skullTypes.length) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        if (par1 < 0 || par1 >= skullTypes.length) {
            par1 = 0;
        }
        return this.field_94586_c[par1];
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int var2 = par1ItemStack.getItemDamage();
        if (var2 < 0 || var2 >= skullTypes.length) {
            var2 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + skullTypes[var2];
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return par1ItemStack.getItemDamage() == 3 && par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().func_150297_b("SkullOwner", 8) ? StatCollector.translateToLocalFormatted("item.skull.player.name", par1ItemStack.getTagCompound().getString("SkullOwner")) : super.getItemStackDisplayName(par1ItemStack);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.field_94586_c = new IIcon[field_94587_a.length];
        int var2 = 0;
        while (var2 < field_94587_a.length) {
            this.field_94586_c[var2] = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + field_94587_a[var2]);
            ++var2;
        }
    }
}

