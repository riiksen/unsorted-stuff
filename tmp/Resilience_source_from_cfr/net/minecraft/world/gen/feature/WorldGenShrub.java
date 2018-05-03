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
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenShrub
extends WorldGenTrees {
    private int field_150528_a;
    private int field_150527_b;
    private static final String __OBFID = "CL_00000411";

    public WorldGenShrub(int par1, int par2) {
        super(false);
        this.field_150527_b = par1;
        this.field_150528_a = par2;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        Block var6;
        while (((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air || var6.getMaterial() == Material.leaves) && par4 > 0) {
            --par4;
        }
        Block var7 = par1World.getBlock(par3, par4, par5);
        if (var7 == Blocks.dirt || var7 == Blocks.grass) {
            this.func_150516_a(par1World, par3, ++par4, par5, Blocks.log, this.field_150527_b);
            int var8 = par4;
            while (var8 <= par4 + 2) {
                int var9 = var8 - par4;
                int var10 = 2 - var9;
                int var11 = par3 - var10;
                while (var11 <= par3 + var10) {
                    int var12 = var11 - par3;
                    int var13 = par5 - var10;
                    while (var13 <= par5 + var10) {
                        int var14 = var13 - par5;
                        if (!(Math.abs(var12) == var10 && Math.abs(var14) == var10 && par2Random.nextInt(2) == 0 || par1World.getBlock(var11, var8, var13).func_149730_j())) {
                            this.func_150516_a(par1World, var11, var8, var13, Blocks.leaves, this.field_150528_a);
                        }
                        ++var13;
                    }
                    ++var11;
                }
                ++var8;
            }
        }
        return true;
    }
}

