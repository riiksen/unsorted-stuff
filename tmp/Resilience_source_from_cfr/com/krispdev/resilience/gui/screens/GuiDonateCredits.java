/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.donate.DonatorSlot;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiResilienceMain;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.Sys;

public class GuiDonateCredits
extends GuiScreen {
    public static GuiDonateCredits guiDonate = new GuiDonateCredits(new GuiResilienceMain(new HookGuiMainMenu()));
    private DonatorSlot slot;
    private GuiScreen parent;

    public GuiDonateCredits(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Collections.sort(Donator.donatorList);
        this.slot = new DonatorSlot(this.mc, this);
        this.slot.registerScrollButtons(7, 8);
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4.0f, 4.0f, 70.0f, 20.0f, "Back"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(69, Resilience.getInstance().getInvoker().getWidth() / 2 - 76, Resilience.getInstance().getInvoker().getHeight() - 51, 160.0f, 20.0f, "Donate"));
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (btn.id == 0) {
            this.mc.displayGuiScreen(this.parent);
        } else {
            Sys.openURL((String)"http://resilience.krispdev.com/donate");
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.slot.drawScreen(i, j, f);
        Resilience.getInstance().getPanelTitleFont().drawCenteredString("Huge thanks to all these wonderful people!", Resilience.getInstance().getInvoker().getWidth() / 2, 8.0f, -22016);
        super.drawScreen(i, j, f);
    }
}

