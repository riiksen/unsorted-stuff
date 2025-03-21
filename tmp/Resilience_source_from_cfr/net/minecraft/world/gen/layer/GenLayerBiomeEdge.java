/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeEdge
extends GenLayer {
    private static final String __OBFID = "CL_00000554";

    public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_) {
        super(p_i45475_1_);
        this.parent = p_i45475_3_;
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
                if (!(this.func_151636_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) || this.func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150607_aa.biomeID, BiomeGenBase.field_150589_Z.biomeID) || this.func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150608_ab.biomeID, BiomeGenBase.field_150589_Z.biomeID) || this.func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150578_U.biomeID, BiomeGenBase.taiga.biomeID))) {
                    int var11;
                    int var10;
                    int var13;
                    int var12;
                    if (var9 == BiomeGenBase.desert.biomeID) {
                        var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                        var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                        var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                        var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                        var6[var8 + var7 * par3] = var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID ? var9 : BiomeGenBase.field_150580_W.biomeID;
                    } else if (var9 == BiomeGenBase.swampland.biomeID) {
                        var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                        var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                        var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                        var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                        var6[var8 + var7 * par3] = var10 != BiomeGenBase.desert.biomeID && var11 != BiomeGenBase.desert.biomeID && var12 != BiomeGenBase.desert.biomeID && var13 != BiomeGenBase.desert.biomeID && var10 != BiomeGenBase.field_150584_S.biomeID && var11 != BiomeGenBase.field_150584_S.biomeID && var12 != BiomeGenBase.field_150584_S.biomeID && var13 != BiomeGenBase.field_150584_S.biomeID && var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID ? (var10 != BiomeGenBase.jungle.biomeID && var13 != BiomeGenBase.jungle.biomeID && var11 != BiomeGenBase.jungle.biomeID && var12 != BiomeGenBase.jungle.biomeID ? var9 : BiomeGenBase.field_150574_L.biomeID) : BiomeGenBase.plains.biomeID;
                    } else {
                        var6[var8 + var7 * par3] = var9;
                    }
                }
                ++var8;
            }
            ++var7;
        }
        return var6;
    }

    private boolean func_151636_a(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_) {
        if (!GenLayerBiomeEdge.func_151616_a(p_151636_6_, p_151636_7_)) {
            return false;
        }
        int var9 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
        int var10 = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        int var11 = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        int var12 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
        p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = this.func_151634_b(var9, p_151636_7_) && this.func_151634_b(var10, p_151636_7_) && this.func_151634_b(var11, p_151636_7_) && this.func_151634_b(var12, p_151636_7_) ? p_151636_6_ : p_151636_8_;
        return true;
    }

    private boolean func_151635_b(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_) {
        if (p_151635_6_ != p_151635_7_) {
            return false;
        }
        int var9 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
        int var10 = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        int var11 = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        int var12 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
        p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = GenLayerBiomeEdge.func_151616_a(var9, p_151635_7_) && GenLayerBiomeEdge.func_151616_a(var10, p_151635_7_) && GenLayerBiomeEdge.func_151616_a(var11, p_151635_7_) && GenLayerBiomeEdge.func_151616_a(var12, p_151635_7_) ? p_151635_6_ : p_151635_8_;
        return true;
    }

    private boolean func_151634_b(int p_151634_1_, int p_151634_2_) {
        if (GenLayerBiomeEdge.func_151616_a(p_151634_1_, p_151634_2_)) {
            return true;
        }
        if (BiomeGenBase.func_150568_d(p_151634_1_) != null && BiomeGenBase.func_150568_d(p_151634_2_) != null) {
            BiomeGenBase.TempCategory var4;
            BiomeGenBase.TempCategory var3 = BiomeGenBase.func_150568_d(p_151634_1_).func_150561_m();
            if (var3 != (var4 = BiomeGenBase.func_150568_d(p_151634_2_).func_150561_m()) && var3 != BiomeGenBase.TempCategory.MEDIUM && var4 != BiomeGenBase.TempCategory.MEDIUM) {
                return false;
            }
            return true;
        }
        return false;
    }
}

