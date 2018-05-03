/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.buttons.DefaultButton;
import com.krispdev.resilience.gui.objects.buttons.ModOptionBox;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.ModulePanel;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModuleButton
extends DefaultButton {
    private DefaultModule mod;
    private ModulePanel panel;
    private boolean overButton = false;
    private int colour1 = 1428521673;
    private int colour2 = 1431655765;
    private int colour3 = 1430559709;
    private int colour4 = 1432840039;
    private int colour5 = 1427990174;
    private int colour6 = 1426089425;
    private int colour7 = 1145982719;
    private int slideCount;
    private int textLength = 0;
    private int modExtraCount = 0;
    private boolean infoOpened = false;
    private boolean changeBindOpen = false;

    public ModuleButton(int x, int y, int x1, int y1, ModulePanel panel, DefaultModule mod) {
        super(x, y, x1, y1);
        this.panel = panel;
        this.mod = mod;
        this.slideCount = (int)(- Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription()));
    }

    @Override
    public void draw(int i, int j) {
        boolean isMouseDownOverButton;
        GL11.glPushMatrix();
        GL11.glDisable((int)2896);
        this.colour7 = -1157627904;
        boolean overChange = i >= this.getX1() + this.panel.getDragX() + 6 && i <= this.getX1() + this.panel.getDragX() + 48 && j >= this.getY() + this.panel.getDragY() + 78 && j <= this.getY() + this.panel.getDragY() + 91;
        boolean overClear = i >= this.getX1() + this.panel.getDragX() + 49 && i <= this.getX1() + this.panel.getDragX() + 90 && j >= this.getY() + this.panel.getDragY() + 78 && j <= this.getY() + this.panel.getDragY() + 91;
        boolean overModName = i >= this.getX1() + this.panel.getDragX() + 6 && i <= this.getX1() + this.panel.getDragX() + 90 && j >= this.getY() + this.panel.getDragY() + 21 && j <= this.getY() + this.panel.getDragY() + 36;
        boolean overToggle = i >= this.getX1() + this.panel.getDragX() + 6 && i <= this.getX1() + this.panel.getDragX() + 90 && j >= this.getY() + this.panel.getDragY() + 37 && j <= this.getY() + this.panel.getDragY() + 51;
        boolean isMouseDownOverToggle = Mouse.isButtonDown((int)0) && overToggle && this.panel.isFocused();
        boolean bl = isMouseDownOverButton = Mouse.isButtonDown((int)0) && this.overButton && this.panel.isFocused();
        if (this.mod.isEnabled()) {
            Utils.drawRect(this.getX() + this.panel.getDragX(), this.getY() + this.panel.getDragY() + 21, this.getX1() + this.panel.getDragX(), this.getY1() + this.panel.getDragY() + 38, this.overButton ? (isMouseDownOverButton ? this.colour5 : this.colour3) : this.colour1);
        } else {
            Utils.drawRect(this.getX() + this.panel.getDragX(), this.getY() + this.panel.getDragY() + 21, this.getX1() + this.panel.getDragX(), this.getY1() + this.panel.getDragY() + 38, this.overButton ? (isMouseDownOverButton ? this.colour6 : this.colour4) : this.colour2);
        }
        Resilience.getInstance().getStandardFont().drawCenteredString(this.mod.getName(), (this.getX() + this.getX1()) / 2 + this.panel.getDragX(), this.getY() + this.panel.getDragY() + 23, -1);
        if (this.infoOpened) {
            Resilience.getInstance().getPanelTitleFont().drawString("<", this.getX1() + this.panel.getDragX() - 9, this.getY() + this.panel.getDragY() + 21, -1);
            Utils.drawRect(this.getX1() + this.panel.getDragX() + 6, this.panel.getDragY() + this.getY() + 21, this.getX1() + this.panel.getDragX() + 90, this.panel.getDragY() + this.getY() + 91 + 12 * this.modExtraCount + (this.mod.guiExtras.size() == 0 ? 0 : 8), this.colour7);
            this.modExtraCount = 0;
            Utils.drawSmallHL(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 21, this.getX1() + this.panel.getDragX() + 90, -9934593);
            Utils.drawSmallHL(this.getX1() + this.panel.getDragX() + 6, this.panel.getDragY() + this.getY() + 91 + 12 * this.modExtraCount + (this.mod.guiExtras.size() == 0 ? 0 : 20), this.getX1() + this.panel.getDragX() + 90, -9934593);
            Utils.drawSmallHL(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 36, this.getX1() + this.panel.getDragX() + 90, 1723184639);
            Utils.drawSmallHL(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 51, this.getX1() + this.panel.getDragX() + 90, 1723184639);
            Utils.drawSmallHL(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 91, this.getX1() + this.panel.getDragX() + 90, this.mod.guiExtras.size() == 0 ? 0 : 1723184639);
            Utils.drawRect(this.getX1() + this.panel.getDragX() + 90, this.getY() + this.panel.getDragY() + 21, (float)(this.getX1() + this.panel.getDragX() + 90) + Resilience.getInstance().getButtonExtraFont().getWidth(this.mod.getDescription()) + (float)this.slideCount, this.getY() + this.panel.getDragY() + 36, -872415232);
            if (overModName) {
                Utils.drawRect(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 21, this.getX1() + this.panel.getDragX() + 90, this.getY() + this.panel.getDragY() + 36, 587202559);
                if (this.slideCount < 4) {
                    this.slideCount = this.slideCount + 12 > 4 ? 4 : (this.slideCount += 12);
                } else {
                    ClickGui.hasHovered = true;
                    Resilience.getInstance().getButtonExtraFont().drawString(this.mod.getDescription(), this.getX1() + this.panel.getDragX() + 92, this.getY() + this.panel.getDragY() + 23, -4868609);
                }
            } else if ((float)this.slideCount > - Resilience.getInstance().getButtonExtraFont().getWidth(this.mod.getDescription())) {
                this.slideCount = (float)(this.slideCount - 12) < - Resilience.getInstance().getButtonExtraFont().getWidth(this.mod.getDescription()) ? (int)(- Resilience.getInstance().getButtonExtraFont().getWidth(this.mod.getDescription())) : (this.slideCount -= 12);
            }
            if (overToggle) {
                Utils.drawRect(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 36, this.getX1() + this.panel.getDragX() + 90, this.getY() + this.panel.getDragY() + 51, isMouseDownOverToggle ? 584965597 : 587202559);
            }
            Resilience.getInstance().getStandardFont().drawCenteredString(this.mod.getName(), this.getX1() + 48 + this.panel.getDragX(), this.getY() + this.panel.getDragY() + 23, -1907998);
            Resilience.getInstance().getStandardFont().drawCenteredString(this.mod.isEnabled() ? "Enabled" : "Disabled", this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 38, this.mod.isEnabled() ? -16711936 : -65536);
            Resilience.getInstance().getStandardFont().drawCenteredString("Current Keybind:", this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 53, -4868609);
            Resilience.getInstance().getStandardFont().drawCenteredString(Keyboard.getKeyName((int)this.mod.getKeyCode()), this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 65, -1);
            Utils.drawRect(this.getX1() + this.panel.getDragX() + 6, this.getY() + this.panel.getDragY() + 78, this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 91, overChange ? 1154733055 : 1153022463);
            Resilience.getInstance().getButtonExtraFont().drawCenteredString("CHANGE", this.getX1() + this.panel.getDragX() + 26, this.getY() + this.panel.getDragY() + 80, -1);
            Utils.drawSmallVL(this.getY() + this.panel.getDragY() + 78, this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 91, -1);
            Utils.drawRect(this.getX1() + this.panel.getDragX() + 48, this.getY() + this.panel.getDragY() + 78, this.getX1() + this.panel.getDragX() + 90, this.getY() + this.panel.getDragY() + 91, overClear ? 1154733055 : 1153022463);
            Resilience.getInstance().getButtonExtraFont().drawCenteredString("CLEAR", this.getX1() + this.panel.getDragX() + 69, this.getY() + this.panel.getDragY() + 80, -1);
            if (this.changeBindOpen) {
                Utils.drawBetterRect(Resilience.getInstance().getClickGui().getWidth() / 2 - 75, Resilience.getInstance().getClickGui().getHeight() / 2 - 50, Resilience.getInstance().getClickGui().getWidth() / 2 + 75, Resilience.getInstance().getClickGui().getHeight() / 2 + 50, 1711302097, -1727855869);
                Resilience.getInstance().getPanelTitleFont().drawCenteredString("Press the Key to Bind", Resilience.getInstance().getClickGui().getWidth() / 2, Resilience.getInstance().getClickGui().getHeight() / 2 - 40, -1);
                Resilience.getInstance().getPanelTitleFont().drawCenteredString(">>          ENTER          <<", Resilience.getInstance().getClickGui().getWidth() / 2, Resilience.getInstance().getClickGui().getHeight() / 2 - 12, -1776412);
            }
            for (ModOptionBox box : this.mod.guiExtras) {
                box.setX(this.getX1() + this.panel.getDragX() + 10);
                box.setY(this.panel.getDragY() + this.getY() + 12 * this.modExtraCount + 100);
                box.draw(i, j);
                ++this.modExtraCount;
            }
        } else {
            Resilience.getInstance().getPanelTitleFont().drawString(">", this.getX1() + this.panel.getDragX() - 9, this.getY() + this.panel.getDragY() + 21, -1);
        }
        GL11.glPopMatrix();
    }

    @Override
    public boolean onClick(int i, int j, int k) {
        if (this.changeBindOpen) {
            return false;
        }
        if (this.overButton && k == 0) {
            this.panel.setFocused(true);
            if (this.panel.isFocused()) {
                this.mod.toggle();
            }
            return true;
        }
        if (i >= this.getX() + this.panel.getDragX() && i <= this.getX1() + this.panel.getDragX() && j >= this.getY() + this.panel.getDragY() + 21 && j <= this.getY1() + this.panel.getDragY() + 38 && k == 1) {
            this.infoOpened = !this.infoOpened;
            ClickGui.hasRightClicked = true;
            Resilience.getInstance().getClickGui().focusWindow(this.panel);
            Resilience.getInstance().getClickGui();
            for (DefaultPanel panel : ClickGui.windows) {
                if (!(panel instanceof ModulePanel)) continue;
                ModulePanel modulePanel = (ModulePanel)panel;
                for (DefaultButton button : modulePanel.buttons) {
                    ModuleButton btn;
                    if (!(button instanceof ModuleButton) || (btn = (ModuleButton)button) == this) continue;
                    btn.setInfoOpened(false);
                }
            }
            return true;
        }
        if (i >= this.getX1() + this.panel.getDragX() + 6 && i <= this.getX1() + this.panel.getDragX() + 48 && j >= this.getY() + this.panel.getDragY() + 78 && j <= this.getY() + this.panel.getDragY() + 91 && this.infoOpened) {
            this.changeBindOpen = true;
            this.panel.setFocused(true);
            return true;
        }
        if (i >= this.getX1() + this.panel.getDragX() + 49 && i <= this.getX1() + this.panel.getDragX() + 90 && j >= this.getY() + this.panel.getDragY() + 78 && j <= this.getY() + this.panel.getDragY() + 91 && this.infoOpened && this.mod.getCategory() != ModuleCategory.GUI) {
            this.mod.setKeyBind(0);
            Resilience.getInstance().getFileManager().saveBinds(new String[0]);
            this.panel.setFocused(true);
            return true;
        }
        if (i >= this.getX1() + this.panel.getDragX() + 6 && i <= this.getX1() + this.panel.getDragX() + 90 && j >= this.getY() + this.panel.getDragY() + 36 && j <= this.getY() + this.panel.getDragY() + 51 && this.infoOpened && this.mod.getCategory() != ModuleCategory.GUI) {
            this.mod.toggle();
            this.panel.setFocused(true);
            return true;
        }
        if (this.infoOpened) {
            for (ModOptionBox box : this.mod.guiExtras) {
                if (!box.clicked(i, j)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(char c, int i) {
        if (this.changeBindOpen) {
            if (i != 1) {
                this.mod.setKeyBind(i);
                Resilience.getInstance().getFileManager().saveBinds(new String[0]);
            }
            this.changeBindOpen = false;
        }
    }

    public void setInfoOpened(boolean flag) {
        this.infoOpened = flag;
    }

    public void setOverButton(boolean flag) {
        this.overButton = flag;
    }
}

