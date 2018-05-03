/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiUpdating;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class UpdateGamePrompt
extends GuiScreen {
    private GuiScreen parentScreen;

    public UpdateGamePrompt(GuiScreen screen) {
        this.parentScreen = screen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 8.0f, Resilience.getInstance().getInvoker().getHeight() - 28, 108.0f, 20.0f, "Update Now"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() - 108, Resilience.getInstance().getInvoker().getHeight() - 28, 100.0f, 20.0f, "Remind Me Later"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        Utils.drawRect(0.0f, 0.0f, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -14671840);
        Resilience.getInstance().getPanelTitleFont().drawCenteredString("An update has been found for ".concat(Resilience.getInstance().getName()), Resilience.getInstance().getInvoker().getWidth() / 2, 8.0f, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (Resilience.getInstance().getInvoker().getId(btn) == 0) {
            this.mc.displayGuiScreen(new GuiUpdating());
        } else {
            this.mc.displayGuiScreen(new HookGuiMainMenu());
        }
    }
}

