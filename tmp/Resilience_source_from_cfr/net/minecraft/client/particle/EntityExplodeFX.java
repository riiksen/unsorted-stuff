/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityExplodeFX
extends EntityFX {
    private static final String __OBFID = "CL_00000903";

    public EntityExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = par8 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.motionY = par10 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.motionZ = par12 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleRed = this.particleBlue;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int)(16.0 / ((double)this.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8999999761581421;
        this.motionY *= 0.8999999761581421;
        this.motionZ *= 0.8999999761581421;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}

