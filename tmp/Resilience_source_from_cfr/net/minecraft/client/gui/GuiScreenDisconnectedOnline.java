/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiScreenDisconnectedOnline
extends GuiScreen {
    private String field_146867_a;
    private IChatComponent field_146865_f;
    private List field_146866_g;
    private final GuiScreen field_146868_h;
    private static final String __OBFID = "CL_00000778";

    public GuiScreenDisconnectedOnline(GuiScreen p_i45037_1_, String p_i45037_2_, IChatComponent p_i45037_3_) {
        this.field_146868_h = p_i45037_1_;
        this.field_146867_a = I18n.format(p_i45037_2_, new Object[0]);
        this.field_146865_f = p_i45037_3_;
    }

    @Override
    protected void keyTyped(char par1, int par2) {
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.back", new Object[0])));
        this.field_146866_g = this.fontRendererObj.listFormattedStringToWidth(this.field_146865_f.getFormattedText(), this.width - 50);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(this.field_146868_h);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146867_a, this.width / 2, this.height / 2 - 50, 11184810);
        int var4 = this.height / 2 - 30;
        if (this.field_146866_g != null) {
            for (String var6 : this.field_146866_g) {
                this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
                var4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(par1, par2, par3);
    }
}

