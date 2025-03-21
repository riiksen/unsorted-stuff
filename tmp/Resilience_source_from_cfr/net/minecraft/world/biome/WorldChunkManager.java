/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;

public class WorldChunkManager {
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private List biomesToSpawnIn;
    private static final String __OBFID = "CL_00000166";

    protected WorldChunkManager() {
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = new ArrayList();
        this.biomesToSpawnIn.add(BiomeGenBase.forest);
        this.biomesToSpawnIn.add(BiomeGenBase.plains);
        this.biomesToSpawnIn.add(BiomeGenBase.taiga);
        this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
        this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
        this.biomesToSpawnIn.add(BiomeGenBase.jungle);
        this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
    }

    public WorldChunkManager(long par1, WorldType par3WorldType) {
        this();
        GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(par1, par3WorldType);
        this.genBiomes = var4[0];
        this.biomeIndexLayer = var4[1];
    }

    public WorldChunkManager(World par1World) {
        this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
    }

    public List getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }

    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.biomeCache.getBiomeGenAt(par1, par2);
    }

    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        IntCache.resetIntCache();
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
        int var7 = 0;
        while (var7 < par4 * par5) {
            try {
                float var8 = (float)BiomeGenBase.func_150568_d(var6[var7]).getIntRainfall() / 65536.0f;
                if (var8 > 1.0f) {
                    var8 = 1.0f;
                }
                par1ArrayOfFloat[var7] = var8;
            }
            catch (Throwable var11) {
                CrashReport var9 = CrashReport.makeCrashReport(var11, "Invalid Biome id");
                CrashReportCategory var10 = var9.makeCategory("DownfallBlock");
                var10.addCrashSection("biome id", var7);
                var10.addCrashSection("downfalls[] size", par1ArrayOfFloat.length);
                var10.addCrashSection("x", par2);
                var10.addCrashSection("z", par3);
                var10.addCrashSection("w", par4);
                var10.addCrashSection("h", par5);
                throw new ReportedException(var9);
            }
            ++var7;
        }
        return par1ArrayOfFloat;
    }

    public float getTemperatureAtHeight(float par1, int par2) {
        return par1;
    }

    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
        IntCache.resetIntCache();
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);
        try {
            int var7 = 0;
            while (var7 < par4 * par5) {
                par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.func_150568_d(var6[var7]);
                ++var7;
            }
            return par1ArrayOfBiomeGenBase;
        }
        catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Invalid Biome id");
            CrashReportCategory var9 = var8.makeCategory("RawBiomeBlock");
            var9.addCrashSection("biomes[] size", par1ArrayOfBiomeGenBase.length);
            var9.addCrashSection("x", par2);
            var9.addCrashSection("z", par3);
            var9.addCrashSection("w", par4);
            var9.addCrashSection("h", par5);
            throw new ReportedException(var8);
        }
    }

    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
        return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
    }

    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6) {
        IntCache.resetIntCache();
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0) {
            BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
            System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
            return par1ArrayOfBiomeGenBase;
        }
        int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
        int var8 = 0;
        while (var8 < par4 * par5) {
            par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.func_150568_d(var7[var8]);
            ++var8;
        }
        return par1ArrayOfBiomeGenBase;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
        IntCache.resetIntCache();
        int var5 = par1 - par3 >> 2;
        int var6 = par2 - par3 >> 2;
        int var7 = par1 + par3 >> 2;
        int var8 = par2 + par3 >> 2;
        int var9 = var7 - var5 + 1;
        int var10 = var8 - var6 + 1;
        int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);
        try {
            int var12 = 0;
            do {
                if (var12 >= var9 * var10) {
                    return true;
                }
                BiomeGenBase var16 = BiomeGenBase.func_150568_d(var11[var12]);
                if (!par4List.contains(var16)) {
                    return false;
                }
                ++var12;
            } while (true);
        }
        catch (Throwable var15) {
            CrashReport var13 = CrashReport.makeCrashReport(var15, "Invalid Biome id");
            CrashReportCategory var14 = var13.makeCategory("Layer");
            var14.addCrashSection("Layer", this.genBiomes.toString());
            var14.addCrashSection("x", par1);
            var14.addCrashSection("z", par2);
            var14.addCrashSection("radius", par3);
            var14.addCrashSection("allowed", par4List);
            throw new ReportedException(var13);
        }
    }

    public ChunkPosition func_150795_a(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_) {
        IntCache.resetIntCache();
        int var6 = p_150795_1_ - p_150795_3_ >> 2;
        int var7 = p_150795_2_ - p_150795_3_ >> 2;
        int var8 = p_150795_1_ + p_150795_3_ >> 2;
        int var9 = p_150795_2_ + p_150795_3_ >> 2;
        int var10 = var8 - var6 + 1;
        int var11 = var9 - var7 + 1;
        int[] var12 = this.genBiomes.getInts(var6, var7, var10, var11);
        ChunkPosition var13 = null;
        int var14 = 0;
        int var15 = 0;
        while (var15 < var10 * var11) {
            int var16 = var6 + var15 % var10 << 2;
            int var17 = var7 + var15 / var10 << 2;
            BiomeGenBase var18 = BiomeGenBase.func_150568_d(var12[var15]);
            if (p_150795_4_.contains(var18) && (var13 == null || p_150795_5_.nextInt(var14 + 1) == 0)) {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
            }
            ++var15;
        }
        return var13;
    }

    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
}

