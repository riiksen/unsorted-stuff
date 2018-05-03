/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDeepOcean
extends GenLayer {
    private static final String __OBFID = "CL_00000546";

    public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
        super(p_i45472_1_);
        this.parent = p_i45472_3_;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int var5 = par1 - 1;
        int var6 = par2 - 1;
        int var7 = par3 + 2;
        int var8 = par4 + 2;
        int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        int[] var10 = IntCache.getIntCache(par3 * par4);
        int var11 = 0;
        while (var11 < par4) {
            int var12 = 0;
            while (var12 < par3) {
                int var13 = var9[var12 + 1 + (var11 + 1 - 1) * (par3 + 2)];
                int var14 = var9[var12 + 1 + 1 + (var11 + 1) * (par3 + 2)];
                int var15 = var9[var12 + 1 - 1 + (var11 + 1) * (par3 + 2)];
                int var16 = var9[var12 + 1 + (var11 + 1 + 1) * (par3 + 2)];
                int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                int var18 = 0;
                if (var13 == 0) {
                    ++var18;
                }
                if (var14 == 0) {
                    ++var18;
                }
                if (var15 == 0) {
                    ++var18;
                }
                if (var16 == 0) {
                    ++var18;
                }
                var10[var12 + var11 * par3] = var17 == 0 && var18 > 3 ? BiomeGenBase.field_150575_M.biomeID : var17;
                ++var12;
            }
            ++var11;
        }
        return var10;
    }
}

