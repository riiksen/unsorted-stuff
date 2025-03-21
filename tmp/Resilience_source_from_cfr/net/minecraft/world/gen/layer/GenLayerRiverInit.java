/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiverInit
extends GenLayer {
    private static final String __OBFID = "CL_00000565";

    public GenLayerRiverInit(long par1, GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
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
                var6[var8 + var7 * par3] = var5[var8 + var7 * par3] > 0 ? this.nextInt(299999) + 2 : 0;
                ++var8;
            }
            ++var7;
        }
        return var6;
    }
}

