/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDoublePlant
extends WorldGenerator {
    private int field_150549_a;
    private static final String __OBFID = "CL_00000408";

    public void func_150548_a(int p_150548_1_) {
        this.field_150549_a = p_150548_1_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        boolean var6 = false;
        int var7 = 0;
        while (var7 < 64) {
            int var9;
            int var10;
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var8, var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4), var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8)) && (!par1World.provider.hasNoSky || var9 < 254) && Blocks.double_plant.canPlaceBlockAt(par1World, var8, var9, var10)) {
                Blocks.double_plant.func_149889_c(par1World, var8, var9, var10, this.field_150549_a, 2);
                var6 = true;
            }
            ++var7;
        }
        return var6;
    }
}

