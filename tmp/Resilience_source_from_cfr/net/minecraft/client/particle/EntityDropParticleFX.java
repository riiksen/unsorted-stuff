/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticleFX
extends EntityFX {
    private Material materialType;
    private int bobTimer;
    private static final String __OBFID = "CL_00000901";

    public EntityDropParticleFX(World par1World, double par2, double par4, double par6, Material par8Material) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        if (par8Material == Material.water) {
            this.particleRed = 0.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 1.0f;
        } else {
            this.particleRed = 1.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 0.0f;
        }
        this.setParticleTextureIndex(113);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.materialType = par8Material;
        this.bobTimer = 40;
        this.particleMaxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return this.materialType == Material.water ? super.getBrightnessForRender(par1) : 257;
    }

    @Override
    public float getBrightness(float par1) {
        return this.materialType == Material.water ? super.getBrightness(par1) : 1.0f;
    }

    @Override
    public void onUpdate() {
        double var2;
        Material var1;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.materialType == Material.water) {
            this.particleRed = 0.2f;
            this.particleGreen = 0.3f;
            this.particleBlue = 1.0f;
        } else {
            this.particleRed = 1.0f;
            this.particleGreen = 16.0f / (float)(40 - this.bobTimer + 16);
            this.particleBlue = 4.0f / (float)(40 - this.bobTimer + 8);
        }
        this.motionY -= (double)this.particleGravity;
        if (this.bobTimer-- > 0) {
            this.motionX *= 0.02;
            this.motionY *= 0.02;
            this.motionZ *= 0.02;
            this.setParticleTextureIndex(113);
        } else {
            this.setParticleTextureIndex(112);
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (this.materialType == Material.water) {
                this.setDead();
                this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            } else {
                this.setParticleTextureIndex(114);
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        if (((var1 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial()).isLiquid() || var1.isSolid()) && this.posY < (var2 = (double)((float)(MathHelper.floor_double(this.posY) + 1) - BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))))) {
            this.setDead();
        }
    }
}

