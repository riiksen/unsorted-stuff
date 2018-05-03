/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.gui.objects.sliders.ModuleValueSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import java.util.ArrayList;

public class ValuePanel
extends DefaultPanel {
    private int count = 0;

    public /* varargs */ ValuePanel(String title, int x, int y, int x1, int y1, boolean visible, NumberValue ... val) {
        super(title, x, y, x1, y1, visible);
        NumberValue[] arrnumberValue = val;
        int n = arrnumberValue.length;
        int n2 = 0;
        while (n2 < n) {
            NumberValue value = arrnumberValue[n2];
            this.addSlider(value);
            ++n2;
        }
        this.setExtended(true);
    }

    private void addSlider(NumberValue val) {
        this.sliders.add(new ModuleValueSlider(val, val.getMin(), val.getMax(), this.getX() + 4, this.getY() + 18 * this.count, this, val.shouldRound()));
        ++this.count;
    }

    @Override
    public void draw(int i, int j) {
        super.draw(i, j);
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + this.sliders.size() * 18 + 19, -1727790076);
            for (DefaultSlider slider : this.sliders) {
                slider.draw(i, j);
            }
        }
    }

    @Override
    public boolean onClick(int i, int j, int k) {
        for (DefaultSlider slider : this.sliders) {
            if (!slider.mouseClicked(i, j, k)) continue;
            return true;
        }
        if (super.onClick(i, j, k)) {
            return true;
        }
        return false;
    }

    @Override
    public void onMouseButtonUp(int i, int j, int k) {
        for (DefaultSlider slider : this.sliders) {
            slider.mouseMovedOrUp(i, j, k);
        }
        super.onMouseButtonUp(i, j, k);
    }
}

