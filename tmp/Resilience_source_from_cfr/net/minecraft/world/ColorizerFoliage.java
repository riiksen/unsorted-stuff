/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

public class ColorizerFoliage {
    private static int[] foliageBuffer = new int[65536];
    private static final String __OBFID = "CL_00000135";

    public static void setFoliageBiomeColorizer(int[] par0ArrayOfInteger) {
        foliageBuffer = par0ArrayOfInteger;
    }

    public static int getFoliageColor(double par0, double par2) {
        int var4 = (int)((1.0 - par0) * 255.0);
        int var5 = (int)((1.0 - (par2 *= par0)) * 255.0);
        return foliageBuffer[var5 << 8 | var4];
    }

    public static int getFoliageColorPine() {
        return 6396257;
    }

    public static int getFoliageColorBirch() {
        return 8431445;
    }

    public static int getFoliageColorBasic() {
        return 4764952;
    }
}

