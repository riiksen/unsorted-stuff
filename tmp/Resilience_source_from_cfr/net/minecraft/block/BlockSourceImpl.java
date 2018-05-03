/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSourceImpl
implements IBlockSource {
    private final World worldObj;
    private final int xPos;
    private final int yPos;
    private final int zPos;
    private static final String __OBFID = "CL_00001194";

    public BlockSourceImpl(World par1World, int par2, int par3, int par4) {
        this.worldObj = par1World;
        this.xPos = par2;
        this.yPos = par3;
        this.zPos = par4;
    }

    @Override
    public World getWorld() {
        return this.worldObj;
    }

    @Override
    public double getX() {
        return (double)this.xPos + 0.5;
    }

    @Override
    public double getY() {
        return (double)this.yPos + 0.5;
    }

    @Override
    public double getZ() {
        return (double)this.zPos + 0.5;
    }

    @Override
    public int getXInt() {
        return this.xPos;
    }

    @Override
    public int getYInt() {
        return this.yPos;
    }

    @Override
    public int getZInt() {
        return this.zPos;
    }

    @Override
    public int getBlockMetadata() {
        return this.worldObj.getBlockMetadata(this.xPos, this.yPos, this.zPos);
    }

    @Override
    public TileEntity getBlockTileEntity() {
        return this.worldObj.getTileEntity(this.xPos, this.yPos, this.zPos);
    }
}

