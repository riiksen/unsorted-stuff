/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public enum EnumFacing {
    DOWN(0, 1, 0, -1, 0),
    UP(1, 0, 0, 1, 0),
    NORTH(2, 3, 0, 0, -1),
    SOUTH(3, 2, 0, 0, 1),
    EAST(4, 5, -1, 0, 0),
    WEST(5, 4, 1, 0, 0);
    
    private final int order_a;
    private final int order_b;
    private final int frontOffsetX;
    private final int frontOffsetY;
    private final int frontOffsetZ;
    private static final EnumFacing[] faceList;
    private static final String __OBFID = "CL_00001201";

    static {
        faceList = new EnumFacing[6];
        EnumFacing[] var0 = EnumFacing.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            EnumFacing var3;
            EnumFacing.faceList[var3.order_a] = var3 = var0[var2];
            ++var2;
        }
    }

    private EnumFacing(String par3, int par4, int par5, int par6, int par7, int n2, int n3) {
        this.order_a = par3;
        this.order_b = par4;
        this.frontOffsetX = par5;
        this.frontOffsetY = par6;
        this.frontOffsetZ = par7;
    }

    public int getFrontOffsetX() {
        return this.frontOffsetX;
    }

    public int getFrontOffsetY() {
        return this.frontOffsetY;
    }

    public int getFrontOffsetZ() {
        return this.frontOffsetZ;
    }

    public static EnumFacing getFront(int par0) {
        return faceList[par0 % faceList.length];
    }
}

