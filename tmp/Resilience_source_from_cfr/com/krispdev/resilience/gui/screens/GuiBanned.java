/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiBanned
extends GuiScreen {
    @Override
    public void initGui() {
        Resilience.getInstance().getInvoker().clearButtons(this);
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, this.width / 2 - 50, this.height - 30, 100.0f, 20.0f, "Close Minecraft"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        Utils.drawRect(0.0f, 0.0f, this.width, this.height, -14277082);
        Resilience.getInstance().getLargeFont().drawCenteredString("Your account has been banned", this.width / 2, 10.0f, -65536);
        Resilience.getInstance().getPanelTitleFont().drawCenteredString("Ban Reason: " + Resilience.getInstance().getValues().banReason, this.width / 2, 34.0f, -256);
        Resilience.getInstance().getStandardFont().drawCenteredString("Ban Lasts for " + Resilience.getInstance().getValues().banTime + " days.", this.width / 2, 50.0f, -256);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        System.exit(0);
    }
}

