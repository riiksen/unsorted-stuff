/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorImproved;

public class NoiseGeneratorOctaves
extends NoiseGenerator {
    private NoiseGeneratorImproved[] generatorCollection;
    private int octaves;
    private static final String __OBFID = "CL_00000535";

    public NoiseGeneratorOctaves(Random par1Random, int par2) {
        this.octaves = par2;
        this.generatorCollection = new NoiseGeneratorImproved[par2];
        int var3 = 0;
        while (var3 < par2) {
            this.generatorCollection[var3] = new NoiseGeneratorImproved(par1Random);
            ++var3;
        }
    }

    public double[] generateNoiseOctaves(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7, double par8, double par10, double par12) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        } else {
            int var14 = 0;
            while (var14 < par1ArrayOfDouble.length) {
                par1ArrayOfDouble[var14] = 0.0;
                ++var14;
            }
        }
        double var27 = 1.0;
        int var16 = 0;
        while (var16 < this.octaves) {
            double var17 = (double)par2 * var27 * par8;
            double var19 = (double)par3 * var27 * par10;
            double var21 = (double)par4 * var27 * par12;
            long var23 = MathHelper.floor_double_long(var17);
            long var25 = MathHelper.floor_double_long(var21);
            var17 -= (double)var23;
            var21 -= (double)var25;
            this.generatorCollection[var16].populateNoiseArray(par1ArrayOfDouble, var17 += (double)(var23 %= 0x1000000), var19, var21 += (double)(var25 %= 0x1000000), par5, par6, par7, par8 * var27, par10 * var27, par12 * var27, var27);
            var27 /= 2.0;
            ++var16;
        }
        return par1ArrayOfDouble;
    }

    public double[] generateNoiseOctaves(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, double par6, double par8, double par10) {
        return this.generateNoiseOctaves(par1ArrayOfDouble, par2, 10, par3, par4, 1, par5, par6, 1.0, par8);
    }
}

