/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.online.friend.OnlineFriend;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class GuiFriendSlot
extends ResilienceSlot {
    private int selectedSlot = 0;
    private Minecraft mc;
    private GuiScreen screen;

    public GuiFriendSlot(Minecraft mc, GuiScreen screen) {
        super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 38);
        this.screen = screen;
    }

    @Override
    protected int func_148138_e() {
        return this.getSize() * 38;
    }

    @Override
    protected int getSize() {
        return GuiFriendManager.friends.size();
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
            OnlineFriend friend = GuiFriendManager.friends.get(selected);
            Resilience.getInstance().getModListFont().drawString("\u00a7f" + friend.getUsername(), x, y, 16777215);
            Resilience.getInstance().getStandardFont().drawString(friend.isOnline() ? "\u00a7bOnline" : "\u00a7cOffline", x, y + 12, -1);
            if (friend.isOnline()) {
                Resilience.getInstance().getStandardFont().drawString(friend.getStatus(), x, y + 23, -8882056);
            } else {
                Resilience.getInstance().getStandardFont().drawString("User is currently offline", x, y + 23, -8882056);
            }
        }
        catch (Exception friend) {
            // empty catch block
        }
    }
}

