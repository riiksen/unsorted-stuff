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
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTaiga2
extends WorldGenAbstractTree {
    private static final String __OBFID = "CL_00000435";

    public WorldGenTaiga2(boolean par1) {
        super(par1);
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = par2Random.nextInt(4) + 6;
        int var7 = 1 + par2Random.nextInt(2);
        int var8 = var6 - var7;
        int var9 = 2 + par2Random.nextInt(2);
        boolean var10 = true;
        if (par4 >= 1 && par4 + var6 + 1 <= 256) {
            int var13;
            int var21;
            int var11 = par4;
            while (var11 <= par4 + 1 + var6 && var10) {
                boolean var12 = true;
                var21 = var11 - par4 < var7 ? 0 : var9;
                var13 = par3 - var21;
                while (var13 <= par3 + var21 && var10) {
                    int var14 = par5 - var21;
                    while (var14 <= par5 + var21 && var10) {
                        if (var11 >= 0 && var11 < 256) {
                            Block var15 = par1World.getBlock(var13, var11, var14);
                            if (var15.getMaterial() != Material.air && var15.getMaterial() != Material.leaves) {
                                var10 = false;
                            }
                        } else {
                            var10 = false;
                        }
                        ++var14;
                    }
                    ++var13;
                }
                ++var11;
            }
            if (!var10) {
                return false;
            }
            Block var22 = par1World.getBlock(par3, par4 - 1, par5);
            if ((var22 == Blocks.grass || var22 == Blocks.dirt || var22 == Blocks.farmland) && par4 < 256 - var6 - 1) {
                int var16;
                this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                var21 = par2Random.nextInt(2);
                var13 = 1;
                int var24 = 0;
                int var23 = 0;
                while (var23 <= var8) {
                    var16 = par4 + var6 - var23;
                    int var17 = par3 - var21;
                    while (var17 <= par3 + var21) {
                        int var18 = var17 - par3;
                        int var19 = par5 - var21;
                        while (var19 <= par5 + var21) {
                            int var20 = var19 - par5;
                            if (!(Math.abs(var18) == var21 && Math.abs(var20) == var21 && var21 > 0 || par1World.getBlock(var17, var16, var19).func_149730_j())) {
                                this.func_150516_a(par1World, var17, var16, var19, Blocks.leaves, 1);
                            }
                            ++var19;
                        }
                        ++var17;
                    }
                    if (var21 >= var13) {
                        var21 = var24;
                        var24 = 1;
                        if (++var13 > var9) {
                            var13 = var9;
                        }
                    } else {
                        ++var21;
                    }
                    ++var23;
                }
                var23 = par2Random.nextInt(3);
                var16 = 0;
                while (var16 < var6 - var23) {
                    Block var25 = par1World.getBlock(par3, par4 + var16, par5);
                    if (var25.getMaterial() == Material.air || var25.getMaterial() == Material.leaves) {
                        this.func_150516_a(par1World, par3, par4 + var16, par5, Blocks.log, 1);
                    }
                    ++var16;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

