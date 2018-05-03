/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.item.ItemMultiTexture;

public class ItemAnvilBlock
extends ItemMultiTexture {
    private static final String __OBFID = "CL_00001764";

    public ItemAnvilBlock(Block par1Block) {
        super(par1Block, par1Block, BlockAnvil.field_149834_a);
    }

    @Override
    public int getMetadata(int par1) {
        return par1 << 2;
    }
}

