/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIceSpike
extends WorldGenerator {
    private static final String __OBFID = "CL_00000417";

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var10;
        int var11;
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2) {
            --par4;
        }
        if (par1World.getBlock(par3, par4, par5) != Blocks.snow) {
            return false;
        }
        par4 += par2Random.nextInt(4);
        int var6 = par2Random.nextInt(4) + 7;
        int var7 = var6 / 4 + par2Random.nextInt(2);
        if (var7 > 1 && par2Random.nextInt(60) == 0) {
            par4 += 10 + par2Random.nextInt(30);
        }
        int var8 = 0;
        while (var8 < var6) {
            float var9 = (1.0f - (float)var8 / (float)var6) * (float)var7;
            var10 = MathHelper.ceiling_float_int(var9);
            var11 = - var10;
            while (var11 <= var10) {
                float var12 = (float)MathHelper.abs_int(var11) - 0.25f;
                int var13 = - var10;
                while (var13 <= var10) {
                    float var14 = (float)MathHelper.abs_int(var13) - 0.25f;
                    if ((var11 == 0 && var13 == 0 || var12 * var12 + var14 * var14 <= var9 * var9) && (var11 != - var10 && var11 != var10 && var13 != - var10 && var13 != var10 || par2Random.nextFloat() <= 0.75f)) {
                        Block var15 = par1World.getBlock(par3 + var11, par4 + var8, par5 + var13);
                        if (var15.getMaterial() == Material.air || var15 == Blocks.dirt || var15 == Blocks.snow || var15 == Blocks.ice) {
                            this.func_150515_a(par1World, par3 + var11, par4 + var8, par5 + var13, Blocks.packed_ice);
                        }
                        if (var8 != 0 && var10 > 1 && ((var15 = par1World.getBlock(par3 + var11, par4 - var8, par5 + var13)).getMaterial() == Material.air || var15 == Blocks.dirt || var15 == Blocks.snow || var15 == Blocks.ice)) {
                            this.func_150515_a(par1World, par3 + var11, par4 - var8, par5 + var13, Blocks.packed_ice);
                        }
                    }
                    ++var13;
                }
                ++var11;
            }
            ++var8;
        }
        var8 = var7 - 1;
        if (var8 < 0) {
            var8 = 0;
        } else if (var8 > 1) {
            var8 = 1;
        }
        int var16 = - var8;
        while (var16 <= var8) {
            var10 = - var8;
            while (var10 <= var8) {
                Block var18;
                var11 = par4 - 1;
                int var17 = 50;
                if (Math.abs(var16) == 1 && Math.abs(var10) == 1) {
                    var17 = par2Random.nextInt(5);
                }
                while (var11 > 50 && ((var18 = par1World.getBlock(par3 + var16, var11, par5 + var10)).getMaterial() == Material.air || var18 == Blocks.dirt || var18 == Blocks.snow || var18 == Blocks.ice || var18 == Blocks.packed_ice)) {
                    this.func_150515_a(par1World, par3 + var16, var11, par5 + var10, Blocks.packed_ice);
                    --var11;
                    if (--var17 > 0) continue;
                    var11 -= par2Random.nextInt(5) + 1;
                    var17 = par2Random.nextInt(5);
                }
                ++var10;
            }
            ++var16;
        }
        return true;
    }
}

