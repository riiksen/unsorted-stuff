/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BlockLeaves
extends BlockLeavesBase {
    int[] field_150128_a;
    protected int field_150127_b;
    protected IIcon[][] field_150129_M = new IIcon[2][];
    private static final String __OBFID = "CL_00000263";

    public BlockLeaves() {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeGrass);
    }

    @Override
    public int getBlockColor() {
        double var1 = 0.5;
        double var3 = 1.0;
        return ColorizerFoliage.getFoliageColor(var1, var3);
    }

    @Override
    public int getRenderColor(int p_149741_1_) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = -1;
        while (var8 <= 1) {
            int var9 = -1;
            while (var9 <= 1) {
                int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).getBiomeFoliageColor(p_149720_2_ + var9, p_149720_3_, p_149720_4_ + var8);
                var5 += (var10 & 16711680) >> 16;
                var6 += (var10 & 65280) >> 8;
                var7 += var10 & 255;
                ++var9;
            }
            ++var8;
        }
        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        int var7 = 1;
        int var8 = var7 + 1;
        if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
            int var9 = - var7;
            while (var9 <= var7) {
                int var10 = - var7;
                while (var10 <= var7) {
                    int var11 = - var7;
                    while (var11 <= var7) {
                        if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves) {
                            int var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11);
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
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        int var6;
        if (!p_149674_1_.isClient && ((var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_)) & 8) != 0 && (var6 & 4) == 0) {
            int var12;
            int var7 = 4;
            int var8 = var7 + 1;
            int var9 = 32;
            int var10 = var9 * var9;
            int var11 = var9 / 2;
            if (this.field_150128_a == null) {
                this.field_150128_a = new int[var9 * var9 * var9];
            }
            if (p_149674_1_.checkChunksExist(p_149674_2_ - var8, p_149674_3_ - var8, p_149674_4_ - var8, p_149674_2_ + var8, p_149674_3_ + var8, p_149674_4_ + var8)) {
                int var13;
                int var14;
                var12 = - var7;
                while (var12 <= var7) {
                    var13 = - var7;
                    while (var13 <= var7) {
                        var14 = - var7;
                        while (var14 <= var7) {
                            Block var15 = p_149674_1_.getBlock(p_149674_2_ + var12, p_149674_3_ + var13, p_149674_4_ + var14);
                            this.field_150128_a[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = var15 != Blocks.log && var15 != Blocks.log2 ? (var15.getMaterial() == Material.leaves ? -2 : -1) : 0;
                            ++var14;
                        }
                        ++var13;
                    }
                    ++var12;
                }
                var12 = 1;
                while (var12 <= 4) {
                    var13 = - var7;
                    while (var13 <= var7) {
                        var14 = - var7;
                        while (var14 <= var7) {
                            int var16 = - var7;
                            while (var16 <= var7) {
                                if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11] == var12 - 1) {
                                    if (this.field_150128_a[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11] == -2) {
                                        this.field_150128_a[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11] = var12;
                                    }
                                    if (this.field_150128_a[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11] == -2) {
                                        this.field_150128_a[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11] = var12;
                                    }
                                    if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11] == -2) {
                                        this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11] = var12;
                                    }
                                    if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11] == -2) {
                                        this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11] = var12;
                                    }
                                    if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1)] == -2) {
                                        this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1)] = var12;
                                    }
                                    if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1] == -2) {
                                        this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1] = var12;
                                    }
                                }
                                ++var16;
                            }
                            ++var14;
                        }
                        ++var13;
                    }
                    ++var12;
                }
            }
            if ((var12 = this.field_150128_a[var11 * var10 + var11 * var9 + var11]) >= 0) {
                p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 & -9, 4);
            } else {
                this.func_150126_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
            }
        }
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        if (p_149734_1_.canLightningStrikeAt(p_149734_2_, p_149734_3_ + 1, p_149734_4_) && !World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && p_149734_5_.nextInt(15) == 1) {
            double var6 = (float)p_149734_2_ + p_149734_5_.nextFloat();
            double var8 = (double)p_149734_3_ - 0.05;
            double var10 = (float)p_149734_4_ + p_149734_5_.nextFloat();
            p_149734_1_.spawnParticle("dripWater", var6, var8, var10, 0.0, 0.0, 0.0);
        }
    }

    private void func_150126_e(World p_150126_1_, int p_150126_2_, int p_150126_3_, int p_150126_4_) {
        this.dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_, p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_, p_150126_3_, p_150126_4_), 0);
        p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return p_149745_1_.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.sapling);
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            int var8 = this.func_150123_b(p_149690_5_);
            if (p_149690_7_ > 0 && (var8 -= 2 << p_149690_7_) < 10) {
                var8 = 10;
            }
            if (p_149690_1_.rand.nextInt(var8) == 0) {
                Item var9 = this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_);
                this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var9, 1, this.damageDropped(p_149690_5_)));
            }
            var8 = 200;
            if (p_149690_7_ > 0 && (var8 -= 10 << p_149690_7_) < 40) {
                var8 = 40;
            }
            this.func_150124_c(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, var8);
        }
    }

    protected void func_150124_c(World p_150124_1_, int p_150124_2_, int p_150124_3_, int p_150124_4_, int p_150124_5_, int p_150124_6_) {
    }

    protected int func_150123_b(int p_150123_1_) {
        return 20;
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Item.getItemFromBlock(this), 1, p_149636_6_ & 3));
        } else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_ & 3;
    }

    @Override
    public boolean isOpaqueCube() {
        return !this.field_150121_P;
    }

    @Override
    public abstract IIcon getIcon(int var1, int var2);

    public void func_150122_b(boolean p_150122_1_) {
        this.field_150121_P = p_150122_1_;
        this.field_150127_b = p_150122_1_ ? 0 : 1;
    }

    @Override
    protected ItemStack createStackedBlock(int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_ & 3);
    }

    public abstract String[] func_150125_e();
}

