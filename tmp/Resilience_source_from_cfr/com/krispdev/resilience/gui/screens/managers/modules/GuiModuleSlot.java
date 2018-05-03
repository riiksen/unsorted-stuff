/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.gui.screens.managers.modules;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;

public class GuiModuleSlot
extends ResilienceSlot {
    private int selectedSlot = 0;
    private Minecraft mc;
    private GuiScreen screen;

    public GuiModuleSlot(Minecraft mc, GuiScreen screen) {
        super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 50);
        this.screen = screen;
    }

    @Override
    protected int func_148138_e() {
        return this.getSize() * 50;
    }

    @Override
    protected int getSize() {
        return GuiModuleManager.visibleModules.size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2, int var3, int var4) {
        this.selectedSlot = var1;
    }

    @Override
    protected boolean isSelected(int var1) {
        if (this.selectedSlot == var1) {
            return true;
        }
        return false;
    }

    protected int getSelectedSlot() {
        return this.selectedSlot;
    }

    @Override
    protected void drawBackground() {
        Utils.drawRect(0.0f, 0.0f, this.screen.width, this.screen.height, -15724528);
    }

    @Override
    protected void drawSlot(int selected, int x, int y, int var4, Tessellator var5, int var6, int var7) {
        try {
            DefaultModule module = GuiModuleManager.visibleModules.get(selected);
            Resilience.getInstance().getModListFont().drawString("\u00a7b" + module.getName(), x, y, 16777215);
            Resilience.getInstance().getStandardFont().drawString("Keybind: \u00a7f" + Keyboard.getKeyName((int)module.getKeyCode()), x, y + 12, -1);
            Resilience.getInstance().getStandardFont().drawString("State: " + (module.isEnabled() ? "\u00a73Enabled" : "\u00a78Disabled"), x, y + 23, -1);
            Resilience.getInstance().getButtonExtraFont().drawString("\u00a73" + module.getDescription(), x, y + 34, 1728053247);
        }
        catch (Exception module) {
            // empty catch block
        }
    }
}

