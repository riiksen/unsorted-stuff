/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayer {
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    protected long baseSeed;
    private static final String __OBFID = "CL_00000559";

    public static GenLayer[] initializeAllBiomeGenerators(long par0, WorldType par2WorldType) {
        boolean var3 = false;
        GenLayerIsland var4 = new GenLayerIsland(1);
        GenLayerFuzzyZoom var11 = new GenLayerFuzzyZoom(2000, var4);
        GenLayerAddIsland var12 = new GenLayerAddIsland(1, var11);
        GenLayerZoom var13 = new GenLayerZoom(2001, var12);
        var12 = new GenLayerAddIsland(2, var13);
        var12 = new GenLayerAddIsland(50, var12);
        var12 = new GenLayerAddIsland(70, var12);
        GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2, var12);
        GenLayerAddSnow var14 = new GenLayerAddSnow(2, var16);
        var12 = new GenLayerAddIsland(3, var14);
        GenLayerEdge var19 = new GenLayerEdge(2, var12, GenLayerEdge.Mode.COOL_WARM);
        var19 = new GenLayerEdge(2, var19, GenLayerEdge.Mode.HEAT_ICE);
        var19 = new GenLayerEdge(3, var19, GenLayerEdge.Mode.SPECIAL);
        var13 = new GenLayerZoom(2002, var19);
        var13 = new GenLayerZoom(2003, var13);
        var12 = new GenLayerAddIsland(4, var13);
        GenLayerAddMushroomIsland var22 = new GenLayerAddMushroomIsland(5, var12);
        GenLayerDeepOcean var24 = new GenLayerDeepOcean(4, var22);
        GenLayer var23 = GenLayerZoom.magnify(1000, var24, 0);
        int var5 = 4;
        if (par2WorldType == WorldType.LARGE_BIOMES) {
            var5 = 6;
        }
        if (var3) {
            var5 = 4;
        }
        GenLayer var6 = GenLayerZoom.magnify(1000, var23, 0);
        GenLayerRiverInit var15 = new GenLayerRiverInit(100, var6);
        GenLayer var7 = new GenLayerBiome(200, var23, par2WorldType);
        if (!var3) {
            GenLayer var18 = GenLayerZoom.magnify(1000, var7, 2);
            var7 = new GenLayerBiomeEdge(1000, var18);
        }
        GenLayer var8 = GenLayerZoom.magnify(1000, var15, 2);
        GenLayerHills var21 = new GenLayerHills(1000, var7, var8);
        var6 = GenLayerZoom.magnify(1000, var15, 2);
        var6 = GenLayerZoom.magnify(1000, var6, var5);
        GenLayerRiver var17 = new GenLayerRiver(1, var6);
        GenLayerSmooth var20 = new GenLayerSmooth(1000, var17);
        var7 = new GenLayerRareBiome(1001, var21);
        int var9 = 0;
        while (var9 < var5) {
            var7 = new GenLayerZoom(1000 + var9, var7);
            if (var9 == 0) {
                var7 = new GenLayerAddIsland(3, var7);
            }
            if (var9 == 1) {
                var7 = new GenLayerShore(1000, var7);
            }
            ++var9;
        }
        GenLayerSmooth var25 = new GenLayerSmooth(1000, var7);
        GenLayerRiverMix var26 = new GenLayerRiverMix(100, var25, var20);
        GenLayerVoronoiZoom var10 = new GenLayerVoronoiZoom(10, var26);
        var26.initWorldGenSeed(par0);
        var10.initWorldGenSeed(par0);
        return new GenLayer[]{var26, var10, var26};
    }

    public GenLayer(long par1) {
        this.baseSeed = par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
    }

    public void initWorldGenSeed(long par1) {
        this.worldGenSeed = par1;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(par1);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    public void initChunkSeed(long par1, long par3) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
    }

    protected int nextInt(int par1) {
        int var2 = (int)((this.chunkSeed >> 24) % (long)par1);
        if (var2 < 0) {
            var2 += par1;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }

    public abstract int[] getInts(int var1, int var2, int var3, int var4);

    protected static boolean func_151616_a(int p_151616_0_, int p_151616_1_) {
        if (p_151616_0_ == p_151616_1_) {
            return true;
        }
        if (p_151616_0_ != BiomeGenBase.field_150607_aa.biomeID && p_151616_0_ != BiomeGenBase.field_150608_ab.biomeID) {
            try {
                return BiomeGenBase.func_150568_d(p_151616_0_) != null && BiomeGenBase.func_150568_d(p_151616_1_) != null ? BiomeGenBase.func_150568_d(p_151616_0_).func_150569_a(BiomeGenBase.func_150568_d(p_151616_1_)) : false;
            }
            catch (Throwable var5) {
                CrashReport var3 = CrashReport.makeCrashReport(var5, "Comparing biomes");
                CrashReportCategory var4 = var3.makeCategory("Biomes being compared");
                var4.addCrashSection("Biome A ID", p_151616_0_);
                var4.addCrashSection("Biome B ID", p_151616_1_);
                var4.addCrashSectionCallable("Biome A", new Callable(){
                    private static final String __OBFID = "CL_00000560";

                    public String call() {
                        return String.valueOf(BiomeGenBase.func_150568_d(val$p_151616_0_));
                    }
                });
                var4.addCrashSectionCallable("Biome B", new Callable(){
                    private static final String __OBFID = "CL_00000561";

                    public String call() {
                        return String.valueOf(BiomeGenBase.func_150568_d(val$p_151616_1_));
                    }
                });
                throw new ReportedException(var3);
            }
        }
        if (p_151616_1_ != BiomeGenBase.field_150607_aa.biomeID && p_151616_1_ != BiomeGenBase.field_150608_ab.biomeID) {
            return false;
        }
        return true;
    }

    protected static boolean func_151618_b(int p_151618_0_) {
        if (p_151618_0_ != BiomeGenBase.ocean.biomeID && p_151618_0_ != BiomeGenBase.field_150575_M.biomeID && p_151618_0_ != BiomeGenBase.frozenOcean.biomeID) {
            return false;
        }
        return true;
    }

    protected /* varargs */ int func_151619_a(int ... p_151619_1_) {
        return p_151619_1_[this.nextInt(p_151619_1_.length)];
    }

    protected int func_151617_b(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
        return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_ ? p_151617_1_ : (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_ ? p_151617_2_ : (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_ ? p_151617_2_ : (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.func_151619_a(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_))))))))));
    }

}

