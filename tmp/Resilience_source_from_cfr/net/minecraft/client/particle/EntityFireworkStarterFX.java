/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkOverlayFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkStarterFX
extends EntityFX {
    private int fireworkAge;
    private final EffectRenderer theEffectRenderer;
    private NBTTagList fireworkExplosions;
    boolean twinkle;
    private static final String __OBFID = "CL_00000906";

    public EntityFireworkStarterFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer, NBTTagCompound par15NBTTagCompound) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.theEffectRenderer = par14EffectRenderer;
        this.particleMaxAge = 8;
        if (par15NBTTagCompound != null) {
            this.fireworkExplosions = par15NBTTagCompound.getTagList("Explosions", 10);
            if (this.fireworkExplosions.tagCount() == 0) {
                this.fireworkExplosions = null;
            } else {
                this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
                int var16 = 0;
                while (var16 < this.fireworkExplosions.tagCount()) {
                    NBTTagCompound var17 = this.fireworkExplosions.getCompoundTagAt(var16);
                    if (var17.getBoolean("Flicker")) {
                        this.twinkle = true;
                        this.particleMaxAge += 15;
                        break;
                    }
                    ++var16;
                }
            }
        }
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
    }

    @Override
    public void onUpdate() {
        boolean var1;
        if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
            var1 = this.func_92037_i();
            boolean var2 = false;
            if (this.fireworkExplosions.tagCount() >= 3) {
                var2 = true;
            } else {
                int var3 = 0;
                while (var3 < this.fireworkExplosions.tagCount()) {
                    NBTTagCompound var4 = this.fireworkExplosions.getCompoundTagAt(var3);
                    if (var4.getByte("Type") == 1) {
                        var2 = true;
                        break;
                    }
                    ++var3;
                }
            }
            String var15 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var15, 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, true);
        }
        if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
            int var13 = this.fireworkAge / 2;
            NBTTagCompound var14 = this.fireworkExplosions.getCompoundTagAt(var13);
            byte var17 = var14.getByte("Type");
            boolean var18 = var14.getBoolean("Trail");
            boolean var5 = var14.getBoolean("Flicker");
            int[] var6 = var14.getIntArray("Colors");
            int[] var7 = var14.getIntArray("FadeColors");
            if (var17 == 1) {
                this.createBall(0.5, 4, var6, var7, var18, var5);
            } else if (var17 == 2) {
                this.createShaped(0.5, new double[][]{{0.0, 1.0}, {0.3455, 0.309}, {0.9511, 0.309}, {0.3795918367346939, -0.12653061224489795}, {0.6122448979591837, -0.8040816326530612}, {0.0, -0.35918367346938773}}, var6, var7, var18, var5, false);
            } else if (var17 == 3) {
                this.createShaped(0.5, new double[][]{{0.0, 0.2}, {0.2, 0.2}, {0.2, 0.6}, {0.6, 0.6}, {0.6, 0.2}, {0.2, 0.2}, {0.2, 0.0}, {0.4, 0.0}, {0.4, -0.6}, {0.2, -0.6}, {0.2, -0.4}, {0.0, -0.4}}, var6, var7, var18, var5, true);
            } else if (var17 == 4) {
                this.createBurst(var6, var7, var18, var5);
            } else {
                this.createBall(0.25, 2, var6, var7, var18, var5);
            }
            int var8 = var6[0];
            float var9 = (float)((var8 & 16711680) >> 16) / 255.0f;
            float var10 = (float)((var8 & 65280) >> 8) / 255.0f;
            float var11 = (float)((var8 & 255) >> 0) / 255.0f;
            EntityFireworkOverlayFX var12 = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
            var12.setRBGColorF(var9, var10, var11);
            this.theEffectRenderer.addEffect(var12);
        }
        ++this.fireworkAge;
        if (this.fireworkAge > this.particleMaxAge) {
            if (this.twinkle) {
                var1 = this.func_92037_i();
                String var16 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
                this.worldObj.playSound(this.posX, this.posY, this.posZ, var16, 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, true);
            }
            this.setDead();
        }
    }

    private boolean func_92037_i() {
        Minecraft var1 = Minecraft.getMinecraft();
        if (var1 != null && var1.renderViewEntity != null && var1.renderViewEntity.getDistanceSq(this.posX, this.posY, this.posZ) < 256.0) {
            return false;
        }
        return true;
    }

    private void createParticle(double par1, double par3, double par5, double par7, double par9, double par11, int[] par13ArrayOfInteger, int[] par14ArrayOfInteger, boolean par15, boolean par16) {
        EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.worldObj, par1, par3, par5, par7, par9, par11, this.theEffectRenderer);
        var17.setTrail(par15);
        var17.setTwinkle(par16);
        int var18 = this.rand.nextInt(par13ArrayOfInteger.length);
        var17.setColour(par13ArrayOfInteger[var18]);
        if (par14ArrayOfInteger != null && par14ArrayOfInteger.length > 0) {
            var17.setFadeColour(par14ArrayOfInteger[this.rand.nextInt(par14ArrayOfInteger.length)]);
        }
        this.theEffectRenderer.addEffect(var17);
    }

    private void createBall(double par1, int par3, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7) {
        double var8 = this.posX;
        double var10 = this.posY;
        double var12 = this.posZ;
        int var14 = - par3;
        while (var14 <= par3) {
            int var15 = - par3;
            while (var15 <= par3) {
                int var16 = - par3;
                while (var16 <= par3) {
                    double var17 = (double)var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    double var19 = (double)var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    double var21 = (double)var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    double var23 = (double)MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / par1 + this.rand.nextGaussian() * 0.05;
                    this.createParticle(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
                    if (var14 != - par3 && var14 != par3 && var15 != - par3 && var15 != par3) {
                        var16 += par3 * 2 - 1;
                    }
                    ++var16;
                }
                ++var15;
            }
            ++var14;
        }
    }

    private void createShaped(double par1, double[][] par3ArrayOfDouble, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7, boolean par8) {
        double var9 = par3ArrayOfDouble[0][0];
        double var11 = par3ArrayOfDouble[0][1];
        this.createParticle(this.posX, this.posY, this.posZ, var9 * par1, var11 * par1, 0.0, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
        float var13 = this.rand.nextFloat() * 3.1415927f;
        double var14 = par8 ? 0.034 : 0.34;
        int var16 = 0;
        while (var16 < 3) {
            double var17 = (double)var13 + (double)((float)var16 * 3.1415927f) * var14;
            double var19 = var9;
            double var21 = var11;
            int var23 = 1;
            while (var23 < par3ArrayOfDouble.length) {
                double var24 = par3ArrayOfDouble[var23][0];
                double var26 = par3ArrayOfDouble[var23][1];
                double var28 = 0.25;
                while (var28 <= 1.0) {
                    double var30 = (var19 + (var24 - var19) * var28) * par1;
                    double var32 = (var21 + (var26 - var21) * var28) * par1;
                    double var34 = var30 * Math.sin(var17);
                    var30 *= Math.cos(var17);
                    double var36 = -1.0;
                    while (var36 <= 1.0) {
                        this.createParticle(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
                        var36 += 2.0;
                    }
                    var28 += 0.25;
                }
                var19 = var24;
                var21 = var26;
                ++var23;
            }
            ++var16;
        }
    }

    private void createBurst(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, boolean par3, boolean par4) {
        double var5 = this.rand.nextGaussian() * 0.05;
        double var7 = this.rand.nextGaussian() * 0.05;
        int var9 = 0;
        while (var9 < 70) {
            double var10 = this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + var5;
            double var12 = this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + var7;
            double var14 = this.motionY * 0.5 + this.rand.nextDouble() * 0.5;
            this.createParticle(this.posX, this.posY, this.posZ, var10, var14, var12, par1ArrayOfInteger, par2ArrayOfInteger, par3, par4);
            ++var9;
        }
    }

    @Override
    public int getFXLayer() {
        return 0;
    }
}

