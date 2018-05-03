/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;

public abstract class BaseAttribute
implements IAttribute {
    private final String unlocalizedName;
    private final double defaultValue;
    private boolean shouldWatch;
    private static final String __OBFID = "CL_00001565";

    protected BaseAttribute(String par1Str, double par2) {
        this.unlocalizedName = par1Str;
        this.defaultValue = par2;
        if (par1Str == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }

    @Override
    public String getAttributeUnlocalizedName() {
        return this.unlocalizedName;
    }

    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public boolean getShouldWatch() {
        return this.shouldWatch;
    }

    public BaseAttribute setShouldWatch(boolean par1) {
        this.shouldWatch = par1;
        return this;
    }

    public int hashCode() {
        return this.unlocalizedName.hashCode();
    }
}

