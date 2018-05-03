/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSpikes
extends WorldGenerator {
    private Block field_150520_a;
    private static final String __OBFID = "CL_00000433";

    public WorldGenSpikes(Block p_i45464_1_) {
        this.field_150520_a = p_i45464_1_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        if (par1World.isAirBlock(par3, par4, par5) && par1World.getBlock(par3, par4 - 1, par5) == this.field_150520_a) {
            int var9;
            int var11;
            int var10;
            int var6 = par2Random.nextInt(32) + 6;
            int var7 = par2Random.nextInt(4) + 1;
            int var8 = par3 - var7;
            while (var8 <= par3 + var7) {
                var9 = par5 - var7;
                while (var9 <= par5 + var7) {
                    var10 = var8 - par3;
                    var11 = var9 - par5;
                    if (var10 * var10 + var11 * var11 <= var7 * var7 + 1 && par1World.getBlock(var8, par4 - 1, var9) != this.field_150520_a) {
                        return false;
                    }
                    ++var9;
                }
                ++var8;
            }
            var8 = par4;
            while (var8 < par4 + var6 && var8 < 256) {
                var9 = par3 - var7;
                while (var9 <= par3 + var7) {
                    var10 = par5 - var7;
                    while (var10 <= par5 + var7) {
                        var11 = var9 - par3;
                        int var12 = var10 - par5;
                        if (var11 * var11 + var12 * var12 <= var7 * var7 + 1) {
                            par1World.setBlock(var9, var8, var10, Blocks.obsidian, 0, 2);
                        }
                        ++var10;
                    }
                    ++var9;
                }
                ++var8;
            }
            EntityEnderCrystal var13 = new EntityEnderCrystal(par1World);
            var13.setLocationAndAngles((float)par3 + 0.5f, par4 + var6, (float)par5 + 0.5f, par2Random.nextFloat() * 360.0f, 0.0f);
            par1World.spawnEntityInWorld(var13);
            par1World.setBlock(par3, par4 + var6, par5, Blocks.bedrock, 0, 2);
            return true;
        }
        return false;
    }
}

