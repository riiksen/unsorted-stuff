/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.gui.objects.other.GuiCustomFontField;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ResilienceConsole
extends GuiScreen {
    private boolean field_73897_d;
    private boolean field_73905_m;
    private int field_73903_n;
    private URI clickedURI;
    protected GuiCustomFontField inputField;
    private String defaultInputFieldText = "";

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.inputField = new GuiCustomFontField(Resilience.getInstance().getStandardFont(), 79, 18, this.width - 75, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.field_73905_m = false;
        if (par2 != 15) {
            this.field_73897_d = false;
        }
        if (par2 == 1) {
            this.mc.displayGuiScreen(null);
        } else if (par2 != 28 && par2 != 156) {
            this.inputField.textboxKeyTyped(par1, par2);
        } else {
            String var3 = this.inputField.getText().trim();
            if (var3.length() > 0) {
                for (Command cmd : Command.cmdList) {
                    try {
                        boolean result;
                        if (!var3.startsWith(cmd.getWords()) || !(result = cmd.recieveCommand(var3))) continue;
                        this.mc.displayGuiScreen(null);
                        return;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Resilience.getInstance().getLogger().warningChat("\u00a7fUnkown command \"\u00a7b".concat(var3).concat("\u00a7f\". Type \"\u00a7bhelp\u00a7f\" for help"));
            }
            this.mc.displayGuiScreen(null);
        }
    }

    public void func_146274_d() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 1;
            }
            if (var1 < -1) {
                var1 = -1;
            }
            if (!ResilienceConsole.isShiftKeyDown()) {
                var1 *= 7;
            }
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        this.inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        if (par2 == 0) {
            if (par1) {
                this.func_73896_a(this.clickedURI);
            }
            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_73896_a(URI par1URI) {
        try {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            var2.getMethod("browse", URI.class).invoke(var3, par1URI);
        }
        catch (Throwable var4) {
            var4.printStackTrace();
        }
    }

    public void completePlayerName() {
    }

    private void func_73893_a(String par1Str, String par2Str) {
        if (par1Str.length() >= 1) {
            this.field_73905_m = true;
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2896);
        Utils.drawBetterRect(75.0, 14.0, this.width - 75, 30.0, -1442840576, -1437708722);
        this.inputField.drawTextBox();
        String msg = this.inputField.getText();
        int theHeight = 35;
        for (Command cmd : Command.cmdList) {
            String toDisplay;
            if (cmd.getWords().startsWith(msg) && msg.length() > 0) {
                toDisplay = String.valueOf(cmd.getWords()) + cmd.getExtras() + " | " + cmd.getDesc();
                Utils.drawBetterRect(75.0, theHeight - 3, this.width - 75, theHeight + 12, -285212673, -299489754);
                Resilience.getInstance().getStandardFont().drawString("\u00a7f" + toDisplay, 79.0f, theHeight - 2, -15384811);
                Resilience.getInstance().getStandardFont().drawString("\u00a7b" + msg, 79.0f, theHeight - 2, -15584170);
                theHeight += 16;
                continue;
            }
            if (!msg.startsWith(cmd.getWords())) continue;
            toDisplay = "\u00a7b" + cmd.getWords() + cmd.getExtras() + " \u00a7f| " + cmd.getDesc();
            Utils.drawBetterRect(75.0, theHeight - 3, this.width - 75, theHeight + 12, -285212673, -299489754);
            Resilience.getInstance().getStandardFont().drawString(toDisplay, 79.0f, theHeight - 2, -15581419);
            theHeight += 16;
        }
        if (msg.length() == 0) {
            Resilience.getInstance().getStandardFont().drawString("\u00a7fType \"\u00a7bhelp\u00a7f\" to get help.", 79.0f, 35.0f, -15584170);
        }
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}

