/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBlockAccess {
    public Block getBlock(int var1, int var2, int var3);

    public TileEntity getTileEntity(int var1, int var2, int var3);

    public int getLightBrightnessForSkyBlocks(int var1, int var2, int var3, int var4);

    public int getBlockMetadata(int var1, int var2, int var3);

    public boolean isAirBlock(int var1, int var2, int var3);

    public BiomeGenBase getBiomeGenForCoords(int var1, int var2);

    public int getHeight();

    public boolean extendedLevelsInChunkCache();

    public Vec3Pool getWorldVec3Pool();

    public int isBlockProvidingPowerTo(int var1, int var2, int var3, int var4);
}

