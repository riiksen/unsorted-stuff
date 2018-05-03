/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

public class ColorizerGrass {
    private static int[] grassBuffer = new int[65536];
    private static final String __OBFID = "CL_00000138";

    public static void setGrassBiomeColorizer(int[] par0ArrayOfInteger) {
        grassBuffer = par0ArrayOfInteger;
    }

    public static int getGrassColor(double par0, double par2) {
        int var4 = (int)((1.0 - par0) * 255.0);
        int var5 = (int)((1.0 - (par2 *= par0)) * 255.0);
        return grassBuffer[var5 << 8 | var4];
    }
}

