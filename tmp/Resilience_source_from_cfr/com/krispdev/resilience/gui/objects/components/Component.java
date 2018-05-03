/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.components;

import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Viewable;
import java.awt.Rectangle;

public class Component
implements Clickable,
Viewable {
    private float x;
    private float y;
    private float x1;
    private float y1;
    private float oX;
    private float oY;
    private float oX1;
    private float oY1;

    public Component(float x, float y, float x1, float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.oX = x;
        this.oY = y;
        this.oX1 = x1;
        this.oY1 = y1;
    }

    public void onComponentClicked(int x, int y, int btn) {
    }

    @Override
    public void draw(int mX, int mY) {
    }

    @Override
    public boolean onClick(int x, int y, int btn) {
        if ((float)x >= this.x && (float)x <= this.x1 && (float)y >= this.y && (float)y <= this.y1) {
            this.onComponentClicked(x, y, btn);
            return true;
        }
        return false;
    }

    @Override
    public void onMouseButtonUp(int x, int y, int btn) {
    }

    public boolean shouldDrawFromScroll(Rectangle scrollArea, int scrollFactor, float error) {
        if ((double)this.x >= scrollArea.getMinX() && (double)(this.y + (- error)) >= scrollArea.getMinY() && (double)(this.y1 + error) <= scrollArea.getMaxY()) {
            return true;
        }
        return false;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getY1() {
        return this.y1;
    }

    public float getX1() {
        return this.x1;
    }

    public float getOX() {
        return this.oX;
    }

    public float getOY() {
        return this.oY;
    }

    public float getOY1() {
        return this.oY1;
    }

    public float getOX1() {
        return this.oX1;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setX1(int newX1) {
        this.x1 = newX1;
    }

    public void setY1(int newY1) {
        this.y1 = newY1;
    }
}

