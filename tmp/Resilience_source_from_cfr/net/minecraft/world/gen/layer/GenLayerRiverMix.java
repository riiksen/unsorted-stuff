/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiverMix
extends GenLayer {
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
    private static final String __OBFID = "CL_00000567";

    public GenLayerRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer) {
        super(par1);
        this.biomePatternGeneratorChain = par3GenLayer;
        this.riverPatternGeneratorChain = par4GenLayer;
    }

    @Override
    public void initWorldGenSeed(long par1) {
        this.biomePatternGeneratorChain.initWorldGenSeed(par1);
        this.riverPatternGeneratorChain.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] var6 = this.riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] var7 = IntCache.getIntCache(par3 * par4);
        int var8 = 0;
        while (var8 < par3 * par4) {
            var7[var8] = var5[var8] != BiomeGenBase.ocean.biomeID && var5[var8] != BiomeGenBase.field_150575_M.biomeID ? (var6[var8] == BiomeGenBase.river.biomeID ? (var5[var8] == BiomeGenBase.icePlains.biomeID ? BiomeGenBase.frozenRiver.biomeID : (var5[var8] != BiomeGenBase.mushroomIsland.biomeID && var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID ? var6[var8] & 255 : BiomeGenBase.mushroomIslandShore.biomeID)) : var5[var8]) : var5[var8];
            ++var8;
        }
        return var7;
    }
}

