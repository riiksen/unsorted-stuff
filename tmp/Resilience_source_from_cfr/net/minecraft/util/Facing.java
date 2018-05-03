/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public class Facing {
    public static final int[] oppositeSide;
    public static final int[] offsetsXForSide;
    public static final int[] offsetsYForSide;
    public static final int[] offsetsZForSide;
    public static final String[] facings;
    private static final String __OBFID = "CL_00001532";

    static {
        int[] arrn = new int[6];
        arrn[0] = 1;
        arrn[2] = 3;
        arrn[3] = 2;
        arrn[4] = 5;
        arrn[5] = 4;
        oppositeSide = arrn;
        int[] arrn2 = new int[6];
        arrn2[4] = -1;
        arrn2[5] = 1;
        offsetsXForSide = arrn2;
        int[] arrn3 = new int[6];
        arrn3[0] = -1;
        arrn3[1] = 1;
        offsetsYForSide = arrn3;
        int[] arrn4 = new int[6];
        arrn4[2] = -1;
        arrn4[3] = 1;
        offsetsZForSide = arrn4;
        facings = new String[]{"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"};
    }
}

