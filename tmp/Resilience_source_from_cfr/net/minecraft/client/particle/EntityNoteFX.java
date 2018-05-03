/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNoteFX
extends EntityFX {
    float noteParticleScale;
    private static final String __OBFID = "CL_00000913";

    public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        this(par1World, par2, par4, par6, par8, par10, par12, 2.0f);
    }

    public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.009999999776482582;
        this.motionY *= 0.009999999776482582;
        this.motionZ *= 0.009999999776482582;
        this.motionY += 0.2;
        this.particleRed = MathHelper.sin(((float)par8 + 0.0f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.particleGreen = MathHelper.sin(((float)par8 + 0.33333334f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.particleBlue = MathHelper.sin(((float)par8 + 0.6666667f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.particleScale *= 0.75f;
        this.particleScale *= par14;
        this.noteParticleScale = this.particleScale;
        this.particleMaxAge = 6;
        this.noClip = false;
        this.setParticleTextureIndex(64);
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
        this.particleScale = this.noteParticleScale * var8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.6600000262260437;
        this.motionY *= 0.6600000262260437;
        this.motionZ *= 0.6600000262260437;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}

