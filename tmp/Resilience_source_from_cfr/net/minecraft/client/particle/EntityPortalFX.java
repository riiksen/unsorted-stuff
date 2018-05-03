/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityPortalFX
extends EntityFX {
    private float portalParticleScale;
    private double portalPosX;
    private double portalPosY;
    private double portalPosZ;
    private static final String __OBFID = "CL_00000921";

    public EntityPortalFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.portalPosX = this.posX = par2;
        this.portalPosY = this.posY = par4;
        this.portalPosZ = this.posZ = par6;
        float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleGreen = this.particleBlue = 1.0f * var14;
        this.particleRed = this.particleBlue;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 8.0));
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = ((float)this.particleAge + par2) / (float)this.particleMaxAge;
        var8 = 1.0f - var8;
        var8 *= var8;
        var8 = 1.0f - var8;
        this.particleScale = this.portalParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    @Override
    public int getBrightnessForRender(float par1) {
        int var2 = super.getBrightnessForRender(par1);
        float var3 = (float)this.particleAge / (float)this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        int var4 = var2 & 255;
        int var5 = var2 >> 16 & 255;
        if ((var5 += (int)(var3 * 15.0f * 16.0f)) > 240) {
            var5 = 240;
        }
        return var4 | var5 << 16;
    }

    @Override
    public float getBrightness(float par1) {
        float var2 = super.getBrightness(par1);
        float var3 = (float)this.particleAge / (float)this.particleMaxAge;
        var3 = var3 * var3 * var3 * var3;
        return var2 * (1.0f - var3) + var3;
    }

    @Override
    public void onUpdate() {
        float var1;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var2 = var1 = (float)this.particleAge / (float)this.particleMaxAge;
        var1 = - var1 + var1 * var1 * 2.0f;
        var1 = 1.0f - var1;
        this.posX = this.portalPosX + this.motionX * (double)var1;
        this.posY = this.portalPosY + this.motionY * (double)var1 + (double)(1.0f - var2);
        this.posZ = this.portalPosZ + this.motionZ * (double)var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
}

