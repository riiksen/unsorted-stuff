/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui2.objects.basic;

import com.krispdev.resilience.gui2.interfaces.Clickable;
import com.krispdev.resilience.gui2.objects.basic.Container;
import com.krispdev.resilience.gui2.objects.geometry.Shape;

public abstract class Component
implements Clickable {
    private Container container;
    private Shape area;

    public Component(Container container, Shape area) {
        this.container = container;
        this.area = area;
    }

    @Override
    public boolean onClick(float x, float y) {
        if (this.overArea(x, y)) {
            this.onAreaClicked(x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean overArea(float x, float y) {
        return this.area.overArea(x, y);
    }

    @Override
    public void mouseUp(float x, float y) {
    }

    @Override
    public void onAreaClicked(float x, float y) {
    }

    public Shape getArea() {
        return this.area;
    }
}

