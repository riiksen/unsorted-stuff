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

public class WorldGenForest
extends WorldGenAbstractTree {
    private boolean field_150531_a;
    private static final String __OBFID = "CL_00000401";

    public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_) {
        super(p_i45449_1_);
        this.field_150531_a = p_i45449_2_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = par2Random.nextInt(3) + 5;
        if (this.field_150531_a) {
            var6 += par2Random.nextInt(7);
        }
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
            Block var17 = par1World.getBlock(par3, par4 - 1, par5);
            if ((var17 == Blocks.grass || var17 == Blocks.dirt || var17 == Blocks.farmland) && par4 < 256 - var6 - 1) {
                this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                int var19 = par4 - 3 + var6;
                while (var19 <= par4 + var6) {
                    var10 = var19 - (par4 + var6);
                    var11 = 1 - var10 / 2;
                    int var20 = par3 - var11;
                    while (var20 <= par3 + var11) {
                        int var13 = var20 - par3;
                        int var14 = par5 - var11;
                        while (var14 <= par5 + var11) {
                            Block var16;
                            int var15 = var14 - par5;
                            if ((Math.abs(var13) != var11 || Math.abs(var15) != var11 || par2Random.nextInt(2) != 0 && var10 != 0) && ((var16 = par1World.getBlock(var20, var19, var14)).getMaterial() == Material.air || var16.getMaterial() == Material.leaves)) {
                                this.func_150516_a(par1World, var20, var19, var14, Blocks.leaves, 2);
                            }
                            ++var14;
                        }
                        ++var20;
                    }
                    ++var19;
                }
                var19 = 0;
                while (var19 < var6) {
                    Block var18 = par1World.getBlock(par3, par4 + var19, par5);
                    if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves) {
                        this.func_150516_a(par1World, par3, par4 + var19, par5, Blocks.log, 2);
                    }
                    ++var19;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

