/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockButtonStone
extends BlockButton {
    private static final String __OBFID = "CL_00000319";

    protected BlockButtonStone() {
        super(false);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.stone.getBlockTextureFromSide(1);
    }
}

