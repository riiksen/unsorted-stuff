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
import net.minecraft.world.gen.MapGenBase;

public class MapGenCavesHell
extends MapGenBase {
    private static final String __OBFID = "CL_00000395";

    protected void func_151544_a(long p_151544_1_, int p_151544_3_, int p_151544_4_, Block[] p_151544_5_, double p_151544_6_, double p_151544_8_, double p_151544_10_) {
        this.func_151543_a(p_151544_1_, p_151544_3_, p_151544_4_, p_151544_5_, p_151544_6_, p_151544_8_, p_151544_10_, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }

    protected void func_151543_a(long p_151543_1_, int p_151543_3_, int p_151543_4_, Block[] p_151543_5_, double p_151543_6_, double p_151543_8_, double p_151543_10_, float p_151543_12_, float p_151543_13_, float p_151543_14_, int p_151543_15_, int p_151543_16_, double p_151543_17_) {
        double var19 = p_151543_3_ * 16 + 8;
        double var21 = p_151543_4_ * 16 + 8;
        float var23 = 0.0f;
        float var24 = 0.0f;
        Random var25 = new Random(p_151543_1_);
        if (p_151543_16_ <= 0) {
            int var26 = this.range * 16 - 16;
            p_151543_16_ = var26 - var25.nextInt(var26 / 4);
        }
        boolean var53 = false;
        if (p_151543_15_ == -1) {
            p_151543_15_ = p_151543_16_ / 2;
            var53 = true;
        }
        int var27 = var25.nextInt(p_151543_16_ / 2) + p_151543_16_ / 4;
        boolean var28 = var25.nextInt(6) == 0;
        while (p_151543_15_ < p_151543_16_) {
            double var29 = 1.5 + (double)(MathHelper.sin((float)p_151543_15_ * 3.1415927f / (float)p_151543_16_) * p_151543_12_ * 1.0f);
            double var31 = var29 * p_151543_17_;
            float var33 = MathHelper.cos(p_151543_14_);
            float var34 = MathHelper.sin(p_151543_14_);
            p_151543_6_ += (double)(MathHelper.cos(p_151543_13_) * var33);
            p_151543_8_ += (double)var34;
            p_151543_10_ += (double)(MathHelper.sin(p_151543_13_) * var33);
            p_151543_14_ = var28 ? (p_151543_14_ *= 0.92f) : (p_151543_14_ *= 0.7f);
            p_151543_14_ += var24 * 0.1f;
            p_151543_13_ += var23 * 0.1f;
            var24 *= 0.9f;
            var23 *= 0.75f;
            var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0f;
            var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0f;
            if (!var53 && p_151543_15_ == var27 && p_151543_12_ > 1.0f) {
                this.func_151543_a(var25.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var25.nextFloat() * 0.5f + 0.5f, p_151543_13_ - 1.5707964f, p_151543_14_ / 3.0f, p_151543_15_, p_151543_16_, 1.0);
                this.func_151543_a(var25.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var25.nextFloat() * 0.5f + 0.5f, p_151543_13_ + 1.5707964f, p_151543_14_ / 3.0f, p_151543_15_, p_151543_16_, 1.0);
                return;
            }
            if (var53 || var25.nextInt(4) != 0) {
                double var35 = p_151543_6_ - var19;
                double var37 = p_151543_10_ - var21;
                double var39 = p_151543_16_ - p_151543_15_;
                double var41 = p_151543_12_ + 2.0f + 16.0f;
                if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
                    return;
                }
                if (p_151543_6_ >= var19 - 16.0 - var29 * 2.0 && p_151543_10_ >= var21 - 16.0 - var29 * 2.0 && p_151543_6_ <= var19 + 16.0 + var29 * 2.0 && p_151543_10_ <= var21 + 16.0 + var29 * 2.0) {
                    int var45;
                    int var54 = MathHelper.floor_double(p_151543_6_ - var29) - p_151543_3_ * 16 - 1;
                    int var36 = MathHelper.floor_double(p_151543_6_ + var29) - p_151543_3_ * 16 + 1;
                    int var56 = MathHelper.floor_double(p_151543_8_ - var31) - 1;
                    int var38 = MathHelper.floor_double(p_151543_8_ + var31) + 1;
                    int var55 = MathHelper.floor_double(p_151543_10_ - var29) - p_151543_4_ * 16 - 1;
                    int var40 = MathHelper.floor_double(p_151543_10_ + var29) - p_151543_4_ * 16 + 1;
                    if (var54 < 0) {
                        var54 = 0;
                    }
                    if (var36 > 16) {
                        var36 = 16;
                    }
                    if (var56 < 1) {
                        var56 = 1;
                    }
                    if (var38 > 120) {
                        var38 = 120;
                    }
                    if (var55 < 0) {
                        var55 = 0;
                    }
                    if (var40 > 16) {
                        var40 = 16;
                    }
                    boolean var57 = false;
                    int var42 = var54;
                    while (!var57 && var42 < var36) {
                        int var43 = var55;
                        while (!var57 && var43 < var40) {
                            int var44 = var38 + 1;
                            while (!var57 && var44 >= var56 - 1) {
                                var45 = (var42 * 16 + var43) * 128 + var44;
                                if (var44 >= 0 && var44 < 128) {
                                    Block var46 = p_151543_5_[var45];
                                    if (var46 == Blocks.flowing_lava || var46 == Blocks.lava) {
                                        var57 = true;
                                    }
                                    if (var44 != var56 - 1 && var42 != var54 && var42 != var36 - 1 && var43 != var55 && var43 != var40 - 1) {
                                        var44 = var56;
                                    }
                                }
                                --var44;
                            }
                            ++var43;
                        }
                        ++var42;
                    }
                    if (!var57) {
                        var42 = var54;
                        while (var42 < var36) {
                            double var59 = ((double)(var42 + p_151543_3_ * 16) + 0.5 - p_151543_6_) / var29;
                            var45 = var55;
                            while (var45 < var40) {
                                double var58 = ((double)(var45 + p_151543_4_ * 16) + 0.5 - p_151543_10_) / var29;
                                int var48 = (var42 * 16 + var45) * 128 + var38;
                                int var49 = var38 - 1;
                                while (var49 >= var56) {
                                    Block var52;
                                    double var50 = ((double)var49 + 0.5 - p_151543_8_) / var31;
                                    if (var50 > -0.7 && var59 * var59 + var50 * var50 + var58 * var58 < 1.0 && ((var52 = p_151543_5_[var48]) == Blocks.netherrack || var52 == Blocks.dirt || var52 == Blocks.grass)) {
                                        p_151543_5_[var48] = null;
                                    }
                                    --var48;
                                    --var49;
                                }
                                ++var45;
                            }
                            ++var42;
                        }
                        if (var53) break;
                    }
                }
            }
            ++p_151543_15_;
        }
    }

    @Override
    protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {
        int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
        if (this.rand.nextInt(5) != 0) {
            var7 = 0;
        }
        int var8 = 0;
        while (var8 < var7) {
            double var9 = p_151538_2_ * 16 + this.rand.nextInt(16);
            double var11 = this.rand.nextInt(128);
            double var13 = p_151538_3_ * 16 + this.rand.nextInt(16);
            int var15 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.func_151544_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13);
                var15 += this.rand.nextInt(4);
            }
            int var16 = 0;
            while (var16 < var15) {
                float var17 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                float var18 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float var19 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                this.func_151543_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13, var19 * 2.0f, var17, var18, 0, 0, 0.5);
                ++var16;
            }
            ++var8;
        }
    }
}

