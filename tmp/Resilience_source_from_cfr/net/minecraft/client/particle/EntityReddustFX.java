/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityReddustFX
extends EntityFX {
    float reddustParticleScale;
    private static final String __OBFID = "CL_00000923";

    public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10) {
        this(par1World, par2, par4, par6, 1.0f, par8, par9, par10);
    }

    public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        if (par9 == 0.0f) {
            par9 = 1.0f;
        }
        float var12 = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * par9 * var12;
        this.particleGreen = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * par10 * var12;
        this.particleBlue = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * par11 * var12;
        this.particleScale *= 0.75f;
        this.particleScale *= par8;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int)((float)this.particleMaxAge * par8);
        this.noClip = false;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0f;
        if (var8 < 0.0f) {
            var8 = 0.0f;
        }
        if (var8 > 1.0f) {
            var8 = 1.0f;
        }
        this.particleScale = this.reddustParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}

