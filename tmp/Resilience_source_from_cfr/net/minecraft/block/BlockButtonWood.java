/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockButtonWood
extends BlockButton {
    private static final String __OBFID = "CL_00000336";

    protected BlockButtonWood() {
        super(true);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.planks.getBlockTextureFromSide(1);
    }
}

