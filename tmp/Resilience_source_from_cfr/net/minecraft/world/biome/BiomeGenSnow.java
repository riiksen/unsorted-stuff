/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeGenSnow
extends BiomeGenBase {
    private boolean field_150615_aC;
    private WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
    private WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);
    private static final String __OBFID = "CL_00000174";

    public BiomeGenSnow(int p_i45378_1_, boolean p_i45378_2_) {
        super(p_i45378_1_);
        this.field_150615_aC = p_i45378_2_;
        if (p_i45378_2_) {
            this.topBlock = Blocks.snow;
        }
        this.spawnableCreatureList.clear();
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        if (this.field_150615_aC) {
            int var6;
            int var7;
            int var5 = 0;
            while (var5 < 3) {
                var6 = par3 + par2Random.nextInt(16) + 8;
                var7 = par4 + par2Random.nextInt(16) + 8;
                this.field_150616_aD.generate(par1World, par2Random, var6, par1World.getHeightValue(var6, var7), var7);
                ++var5;
            }
            var5 = 0;
            while (var5 < 2) {
                var6 = par3 + par2Random.nextInt(16) + 8;
                var7 = par4 + par2Random.nextInt(16) + 8;
                this.field_150617_aE.generate(par1World, par2Random, var6, par1World.getHeightValue(var6, var7), var7);
                ++var5;
            }
        }
        super.decorate(par1World, par2Random, par3, par4);
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return new WorldGenTaiga2(false);
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        BiomeGenBase var1 = new BiomeGenSnow(this.biomeID + 128, true).func_150557_a(13828095, true).setBiomeName(String.valueOf(this.biomeName) + " Spikes").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).func_150570_a(new BiomeGenBase.Height(this.minHeight + 0.1f, this.maxHeight + 0.1f));
        var1.minHeight = this.minHeight + 0.3f;
        var1.maxHeight = this.maxHeight + 0.4f;
        return var1;
    }
}

