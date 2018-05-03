/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.util.MathHelper;

public class Direction {
    public static final int[] offsetX;
    public static final int[] offsetZ;
    public static final String[] directions;
    public static final int[] directionToFacing;
    public static final int[] facingToDirection;
    public static final int[] rotateOpposite;
    public static final int[] rotateRight;
    public static final int[] rotateLeft;
    public static final int[][] bedDirection;
    private static final String __OBFID = "CL_00001506";

    static {
        int[] arrn = new int[4];
        arrn[1] = -1;
        arrn[3] = 1;
        offsetX = arrn;
        int[] arrn2 = new int[4];
        arrn2[0] = 1;
        arrn2[2] = -1;
        offsetZ = arrn2;
        directions = new String[]{"SOUTH", "WEST", "NORTH", "EAST"};
        directionToFacing = new int[]{3, 4, 2, 5};
        int[] arrn3 = new int[6];
        arrn3[0] = -1;
        arrn3[1] = -1;
        arrn3[2] = 2;
        arrn3[4] = 1;
        arrn3[5] = 3;
        facingToDirection = arrn3;
        int[] arrn4 = new int[4];
        arrn4[0] = 2;
        arrn4[1] = 3;
        arrn4[3] = 1;
        rotateOpposite = arrn4;
        int[] arrn5 = new int[4];
        arrn5[0] = 1;
        arrn5[1] = 2;
        arrn5[2] = 3;
        rotateRight = arrn5;
        int[] arrn6 = new int[4];
        arrn6[0] = 3;
        arrn6[2] = 1;
        arrn6[3] = 2;
        rotateLeft = arrn6;
        int[][] arrarrn = new int[4][];
        int[] arrn7 = new int[6];
        arrn7[0] = 1;
        arrn7[2] = 3;
        arrn7[3] = 2;
        arrn7[4] = 5;
        arrn7[5] = 4;
        arrarrn[0] = arrn7;
        int[] arrn8 = new int[6];
        arrn8[0] = 1;
        arrn8[2] = 5;
        arrn8[3] = 4;
        arrn8[4] = 2;
        arrn8[5] = 3;
        arrarrn[1] = arrn8;
        int[] arrn9 = new int[6];
        arrn9[0] = 1;
        arrn9[2] = 2;
        arrn9[3] = 3;
        arrn9[4] = 4;
        arrn9[5] = 5;
        arrarrn[2] = arrn9;
        int[] arrn10 = new int[6];
        arrn10[0] = 1;
        arrn10[2] = 4;
        arrn10[3] = 5;
        arrn10[4] = 3;
        arrn10[5] = 2;
        arrarrn[3] = arrn10;
        bedDirection = arrarrn;
    }

    public static int getMovementDirection(double par0, double par2) {
        return MathHelper.abs((float)par0) > MathHelper.abs((float)par2) ? (par0 > 0.0 ? 1 : 3) : (par2 > 0.0 ? 2 : 0);
    }
}

