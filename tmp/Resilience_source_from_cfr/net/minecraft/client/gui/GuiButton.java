/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButton
extends Gui {
    protected static final ResourceLocation field_146122_a = new ResourceLocation("textures/gui/widgets.png");
    protected int field_146120_f = 200;
    protected int field_146121_g = 20;
    public int field_146128_h;
    public int field_146129_i;
    public String displayString;
    public int id;
    public boolean enabled = true;
    public boolean field_146125_m = true;
    protected boolean field_146123_n;
    private static final String __OBFID = "CL_00000668";

    public GuiButton(int par1, int par2, int par3, String par4Str) {
        this(par1, par2, par3, 200, 20, par4Str);
    }

    public GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        this.id = par1;
        this.field_146128_h = par2;
        this.field_146129_i = par3;
        this.field_146120_f = par4;
        this.field_146121_g = par5;
        this.displayString = par6Str;
    }

    protected int getHoverState(boolean p_146114_1_) {
        int var2 = 1;
        if (!this.enabled) {
            var2 = 0;
        } else if (p_146114_1_) {
            var2 = 2;
        }
        return var2;
    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        if (this.field_146125_m) {
            FontRenderer var4 = p_146112_1_.fontRenderer;
            p_146112_1_.getTextureManager().bindTexture(field_146122_a);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.field_146123_n = p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g;
            int var5 = this.getHoverState(this.field_146123_n);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc((int)770, (int)771);
            this.drawTexturedModalRect(this.field_146128_h, this.field_146129_i, 0, 46 + var5 * 20, this.field_146120_f / 2, this.field_146121_g);
            this.drawTexturedModalRect(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + var5 * 20, this.field_146120_f / 2, this.field_146121_g);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int var6 = 14737632;
            if (!this.enabled) {
                var6 = 10526880;
            } else if (this.field_146123_n) {
                var6 = 16777120;
            }
            this.drawCenteredString(var4, this.displayString, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, var6);
        }
    }

    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
    }

    public void mouseReleased(int p_146118_1_, int p_146118_2_) {
    }

    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
        if (this.enabled && this.field_146125_m && p_146116_2_ >= this.field_146128_h && p_146116_3_ >= this.field_146129_i && p_146116_2_ < this.field_146128_h + this.field_146120_f && p_146116_3_ < this.field_146129_i + this.field_146121_g) {
            return true;
        }
        return false;
    }

    public boolean func_146115_a() {
        return this.field_146123_n;
    }

    public void func_146111_b(int p_146111_1_, int p_146111_2_) {
    }

    public void func_146113_a(SoundHandler p_146113_1_) {
        p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
    }

    public int func_146117_b() {
        return this.field_146120_f;
    }
}

