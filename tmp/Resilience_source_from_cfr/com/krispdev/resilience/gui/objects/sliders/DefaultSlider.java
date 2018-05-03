/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.sliders;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class DefaultSlider {
    private NumberValue value;
    private float min;
    private float max;
    private int posX;
    private int posY;
    private boolean dragging = false;
    private int width = 100;
    private int height = 20;
    private int lastClickX;
    private int dragX = 0;
    private DefaultPanel panel;
    private boolean round;

    public DefaultSlider(NumberValue value, float min, float max, int posX, int posY, DefaultPanel valuePanel, boolean round) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.posX = posX;
        this.posY = posY;
        this.panel = valuePanel;
        this.round = round;
        this.setVal(value.getValue());
    }

    public void draw(int i, int j) {
        this.width = 99;
        if (this.dragging) {
            this.drag(i);
        }
        if (this.dragX < 0) {
            this.dragX = 0;
        }
        if (this.dragX > this.width - 5) {
            this.dragX = this.width - 5;
        }
        this.value.setValue((float)this.dragX / ((float)(this.width - 5) / (this.max - this.min)) + this.min);
    }

    public void drag(int i) {
        this.dragX = i - this.lastClickX;
    }

    public void setVal(float val) {
        float dif = this.max - this.min;
        float f = (float)(this.width - 5) / dif;
        this.value.setValue(val -= this.min);
        this.dragX = (int)(f * val);
    }

    public boolean mouseClicked(int i, int j, int k) {
        if (k == 0) {
            if (i >= this.posX + this.panel.getDragX() && i <= this.posX + 6 + this.panel.getDragX() + 94 && j >= this.posY + this.panel.getDragY() + 20 && j <= this.posY + this.panel.getDragY() + 34) {
                this.dragX = i - (this.panel.getDragX() + this.posX + 1);
                this.lastClickX = i - this.dragX;
                this.dragging = true;
                return true;
            }
            this.dragging = false;
        }
        return false;
    }

    public void mouseMovedOrUp(int i, int j, int k) {
        if (k == 0) {
            this.dragging = false;
            Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
        }
    }

    public int getDragX() {
        return this.dragX;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public NumberValue getValue() {
        return this.value;
    }

    public boolean shouldRound() {
        return this.round;
    }

    public boolean overSlider(int i, int j) {
        if (i >= this.posX + this.panel.getDragX() && i <= this.posX + 6 + this.panel.getDragX() + 94 && j >= this.posY + this.panel.getDragY() + 20 && j <= this.posY + 34 + this.panel.getDragY()) {
            return true;
        }
        return false;
    }

    public boolean overNob(int i, int j) {
        if (i >= this.posX + this.panel.getDragX() && i <= this.posX + 6 + this.panel.getDragX() + 94 && j >= this.posY + this.panel.getDragY() + 20 && j <= this.posY + 34 + this.panel.getDragY()) {
            return true;
        }
        return false;
    }

    public DefaultPanel getPanel() {
        return this.panel;
    }
}

