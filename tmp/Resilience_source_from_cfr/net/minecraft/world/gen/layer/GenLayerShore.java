/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerShore
extends GenLayer {
    private static final String __OBFID = "CL_00000568";

    public GenLayerShore(long par1, GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = IntCache.getIntCache(par3 * par4);
        int var7 = 0;
        while (var7 < par4) {
            int var8 = 0;
            while (var8 < par3) {
                int var11;
                int var13;
                int var14;
                int var12;
                this.initChunkSeed(var8 + par1, var7 + par2);
                int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
                BiomeGenBase var10 = BiomeGenBase.func_150568_d(var9);
                if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
                    var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    var6[var8 + var7 * par3] = var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID && var14 != BiomeGenBase.ocean.biomeID ? var9 : BiomeGenBase.mushroomIslandShore.biomeID;
                } else if (var10 != null && var10.func_150562_l() == BiomeGenJungle.class) {
                    var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    var6[var8 + var7 * par3] = this.func_151631_c(var11) && this.func_151631_c(var12) && this.func_151631_c(var13) && this.func_151631_c(var14) ? (!(GenLayerShore.func_151618_b(var11) || GenLayerShore.func_151618_b(var12) || GenLayerShore.func_151618_b(var13) || GenLayerShore.func_151618_b(var14)) ? var9 : BiomeGenBase.beach.biomeID) : BiomeGenBase.field_150574_L.biomeID;
                } else if (var9 != BiomeGenBase.extremeHills.biomeID && var9 != BiomeGenBase.field_150580_W.biomeID && var9 != BiomeGenBase.extremeHillsEdge.biomeID) {
                    if (var10 != null && var10.func_150559_j()) {
                        this.func_151632_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150577_O.biomeID);
                    } else if (var9 != BiomeGenBase.field_150589_Z.biomeID && var9 != BiomeGenBase.field_150607_aa.biomeID) {
                        if (var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.field_150575_M.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID) {
                            var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                            var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                            var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                            var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                            var6[var8 + var7 * par3] = !(GenLayerShore.func_151618_b(var11) || GenLayerShore.func_151618_b(var12) || GenLayerShore.func_151618_b(var13) || GenLayerShore.func_151618_b(var14)) ? var9 : BiomeGenBase.beach.biomeID;
                        } else {
                            var6[var8 + var7 * par3] = var9;
                        }
                    } else {
                        var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                        var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                        var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                        var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                        var6[var8 + var7 * par3] = !(GenLayerShore.func_151618_b(var11) || GenLayerShore.func_151618_b(var12) || GenLayerShore.func_151618_b(var13) || GenLayerShore.func_151618_b(var14)) ? (this.func_151633_d(var11) && this.func_151633_d(var12) && this.func_151633_d(var13) && this.func_151633_d(var14) ? var9 : BiomeGenBase.desert.biomeID) : var9;
                    }
                } else {
                    this.func_151632_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150576_N.biomeID);
                }
                ++var8;
            }
            ++var7;
        }
        return var6;
    }

    private void func_151632_a(int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_) {
        if (GenLayerShore.func_151618_b(p_151632_6_)) {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
        } else {
            int var8 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
            int var9 = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int var10 = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int var11 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = !GenLayerShore.func_151618_b(var8) && !GenLayerShore.func_151618_b(var9) && !GenLayerShore.func_151618_b(var10) && !GenLayerShore.func_151618_b(var11) ? p_151632_6_ : p_151632_7_;
        }
    }

    private boolean func_151631_c(int p_151631_1_) {
        return BiomeGenBase.func_150568_d(p_151631_1_) != null && BiomeGenBase.func_150568_d(p_151631_1_).func_150562_l() == BiomeGenJungle.class ? true : p_151631_1_ == BiomeGenBase.field_150574_L.biomeID || p_151631_1_ == BiomeGenBase.jungle.biomeID || p_151631_1_ == BiomeGenBase.jungleHills.biomeID || p_151631_1_ == BiomeGenBase.forest.biomeID || p_151631_1_ == BiomeGenBase.taiga.biomeID || GenLayerShore.func_151618_b(p_151631_1_);
    }

    private boolean func_151633_d(int p_151633_1_) {
        if (BiomeGenBase.func_150568_d(p_151633_1_) != null && BiomeGenBase.func_150568_d(p_151633_1_) instanceof BiomeGenMesa) {
            return true;
        }
        return false;
    }
}

