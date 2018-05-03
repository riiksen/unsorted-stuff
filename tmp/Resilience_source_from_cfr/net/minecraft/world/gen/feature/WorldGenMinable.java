/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMinable
extends WorldGenerator {
    private Block field_150519_a;
    private int numberOfBlocks;
    private Block field_150518_c;
    private static final String __OBFID = "CL_00000426";

    public WorldGenMinable(Block p_i45459_1_, int p_i45459_2_) {
        this(p_i45459_1_, p_i45459_2_, Blocks.stone);
    }

    public WorldGenMinable(Block p_i45460_1_, int p_i45460_2_, Block p_i45460_3_) {
        this.field_150519_a = p_i45460_1_;
        this.numberOfBlocks = p_i45460_2_;
        this.field_150518_c = p_i45460_3_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        float var6 = par2Random.nextFloat() * 3.1415927f;
        double var7 = (float)(par3 + 8) + MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var9 = (float)(par3 + 8) - MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var11 = (float)(par5 + 8) + MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var13 = (float)(par5 + 8) - MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var15 = par4 + par2Random.nextInt(3) - 2;
        double var17 = par4 + par2Random.nextInt(3) - 2;
        int var19 = 0;
        while (var19 <= this.numberOfBlocks) {
            double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.numberOfBlocks;
            double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.numberOfBlocks;
            double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.numberOfBlocks;
            double var26 = par2Random.nextDouble() * (double)this.numberOfBlocks / 16.0;
            double var28 = (double)(MathHelper.sin((float)var19 * 3.1415927f / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            double var30 = (double)(MathHelper.sin((float)var19 * 3.1415927f / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            int var32 = MathHelper.floor_double(var20 - var28 / 2.0);
            int var33 = MathHelper.floor_double(var22 - var30 / 2.0);
            int var34 = MathHelper.floor_double(var24 - var28 / 2.0);
            int var35 = MathHelper.floor_double(var20 + var28 / 2.0);
            int var36 = MathHelper.floor_double(var22 + var30 / 2.0);
            int var37 = MathHelper.floor_double(var24 + var28 / 2.0);
            int var38 = var32;
            while (var38 <= var35) {
                double var39 = ((double)var38 + 0.5 - var20) / (var28 / 2.0);
                if (var39 * var39 < 1.0) {
                    int var41 = var33;
                    while (var41 <= var36) {
                        double var42 = ((double)var41 + 0.5 - var22) / (var30 / 2.0);
                        if (var39 * var39 + var42 * var42 < 1.0) {
                            int var44 = var34;
                            while (var44 <= var37) {
                                double var45 = ((double)var44 + 0.5 - var24) / (var28 / 2.0);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0 && par1World.getBlock(var38, var41, var44) == this.field_150518_c) {
                                    par1World.setBlock(var38, var41, var44, this.field_150519_a, 0, 2);
                                }
                                ++var44;
                            }
                        }
                        ++var41;
                    }
                }
                ++var38;
            }
            ++var19;
        }
        return true;
    }
}

