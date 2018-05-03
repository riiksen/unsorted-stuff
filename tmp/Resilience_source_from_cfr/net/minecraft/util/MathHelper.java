/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.Random;

public class MathHelper {
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    public static final float PId2 = 1.5707964f;
    private static final float radFull = 6.2831855f;
    private static final float degFull = 360.0f;
    private static final float radToIndex = 651.8986f;
    private static final float degToIndex = 11.377778f;
    public static final float deg2Rad = 0.017453292f;
    private static final float[] SIN_TABLE_FAST = new float[4096];
    public static boolean fastMath = false;
    private static float[] SIN_TABLE = new float[65536];
    private static final int[] multiplyDeBruijnBitPosition;
    private static final String __OBFID = "CL_00001496";

    static {
        int i = 0;
        while (i < 65536) {
            MathHelper.SIN_TABLE[i] = (float)Math.sin((double)i * 3.141592653589793 * 2.0 / 65536.0);
            ++i;
        }
        int[] arrn = new int[32];
        arrn[1] = 1;
        arrn[2] = 28;
        arrn[3] = 2;
        arrn[4] = 29;
        arrn[5] = 14;
        arrn[6] = 24;
        arrn[7] = 3;
        arrn[8] = 30;
        arrn[9] = 22;
        arrn[10] = 20;
        arrn[11] = 15;
        arrn[12] = 25;
        arrn[13] = 17;
        arrn[14] = 4;
        arrn[15] = 8;
        arrn[16] = 31;
        arrn[17] = 27;
        arrn[18] = 13;
        arrn[19] = 23;
        arrn[20] = 21;
        arrn[21] = 19;
        arrn[22] = 16;
        arrn[23] = 7;
        arrn[24] = 26;
        arrn[25] = 12;
        arrn[26] = 18;
        arrn[27] = 6;
        arrn[28] = 11;
        arrn[29] = 5;
        arrn[30] = 10;
        arrn[31] = 9;
        multiplyDeBruijnBitPosition = arrn;
        i = 0;
        while (i < 4096) {
            MathHelper.SIN_TABLE_FAST[i] = (float)Math.sin(((float)i + 0.5f) / 4096.0f * 6.2831855f);
            ++i;
        }
        i = 0;
        while (i < 360) {
            MathHelper.SIN_TABLE_FAST[(int)((float)i * 11.377778f) & 4095] = (float)Math.sin((float)i * 0.017453292f);
            i += 90;
        }
    }

    public static final float sin(float par0) {
        return fastMath ? SIN_TABLE_FAST[(int)(par0 * 651.8986f) & 4095] : SIN_TABLE[(int)(par0 * 10430.378f) & 65535];
    }

    public static final float cos(float par0) {
        return fastMath ? SIN_TABLE_FAST[(int)((par0 + 1.5707964f) * 651.8986f) & 4095] : SIN_TABLE[(int)(par0 * 10430.378f + 16384.0f) & 65535];
    }

    public static final float sqrt_float(float par0) {
        return (float)Math.sqrt(par0);
    }

    public static final float sqrt_double(double par0) {
        return (float)Math.sqrt(par0);
    }

    public static int floor_float(float par0) {
        int var1 = (int)par0;
        return par0 < (float)var1 ? var1 - 1 : var1;
    }

    public static int truncateDoubleToInt(double par0) {
        return (int)(par0 + 1024.0) - 1024;
    }

    public static int floor_double(double par0) {
        int var2 = (int)par0;
        return par0 < (double)var2 ? var2 - 1 : var2;
    }

    public static long floor_double_long(double par0) {
        long var2 = (long)par0;
        return par0 < (double)var2 ? var2 - 1 : var2;
    }

    public static float abs(float par0) {
        return par0 >= 0.0f ? par0 : - par0;
    }

    public static int abs_int(int par0) {
        return par0 >= 0 ? par0 : - par0;
    }

    public static int ceiling_float_int(float par0) {
        int var1 = (int)par0;
        return par0 > (float)var1 ? var1 + 1 : var1;
    }

    public static int ceiling_double_int(double par0) {
        int var2 = (int)par0;
        return par0 > (double)var2 ? var2 + 1 : var2;
    }

    public static int clamp_int(int par0, int par1, int par2) {
        return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
    }

    public static float clamp_float(float par0, float par1, float par2) {
        return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
    }

    public static double clamp_double(double p_151237_0_, double p_151237_2_, double p_151237_4_) {
        return p_151237_0_ < p_151237_2_ ? p_151237_2_ : (p_151237_0_ > p_151237_4_ ? p_151237_4_ : p_151237_0_);
    }

    public static double denormalizeClamp(double p_151238_0_, double p_151238_2_, double p_151238_4_) {
        return p_151238_4_ < 0.0 ? p_151238_0_ : (p_151238_4_ > 1.0 ? p_151238_2_ : p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_);
    }

    public static double abs_max(double par0, double par2) {
        if (par0 < 0.0) {
            par0 = - par0;
        }
        if (par2 < 0.0) {
            par2 = - par2;
        }
        return par0 > par2 ? par0 : par2;
    }

    public static int bucketInt(int par0, int par1) {
        return par0 < 0 ? - (- par0 - 1) / par1 - 1 : par0 / par1;
    }

    public static boolean stringNullOrLengthZero(String par0Str) {
        if (par0Str != null && par0Str.length() != 0) {
            return false;
        }
        return true;
    }

    public static int getRandomIntegerInRange(Random par0Random, int par1, int par2) {
        return par1 >= par2 ? par1 : par0Random.nextInt(par2 - par1 + 1) + par1;
    }

    public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
        return p_151240_1_ >= p_151240_2_ ? p_151240_1_ : p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_;
    }

    public static double getRandomDoubleInRange(Random par0Random, double par1, double par3) {
        return par1 >= par3 ? par1 : par0Random.nextDouble() * (par3 - par1) + par1;
    }

    public static double average(long[] par0ArrayOfLong) {
        long var1 = 0;
        long[] var3 = par0ArrayOfLong;
        int var4 = par0ArrayOfLong.length;
        int var5 = 0;
        while (var5 < var4) {
            long var6 = var3[var5];
            var1 += var6;
            ++var5;
        }
        return (double)var1 / (double)par0ArrayOfLong.length;
    }

    public static float wrapAngleTo180_float(float par0) {
        if ((par0 %= 360.0f) >= 180.0f) {
            par0 -= 360.0f;
        }
        if (par0 < -180.0f) {
            par0 += 360.0f;
        }
        return par0;
    }

    public static double wrapAngleTo180_double(double par0) {
        if ((par0 %= 360.0) >= 180.0) {
            par0 -= 360.0;
        }
        if (par0 < -180.0) {
            par0 += 360.0;
        }
        return par0;
    }

    public static int parseIntWithDefault(String par0Str, int par1) {
        int var2 = par1;
        try {
            var2 = Integer.parseInt(par0Str);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return var2;
    }

    public static int parseIntWithDefaultAndMax(String par0Str, int par1, int par2) {
        int var3 = par1;
        try {
            var3 = Integer.parseInt(par0Str);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (var3 < par2) {
            var3 = par2;
        }
        return var3;
    }

    public static double parseDoubleWithDefault(String par0Str, double par1) {
        double var3 = par1;
        try {
            var3 = Double.parseDouble(par0Str);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return var3;
    }

    public static double parseDoubleWithDefaultAndMax(String par0Str, double par1, double par3) {
        double var5 = par1;
        try {
            var5 = Double.parseDouble(par0Str);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (var5 < par3) {
            var5 = par3;
        }
        return var5;
    }

    public static int roundUpToPowerOfTwo(int p_151236_0_) {
        int var1 = p_151236_0_ - 1;
        var1 |= var1 >> 1;
        var1 |= var1 >> 2;
        var1 |= var1 >> 4;
        var1 |= var1 >> 8;
        var1 |= var1 >> 16;
        return var1 + 1;
    }

    private static boolean isPowerOfTwo(int p_151235_0_) {
        if (p_151235_0_ != 0 && (p_151235_0_ & p_151235_0_ - 1) == 0) {
            return true;
        }
        return false;
    }

    private static int calculateLogBaseTwoDeBruijn(int p_151241_0_) {
        p_151241_0_ = MathHelper.isPowerOfTwo(p_151241_0_) ? p_151241_0_ : MathHelper.roundUpToPowerOfTwo(p_151241_0_);
        return multiplyDeBruijnBitPosition[(int)((long)p_151241_0_ * 125613361 >> 27) & 31];
    }

    public static int calculateLogBaseTwo(int p_151239_0_) {
        return MathHelper.calculateLogBaseTwoDeBruijn(p_151239_0_) - (MathHelper.isPowerOfTwo(p_151239_0_) ? 0 : 1);
    }
}

