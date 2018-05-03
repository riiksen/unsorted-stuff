/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.buttons.DefaultButton;
import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Dragable;
import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import java.util.ArrayList;
import java.util.List;

public class DefaultPanel
implements Dragable,
Clickable {
    public ArrayList<DefaultButton> buttons = new ArrayList();
    public ArrayList<DefaultSlider> sliders = new ArrayList();
    private boolean isPinned = false;
    private boolean isExtended = false;
    private boolean isDragging = false;
    private boolean isFocused = false;
    private boolean isVisible = false;
    private int lastClickX;
    private int lastClickY;
    private int x;
    private int y;
    private int x1;
    private int y1;
    private int dragX;
    private int dragY;
    private String title;
    private int color1 = -1157496318;
    private int color3 = -1157598484;
    private int color4 = -1149058817;

    public DefaultPanel(String title, int x, int y, int x1, int y1, boolean visible) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.title = title;
        this.isVisible = visible;
        ClickGui.windows.add(this);
    }

    public void draw(int i, int j) {
        if (!this.isVisible) {
            return;
        }
        if (this.isDragging) {
            this.drag(i, j);
        }
        Utils.drawRect(this.getX(), this.getY(), this.getX1(), this.getY() + 17, this.color1);
        Utils.drawRect(this.getX1() - 14, this.getY() + 14, this.getX1() - 3, this.getY() + 3, this.isExtended() ? this.color3 : this.color4);
        Utils.drawRect(this.getX1() - 27, this.getY() + 14, this.getX1() - 16, this.getY() + 3, this.isPinned() ? this.color3 : this.color4);
        Resilience.getInstance().getPanelTitleFont().drawString(this.getTitle(), this.getX() + 4, this.getY() + 1, -1);
    }

    @Override
    public boolean onClick(int i, int j, int k) {
        if (!this.isVisible) {
            return false;
        }
        boolean hasClicked = false;
        if (i <= this.getX1() && i >= this.getX() && j <= this.getY() + 17 && j >= this.getY() && k == 0) {
            Resilience.getInstance().getClickGui().focusWindow(this);
            this.isDragging = true;
            this.lastClickX = i - this.dragX;
            this.lastClickY = j - this.dragY;
            hasClicked = true;
        }
        if (i >= this.getX1() - 15 && j <= this.getY() + 15 && i <= this.getX1() - 2 && j >= this.getY() + 2) {
            ClickGui.hasOpened = true;
            this.setExtended(!this.isExtended());
            hasClicked = true;
        }
        if (i >= this.getX1() - 27 && j <= this.getY() + 15 && i <= this.getX1() - 16 && j >= this.getY() + 2) {
            ClickGui.hasPinned = true;
            this.setPinned(!this.isPinned());
            hasClicked = true;
        }
        return hasClicked;
    }

    public void onGuiClosed() {
        this.isDragging = false;
    }

    @Override
    public void onMouseButtonUp(int i, int j, int k) {
        if (k == 0) {
            this.isDragging = false;
        }
    }

    public void keyTyped(char c, int i) {
        if (!this.isVisible) {
            return;
        }
    }

    public int getX() {
        return this.x + this.dragX;
    }

    public int getY() {
        return this.y + this.dragY;
    }

    public int getX1() {
        return this.x1 + this.dragX;
    }

    public int getY1() {
        return this.y1 + this.dragY;
    }

    @Override
    public void drag(int x, int y) {
        if (!this.isVisible) {
            return;
        }
        this.dragX = x - this.lastClickX;
        this.dragY = y - this.lastClickY;
    }

    @Override
    public int getDragX() {
        return this.dragX;
    }

    @Override
    public int getDragY() {
        return this.dragY;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setDragX(int i) {
        this.dragX = i;
    }

    @Override
    public void setDragY(int i) {
        this.dragY = i;
    }

    public boolean isPinned() {
        return this.isPinned;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public void setPinned(boolean flag) {
        this.isPinned = flag;
    }

    public void setExtended(boolean flag) {
        this.isExtended = flag;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setFocused(boolean flag) {
        this.isFocused = flag;
    }

    public int getOriginalX() {
        return this.x;
    }

    public int getOriginalY() {
        return this.y;
    }

    public int getOriginalX1() {
        return this.x1;
    }

    public int getOriginalY1() {
        return this.y1;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }
}

