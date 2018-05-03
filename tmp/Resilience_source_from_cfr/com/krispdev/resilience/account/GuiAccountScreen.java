/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.account;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.Account;
import com.krispdev.resilience.account.GuiAccountSlot;
import com.krispdev.resilience.account.GuiAdd;
import com.krispdev.resilience.account.GuiDirect;
import com.krispdev.resilience.account.GuiEdit;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiResilienceMain;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;

public class GuiAccountScreen
extends GuiScreen {
    public static final GuiAccountScreen guiScreen = new GuiAccountScreen(new GuiResilienceMain(new HookGuiMainMenu()));
    private GuiAccountSlot accountSlot;
    private GuiScreen parentScreen;
    public static String status = "Idle";
    private boolean firstTime = true;

    public GuiAccountScreen(GuiScreen screen) {
        this.parentScreen = screen;
        Resilience.getInstance().getFileManager().loadAccounts();
    }

    @Override
    public void initGui() {
        this.firstTime = true;
        this.accountSlot = new GuiAccountSlot(Minecraft.getMinecraft(), this);
        this.accountSlot.registerScrollButtons(7, 8);
        this.buttonList.clear();
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, this.width / 2 - 100, this.height - 30 + 1 + 2, 200.0f, 20.0f, "Login"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, this.width / 2 - 204, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Add"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(2, this.width / 2 + 104, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Delete"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(4, this.width / 2 - 204, this.height - 53 + 2, 100.0f, 20.0f, "Edit"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(5, this.width / 2 + 104, this.height - 53 + 2, 100.0f, 20.0f, "Direct"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(3, 4.0f, 4.0f, 50.0f, 20.0f, "Back"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        Utils.drawRect(0.0f, 0.0f, this.width, this.height, -15724528);
        this.accountSlot.drawScreen(i, j, f);
        Resilience.getInstance().getStandardFont().drawString(this.firstTime ? "\u00a7fStatus: \u00a78Idle" : "\u00a7fStatus: " + status, 62.0f, 9.0f, 1716911957);
        Resilience.getInstance().getStandardFont().drawCenteredString("Alt Count: \u00a7f" + Account.accountList.size(), this.width / 2, 4.0f, -1776412);
        Resilience.getInstance().getStandardFont().drawString("\u00a7fUsername: \u00a7b" + Resilience.getInstance().getInvoker().getSessionUsername(), (float)Resilience.getInstance().getInvoker().getWidth() - Resilience.getInstance().getStandardFont().getWidth("\u00a7fUsername: \u00a7b" + Resilience.getInstance().getInvoker().getSessionUsername()) - 11.0f, 9.0f, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        Utils.setOnline(false);
        if (btn.id == 0) {
            this.firstTime = false;
            if (Account.accountList.size() <= 0) {
                return;
            }
            Account acc = Account.accountList.get(this.accountSlot.getSelectedSlot());
            if (acc == null) {
                return;
            }
            if (acc.isPremium()) {
                final String username = acc.getUsername();
                final String password = acc.getPassword();
                status = "\u00a7fLoggin in...";
                new Thread(){

                    @Override
                    public void run() {
                        try {
                            GuiAccountScreen.status = Utils.setSessionData(username, password);
                            Resilience.getInstance().getFileManager().saveAccounts(new String[0]);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                this.mc.session = new Session(acc.getUsername(), "", "");
                status = "\u00a7bSuccess!";
            }
        } else if (btn.id == 1) {
            this.mc.displayGuiScreen(new GuiAdd(guiScreen));
        } else if (btn.id == 4) {
            if (Account.accountList.size() <= 0) {
                return;
            }
            Account acc = Account.accountList.get(this.accountSlot.getSelectedSlot());
            if (acc == null) {
                return;
            }
            Resilience.getInstance().getInvoker().displayScreen(new GuiEdit(acc, this));
        } else if (btn.id == 2) {
            try {
                if (Account.accountList.size() <= 0) {
                    return;
                }
                Account.accountList.remove(Account.accountList.indexOf(Account.accountList.get(this.accountSlot.getSelectedSlot())));
                Resilience.getInstance().getFileManager().saveAccounts(new String[0]);
            }
            catch (Exception acc) {}
        } else if (btn.id == 5) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiDirect(this));
        } else {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

}

