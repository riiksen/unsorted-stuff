/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLightningBolt
extends Render {
    private static final String __OBFID = "CL_00001011";

    public void doRender(EntityLightningBolt par1EntityLightningBolt, double par2, double par4, double par6, float par8, float par9) {
        Tessellator var10 = Tessellator.instance;
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)1);
        double[] var11 = new double[8];
        double[] var12 = new double[8];
        double var13 = 0.0;
        double var15 = 0.0;
        Random var17 = new Random(par1EntityLightningBolt.boltVertex);
        int var18 = 7;
        while (var18 >= 0) {
            var11[var18] = var13;
            var12[var18] = var15;
            var13 += (double)(var17.nextInt(11) - 5);
            var15 += (double)(var17.nextInt(11) - 5);
            --var18;
        }
        int var45 = 0;
        while (var45 < 4) {
            Random var46 = new Random(par1EntityLightningBolt.boltVertex);
            int var19 = 0;
            while (var19 < 3) {
                int var20 = 7;
                int var21 = 0;
                if (var19 > 0) {
                    var20 = 7 - var19;
                }
                if (var19 > 0) {
                    var21 = var20 - 2;
                }
                double var22 = var11[var20] - var13;
                double var24 = var12[var20] - var15;
                int var26 = var20;
                while (var26 >= var21) {
                    double var27 = var22;
                    double var29 = var24;
                    if (var19 == 0) {
                        var22 += (double)(var46.nextInt(11) - 5);
                        var24 += (double)(var46.nextInt(11) - 5);
                    } else {
                        var22 += (double)(var46.nextInt(31) - 15);
                        var24 += (double)(var46.nextInt(31) - 15);
                    }
                    var10.startDrawing(5);
                    float var31 = 0.5f;
                    var10.setColorRGBA_F(0.9f * var31, 0.9f * var31, 1.0f * var31, 0.3f);
                    double var32 = 0.1 + (double)var45 * 0.2;
                    if (var19 == 0) {
                        var32 *= (double)var26 * 0.1 + 1.0;
                    }
                    double var34 = 0.1 + (double)var45 * 0.2;
                    if (var19 == 0) {
                        var34 *= (double)(var26 - 1) * 0.1 + 1.0;
                    }
                    int var36 = 0;
                    while (var36 < 5) {
                        double var37 = par2 + 0.5 - var32;
                        double var39 = par6 + 0.5 - var32;
                        if (var36 == 1 || var36 == 2) {
                            var37 += var32 * 2.0;
                        }
                        if (var36 == 2 || var36 == 3) {
                            var39 += var32 * 2.0;
                        }
                        double var41 = par2 + 0.5 - var34;
                        double var43 = par6 + 0.5 - var34;
                        if (var36 == 1 || var36 == 2) {
                            var41 += var34 * 2.0;
                        }
                        if (var36 == 2 || var36 == 3) {
                            var43 += var34 * 2.0;
                        }
                        var10.addVertex(var41 + var22, par4 + (double)(var26 * 16), var43 + var24);
                        var10.addVertex(var37 + var27, par4 + (double)((var26 + 1) * 16), var39 + var29);
                        ++var36;
                    }
                    var10.draw();
                    --var26;
                }
                ++var19;
            }
            ++var45;
        }
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
    }

    protected ResourceLocation getEntityTexture(EntityLightningBolt par1EntityLightningBolt) {
        return null;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityLightningBolt)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLightningBolt)par1Entity, par2, par4, par6, par8, par9);
    }
}

