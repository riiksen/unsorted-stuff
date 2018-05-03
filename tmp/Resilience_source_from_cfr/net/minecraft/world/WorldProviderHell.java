/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderHell
extends WorldProvider {
    private static final String __OBFID = "CL_00000387";

    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }

    @Override
    public Vec3 getFogColor(float par1, float par2) {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }

    @Override
    protected void generateLightBrightnessTable() {
        float var1 = 0.1f;
        int var2 = 0;
        while (var2 <= 15) {
            float var3 = 1.0f - (float)var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
            ++var2;
        }
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return false;
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0.5f;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int par1, int par2) {
        return true;
    }

    @Override
    public String getDimensionName() {
        return "Nether";
    }
}

