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
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLakes
extends WorldGenerator {
    private Block field_150556_a;
    private static final String __OBFID = "CL_00000418";

    public WorldGenLakes(Block p_i45455_1_) {
        this.field_150556_a = p_i45455_1_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        boolean var33;
        int var10;
        int var32;
        while (par4 > 5 && par1World.isAirBlock(par3 -= 8, par4, par5 -= 8)) {
            --par4;
        }
        if (par4 <= 4) {
            return false;
        }
        par4 -= 4;
        boolean[] var6 = new boolean[2048];
        int var7 = par2Random.nextInt(4) + 4;
        int var8 = 0;
        while (var8 < var7) {
            double var9 = par2Random.nextDouble() * 6.0 + 3.0;
            double var11 = par2Random.nextDouble() * 4.0 + 2.0;
            double var13 = par2Random.nextDouble() * 6.0 + 3.0;
            double var15 = par2Random.nextDouble() * (16.0 - var9 - 2.0) + 1.0 + var9 / 2.0;
            double var17 = par2Random.nextDouble() * (8.0 - var11 - 4.0) + 2.0 + var11 / 2.0;
            double var19 = par2Random.nextDouble() * (16.0 - var13 - 2.0) + 1.0 + var13 / 2.0;
            int var21 = 1;
            while (var21 < 15) {
                int var22 = 1;
                while (var22 < 15) {
                    int var23 = 1;
                    while (var23 < 7) {
                        double var24 = ((double)var21 - var15) / (var9 / 2.0);
                        double var26 = ((double)var23 - var17) / (var11 / 2.0);
                        double var28 = ((double)var22 - var19) / (var13 / 2.0);
                        double var30 = var24 * var24 + var26 * var26 + var28 * var28;
                        if (var30 < 1.0) {
                            var6[(var21 * 16 + var22) * 8 + var23] = true;
                        }
                        ++var23;
                    }
                    ++var22;
                }
                ++var21;
            }
            ++var8;
        }
        var8 = 0;
        while (var8 < 16) {
            var32 = 0;
            while (var32 < 16) {
                var10 = 0;
                while (var10 < 8) {
                    boolean bl = var33 = !var6[(var8 * 16 + var32) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var32) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var32) * 8 + var10] || var32 < 15 && var6[(var8 * 16 + var32 + 1) * 8 + var10] || var32 > 0 && var6[(var8 * 16 + (var32 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var32) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var32) * 8 + (var10 - 1)]);
                    if (var33) {
                        Material var12 = par1World.getBlock(par3 + var8, par4 + var10, par5 + var32).getMaterial();
                        if (var10 >= 4 && var12.isLiquid()) {
                            return false;
                        }
                        if (var10 < 4 && !var12.isSolid() && par1World.getBlock(par3 + var8, par4 + var10, par5 + var32) != this.field_150556_a) {
                            return false;
                        }
                    }
                    ++var10;
                }
                ++var32;
            }
            ++var8;
        }
        var8 = 0;
        while (var8 < 16) {
            var32 = 0;
            while (var32 < 16) {
                var10 = 0;
                while (var10 < 8) {
                    if (var6[(var8 * 16 + var32) * 8 + var10]) {
                        par1World.setBlock(par3 + var8, par4 + var10, par5 + var32, var10 >= 4 ? Blocks.air : this.field_150556_a, 0, 2);
                    }
                    ++var10;
                }
                ++var32;
            }
            ++var8;
        }
        var8 = 0;
        while (var8 < 16) {
            var32 = 0;
            while (var32 < 16) {
                var10 = 4;
                while (var10 < 8) {
                    if (var6[(var8 * 16 + var32) * 8 + var10] && par1World.getBlock(par3 + var8, par4 + var10 - 1, par5 + var32) == Blocks.dirt && par1World.getSavedLightValue(EnumSkyBlock.Sky, par3 + var8, par4 + var10, par5 + var32) > 0) {
                        BiomeGenBase var35 = par1World.getBiomeGenForCoords(par3 + var8, par5 + var32);
                        if (var35.topBlock == Blocks.mycelium) {
                            par1World.setBlock(par3 + var8, par4 + var10 - 1, par5 + var32, Blocks.mycelium, 0, 2);
                        } else {
                            par1World.setBlock(par3 + var8, par4 + var10 - 1, par5 + var32, Blocks.grass, 0, 2);
                        }
                    }
                    ++var10;
                }
                ++var32;
            }
            ++var8;
        }
        if (this.field_150556_a.getMaterial() == Material.lava) {
            var8 = 0;
            while (var8 < 16) {
                var32 = 0;
                while (var32 < 16) {
                    var10 = 0;
                    while (var10 < 8) {
                        boolean bl = var33 = !var6[(var8 * 16 + var32) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var32) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var32) * 8 + var10] || var32 < 15 && var6[(var8 * 16 + var32 + 1) * 8 + var10] || var32 > 0 && var6[(var8 * 16 + (var32 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var32) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var32) * 8 + (var10 - 1)]);
                        if (var33 && (var10 < 4 || par2Random.nextInt(2) != 0) && par1World.getBlock(par3 + var8, par4 + var10, par5 + var32).getMaterial().isSolid()) {
                            par1World.setBlock(par3 + var8, par4 + var10, par5 + var32, Blocks.stone, 0, 2);
                        }
                        ++var10;
                    }
                    ++var32;
                }
                ++var8;
            }
        }
        if (this.field_150556_a.getMaterial() == Material.water) {
            var8 = 0;
            while (var8 < 16) {
                var32 = 0;
                while (var32 < 16) {
                    int var34 = 4;
                    if (par1World.isBlockFreezable(par3 + var8, par4 + var34, par5 + var32)) {
                        par1World.setBlock(par3 + var8, par4 + var34, par5 + var32, Blocks.ice, 0, 2);
                    }
                    ++var32;
                }
                ++var8;
            }
        }
        return true;
    }
}

