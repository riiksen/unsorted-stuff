/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockRedstoneOre
extends Block {
    private boolean field_150187_a;
    private static final String __OBFID = "CL_00000294";

    public BlockRedstoneOre(boolean p_i45420_1_) {
        super(Material.rock);
        if (p_i45420_1_) {
            this.setTickRandomly(true);
        }
        this.field_150187_a = p_i45420_1_;
    }

    @Override
    public int func_149738_a(World p_149738_1_) {
        return 30;
    }

    @Override
    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
        this.func_150185_e(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
        super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }

    @Override
    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_) {
        this.func_150185_e(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_);
        super.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        this.func_150185_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    private void func_150185_e(World p_150185_1_, int p_150185_2_, int p_150185_3_, int p_150185_4_) {
        this.func_150186_m(p_150185_1_, p_150185_2_, p_150185_3_, p_150185_4_);
        if (this == Blocks.redstone_ore) {
            p_150185_1_.setBlock(p_150185_2_, p_150185_3_, p_150185_4_, Blocks.lit_redstone_ore);
        }
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        if (this == Blocks.lit_redstone_ore) {
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_ore);
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.redstone;
    }

    @Override
    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
        return this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 4 + p_149745_1_.nextInt(2);
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        if (this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_) != Item.getItemFromBlock(this)) {
            int var8 = 1 + p_149690_1_.rand.nextInt(5);
            this.dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
        }
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        if (this.field_150187_a) {
            this.func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
        }
    }

    private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_) {
        Random var5 = p_150186_1_.rand;
        double var6 = 0.0625;
        int var8 = 0;
        while (var8 < 6) {
            double var9 = (float)p_150186_2_ + var5.nextFloat();
            double var11 = (float)p_150186_3_ + var5.nextFloat();
            double var13 = (float)p_150186_4_ + var5.nextFloat();
            if (var8 == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube()) {
                var11 = (double)(p_150186_3_ + 1) + var6;
            }
            if (var8 == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube()) {
                var11 = (double)(p_150186_3_ + 0) - var6;
            }
            if (var8 == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube()) {
                var13 = (double)(p_150186_4_ + 1) + var6;
            }
            if (var8 == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube()) {
                var13 = (double)(p_150186_4_ + 0) - var6;
            }
            if (var8 == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
                var9 = (double)(p_150186_2_ + 1) + var6;
            }
            if (var8 == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
                var9 = (double)(p_150186_2_ + 0) - var6;
            }
            if (var9 < (double)p_150186_2_ || var9 > (double)(p_150186_2_ + 1) || var11 < 0.0 || var11 > (double)(p_150186_3_ + 1) || var13 < (double)p_150186_4_ || var13 > (double)(p_150186_4_ + 1)) {
                p_150186_1_.spawnParticle("reddust", var9, var11, var13, 0.0, 0.0, 0.0);
            }
            ++var8;
        }
    }

    @Override
    protected ItemStack createStackedBlock(int p_149644_1_) {
        return new ItemStack(Blocks.redstone_ore);
    }
}

