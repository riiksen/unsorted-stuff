/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LoadingScreenRenderer
implements IProgressUpdate {
    private String field_73727_a = "";
    private Minecraft mc;
    private String currentlyDisplayedText = "";
    private long field_73723_d = Minecraft.getSystemTime();
    private boolean field_73724_e;
    private ScaledResolution field_146587_f;
    private Framebuffer field_146588_g;
    private static final String __OBFID = "CL_00000655";

    public LoadingScreenRenderer(Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
        this.field_146587_f = new ScaledResolution(par1Minecraft.gameSettings, par1Minecraft.displayWidth, par1Minecraft.displayHeight);
        this.field_146588_g = new Framebuffer(this.field_146587_f.getScaledWidth(), this.field_146587_f.getScaledHeight(), false);
        this.field_146588_g.setFramebufferFilter(9728);
    }

    @Override
    public void resetProgressAndMessage(String par1Str) {
        this.field_73724_e = false;
        this.func_73722_d(par1Str);
    }

    @Override
    public void displayProgressMessage(String par1Str) {
        this.field_73724_e = true;
        this.func_73722_d(par1Str);
    }

    public void func_73722_d(String par1Str) {
        this.currentlyDisplayedText = par1Str;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {
            GL11.glClear((int)256);
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            if (OpenGlHelper.isFramebufferEnabled()) {
                int var2 = this.field_146587_f.getScaleFactor();
                GL11.glOrtho((double)0.0, (double)(this.field_146587_f.getScaledWidth() * var2), (double)(this.field_146587_f.getScaledHeight() * var2), (double)0.0, (double)100.0, (double)300.0);
            } else {
                ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                GL11.glOrtho((double)0.0, (double)var3.getScaledWidth_double(), (double)var3.getScaledHeight_double(), (double)0.0, (double)100.0, (double)300.0);
            }
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
        }
    }

    @Override
    public void resetProgresAndWorkingMessage(String par1Str) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {
            this.field_73723_d = 0;
            this.field_73727_a = par1Str;
            this.setLoadingProgress(-1);
            this.field_73723_d = 0;
        }
    }

    @Override
    public void setLoadingProgress(int par1) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {
            long var2 = Minecraft.getSystemTime();
            if (var2 - this.field_73723_d >= 100) {
                this.field_73723_d = var2;
                ScaledResolution var4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int var5 = var4.getScaleFactor();
                int var6 = var4.getScaledWidth();
                int var7 = var4.getScaledHeight();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferClear();
                } else {
                    GL11.glClear((int)256);
                }
                this.field_146588_g.bindFramebuffer(true);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    GL11.glOrtho((double)0.0, (double)var6, (double)var7, (double)0.0, (double)100.0, (double)300.0);
                } else {
                    GL11.glOrtho((double)0.0, (double)var4.getScaledWidth_double(), (double)var4.getScaledHeight_double(), (double)0.0, (double)100.0, (double)300.0);
                }
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GL11.glClear((int)16640);
                }
                Tessellator var8 = Tessellator.instance;
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                float var9 = 32.0f;
                var8.startDrawingQuads();
                var8.setColorOpaque_I(4210752);
                var8.addVertexWithUV(0.0, var7, 0.0, 0.0, (float)var7 / var9);
                var8.addVertexWithUV(var6, var7, 0.0, (float)var6 / var9, (float)var7 / var9);
                var8.addVertexWithUV(var6, 0.0, 0.0, (float)var6 / var9, 0.0);
                var8.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                var8.draw();
                if (par1 >= 0) {
                    int var10 = 100;
                    int var11 = 2;
                    int var12 = var6 / 2 - var10 / 2;
                    int var13 = var7 / 2 + 16;
                    GL11.glDisable((int)3553);
                    var8.startDrawingQuads();
                    var8.setColorOpaque_I(8421504);
                    var8.addVertex(var12, var13, 0.0);
                    var8.addVertex(var12, var13 + var11, 0.0);
                    var8.addVertex(var12 + var10, var13 + var11, 0.0);
                    var8.addVertex(var12 + var10, var13, 0.0);
                    var8.setColorOpaque_I(8454016);
                    var8.addVertex(var12, var13, 0.0);
                    var8.addVertex(var12, var13 + var11, 0.0);
                    var8.addVertex(var12 + par1, var13 + var11, 0.0);
                    var8.addVertex(var12 + par1, var13, 0.0);
                    var8.draw();
                    GL11.glEnable((int)3553);
                }
                GL11.glEnable((int)3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                this.mc.fontRenderer.drawStringWithShadow(this.currentlyDisplayedText, (var6 - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2, var7 / 2 - 4 - 16, 16777215);
                this.mc.fontRenderer.drawStringWithShadow(this.field_73727_a, (var6 - this.mc.fontRenderer.getStringWidth(this.field_73727_a)) / 2, var7 / 2 - 4 + 8, 16777215);
                this.field_146588_g.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferRender(var6 * var5, var7 * var5);
                }
                this.mc.func_147120_f();
                try {
                    Thread.yield();
                }
                catch (Exception var10) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void func_146586_a() {
    }
}

