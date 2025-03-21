/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockColored
extends Block {
    private IIcon[] field_150033_a;
    private static final String __OBFID = "CL_00000217";

    public BlockColored(Material p_i45398_1_) {
        super(p_i45398_1_);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.field_150033_a[p_149691_2_ % this.field_150033_a.length];
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    public static int func_150032_b(int p_150032_0_) {
        return BlockColored.func_150031_c(p_150032_0_);
    }

    public static int func_150031_c(int p_150031_0_) {
        return ~ p_150031_0_ & 15;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 0;
        while (var4 < 16) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150033_a = new IIcon[16];
        int var2 = 0;
        while (var2 < this.field_150033_a.length) {
            this.field_150033_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + ItemDye.field_150921_b[BlockColored.func_150031_c(var2)]);
            ++var2;
        }
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return MapColor.func_151644_a(p_149728_1_);
    }
}

