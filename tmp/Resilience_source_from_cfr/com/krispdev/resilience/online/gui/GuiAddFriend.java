/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiAddFriend
extends GuiScreen {
    private GuiScreen parent;
    private GuiTextField nameBox;
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private String state = "";

    public GuiAddFriend(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiAddFriend.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -15724528);
        Resilience.getInstance().getLargeItalicFont().drawCenteredString("\u00a7bAdd a Friend!", Resilience.getInstance().getInvoker().getWidth() / 2, 4.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("You can send your friends friend requests to view where they're playing and if they're online!", this.invoker.getWidth() / 2, 28.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("To do so, simply fill in their username and hit \"Send Request\"", this.invoker.getWidth() / 2, 40.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString(this.state, this.invoker.getWidth() / 2, 64.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Friend's Username (Case Sensitive!):", Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 81.0f, -2236929);
        this.nameBox.drawTextBox();
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
            String url = "http://resilience.krispdev.com/requestFriend.php?ign=" + this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&target=" + this.nameBox.getText();
            String result = Utils.sendGetRequest(url);
            if (result.equals("err")) {
                this.state = "\u00a7cError connecting, please try again later.";
            } else if (result.equals("Incorrect password!")) {
                this.state = "\u00a7cIncorrect password! If you've forgotten your password, please email krisphf@gmail.com";
            } else {
                if (this.parent instanceof GuiFriendManager) {
                    GuiFriendManager fm = (GuiFriendManager)this.parent;
                    fm.state = "\u00a7bSent a friend request to " + this.nameBox.getText();
                }
                this.invoker.displayScreen(this.parent);
            }
        }
    }

    @Override
    public void updateScreen() {
        this.nameBox.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.nameBox.textboxKeyTyped(c, i);
        if (c == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.nameBox.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 150.0f, 204.0f, 20.0f, "Send Request"));
        this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() / 2 - 102, 174.0f, 204.0f, 20.0f, "Cancel"));
        this.nameBox = new GuiTextField(this.mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth() / 2 - 100, 96, 200, 20);
    }
}

