/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.ILocatableSource;
import net.minecraft.tileentity.TileEntity;

public interface IBlockSource
extends ILocatableSource {
    @Override
    public double getX();

    @Override
    public double getY();

    @Override
    public double getZ();

    public int getXInt();

    public int getYInt();

    public int getZInt();

    public int getBlockMetadata();

    public TileEntity getBlockTileEntity();
}

