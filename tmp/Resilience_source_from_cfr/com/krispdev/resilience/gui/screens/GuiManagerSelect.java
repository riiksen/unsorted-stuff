/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiManagerSelect
extends GuiScreen {
    private GuiScreen parent;

    public GuiManagerSelect(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4.0f, 4.0f, 50.0f, 20.0f, "Back"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, Resilience.getInstance().getInvoker().getHeight() / 2 - 10, 200.0f, 20.0f, "Module Manager"));
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        if (Resilience.getInstance().getWrapper().getPlayer() != null) {
            this.drawDefaultBackground();
        } else {
            GuiManagerSelect.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -15724528);
        }
        super.drawScreen(x, y, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (Resilience.getInstance().getInvoker().getId(btn) == 0) {
            Resilience.getInstance().getInvoker().displayScreen(this.parent);
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 1) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiModuleManager(this));
        }
    }
}

