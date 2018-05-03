/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialLiquid
extends Material {
    private static final String __OBFID = "CL_00000541";

    public MaterialLiquid(MapColor par1MapColor) {
        super(par1MapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }

    @Override
    public boolean isLiquid() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}

