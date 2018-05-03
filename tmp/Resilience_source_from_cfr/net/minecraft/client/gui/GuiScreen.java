/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen
extends Gui {
    protected static RenderItem itemRender = new RenderItem();
    protected Minecraft mc;
    public int width;
    public int height;
    public List buttonList = new ArrayList();
    protected List labelList = new ArrayList();
    public boolean field_146291_p;
    protected FontRenderer fontRendererObj;
    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int field_146298_h;
    private static final String __OBFID = "CL_00000710";

    public void drawScreen(int par1, int par2, float par3) {
        int var4 = 0;
        while (var4 < this.buttonList.size()) {
            ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, par1, par2);
            ++var4;
        }
        var4 = 0;
        while (var4 < this.labelList.size()) {
            ((GuiLabel)this.labelList.get(var4)).func_146159_a(this.mc, par1, par2);
            ++var4;
        }
    }

    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    public static String getClipboardString() {
        try {
            Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)var0.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception var0) {
            // empty catch block
        }
        return "";
    }

    public static void setClipboardString(String p_146275_0_) {
        try {
            StringSelection var1 = new StringSelection(p_146275_0_);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
        }
        catch (Exception var1) {
            // empty catch block
        }
    }

    protected void func_146285_a(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_) {
        List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        int var5 = 0;
        while (var5 < var4.size()) {
            if (var5 == 0) {
                var4.set(var5, (Object)((Object)p_146285_1_.getRarity().rarityColor) + (String)var4.get(var5));
            } else {
                var4.set(var5, (Object)((Object)EnumChatFormatting.GRAY) + (String)var4.get(var5));
            }
            ++var5;
        }
        this.func_146283_a(var4, p_146285_2_, p_146285_3_);
    }

    protected void func_146279_a(String p_146279_1_, int p_146279_2_, int p_146279_3_) {
        this.func_146283_a(Arrays.asList(p_146279_1_), p_146279_2_, p_146279_3_);
    }

    protected void func_146283_a(List p_146283_1_, int p_146283_2_, int p_146283_3_) {
        if (!p_146283_1_.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int var4 = 0;
            for (String var6 : p_146283_1_) {
                int var7 = this.fontRendererObj.getStringWidth(var6);
                if (var7 <= var4) continue;
                var4 = var7;
            }
            int var14 = p_146283_2_ + 12;
            int var15 = p_146283_3_ - 12;
            int var8 = 8;
            if (p_146283_1_.size() > 1) {
                var8 += 2 + (p_146283_1_.size() - 1) * 10;
            }
            if (var14 + var4 > this.width) {
                var14 -= 28 + var4;
            }
            if (var15 + var8 + 6 > this.height) {
                var15 = this.height - var8 - 6;
            }
            this.zLevel = 300.0f;
            GuiScreen.itemRender.zLevel = 300.0f;
            int var9 = -267386864;
            this.drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
            this.drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
            this.drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
            this.drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
            int var10 = 1347420415;
            int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
            this.drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
            this.drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);
            int var12 = 0;
            while (var12 < p_146283_1_.size()) {
                String var13 = (String)p_146283_1_.get(var12);
                this.fontRendererObj.drawStringWithShadow(var13, var14, var15, -1);
                if (var12 == 0) {
                    var15 += 2;
                }
                var15 += 10;
                ++var12;
            }
            this.zLevel = 0.0f;
            GuiScreen.itemRender.zLevel = 0.0f;
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable((int)32826);
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int var4 = 0;
            while (var4 < this.buttonList.size()) {
                GuiButton var5 = (GuiButton)this.buttonList.get(var4);
                if (var5.mousePressed(this.mc, par1, par2)) {
                    this.selectedButton = var5;
                    var5.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(var5);
                }
                ++var4;
            }
        }
    }

    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        if (this.selectedButton != null && p_146286_3_ == 0) {
            this.selectedButton.mouseReleased(p_146286_1_, p_146286_2_);
            this.selectedButton = null;
        }
    }

    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
    }

    protected void actionPerformed(GuiButton p_146284_1_) {
    }

    public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
        this.mc = p_146280_1_;
        this.fontRendererObj = p_146280_1_.fontRenderer;
        this.width = p_146280_2_;
        this.height = p_146280_3_;
        this.buttonList.clear();
        this.initGui();
    }

    public void initGui() {
    }

    public void handleInput() {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }

    public void handleMouseInput() {
        int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int var3 = Mouse.getEventButton();
        if (Minecraft.isRunningOnMac && var3 == 0 && (Keyboard.isKeyDown((int)29) || Keyboard.isKeyDown((int)157))) {
            var3 = 1;
        }
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.field_146298_h++ > 0) {
                return;
            }
            this.eventButton = var3;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(var1, var2, this.eventButton);
        } else if (var3 != -1) {
            if (this.mc.gameSettings.touchscreen && --this.field_146298_h > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseMovedOrUp(var1, var2, var3);
        } else if (this.eventButton != -1 && this.lastMouseEvent > 0) {
            long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(var1, var2, this.eventButton, var4);
        }
    }

    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            int var1 = Keyboard.getEventKey();
            char var2 = Keyboard.getEventCharacter();
            if (var1 == 87) {
                this.mc.toggleFullscreen();
                return;
            }
            this.keyTyped(var2, var1);
        }
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public void drawDefaultBackground() {
        this.func_146270_b(0);
    }

    public void func_146270_b(int p_146270_1_) {
        if (this.mc.theWorld != null) {
            this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.func_146278_c(p_146270_1_);
        }
    }

    public void func_146278_c(int p_146278_1_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        Tessellator var2 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(optionsBackground);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var3 = 32.0f;
        var2.startDrawingQuads();
        var2.setColorOpaque_I(4210752);
        var2.addVertexWithUV(0.0, this.height, 0.0, 0.0, (float)this.height / var3 + (float)p_146278_1_);
        var2.addVertexWithUV(this.width, this.height, 0.0, (float)this.width / var3, (float)this.height / var3 + (float)p_146278_1_);
        var2.addVertexWithUV(this.width, 0.0, 0.0, (float)this.width / var3, p_146278_1_);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, p_146278_1_);
        var2.draw();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void confirmClicked(boolean par1, int par2) {
    }

    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown((int)219) || Keyboard.isKeyDown((int)220) : Keyboard.isKeyDown((int)29) || Keyboard.isKeyDown((int)157);
    }

    public static boolean isShiftKeyDown() {
        if (!Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54)) {
            return false;
        }
        return true;
    }
}

