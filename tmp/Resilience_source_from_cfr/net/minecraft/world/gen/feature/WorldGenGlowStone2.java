/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlowStone2
extends WorldGenerator {
    private static final String __OBFID = "CL_00000413";

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        if (!par1World.isAirBlock(par3, par4, par5)) {
            return false;
        }
        if (par1World.getBlock(par3, par4 + 1, par5) != Blocks.netherrack) {
            return false;
        }
        par1World.setBlock(par3, par4, par5, Blocks.glowstone, 0, 2);
        int var6 = 0;
        while (var6 < 1500) {
            int var9;
            int var8;
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.getBlock(var7, var8 = par4 - par2Random.nextInt(12), var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8)).getMaterial() == Material.air) {
                int var10 = 0;
                int var11 = 0;
                while (var11 < 6) {
                    Block var12 = null;
                    if (var11 == 0) {
                        var12 = par1World.getBlock(var7 - 1, var8, var9);
                    }
                    if (var11 == 1) {
                        var12 = par1World.getBlock(var7 + 1, var8, var9);
                    }
                    if (var11 == 2) {
                        var12 = par1World.getBlock(var7, var8 - 1, var9);
                    }
                    if (var11 == 3) {
                        var12 = par1World.getBlock(var7, var8 + 1, var9);
                    }
                    if (var11 == 4) {
                        var12 = par1World.getBlock(var7, var8, var9 - 1);
                    }
                    if (var11 == 5) {
                        var12 = par1World.getBlock(var7, var8, var9 + 1);
                    }
                    if (var12 == Blocks.glowstone) {
                        ++var10;
                    }
                    ++var11;
                }
                if (var10 == 1) {
                    par1World.setBlock(var7, var8, var9, Blocks.glowstone, 0, 2);
                }
            }
            ++var6;
        }
        return true;
    }
}

