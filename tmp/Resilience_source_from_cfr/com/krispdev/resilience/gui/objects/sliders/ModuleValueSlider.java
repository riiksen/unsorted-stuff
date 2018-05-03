/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.sliders;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import java.text.DecimalFormat;

public class ModuleValueSlider
extends DefaultSlider {
    public ModuleValueSlider(NumberValue value, float min, float max, int posX, int posY, DefaultPanel valuePanel, boolean round) {
        super(value, min, max, posX, posY, valuePanel, round);
    }

    @Override
    public void draw(int i, int j) {
        boolean overThingy = i >= this.getPosX() + this.getPanel().getDragX() + this.getDragX() && i <= this.getPosX() + this.getDragX() + 6 + this.getPanel().getDragX() && j >= this.getPosY() + this.getPanel().getDragY() + 20 && j <= this.getPosY() + 34 + this.getPanel().getDragY();
        boolean overSlider = i >= this.getPosX() + this.getPanel().getDragX() && i <= this.getPosX() + 6 + this.getPanel().getDragX() + 94 && j >= this.getPosY() + this.getPanel().getDragY() + 20 && j <= this.getPosY() + 34 + this.getPanel().getDragY();
        Utils.drawBetterRect(this.getPosX() + this.getPanel().getDragX(), this.getPosY() + this.getPanel().getDragY() + 20, this.getPosX() + 6 + this.getPanel().getDragX() + 94, this.getPosY() + 34 + this.getPanel().getDragY(), -16777216, overSlider ? -2013255598 : -2013253806);
        Utils.drawBetterRect(this.getPosX() + this.getPanel().getDragX() + this.getDragX(), this.getPosY() + this.getPanel().getDragY() + 20, this.getPosX() + this.getDragX() + 6 + this.getPanel().getDragX(), this.getPosY() + 34 + this.getPanel().getDragY(), -16777216, overThingy ? -1145719297 : -1147495681);
        Resilience.getInstance().getButtonExtraFont().drawCenteredString(String.valueOf(this.getValue().getName()) + " - " + (this.shouldRound() ? new DecimalFormat("#").format(this.getValue().getValue()) : new DecimalFormat("#.#").format(this.getValue().getValue())), 50 + this.getPanel().getDragX() + this.getPosX(), this.getPosY() + this.getPanel().getDragY() + 22, -1118482);
        super.draw(i, j);
    }
}

