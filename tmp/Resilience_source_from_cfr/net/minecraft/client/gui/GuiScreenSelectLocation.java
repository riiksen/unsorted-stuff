/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiScreenSelectLocation {
    private final Minecraft field_148368_a;
    private int field_148363_g;
    private int field_148375_h;
    protected int field_148366_b;
    protected int field_148367_c;
    private int field_148376_i;
    private int field_148373_j;
    protected final int field_148364_d;
    private int field_148374_k;
    private int field_148371_l;
    protected int field_148365_e;
    protected int field_148362_f;
    private float field_148372_m = -2.0f;
    private float field_148369_n;
    private float field_148370_o;
    private int field_148381_p = -1;
    private long field_148380_q;
    private boolean field_148379_r = true;
    private boolean field_148378_s;
    private int field_148377_t;
    private static final String __OBFID = "CL_00000785";

    public GuiScreenSelectLocation(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6) {
        this.field_148368_a = par1Minecraft;
        this.field_148363_g = par2;
        this.field_148375_h = par3;
        this.field_148366_b = par4;
        this.field_148367_c = par5;
        this.field_148364_d = par6;
        this.field_148373_j = 0;
        this.field_148376_i = par2;
    }

    public void func_148346_a(int p_148346_1_, int p_148346_2_, int p_148346_3_, int p_148346_4_) {
        this.field_148363_g = p_148346_1_;
        this.field_148375_h = p_148346_2_;
        this.field_148366_b = p_148346_3_;
        this.field_148367_c = p_148346_4_;
        this.field_148373_j = 0;
        this.field_148376_i = p_148346_1_;
    }

    protected abstract int func_148355_a();

    protected abstract void func_148352_a(int var1, boolean var2);

    protected abstract boolean func_148356_a(int var1);

    protected abstract boolean func_148349_b(int var1);

    protected int func_148351_b() {
        return this.func_148355_a() * this.field_148364_d + this.field_148377_t;
    }

    protected abstract void func_148358_c();

    protected abstract void func_148348_a(int var1, int var2, int var3, int var4, Tessellator var5);

    protected void func_148354_a(int p_148354_1_, int p_148354_2_, Tessellator p_148354_3_) {
    }

    protected void func_148359_a(int p_148359_1_, int p_148359_2_) {
    }

    protected void func_148353_b(int p_148353_1_, int p_148353_2_) {
    }

    private void func_148361_h() {
        int var1 = this.func_148347_d();
        if (var1 < 0) {
            var1 /= 2;
        }
        if (this.field_148370_o < 0.0f) {
            this.field_148370_o = 0.0f;
        }
        if (this.field_148370_o > (float)var1) {
            this.field_148370_o = var1;
        }
    }

    public int func_148347_d() {
        return this.func_148351_b() - (this.field_148367_c - this.field_148366_b - 4);
    }

    public void func_148357_a(GuiButton p_148357_1_) {
        if (p_148357_1_.enabled) {
            if (p_148357_1_.id == this.field_148374_k) {
                this.field_148370_o -= (float)(this.field_148364_d * 2 / 3);
                this.field_148372_m = -2.0f;
                this.func_148361_h();
            } else if (p_148357_1_.id == this.field_148371_l) {
                this.field_148370_o += (float)(this.field_148364_d * 2 / 3);
                this.field_148372_m = -2.0f;
                this.func_148361_h();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void func_148350_a(int p_148350_1_, int p_148350_2_, float p_148350_3_) {
        block27 : {
            block26 : {
                this.field_148365_e = p_148350_1_;
                this.field_148362_f = p_148350_2_;
                this.func_148358_c();
                var4 = this.func_148355_a();
                var5 = this.func_148360_g();
                var6 = var5 + 6;
                if (!Mouse.isButtonDown((int)0)) ** GOTO lbl53
                if (this.field_148372_m != -1.0f) break block26;
                var7 = true;
                if (p_148350_2_ >= this.field_148366_b && p_148350_2_ <= this.field_148367_c) {
                    var8 = this.field_148363_g / 2 - 110;
                    var9 = this.field_148363_g / 2 + 110;
                    var10 = p_148350_2_ - this.field_148366_b - this.field_148377_t + (int)this.field_148370_o - 4;
                    var11 = var10 / this.field_148364_d;
                    if (p_148350_1_ >= var8 && p_148350_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                        var12 = var11 == this.field_148381_p && Minecraft.getSystemTime() - this.field_148380_q < 250;
                        this.func_148352_a(var11, var12);
                        this.field_148381_p = var11;
                        this.field_148380_q = Minecraft.getSystemTime();
                    } else if (p_148350_1_ >= var8 && p_148350_1_ <= var9 && var10 < 0) {
                        this.func_148359_a(p_148350_1_ - var8, p_148350_2_ - this.field_148366_b + (int)this.field_148370_o - 4);
                        var7 = false;
                    }
                    if (p_148350_1_ >= var5 && p_148350_1_ <= var6) {
                        this.field_148369_n = -1.0f;
                        var19 = this.func_148347_d();
                        if (var19 < 1) {
                            var19 = 1;
                        }
                        if ((var13 = (int)((float)((this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b)) / (float)this.func_148351_b())) < 32) {
                            var13 = 32;
                        }
                        if (var13 > this.field_148367_c - this.field_148366_b - 8) {
                            var13 = this.field_148367_c - this.field_148366_b - 8;
                        }
                        this.field_148369_n /= (float)(this.field_148367_c - this.field_148366_b - var13) / (float)var19;
                    } else {
                        this.field_148369_n = 1.0f;
                    }
                    this.field_148372_m = var7 ? (float)p_148350_2_ : -2.0f;
                } else {
                    this.field_148372_m = -2.0f;
                }
                break block27;
            }
            if (this.field_148372_m < 0.0f) break block27;
            this.field_148370_o -= ((float)p_148350_2_ - this.field_148372_m) * this.field_148369_n;
            this.field_148372_m = p_148350_2_;
            break block27;
lbl-1000: // 1 sources:
            {
                var16 = Mouse.getEventDWheel();
                if (var16 == 0) continue;
                if (var16 > 0) {
                    var16 = -1;
                } else if (var16 < 0) {
                    var16 = 1;
                }
                this.field_148370_o += (float)(var16 * this.field_148364_d / 2);
lbl53: // 3 sources:
                ** while (!this.field_148368_a.gameSettings.touchscreen && Mouse.next())
            }
lbl54: // 1 sources:
            this.field_148372_m = -1.0f;
        }
        this.func_148361_h();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        var18 = Tessellator.instance;
        this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var17 = 32.0f;
        var18.startDrawingQuads();
        var18.setColorOpaque_I(2105376);
        var18.addVertexWithUV(this.field_148373_j, this.field_148367_c, 0.0, (float)this.field_148373_j / var17, (float)(this.field_148367_c + (int)this.field_148370_o) / var17);
        var18.addVertexWithUV(this.field_148376_i, this.field_148367_c, 0.0, (float)this.field_148376_i / var17, (float)(this.field_148367_c + (int)this.field_148370_o) / var17);
        var18.addVertexWithUV(this.field_148376_i, this.field_148366_b, 0.0, (float)this.field_148376_i / var17, (float)(this.field_148366_b + (int)this.field_148370_o) / var17);
        var18.addVertexWithUV(this.field_148373_j, this.field_148366_b, 0.0, (float)this.field_148373_j / var17, (float)(this.field_148366_b + (int)this.field_148370_o) / var17);
        var18.draw();
        var9 = this.field_148363_g / 2 - 92 - 16;
        var10 = this.field_148366_b + 4 - (int)this.field_148370_o;
        if (this.field_148378_s) {
            this.func_148354_a(var9, var10, var18);
        }
        var11 = 0;
        while (var11 < var4) {
            var19 = var10 + var11 * this.field_148364_d + this.field_148377_t;
            var13 = this.field_148364_d - 4;
            if (var19 <= this.field_148367_c && var19 + var13 >= this.field_148366_b) {
                if (this.field_148379_r && this.func_148349_b(var11)) {
                    var14 = this.field_148363_g / 2 - 110;
                    var15 = this.field_148363_g / 2 + 110;
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3553);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0, 0.0, 1.0);
                    var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0, 1.0, 1.0);
                    var18.addVertexWithUV(var15, var19 - 2, 0.0, 1.0, 0.0);
                    var18.addVertexWithUV(var14, var19 - 2, 0.0, 0.0, 0.0);
                    var18.draw();
                    GL11.glEnable((int)3553);
                }
                if (this.field_148379_r && this.func_148356_a(var11)) {
                    var14 = this.field_148363_g / 2 - 110;
                    var15 = this.field_148363_g / 2 + 110;
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3553);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(8421504);
                    var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0, 0.0, 1.0);
                    var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0, 1.0, 1.0);
                    var18.addVertexWithUV(var15, var19 - 2, 0.0, 1.0, 0.0);
                    var18.addVertexWithUV(var14, var19 - 2, 0.0, 0.0, 0.0);
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV(var14 + 1, var19 + var13 + 1, 0.0, 0.0, 1.0);
                    var18.addVertexWithUV(var15 - 1, var19 + var13 + 1, 0.0, 1.0, 1.0);
                    var18.addVertexWithUV(var15 - 1, var19 - 1, 0.0, 1.0, 0.0);
                    var18.addVertexWithUV(var14 + 1, var19 - 1, 0.0, 0.0, 0.0);
                    var18.draw();
                    GL11.glEnable((int)3553);
                }
                this.func_148348_a(var11, var9, var19, var13, var18);
            }
            ++var11;
        }
        GL11.glDisable((int)2929);
        var20 = 4;
        this.func_148345_b(0, this.field_148366_b, 255, 255);
        this.func_148345_b(this.field_148367_c, this.field_148375_h, 255, 255);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV(this.field_148373_j, this.field_148366_b + var20, 0.0, 0.0, 1.0);
        var18.addVertexWithUV(this.field_148376_i, this.field_148366_b + var20, 0.0, 1.0, 1.0);
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV(this.field_148376_i, this.field_148366_b, 0.0, 1.0, 0.0);
        var18.addVertexWithUV(this.field_148373_j, this.field_148366_b, 0.0, 0.0, 0.0);
        var18.draw();
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV(this.field_148373_j, this.field_148367_c, 0.0, 0.0, 1.0);
        var18.addVertexWithUV(this.field_148376_i, this.field_148367_c, 0.0, 1.0, 1.0);
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV(this.field_148376_i, this.field_148367_c - var20, 0.0, 1.0, 0.0);
        var18.addVertexWithUV(this.field_148373_j, this.field_148367_c - var20, 0.0, 0.0, 0.0);
        var18.draw();
        var19 = this.func_148347_d();
        if (var19 > 0) {
            var13 = (this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b) / this.func_148351_b();
            if (var13 < 32) {
                var13 = 32;
            }
            if (var13 > this.field_148367_c - this.field_148366_b - 8) {
                var13 = this.field_148367_c - this.field_148366_b - 8;
            }
            if ((var14 = (int)this.field_148370_o * (this.field_148367_c - this.field_148366_b - var13) / var19 + this.field_148366_b) < this.field_148366_b) {
                var14 = this.field_148366_b;
            }
            var18.startDrawingQuads();
            var18.setColorRGBA_I(0, 255);
            var18.addVertexWithUV(var5, this.field_148367_c, 0.0, 0.0, 1.0);
            var18.addVertexWithUV(var6, this.field_148367_c, 0.0, 1.0, 1.0);
            var18.addVertexWithUV(var6, this.field_148366_b, 0.0, 1.0, 0.0);
            var18.addVertexWithUV(var5, this.field_148366_b, 0.0, 0.0, 0.0);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(8421504, 255);
            var18.addVertexWithUV(var5, var14 + var13, 0.0, 0.0, 1.0);
            var18.addVertexWithUV(var6, var14 + var13, 0.0, 1.0, 1.0);
            var18.addVertexWithUV(var6, var14, 0.0, 1.0, 0.0);
            var18.addVertexWithUV(var5, var14, 0.0, 0.0, 0.0);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(12632256, 255);
            var18.addVertexWithUV(var5, var14 + var13 - 1, 0.0, 0.0, 1.0);
            var18.addVertexWithUV(var6 - 1, var14 + var13 - 1, 0.0, 1.0, 1.0);
            var18.addVertexWithUV(var6 - 1, var14, 0.0, 1.0, 0.0);
            var18.addVertexWithUV(var5, var14, 0.0, 0.0, 0.0);
            var18.draw();
        }
        this.func_148353_b(p_148350_1_, p_148350_2_);
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)3042);
    }

    protected int func_148360_g() {
        return this.field_148363_g / 2 + 124;
    }

    private void func_148345_b(int p_148345_1_, int p_148345_2_, int p_148345_3_, int p_148345_4_) {
        Tessellator var5 = Tessellator.instance;
        this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, p_148345_4_);
        var5.addVertexWithUV(0.0, p_148345_2_, 0.0, 0.0, (float)p_148345_2_ / var6);
        var5.addVertexWithUV(this.field_148363_g, p_148345_2_, 0.0, (float)this.field_148363_g / var6, (float)p_148345_2_ / var6);
        var5.setColorRGBA_I(4210752, p_148345_3_);
        var5.addVertexWithUV(this.field_148363_g, p_148345_1_, 0.0, (float)this.field_148363_g / var6, (float)p_148345_1_ / var6);
        var5.addVertexWithUV(0.0, p_148345_1_, 0.0, 0.0, (float)p_148345_1_ / var6);
        var5.draw();
    }
}

