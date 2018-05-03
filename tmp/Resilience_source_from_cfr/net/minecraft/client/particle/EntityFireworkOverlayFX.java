/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkOverlayFX
extends EntityFX {
    private static final String __OBFID = "CL_00000904";

    protected EntityFireworkOverlayFX(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        this.particleMaxAge = 4;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = 0.25f;
        float var9 = var8 + 0.25f;
        float var10 = 0.125f;
        float var11 = var10 + 0.25f;
        float var12 = 7.1f * MathHelper.sin(((float)this.particleAge + par2 - 1.0f) * 0.25f * 3.1415927f);
        this.particleAlpha = 0.6f - ((float)this.particleAge + par2 - 1.0f) * 0.25f * 0.5f;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
    }
}

