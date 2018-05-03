/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManagerHell
extends WorldChunkManager {
    private BiomeGenBase biomeGenerator;
    private float rainfall;
    private static final String __OBFID = "CL_00000169";

    public WorldChunkManagerHell(BiomeGenBase p_i45374_1_, float p_i45374_2_) {
        this.biomeGenerator = p_i45374_1_;
        this.rainfall = p_i45374_2_;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.biomeGenerator;
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }

    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.rainfall);
        return par1ArrayOfFloat;
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6) {
        return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
    }

    @Override
    public ChunkPosition func_150795_a(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_) {
        return p_150795_4_.contains(this.biomeGenerator) ? new ChunkPosition(p_150795_1_ - p_150795_3_ + p_150795_5_.nextInt(p_150795_3_ * 2 + 1), 0, p_150795_2_ - p_150795_3_ + p_150795_5_.nextInt(p_150795_3_ * 2 + 1)) : null;
    }

    @Override
    public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
        return par4List.contains(this.biomeGenerator);
    }
}

