/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiInfo
extends GuiScreen {
    private GuiScreen parentScreen;

    public GuiInfo(GuiScreen screen) {
        this.parentScreen = screen;
    }

    @Override
    public void initGui() {
        Resilience.getInstance().getInvoker().clearButtons(this);
        this.buttonList.add(new ResilienceButton(0, 8.0f, 8.0f, 50.0f, 20.0f, "Back"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        Utils.drawRect(0.0f, 0.0f, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -14671840);
        Resilience.getInstance().getStandardFont().drawCenteredString("This client was coded by Krisp.", Resilience.getInstance().getInvoker().getWidth() / 2, 18.0f, -1776412);
        Resilience.getInstance().getStandardFont().drawCenteredString("Optifine is currently included in this update", Resilience.getInstance().getInvoker().getWidth() / 2, 30.0f, -1776412);
        Resilience.getInstance().getStandardFont().drawCenteredString("If you have any questions, comments, or suggestions feel free to contact me:", Resilience.getInstance().getInvoker().getWidth() / 2, 42.0f, -1776412);
        Resilience.getInstance().getStandardFont().drawCenteredString("krisphf@gmail.com", Resilience.getInstance().getInvoker().getWidth() / 2, 54.0f, -16755201);
        Resilience.getInstance().getStandardFont().drawCenteredString("Credits: Aarow - Bow aimbot, Ownage - Font Renderer, N3xuz_DK - FastBow/Eat exploit, Halalaboos - Projectiles", Resilience.getInstance().getInvoker().getWidth() / 2, 70.0f, -1776412);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (Resilience.getInstance().getInvoker().getId(btn) == 0) {
            Resilience.getInstance().getInvoker().displayScreen(this.parentScreen);
        }
    }
}

