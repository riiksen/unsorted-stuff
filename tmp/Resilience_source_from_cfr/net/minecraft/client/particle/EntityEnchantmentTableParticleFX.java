/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityEnchantmentTableParticleFX
extends EntityFX {
    private float field_70565_a;
    private double field_70568_aq;
    private double field_70567_ar;
    private double field_70566_as;
    private static final String __OBFID = "CL_00000902";

    public EntityEnchantmentTableParticleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.field_70568_aq = this.posX = par2;
        this.field_70567_ar = this.posY = par4;
        this.field_70566_as = this.posZ = par6;
        float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        this.field_70565_a = this.particleScale = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleGreen = this.particleBlue = 1.0f * var14;
        this.particleRed = this.particleBlue;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
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
        var3 *= var3;
        var3 *= var3;
        return var2 * (1.0f - var3) + var3;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var1 = (float)this.particleAge / (float)this.particleMaxAge;
        var1 = 1.0f - var1;
        float var2 = 1.0f - var1;
        var2 *= var2;
        var2 *= var2;
        this.posX = this.field_70568_aq + this.motionX * (double)var1;
        this.posY = this.field_70567_ar + this.motionY * (double)var1 - (double)(var2 * 1.2f);
        this.posZ = this.field_70566_as + this.motionZ * (double)var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
}

