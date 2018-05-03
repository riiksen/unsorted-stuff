/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockHay
extends BlockRotatedPillar {
    private static final String __OBFID = "CL_00000256";

    public BlockHay() {
        super(Material.grass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    protected IIcon func_150163_b(int p_150163_1_) {
        return this.blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150164_N = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_top");
        this.blockIcon = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_side");
    }
}

