/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater
extends BlockRedstoneDiode {
    public static final double[] field_149973_b = new double[]{-0.0625, 0.0625, 0.1875, 0.3125};
    private static final int[] field_149974_M = new int[]{1, 2, 3, 4};
    private static final String __OBFID = "CL_00000301";

    protected BlockRedstoneRepeater(boolean p_i45424_1_) {
        super(p_i45424_1_);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        int var11 = (var10 & 12) >> 2;
        var11 = var11 + 1 << 2 & 12;
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 | var10 & 3, 3);
        return true;
    }

    @Override
    protected int func_149901_b(int p_149901_1_) {
        return field_149974_M[(p_149901_1_ & 12) >> 2] * 2;
    }

    @Override
    protected BlockRedstoneDiode func_149906_e() {
        return Blocks.powered_repeater;
    }

    @Override
    protected BlockRedstoneDiode func_149898_i() {
        return Blocks.unpowered_repeater;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.repeater;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.repeater;
    }

    @Override
    public int getRenderType() {
        return 15;
    }

    @Override
    public boolean func_149910_g(IBlockAccess p_149910_1_, int p_149910_2_, int p_149910_3_, int p_149910_4_, int p_149910_5_) {
        if (this.func_149902_h(p_149910_1_, p_149910_2_, p_149910_3_, p_149910_4_, p_149910_5_) > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean func_149908_a(Block p_149908_1_) {
        return BlockRedstoneRepeater.func_149909_d(p_149908_1_);
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        if (this.field_149914_a) {
            int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            int var7 = BlockRedstoneRepeater.func_149895_l(var6);
            double var8 = (double)((float)p_149734_2_ + 0.5f) + (double)(p_149734_5_.nextFloat() - 0.5f) * 0.2;
            double var10 = (double)((float)p_149734_3_ + 0.4f) + (double)(p_149734_5_.nextFloat() - 0.5f) * 0.2;
            double var12 = (double)((float)p_149734_4_ + 0.5f) + (double)(p_149734_5_.nextFloat() - 0.5f) * 0.2;
            double var14 = 0.0;
            double var16 = 0.0;
            if (p_149734_5_.nextInt(2) == 0) {
                switch (var7) {
                    case 0: {
                        var16 = -0.3125;
                        break;
                    }
                    case 1: {
                        var14 = 0.3125;
                        break;
                    }
                    case 2: {
                        var16 = 0.3125;
                        break;
                    }
                    case 3: {
                        var14 = -0.3125;
                    }
                    default: {
                        break;
                    }
                }
            } else {
                int var18 = (var6 & 12) >> 2;
                switch (var7) {
                    case 0: {
                        var16 = field_149973_b[var18];
                        break;
                    }
                    case 1: {
                        var14 = - field_149973_b[var18];
                        break;
                    }
                    case 2: {
                        var16 = - field_149973_b[var18];
                        break;
                    }
                    case 3: {
                        var14 = field_149973_b[var18];
                    }
                }
            }
            p_149734_1_.spawnParticle("reddust", var8 + var14, var10, var12 + var16, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        this.func_149911_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
    }
}

