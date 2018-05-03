/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;

public class BiomeGenPlains
extends BiomeGenBase {
    protected boolean field_150628_aC;
    private static final String __OBFID = "CL_00000180";

    protected BiomeGenPlains(int par1) {
        super(par1);
        this.setTemperatureRainfall(0.8f, 0.4f);
        this.func_150570_a(field_150593_e);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
    }

    @Override
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_) {
        double var5 = field_150606_ad.func_151601_a((double)p_150572_2_ / 200.0, (double)p_150572_4_ / 200.0);
        if (var5 < -0.8) {
            int var7 = p_150572_1_.nextInt(4);
            return BlockFlower.field_149859_a[4 + var7];
        }
        if (p_150572_1_.nextInt(3) > 0) {
            int var7 = p_150572_1_.nextInt(3);
            return var7 == 0 ? BlockFlower.field_149859_a[0] : (var7 == 1 ? BlockFlower.field_149859_a[3] : BlockFlower.field_149859_a[8]);
        }
        return BlockFlower.field_149858_b[0];
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        int var9;
        int var10;
        int var8;
        int var7;
        double var5 = field_150606_ad.func_151601_a((double)(par3 + 8) / 200.0, (double)(par4 + 8) / 200.0);
        if (var5 < -0.8) {
            this.theBiomeDecorator.flowersPerChunk = 15;
            this.theBiomeDecorator.grassPerChunk = 5;
        } else {
            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            field_150610_ae.func_150548_a(2);
            var7 = 0;
            while (var7 < 7) {
                var8 = par3 + par2Random.nextInt(16) + 8;
                var9 = par4 + par2Random.nextInt(16) + 8;
                var10 = par2Random.nextInt(par1World.getHeightValue(var8, var9) + 32);
                field_150610_ae.generate(par1World, par2Random, var8, var10, var9);
                ++var7;
            }
        }
        if (this.field_150628_aC) {
            field_150610_ae.func_150548_a(0);
            var7 = 0;
            while (var7 < 10) {
                var8 = par3 + par2Random.nextInt(16) + 8;
                var9 = par4 + par2Random.nextInt(16) + 8;
                var10 = par2Random.nextInt(par1World.getHeightValue(var8, var9) + 32);
                field_150610_ae.generate(par1World, par2Random, var8, var10, var9);
                ++var7;
            }
        }
        super.decorate(par1World, par2Random, par3, par4);
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        BiomeGenPlains var1 = new BiomeGenPlains(this.biomeID + 128);
        var1.setBiomeName("Sunflower Plains");
        var1.field_150628_aC = true;
        var1.setColor(9286496);
        var1.field_150609_ah = 14273354;
        return var1;
    }
}

