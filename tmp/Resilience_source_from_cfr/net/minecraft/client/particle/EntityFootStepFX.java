/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityFootStepFX
extends EntityFX {
    private static final ResourceLocation field_110126_a = new ResourceLocation("textures/particle/footprint.png");
    private int footstepAge;
    private int footstepMaxAge;
    private TextureManager currentFootSteps;
    private static final String __OBFID = "CL_00000908";

    public EntityFootStepFX(TextureManager par1TextureManager, World par2World, double par3, double par5, double par7) {
        super(par2World, par3, par5, par7, 0.0, 0.0, 0.0);
        this.currentFootSteps = par1TextureManager;
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        this.footstepMaxAge = 200;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var9;
        float var8 = ((float)this.footstepAge + par2) / (float)this.footstepMaxAge;
        if ((var9 = 2.0f - (var8 *= var8) * 2.0f) > 1.0f) {
            var9 = 1.0f;
        }
        GL11.glDisable((int)2896);
        float var10 = 0.125f;
        float var11 = (float)(this.posX - interpPosX);
        float var12 = (float)(this.posY - interpPosY);
        float var13 = (float)(this.posZ - interpPosZ);
        float var14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        this.currentFootSteps.bindTexture(field_110126_a);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorRGBA_F(var14, var14, var14, var9 *= 0.2f);
        par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 + var10, 0.0, 1.0);
        par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 + var10, 1.0, 1.0);
        par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 - var10, 1.0, 0.0);
        par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 - var10, 0.0, 0.0);
        par1Tessellator.draw();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
    }

    @Override
    public void onUpdate() {
        ++this.footstepAge;
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}

