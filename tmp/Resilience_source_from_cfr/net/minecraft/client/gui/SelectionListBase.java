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
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class SelectionListBase {
    private final Minecraft field_148456_a;
    private final int field_148453_e;
    private final int field_148450_f;
    private final int field_148451_g;
    private final int field_148461_h;
    protected final int field_148454_b;
    protected int field_148455_c;
    protected int field_148452_d;
    private float field_148462_i = -2.0f;
    private float field_148459_j;
    private float field_148460_k;
    private int field_148457_l = -1;
    private long field_148458_m;
    private static final String __OBFID = "CL_00000789";

    public SelectionListBase(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6) {
        this.field_148456_a = par1Minecraft;
        this.field_148450_f = par3;
        this.field_148461_h = par3 + par5;
        this.field_148454_b = par6;
        this.field_148453_e = par2;
        this.field_148451_g = par2 + par4;
    }

    protected abstract int func_148443_a();

    protected abstract void func_148449_a(int var1, boolean var2);

    protected abstract boolean func_148444_a(int var1);

    protected int func_148447_b() {
        return this.func_148443_a() * this.field_148454_b;
    }

    protected abstract void func_148445_c();

    protected abstract void func_148442_a(int var1, int var2, int var3, int var4, Tessellator var5);

    private void func_148448_f() {
        int var1 = this.func_148441_d();
        if (var1 < 0) {
            var1 = 0;
        }
        if (this.field_148460_k < 0.0f) {
            this.field_148460_k = 0.0f;
        }
        if (this.field_148460_k > (float)var1) {
            this.field_148460_k = var1;
        }
    }

    public int func_148441_d() {
        return this.func_148447_b() - (this.field_148461_h - this.field_148450_f - 4);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void func_148446_a(int p_148446_1_, int p_148446_2_, float p_148446_3_) {
        block25 : {
            block24 : {
                this.field_148455_c = p_148446_1_;
                this.field_148452_d = p_148446_2_;
                this.func_148445_c();
                var4 = this.func_148443_a();
                var5 = this.func_148440_e();
                var6 = var5 + 6;
                if (!Mouse.isButtonDown((int)0)) ** GOTO lbl52
                if (this.field_148462_i != -1.0f) break block24;
                var7 = true;
                if (p_148446_2_ >= this.field_148450_f && p_148446_2_ <= this.field_148461_h) {
                    var8 = this.field_148453_e + 2;
                    var9 = this.field_148451_g - 2;
                    var10 = p_148446_2_ - this.field_148450_f + (int)this.field_148460_k - 4;
                    var11 = var10 / this.field_148454_b;
                    if (p_148446_1_ >= var8 && p_148446_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                        var12 = var11 == this.field_148457_l && Minecraft.getSystemTime() - this.field_148458_m < 250;
                        this.func_148449_a(var11, var12);
                        this.field_148457_l = var11;
                        this.field_148458_m = Minecraft.getSystemTime();
                    } else if (p_148446_1_ >= var8 && p_148446_1_ <= var9 && var10 < 0) {
                        var7 = false;
                    }
                    if (p_148446_1_ >= var5 && p_148446_1_ <= var6) {
                        this.field_148459_j = -1.0f;
                        var19 = this.func_148441_d();
                        if (var19 < 1) {
                            var19 = 1;
                        }
                        if ((var13 = (int)((float)((this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f)) / (float)this.func_148447_b())) < 32) {
                            var13 = 32;
                        }
                        if (var13 > this.field_148461_h - this.field_148450_f - 8) {
                            var13 = this.field_148461_h - this.field_148450_f - 8;
                        }
                        this.field_148459_j /= (float)(this.field_148461_h - this.field_148450_f - var13) / (float)var19;
                    } else {
                        this.field_148459_j = 1.0f;
                    }
                    this.field_148462_i = var7 ? (float)p_148446_2_ : -2.0f;
                } else {
                    this.field_148462_i = -2.0f;
                }
                break block25;
            }
            if (this.field_148462_i < 0.0f) break block25;
            this.field_148460_k -= ((float)p_148446_2_ - this.field_148462_i) * this.field_148459_j;
            this.field_148462_i = p_148446_2_;
            break block25;
lbl-1000: // 1 sources:
            {
                var16 = Mouse.getEventDWheel();
                if (var16 == 0) continue;
                if (var16 > 0) {
                    var16 = -1;
                } else if (var16 < 0) {
                    var16 = 1;
                }
                this.field_148460_k += (float)(var16 * this.field_148454_b / 2);
lbl52: // 3 sources:
                ** while (!this.field_148456_a.gameSettings.touchscreen && Mouse.next())
            }
lbl53: // 1 sources:
            this.field_148462_i = -1.0f;
        }
        this.func_148448_f();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        var18 = Tessellator.instance;
        this.field_148456_a.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var17 = 32.0f;
        var18.startDrawingQuads();
        var18.setColorOpaque_I(2105376);
        var18.addVertexWithUV(this.field_148453_e, this.field_148461_h, 0.0, (float)this.field_148453_e / var17, (float)(this.field_148461_h + (int)this.field_148460_k) / var17);
        var18.addVertexWithUV(this.field_148451_g, this.field_148461_h, 0.0, (float)this.field_148451_g / var17, (float)(this.field_148461_h + (int)this.field_148460_k) / var17);
        var18.addVertexWithUV(this.field_148451_g, this.field_148450_f, 0.0, (float)this.field_148451_g / var17, (float)(this.field_148450_f + (int)this.field_148460_k) / var17);
        var18.addVertexWithUV(this.field_148453_e, this.field_148450_f, 0.0, (float)this.field_148453_e / var17, (float)(this.field_148450_f + (int)this.field_148460_k) / var17);
        var18.draw();
        var9 = this.field_148453_e + 2;
        var10 = this.field_148450_f + 4 - (int)this.field_148460_k;
        var11 = 0;
        while (var11 < var4) {
            var19 = var10 + var11 * this.field_148454_b;
            var13 = this.field_148454_b - 4;
            if (var19 + this.field_148454_b <= this.field_148461_h && var19 - 4 >= this.field_148450_f) {
                if (this.func_148444_a(var11)) {
                    var14 = this.field_148453_e + 2;
                    var15 = this.field_148451_g - 2;
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
                this.func_148442_a(var11, var9, var19, var13, var18);
            }
            ++var11;
        }
        GL11.glDisable((int)2929);
        var20 = 4;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV(this.field_148453_e, this.field_148450_f + var20, 0.0, 0.0, 1.0);
        var18.addVertexWithUV(this.field_148451_g, this.field_148450_f + var20, 0.0, 1.0, 1.0);
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV(this.field_148451_g, this.field_148450_f, 0.0, 1.0, 0.0);
        var18.addVertexWithUV(this.field_148453_e, this.field_148450_f, 0.0, 0.0, 0.0);
        var18.draw();
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV(this.field_148453_e, this.field_148461_h, 0.0, 0.0, 1.0);
        var18.addVertexWithUV(this.field_148451_g, this.field_148461_h, 0.0, 1.0, 1.0);
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV(this.field_148451_g, this.field_148461_h - var20, 0.0, 1.0, 0.0);
        var18.addVertexWithUV(this.field_148453_e, this.field_148461_h - var20, 0.0, 0.0, 0.0);
        var18.draw();
        var19 = this.func_148441_d();
        if (var19 > 0) {
            var13 = (this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f) / this.func_148447_b();
            if (var13 < 32) {
                var13 = 32;
            }
            if (var13 > this.field_148461_h - this.field_148450_f - 8) {
                var13 = this.field_148461_h - this.field_148450_f - 8;
            }
            if ((var14 = (int)this.field_148460_k * (this.field_148461_h - this.field_148450_f - var13) / var19 + this.field_148450_f) < this.field_148450_f) {
                var14 = this.field_148450_f;
            }
            var18.startDrawingQuads();
            var18.setColorRGBA_I(0, 255);
            var18.addVertexWithUV(var5, this.field_148461_h, 0.0, 0.0, 1.0);
            var18.addVertexWithUV(var6, this.field_148461_h, 0.0, 1.0, 1.0);
            var18.addVertexWithUV(var6, this.field_148450_f, 0.0, 1.0, 0.0);
            var18.addVertexWithUV(var5, this.field_148450_f, 0.0, 0.0, 0.0);
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
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)3042);
    }

    protected int func_148440_e() {
        return this.field_148451_g - 8;
    }
}

