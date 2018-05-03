/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityFishWakeFX
extends EntityFX {
    private static final String __OBFID = "CL_00000933";

    public EntityFishWakeFX(World p_i45073_1_, double p_i45073_2_, double p_i45073_4_, double p_i45073_6_, double p_i45073_8_, double p_i45073_10_, double p_i45073_12_) {
        super(p_i45073_1_, p_i45073_2_, p_i45073_4_, p_i45073_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = (float)Math.random() * 0.2f + 0.1f;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(19);
        this.setSize(0.01f, 0.01f);
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleGravity = 0.0f;
        this.motionX = p_i45073_8_;
        this.motionY = p_i45073_10_;
        this.motionZ = p_i45073_12_;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        int var1 = 60 - this.particleMaxAge;
        float var2 = (float)var1 * 0.001f;
        this.setSize(var2, var2);
        this.setParticleTextureIndex(19 + var1 % 4);
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}

