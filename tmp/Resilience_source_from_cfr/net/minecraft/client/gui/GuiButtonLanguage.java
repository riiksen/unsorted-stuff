/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonLanguage
extends GuiButton {
    private static final String __OBFID = "CL_00000672";

    public GuiButtonLanguage(int par1, int par2, int par3) {
        super(par1, par2, par3, 20, 20, "");
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        if (this.field_146125_m) {
            p_146112_1_.getTextureManager().bindTexture(GuiButton.field_146122_a);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean var4 = p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g;
            int var5 = 106;
            if (var4) {
                var5 += this.field_146121_g;
            }
            this.drawTexturedModalRect(this.field_146128_h, this.field_146129_i, 0, var5, this.field_146120_f, this.field_146121_g);
        }
    }
}

