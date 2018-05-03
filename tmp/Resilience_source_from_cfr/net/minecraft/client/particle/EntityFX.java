/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX
extends Entity {
    protected int particleTextureIndexX;
    protected int particleTextureIndexY;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleScale;
    protected float particleGravity;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    protected float particleAlpha = 1.0f;
    protected IIcon particleIcon;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;
    private static final String __OBFID = "CL_00000914";

    protected EntityFX(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.setSize(0.2f, 0.2f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.lastTickPosX = par2;
        this.lastTickPosY = par4;
        this.lastTickPosZ = par6;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }

    public EntityFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        this(par1World, par2, par4, par6);
        this.motionX = par8 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionY = par10 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionZ = par12 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        float var14 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)var15 * (double)var14 * 0.4000000059604645;
        this.motionY = this.motionY / (double)var15 * (double)var14 * 0.4000000059604645 + 0.10000000149011612;
        this.motionZ = this.motionZ / (double)var15 * (double)var14 * 0.4000000059604645;
    }

    public EntityFX multiplyVelocity(float par1) {
        this.motionX *= (double)par1;
        this.motionY = (this.motionY - 0.10000000149011612) * (double)par1 + 0.10000000149011612;
        this.motionZ *= (double)par1;
        return this;
    }

    public EntityFX multipleParticleScaleBy(float par1) {
        this.setSize(0.2f * par1, 0.2f * par1);
        this.particleScale *= par1;
        return this;
    }

    public void setRBGColorF(float par1, float par2, float par3) {
        this.particleRed = par1;
        this.particleGreen = par2;
        this.particleBlue = par3;
    }

    public void setAlphaF(float par1) {
        this.particleAlpha = par1;
    }

    public float getRedColorF() {
        return this.particleRed;
    }

    public float getGreenColorF() {
        return this.particleGreen;
    }

    public float getBlueColorF() {
        return this.particleBlue;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = (float)this.particleTextureIndexX / 16.0f;
        float var9 = var8 + 0.0624375f;
        float var10 = (float)this.particleTextureIndexY / 16.0f;
        float var11 = var10 + 0.0624375f;
        float var12 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            var8 = this.particleIcon.getMinU();
            var9 = this.particleIcon.getMaxU();
            var10 = this.particleIcon.getMinV();
            var11 = this.particleIcon.getMaxV();
        }
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
    }

    public int getFXLayer() {
        return 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    public void setParticleIcon(IIcon par1Icon) {
        if (this.getFXLayer() == 1) {
            this.particleIcon = par1Icon;
        } else {
            if (this.getFXLayer() != 2) {
                throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
            }
            this.particleIcon = par1Icon;
        }
    }

    public void setParticleTextureIndex(int par1) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }
        this.particleTextureIndexX = par1 % 16;
        this.particleTextureIndexY = par1 / 16;
    }

    public void nextTextureIndexX() {
        ++this.particleTextureIndexX;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getClass().getSimpleName()) + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
    }
}

