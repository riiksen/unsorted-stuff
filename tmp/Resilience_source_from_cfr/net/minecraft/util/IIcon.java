/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public interface IIcon {
    public int getIconWidth();

    public int getIconHeight();

    public float getMinU();

    public float getMaxU();

    public float getInterpolatedU(double var1);

    public float getMinV();

    public float getMaxV();

    public float getInterpolatedV(double var1);

    public String getIconName();
}

