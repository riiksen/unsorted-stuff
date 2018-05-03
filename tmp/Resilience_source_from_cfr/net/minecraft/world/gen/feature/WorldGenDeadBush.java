/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDeadBush
extends WorldGenerator {
    private Block field_150547_a;
    private static final String __OBFID = "CL_00000406";

    public WorldGenDeadBush(Block p_i45451_1_) {
        this.field_150547_a = p_i45451_1_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        Block var6;
        while (((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air || var6.getMaterial() == Material.leaves) && par4 > 0) {
            --par4;
        }
        int var7 = 0;
        while (var7 < 4) {
            int var9;
            int var10;
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var8, var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4), var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8)) && this.field_150547_a.canBlockStay(par1World, var8, var9, var10)) {
                par1World.setBlock(var8, var9, var10, this.field_150547_a, 0, 2);
            }
            ++var7;
        }
        return true;
    }
}

