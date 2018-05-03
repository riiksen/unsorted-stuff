/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.achievement;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAchievement
extends Gui {
    private static final ResourceLocation field_146261_a = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    private Minecraft field_146259_f;
    private int field_146260_g;
    private int field_146267_h;
    private String field_146268_i;
    private String field_146265_j;
    private Achievement field_146266_k;
    private long field_146263_l;
    private RenderItem field_146264_m;
    private boolean field_146262_n;
    private static final String __OBFID = "CL_00000721";

    public GuiAchievement(Minecraft par1Minecraft) {
        this.field_146259_f = par1Minecraft;
        this.field_146264_m = new RenderItem();
    }

    public void func_146256_a(Achievement p_146256_1_) {
        this.field_146268_i = I18n.format("achievement.get", new Object[0]);
        this.field_146265_j = p_146256_1_.func_150951_e().getUnformattedText();
        this.field_146263_l = Minecraft.getSystemTime();
        this.field_146266_k = p_146256_1_;
        this.field_146262_n = false;
    }

    public void func_146255_b(Achievement p_146255_1_) {
        this.field_146268_i = p_146255_1_.func_150951_e().getUnformattedText();
        this.field_146265_j = p_146255_1_.getDescription();
        this.field_146263_l = Minecraft.getSystemTime() + 2500;
        this.field_146266_k = p_146255_1_;
        this.field_146262_n = true;
    }

    private void func_146258_c() {
        GL11.glViewport((int)0, (int)0, (int)this.field_146259_f.displayWidth, (int)this.field_146259_f.displayHeight);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        this.field_146260_g = this.field_146259_f.displayWidth;
        this.field_146267_h = this.field_146259_f.displayHeight;
        ScaledResolution var1 = new ScaledResolution(this.field_146259_f.gameSettings, this.field_146259_f.displayWidth, this.field_146259_f.displayHeight);
        this.field_146260_g = var1.getScaledWidth();
        this.field_146267_h = var1.getScaledHeight();
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.field_146260_g, (double)this.field_146267_h, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public void func_146254_a() {
        if (this.field_146266_k != null && this.field_146263_l != 0 && Minecraft.getMinecraft().thePlayer != null) {
            double var1 = (double)(Minecraft.getSystemTime() - this.field_146263_l) / 3000.0;
            if (!this.field_146262_n) {
                if (var1 < 0.0 || var1 > 1.0) {
                    this.field_146263_l = 0;
                    return;
                }
            } else if (var1 > 0.5) {
                var1 = 0.5;
            }
            this.func_146258_c();
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            double var3 = var1 * 2.0;
            if (var3 > 1.0) {
                var3 = 2.0 - var3;
            }
            var3 *= 4.0;
            if ((var3 = 1.0 - var3) < 0.0) {
                var3 = 0.0;
            }
            var3 *= var3;
            var3 *= var3;
            int var5 = this.field_146260_g - 160;
            int var6 = 0 - (int)(var3 * 36.0);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3553);
            this.field_146259_f.getTextureManager().bindTexture(field_146261_a);
            GL11.glDisable((int)2896);
            this.drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
            if (this.field_146262_n) {
                this.field_146259_f.fontRenderer.drawSplitString(this.field_146265_j, var5 + 30, var6 + 7, 120, -1);
            } else {
                this.field_146259_f.fontRenderer.drawString(this.field_146268_i, var5 + 30, var6 + 7, -256);
                this.field_146259_f.fontRenderer.drawString(this.field_146265_j, var5 + 30, var6 + 18, -1);
            }
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable((int)2896);
            GL11.glEnable((int)32826);
            GL11.glEnable((int)2903);
            GL11.glEnable((int)2896);
            this.field_146264_m.renderItemAndEffectIntoGUI(this.field_146259_f.fontRenderer, this.field_146259_f.getTextureManager(), this.field_146266_k.theItemStack, var5 + 8, var6 + 8);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
    }

    public void func_146257_b() {
        this.field_146266_k = null;
        this.field_146263_l = 0;
    }
}

