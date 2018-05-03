/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

public class ChunkProviderHell
implements IChunkProvider {
    private Random hellRNG;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] netherrackExclusivityNoise = new double[256];
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    private static final String __OBFID = "CL_00000392";

    public ChunkProviderHell(World par1World, long par2) {
        this.worldObj = par1World;
        this.hellRNG = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }

    public void func_147419_a(int p_147419_1_, int p_147419_2_, Block[] p_147419_3_) {
        int var4 = 4;
        int var5 = 32;
        int var6 = var4 + 1;
        int var7 = 17;
        int var8 = var4 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, p_147419_1_ * var4, 0, p_147419_2_ * var4, var6, var7, var8);
        int var9 = 0;
        while (var9 < var4) {
            int var10 = 0;
            while (var10 < var4) {
                int var11 = 0;
                while (var11 < 16) {
                    double var12 = 0.125;
                    double var14 = this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var18 = this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var20 = this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var22 = (this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
                    double var24 = (this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    double var26 = (this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
                    double var28 = (this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;
                    int var30 = 0;
                    while (var30 < 8) {
                        double var31 = 0.25;
                        double var33 = var14;
                        double var35 = var16;
                        double var37 = (var18 - var14) * var31;
                        double var39 = (var20 - var16) * var31;
                        int var41 = 0;
                        while (var41 < 4) {
                            int var42 = var41 + var9 * 4 << 11 | 0 + var10 * 4 << 7 | var11 * 8 + var30;
                            int var43 = 128;
                            double var44 = 0.25;
                            double var46 = var33;
                            double var48 = (var35 - var33) * var44;
                            int var50 = 0;
                            while (var50 < 4) {
                                Block var51 = null;
                                if (var11 * 8 + var30 < var5) {
                                    var51 = Blocks.lava;
                                }
                                if (var46 > 0.0) {
                                    var51 = Blocks.netherrack;
                                }
                                p_147419_3_[var42] = var51;
                                var42 += var43;
                                var46 += var48;
                                ++var50;
                            }
                            var33 += var37;
                            var35 += var39;
                            ++var41;
                        }
                        var14 += var22;
                        var16 += var24;
                        var18 += var26;
                        var20 += var28;
                        ++var30;
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
    }

    public void func_147418_b(int p_147418_1_, int p_147418_2_, Block[] p_147418_3_) {
        int var4 = 64;
        double var5 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5, var5, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_147418_1_ * 16, 109, p_147418_2_ * 16, 16, 1, 16, var5, 1.0, var5);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5 * 2.0, var5 * 2.0, var5 * 2.0);
        int var7 = 0;
        while (var7 < 16) {
            int var8 = 0;
            while (var8 < 16) {
                boolean var9 = this.slowsandNoise[var7 + var8 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                boolean var10 = this.gravelNoise[var7 + var8 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                int var11 = (int)(this.netherrackExclusivityNoise[var7 + var8 * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
                int var12 = -1;
                Block var13 = Blocks.netherrack;
                Block var14 = Blocks.netherrack;
                int var15 = 127;
                while (var15 >= 0) {
                    int var16 = (var8 * 16 + var7) * 128 + var15;
                    if (var15 < 127 - this.hellRNG.nextInt(5) && var15 > 0 + this.hellRNG.nextInt(5)) {
                        Block var17 = p_147418_3_[var16];
                        if (var17 != null && var17.getMaterial() != Material.air) {
                            if (var17 == Blocks.netherrack) {
                                if (var12 == -1) {
                                    if (var11 <= 0) {
                                        var13 = null;
                                        var14 = Blocks.netherrack;
                                    } else if (var15 >= var4 - 4 && var15 <= var4 + 1) {
                                        var13 = Blocks.netherrack;
                                        var14 = Blocks.netherrack;
                                        if (var10) {
                                            var13 = Blocks.gravel;
                                            var14 = Blocks.netherrack;
                                        }
                                        if (var9) {
                                            var13 = Blocks.soul_sand;
                                            var14 = Blocks.soul_sand;
                                        }
                                    }
                                    if (var15 < var4 && (var13 == null || var13.getMaterial() == Material.air)) {
                                        var13 = Blocks.lava;
                                    }
                                    var12 = var11;
                                    p_147418_3_[var16] = var15 >= var4 - 1 ? var13 : var14;
                                } else if (var12 > 0) {
                                    --var12;
                                    p_147418_3_[var16] = var14;
                                }
                            }
                        } else {
                            var12 = -1;
                        }
                    } else {
                        p_147418_3_[var16] = Blocks.bedrock;
                    }
                    --var15;
                }
                ++var8;
            }
            ++var7;
        }
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        this.hellRNG.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] var3 = new Block[32768];
        this.func_147419_a(par1, par2, var3);
        this.func_147418_b(par1, par2, var3);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
        this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, var3);
        Chunk var4 = new Chunk(this.worldObj, var3, par1, par2);
        BiomeGenBase[] var5 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
        byte[] var6 = var4.getBiomeArray();
        int var7 = 0;
        while (var7 < var6.length) {
            var6[var7] = (byte)var5[var7].biomeID;
            ++var7;
        }
        var4.resetRelightChecks();
        return var4;
    }

    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        double var8 = 684.412;
        double var10 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0, var10 / 60.0, var8 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        int var12 = 0;
        int var13 = 0;
        double[] var14 = new double[par6];
        int var15 = 0;
        while (var15 < par6) {
            var14[var15] = Math.cos((double)var15 * 3.141592653589793 * 6.0 / (double)par6) * 2.0;
            double var16 = var15;
            if (var15 > par6 / 2) {
                var16 = par6 - 1 - var15;
            }
            if (var16 < 4.0) {
                var16 = 4.0 - var16;
                double[] arrd = var14;
                int n = var15;
                arrd[n] = arrd[n] - var16 * var16 * var16 * 10.0;
            }
            ++var15;
        }
        var15 = 0;
        while (var15 < par5) {
            int var36 = 0;
            while (var36 < par7) {
                double var17 = (this.noiseData4[var13] + 256.0) / 512.0;
                if (var17 > 1.0) {
                    var17 = 1.0;
                }
                double var19 = 0.0;
                double var21 = this.noiseData5[var13] / 8000.0;
                if (var21 < 0.0) {
                    var21 = - var21;
                }
                if ((var21 = var21 * 3.0 - 3.0) < 0.0) {
                    if ((var21 /= 2.0) < -1.0) {
                        var21 = -1.0;
                    }
                    var21 /= 1.4;
                    var21 /= 2.0;
                    var17 = 0.0;
                } else {
                    if (var21 > 1.0) {
                        var21 = 1.0;
                    }
                    var21 /= 6.0;
                }
                var17 += 0.5;
                var21 = var21 * (double)par6 / 16.0;
                ++var13;
                int var23 = 0;
                while (var23 < par6) {
                    double var34;
                    double var24 = 0.0;
                    double var26 = var14[var23];
                    double var28 = this.noiseData2[var12] / 512.0;
                    double var30 = this.noiseData3[var12] / 512.0;
                    double var32 = (this.noiseData1[var12] / 10.0 + 1.0) / 2.0;
                    var24 = var32 < 0.0 ? var28 : (var32 > 1.0 ? var30 : var28 + (var30 - var28) * var32);
                    var24 -= var26;
                    if (var23 > par6 - 4) {
                        var34 = (float)(var23 - (par6 - 4)) / 3.0f;
                        var24 = var24 * (1.0 - var34) + -10.0 * var34;
                    }
                    if ((double)var23 < var19) {
                        var34 = (var19 - (double)var23) / 4.0;
                        if (var34 < 0.0) {
                            var34 = 0.0;
                        }
                        if (var34 > 1.0) {
                            var34 = 1.0;
                        }
                        var24 = var24 * (1.0 - var34) + -10.0 * var34;
                    }
                    par1ArrayOfDouble[var12] = var24;
                    ++var12;
                    ++var23;
                }
                ++var36;
            }
            ++var15;
        }
        return par1ArrayOfDouble;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        int var9;
        int var11;
        int var10;
        int var8;
        int var7;
        BlockFalling.field_149832_M = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        int var6 = 0;
        while (var6 < 8) {
            var7 = var4 + this.hellRNG.nextInt(16) + 8;
            var8 = this.hellRNG.nextInt(120) + 4;
            var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenHellLava(Blocks.flowing_lava, false).generate(this.worldObj, this.hellRNG, var7, var8, var9);
            ++var6;
        }
        var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        var7 = 0;
        while (var7 < var6) {
            var8 = var4 + this.hellRNG.nextInt(16) + 8;
            var9 = this.hellRNG.nextInt(120) + 4;
            var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFire().generate(this.worldObj, this.hellRNG, var8, var9, var10);
            ++var7;
        }
        var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);
        var7 = 0;
        while (var7 < var6) {
            var8 = var4 + this.hellRNG.nextInt(16) + 8;
            var9 = this.hellRNG.nextInt(120) + 4;
            var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone1().generate(this.worldObj, this.hellRNG, var8, var9, var10);
            ++var7;
        }
        var7 = 0;
        while (var7 < 10) {
            var8 = var4 + this.hellRNG.nextInt(16) + 8;
            var9 = this.hellRNG.nextInt(128);
            var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone2().generate(this.worldObj, this.hellRNG, var8, var9, var10);
            ++var7;
        }
        if (this.hellRNG.nextInt(1) == 0) {
            var7 = var4 + this.hellRNG.nextInt(16) + 8;
            var8 = this.hellRNG.nextInt(128);
            var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.brown_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            var7 = var4 + this.hellRNG.nextInt(16) + 8;
            var8 = this.hellRNG.nextInt(128);
            var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.red_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        WorldGenMinable var12 = new WorldGenMinable(Blocks.quartz_ore, 13, Blocks.netherrack);
        var8 = 0;
        while (var8 < 16) {
            var9 = var4 + this.hellRNG.nextInt(16);
            var10 = this.hellRNG.nextInt(108) + 10;
            var11 = var5 + this.hellRNG.nextInt(16);
            var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
            ++var8;
        }
        var8 = 0;
        while (var8 < 16) {
            var9 = var4 + this.hellRNG.nextInt(16);
            var10 = this.hellRNG.nextInt(108) + 10;
            var11 = var5 + this.hellRNG.nextInt(16);
            new WorldGenHellLava(Blocks.flowing_lava, true).generate(this.worldObj, this.hellRNG, var9, var10, var11);
            ++var8;
        }
        BlockFalling.field_149832_M = false;
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "HellRandomLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        if (par1EnumCreatureType == EnumCreatureType.monster) {
            if (this.genNetherBridge.hasStructureAt(par2, par3, par4)) {
                return this.genNetherBridge.getSpawnList();
            }
            if (this.genNetherBridge.func_142038_b(par2, par3, par4) && this.worldObj.getBlock(par2, par3 - 1, par4) == Blocks.nether_brick) {
                return this.genNetherBridge.getSpawnList();
            }
        }
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return var5.getSpawnableList(par1EnumCreatureType);
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int par1, int par2) {
        this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, null);
    }
}

