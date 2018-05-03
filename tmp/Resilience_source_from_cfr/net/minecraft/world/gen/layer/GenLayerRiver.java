/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiver
extends GenLayer {
    private static final String __OBFID = "CL_00000566";

    public GenLayerRiver(long par1, GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
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
                int var13 = this.func_151630_c(var9[var12 + 0 + (var11 + 1) * var7]);
                int var14 = this.func_151630_c(var9[var12 + 2 + (var11 + 1) * var7]);
                int var15 = this.func_151630_c(var9[var12 + 1 + (var11 + 0) * var7]);
                int var16 = this.func_151630_c(var9[var12 + 1 + (var11 + 2) * var7]);
                int var17 = this.func_151630_c(var9[var12 + 1 + (var11 + 1) * var7]);
                var10[var12 + var11 * par3] = var17 == var13 && var17 == var15 && var17 == var14 && var17 == var16 ? -1 : BiomeGenBase.river.biomeID;
                ++var12;
            }
            ++var11;
        }
        return var10;
    }

    private int func_151630_c(int p_151630_1_) {
        return p_151630_1_ >= 2 ? 2 + (p_151630_1_ & 1) : p_151630_1_;
    }
}

