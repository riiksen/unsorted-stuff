/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.account;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.Account;
import com.krispdev.resilience.account.GuiAccountScreen;
import com.krispdev.resilience.account.GuiPasswordBox;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiEdit
extends GuiScreen {
    private Account account;
    private GuiScreen parentScreen;
    private GuiTextField usernameBox;
    private GuiPasswordBox passwordBox;

    public GuiEdit(Account account, GuiScreen parent) {
        this.account = account;
        this.parentScreen = parent;
    }

    @Override
    public void updateScreen() {
        this.usernameBox.updateCursorCounter();
        this.passwordBox.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.usernameBox.textboxKeyTyped(c, i);
        this.passwordBox.textboxKeyTyped(c, i);
        if (c == '\t') {
            if (this.usernameBox.isFocused()) {
                this.usernameBox.setFocused(false);
                this.passwordBox.setFocused(true);
            } else {
                this.usernameBox.setFocused(true);
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
        this.usernameBox.mouseClicked(i, j, k);
        this.passwordBox.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, Resilience.getInstance().getInvoker().getHeight() / 2 + 25, 204.0f, 20.0f, "Done"));
        this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, Resilience.getInstance().getInvoker().getHeight() / 2 + 49, 204.0f, 20.0f, "Back"));
        this.usernameBox = new GuiTextField(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 76, 200, 20);
        this.passwordBox = new GuiPasswordBox(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 116, 200, 20);
        this.usernameBox.setText(this.account.getUsername());
        this.passwordBox.setText(this.account.getPassword());
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiEdit.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -15724528);
        Resilience.getInstance().getStandardFont().drawString("Username", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 63.0f, 10526880);
        Resilience.getInstance().getStandardFont().drawString("Password", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 104.0f, 10526880);
        this.usernameBox.drawTextBox();
        this.passwordBox.drawTextBox();
        super.drawScreen(i, j, f);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton par1) {
        if (par1.id == 1) {
            this.mc.displayGuiScreen(GuiAccountScreen.guiScreen);
        }
        if (par1.id == 0) {
            int index = Account.accountList.indexOf(this.account);
            Account.accountList.remove(index);
            Account.accountList.add(index, new Account(this.usernameBox.getText(), this.passwordBox.getText()));
            Resilience.getInstance().getFileManager().saveAccounts(new String[0]);
            this.mc.displayGuiScreen(GuiAccountScreen.guiScreen);
        }
    }
}

