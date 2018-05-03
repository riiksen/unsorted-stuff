/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.utilities.font.TTFRenderer;

public class ModOptionBox {
    private String text;
    private float x;
    private float y;
    private CheckBox checkBox;

    public ModOptionBox(String text, float y, float x, boolean startChecked) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.checkBox = new CheckBox(x, y, startChecked);
    }

    public void draw(int mX, int mY) {
        this.checkBox.draw((int)this.x, (int)this.y);
        Resilience.getInstance().getStandardFont().drawString(this.text, this.x + 12.0f, this.y - 2.0f, -1);
    }

    public boolean clicked(int x, int y) {
        if (this.checkBox.clicked(x, y)) {
            return true;
        }
        return false;
    }

    public boolean isChecked() {
        return this.checkBox == null ? false : this.checkBox.isChecked();
    }

    public void setX(int x) {
        this.x = x;
        this.checkBox.setX(x);
    }

    public void setY(int y) {
        this.y = y;
        this.checkBox.setY(y);
    }

    public void setChecked(boolean checked) {
        this.checkBox.setChecked(checked);
    }
}

