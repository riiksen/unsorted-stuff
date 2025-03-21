/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerHills
extends GenLayer {
    private static final Logger logger = LogManager.getLogger();
    private GenLayer field_151628_d;
    private static final String __OBFID = "CL_00000563";

    public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_) {
        super(p_i45479_1_);
        this.parent = p_i45479_3_;
        this.field_151628_d = p_i45479_4_;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = this.field_151628_d.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var7 = IntCache.getIntCache(par3 * par4);
        int var8 = 0;
        while (var8 < par4) {
            int var9 = 0;
            while (var9 < par3) {
                boolean var12;
                this.initChunkSeed(var9 + par1, var8 + par2);
                int var10 = var5[var9 + 1 + (var8 + 1) * (par3 + 2)];
                int var11 = var6[var9 + 1 + (var8 + 1) * (par3 + 2)];
                boolean bl = var12 = (var11 - 2) % 29 == 0;
                if (var10 > 255) {
                    logger.debug("old! " + var10);
                }
                if (var10 != 0 && var11 >= 2 && (var11 - 2) % 29 == 1 && var10 < 128) {
                    var7[var9 + var8 * par3] = BiomeGenBase.func_150568_d(var10 + 128) != null ? var10 + 128 : var10;
                } else if (this.nextInt(3) != 0 && !var12) {
                    var7[var9 + var8 * par3] = var10;
                } else {
                    int var14;
                    int var13 = var10;
                    if (var10 == BiomeGenBase.desert.biomeID) {
                        var13 = BiomeGenBase.desertHills.biomeID;
                    } else if (var10 == BiomeGenBase.forest.biomeID) {
                        var13 = BiomeGenBase.forestHills.biomeID;
                    } else if (var10 == BiomeGenBase.field_150583_P.biomeID) {
                        var13 = BiomeGenBase.field_150582_Q.biomeID;
                    } else if (var10 == BiomeGenBase.field_150585_R.biomeID) {
                        var13 = BiomeGenBase.plains.biomeID;
                    } else if (var10 == BiomeGenBase.taiga.biomeID) {
                        var13 = BiomeGenBase.taigaHills.biomeID;
                    } else if (var10 == BiomeGenBase.field_150578_U.biomeID) {
                        var13 = BiomeGenBase.field_150581_V.biomeID;
                    } else if (var10 == BiomeGenBase.field_150584_S.biomeID) {
                        var13 = BiomeGenBase.field_150579_T.biomeID;
                    } else if (var10 == BiomeGenBase.plains.biomeID) {
                        var13 = this.nextInt(3) == 0 ? BiomeGenBase.forestHills.biomeID : BiomeGenBase.forest.biomeID;
                    } else if (var10 == BiomeGenBase.icePlains.biomeID) {
                        var13 = BiomeGenBase.iceMountains.biomeID;
                    } else if (var10 == BiomeGenBase.jungle.biomeID) {
                        var13 = BiomeGenBase.jungleHills.biomeID;
                    } else if (var10 == BiomeGenBase.ocean.biomeID) {
                        var13 = BiomeGenBase.field_150575_M.biomeID;
                    } else if (var10 == BiomeGenBase.extremeHills.biomeID) {
                        var13 = BiomeGenBase.field_150580_W.biomeID;
                    } else if (var10 == BiomeGenBase.field_150588_X.biomeID) {
                        var13 = BiomeGenBase.field_150587_Y.biomeID;
                    } else if (GenLayerHills.func_151616_a(var10, BiomeGenBase.field_150607_aa.biomeID)) {
                        var13 = BiomeGenBase.field_150589_Z.biomeID;
                    } else if (var10 == BiomeGenBase.field_150575_M.biomeID && this.nextInt(3) == 0) {
                        var14 = this.nextInt(2);
                        var13 = var14 == 0 ? BiomeGenBase.plains.biomeID : BiomeGenBase.forest.biomeID;
                    }
                    if (var12 && var13 != var10) {
                        var13 = BiomeGenBase.func_150568_d(var13 + 128) != null ? (var13 += 128) : var10;
                    }
                    if (var13 == var10) {
                        var7[var9 + var8 * par3] = var10;
                    } else {
                        var14 = var5[var9 + 1 + (var8 + 1 - 1) * (par3 + 2)];
                        int var15 = var5[var9 + 1 + 1 + (var8 + 1) * (par3 + 2)];
                        int var16 = var5[var9 + 1 - 1 + (var8 + 1) * (par3 + 2)];
                        int var17 = var5[var9 + 1 + (var8 + 1 + 1) * (par3 + 2)];
                        int var18 = 0;
                        if (GenLayerHills.func_151616_a(var14, var10)) {
                            ++var18;
                        }
                        if (GenLayerHills.func_151616_a(var15, var10)) {
                            ++var18;
                        }
                        if (GenLayerHills.func_151616_a(var16, var10)) {
                            ++var18;
                        }
                        if (GenLayerHills.func_151616_a(var17, var10)) {
                            ++var18;
                        }
                        var7[var9 + var8 * par3] = var18 >= 3 ? var13 : var10;
                    }
                }
                ++var9;
            }
            ++var8;
        }
        return var7;
    }
}

