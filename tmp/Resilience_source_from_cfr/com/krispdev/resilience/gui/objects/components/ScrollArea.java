/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.components;

import com.krispdev.resilience.gui.objects.components.Component;
import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Viewable;
import com.krispdev.resilience.gui.objects.sliders.VerticalScrollSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ScrollArea
implements Viewable,
Clickable {
    private ArrayList<Component> components = new ArrayList();
    private NumberValue scrollValue;
    private VerticalScrollSlider slider;
    private int scrollFactor;
    private Rectangle area;
    private int oldScroll = 0;

    public ScrollArea(Rectangle area) {
        this.area = area;
        this.scrollValue = new NumberValue(0.0f, 0.0f, 100.0f, "Scroll", false);
        this.slider = new VerticalScrollSlider(this.scrollValue, (int)area.getMinX(), (int)area.getMinY(), 10, (int)area.getMaxY() / 2 - 8);
    }

    @Override
    public void draw(int x, int y) {
        boolean shouldApplyScroll;
        if (this.components.size() < 1) {
            return;
        }
        int neededSize = (int)this.components.get(this.components.size() - 1).getOY1();
        float scrollPercent = this.scrollValue.getValue();
        this.scrollFactor = (int)(- (double)scrollPercent * 0.01 * (double)neededSize);
        Utils.drawRect((float)this.area.getMinX(), (float)this.area.getMinY(), (float)this.area.getMaxX(), (float)this.area.getMaxY(), -1728053248);
        int comSize = (int)(this.components.get(0).getOY1() - this.components.get(0).getOY());
        boolean bl = shouldApplyScroll = this.scrollFactor % comSize == 0;
        if (shouldApplyScroll) {
            this.oldScroll = this.scrollFactor / 10;
        }
        for (Component c : this.components) {
            c.setX((int)((double)c.getOX() + this.area.getMinX()));
            c.setY((int)((double)c.getOY() + this.area.getMinY()) + this.oldScroll);
            c.setX1((int)((double)c.getOX1() + this.area.getMinX()));
            c.setY1((int)((double)c.getOY1() + this.area.getMinY()) + this.oldScroll);
            if (!c.shouldDrawFromScroll(this.area, this.scrollFactor, 4.0f)) continue;
            c.draw(x, y);
        }
        this.slider.setX((int)this.area.getMaxX() - 10);
        this.slider.setY((int)this.area.getMinY());
        this.slider.draw(x, y);
    }

    public void addComponent(Component component1) {
        this.components.add(component1);
    }

    public void setX(int newX) {
        this.area.x = newX;
    }

    public void setY(int newY) {
        this.area.y = newY;
    }

    public void setX1(int newX1) {
        this.area.width = newX1;
    }

    public void setY1(int newY1) {
        this.area.height = newY1;
    }

    @Override
    public boolean onClick(int x, int y, int btn) {
        this.slider.mouseClicked(x, y, btn);
        return false;
    }

    @Override
    public void onMouseButtonUp(int x, int y, int btn) {
        this.slider.mouseMovedOrUp(x, y, btn);
    }

    public int getX() {
        return (int)this.area.getX();
    }

    public int getY() {
        return (int)this.area.getY();
    }

    public int getX1() {
        return (int)this.area.getWidth() + (int)this.area.getX();
    }

    public int getY1() {
        return (int)this.area.getHeight() + (int)this.area.getY();
    }

    public void clearComponents() {
        this.components.clear();
    }
}

