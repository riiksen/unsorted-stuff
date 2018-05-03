/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDesertWells
extends WorldGenerator {
    private static final String __OBFID = "CL_00000407";

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var7;
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2) {
            --par4;
        }
        if (par1World.getBlock(par3, par4, par5) != Blocks.sand) {
            return false;
        }
        int var6 = -2;
        while (var6 <= 2) {
            var7 = -2;
            while (var7 <= 2) {
                if (par1World.isAirBlock(par3 + var6, par4 - 1, par5 + var7) && par1World.isAirBlock(par3 + var6, par4 - 2, par5 + var7)) {
                    return false;
                }
                ++var7;
            }
            ++var6;
        }
        var6 = -1;
        while (var6 <= 0) {
            var7 = -2;
            while (var7 <= 2) {
                int var8 = -2;
                while (var8 <= 2) {
                    par1World.setBlock(par3 + var7, par4 + var6, par5 + var8, Blocks.sandstone, 0, 2);
                    ++var8;
                }
                ++var7;
            }
            ++var6;
        }
        par1World.setBlock(par3, par4, par5, Blocks.flowing_water, 0, 2);
        par1World.setBlock(par3 - 1, par4, par5, Blocks.flowing_water, 0, 2);
        par1World.setBlock(par3 + 1, par4, par5, Blocks.flowing_water, 0, 2);
        par1World.setBlock(par3, par4, par5 - 1, Blocks.flowing_water, 0, 2);
        par1World.setBlock(par3, par4, par5 + 1, Blocks.flowing_water, 0, 2);
        var6 = -2;
        while (var6 <= 2) {
            var7 = -2;
            while (var7 <= 2) {
                if (var6 == -2 || var6 == 2 || var7 == -2 || var7 == 2) {
                    par1World.setBlock(par3 + var6, par4 + 1, par5 + var7, Blocks.sandstone, 0, 2);
                }
                ++var7;
            }
            ++var6;
        }
        par1World.setBlock(par3 + 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
        par1World.setBlock(par3 - 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
        par1World.setBlock(par3, par4 + 1, par5 + 2, Blocks.stone_slab, 1, 2);
        par1World.setBlock(par3, par4 + 1, par5 - 2, Blocks.stone_slab, 1, 2);
        var6 = -1;
        while (var6 <= 1) {
            var7 = -1;
            while (var7 <= 1) {
                if (var6 == 0 && var7 == 0) {
                    par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.sandstone, 0, 2);
                } else {
                    par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.stone_slab, 1, 2);
                }
                ++var7;
            }
            ++var6;
        }
        var6 = 1;
        while (var6 <= 3) {
            par1World.setBlock(par3 - 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
            par1World.setBlock(par3 - 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
            par1World.setBlock(par3 + 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
            par1World.setBlock(par3 + 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
            ++var6;
        }
        return true;
    }
}

