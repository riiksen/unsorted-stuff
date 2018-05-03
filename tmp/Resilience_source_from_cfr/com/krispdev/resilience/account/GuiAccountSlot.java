/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.account;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.Account;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class GuiAccountSlot
extends ResilienceSlot {
    private int selectedSlot = 0;
    private Minecraft mc;
    private GuiScreen screen;

    public GuiAccountSlot(Minecraft mc, GuiScreen screen) {
        super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 30);
        this.screen = screen;
    }

    @Override
    protected int func_148138_e() {
        return this.getSize() * 30;
    }

    @Override
    protected int getSize() {
        return Account.accountList.size();
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
            Account account = Account.accountList.get(selected);
            Resilience.getInstance().getPanelTitleFont().drawString(account.getUsername(), x, y + 1, 16777215);
            if (account.isPremium()) {
                Resilience.getInstance().getStandardFont().drawString("\u00a7bPremium", x, y + 14, 1728053247);
            } else {
                Resilience.getInstance().getStandardFont().drawString("\u00a7cNon-Premium (No Password)", x, y + 14, 1728053247);
            }
        }
        catch (Exception account) {
            // empty catch block
        }
    }
}

