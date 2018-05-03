/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui2.objects.geometry.shapes;

import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class Rectangle
implements Shape {
    private float x;
    private float y;
    private float x1;
    private float y1;
    private float width;
    private float height;

    public Rectangle(float x, float y, float x1, float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.width = x1 - x;
        this.height = y1 - y;
    }

    @Override
    public boolean overArea(float pX, float pY) {
        if (pX >= this.x && pX <= this.x1 && pY >= this.y && pY <= this.y1) {
            return true;
        }
        return false;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    public float getX1() {
        return this.x1;
    }

    public float getY1() {
        return this.y1;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}

