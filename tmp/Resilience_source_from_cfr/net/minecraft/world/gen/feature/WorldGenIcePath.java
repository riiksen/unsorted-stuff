/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIcePath
extends WorldGenerator {
    private Block field_150555_a = Blocks.packed_ice;
    private int field_150554_b;
    private static final String __OBFID = "CL_00000416";

    public WorldGenIcePath(int p_i45454_1_) {
        this.field_150554_b = p_i45454_1_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2) {
            --par4;
        }
        if (par1World.getBlock(par3, par4, par5) != Blocks.snow) {
            return false;
        }
        int var6 = par2Random.nextInt(this.field_150554_b - 2) + 2;
        int var7 = 1;
        int var8 = par3 - var6;
        while (var8 <= par3 + var6) {
            int var9 = par5 - var6;
            while (var9 <= par5 + var6) {
                int var10 = var8 - par3;
                int var11 = var9 - par5;
                if (var10 * var10 + var11 * var11 <= var6 * var6) {
                    int var12 = par4 - var7;
                    while (var12 <= par4 + var7) {
                        Block var13 = par1World.getBlock(var8, var12, var9);
                        if (var13 == Blocks.dirt || var13 == Blocks.snow || var13 == Blocks.ice) {
                            par1World.setBlock(var8, var12, var9, this.field_150555_a, 0, 2);
                        }
                        ++var12;
                    }
                }
                ++var9;
            }
            ++var8;
        }
        return true;
    }
}

