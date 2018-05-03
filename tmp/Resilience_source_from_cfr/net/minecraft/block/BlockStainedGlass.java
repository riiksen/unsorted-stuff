/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockStainedGlass
extends BlockBreakable {
    private static final IIcon[] field_149998_a = new IIcon[16];
    private static final String __OBFID = "CL_00000312";

    public BlockStainedGlass(Material p_i45427_1_) {
        super("glass", p_i45427_1_, false);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return field_149998_a[p_149691_2_ % field_149998_a.length];
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    public static int func_149997_b(int p_149997_0_) {
        return ~ p_149997_0_ & 15;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 0;
        while (var4 < field_149998_a.length) {
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
        int var2 = 0;
        while (var2 < field_149998_a.length) {
            BlockStainedGlass.field_149998_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + ItemDye.field_150921_b[BlockStainedGlass.func_149997_b(var2)]);
            ++var2;
        }
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}

