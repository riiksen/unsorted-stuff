/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockSapling
extends BlockBush
implements IGrowable {
    public static final String[] field_149882_a = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "roofed_oak"};
    private static final IIcon[] field_149881_b = new IIcon[field_149882_a.length];
    private static final String __OBFID = "CL_00000305";

    protected BlockSapling() {
        float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var1 * 2.0f, 0.5f + var1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && p_149674_5_.nextInt(7) == 0) {
                this.func_149879_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            }
        }
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return field_149881_b[MathHelper.clamp_int(p_149691_2_ &= 7, 0, 5)];
    }

    public void func_149879_c(World p_149879_1_, int p_149879_2_, int p_149879_3_, int p_149879_4_, Random p_149879_5_) {
        int var6 = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);
        if ((var6 & 8) == 0) {
            p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, var6 | 8, 4);
        } else {
            this.func_149878_d(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
        }
    }

    public void func_149878_d(World p_149878_1_, int p_149878_2_, int p_149878_3_, int p_149878_4_, Random p_149878_5_) {
        int var6 = p_149878_1_.getBlockMetadata(p_149878_2_, p_149878_3_, p_149878_4_) & 7;
        WorldGenAbstractTree var7 = p_149878_5_.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;
        switch (var6) {
            default: {
                break;
            }
            case 1: {
                var8 = 0;
                block7 : while (var8 >= -1) {
                    var9 = 0;
                    while (var9 >= -1) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 1)) {
                            var7 = new WorldGenMegaPineTree(false, p_149878_5_.nextBoolean());
                            var10 = true;
                            break block7;
                        }
                        --var9;
                    }
                    --var8;
                }
                if (var10) break;
                var9 = 0;
                var8 = 0;
                var7 = new WorldGenTaiga2(true);
                break;
            }
            case 2: {
                var7 = new WorldGenForest(true, false);
                break;
            }
            case 3: {
                var8 = 0;
                block9 : while (var8 >= -1) {
                    var9 = 0;
                    while (var9 >= -1) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 3)) {
                            var7 = new WorldGenMegaJungle(true, 10, 20, 3, 3);
                            var10 = true;
                            break block9;
                        }
                        --var9;
                    }
                    --var8;
                }
                if (var10) break;
                var9 = 0;
                var8 = 0;
                var7 = new WorldGenTrees(true, 4 + p_149878_5_.nextInt(7), 3, 3, false);
                break;
            }
            case 4: {
                var7 = new WorldGenSavannaTree(true);
                break;
            }
            case 5: {
                var8 = 0;
                block11 : while (var8 >= -1) {
                    var9 = 0;
                    while (var9 >= -1) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 5)) {
                            var7 = new WorldGenCanopyTree(true);
                            var10 = true;
                            break block11;
                        }
                        --var9;
                    }
                    --var8;
                }
                if (var10) break;
                return;
            }
        }
        Block var11 = Blocks.air;
        if (var10) {
            p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
        } else {
            p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, var11, 0, 4);
        }
        if (!((WorldGenerator)var7).generate(p_149878_1_, p_149878_5_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9)) {
            if (var10) {
                p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
            } else {
                p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, this, var6, 4);
            }
        }
    }

    public boolean func_149880_a(World p_149880_1_, int p_149880_2_, int p_149880_3_, int p_149880_4_, int p_149880_5_) {
        if (p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this && (p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 7) == p_149880_5_) {
            return true;
        }
        return false;
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return MathHelper.clamp_int(p_149692_1_ & 7, 0, 5);
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        int var2 = 0;
        while (var2 < field_149881_b.length) {
            BlockSapling.field_149881_b[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + field_149882_a[var2]);
            ++var2;
        }
    }

    @Override
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
        return true;
    }

    @Override
    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
        if ((double)p_149852_1_.rand.nextFloat() < 0.45) {
            return true;
        }
        return false;
    }

    @Override
    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {
        this.func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}

