/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.opengl.GL11;

public class GuiOptionSlider
extends GuiButton {
    private float field_146134_p = 1.0f;
    public boolean field_146135_o;
    private GameSettings.Options field_146133_q;
    private final float field_146132_r;
    private final float field_146131_s;
    private static final String __OBFID = "CL_00000680";

    public GuiOptionSlider(int p_i45016_1_, int p_i45016_2_, int p_i45016_3_, GameSettings.Options p_i45016_4_) {
        this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0f, 1.0f);
    }

    public GuiOptionSlider(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, GameSettings.Options p_i45017_4_, float p_i45017_5_, float p_i45017_6_) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.field_146133_q = p_i45017_4_;
        this.field_146132_r = p_i45017_5_;
        this.field_146131_s = p_i45017_6_;
        Minecraft var7 = Minecraft.getMinecraft();
        this.field_146134_p = p_i45017_4_.normalizeValue(var7.gameSettings.getOptionFloatValue(p_i45017_4_));
        this.displayString = var7.gameSettings.getKeyBinding(p_i45017_4_);
    }

    @Override
    protected int getHoverState(boolean p_146114_1_) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
        if (this.field_146125_m) {
            if (this.field_146135_o) {
                this.field_146134_p = (float)(p_146119_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
                if (this.field_146134_p < 0.0f) {
                    this.field_146134_p = 0.0f;
                }
                if (this.field_146134_p > 1.0f) {
                    this.field_146134_p = 1.0f;
                }
                float var4 = this.field_146133_q.denormalizeValue(this.field_146134_p);
                p_146119_1_.gameSettings.setOptionFloatValue(this.field_146133_q, var4);
                this.field_146134_p = this.field_146133_q.normalizeValue(var4);
                this.displayString = p_146119_1_.gameSettings.getKeyBinding(this.field_146133_q);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.field_146134_p * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.field_146134_p * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
        if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
            this.field_146134_p = (float)(p_146116_2_ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            if (this.field_146134_p < 0.0f) {
                this.field_146134_p = 0.0f;
            }
            if (this.field_146134_p > 1.0f) {
                this.field_146134_p = 1.0f;
            }
            p_146116_1_.gameSettings.setOptionFloatValue(this.field_146133_q, this.field_146133_q.denormalizeValue(this.field_146134_p));
            this.displayString = p_146116_1_.gameSettings.getKeyBinding(this.field_146133_q);
            this.field_146135_o = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int p_146118_1_, int p_146118_2_) {
        this.field_146135_o = false;
    }
}

