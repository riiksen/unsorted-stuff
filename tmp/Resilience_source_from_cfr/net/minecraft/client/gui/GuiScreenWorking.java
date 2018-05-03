/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking
extends GuiScreen
implements IProgressUpdate {
    private String field_146591_a = "";
    private String field_146589_f = "";
    private int field_146590_g;
    private boolean field_146592_h;
    private static final String __OBFID = "CL_00000707";

    @Override
    public void displayProgressMessage(String par1Str) {
        this.resetProgressAndMessage(par1Str);
    }

    @Override
    public void resetProgressAndMessage(String par1Str) {
        this.field_146591_a = par1Str;
        this.resetProgresAndWorkingMessage("Working...");
    }

    @Override
    public void resetProgresAndWorkingMessage(String par1Str) {
        this.field_146589_f = par1Str;
        this.setLoadingProgress(0);
    }

    @Override
    public void setLoadingProgress(int par1) {
        this.field_146590_g = par1;
    }

    @Override
    public void func_146586_a() {
        this.field_146592_h = true;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (this.field_146592_h) {
            this.mc.displayGuiScreen(null);
        } else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + " " + this.field_146590_g + "%", this.width / 2, 90, 16777215);
            super.drawScreen(par1, par2, par3);
        }
    }
}

