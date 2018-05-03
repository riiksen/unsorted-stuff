/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBigMushroom
extends WorldGenerator {
    private int mushroomType = -1;
    private static final String __OBFID = "CL_00000415";

    public WorldGenBigMushroom(int par1) {
        super(true);
        this.mushroomType = par1;
    }

    public WorldGenBigMushroom() {
        super(false);
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        block36 : {
            int var12;
            int var11;
            int var6 = par2Random.nextInt(2);
            if (this.mushroomType >= 0) {
                var6 = this.mushroomType;
            }
            int var7 = par2Random.nextInt(3) + 4;
            boolean var8 = true;
            if (par4 < 1 || par4 + var7 + 1 >= 256) break block36;
            int var9 = par4;
            while (var9 <= par4 + 1 + var7) {
                int var10 = 3;
                if (var9 <= par4 + 3) {
                    var10 = 0;
                }
                var11 = par3 - var10;
                while (var11 <= par3 + var10 && var8) {
                    var12 = par5 - var10;
                    while (var12 <= par5 + var10 && var8) {
                        if (var9 >= 0 && var9 < 256) {
                            Block var13 = par1World.getBlock(var11, var9, var12);
                            if (var13.getMaterial() != Material.air && var13.getMaterial() != Material.leaves) {
                                var8 = false;
                            }
                        } else {
                            var8 = false;
                        }
                        ++var12;
                    }
                    ++var11;
                }
                ++var9;
            }
            if (!var8) {
                return false;
            }
            Block var16 = par1World.getBlock(par3, par4 - 1, par5);
            if (var16 != Blocks.dirt && var16 != Blocks.grass && var16 != Blocks.mycelium) {
                return false;
            }
            int var17 = par4 + var7;
            if (var6 == 1) {
                var17 = par4 + var7 - 3;
            }
            var11 = var17;
            while (var11 <= par4 + var7) {
                var12 = 1;
                if (var11 < par4 + var7) {
                    ++var12;
                }
                if (var6 == 0) {
                    var12 = 3;
                }
                int var19 = par3 - var12;
                while (var19 <= par3 + var12) {
                    int var14 = par5 - var12;
                    while (var14 <= par5 + var12) {
                        block38 : {
                            int var15;
                            block37 : {
                                var15 = 5;
                                if (var19 == par3 - var12) {
                                    --var15;
                                }
                                if (var19 == par3 + var12) {
                                    ++var15;
                                }
                                if (var14 == par5 - var12) {
                                    var15 -= 3;
                                }
                                if (var14 == par5 + var12) {
                                    var15 += 3;
                                }
                                if (var6 != 0 && var11 >= par4 + var7) break block37;
                                if ((var19 == par3 - var12 || var19 == par3 + var12) && (var14 == par5 - var12 || var14 == par5 + var12)) break block38;
                                if (var19 == par3 - (var12 - 1) && var14 == par5 - var12) {
                                    var15 = 1;
                                }
                                if (var19 == par3 - var12 && var14 == par5 - (var12 - 1)) {
                                    var15 = 1;
                                }
                                if (var19 == par3 + (var12 - 1) && var14 == par5 - var12) {
                                    var15 = 3;
                                }
                                if (var19 == par3 + var12 && var14 == par5 - (var12 - 1)) {
                                    var15 = 3;
                                }
                                if (var19 == par3 - (var12 - 1) && var14 == par5 + var12) {
                                    var15 = 7;
                                }
                                if (var19 == par3 - var12 && var14 == par5 + (var12 - 1)) {
                                    var15 = 7;
                                }
                                if (var19 == par3 + (var12 - 1) && var14 == par5 + var12) {
                                    var15 = 9;
                                }
                                if (var19 == par3 + var12 && var14 == par5 + (var12 - 1)) {
                                    var15 = 9;
                                }
                            }
                            if (var15 == 5 && var11 < par4 + var7) {
                                var15 = 0;
                            }
                            if (!(var15 == 0 && par4 < par4 + var7 - 1 || par1World.getBlock(var19, var11, var14).func_149730_j())) {
                                this.func_150516_a(par1World, var19, var11, var14, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), var15);
                            }
                        }
                        ++var14;
                    }
                    ++var19;
                }
                ++var11;
            }
            var11 = 0;
            while (var11 < var7) {
                Block var18 = par1World.getBlock(par3, par4 + var11, par5);
                if (!var18.func_149730_j()) {
                    this.func_150516_a(par1World, par3, par4 + var11, par5, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), 10);
                }
                ++var11;
            }
            return true;
        }
        return false;
    }
}

