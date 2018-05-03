/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class MapGenRavine
extends MapGenBase {
    private float[] field_75046_d = new float[1024];
    private static final String __OBFID = "CL_00000390";

    protected void func_151540_a(long p_151540_1_, int p_151540_3_, int p_151540_4_, Block[] p_151540_5_, double p_151540_6_, double p_151540_8_, double p_151540_10_, float p_151540_12_, float p_151540_13_, float p_151540_14_, int p_151540_15_, int p_151540_16_, double p_151540_17_) {
        Random var19 = new Random(p_151540_1_);
        double var20 = p_151540_3_ * 16 + 8;
        double var22 = p_151540_4_ * 16 + 8;
        float var24 = 0.0f;
        float var25 = 0.0f;
        if (p_151540_16_ <= 0) {
            int var26 = this.range * 16 - 16;
            p_151540_16_ = var26 - var19.nextInt(var26 / 4);
        }
        boolean var54 = false;
        if (p_151540_15_ == -1) {
            p_151540_15_ = p_151540_16_ / 2;
            var54 = true;
        }
        float var27 = 1.0f;
        int var28 = 0;
        while (var28 < 256) {
            if (var28 == 0 || var19.nextInt(3) == 0) {
                var27 = 1.0f + var19.nextFloat() * var19.nextFloat() * 1.0f;
            }
            this.field_75046_d[var28] = var27 * var27;
            ++var28;
        }
        while (p_151540_15_ < p_151540_16_) {
            double var53 = 1.5 + (double)(MathHelper.sin((float)p_151540_15_ * 3.1415927f / (float)p_151540_16_) * p_151540_12_ * 1.0f);
            double var30 = var53 * p_151540_17_;
            var53 *= (double)var19.nextFloat() * 0.25 + 0.75;
            var30 *= (double)var19.nextFloat() * 0.25 + 0.75;
            float var32 = MathHelper.cos(p_151540_14_);
            float var33 = MathHelper.sin(p_151540_14_);
            p_151540_6_ += (double)(MathHelper.cos(p_151540_13_) * var32);
            p_151540_8_ += (double)var33;
            p_151540_10_ += (double)(MathHelper.sin(p_151540_13_) * var32);
            p_151540_14_ *= 0.7f;
            p_151540_14_ += var25 * 0.05f;
            p_151540_13_ += var24 * 0.05f;
            var25 *= 0.8f;
            var24 *= 0.5f;
            var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0f;
            var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0f;
            if (var54 || var19.nextInt(4) != 0) {
                double var34 = p_151540_6_ - var20;
                double var36 = p_151540_10_ - var22;
                double var38 = p_151540_16_ - p_151540_15_;
                double var40 = p_151540_12_ + 2.0f + 16.0f;
                if (var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40) {
                    return;
                }
                if (p_151540_6_ >= var20 - 16.0 - var53 * 2.0 && p_151540_10_ >= var22 - 16.0 - var53 * 2.0 && p_151540_6_ <= var20 + 16.0 + var53 * 2.0 && p_151540_10_ <= var22 + 16.0 + var53 * 2.0) {
                    int var44;
                    int var56 = MathHelper.floor_double(p_151540_6_ - var53) - p_151540_3_ * 16 - 1;
                    int var35 = MathHelper.floor_double(p_151540_6_ + var53) - p_151540_3_ * 16 + 1;
                    int var55 = MathHelper.floor_double(p_151540_8_ - var30) - 1;
                    int var37 = MathHelper.floor_double(p_151540_8_ + var30) + 1;
                    int var57 = MathHelper.floor_double(p_151540_10_ - var53) - p_151540_4_ * 16 - 1;
                    int var39 = MathHelper.floor_double(p_151540_10_ + var53) - p_151540_4_ * 16 + 1;
                    if (var56 < 0) {
                        var56 = 0;
                    }
                    if (var35 > 16) {
                        var35 = 16;
                    }
                    if (var55 < 1) {
                        var55 = 1;
                    }
                    if (var37 > 248) {
                        var37 = 248;
                    }
                    if (var57 < 0) {
                        var57 = 0;
                    }
                    if (var39 > 16) {
                        var39 = 16;
                    }
                    boolean var58 = false;
                    int var41 = var56;
                    while (!var58 && var41 < var35) {
                        int var42 = var57;
                        while (!var58 && var42 < var39) {
                            int var43 = var37 + 1;
                            while (!var58 && var43 >= var55 - 1) {
                                var44 = (var41 * 16 + var42) * 256 + var43;
                                if (var43 >= 0 && var43 < 256) {
                                    Block var45 = p_151540_5_[var44];
                                    if (var45 == Blocks.flowing_water || var45 == Blocks.water) {
                                        var58 = true;
                                    }
                                    if (var43 != var55 - 1 && var41 != var56 && var41 != var35 - 1 && var42 != var57 && var42 != var39 - 1) {
                                        var43 = var55;
                                    }
                                }
                                --var43;
                            }
                            ++var42;
                        }
                        ++var41;
                    }
                    if (!var58) {
                        var41 = var56;
                        while (var41 < var35) {
                            double var60 = ((double)(var41 + p_151540_3_ * 16) + 0.5 - p_151540_6_) / var53;
                            var44 = var57;
                            while (var44 < var39) {
                                double var59 = ((double)(var44 + p_151540_4_ * 16) + 0.5 - p_151540_10_) / var53;
                                int var47 = (var41 * 16 + var44) * 256 + var37;
                                boolean var48 = false;
                                if (var60 * var60 + var59 * var59 < 1.0) {
                                    int var49 = var37 - 1;
                                    while (var49 >= var55) {
                                        double var50 = ((double)var49 + 0.5 - p_151540_8_) / var30;
                                        if ((var60 * var60 + var59 * var59) * (double)this.field_75046_d[var49] + var50 * var50 / 6.0 < 1.0) {
                                            Block var52 = p_151540_5_[var47];
                                            if (var52 == Blocks.grass) {
                                                var48 = true;
                                            }
                                            if (var52 == Blocks.stone || var52 == Blocks.dirt || var52 == Blocks.grass) {
                                                if (var49 < 10) {
                                                    p_151540_5_[var47] = Blocks.flowing_lava;
                                                } else {
                                                    p_151540_5_[var47] = null;
                                                    if (var48 && p_151540_5_[var47 - 1] == Blocks.dirt) {
                                                        p_151540_5_[var47 - 1] = this.worldObj.getBiomeGenForCoords((int)(var41 + p_151540_3_ * 16), (int)(var44 + p_151540_4_ * 16)).topBlock;
                                                    }
                                                }
                                            }
                                        }
                                        --var47;
                                        --var49;
                                    }
                                }
                                ++var44;
                            }
                            ++var41;
                        }
                        if (var54) break;
                    }
                }
            }
            ++p_151540_15_;
        }
    }

    @Override
    protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {
        if (this.rand.nextInt(50) == 0) {
            double var7 = p_151538_2_ * 16 + this.rand.nextInt(16);
            double var9 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
            double var11 = p_151538_3_ * 16 + this.rand.nextInt(16);
            int var13 = 1;
            int var14 = 0;
            while (var14 < var13) {
                float var15 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                float var16 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float var17 = (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f;
                this.func_151540_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var7, var9, var11, var17, var15, var16, 0, 0, 3.0);
                ++var14;
            }
        }
    }
}

