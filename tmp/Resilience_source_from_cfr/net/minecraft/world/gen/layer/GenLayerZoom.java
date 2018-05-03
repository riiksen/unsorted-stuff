/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerZoom
extends GenLayer {
    private static final String __OBFID = "CL_00000572";

    public GenLayerZoom(long par1, GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int var14;
        int var5 = par1 >> 1;
        int var6 = par2 >> 1;
        int var7 = (par3 >> 1) + 2;
        int var8 = (par4 >> 1) + 2;
        int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        int var10 = var7 - 1 << 1;
        int var11 = var8 - 1 << 1;
        int[] var12 = IntCache.getIntCache(var10 * var11);
        int var13 = 0;
        while (var13 < var8 - 1) {
            var14 = (var13 << 1) * var10;
            int var15 = 0;
            int var16 = var9[var15 + 0 + (var13 + 0) * var7];
            int var17 = var9[var15 + 0 + (var13 + 1) * var7];
            while (var15 < var7 - 1) {
                this.initChunkSeed(var15 + var5 << 1, var13 + var6 << 1);
                int var18 = var9[var15 + 1 + (var13 + 0) * var7];
                int var19 = var9[var15 + 1 + (var13 + 1) * var7];
                var12[var14] = var16;
                var12[var14++ + var10] = this.func_151619_a(var16, var17);
                var12[var14] = this.func_151619_a(var16, var18);
                var12[var14++ + var10] = this.func_151617_b(var16, var18, var17, var19);
                var16 = var18;
                var17 = var19;
                ++var15;
            }
            ++var13;
        }
        int[] var20 = IntCache.getIntCache(par3 * par4);
        var14 = 0;
        while (var14 < par4) {
            System.arraycopy(var12, (var14 + (par2 & 1)) * var10 + (par1 & 1), var20, var14 * par3, par3);
            ++var14;
        }
        return var20;
    }

    public static GenLayer magnify(long par0, GenLayer par2GenLayer, int par3) {
        GenLayer var4 = par2GenLayer;
        int var5 = 0;
        while (var5 < par3) {
            var4 = new GenLayerZoom(par0 + (long)var5, var4);
            ++var5;
        }
        return var4;
    }
}

