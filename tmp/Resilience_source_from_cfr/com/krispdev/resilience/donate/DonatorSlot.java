/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.donate;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class DonatorSlot
extends ResilienceSlot {
    private GuiScreen screen;
    private int selected;

    public DonatorSlot(Minecraft theMinecraft, GuiScreen screen) {
        super(theMinecraft, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 41);
        this.screen = screen;
        this.selected = 0;
    }

    @Override
    protected int func_148138_e() {
        return this.getSize() * 41;
    }

    @Override
    protected int getSize() {
        return Donator.donatorList.size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2, int var3, int var4) {
        this.selected = var1;
    }

    @Override
    protected boolean isSelected(int var1) {
        if (this.selected == var1) {
            return true;
        }
        return false;
    }

    protected int getSelected() {
        return this.selected;
    }

    @Override
    protected void drawBackground() {
        this.screen.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int selectedIndex, int x, int y, int var4, Tessellator var5, int var6, int var7) {
        try {
            Donator donator = Donator.donatorList.get(selectedIndex);
            Resilience.getInstance().getStandardFont().drawString(donator.getNickname().replaceAll("~REPLACECHAR1~", " "), x, y + 3, -11141121);
            Resilience.getInstance().getStandardFont().drawString("$" + donator.getAmount(), x, y + 14, -8355712);
            Resilience.getInstance().getStandardFont().drawString(donator.getMessage().replaceAll("~REPLACECHAR1~", " "), x, y + 25, -11141291);
        }
        catch (Exception donator) {
            // empty catch block
        }
    }
}

