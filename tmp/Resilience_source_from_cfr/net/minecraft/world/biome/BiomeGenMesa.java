/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeGenMesa
extends BiomeGenBase {
    private byte[] field_150621_aC;
    private long field_150622_aD;
    private NoiseGeneratorPerlin field_150623_aE;
    private NoiseGeneratorPerlin field_150624_aF;
    private NoiseGeneratorPerlin field_150625_aG;
    private boolean field_150626_aH;
    private boolean field_150620_aI;
    private static final String __OBFID = "CL_00000176";

    public BiomeGenMesa(int p_i45380_1_, boolean p_i45380_2_, boolean p_i45380_3_) {
        super(p_i45380_1_);
        this.field_150626_aH = p_i45380_2_;
        this.field_150620_aI = p_i45380_3_;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0f, 0.0f);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.field_150604_aj = 1;
        this.fillerBlock = Blocks.stained_hardened_clay;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();
        if (p_i45380_3_) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return this.worldGeneratorTrees;
    }

    @Override
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        return 10387789;
    }

    @Override
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        return 9470285;
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        super.decorate(par1World, par2Random, par3, par4);
    }

    @Override
    public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        int var11;
        int var12;
        if (this.field_150621_aC == null || this.field_150622_aD != p_150573_1_.getSeed()) {
            this.func_150619_a(p_150573_1_.getSeed());
        }
        if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != p_150573_1_.getSeed()) {
            Random var9 = new Random(this.field_150622_aD);
            this.field_150623_aE = new NoiseGeneratorPerlin(var9, 4);
            this.field_150624_aF = new NoiseGeneratorPerlin(var9, 1);
        }
        this.field_150622_aD = p_150573_1_.getSeed();
        double var25 = 0.0;
        if (this.field_150626_aH) {
            var11 = (p_150573_5_ & -16) + (p_150573_6_ & 15);
            var12 = (p_150573_6_ & -16) + (p_150573_5_ & 15);
            double var13 = Math.min(Math.abs(p_150573_7_), this.field_150623_aE.func_151601_a((double)var11 * 0.25, (double)var12 * 0.25));
            if (var13 > 0.0) {
                var25 = var13 * var13 * 2.5;
                double var15 = 0.001953125;
                double var17 = Math.abs(this.field_150624_aF.func_151601_a((double)var11 * var15, (double)var12 * var15));
                double var19 = Math.ceil(var17 * 50.0) + 14.0;
                if (var25 > var19) {
                    var25 = var19;
                }
                var25 += 64.0;
            }
        }
        var11 = p_150573_5_ & 15;
        var12 = p_150573_6_ & 15;
        boolean var26 = true;
        Block var14 = Blocks.stained_hardened_clay;
        Block var27 = this.fillerBlock;
        int var16 = (int)(p_150573_7_ / 3.0 + 3.0 + p_150573_2_.nextDouble() * 0.25);
        boolean var28 = Math.cos(p_150573_7_ / 3.0 * 3.141592653589793) > 0.0;
        int var18 = -1;
        boolean var29 = false;
        int var20 = p_150573_3_.length / 256;
        int var21 = 255;
        while (var21 >= 0) {
            int var22 = (var12 * 16 + var11) * var20 + var21;
            if ((p_150573_3_[var22] == null || p_150573_3_[var22].getMaterial() == Material.air) && var21 < (int)var25) {
                p_150573_3_[var22] = Blocks.stone;
            }
            if (var21 <= 0 + p_150573_2_.nextInt(5)) {
                p_150573_3_[var22] = Blocks.bedrock;
            } else {
                Block var23 = p_150573_3_[var22];
                if (var23 != null && var23.getMaterial() != Material.air) {
                    if (var23 == Blocks.stone) {
                        int var24;
                        if (var18 == -1) {
                            var29 = false;
                            if (var16 <= 0) {
                                var14 = null;
                                var27 = Blocks.stone;
                            } else if (var21 >= 59 && var21 <= 64) {
                                var14 = Blocks.stained_hardened_clay;
                                var27 = this.fillerBlock;
                            }
                            if (var21 < 63 && (var14 == null || var14.getMaterial() == Material.air)) {
                                var14 = Blocks.water;
                            }
                            var18 = var16 + Math.max(0, var21 - 63);
                            if (var21 >= 62) {
                                if (this.field_150620_aI && var21 > 86 + var16 * 2) {
                                    if (var28) {
                                        p_150573_3_[var22] = Blocks.dirt;
                                        p_150573_4_[var22] = 1;
                                    } else {
                                        p_150573_3_[var22] = Blocks.grass;
                                    }
                                } else if (var21 > 66 + var16) {
                                    var24 = 16;
                                    if (var21 >= 64 && var21 <= 127) {
                                        if (!var28) {
                                            var24 = this.func_150618_d(p_150573_5_, var21, p_150573_6_);
                                        }
                                    } else {
                                        var24 = 1;
                                    }
                                    if (var24 < 16) {
                                        p_150573_3_[var22] = Blocks.stained_hardened_clay;
                                        p_150573_4_[var22] = var24;
                                    } else {
                                        p_150573_3_[var22] = Blocks.hardened_clay;
                                    }
                                } else {
                                    p_150573_3_[var22] = this.topBlock;
                                    p_150573_4_[var22] = (byte)this.field_150604_aj;
                                    var29 = true;
                                }
                            } else {
                                p_150573_3_[var22] = var27;
                                if (var27 == Blocks.stained_hardened_clay) {
                                    p_150573_4_[var22] = 1;
                                }
                            }
                        } else if (var18 > 0) {
                            --var18;
                            if (var29) {
                                p_150573_3_[var22] = Blocks.stained_hardened_clay;
                                p_150573_4_[var22] = 1;
                            } else {
                                var24 = this.func_150618_d(p_150573_5_, var21, p_150573_6_);
                                if (var24 < 16) {
                                    p_150573_3_[var22] = Blocks.stained_hardened_clay;
                                    p_150573_4_[var22] = var24;
                                } else {
                                    p_150573_3_[var22] = Blocks.hardened_clay;
                                }
                            }
                        }
                    }
                } else {
                    var18 = -1;
                }
            }
            --var21;
        }
    }

    private void func_150619_a(long p_150619_1_) {
        int var9;
        int var8;
        int var7;
        int var6;
        this.field_150621_aC = new byte[64];
        Arrays.fill(this.field_150621_aC, 16);
        Random var3 = new Random(p_150619_1_);
        this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);
        int var4 = 0;
        while (var4 < 64) {
            if ((var4 += var3.nextInt(5) + 1) < 64) {
                this.field_150621_aC[var4] = 1;
            }
            ++var4;
        }
        var4 = var3.nextInt(4) + 2;
        int var5 = 0;
        while (var5 < var4) {
            var6 = var3.nextInt(3) + 1;
            var7 = var3.nextInt(64);
            var8 = 0;
            while (var7 + var8 < 64 && var8 < var6) {
                this.field_150621_aC[var7 + var8] = 4;
                ++var8;
            }
            ++var5;
        }
        var5 = var3.nextInt(4) + 2;
        var6 = 0;
        while (var6 < var5) {
            var7 = var3.nextInt(3) + 2;
            var8 = var3.nextInt(64);
            var9 = 0;
            while (var8 + var9 < 64 && var9 < var7) {
                this.field_150621_aC[var8 + var9] = 12;
                ++var9;
            }
            ++var6;
        }
        var6 = var3.nextInt(4) + 2;
        var7 = 0;
        while (var7 < var6) {
            var8 = var3.nextInt(3) + 1;
            var9 = var3.nextInt(64);
            int var10 = 0;
            while (var9 + var10 < 64 && var10 < var8) {
                this.field_150621_aC[var9 + var10] = 14;
                ++var10;
            }
            ++var7;
        }
        var7 = var3.nextInt(3) + 3;
        var8 = 0;
        var9 = 0;
        while (var9 < var7) {
            int var12 = 1;
            int var11 = 0;
            while ((var8 += var3.nextInt(16) + 4) + var11 < 64 && var11 < var12) {
                this.field_150621_aC[var8 + var11] = 0;
                if (var8 + var11 > 1 && var3.nextBoolean()) {
                    this.field_150621_aC[var8 + var11 - 1] = 8;
                }
                if (var8 + var11 < 63 && var3.nextBoolean()) {
                    this.field_150621_aC[var8 + var11 + 1] = 8;
                }
                ++var11;
            }
            ++var9;
        }
    }

    private byte func_150618_d(int p_150618_1_, int p_150618_2_, int p_150618_3_) {
        int var4 = (int)Math.round(this.field_150625_aG.func_151601_a((double)p_150618_1_ * 1.0 / 512.0, (double)p_150618_1_ * 1.0 / 512.0) * 2.0);
        return this.field_150621_aC[(p_150618_2_ + var4 + 64) % 64];
    }

    @Override
    protected BiomeGenBase func_150566_k() {
        boolean var1 = this.biomeID == BiomeGenBase.field_150589_Z.biomeID;
        BiomeGenMesa var2 = new BiomeGenMesa(this.biomeID + 128, var1, this.field_150620_aI);
        if (!var1) {
            var2.func_150570_a(field_150591_g);
            var2.setBiomeName(String.valueOf(this.biomeName) + " M");
        } else {
            var2.setBiomeName(String.valueOf(this.biomeName) + " (Bryce)");
        }
        var2.func_150557_a(this.color, true);
        return var2;
    }
}

