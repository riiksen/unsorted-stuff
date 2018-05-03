/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFire
extends WorldGenerator {
    private static final String __OBFID = "CL_00000412";

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = 0;
        while (var6 < 64) {
            int var9;
            int var8;
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var7, var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4), var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8)) && par1World.getBlock(var7, var8 - 1, var9) == Blocks.netherrack) {
                par1World.setBlock(var7, var8, var9, Blocks.fire, 0, 2);
            }
            ++var6;
        }
        return true;
    }
}

