/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper
extends IInventory {
    public World getWorldObj();

    public double getXPos();

    public double getYPos();

    public double getZPos();
}

