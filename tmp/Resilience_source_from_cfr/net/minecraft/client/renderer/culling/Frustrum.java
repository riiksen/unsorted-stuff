/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.culling;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.AxisAlignedBB;

public class Frustrum
implements ICamera {
    private ClippingHelper clippingHelper = ClippingHelperImpl.getInstance();
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private static final String __OBFID = "CL_00000976";

    @Override
    public void setPosition(double par1, double par3, double par5) {
        this.xPosition = par1;
        this.yPosition = par3;
        this.zPosition = par5;
    }

    public boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11) {
        return this.clippingHelper.isBoxInFrustum(par1 - this.xPosition, par3 - this.yPosition, par5 - this.zPosition, par7 - this.xPosition, par9 - this.yPosition, par11 - this.zPosition);
    }

    @Override
    public boolean isBoundingBoxInFrustum(AxisAlignedBB par1AxisAlignedBB) {
        return this.isBoxInFrustum(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    }

    public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.clippingHelper.isBoxInFrustumFully(minX - this.xPosition, minY - this.yPosition, minZ - this.zPosition, maxX - this.xPosition, maxY - this.yPosition, maxZ - this.zPosition);
    }

    @Override
    public boolean isBoundingBoxInFrustumFully(AxisAlignedBB aab) {
        return this.isBoxInFrustumFully(aab.minX, aab.minY, aab.minZ, aab.maxX, aab.maxY, aab.maxZ);
    }
}

