/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.sliders;

import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class VerticalScrollSlider {
    private NumberValue value;
    private float min;
    private float max;
    private int posX;
    private int posY;
    private boolean dragging = false;
    private int width = 99;
    private int height = 20;
    private int lastClickY;
    private int dragY = 0;
    private boolean round;

    public VerticalScrollSlider(NumberValue value, int posX, int posY, int posX1, int posY1) {
        this.value = value;
        this.min = value.getMin();
        this.max = value.getMax();
        this.posX = posX;
        this.posY = posY;
        this.width = posX1;
        this.height = posY1 - 5;
        this.setVal(value.getValue());
    }

    public void draw(int i, int j) {
        if (this.dragging) {
            this.drag(j);
        }
        if (this.dragY < 0) {
            this.dragY = 0;
        }
        if (this.dragY > this.height - 10) {
            this.dragY = this.height - 10;
        }
        Utils.drawBetterRect(this.getPosX(), this.getPosY(), this.getPosX() + this.width, this.getPosY() + this.height, -16777216, -2013253806);
        Utils.drawBetterRect(this.getPosX(), this.getPosY() + this.getDragY(), this.getPosX() + this.width, this.getPosY() + this.getDragY() + 10, -16777216, -1147495681);
        this.value.setValue((float)this.dragY / ((float)this.height / (this.max - this.min)) + this.min);
    }

    public void drag(int i) {
        this.dragY = i - this.lastClickY;
    }

    public void setVal(float val) {
        float dif = this.max - this.min;
        float f = (float)this.height / dif;
        this.value.setValue(val -= this.min);
        this.dragY = (int)(f * val);
    }

    public void mouseClicked(int i, int j, int k) {
        if (k == 0) {
            if (i >= this.posX && i <= this.posX + this.width && j >= this.posY && j <= this.posY + this.height) {
                this.dragY = j - (this.posY + 1);
                this.lastClickY = j - this.dragY;
                this.dragging = true;
            } else {
                this.dragging = false;
            }
        }
    }

    public void mouseMovedOrUp(int i, int j, int k) {
        if (k == 0) {
            this.dragging = false;
        }
    }

    public int getDragY() {
        return this.dragY;
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
        if (i >= this.posX && i <= this.posX + this.width && j >= this.posY && j <= this.posY + this.height) {
            return true;
        }
        return false;
    }

    public boolean overNob(int i, int j) {
        if (i >= this.posX && i <= this.posX + this.width && j >= this.posY && j <= this.posY + this.height) {
            return true;
        }
        return false;
    }

    public void setX(int newX) {
        this.posX = newX;
    }

    public void setY(int newY) {
        this.posY = newY;
    }
}

