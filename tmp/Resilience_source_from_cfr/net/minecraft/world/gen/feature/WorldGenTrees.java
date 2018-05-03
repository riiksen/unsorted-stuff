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

public class WorldGenTrees
extends WorldGenAbstractTree {
    private final int minTreeHeight;
    private final boolean vinesGrow;
    private final int metaWood;
    private final int metaLeaves;
    private static final String __OBFID = "CL_00000438";

    public WorldGenTrees(boolean par1) {
        this(par1, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5) {
        super(par1);
        this.minTreeHeight = par2;
        this.metaWood = par3;
        this.metaLeaves = par4;
        this.vinesGrow = par5;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = par2Random.nextInt(3) + this.minTreeHeight;
        boolean var7 = true;
        if (par4 >= 1 && par4 + var6 + 1 <= 256) {
            int var11;
            int var9;
            Block var12;
            int var8 = par4;
            while (var8 <= par4 + 1 + var6) {
                var9 = 1;
                if (var8 == par4) {
                    var9 = 0;
                }
                if (var8 >= par4 + 1 + var6 - 2) {
                    var9 = 2;
                }
                int var10 = par3 - var9;
                while (var10 <= par3 + var9 && var7) {
                    var11 = par5 - var9;
                    while (var11 <= par5 + var9 && var7) {
                        if (var8 >= 0 && var8 < 256) {
                            var12 = par1World.getBlock(var10, var8, var11);
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
            Block var19 = par1World.getBlock(par3, par4 - 1, par5);
            if ((var19 == Blocks.grass || var19 == Blocks.dirt || var19 == Blocks.farmland) && par4 < 256 - var6 - 1) {
                int var15;
                int var13;
                int var14;
                int var21;
                this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                var9 = 3;
                int var20 = 0;
                var11 = par4 - var9 + var6;
                while (var11 <= par4 + var6) {
                    var21 = var11 - (par4 + var6);
                    var13 = var20 + 1 - var21 / 2;
                    var14 = par3 - var13;
                    while (var14 <= par3 + var13) {
                        var15 = var14 - par3;
                        int var16 = par5 - var13;
                        while (var16 <= par5 + var13) {
                            Block var18;
                            int var17 = var16 - par5;
                            if ((Math.abs(var15) != var13 || Math.abs(var17) != var13 || par2Random.nextInt(2) != 0 && var21 != 0) && ((var18 = par1World.getBlock(var14, var11, var16)).getMaterial() == Material.air || var18.getMaterial() == Material.leaves)) {
                                this.func_150516_a(par1World, var14, var11, var16, Blocks.leaves, this.metaLeaves);
                            }
                            ++var16;
                        }
                        ++var14;
                    }
                    ++var11;
                }
                var11 = 0;
                while (var11 < var6) {
                    var12 = par1World.getBlock(par3, par4 + var11, par5);
                    if (var12.getMaterial() == Material.air || var12.getMaterial() == Material.leaves) {
                        this.func_150516_a(par1World, par3, par4 + var11, par5, Blocks.log, this.metaWood);
                        if (this.vinesGrow && var11 > 0) {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var11, par5)) {
                                this.func_150516_a(par1World, par3 - 1, par4 + var11, par5, Blocks.vine, 8);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var11, par5)) {
                                this.func_150516_a(par1World, par3 + 1, par4 + var11, par5, Blocks.vine, 2);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 - 1)) {
                                this.func_150516_a(par1World, par3, par4 + var11, par5 - 1, Blocks.vine, 1);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 + 1)) {
                                this.func_150516_a(par1World, par3, par4 + var11, par5 + 1, Blocks.vine, 4);
                            }
                        }
                    }
                    ++var11;
                }
                if (this.vinesGrow) {
                    var11 = par4 - 3 + var6;
                    while (var11 <= par4 + var6) {
                        var21 = var11 - (par4 + var6);
                        var13 = 2 - var21 / 2;
                        var14 = par3 - var13;
                        while (var14 <= par3 + var13) {
                            var15 = par5 - var13;
                            while (var15 <= par5 + var13) {
                                if (par1World.getBlock(var14, var11, var15).getMaterial() == Material.leaves) {
                                    if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14 - 1, var11, var15).getMaterial() == Material.air) {
                                        this.growVines(par1World, var14 - 1, var11, var15, 8);
                                    }
                                    if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14 + 1, var11, var15).getMaterial() == Material.air) {
                                        this.growVines(par1World, var14 + 1, var11, var15, 2);
                                    }
                                    if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14, var11, var15 - 1).getMaterial() == Material.air) {
                                        this.growVines(par1World, var14, var11, var15 - 1, 1);
                                    }
                                    if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14, var11, var15 + 1).getMaterial() == Material.air) {
                                        this.growVines(par1World, var14, var11, var15 + 1, 4);
                                    }
                                }
                                ++var15;
                            }
                            ++var14;
                        }
                        ++var11;
                    }
                    if (par2Random.nextInt(5) == 0 && var6 > 5) {
                        var11 = 0;
                        while (var11 < 2) {
                            var21 = 0;
                            while (var21 < 4) {
                                if (par2Random.nextInt(4 - var11) == 0) {
                                    var13 = par2Random.nextInt(3);
                                    this.func_150516_a(par1World, par3 + Direction.offsetX[Direction.rotateOpposite[var21]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.rotateOpposite[var21]], Blocks.cocoa, var13 << 2 | var21);
                                }
                                ++var21;
                            }
                            ++var11;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void growVines(World par1World, int par2, int par3, int par4, int par5) {
        this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
        int var6 = 4;
        while (par1World.getBlock(par2, --par3, par4).getMaterial() == Material.air && var6 > 0) {
            this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
            --var6;
        }
        return;
    }
}

