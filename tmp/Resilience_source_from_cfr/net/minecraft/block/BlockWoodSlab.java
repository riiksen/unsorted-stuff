/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockWoodSlab
extends BlockSlab {
    public static final String[] field_150005_b = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "big_oak"};
    private static final String __OBFID = "CL_00000337";

    public BlockWoodSlab(boolean p_i45437_1_) {
        super(p_i45437_1_, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.planks.getIcon(p_149691_1_, p_149691_2_ & 7);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }

    @Override
    protected ItemStack createStackedBlock(int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(Blocks.wooden_slab), 2, p_149644_1_ & 7);
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        if (p_150002_1_ < 0 || p_150002_1_ >= field_150005_b.length) {
            p_150002_1_ = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + field_150005_b[p_150002_1_];
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        if (p_149666_1_ != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            int var4 = 0;
            while (var4 < field_150005_b.length) {
                p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
                ++var4;
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }
}

