/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator {
    protected World currentWorld;
    protected Random randomGenerator;
    protected int chunk_X;
    protected int chunk_Z;
    protected WorldGenerator clayGen = new WorldGenClay(4);
    protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
    protected WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
    protected WorldGenerator dirtGen = new WorldGenMinable(Blocks.dirt, 32);
    protected WorldGenerator gravelGen = new WorldGenMinable(Blocks.gravel, 32);
    protected WorldGenerator coalGen = new WorldGenMinable(Blocks.coal_ore, 16);
    protected WorldGenerator ironGen = new WorldGenMinable(Blocks.iron_ore, 8);
    protected WorldGenerator goldGen = new WorldGenMinable(Blocks.gold_ore, 8);
    protected WorldGenerator redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7);
    protected WorldGenerator diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7);
    protected WorldGenerator lapisGen = new WorldGenMinable(Blocks.lapis_ore, 6);
    protected WorldGenFlowers field_150514_p = new WorldGenFlowers(Blocks.yellow_flower);
    protected WorldGenerator mushroomBrownGen = new WorldGenFlowers(Blocks.brown_mushroom);
    protected WorldGenerator mushroomRedGen = new WorldGenFlowers(Blocks.red_mushroom);
    protected WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
    protected WorldGenerator reedGen = new WorldGenReed();
    protected WorldGenerator cactusGen = new WorldGenCactus();
    protected WorldGenerator waterlilyGen = new WorldGenWaterlily();
    protected int waterlilyPerChunk;
    protected int treesPerChunk;
    protected int flowersPerChunk = 2;
    protected int grassPerChunk = 1;
    protected int deadBushPerChunk;
    protected int mushroomsPerChunk;
    protected int reedsPerChunk;
    protected int cactiPerChunk;
    protected int sandPerChunk = 1;
    protected int sandPerChunk2 = 3;
    protected int clayPerChunk = 1;
    protected int bigMushroomsPerChunk;
    public boolean generateLakes = true;
    private static final String __OBFID = "CL_00000164";

    public void func_150512_a(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_) {
        if (this.currentWorld != null) {
            throw new RuntimeException("Already decorating!!");
        }
        this.currentWorld = p_150512_1_;
        this.randomGenerator = p_150512_2_;
        this.chunk_X = p_150512_4_;
        this.chunk_Z = p_150512_5_;
        this.func_150513_a(p_150512_3_);
        this.currentWorld = null;
        this.randomGenerator = null;
    }

    protected void func_150513_a(BiomeGenBase p_150513_1_) {
        int var5;
        int var6;
        int var3;
        int var4;
        this.generateOres();
        int var2 = 0;
        while (var2 < this.sandPerChunk2) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
            ++var2;
        }
        var2 = 0;
        while (var2 < this.clayPerChunk) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.clayGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
            ++var2;
        }
        var2 = 0;
        while (var2 < this.sandPerChunk) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
            ++var2;
        }
        var2 = this.treesPerChunk;
        if (this.randomGenerator.nextInt(10) == 0) {
            ++var2;
        }
        var3 = 0;
        while (var3 < var2) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.currentWorld.getHeightValue(var4, var5);
            WorldGenAbstractTree var7 = p_150513_1_.func_150567_a(this.randomGenerator);
            var7.setScale(1.0, 1.0, 1.0);
            if (var7.generate(this.currentWorld, this.randomGenerator, var4, var6, var5)) {
                var7.func_150524_b(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < this.bigMushroomsPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, var4, this.currentWorld.getHeightValue(var4, var5), var5);
            ++var3;
        }
        var3 = 0;
        while (var3 < this.flowersPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            String var9 = p_150513_1_.func_150572_a(this.randomGenerator, var4, var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8) + 32), var5);
            BlockFlower var8 = BlockFlower.func_149857_e(var9);
            if (var8.getMaterial() != Material.air) {
                this.field_150514_p.func_150550_a(var8, BlockFlower.func_149856_f(var9));
                this.field_150514_p.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < this.grassPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            WorldGenerator var10 = p_150513_1_.getRandomWorldGenForGrass(this.randomGenerator);
            var10.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        var3 = 0;
        while (var3 < this.deadBushPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            new WorldGenDeadBush(Blocks.deadbush).generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        var3 = 0;
        while (var3 < this.waterlilyPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            while (var6 > 0 && this.currentWorld.isAirBlock(var4, var6 - 1, var5)) {
                --var6;
            }
            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        var3 = 0;
        while (var3 < this.mushroomsPerChunk) {
            if (this.randomGenerator.nextInt(4) == 0) {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                var6 = this.currentWorld.getHeightValue(var4, var5);
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
            if (this.randomGenerator.nextInt(8) == 0) {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
                this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
            ++var3;
        }
        if (this.randomGenerator.nextInt(4) == 0) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }
        if (this.randomGenerator.nextInt(8) == 0) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }
        var3 = 0;
        while (var3 < this.reedsPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        var3 = 0;
        while (var3 < 10) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        if (this.randomGenerator.nextInt(32) == 0) {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }
        var3 = 0;
        while (var3 < this.cactiPerChunk) {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            ++var3;
        }
        if (this.generateLakes) {
            var3 = 0;
            while (var3 < 50) {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8);
                var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                new WorldGenLiquids(Blocks.flowing_water).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
                ++var3;
            }
            var3 = 0;
            while (var3 < 20) {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
                var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                new WorldGenLiquids(Blocks.flowing_lava).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
                ++var3;
            }
        }
    }

    protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) {
        int var5 = 0;
        while (var5 < par1) {
            int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            int var7 = this.randomGenerator.nextInt(par4 - par3) + par3;
            int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
            ++var5;
        }
    }

    protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) {
        int var5 = 0;
        while (var5 < par1) {
            int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            int var7 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + (par3 - par4);
            int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
            ++var5;
        }
    }

    protected void generateOres() {
        this.genStandardOre1(20, this.dirtGen, 0, 256);
        this.genStandardOre1(10, this.gravelGen, 0, 256);
        this.genStandardOre1(20, this.coalGen, 0, 128);
        this.genStandardOre1(20, this.ironGen, 0, 64);
        this.genStandardOre1(2, this.goldGen, 0, 32);
        this.genStandardOre1(8, this.redstoneGen, 0, 16);
        this.genStandardOre1(1, this.diamondGen, 0, 16);
        this.genStandardOre2(1, this.lapisGen, 16, 16);
    }
}

