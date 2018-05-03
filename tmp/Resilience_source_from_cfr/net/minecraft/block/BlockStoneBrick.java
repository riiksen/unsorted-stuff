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

public class BlockStoneBrick
extends Block {
    public static final String[] field_150142_a = new String[]{"default", "mossy", "cracked", "chiseled"};
    public static final String[] field_150141_b;
    private IIcon[] field_150143_M;
    private static final String __OBFID = "CL_00000318";

    static {
        String[] arrstring = new String[4];
        arrstring[1] = "mossy";
        arrstring[2] = "cracked";
        arrstring[3] = "carved";
        field_150141_b = arrstring;
    }

    public BlockStoneBrick() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ >= field_150141_b.length) {
            p_149691_2_ = 0;
        }
        return this.field_150143_M[p_149691_2_];
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 0;
        while (var4 < 4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150143_M = new IIcon[field_150141_b.length];
        int var2 = 0;
        while (var2 < this.field_150143_M.length) {
            String var3 = this.getTextureName();
            if (field_150141_b[var2] != null) {
                var3 = String.valueOf(var3) + "_" + field_150141_b[var2];
            }
            this.field_150143_M[var2] = p_149651_1_.registerIcon(var3);
            ++var2;
        }
    }
}

