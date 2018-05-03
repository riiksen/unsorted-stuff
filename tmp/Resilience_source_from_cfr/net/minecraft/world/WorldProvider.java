/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.storage.WorldInfo;

public abstract class WorldProvider {
    public static final float[] moonPhaseFactors = new float[]{1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f};
    public World worldObj;
    public WorldType terrainType;
    public String field_82913_c;
    public WorldChunkManager worldChunkMgr;
    public boolean isHellWorld;
    public boolean hasNoSky;
    public float[] lightBrightnessTable = new float[16];
    public int dimensionId;
    private float[] colorsSunriseSunset = new float[4];
    private static final String __OBFID = "CL_00000386";

    public final void registerWorld(World par1World) {
        this.worldObj = par1World;
        this.terrainType = par1World.getWorldInfo().getTerrainType();
        this.field_82913_c = par1World.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }

    protected void generateLightBrightnessTable() {
        float var1 = 0.0f;
        int var2 = 0;
        while (var2 <= 15) {
            float var3 = 1.0f - (float)var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
            ++var2;
        }
    }

    protected void registerWorldChunkManager() {
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) {
            FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.func_150568_d(var1.getBiome()), 0.5f);
        } else {
            this.worldChunkMgr = new WorldChunkManager(this.worldObj);
        }
    }

    public IChunkProvider createChunkGenerator() {
        return this.terrainType == WorldType.FLAT ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.field_82913_c) : new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }

    public boolean canCoordinateBeSpawn(int par1, int par2) {
        if (this.worldObj.getTopBlock(par1, par2) == Blocks.grass) {
            return true;
        }
        return false;
    }

    public float calculateCelestialAngle(long par1, float par3) {
        int var4 = (int)(par1 % 24000);
        float var5 = ((float)var4 + par3) / 24000.0f - 0.25f;
        if (var5 < 0.0f) {
            var5 += 1.0f;
        }
        if (var5 > 1.0f) {
            var5 -= 1.0f;
        }
        float var6 = var5;
        var5 = 1.0f - (float)((Math.cos((double)var5 * 3.141592653589793) + 1.0) / 2.0);
        var5 = var6 + (var5 - var6) / 3.0f;
        return var5;
    }

    public int getMoonPhase(long par1) {
        return (int)(par1 / 24000 % 8 + 8) % 8;
    }

    public boolean isSurfaceWorld() {
        return true;
    }

    public float[] calcSunriseSunsetColors(float par1, float par2) {
        float var5;
        float var3 = 0.4f;
        float var4 = MathHelper.cos(par1 * 3.1415927f * 2.0f) - 0.0f;
        if (var4 >= (var5 = -0.0f) - var3 && var4 <= var5 + var3) {
            float var6 = (var4 - var5) / var3 * 0.5f + 0.5f;
            float var7 = 1.0f - (1.0f - MathHelper.sin(var6 * 3.1415927f)) * 0.99f;
            var7 *= var7;
            this.colorsSunriseSunset[0] = var6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[1] = var6 * var6 * 0.7f + 0.2f;
            this.colorsSunriseSunset[2] = var6 * var6 * 0.0f + 0.2f;
            this.colorsSunriseSunset[3] = var7;
            return this.colorsSunriseSunset;
        }
        return null;
    }

    public Vec3 getFogColor(float par1, float par2) {
        float var3 = MathHelper.cos(par1 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = 0.7529412f;
        float var5 = 0.84705883f;
        float var6 = 1.0f;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var4 *= var3 * 0.94f + 0.06f, var5 *= var3 * 0.94f + 0.06f, var6 *= var3 * 0.91f + 0.09f);
    }

    public boolean canRespawnHere() {
        return true;
    }

    public static WorldProvider getProviderForDimension(int par0) {
        return par0 == -1 ? new WorldProviderHell() : (par0 == 0 ? new WorldProviderSurface() : (par0 == 1 ? new WorldProviderEnd() : null));
    }

    public float getCloudHeight() {
        return 128.0f;
    }

    public boolean isSkyColored() {
        return true;
    }

    public ChunkCoordinates getEntrancePortalLocation() {
        return null;
    }

    public int getAverageGroundLevel() {
        return this.terrainType == WorldType.FLAT ? 4 : 64;
    }

    public boolean getWorldHasVoidParticles() {
        if (this.terrainType != WorldType.FLAT && !this.hasNoSky) {
            return true;
        }
        return false;
    }

    public double getVoidFogYFactor() {
        return this.terrainType == WorldType.FLAT ? 1.0 : 0.03125;
    }

    public boolean doesXZShowFog(int par1, int par2) {
        return false;
    }

    public abstract String getDimensionName();
}

