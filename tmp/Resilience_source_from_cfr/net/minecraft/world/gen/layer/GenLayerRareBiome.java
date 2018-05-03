/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRareBiome
extends GenLayer {
    private static final String __OBFID = "CL_00000562";

    public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
        super(p_i45478_1_);
        this.parent = p_i45478_3_;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = IntCache.getIntCache(par3 * par4);
        int var7 = 0;
        while (var7 < par4) {
            int var8 = 0;
            while (var8 < par3) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
                var6[var8 + var7 * par3] = this.nextInt(57) == 0 ? (var9 == BiomeGenBase.plains.biomeID ? BiomeGenBase.plains.biomeID + 128 : var9) : var9;
                ++var8;
            }
            ++var7;
        }
        return var6;
    }
}

