/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookGuiMainMenu;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiMemoryErrorScreen
extends GuiScreen {
    private static final String __OBFID = "CL_00000702";

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 4 + 120 + 12, I18n.format("gui.toMenu", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 4 + 120 + 12, I18n.format("menu.quit", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new HookGuiMainMenu());
        } else if (p_146284_1_.id == 1) {
            this.mc.shutdown();
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Out of memory!", this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRendererObj, "Minecraft has run out of memory.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880);
        this.drawString(this.fontRendererObj, "This could be caused by a bug in the game or by the", this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 10526880);
        this.drawString(this.fontRendererObj, "Java Virtual Machine not being allocated enough", this.width / 2 - 140, this.height / 4 - 60 + 60 + 27, 10526880);
        this.drawString(this.fontRendererObj, "memory.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 10526880);
        this.drawString(this.fontRendererObj, "To prevent level corruption, the current game has quit.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 54, 10526880);
        this.drawString(this.fontRendererObj, "We've tried to free up enough memory to let you go back to", this.width / 2 - 140, this.height / 4 - 60 + 60 + 63, 10526880);
        this.drawString(this.fontRendererObj, "the main menu and back to playing, but this may not have worked.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 72, 10526880);
        this.drawString(this.fontRendererObj, "Please restart the game if you see this message again.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 81, 10526880);
        super.drawScreen(par1, par2, par3);
    }
}

