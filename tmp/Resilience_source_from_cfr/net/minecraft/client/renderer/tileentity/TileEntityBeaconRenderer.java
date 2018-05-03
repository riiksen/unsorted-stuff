/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
    private static final String __OBFID = "CL_00000962";

    public void renderTileEntityAt(TileEntityBeacon p_147522_1_, double p_147522_2_, double p_147522_4_, double p_147522_6_, float p_147522_8_) {
        float var9 = p_147522_1_.func_146002_i();
        GL11.glAlphaFunc((int)516, (float)0.1f);
        if (var9 > 0.0f) {
            Tessellator var10 = Tessellator.instance;
            this.bindTexture(field_147523_b);
            GL11.glTexParameterf((int)3553, (int)10242, (float)10497.0f);
            GL11.glTexParameterf((int)3553, (int)10243, (float)10497.0f);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glDepthMask((boolean)true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float var11 = (float)p_147522_1_.getWorldObj().getTotalWorldTime() + p_147522_8_;
            float var12 = (- var11) * 0.2f - (float)MathHelper.floor_float((- var11) * 0.1f);
            boolean var13 = true;
            double var14 = (double)var11 * 0.025 * (1.0 - (double)(var13 & true) ? 1 : 0 * 2.5);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            double var16 = (double)var13 ? 1 : 0 * 0.2;
            double var18 = 0.5 + Math.cos(var14 + 2.356194490192345) * var16;
            double var20 = 0.5 + Math.sin(var14 + 2.356194490192345) * var16;
            double var22 = 0.5 + Math.cos(var14 + 0.7853981633974483) * var16;
            double var24 = 0.5 + Math.sin(var14 + 0.7853981633974483) * var16;
            double var26 = 0.5 + Math.cos(var14 + 3.9269908169872414) * var16;
            double var28 = 0.5 + Math.sin(var14 + 3.9269908169872414) * var16;
            double var30 = 0.5 + Math.cos(var14 + 5.497787143782138) * var16;
            double var32 = 0.5 + Math.sin(var14 + 5.497787143782138) * var16;
            double var34 = 256.0f * var9;
            double var36 = 0.0;
            double var38 = 1.0;
            double var40 = -1.0f + var12;
            double var42 = (double)(256.0f * var9) * (0.5 / var16) + var40;
            var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_ + var34, p_147522_6_ + var20, var38, var42);
            var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_, p_147522_6_ + var20, var38, var40);
            var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_, p_147522_6_ + var24, var36, var40);
            var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_ + var34, p_147522_6_ + var24, var36, var42);
            var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_ + var34, p_147522_6_ + var32, var38, var42);
            var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_, p_147522_6_ + var32, var38, var40);
            var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_, p_147522_6_ + var28, var36, var40);
            var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_ + var34, p_147522_6_ + var28, var36, var42);
            var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_ + var34, p_147522_6_ + var24, var38, var42);
            var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_, p_147522_6_ + var24, var38, var40);
            var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_, p_147522_6_ + var32, var36, var40);
            var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_ + var34, p_147522_6_ + var32, var36, var42);
            var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_ + var34, p_147522_6_ + var28, var38, var42);
            var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_, p_147522_6_ + var28, var38, var40);
            var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_, p_147522_6_ + var20, var36, var40);
            var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_ + var34, p_147522_6_ + var20, var36, var42);
            var10.draw();
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask((boolean)false);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            double var44 = 0.2;
            double var15 = 0.2;
            double var17 = 0.8;
            double var19 = 0.2;
            double var21 = 0.2;
            double var23 = 0.8;
            double var25 = 0.8;
            double var27 = 0.8;
            double var29 = 256.0f * var9;
            double var31 = 0.0;
            double var33 = 1.0;
            double var35 = -1.0f + var12;
            double var37 = (double)(256.0f * var9) + var35;
            var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_ + var29, p_147522_6_ + var15, var33, var37);
            var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_, p_147522_6_ + var15, var33, var35);
            var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_, p_147522_6_ + var19, var31, var35);
            var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_ + var29, p_147522_6_ + var19, var31, var37);
            var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_ + var29, p_147522_6_ + var27, var33, var37);
            var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_, p_147522_6_ + var27, var33, var35);
            var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_, p_147522_6_ + var23, var31, var35);
            var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_ + var29, p_147522_6_ + var23, var31, var37);
            var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_ + var29, p_147522_6_ + var19, var33, var37);
            var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_, p_147522_6_ + var19, var33, var35);
            var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_, p_147522_6_ + var27, var31, var35);
            var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_ + var29, p_147522_6_ + var27, var31, var37);
            var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_ + var29, p_147522_6_ + var23, var33, var37);
            var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_, p_147522_6_ + var23, var33, var35);
            var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_, p_147522_6_ + var15, var31, var35);
            var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_ + var29, p_147522_6_ + var15, var31, var37);
            var10.draw();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
        }
        GL11.glAlphaFunc((int)516, (float)0.5f);
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityBeacon)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

