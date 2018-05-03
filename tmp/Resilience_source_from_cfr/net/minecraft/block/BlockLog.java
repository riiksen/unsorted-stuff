/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class BlockLog
extends BlockRotatedPillar {
    protected IIcon[] field_150167_a;
    protected IIcon[] field_150166_b;
    private static final String __OBFID = "CL_00000266";

    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(soundTypeWood);
    }

    public static int func_150165_c(int p_150165_0_) {
        return p_150165_0_ & 3;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 1;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        int var7 = 4;
        int var8 = var7 + 1;
        if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
            int var9 = - var7;
            while (var9 <= var7) {
                int var10 = - var7;
                while (var10 <= var7) {
                    int var11 = - var7;
                    while (var11 <= var7) {
                        int var12;
                        if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves && ((var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11)) & 8) == 0) {
                            p_149749_1_.setBlockMetadataWithNotify(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11, var12 | 8, 4);
                        }
                        ++var11;
                    }
                    ++var10;
                }
                ++var9;
            }
        }
    }

    @Override
    protected IIcon func_150163_b(int p_150163_1_) {
        return this.field_150167_a[p_150163_1_ % this.field_150167_a.length];
    }

    @Override
    protected IIcon func_150161_d(int p_150161_1_) {
        return this.field_150166_b[p_150161_1_ % this.field_150166_b.length];
    }
}

