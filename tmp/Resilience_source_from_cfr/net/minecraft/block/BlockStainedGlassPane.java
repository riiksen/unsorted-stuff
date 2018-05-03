/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockStainedGlassPane
extends BlockPane {
    private static final IIcon[] field_150106_a = new IIcon[16];
    private static final IIcon[] field_150105_b = new IIcon[16];
    private static final String __OBFID = "CL_00000313";

    public BlockStainedGlassPane() {
        super("glass", "glass_pane_top", Material.glass, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public IIcon func_149735_b(int p_149735_1_, int p_149735_2_) {
        return field_150106_a[p_149735_2_ % field_150106_a.length];
    }

    public IIcon func_150104_b(int p_150104_1_) {
        return field_150105_b[~ p_150104_1_ & 15];
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.func_149735_b(p_149691_1_, ~ p_149691_2_ & 15);
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    public static int func_150103_c(int p_150103_0_) {
        return p_150103_0_ & 15;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 0;
        while (var4 < field_150106_a.length) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        int var2 = 0;
        while (var2 < field_150106_a.length) {
            BlockStainedGlassPane.field_150106_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + ItemDye.field_150921_b[BlockStainedGlassPane.func_150103_c(var2)]);
            BlockStainedGlassPane.field_150105_b[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_pane_top_" + ItemDye.field_150921_b[BlockStainedGlassPane.func_150103_c(var2)]);
            ++var2;
        }
    }
}

