/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class ChunkProviderEnd
implements IChunkProvider {
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    int[][] field_73203_h = new int[32][32];
    private static final String __OBFID = "CL_00000397";

    public ChunkProviderEnd(World par1World, long par2) {
        this.endWorld = par1World;
        this.endRNG = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }

    public void func_147420_a(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_, BiomeGenBase[] p_147420_4_) {
        int var5 = 2;
        int var6 = var5 + 1;
        int var7 = 33;
        int var8 = var5 + 1;
        this.densities = this.initializeNoiseField(this.densities, p_147420_1_ * var5, 0, p_147420_2_ * var5, var6, var7, var8);
        int var9 = 0;
        while (var9 < var5) {
            int var10 = 0;
            while (var10 < var5) {
                int var11 = 0;
                while (var11 < 32) {
                    double var12 = 0.25;
                    double var14 = this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var18 = this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var20 = this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var22 = (this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
                    double var24 = (this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    double var26 = (this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
                    double var28 = (this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;
                    int var30 = 0;
                    while (var30 < 4) {
                        double var31 = 0.125;
                        double var33 = var14;
                        double var35 = var16;
                        double var37 = (var18 - var14) * var31;
                        double var39 = (var20 - var16) * var31;
                        int var41 = 0;
                        while (var41 < 8) {
                            int var42 = var41 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var30;
                            int var43 = 128;
                            double var44 = 0.125;
                            double var46 = var33;
                            double var48 = (var35 - var33) * var44;
                            int var50 = 0;
                            while (var50 < 8) {
                                Block var51 = null;
                                if (var46 > 0.0) {
                                    var51 = Blocks.end_stone;
                                }
                                p_147420_3_[var42] = var51;
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

    public void func_147421_b(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_) {
        int var5 = 0;
        while (var5 < 16) {
            int var6 = 0;
            while (var6 < 16) {
                int var7 = 1;
                int var8 = -1;
                Block var9 = Blocks.end_stone;
                Block var10 = Blocks.end_stone;
                int var11 = 127;
                while (var11 >= 0) {
                    int var12 = (var6 * 16 + var5) * 128 + var11;
                    Block var13 = p_147421_3_[var12];
                    if (var13 != null && var13.getMaterial() != Material.air) {
                        if (var13 == Blocks.stone) {
                            if (var8 == -1) {
                                if (var7 <= 0) {
                                    var9 = null;
                                    var10 = Blocks.end_stone;
                                }
                                var8 = var7;
                                p_147421_3_[var12] = var11 >= 0 ? var9 : var10;
                            } else if (var8 > 0) {
                                --var8;
                                p_147421_3_[var12] = var10;
                            }
                        }
                    } else {
                        var8 = -1;
                    }
                    --var11;
                }
                ++var6;
            }
            ++var5;
        }
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        this.endRNG.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] var3 = new Block[32768];
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.func_147420_a(par1, par2, var3, this.biomesForGeneration);
        this.func_147421_b(par1, par2, var3, this.biomesForGeneration);
        Chunk var4 = new Chunk(this.endWorld, var3, par1, par2);
        byte[] var5 = var4.getBiomeArray();
        int var6 = 0;
        while (var6 < var5.length) {
            var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
            ++var6;
        }
        var4.generateSkylightMap();
        return var4;
    }

    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        double var8 = 684.412;
        double var10 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, par2, par4, par5, par7, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, par2, par4, par5, par7, 200.0, 200.0, 0.5);
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0, var10 / 160.0, (var8 *= 2.0) / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        while (var14 < par5) {
            int var15 = 0;
            while (var15 < par7) {
                double var18;
                double var16 = (this.noiseData4[var13] + 256.0) / 512.0;
                if (var16 > 1.0) {
                    var16 = 1.0;
                }
                if ((var18 = this.noiseData5[var13] / 8000.0) < 0.0) {
                    var18 = (- var18) * 0.3;
                }
                var18 = var18 * 3.0 - 2.0;
                float var20 = (float)(var14 + par2 - 0) / 1.0f;
                float var21 = (float)(var15 + par4 - 0) / 1.0f;
                float var22 = 100.0f - MathHelper.sqrt_float(var20 * var20 + var21 * var21) * 8.0f;
                if (var22 > 80.0f) {
                    var22 = 80.0f;
                }
                if (var22 < -100.0f) {
                    var22 = -100.0f;
                }
                if (var18 > 1.0) {
                    var18 = 1.0;
                }
                var18 /= 8.0;
                var18 = 0.0;
                if (var16 < 0.0) {
                    var16 = 0.0;
                }
                var16 += 0.5;
                var18 = var18 * (double)par6 / 16.0;
                ++var13;
                double var23 = (double)par6 / 2.0;
                int var25 = 0;
                while (var25 < par6) {
                    double var37;
                    double var26 = 0.0;
                    double var28 = ((double)var25 - var23) * 8.0 / var16;
                    if (var28 < 0.0) {
                        var28 *= -1.0;
                    }
                    double var30 = this.noiseData2[var12] / 512.0;
                    double var32 = this.noiseData3[var12] / 512.0;
                    double var34 = (this.noiseData1[var12] / 10.0 + 1.0) / 2.0;
                    var26 = var34 < 0.0 ? var30 : (var34 > 1.0 ? var32 : var30 + (var32 - var30) * var34);
                    var26 -= 8.0;
                    var26 += (double)var22;
                    int var36 = 2;
                    if (var25 > par6 / 2 - var36) {
                        var37 = (float)(var25 - (par6 / 2 - var36)) / 64.0f;
                        if (var37 < 0.0) {
                            var37 = 0.0;
                        }
                        if (var37 > 1.0) {
                            var37 = 1.0;
                        }
                        var26 = var26 * (1.0 - var37) + -3000.0 * var37;
                    }
                    if (var25 < (var36 = 8)) {
                        var37 = (float)(var36 - var25) / ((float)var36 - 1.0f);
                        var26 = var26 * (1.0 - var37) + -30.0 * var37;
                    }
                    par1ArrayOfDouble[var12] = var26;
                    ++var12;
                    ++var25;
                }
                ++var15;
            }
            ++var14;
        }
        return par1ArrayOfDouble;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.field_149832_M = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
        var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);
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
        return "RandomLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(par2, par4);
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
    }
}

