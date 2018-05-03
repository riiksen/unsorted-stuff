/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityFlameFX
extends EntityFX {
    private float flameScale;
    private static final String __OBFID = "CL_00000907";

    public EntityFlameFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = this.motionX * 0.009999999776482582 + par8;
        this.motionY = this.motionY * 0.009999999776482582 + par10;
        this.motionZ = this.motionZ * 0.009999999776482582 + par12;
        double var10000 = par2 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = par4 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = par6 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.flameScale = this.particleScale;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = ((float)this.particleAge + par2) / (float)this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - var8 * var8 * 0.5f);
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    @Override
    public int getBrightnessForRender(float par1) {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 255;
        int var5 = var3 >> 16 & 255;
        if ((var4 += (int)(var2 * 15.0f * 16.0f)) > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }

    @Override
    public float getBrightness(float par1) {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        float var3 = super.getBrightness(par1);
        return var3 * var2 + (1.0f - var2);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}

