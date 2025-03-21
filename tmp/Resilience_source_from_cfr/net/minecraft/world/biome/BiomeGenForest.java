/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeGenForest
extends BiomeGenBase {
    private int field_150632_aF;
    protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
    protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
    protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);
    private static final String __OBFID = "CL_00000170";

    public BiomeGenForest(int p_i45377_1_, int p_i45377_2_) {
        super(p_i45377_1_);
        this.field_150632_aF = p_i45377_2_;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }
        this.func_76733_a(5159473);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }
        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }

    @Override
    protected BiomeGenBase func_150557_a(int p_150557_1_, boolean p_150557_2_) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = p_150557_1_;
            if (p_150557_2_) {
                this.field_150609_ah = (this.field_150609_ah & 16711422) >> 1;
            }
            return this;
        }
        return super.func_150557_a(p_150557_1_, p_150557_2_);
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return this.field_150632_aF == 3 && p_150567_1_.nextInt(3) > 0 ? field_150631_aE : (this.field_150632_aF != 2 && p_150567_1_.nextInt(5) != 0 ? this.worldGeneratorTrees : field_150630_aD);
    }

    @Override
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_) {
        if (this.field_150632_aF == 1) {
            double var5 = MathHelper.clamp_double((1.0 + field_150606_ad.func_151601_a((double)p_150572_2_ / 48.0, (double)p_150572_4_ / 48.0)) / 2.0, 0.0, 0.9999);
            int var7 = (int)(var5 * (double)BlockFlower.field_149859_a.length);
            if (var7 == 1) {
                var7 = 0;
            }
            return BlockFlower.field_149859_a[var7];
        }
        return super.func_150572_a(p_150572_1_, p_150572_2_, p_150572_3_, p_150572_4_);
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        int var9;
        int var5;
        int var8;
        int var6;
        int var7;
        if (this.field_150632_aF == 3) {
            var5 = 0;
            while (var5 < 4) {
                var6 = 0;
                while (var6 < 4) {
                    var7 = par3 + var5 * 4 + 1 + 8 + par2Random.nextInt(3);
                    var8 = par4 + var6 * 4 + 1 + 8 + par2Random.nextInt(3);
                    var9 = par1World.getHeightValue(var7, var8);
                    if (par2Random.nextInt(20) == 0) {
                        WorldGenBigMushroom var10 = new WorldGenBigMushroom();
                        var10.generate(par1World, par2Random, var7, var9, var8);
                    } else {
                        WorldGenAbstractTree var12 = this.func_150567_a(par2Random);
                        var12.setScale(1.0, 1.0, 1.0);
                        if (var12.generate(par1World, par2Random, var7, var9, var8)) {
                            var12.func_150524_b(par1World, par2Random, var7, var9, var8);
                        }
                    }
                    ++var6;
                }
                ++var5;
            }
        }
        var5 = par2Random.nextInt(5) - 3;
        if (this.field_150632_aF == 1) {
            var5 += 2;
        }
        var6 = 0;
        while (var6 < var5) {
            int var11;
            int var13;
            var7 = par2Random.nextInt(3);
            if (var7 == 0) {
                field_150610_ae.func_150548_a(1);
            } else if (var7 == 1) {
                field_150610_ae.func_150548_a(4);
            } else if (var7 == 2) {
                field_150610_ae.func_150548_a(5);
            }
            for (var8 = 0; var8 < 5 && !field_150610_ae.generate(par1World, par2Random, var9 = par3 + par2Random.nextInt(16) + 8, var11 = par2Random.nextInt(par1World.getHeightValue(var9, var13 = par4 + par2Random.nextInt(16) + 8) + 32), var13); ++var8) {
            }
            ++var6;
        }
        super.decorate(par1World, par2Random, par3, par4);
    }

    @Override
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        int var4 = super.getBiomeGrassColor(p_150558_1_, p_150558_2_, p_150558_3_);
        return this.field_150632_aF == 3 ? (var4 & 16711422) + 2634762 >> 1 : var4;
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            BiomeGenForest var1 = new BiomeGenForest(this.biomeID + 128, 1);
            var1.func_150570_a(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2f));
            var1.setBiomeName("Flower Forest");
            var1.func_150557_a(6976549, true);
            var1.func_76733_a(8233509);
            return var1;
        }
        return this.biomeID != BiomeGenBase.field_150583_P.biomeID && this.biomeID != BiomeGenBase.field_150582_Q.biomeID ? new BiomeGenMutated(this.biomeID + 128, this){

            @Override
            public void decorate(World var1, Random var2, int var3, int var4) {
                this.field_150611_aD.decorate(var1, var2, var3, var4);
            }
        } : new BiomeGenMutated(this.biomeID + 128, this){
            private static final String __OBFID = "CL_00000172";

            @Override
            public WorldGenAbstractTree func_150567_a(Random var1) {
                return var1.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
            }
        };
    }

}

