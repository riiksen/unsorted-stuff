/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.BaseAttribute;

public class RangedAttribute
extends BaseAttribute {
    private final double minimumValue;
    private final double maximumValue;
    private String description;
    private static final String __OBFID = "CL_00001568";

    public RangedAttribute(String par1Str, double par2, double par4, double par6) {
        super(par1Str, par2);
        this.minimumValue = par4;
        this.maximumValue = par6;
        if (par4 > par6) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (par2 < par4) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (par2 > par6) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public RangedAttribute setDescription(String par1Str) {
        this.description = par1Str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public double clampValue(double par1) {
        if (par1 < this.minimumValue) {
            par1 = this.minimumValue;
        }
        if (par1 > this.maximumValue) {
            par1 = this.maximumValue;
        }
        return par1;
    }
}

