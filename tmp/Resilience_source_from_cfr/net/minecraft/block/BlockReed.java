/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockReed
extends Block {
    private static final String __OBFID = "CL_00000300";

    protected BlockReed() {
        super(Material.plants);
        float var1 = 0.375f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 1.0f, 0.5f + var1);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        if ((p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.reeds || this.func_150170_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) && p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)) {
            int var6 = 1;
            while (p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - var6, p_149674_4_) == this) {
                ++var6;
            }
            if (var6 < 3) {
                int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
                if (var7 == 15) {
                    p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 4);
                } else {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + 1, 4);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        Block var5 = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
        return var5 == this ? true : (var5 != Blocks.grass && var5 != Blocks.dirt && var5 != Blocks.sand ? false : (p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_ - 1, p_149742_4_).getMaterial() == Material.water ? true : (p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_ - 1, p_149742_4_).getMaterial() == Material.water ? true : (p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_ - 1).getMaterial() == Material.water ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_ + 1).getMaterial() == Material.water))));
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        this.func_150170_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    protected final boolean func_150170_e(World p_150170_1_, int p_150170_2_, int p_150170_3_, int p_150170_4_) {
        if (!this.canBlockStay(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_)) {
            this.dropBlockAsItem(p_150170_1_, p_150170_2_, p_150170_3_, p_150170_4_, p_150170_1_.getBlockMetadata(p_150170_2_, p_150170_3_, p_150170_4_), 0);
            p_150170_1_.setBlockToAir(p_150170_2_, p_150170_3_, p_150170_4_);
            return false;
        }
        return true;
    }

    @Override
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return this.canPlaceBlockAt(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.reeds;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.reeds;
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }
}

