/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.Sys;

public class GuiAskDonate
extends GuiScreen {
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private GuiScreen parent;

    public GuiAskDonate(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.invoker.clearButtons(this);
        this.invoker.addButton(this, new ResilienceButton(0, 8.0f, this.invoker.getHeight() - 28, 100.0f, 20.0f, "Remind Me Later"));
        this.invoker.addButton(this, new ResilienceButton(1, this.invoker.getWidth() / 2 - 75, this.invoker.getHeight() - 28, 150.0f, 20.0f, "Donate!"));
        this.invoker.addButton(this, new ResilienceButton(2, this.invoker.getWidth() - 108, this.invoker.getHeight() - 28, 100.0f, 20.0f, "Never >:("));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiAskDonate.drawRect(0, 0, this.invoker.getWidth(), this.invoker.getHeight(), -15724528);
        Resilience.getInstance().getPanelTitleFont().drawCenteredString("Donate to " + Resilience.getInstance().getName() + "!", this.invoker.getWidth() / 2, 8.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("Donators who paid $5 or more to " + Resilience.getInstance().getName() + " receive: A cape in-game (seen by everybody using the client),", this.invoker.getWidth() / 2, 24.0f, -43521);
        Resilience.getInstance().getStandardFont().drawCenteredString("A spot on the donation credits (In the" + Resilience.getInstance().getName() + " menu under \"Donate\"),", this.invoker.getWidth() / 2, 42.0f, -43521);
        Resilience.getInstance().getStandardFont().drawCenteredString("Eternal happiness!", this.invoker.getWidth() / 2, 96.0f, -43521);
        Resilience.getInstance().getStandardFont().drawCenteredString("More features in the future (such as VIP mods),", this.invoker.getWidth() / 2, 60.0f, -43521);
        Resilience.getInstance().getStandardFont().drawCenteredString("The removal of this screen,", this.invoker.getWidth() / 2, 78.0f, -43521);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (this.invoker.getId(btn) == 0) {
            this.invoker.displayScreen(this.parent);
        } else if (this.invoker.getId(btn) == 1) {
            Sys.openURL((String)"http://resilience.krispdev.com/donate");
            this.invoker.displayScreen(this.parent);
            Resilience.getInstance().getFileManager().saveOptions("0");
        } else {
            Resilience.getInstance().getFileManager().saveOptions("-1");
            this.invoker.displayScreen(this.parent);
        }
    }
}

