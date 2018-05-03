/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui2.objects.geometry.shapes;

import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class Circle
implements Shape {
    private double radius;
    private double diameter;
    private double circumference;
    private float x;
    private float y;

    public Circle(double radius, float x, float y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.diameter = this.calculateDiameter(radius);
        this.circumference = this.calculateCircumferenceR(radius);
    }

    @Override
    public boolean overArea(float pX, float pY) {
        float distAway = (float)Math.hypot(pX, pY);
        if ((double)distAway <= (double)this.x + this.radius && distAway >= this.x && (double)distAway <= (double)this.y + this.radius && distAway >= this.y) {
            return true;
        }
        return false;
    }

    public double calculateDiameter(double radius) {
        return radius * 2.0;
    }

    public double calculateCircumferenceR(double radius) {
        return this.calculateDiameter(radius) * 3.141592653589793;
    }

    public double calculateCircumferenceD(double diameter) {
        return diameter * 3.141592653589793;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.diameter = this.calculateDiameter(radius);
        this.circumference = this.calculateCircumferenceR(radius);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}

