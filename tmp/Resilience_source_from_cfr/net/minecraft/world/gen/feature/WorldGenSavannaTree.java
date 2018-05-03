/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenSavannaTree
extends WorldGenAbstractTree {
    private static final String __OBFID = "CL_00000432";

    public WorldGenSavannaTree(boolean p_i45463_1_) {
        super(p_i45463_1_);
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = par2Random.nextInt(3) + par2Random.nextInt(3) + 5;
        boolean var7 = true;
        if (par4 >= 1 && par4 + var6 + 1 <= 256) {
            int var11;
            int var10;
            int var8 = par4;
            while (var8 <= par4 + 1 + var6) {
                int var9 = 1;
                if (var8 == par4) {
                    var9 = 0;
                }
                if (var8 >= par4 + 1 + var6 - 2) {
                    var9 = 2;
                }
                var10 = par3 - var9;
                while (var10 <= par3 + var9 && var7) {
                    var11 = par5 - var9;
                    while (var11 <= par5 + var9 && var7) {
                        if (var8 >= 0 && var8 < 256) {
                            Block var12 = par1World.getBlock(var10, var8, var11);
                            if (!this.func_150523_a(var12)) {
                                var7 = false;
                            }
                        } else {
                            var7 = false;
                        }
                        ++var11;
                    }
                    ++var10;
                }
                ++var8;
            }
            if (!var7) {
                return false;
            }
            Block var21 = par1World.getBlock(par3, par4 - 1, par5);
            if ((var21 == Blocks.grass || var21 == Blocks.dirt) && par4 < 256 - var6 - 1) {
                int var16;
                this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                int var22 = par2Random.nextInt(4);
                var10 = var6 - par2Random.nextInt(4) - 1;
                var11 = 3 - par2Random.nextInt(3);
                int var23 = par3;
                int var13 = par5;
                int var14 = 0;
                int var15 = 0;
                while (var15 < var6) {
                    Block var17;
                    var16 = par4 + var15;
                    if (var15 >= var10 && var11 > 0) {
                        var23 += Direction.offsetX[var22];
                        var13 += Direction.offsetZ[var22];
                        --var11;
                    }
                    if ((var17 = par1World.getBlock(var23, var16, var13)).getMaterial() == Material.air || var17.getMaterial() == Material.leaves) {
                        this.func_150516_a(par1World, var23, var16, var13, Blocks.log2, 0);
                        var14 = var16;
                    }
                    ++var15;
                }
                var15 = -1;
                while (var15 <= 1) {
                    var16 = -1;
                    while (var16 <= 1) {
                        this.func_150525_a(par1World, var23 + var15, var14 + 1, var13 + var16);
                        ++var16;
                    }
                    ++var15;
                }
                this.func_150525_a(par1World, var23 + 2, var14 + 1, var13);
                this.func_150525_a(par1World, var23 - 2, var14 + 1, var13);
                this.func_150525_a(par1World, var23, var14 + 1, var13 + 2);
                this.func_150525_a(par1World, var23, var14 + 1, var13 - 2);
                var15 = -3;
                while (var15 <= 3) {
                    var16 = -3;
                    while (var16 <= 3) {
                        if (Math.abs(var15) != 3 || Math.abs(var16) != 3) {
                            this.func_150525_a(par1World, var23 + var15, var14, var13 + var16);
                        }
                        ++var16;
                    }
                    ++var15;
                }
                var23 = par3;
                var13 = par5;
                var15 = par2Random.nextInt(4);
                if (var15 != var22) {
                    int var19;
                    var16 = var10 - par2Random.nextInt(2) - 1;
                    int var24 = 1 + par2Random.nextInt(3);
                    var14 = 0;
                    int var18 = var16;
                    while (var18 < var6 && var24 > 0) {
                        Block var20;
                        if (var18 >= 1 && ((var20 = par1World.getBlock(var23 += Direction.offsetX[var15], var19 = par4 + var18, var13 += Direction.offsetZ[var15])).getMaterial() == Material.air || var20.getMaterial() == Material.leaves)) {
                            this.func_150516_a(par1World, var23, var19, var13, Blocks.log2, 0);
                            var14 = var19;
                        }
                        ++var18;
                        --var24;
                    }
                    if (var14 > 0) {
                        var18 = -1;
                        while (var18 <= 1) {
                            var19 = -1;
                            while (var19 <= 1) {
                                this.func_150525_a(par1World, var23 + var18, var14 + 1, var13 + var19);
                                ++var19;
                            }
                            ++var18;
                        }
                        var18 = -2;
                        while (var18 <= 2) {
                            var19 = -2;
                            while (var19 <= 2) {
                                if (Math.abs(var18) != 2 || Math.abs(var19) != 2) {
                                    this.func_150525_a(par1World, var23 + var18, var14, var13 + var19);
                                }
                                ++var19;
                            }
                            ++var18;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void func_150525_a(World p_150525_1_, int p_150525_2_, int p_150525_3_, int p_150525_4_) {
        Block var5 = p_150525_1_.getBlock(p_150525_2_, p_150525_3_, p_150525_4_);
        if (var5.getMaterial() == Material.air || var5.getMaterial() == Material.leaves) {
            this.func_150516_a(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_, Blocks.leaves2, 0);
        }
    }
}

