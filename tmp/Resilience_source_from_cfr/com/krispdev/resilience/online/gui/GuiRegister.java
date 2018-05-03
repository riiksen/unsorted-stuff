/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiRegister
extends GuiScreen {
    private boolean firstTime;
    private GuiScreen parent;
    private GuiTextField emailBox;
    private GuiTextField passwordBox;
    private String state = "";
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();

    public GuiRegister(GuiScreen parent, boolean firstTime) {
        this.parent = parent;
        this.firstTime = firstTime;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiRegister.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -15724528);
        Resilience.getInstance().getLargeItalicFont().drawCenteredString("Welcome to \u00a7bResilience Online!", Resilience.getInstance().getInvoker().getWidth() / 2, 4.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("\u00a7bResilience Online \u00a7fis an inovative new idea in Minecraft Griefing that gives users many new possiblities.", this.invoker.getWidth() / 2, 28.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("\u00a7bResilience Online \u00a7fallows you to organize a team and play with your friends like never before.", this.invoker.getWidth() / 2, 40.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("To use these features you must register. \u00a7bUse a unique password, not your Minecraft password!", this.invoker.getWidth() / 2, 52.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString(this.state, this.invoker.getWidth() / 2, 64.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Email:", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 81.0f, 10526880);
        Resilience.getInstance().getStandardFont().drawString("Password:", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 122.0f, 10526880);
        this.emailBox.drawTextBox();
        this.passwordBox.drawTextBox();
        super.drawScreen(i, j, f);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        if (this.invoker.getId(btn) == 1) {
            this.invoker.displayScreen(this.parent);
        }
        if (this.invoker.getId(btn) == 0) {
            if (this.emailBox.getText().equals("") || !this.emailBox.getText().contains("@") || !this.emailBox.getText().contains(".")) {
                this.state = "\u00a7cError! Invalid email. Don't worry, we will never disclose your email to another company!";
                return;
            }
            if (this.passwordBox.getText().equals("")) {
                this.state = "\u00a7cError! For security reasons, blank passwords are not accepted.";
                return;
            }
            if (this.passwordBox.getText().length() < 8) {
                this.state = "\u00a7cError! For security reasons, passwords less than 8 characters long are not accepted";
                return;
            }
            if (this.passwordBox.getText().equals("12345678")) {
                this.state = "\u00a7cThat password would be cracked instantly...";
                return;
            }
            String url = "http://resilience.krispdev.com/registerUser.php?ign=" + this.invoker.getSessionUsername() + "&password=" + this.passwordBox.getText() + "&email=" + this.emailBox.getText();
            String result = Utils.sendGetRequest(url);
            if (result.equals("err")) {
                this.state = "\u00a7cError registering! Please try again later.";
            } else {
                Resilience.getInstance().getValues().onlinePassword = this.passwordBox.getText();
                FileUtils.addToFile(Resilience.getInstance().getFileManager().online, this.invoker.getSessionUsername());
            }
            this.invoker.displayScreen(new GuiLogin(this.parent));
        }
    }

    @Override
    public void updateScreen() {
        this.emailBox.updateCursorCounter();
        this.passwordBox.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.emailBox.textboxKeyTyped(c, i);
        this.passwordBox.textboxKeyTyped(c, i);
        if (c == '\t') {
            if (this.emailBox.isFocused()) {
                this.emailBox.setFocused(false);
                this.passwordBox.setFocused(true);
            } else {
                this.emailBox.setFocused(true);
                this.passwordBox.setFocused(false);
            }
        }
        if (c == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.emailBox.mouseClicked(i, j, k);
        this.passwordBox.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 180.0f, 204.0f, 20.0f, "Register"));
        this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 204.0f, 204.0f, 20.0f, "Register Next Time"));
        this.emailBox = new GuiTextField(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 96, 200, 20);
        this.passwordBox = new GuiTextField(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 136, 200, 20);
    }
}

