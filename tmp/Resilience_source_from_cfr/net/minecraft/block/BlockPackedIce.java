/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPackedIce
extends Block {
    private static final String __OBFID = "CL_00000283";

    public BlockPackedIce() {
        super(Material.field_151598_x);
        this.slipperiness = 0.98f;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }
}

