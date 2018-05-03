/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd
extends WorldProvider {
    private static final String __OBFID = "CL_00000389";

    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0.0f;
    }

    @Override
    public float[] calcSunriseSunsetColors(float par1, float par2) {
        return null;
    }

    @Override
    public Vec3 getFogColor(float par1, float par2) {
        int var3 = 10518688;
        float var4 = MathHelper.cos(par1 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        float var5 = (float)(var3 >> 16 & 255) / 255.0f;
        float var6 = (float)(var3 >> 8 & 255) / 255.0f;
        float var7 = (float)(var3 & 255) / 255.0f;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var5 *= var4 * 0.0f + 0.15f, var6 *= var4 * 0.0f + 0.15f, var7 *= var4 * 0.0f + 0.15f);
    }

    @Override
    public boolean isSkyColored() {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return 8.0f;
    }

    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return this.worldObj.getTopBlock(par1, par2).getMaterial().blocksMovement();
    }

    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(100, 50, 0);
    }

    @Override
    public int getAverageGroundLevel() {
        return 50;
    }

    @Override
    public boolean doesXZShowFog(int par1, int par2) {
        return true;
    }

    @Override
    public String getDimensionName() {
        return "The End";
    }
}

