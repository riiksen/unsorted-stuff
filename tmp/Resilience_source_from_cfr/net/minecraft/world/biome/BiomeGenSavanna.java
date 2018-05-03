/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeGenSavanna
extends BiomeGenBase {
    private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
    private static final String __OBFID = "CL_00000182";

    protected BiomeGenSavanna(int p_i45383_1_) {
        super(p_i45383_1_);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return p_150567_1_.nextInt(5) > 0 ? field_150627_aC : this.worldGeneratorTrees;
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        Mutated var1 = new Mutated(this.biomeID + 128, this);
        var1.temperature = (this.temperature + 1.0f) * 0.5f;
        var1.minHeight = this.minHeight * 0.5f + 0.3f;
        var1.maxHeight = this.maxHeight * 0.5f + 1.2f;
        return var1;
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        field_150610_ae.func_150548_a(2);
        int var5 = 0;
        while (var5 < 7) {
            int var6 = par3 + par2Random.nextInt(16) + 8;
            int var7 = par4 + par2Random.nextInt(16) + 8;
            int var8 = par2Random.nextInt(par1World.getHeightValue(var6, var7) + 32);
            field_150610_ae.generate(par1World, par2Random, var6, var8, var7);
            ++var5;
        }
        super.decorate(par1World, par2Random, par3, par4);
    }

    public static class Mutated
    extends BiomeGenMutated {
        private static final String __OBFID = "CL_00000183";

        public Mutated(int p_i45382_1_, BiomeGenBase p_i45382_2_) {
            super(p_i45382_1_, p_i45382_2_);
            this.theBiomeDecorator.treesPerChunk = 2;
            this.theBiomeDecorator.flowersPerChunk = 2;
            this.theBiomeDecorator.grassPerChunk = 5;
        }

        @Override
        public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
            this.topBlock = Blocks.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.dirt;
            if (p_150573_7_ > 1.75) {
                this.topBlock = Blocks.stone;
                this.fillerBlock = Blocks.stone;
            } else if (p_150573_7_ > -0.5) {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 1;
            }
            this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
        }

        @Override
        public void decorate(World par1World, Random par2Random, int par3, int par4) {
            this.theBiomeDecorator.func_150512_a(par1World, par2Random, this, par3, par4);
        }
    }

}

