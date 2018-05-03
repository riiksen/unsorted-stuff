/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialPortal
extends Material {
    private static final String __OBFID = "CL_00000545";

    public MaterialPortal(MapColor par1MapColor) {
        super(par1MapColor);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean getCanBlockGrass() {
        return false;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}

