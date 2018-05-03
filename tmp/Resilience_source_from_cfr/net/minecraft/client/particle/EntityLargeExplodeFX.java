/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityLargeExplodeFX
extends EntityFX {
    private static final ResourceLocation field_110127_a = new ResourceLocation("textures/entity/explosion.png");
    private int field_70581_a;
    private int field_70584_aq;
    private TextureManager theRenderEngine;
    private float field_70582_as;
    private static final String __OBFID = "CL_00000910";

    public EntityLargeExplodeFX(TextureManager par1TextureManager, World par2World, double par3, double par5, double par7, double par9, double par11, double par13) {
        super(par2World, par3, par5, par7, 0.0, 0.0, 0.0);
        this.theRenderEngine = par1TextureManager;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleRed = this.particleBlue;
        this.field_70582_as = 1.0f - (float)par9 * 0.5f;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        int var8 = (int)(((float)this.field_70581_a + par2) * 15.0f / (float)this.field_70584_aq);
        if (var8 <= 15) {
            this.theRenderEngine.bindTexture(field_110127_a);
            float var9 = (float)(var8 % 4) / 4.0f;
            float var10 = var9 + 0.24975f;
            float var11 = (float)(var8 / 4) / 4.0f;
            float var12 = var11 + 0.24975f;
            float var13 = 2.0f * this.field_70582_as;
            float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
            float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
            float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)2896);
            RenderHelper.disableStandardItemLighting();
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
            par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
            par1Tessellator.setBrightness(240);
            par1Tessellator.addVertexWithUV(var14 - par3 * var13 - par6 * var13, var15 - par4 * var13, var16 - par5 * var13 - par7 * var13, var10, var12);
            par1Tessellator.addVertexWithUV(var14 - par3 * var13 + par6 * var13, var15 + par4 * var13, var16 - par5 * var13 + par7 * var13, var10, var11);
            par1Tessellator.addVertexWithUV(var14 + par3 * var13 + par6 * var13, var15 + par4 * var13, var16 + par5 * var13 + par7 * var13, var9, var11);
            par1Tessellator.addVertexWithUV(var14 + par3 * var13 - par6 * var13, var15 - par4 * var13, var16 + par5 * var13 - par7 * var13, var9, var12);
            par1Tessellator.draw();
            GL11.glPolygonOffset((float)0.0f, (float)0.0f);
            GL11.glEnable((int)2896);
        }
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 61680;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_70581_a;
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}

