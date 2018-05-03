/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Gui {
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    public float zLevel;
    private static final String __OBFID = "CL_00000662";

    protected void drawHorizontalLine(int par1, int par2, int par3, int par4) {
        if (par2 < par1) {
            int var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        Gui.drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }

    protected void drawVerticalLine(int par1, int par2, int par3, int par4) {
        if (par3 < par2) {
            int var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        Gui.drawRect(par1, par2 + 1, par1 + 1, par3, par4);
    }

    public static void drawRect(int par0, int par1, int par2, int par3, int par4) {
        int var5;
        if (par0 < par2) {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        float var10 = (float)(par4 >> 24 & 255) / 255.0f;
        float var6 = (float)(par4 >> 16 & 255) / 255.0f;
        float var7 = (float)(par4 >> 8 & 255) / 255.0f;
        float var8 = (float)(par4 & 255) / 255.0f;
        Tessellator var9 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float)var6, (float)var7, (float)var8, (float)var10);
        var9.startDrawingQuads();
        var9.addVertex(par0, par3, 0.0);
        var9.addVertex(par2, par3, 0.0);
        var9.addVertex(par2, par1, 0.0);
        var9.addVertex(par0, par1, 0.0);
        var9.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = (float)(par5 >> 24 & 255) / 255.0f;
        float var8 = (float)(par5 >> 16 & 255) / 255.0f;
        float var9 = (float)(par5 >> 8 & 255) / 255.0f;
        float var10 = (float)(par5 & 255) / 255.0f;
        float var11 = (float)(par6 >> 24 & 255) / 255.0f;
        float var12 = (float)(par6 >> 16 & 255) / 255.0f;
        float var13 = (float)(par6 >> 8 & 255) / 255.0f;
        float var14 = (float)(par6 & 255) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel((int)7425);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(par3, par2, this.zLevel);
        var15.addVertex(par1, par2, this.zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(par1, par4, this.zLevel);
        var15.addVertex(par3, par4, this.zLevel);
        var15.draw();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
        par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }

    public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
        par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
    }

    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + par6, this.zLevel, (float)(par3 + 0) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + par6, this.zLevel, (float)(par3 + par5) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + 0, this.zLevel, (float)(par3 + par5) * var7, (float)(par4 + 0) * var8);
        var9.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (float)(par3 + 0) * var7, (float)(par4 + 0) * var8);
        var9.draw();
    }

    public void drawTexturedModelRectFromIcon(int par1, int par2, IIcon par3Icon, int par4, int par5) {
        Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(par1 + 0, par2 + par5, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + par5, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
        var6.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
        var6.draw();
    }

    public static void func_146110_a(int p_146110_0_, int p_146110_1_, float p_146110_2_, float p_146110_3_, int p_146110_4_, int p_146110_5_, float p_146110_6_, float p_146110_7_) {
        float var8 = 1.0f / p_146110_6_;
        float var9 = 1.0f / p_146110_7_;
        Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.addVertexWithUV(p_146110_0_, p_146110_1_ + p_146110_5_, 0.0, p_146110_2_ * var8, (p_146110_3_ + (float)p_146110_5_) * var9);
        var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_ + p_146110_5_, 0.0, (p_146110_2_ + (float)p_146110_4_) * var8, (p_146110_3_ + (float)p_146110_5_) * var9);
        var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_, 0.0, (p_146110_2_ + (float)p_146110_4_) * var8, p_146110_3_ * var9);
        var10.addVertexWithUV(p_146110_0_, p_146110_1_, 0.0, p_146110_2_ * var8, p_146110_3_ * var9);
        var10.draw();
    }
}

