/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntityDispenser;

public class TileEntityDropper
extends TileEntityDispenser {
    private static final String __OBFID = "CL_00000353";

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_146020_a : "container.dropper";
    }
}

