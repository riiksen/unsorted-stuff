/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.IPosition;

public class PositionImpl
implements IPosition {
    protected final double x;
    protected final double y;
    protected final double z;
    private static final String __OBFID = "CL_00001208";

    public PositionImpl(double par1, double par3, double par5) {
        this.x = par1;
        this.y = par3;
        this.z = par5;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }
}

