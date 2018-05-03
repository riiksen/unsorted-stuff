/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiome
extends GenLayer {
    private BiomeGenBase[] field_151623_c = new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.field_150588_X, BiomeGenBase.field_150588_X, BiomeGenBase.plains};
    private BiomeGenBase[] field_151621_d = new BiomeGenBase[]{BiomeGenBase.forest, BiomeGenBase.field_150585_R, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.field_150583_P, BiomeGenBase.swampland};
    private BiomeGenBase[] field_151622_e = new BiomeGenBase[]{BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains};
    private BiomeGenBase[] field_151620_f = new BiomeGenBase[]{BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.field_150584_S};
    private static final String __OBFID = "CL_00000555";

    public GenLayerBiome(long par1, GenLayer par3GenLayer, WorldType par4WorldType) {
        super(par1);
        this.parent = par3GenLayer;
        if (par4WorldType == WorldType.DEFAULT_1_1) {
            this.field_151623_c = new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga};
        }
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);
        int var7 = 0;
        while (var7 < par4) {
            int var8 = 0;
            while (var8 < par3) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                int var9 = var5[var8 + var7 * par3];
                int var10 = (var9 & 3840) >> 8;
                var6[var8 + var7 * par3] = GenLayerBiome.func_151618_b(var9 &= -3841) ? var9 : (var9 == BiomeGenBase.mushroomIsland.biomeID ? var9 : (var9 == 1 ? (var10 > 0 ? (this.nextInt(3) == 0 ? BiomeGenBase.field_150608_ab.biomeID : BiomeGenBase.field_150607_aa.biomeID) : this.field_151623_c[this.nextInt((int)this.field_151623_c.length)].biomeID) : (var9 == 2 ? (var10 > 0 ? BiomeGenBase.jungle.biomeID : this.field_151621_d[this.nextInt((int)this.field_151621_d.length)].biomeID) : (var9 == 3 ? (var10 > 0 ? BiomeGenBase.field_150578_U.biomeID : this.field_151622_e[this.nextInt((int)this.field_151622_e.length)].biomeID) : (var9 == 4 ? this.field_151620_f[this.nextInt((int)this.field_151620_f.length)].biomeID : BiomeGenBase.mushroomIsland.biomeID)))));
                ++var8;
            }
            ++var7;
        }
        return var6;
    }
}

