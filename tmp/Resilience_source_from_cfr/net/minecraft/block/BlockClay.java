/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockClay
extends Block {
    private static final String __OBFID = "CL_00000215";

    public BlockClay() {
        super(Material.field_151571_B);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.clay_ball;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 4;
    }
}

