/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialTransparent
extends Material {
    private static final String __OBFID = "CL_00000540";

    public MaterialTransparent(MapColor par1MapColor) {
        super(par1MapColor);
        this.setReplaceable();
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

