/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.krispdev.resilience.hooks.HookGuiIngame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class GuiNewChat
extends Gui {
    private static final Logger logger = LogManager.getLogger();
    protected final Minecraft field_146247_f;
    private final List field_146248_g = new ArrayList();
    private final List field_146252_h = new ArrayList();
    protected final List field_146253_i = new ArrayList();
    protected int field_146250_j;
    protected boolean field_146251_k;
    private static final String __OBFID = "CL_00000669";

    public GuiNewChat(Minecraft par1Minecraft) {
        this.field_146247_f = par1Minecraft;
    }

    public void func_146230_a(int p_146230_1_) {
        if (this.field_146247_f.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int var2 = this.func_146232_i();
            boolean var3 = false;
            int var4 = 0;
            int var5 = this.field_146253_i.size();
            float var6 = this.field_146247_f.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (var5 > 0) {
                int var11;
                int var14;
                if (this.func_146241_e()) {
                    var3 = true;
                }
                float var7 = this.func_146244_h();
                int var8 = MathHelper.ceiling_float_int((float)this.func_146228_f() / var7);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)2.0f, (float)20.0f, (float)0.0f);
                GL11.glScalef((float)var7, (float)var7, (float)1.0f);
                int var9 = 0;
                while (var9 + this.field_146250_j < this.field_146253_i.size() && var9 < var2) {
                    ChatLine var10 = (ChatLine)this.field_146253_i.get(var9 + this.field_146250_j);
                    if (var10 != null && ((var11 = p_146230_1_ - var10.getUpdatedCounter()) < 200 || var3)) {
                        double var12 = (double)var11 / 200.0;
                        var12 = 1.0 - var12;
                        if ((var12 *= 10.0) < 0.0) {
                            var12 = 0.0;
                        }
                        if (var12 > 1.0) {
                            var12 = 1.0;
                        }
                        var12 *= var12;
                        var14 = (int)(255.0 * var12);
                        if (var3) {
                            var14 = 255;
                        }
                        var14 = (int)((float)var14 * var6);
                        ++var4;
                        if (var14 > 3) {
                            int var15 = 0;
                            int var16 = (- var9) * 9;
                            GuiNewChat.drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
                            String var17 = var10.func_151461_a().getFormattedText();
                            this.field_146247_f.fontRenderer.drawStringWithShadow(var17, var15, var16 - 8, 16777215 + (var14 << 24));
                            GL11.glDisable((int)3008);
                        }
                    }
                    ++var9;
                }
                if (var3) {
                    var9 = this.field_146247_f.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef((float)-3.0f, (float)0.0f, (float)0.0f);
                    int var18 = var5 * var9 + var5;
                    var11 = var4 * var9 + var4;
                    int var20 = this.field_146250_j * var11 / var5;
                    int var13 = var11 * var11 / var18;
                    if (var18 != var11) {
                        var14 = var20 > 0 ? 170 : 96;
                        int var19 = this.field_146251_k ? 13382451 : 3355562;
                        GuiNewChat.drawRect(0, - var20, 2, - var20 - var13, var19 + (var14 << 24));
                        GuiNewChat.drawRect(2, - var20, 1, - var20 - var13, 13421772 + (var14 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }

    public void func_146231_a() {
        this.field_146253_i.clear();
        this.field_146252_h.clear();
        this.field_146248_g.clear();
    }

    public void func_146227_a(IChatComponent p_146227_1_) {
        this.func_146234_a(p_146227_1_, 0);
    }

    public void func_146234_a(IChatComponent p_146234_1_, int p_146234_2_) {
        this.func_146237_a(p_146234_1_, p_146234_2_, this.field_146247_f.ingameGUI.getUpdateCounter(), false);
        logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
    }

    private String func_146235_b(String p_146235_1_) {
        return Minecraft.getMinecraft().gameSettings.chatColours ? p_146235_1_ : EnumChatFormatting.getTextWithoutFormattingCodes(p_146235_1_);
    }

    private void func_146237_a(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
        if (p_146237_2_ != 0) {
            this.func_146242_c(p_146237_2_);
        }
        int var5 = MathHelper.floor_float((float)this.func_146228_f() / this.func_146244_h());
        int var6 = 0;
        ChatComponentText var7 = new ChatComponentText("");
        ArrayList var8 = Lists.newArrayList();
        ArrayList var9 = Lists.newArrayList((Iterable)p_146237_1_);
        int var10 = 0;
        while (var10 < var9.size()) {
            IChatComponent var11 = (IChatComponent)var9.get(var10);
            String var12 = this.func_146235_b(String.valueOf(var11.getChatStyle().getFormattingCode()) + var11.getUnformattedTextForChat());
            int var13 = this.field_146247_f.fontRenderer.getStringWidth(var12);
            ChatComponentText var14 = new ChatComponentText(var12);
            var14.setChatStyle(var11.getChatStyle().createShallowCopy());
            boolean var15 = false;
            if (var6 + var13 > var5) {
                String var17;
                String var16 = this.field_146247_f.fontRenderer.trimStringToWidth(var12, var5 - var6, false);
                String string = var17 = var16.length() < var12.length() ? var12.substring(var16.length()) : null;
                if (var17 != null && var17.length() > 0) {
                    int var18 = var16.lastIndexOf(" ");
                    if (var18 >= 0 && this.field_146247_f.fontRenderer.getStringWidth(var12.substring(0, var18)) > 0) {
                        var16 = var12.substring(0, var18);
                        var17 = var12.substring(var18);
                    }
                    ChatComponentText var19 = new ChatComponentText(var17);
                    var19.setChatStyle(var11.getChatStyle().createShallowCopy());
                    var9.add(var10 + 1, var19);
                }
                var13 = this.field_146247_f.fontRenderer.getStringWidth(var16);
                var14 = new ChatComponentText(var16);
                var14.setChatStyle(var11.getChatStyle().createShallowCopy());
                var15 = true;
            }
            if (var6 + var13 <= var5) {
                var6 += var13;
                var7.appendSibling(var14);
            } else {
                var15 = true;
            }
            if (var15) {
                var8.add(var7);
                var6 = 0;
                var7 = new ChatComponentText("");
            }
            ++var10;
        }
        var8.add(var7);
        boolean var21 = this.func_146241_e();
        for (IChatComponent var22 : var8) {
            if (var21 && this.field_146250_j > 0) {
                this.field_146251_k = true;
                this.func_146229_b(1);
            }
            this.field_146253_i.add(0, new ChatLine(p_146237_3_, var22, p_146237_2_));
        }
        while (this.field_146253_i.size() > 100) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!p_146237_4_) {
            this.field_146252_h.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
            while (this.field_146252_h.size() > 100) {
                this.field_146252_h.remove(this.field_146252_h.size() - 1);
            }
        }
    }

    public void func_146245_b() {
        this.field_146253_i.clear();
        this.resetScroll();
        int var1 = this.field_146252_h.size() - 1;
        while (var1 >= 0) {
            ChatLine var2 = (ChatLine)this.field_146252_h.get(var1);
            this.func_146237_a(var2.func_151461_a(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
            --var1;
        }
    }

    public List func_146238_c() {
        return this.field_146248_g;
    }

    public void func_146239_a(String p_146239_1_) {
        if (this.field_146248_g.isEmpty() || !((String)this.field_146248_g.get(this.field_146248_g.size() - 1)).equals(p_146239_1_)) {
            this.field_146248_g.add(p_146239_1_);
        }
    }

    public void resetScroll() {
        this.field_146250_j = 0;
        this.field_146251_k = false;
    }

    public void func_146229_b(int p_146229_1_) {
        this.field_146250_j += p_146229_1_;
        int var2 = this.field_146253_i.size();
        if (this.field_146250_j > var2 - this.func_146232_i()) {
            this.field_146250_j = var2 - this.func_146232_i();
        }
        if (this.field_146250_j <= 0) {
            this.field_146250_j = 0;
            this.field_146251_k = false;
        }
    }

    public IChatComponent func_146236_a(int p_146236_1_, int p_146236_2_) {
        if (!this.func_146241_e()) {
            return null;
        }
        ScaledResolution var3 = new ScaledResolution(this.field_146247_f.gameSettings, this.field_146247_f.displayWidth, this.field_146247_f.displayHeight);
        int var4 = var3.getScaleFactor();
        float var5 = this.func_146244_h();
        int var6 = p_146236_1_ / var4 - 3;
        int var7 = p_146236_2_ / var4 - 27;
        var6 = MathHelper.floor_float((float)var6 / var5);
        var7 = MathHelper.floor_float((float)var7 / var5);
        if (var6 >= 0 && var7 >= 0) {
            int var8 = Math.min(this.func_146232_i(), this.field_146253_i.size());
            if (var6 <= MathHelper.floor_float((float)this.func_146228_f() / this.func_146244_h()) && var7 < this.field_146247_f.fontRenderer.FONT_HEIGHT * var8 + var8) {
                int var9 = var7 / this.field_146247_f.fontRenderer.FONT_HEIGHT + this.field_146250_j;
                if (var9 >= 0 && var9 < this.field_146253_i.size()) {
                    ChatLine var10 = (ChatLine)this.field_146253_i.get(var9);
                    int var11 = 0;
                    for (IChatComponent var13 : var10.func_151461_a()) {
                        if (!(var13 instanceof ChatComponentText) || (var11 += this.field_146247_f.fontRenderer.getStringWidth(this.func_146235_b(((ChatComponentText)var13).getChatComponentText_TextValue()))) <= var6) continue;
                        return var13;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public boolean func_146241_e() {
        return this.field_146247_f.currentScreen instanceof GuiChat;
    }

    public void func_146242_c(int p_146242_1_) {
        ChatLine var3;
        Iterator var2 = this.field_146253_i.iterator();
        do {
            if (var2.hasNext()) continue;
            var2 = this.field_146252_h.iterator();
            do {
                if (var2.hasNext()) continue;
                return;
            } while ((var3 = (ChatLine)var2.next()).getChatLineID() != p_146242_1_);
            var2.remove();
            return;
        } while ((var3 = (ChatLine)var2.next()).getChatLineID() != p_146242_1_);
        var2.remove();
    }

    public int func_146228_f() {
        return GuiNewChat.func_146233_a(this.field_146247_f.gameSettings.chatWidth);
    }

    public int func_146246_g() {
        return GuiNewChat.func_146243_b(this.func_146241_e() ? this.field_146247_f.gameSettings.chatHeightFocused : this.field_146247_f.gameSettings.chatHeightUnfocused);
    }

    public float func_146244_h() {
        return this.field_146247_f.gameSettings.chatScale;
    }

    public static int func_146233_a(float p_146233_0_) {
        int var1 = 320;
        int var2 = 40;
        return MathHelper.floor_float(p_146233_0_ * (float)(var1 - var2) + (float)var2);
    }

    public static int func_146243_b(float p_146243_0_) {
        int var1 = 180;
        int var2 = 20;
        return MathHelper.floor_float(p_146243_0_ * (float)(var1 - var2) + (float)var2);
    }

    public int func_146232_i() {
        return this.func_146246_g() / 9;
    }
}

