/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EffectRenderer {
    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
    protected World worldObj;
    private List[] fxLayers = new List[4];
    private TextureManager renderer;
    private Random rand = new Random();
    private static final String __OBFID = "CL_00000915";

    public EffectRenderer(World par1World, TextureManager par2TextureManager) {
        if (par1World != null) {
            this.worldObj = par1World;
        }
        this.renderer = par2TextureManager;
        int var3 = 0;
        while (var3 < 4) {
            this.fxLayers[var3] = new ArrayList();
            ++var3;
        }
    }

    public void addEffect(EntityFX par1EntityFX) {
        int var2 = par1EntityFX.getFXLayer();
        if (this.fxLayers[var2].size() >= 4000) {
            this.fxLayers[var2].remove(0);
        }
        this.fxLayers[var2].add(par1EntityFX);
    }

    public void updateEffects() {
        int var11 = 0;
        while (var11 < 4) {
            final int var1 = var11;
            int var2 = 0;
            while (var2 < this.fxLayers[var1].size()) {
                final EntityFX var3 = (EntityFX)this.fxLayers[var1].get(var2);
                try {
                    var3.onUpdate();
                }
                catch (Throwable var8) {
                    CrashReport var5 = CrashReport.makeCrashReport(var8, "Ticking Particle");
                    CrashReportCategory var6 = var5.makeCategory("Particle being ticked");
                    var6.addCrashSectionCallable("Particle", new Callable(){
                        private static final String __OBFID = "CL_00000916";

                        public String call() {
                            return var3.toString();
                        }
                    });
                    var6.addCrashSectionCallable("Particle Type", new Callable(){
                        private static final String __OBFID = "CL_00000917";

                        public String call() {
                            return var1 == 0 ? "MISC_TEXTURE" : (var1 == 1 ? "TERRAIN_TEXTURE" : (var1 == 2 ? "ITEM_TEXTURE" : (var1 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var1)));
                        }
                    });
                    throw new ReportedException(var5);
                }
                if (var3.isDead) {
                    this.fxLayers[var1].remove(var2--);
                }
                ++var2;
            }
            ++var11;
        }
    }

    public void renderParticles(Entity par1Entity, float par2) {
        float var3 = ActiveRenderInfo.rotationX;
        float var4 = ActiveRenderInfo.rotationZ;
        float var5 = ActiveRenderInfo.rotationYZ;
        float var6 = ActiveRenderInfo.rotationXY;
        float var7 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * (double)par2;
        EntityFX.interpPosY = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * (double)par2;
        EntityFX.interpPosZ = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * (double)par2;
        int var88 = 0;
        while (var88 < 3) {
            final int var8 = var88;
            if (!this.fxLayers[var8].isEmpty()) {
                switch (var8) {
                    default: {
                        this.renderer.bindTexture(particleTextures);
                        break;
                    }
                    case 1: {
                        this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                        break;
                    }
                    case 2: {
                        this.renderer.bindTexture(TextureMap.locationItemsTexture);
                    }
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDepthMask((boolean)false);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glAlphaFunc((int)516, (float)0.003921569f);
                Tessellator var9 = Tessellator.instance;
                var9.startDrawingQuads();
                int var10 = 0;
                while (var10 < this.fxLayers[var8].size()) {
                    final EntityFX var11 = (EntityFX)this.fxLayers[var8].get(var10);
                    var9.setBrightness(var11.getBrightnessForRender(par2));
                    try {
                        var11.renderParticle(var9, par2, var3, var7, var4, var5, var6);
                    }
                    catch (Throwable var16) {
                        CrashReport var13 = CrashReport.makeCrashReport(var16, "Rendering Particle");
                        CrashReportCategory var14 = var13.makeCategory("Particle being rendered");
                        var14.addCrashSectionCallable("Particle", new Callable(){
                            private static final String __OBFID = "CL_00000918";

                            public String call() {
                                return var11.toString();
                            }
                        });
                        var14.addCrashSectionCallable("Particle Type", new Callable(){
                            private static final String __OBFID = "CL_00000919";

                            public String call() {
                                return var8 == 0 ? "MISC_TEXTURE" : (var8 == 1 ? "TERRAIN_TEXTURE" : (var8 == 2 ? "ITEM_TEXTURE" : (var8 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var8)));
                            }
                        });
                        throw new ReportedException(var13);
                    }
                    ++var10;
                }
                var9.draw();
                GL11.glDisable((int)3042);
                GL11.glDepthMask((boolean)true);
                GL11.glAlphaFunc((int)516, (float)0.1f);
            }
            ++var88;
        }
    }

    public void renderLitParticles(Entity par1Entity, float par2) {
        float var3 = 0.017453292f;
        float var4 = MathHelper.cos(par1Entity.rotationYaw * 0.017453292f);
        float var5 = MathHelper.sin(par1Entity.rotationYaw * 0.017453292f);
        float var6 = (- var5) * MathHelper.sin(par1Entity.rotationPitch * 0.017453292f);
        float var7 = var4 * MathHelper.sin(par1Entity.rotationPitch * 0.017453292f);
        float var8 = MathHelper.cos(par1Entity.rotationPitch * 0.017453292f);
        int var9 = 3;
        List var10 = this.fxLayers[var9];
        if (!var10.isEmpty()) {
            Tessellator var11 = Tessellator.instance;
            int var12 = 0;
            while (var12 < var10.size()) {
                EntityFX var13 = (EntityFX)var10.get(var12);
                var11.setBrightness(var13.getBrightnessForRender(par2));
                var13.renderParticle(var11, par2, var4, var8, var5, var6, var7);
                ++var12;
            }
        }
    }

    public void clearEffects(World par1World) {
        this.worldObj = par1World;
        int var2 = 0;
        while (var2 < 4) {
            this.fxLayers[var2].clear();
            ++var2;
        }
    }

    public void func_147215_a(int p_147215_1_, int p_147215_2_, int p_147215_3_, Block p_147215_4_, int p_147215_5_) {
        if (p_147215_4_.getMaterial() != Material.air) {
            int var6 = 4;
            int var7 = 0;
            while (var7 < var6) {
                int var8 = 0;
                while (var8 < var6) {
                    int var9 = 0;
                    while (var9 < var6) {
                        double var10 = (double)p_147215_1_ + ((double)var7 + 0.5) / (double)var6;
                        double var12 = (double)p_147215_2_ + ((double)var8 + 0.5) / (double)var6;
                        double var14 = (double)p_147215_3_ + ((double)var9 + 0.5) / (double)var6;
                        this.addEffect(new EntityDiggingFX(this.worldObj, var10, var12, var14, var10 - (double)p_147215_1_ - 0.5, var12 - (double)p_147215_2_ - 0.5, var14 - (double)p_147215_3_ - 0.5, p_147215_4_, p_147215_5_).applyColourMultiplier(p_147215_1_, p_147215_2_, p_147215_3_));
                        ++var9;
                    }
                    ++var8;
                }
                ++var7;
            }
        }
    }

    public void addBlockHitEffects(int par1, int par2, int par3, int par4) {
        Block var5 = this.worldObj.getBlock(par1, par2, par3);
        if (var5.getMaterial() != Material.air) {
            float var6 = 0.1f;
            double var7 = (double)par1 + this.rand.nextDouble() * (var5.getBlockBoundsMaxX() - var5.getBlockBoundsMinX() - (double)(var6 * 2.0f)) + (double)var6 + var5.getBlockBoundsMinX();
            double var9 = (double)par2 + this.rand.nextDouble() * (var5.getBlockBoundsMaxY() - var5.getBlockBoundsMinY() - (double)(var6 * 2.0f)) + (double)var6 + var5.getBlockBoundsMinY();
            double var11 = (double)par3 + this.rand.nextDouble() * (var5.getBlockBoundsMaxZ() - var5.getBlockBoundsMinZ() - (double)(var6 * 2.0f)) + (double)var6 + var5.getBlockBoundsMinZ();
            if (par4 == 0) {
                var9 = (double)par2 + var5.getBlockBoundsMinY() - (double)var6;
            }
            if (par4 == 1) {
                var9 = (double)par2 + var5.getBlockBoundsMaxY() + (double)var6;
            }
            if (par4 == 2) {
                var11 = (double)par3 + var5.getBlockBoundsMinZ() - (double)var6;
            }
            if (par4 == 3) {
                var11 = (double)par3 + var5.getBlockBoundsMaxZ() + (double)var6;
            }
            if (par4 == 4) {
                var7 = (double)par1 + var5.getBlockBoundsMinX() - (double)var6;
            }
            if (par4 == 5) {
                var7 = (double)par1 + var5.getBlockBoundsMaxX() + (double)var6;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, var7, var9, var11, 0.0, 0.0, 0.0, var5, this.worldObj.getBlockMetadata(par1, par2, par3)).applyColourMultiplier(par1, par2, par3).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }

    public String getStatistics() {
        return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
    }

}

