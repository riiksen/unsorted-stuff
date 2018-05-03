/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.GuiPasswordBox;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiAskDonate;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiLogin
extends GuiScreen {
    private GuiScreen parent;
    private GuiPasswordBox passwordBox;
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private CheckBox hidePass;
    private String state = "";

    public GuiLogin(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiLogin.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -15724528);
        Resilience.getInstance().getLargeItalicFont().drawCenteredString("Welcome back to \u00a7bResilience Online!", Resilience.getInstance().getInvoker().getWidth() / 2, 4.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("Your current account is \u00a7b" + this.invoker.getSessionUsername(), this.invoker.getWidth() / 2, 28.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("Please login to \u00a7bResilience Online \u00a7fwith the password you registered with!", this.invoker.getWidth() / 2, 40.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString(this.state, this.invoker.getWidth() / 2, 52.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("Hide Password", this.invoker.getWidth() / 2 - 60, 127.5f, -1);
        this.passwordBox.show = !this.hidePass.isChecked();
        Resilience.getInstance().getStandardFont().drawString("Password:", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 81.0f, 10526880);
        this.passwordBox.drawTextBox();
        this.hidePass.draw(i, j);
        super.drawScreen(i, j, f);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        if (this.invoker.getId(btn) == 1) {
            if (Resilience.getInstance().getFileManager().shouldAsk && !Donator.isDonator(this.invoker.getSessionUsername(), 0.01f)) {
                this.invoker.displayScreen(new GuiAskDonate(new HookGuiMainMenu()));
            } else {
                this.invoker.displayScreen(this.parent);
            }
        }
        if (this.invoker.getId(btn) == 0) {
            String url = "http://resilience.krispdev.com/updateOnline.php?ign=" + this.invoker.getSessionUsername() + "&password=" + this.passwordBox.getText() + "&online=true&channel=" + Resilience.getInstance().getValues().userChannel;
            String result = Utils.sendGetRequest(url);
            if (result.equals("err")) {
                this.state = "\u00a7cError connecting, please try again later.";
            } else if (result.equals("Incorrect password!")) {
                this.state = "\u00a7cIncorrect password! If you've forgotten your password, please email krisphf@gmail.com";
            } else {
                Resilience.getInstance().getValues().onlinePassword = this.passwordBox.getText();
                FileUtils.addToFile(Resilience.getInstance().getFileManager().online, this.invoker.getSessionUsername());
                if (Resilience.getInstance().getFileManager().shouldAsk && !Donator.isDonator(this.invoker.getSessionUsername(), 0.01f)) {
                    this.invoker.displayScreen(new GuiAskDonate(new HookGuiMainMenu()));
                } else {
                    this.invoker.displayScreen(this.parent);
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        this.passwordBox.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.passwordBox.textboxKeyTyped(c, i);
        if (c == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.passwordBox.mouseClicked(i, j, k);
        this.hidePass.clicked(i, j);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 150.0f, 204.0f, 20.0f, "Login"));
        this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 174.0f, 204.0f, 20.0f, "Go Offline"));
        this.hidePass = new CheckBox(this.invoker.getWidth() / 2 - 101, 129.0f, true);
        this.passwordBox = new GuiPasswordBox(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 96, 200, 20);
    }
}

