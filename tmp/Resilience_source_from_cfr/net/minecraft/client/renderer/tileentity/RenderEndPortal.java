/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEndPortal
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random field_147527_e = new Random(31100);
    FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
    private static final String __OBFID = "CL_00000972";

    public void renderTileEntityAt(TileEntityEndPortal p_147524_1_, double p_147524_2_, double p_147524_4_, double p_147524_6_, float p_147524_8_) {
        float var9 = (float)this.field_147501_a.field_147560_j;
        float var10 = (float)this.field_147501_a.field_147561_k;
        float var11 = (float)this.field_147501_a.field_147558_l;
        GL11.glDisable((int)2896);
        field_147527_e.setSeed(31100);
        float var12 = 0.75f;
        int var13 = 0;
        while (var13 < 16) {
            GL11.glPushMatrix();
            float var14 = 16 - var13;
            float var15 = 0.0625f;
            float var16 = 1.0f / (var14 + 1.0f);
            if (var13 == 0) {
                this.bindTexture(field_147529_c);
                var16 = 0.1f;
                var14 = 65.0f;
                var15 = 0.125f;
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
            }
            if (var13 == 1) {
                this.bindTexture(field_147526_d);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)1, (int)1);
                var15 = 0.5f;
            }
            float var17 = (float)(- p_147524_4_ + (double)var12);
            float var18 = var17 + ActiveRenderInfo.objectY;
            float var19 = var17 + var14 + ActiveRenderInfo.objectY;
            float var20 = var18 / var19;
            GL11.glTranslatef((float)var9, (float)(var20 += (float)(p_147524_4_ + (double)var12)), (float)var11);
            GL11.glTexGeni((int)8192, (int)9472, (int)9217);
            GL11.glTexGeni((int)8193, (int)9472, (int)9217);
            GL11.glTexGeni((int)8194, (int)9472, (int)9217);
            GL11.glTexGeni((int)8195, (int)9472, (int)9216);
            GL11.glTexGen((int)8192, (int)9473, (FloatBuffer)this.func_147525_a(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen((int)8193, (int)9473, (FloatBuffer)this.func_147525_a(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen((int)8194, (int)9473, (FloatBuffer)this.func_147525_a(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen((int)8195, (int)9474, (FloatBuffer)this.func_147525_a(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable((int)3168);
            GL11.glEnable((int)3169);
            GL11.glEnable((int)3170);
            GL11.glEnable((int)3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)((float)(Minecraft.getSystemTime() % 700000) / 700000.0f), (float)0.0f);
            GL11.glScalef((float)var15, (float)var15, (float)var15);
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.0f);
            GL11.glRotatef((float)((float)(var13 * var13 * 4321 + var13 * 9) * 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)0.0f);
            GL11.glTranslatef((float)(- var9), (float)(- var11), (float)(- var10));
            var18 = var17 + ActiveRenderInfo.objectY;
            GL11.glTranslatef((float)(ActiveRenderInfo.objectX * var14 / var18), (float)(ActiveRenderInfo.objectZ * var14 / var18), (float)(- var10));
            Tessellator var23 = Tessellator.instance;
            var23.startDrawingQuads();
            var20 = field_147527_e.nextFloat() * 0.5f + 0.1f;
            float var21 = field_147527_e.nextFloat() * 0.5f + 0.4f;
            float var22 = field_147527_e.nextFloat() * 0.5f + 0.5f;
            if (var13 == 0) {
                var22 = 1.0f;
                var21 = 1.0f;
                var20 = 1.0f;
            }
            var23.setColorRGBA_F(var20 * var16, var21 * var16, var22 * var16, 1.0f);
            var23.addVertex(p_147524_2_, p_147524_4_ + (double)var12, p_147524_6_);
            var23.addVertex(p_147524_2_, p_147524_4_ + (double)var12, p_147524_6_ + 1.0);
            var23.addVertex(p_147524_2_ + 1.0, p_147524_4_ + (double)var12, p_147524_6_ + 1.0);
            var23.addVertex(p_147524_2_ + 1.0, p_147524_4_ + (double)var12, p_147524_6_);
            var23.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            ++var13;
        }
        GL11.glDisable((int)3042);
        GL11.glDisable((int)3168);
        GL11.glDisable((int)3169);
        GL11.glDisable((int)3170);
        GL11.glDisable((int)3171);
        GL11.glEnable((int)2896);
    }

    private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
        this.field_147528_b.clear();
        this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        this.field_147528_b.flip();
        return this.field_147528_b;
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEndPortal)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

