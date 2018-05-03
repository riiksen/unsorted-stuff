/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEdge
extends GenLayer {
    private final Mode field_151627_c;
    private static final String __OBFID = "CL_00000547";

    public GenLayerEdge(long p_i45474_1_, GenLayer p_i45474_3_, Mode p_i45474_4_) {
        super(p_i45474_1_);
        this.parent = p_i45474_3_;
        this.field_151627_c = p_i45474_4_;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        switch (SwitchMode.field_151642_a[this.field_151627_c.ordinal()]) {
            default: {
                return this.func_151626_c(par1, par2, par3, par4);
            }
            case 2: {
                return this.func_151624_d(par1, par2, par3, par4);
            }
            case 3: 
        }
        return this.func_151625_e(par1, par2, par3, par4);
    }

    private int[] func_151626_c(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_) {
        int var5 = p_151626_1_ - 1;
        int var6 = p_151626_2_ - 1;
        int var7 = 1 + p_151626_3_ + 1;
        int var8 = 1 + p_151626_4_ + 1;
        int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        int[] var10 = IntCache.getIntCache(p_151626_3_ * p_151626_4_);
        int var11 = 0;
        while (var11 < p_151626_4_) {
            int var12 = 0;
            while (var12 < p_151626_3_) {
                this.initChunkSeed(var12 + p_151626_1_, var11 + p_151626_2_);
                int var13 = var9[var12 + 1 + (var11 + 1) * var7];
                if (var13 == 1) {
                    boolean var19;
                    int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
                    int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
                    int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
                    int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
                    boolean var18 = var14 == 3 || var15 == 3 || var16 == 3 || var17 == 3;
                    boolean bl = var19 = var14 == 4 || var15 == 4 || var16 == 4 || var17 == 4;
                    if (var18 || var19) {
                        var13 = 2;
                    }
                }
                var10[var12 + var11 * p_151626_3_] = var13;
                ++var12;
            }
            ++var11;
        }
        return var10;
    }

    private int[] func_151624_d(int p_151624_1_, int p_151624_2_, int p_151624_3_, int p_151624_4_) {
        int var5 = p_151624_1_ - 1;
        int var6 = p_151624_2_ - 1;
        int var7 = 1 + p_151624_3_ + 1;
        int var8 = 1 + p_151624_4_ + 1;
        int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        int[] var10 = IntCache.getIntCache(p_151624_3_ * p_151624_4_);
        int var11 = 0;
        while (var11 < p_151624_4_) {
            int var12 = 0;
            while (var12 < p_151624_3_) {
                int var13 = var9[var12 + 1 + (var11 + 1) * var7];
                if (var13 == 4) {
                    boolean var19;
                    int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
                    int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
                    int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
                    int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
                    boolean var18 = var14 == 2 || var15 == 2 || var16 == 2 || var17 == 2;
                    boolean bl = var19 = var14 == 1 || var15 == 1 || var16 == 1 || var17 == 1;
                    if (var19 || var18) {
                        var13 = 3;
                    }
                }
                var10[var12 + var11 * p_151624_3_] = var13;
                ++var12;
            }
            ++var11;
        }
        return var10;
    }

    private int[] func_151625_e(int p_151625_1_, int p_151625_2_, int p_151625_3_, int p_151625_4_) {
        int[] var5 = this.parent.getInts(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
        int[] var6 = IntCache.getIntCache(p_151625_3_ * p_151625_4_);
        int var7 = 0;
        while (var7 < p_151625_4_) {
            int var8 = 0;
            while (var8 < p_151625_3_) {
                this.initChunkSeed(var8 + p_151625_1_, var7 + p_151625_2_);
                int var9 = var5[var8 + var7 * p_151625_3_];
                if (var9 != 0 && this.nextInt(13) == 0) {
                    var9 |= 1 + this.nextInt(15) << 8 & 3840;
                }
                var6[var8 + var7 * p_151625_3_] = var9;
                ++var8;
            }
            ++var7;
        }
        return var6;
    }

    public static enum Mode {
        COOL_WARM("COOL_WARM", 0),
        HEAT_ICE("HEAT_ICE", 1),
        SPECIAL("SPECIAL", 2);
        
        private static final Mode[] $VALUES;
        private static final String __OBFID = "CL_00000549";

        static {
            $VALUES = new Mode[]{COOL_WARM, HEAT_ICE, SPECIAL};
        }

        private Mode(String p_i45473_1_, int p_i45473_2_, String string2, int n2) {
        }
    }

    static final class SwitchMode {
        static final int[] field_151642_a = new int[Mode.values().length];
        private static final String __OBFID = "CL_00000548";

        static {
            try {
                SwitchMode.field_151642_a[Mode.COOL_WARM.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchMode.field_151642_a[Mode.HEAT_ICE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchMode.field_151642_a[Mode.SPECIAL.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchMode() {
        }
    }

}

