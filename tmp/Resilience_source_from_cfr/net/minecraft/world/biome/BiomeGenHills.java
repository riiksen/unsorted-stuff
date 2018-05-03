/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHills
extends BiomeGenBase {
    private WorldGenerator theWorldGenerator = new WorldGenMinable(Blocks.monster_egg, 8);
    private WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
    private int field_150635_aE = 0;
    private int field_150636_aF = 1;
    private int field_150637_aG = 2;
    private int field_150638_aH = this.field_150635_aE;
    private static final String __OBFID = "CL_00000168";

    protected BiomeGenHills(int p_i45373_1_, boolean p_i45373_2_) {
        super(p_i45373_1_);
        if (p_i45373_2_) {
            this.theBiomeDecorator.treesPerChunk = 3;
            this.field_150638_aH = this.field_150636_aF;
        }
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return p_150567_1_.nextInt(3) > 0 ? this.field_150634_aD : super.func_150567_a(p_150567_1_);
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        int var8;
        int var7;
        super.decorate(par1World, par2Random, par3, par4);
        int var5 = 3 + par2Random.nextInt(6);
        int var6 = 0;
        while (var6 < var5) {
            int var9;
            var7 = par3 + par2Random.nextInt(16);
            if (par1World.getBlock(var7, var8 = par2Random.nextInt(28) + 4, var9 = par4 + par2Random.nextInt(16)) == Blocks.stone) {
                par1World.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
            }
            ++var6;
        }
        var5 = 0;
        while (var5 < 7) {
            var6 = par3 + par2Random.nextInt(16);
            var7 = par2Random.nextInt(64);
            var8 = par4 + par2Random.nextInt(16);
            this.theWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
            ++var5;
        }
    }

    @Override
    public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        this.topBlock = Blocks.grass;
        this.field_150604_aj = 0;
        this.fillerBlock = Blocks.dirt;
        if ((p_150573_7_ < -1.0 || p_150573_7_ > 2.0) && this.field_150638_aH == this.field_150637_aG) {
            this.topBlock = Blocks.gravel;
            this.fillerBlock = Blocks.gravel;
        } else if (p_150573_7_ > 1.0 && this.field_150638_aH != this.field_150636_aF) {
            this.topBlock = Blocks.stone;
            this.fillerBlock = Blocks.stone;
        }
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }

    private BiomeGenHills func_150633_b(BiomeGenBase p_150633_1_) {
        this.field_150638_aH = this.field_150637_aG;
        this.func_150557_a(p_150633_1_.color, true);
        this.setBiomeName(String.valueOf(p_150633_1_.biomeName) + " M");
        this.func_150570_a(new BiomeGenBase.Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
        this.setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
        return this;
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        return new BiomeGenHills(this.biomeID + 128, false).func_150633_b(this);
    }
}

