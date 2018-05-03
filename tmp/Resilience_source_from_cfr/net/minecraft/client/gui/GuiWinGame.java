/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.Charsets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class GuiWinGame
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation field_146577_g = new ResourceLocation("textures/misc/vignette.png");
    private int field_146581_h;
    private List field_146582_i;
    private int field_146579_r;
    private float field_146578_s = 0.5f;
    private static final String __OBFID = "CL_00000719";

    @Override
    public void updateScreen() {
        ++this.field_146581_h;
        float var1 = (float)(this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
        if ((float)this.field_146581_h > var1) {
            this.func_146574_g();
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            this.func_146574_g();
        }
    }

    private void func_146574_g() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        this.mc.displayGuiScreen(null);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void initGui() {
        if (this.field_146582_i == null) {
            this.field_146582_i = new ArrayList();
            try {
                int var6;
                String var1 = "";
                String var2 = (Object)((Object)EnumChatFormatting.WHITE) + (Object)((Object)EnumChatFormatting.OBFUSCATED) + (Object)((Object)EnumChatFormatting.GREEN) + (Object)((Object)EnumChatFormatting.AQUA);
                int var3 = 274;
                BufferedReader var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
                Random var5 = new Random(8124371);
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    while (var1.contains(var2)) {
                        var6 = var1.indexOf(var2);
                        String var7 = var1.substring(0, var6);
                        String var8 = var1.substring(var6 + var2.length());
                        var1 = String.valueOf(var7) + (Object)((Object)EnumChatFormatting.WHITE) + (Object)((Object)EnumChatFormatting.OBFUSCATED) + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8;
                    }
                    this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                var6 = 0;
                while (var6 < 8) {
                    this.field_146582_i.add("");
                    ++var6;
                }
                var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8));
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    var1 = var1.replaceAll("\t", "    ");
                    this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                this.field_146579_r = this.field_146582_i.size() * 12;
            }
            catch (Exception var9) {
                logger.error("Couldn't load credits", (Throwable)var9);
            }
        }
    }

    private void func_146575_b(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
        Tessellator var4 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var5 = this.width;
        float var6 = 0.0f - ((float)this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        float var7 = (float)this.height - ((float)this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        float var8 = 0.015625f;
        float var9 = ((float)this.field_146581_h + p_146575_3_ - 0.0f) * 0.02f;
        float var10 = (float)(this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
        float var11 = (var10 - 20.0f - ((float)this.field_146581_h + p_146575_3_)) * 0.005f;
        if (var11 < var9) {
            var9 = var11;
        }
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        var9 *= var9;
        var9 = var9 * 96.0f / 255.0f;
        var4.setColorOpaque_F(var9, var9, var9);
        var4.addVertexWithUV(0.0, this.height, this.zLevel, 0.0, var6 * var8);
        var4.addVertexWithUV(var5, this.height, this.zLevel, (float)var5 * var8, var6 * var8);
        var4.addVertexWithUV(var5, 0.0, this.zLevel, (float)var5 * var8, var7 * var8);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, var7 * var8);
        var4.draw();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.func_146575_b(par1, par2, par3);
        Tessellator var4 = Tessellator.instance;
        int var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        int var7 = this.height + 50;
        float var8 = (- (float)this.field_146581_h + par3) * this.field_146578_s;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)var8, (float)0.0f);
        this.mc.getTextureManager().bindTexture(field_146576_f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(var6, var7, 0, 0, 155, 44);
        this.drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
        var4.setColorOpaque_I(16777215);
        int var9 = var7 + 200;
        int var10 = 0;
        while (var10 < this.field_146582_i.size()) {
            float var11;
            if (var10 == this.field_146582_i.size() - 1 && (var11 = (float)var9 + var8 - (float)(this.height / 2 - 6)) < 0.0f) {
                GL11.glTranslatef((float)0.0f, (float)(- var11), (float)0.0f);
            }
            if ((float)var9 + var8 + 12.0f + 8.0f > 0.0f && (float)var9 + var8 < (float)this.height) {
                String var12 = (String)this.field_146582_i.get(var10);
                if (var12.startsWith("[C]")) {
                    this.fontRendererObj.drawStringWithShadow(var12.substring(3), var6 + (var5 - this.fontRendererObj.getStringWidth(var12.substring(3))) / 2, var9, 16777215);
                } else {
                    this.fontRendererObj.fontRandom.setSeed((long)var10 * 4238972211L + (long)(this.field_146581_h / 4));
                    this.fontRendererObj.drawStringWithShadow(var12, var6, var9, 16777215);
                }
            }
            var9 += 12;
            ++var10;
        }
        GL11.glPopMatrix();
        this.mc.getTextureManager().bindTexture(field_146577_g);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)0, (int)769);
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        var10 = this.width;
        int var13 = this.height;
        var4.addVertexWithUV(0.0, var13, this.zLevel, 0.0, 1.0);
        var4.addVertexWithUV(var10, var13, this.zLevel, 1.0, 1.0);
        var4.addVertexWithUV(var10, 0.0, this.zLevel, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, 0.0);
        var4.draw();
        GL11.glDisable((int)3042);
        super.drawScreen(par1, par2, par3);
    }
}

