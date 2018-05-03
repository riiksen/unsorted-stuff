/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ResilienceButton
extends GuiButton {
    private float x;
    private float y;
    private float x1;
    private float y1;

    public ResilienceButton(int par1, float par2, float par3, float par4, float par5, String par6Str) {
        super(par1, (int)par2, (int)par3, (int)par4, (int)par5, par6Str);
        this.x = par2;
        this.y = par3;
        this.x1 = par4;
        this.y1 = par5;
        this.displayString = par6Str;
    }

    public ResilienceButton(int i, int j, int k, String stringParams) {
        this(i, j, k, 200.0f, 20.0f, stringParams);
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int mX, int mY) {
        boolean overButton = (float)mX >= this.x && (float)mX <= this.x + this.x1 && (float)mY >= this.y && (float)mY <= this.y + this.y1;
        Utils.drawRect(this.x, this.y, this.x + this.x1, this.y + this.y1, overButton ? 1429419007 : 1426063589);
        if (this.y1 == 15.0f) {
            Resilience.getInstance().getStandardFont().drawCenteredString(this.displayString, (int)(this.x + this.x1 / 2.0f), this.y + 1.5f, -1);
        } else if (this.y1 < 30.0f) {
            Resilience.getInstance().getPanelTitleFont().drawCenteredString(this.displayString, (int)(this.x + this.x1 / 2.0f), this.y + 2.0f, -1);
        } else {
            Resilience.getInstance().getPanelTitleFont().drawCenteredString(this.displayString, (int)(this.x + this.x1 / 2.0f), (int)(this.y + 11.5f), -1);
        }
    }

    public void setY(int y) {
        this.field_146129_i = y;
        this.y = y;
    }
}

