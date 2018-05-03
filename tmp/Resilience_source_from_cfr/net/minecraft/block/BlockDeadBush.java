/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class BlockDeadBush
extends BlockBush {
    private static final String __OBFID = "CL_00000224";

    protected BlockDeadBush() {
        super(Material.vine);
        float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }

    @Override
    protected boolean func_149854_a(Block p_149854_1_) {
        if (p_149854_1_ != Blocks.sand && p_149854_1_ != Blocks.hardened_clay && p_149854_1_ != Blocks.stained_hardened_clay && p_149854_1_ != Blocks.dirt) {
            return false;
        }
        return true;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.deadbush, 1, p_149636_6_));
        } else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }
}

