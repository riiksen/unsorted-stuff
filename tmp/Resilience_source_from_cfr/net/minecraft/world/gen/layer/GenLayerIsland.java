/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerIsland
extends GenLayer {
    private static final String __OBFID = "CL_00000558";

    public GenLayerIsland(long par1) {
        super(par1);
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = IntCache.getIntCache(par3 * par4);
        int var6 = 0;
        while (var6 < par4) {
            int var7 = 0;
            while (var7 < par3) {
                this.initChunkSeed(par1 + var7, par2 + var6);
                var5[var7 + var6 * par3] = this.nextInt(10) == 0 ? 1 : 0;
                ++var7;
            }
            ++var6;
        }
        if (par1 > - par3 && par1 <= 0 && par2 > - par4 && par2 <= 0) {
            var5[- par1 + (- par2) * par3] = 1;
        }
        return var5;
    }
}

