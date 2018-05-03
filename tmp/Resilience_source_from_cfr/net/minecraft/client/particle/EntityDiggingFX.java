/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityDiggingFX
extends EntityFX {
    private Block field_145784_a;
    private static final String __OBFID = "CL_00000932";

    public EntityDiggingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Block par14Block, int par15) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.field_145784_a = par14Block;
        this.setParticleIcon(par14Block.getIcon(0, par15));
        this.particleGravity = par14Block.blockParticleGravity;
        this.particleBlue = 0.6f;
        this.particleGreen = 0.6f;
        this.particleRed = 0.6f;
        this.particleScale /= 2.0f;
    }

    public EntityDiggingFX applyColourMultiplier(int par1, int par2, int par3) {
        if (this.field_145784_a == Blocks.grass) {
            return this;
        }
        int var4 = this.field_145784_a.colorMultiplier(this.worldObj, par1, par2, par3);
        this.particleRed *= (float)(var4 >> 16 & 255) / 255.0f;
        this.particleGreen *= (float)(var4 >> 8 & 255) / 255.0f;
        this.particleBlue *= (float)(var4 & 255) / 255.0f;
        return this;
    }

    public EntityDiggingFX applyRenderColor(int par1) {
        if (this.field_145784_a == Blocks.grass) {
            return this;
        }
        int var2 = this.field_145784_a.getRenderColor(par1);
        this.particleRed *= (float)(var2 >> 16 & 255) / 255.0f;
        this.particleGreen *= (float)(var2 >> 8 & 255) / 255.0f;
        this.particleBlue *= (float)(var2 & 255) / 255.0f;
        return this;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = ((float)this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float var9 = var8 + 0.015609375f;
        float var10 = ((float)this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float var11 = var10 + 0.015609375f;
        float var12 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            var8 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            var9 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            var10 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            var11 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        par1Tessellator.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
    }
}

