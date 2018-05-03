/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityDaylightDetector
extends TileEntity {
    private static final String __OBFID = "CL_00000350";

    @Override
    public void updateEntity() {
        if (this.worldObj != null && !this.worldObj.isClient && this.worldObj.getTotalWorldTime() % 20 == 0) {
            this.blockType = this.getBlockType();
            if (this.blockType instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector)this.blockType).func_149957_e(this.worldObj, this.field_145851_c, this.field_145848_d, this.field_145849_e);
            }
        }
    }
}

