/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer;

import net.minecraft.util.IIcon;

public class IconFlipped
implements IIcon {
    private final IIcon baseIcon;
    private final boolean flipU;
    private final boolean flipV;
    private static final String __OBFID = "CL_00001511";

    public IconFlipped(IIcon par1Icon, boolean par2, boolean par3) {
        this.baseIcon = par1Icon;
        this.flipU = par2;
        this.flipV = par3;
    }

    @Override
    public int getIconWidth() {
        return this.baseIcon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.baseIcon.getIconHeight();
    }

    @Override
    public float getMinU() {
        return this.flipU ? this.baseIcon.getMaxU() : this.baseIcon.getMinU();
    }

    @Override
    public float getMaxU() {
        return this.flipU ? this.baseIcon.getMinU() : this.baseIcon.getMaxU();
    }

    @Override
    public float getInterpolatedU(double par1) {
        float var3 = this.getMaxU() - this.getMinU();
        return this.getMinU() + var3 * ((float)par1 / 16.0f);
    }

    @Override
    public float getMinV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMinV();
    }

    @Override
    public float getMaxV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMaxV();
    }

    @Override
    public float getInterpolatedV(double par1) {
        float var3 = this.getMaxV() - this.getMinV();
        return this.getMinV() + var3 * ((float)par1 / 16.0f);
    }

    @Override
    public String getIconName() {
        return this.baseIcon.getIconName();
    }
}

