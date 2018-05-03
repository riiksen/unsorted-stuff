/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui2.objects.basic;

import com.krispdev.resilience.gui2.interfaces.Clickable;
import com.krispdev.resilience.gui2.interfaces.Dragable;
import com.krispdev.resilience.gui2.interfaces.Holdable;
import com.krispdev.resilience.gui2.interfaces.Visible;
import com.krispdev.resilience.gui2.objects.basic.Component;
import com.krispdev.resilience.gui2.objects.geometry.Shape;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;
import java.util.ArrayList;

public class Container
implements Dragable,
Clickable,
Holdable,
Visible {
    private boolean dragging = false;
    private Shape area;
    private Shape dragArea;
    private ArrayList<Component> componentList = new ArrayList();

    public Container(Shape area, Rectangle dragArea) {
        this.area = area;
        this.dragArea = dragArea;
    }

    @Override
    public void mouseUp(float x, float y) {
        if (this.overArea(x, y)) {
            this.dragging = false;
        }
    }

    @Override
    public void onAreaClicked(float x, float y) {
    }

    @Override
    public boolean onClick(float x, float y) {
        if (this.dragArea.overArea(x, y)) {
            this.dragging = true;
        }
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
    public void drag(float x, float y) {
        this.area.setX(x);
        this.area.setY(y);
        if (this.area instanceof Rectangle) {
            Rectangle rect = (Rectangle)this.area;
            rect.setX1(x + rect.getWidth());
            rect.setY1(y + rect.getHeight());
        }
        for (Component c : this.componentList) {
            c.getArea().setX(x);
            c.getArea().setY(y);
            if (!(c.getArea() instanceof Rectangle)) continue;
            Rectangle rect = (Rectangle)c.getArea();
            rect.setX1(x + rect.getWidth());
            rect.setY1(y + rect.getHeight());
        }
    }

    @Override
    public void updateDrag(float x, float y) {
        if (this.dragging) {
            this.drag(x, y);
        }
    }

    @Override
    public void addComponent(Component c) {
        this.componentList.add(c);
    }

    @Override
    public void removeComponent(Component c) {
        boolean success = false;
        do {
            try {
                this.componentList.remove(this.componentList.indexOf(c));
                success = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
        } while (!success);
    }

    @Override
    public void draw(float x, float y) {
    }

    @Override
    public void update(float x, float y) {
        this.updateDrag(x, y);
    }
}

