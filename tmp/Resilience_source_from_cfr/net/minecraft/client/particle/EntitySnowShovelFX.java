/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySnowShovelFX
extends EntityFX {
    float snowDigParticleScale;
    private static final String __OBFID = "CL_00000925";

    public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0f);
    }

    public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
        this.particleGreen = this.particleBlue = 1.0f - (float)(Math.random() * 0.30000001192092896);
        this.particleRed = this.particleBlue;
        this.particleScale *= 0.75f;
        this.particleScale *= par14;
        this.snowDigParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int)((float)this.particleMaxAge * par14);
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
        this.particleScale = this.snowDigParticleScale * var8;
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
        this.motionY -= 0.03;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9900000095367432;
        this.motionY *= 0.9900000095367432;
        this.motionZ *= 0.9900000095367432;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}

