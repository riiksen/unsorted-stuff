/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatLayerInfo;

public class FlatGeneratorInfo {
    private final List flatLayers = new ArrayList();
    private final Map worldFeatures = new HashMap();
    private int biomeToUse;
    private static final String __OBFID = "CL_00000440";

    public int getBiome() {
        return this.biomeToUse;
    }

    public void setBiome(int par1) {
        this.biomeToUse = par1;
    }

    public Map getWorldFeatures() {
        return this.worldFeatures;
    }

    public List getFlatLayers() {
        return this.flatLayers;
    }

    public void func_82645_d() {
        int var1 = 0;
        for (FlatLayerInfo var3 : this.flatLayers) {
            var3.setMinY(var1);
            var1 += var3.getLayerCount();
        }
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder();
        var1.append(2);
        var1.append(";");
        int var2 = 0;
        while (var2 < this.flatLayers.size()) {
            if (var2 > 0) {
                var1.append(",");
            }
            var1.append(((FlatLayerInfo)this.flatLayers.get(var2)).toString());
            ++var2;
        }
        var1.append(";");
        var1.append(this.biomeToUse);
        if (!this.worldFeatures.isEmpty()) {
            var1.append(";");
            var2 = 0;
            for (Map.Entry var4 : this.worldFeatures.entrySet()) {
                if (var2++ > 0) {
                    var1.append(",");
                }
                var1.append(((String)var4.getKey()).toLowerCase());
                Map var5 = (Map)var4.getValue();
                if (var5.isEmpty()) continue;
                var1.append("(");
                int var6 = 0;
                for (Map.Entry var8 : var5.entrySet()) {
                    if (var6++ > 0) {
                        var1.append(" ");
                    }
                    var1.append((String)var8.getKey());
                    var1.append("=");
                    var1.append((String)var8.getValue());
                }
                var1.append(")");
            }
        } else {
            var1.append(";");
        }
        return var1.toString();
    }

    private static FlatLayerInfo func_82646_a(String par0Str, int par1) {
        int var4;
        String[] var2 = par0Str.split("x", 2);
        int var3 = 1;
        int var5 = 0;
        if (var2.length == 2) {
            try {
                var3 = Integer.parseInt(var2[0]);
                if (par1 + var3 >= 256) {
                    var3 = 256 - par1;
                }
                if (var3 < 0) {
                    var3 = 0;
                }
            }
            catch (Throwable var7) {
                return null;
            }
        }
        try {
            String var6 = var2[var2.length - 1];
            var2 = var6.split(":", 2);
            var4 = Integer.parseInt(var2[0]);
            if (var2.length > 1) {
                var5 = Integer.parseInt(var2[1]);
            }
            if (Block.getBlockById(var4) == Blocks.air) {
                var4 = 0;
                var5 = 0;
            }
            if (var5 < 0 || var5 > 15) {
                var5 = 0;
            }
        }
        catch (Throwable var8) {
            return null;
        }
        FlatLayerInfo var9 = new FlatLayerInfo(var3, Block.getBlockById(var4), var5);
        var9.setMinY(par1);
        return var9;
    }

    private static List func_82652_b(String par0Str) {
        if (par0Str != null && par0Str.length() >= 1) {
            ArrayList<FlatLayerInfo> var1 = new ArrayList<FlatLayerInfo>();
            String[] var2 = par0Str.split(",");
            int var3 = 0;
            String[] var4 = var2;
            int var5 = var2.length;
            int var6 = 0;
            while (var6 < var5) {
                String var7 = var4[var6];
                FlatLayerInfo var8 = FlatGeneratorInfo.func_82646_a(var7, var3);
                if (var8 == null) {
                    return null;
                }
                var1.add(var8);
                var3 += var8.getLayerCount();
                ++var6;
            }
            return var1;
        }
        return null;
    }

    public static FlatGeneratorInfo createFlatGeneratorFromString(String par0Str) {
        int var2;
        if (par0Str == null) {
            return FlatGeneratorInfo.getDefaultFlatGenerator();
        }
        String[] var1 = par0Str.split(";", -1);
        int n = var2 = var1.length == 1 ? 0 : MathHelper.parseIntWithDefault(var1[0], 0);
        if (var2 >= 0 && var2 <= 2) {
            List var5;
            FlatGeneratorInfo var3 = new FlatGeneratorInfo();
            int var4 = var1.length == 1 ? 0 : 1;
            if ((var5 = FlatGeneratorInfo.func_82652_b(var1[var4++])) != null && !var5.isEmpty()) {
                var3.getFlatLayers().addAll(var5);
                var3.func_82645_d();
                int var6 = BiomeGenBase.plains.biomeID;
                if (var2 > 0 && var1.length > var4) {
                    var6 = MathHelper.parseIntWithDefault(var1[var4++], var6);
                }
                var3.setBiome(var6);
                if (var2 > 0 && var1.length > var4) {
                    String[] var7;
                    String[] var8 = var7 = var1[var4++].toLowerCase().split(",");
                    int var9 = var7.length;
                    int var10 = 0;
                    while (var10 < var9) {
                        String var11 = var8[var10];
                        String[] var12 = var11.split("\\(", 2);
                        HashMap<String, String> var13 = new HashMap<String, String>();
                        if (var12[0].length() > 0) {
                            var3.getWorldFeatures().put(var12[0], var13);
                            if (var12.length > 1 && var12[1].endsWith(")") && var12[1].length() > 1) {
                                String[] var14 = var12[1].substring(0, var12[1].length() - 1).split(" ");
                                int var15 = 0;
                                while (var15 < var14.length) {
                                    String[] var16 = var14[var15].split("=", 2);
                                    if (var16.length == 2) {
                                        var13.put(var16[0], var16[1]);
                                    }
                                    ++var15;
                                }
                            }
                        }
                        ++var10;
                    }
                } else {
                    var3.getWorldFeatures().put("village", new HashMap());
                }
                return var3;
            }
            return FlatGeneratorInfo.getDefaultFlatGenerator();
        }
        return FlatGeneratorInfo.getDefaultFlatGenerator();
    }

    public static FlatGeneratorInfo getDefaultFlatGenerator() {
        FlatGeneratorInfo var0 = new FlatGeneratorInfo();
        var0.setBiome(BiomeGenBase.plains.biomeID);
        var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
        var0.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
        var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
        var0.func_82645_d();
        var0.getWorldFeatures().put("village", new HashMap());
        return var0;
    }
}

