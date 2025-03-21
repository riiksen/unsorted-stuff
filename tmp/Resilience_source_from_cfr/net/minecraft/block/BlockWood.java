/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockWood
extends Block {
    public static final String[] field_150096_a = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "big_oak"};
    private IIcon[] field_150095_b;
    private static final String __OBFID = "CL_00000335";

    public BlockWood() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ >= this.field_150095_b.length) {
            p_149691_2_ = 0;
        }
        return this.field_150095_b[p_149691_2_];
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150095_b = new IIcon[field_150096_a.length];
        int var2 = 0;
        while (var2 < this.field_150095_b.length) {
            this.field_150095_b[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + field_150096_a[var2]);
            ++var2;
        }
    }
}

