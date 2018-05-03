/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.components.Component;
import com.krispdev.resilience.gui.objects.components.XrayBlockComponent;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.XrayUtils;
import java.util.ArrayList;

public class XrayBlocksPanel
extends DefaultPanel {
    private ArrayList<Component> components = new ArrayList();
    private int[] blockIds = new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 21, 22, 23, 29, 33, 41, 42, 46, 47, 48, 49, 52, 56, 57, 58, 73, 116, 129, 133, 152, 158};

    public XrayBlocksPanel(String title, int x, int y, int x1, int y1, boolean visible) {
        super(title, x, y, x1, y1, visible);
        int size = 22;
        int xPos = 4;
        int yPos = 4;
        int i = 0;
        while (i < this.blockIds.length) {
            this.components.add(new XrayBlockComponent(xPos + this.getOriginalX(), yPos + 17 + this.getOriginalY(), xPos + size + this.getOriginalX(), yPos + size + 17 + this.getOriginalY(), this.blockIds[i], Resilience.getInstance().getXrayUtils().shouldRenderBlock(this.blockIds[i])));
            if ((xPos += size + 4) + size + 4 >= 240) {
                xPos = 4;
                yPos += size + 4;
            }
            ++i;
        }
    }

    @Override
    public void draw(int x, int y) {
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + 110 + 15, -1727790076);
            for (Component c : this.components) {
                c.setX((int)(c.getOX() + (float)this.getDragX()));
                c.setY((int)(c.getOY() + (float)this.getDragY()));
                c.setX1((int)(c.getOX1() + (float)this.getDragX()));
                c.setY1((int)(c.getOY1() + (float)this.getDragY()));
                c.draw(x, y);
            }
        }
        super.draw(x, y);
    }

    @Override
    public boolean onClick(int x, int y, int btn) {
        if (this.isExtended()) {
            for (Component c : this.components) {
                if (!c.onClick(x, y, btn)) continue;
                return true;
            }
        }
        if (super.onClick(x, y, btn)) {
            return true;
        }
        return false;
    }

    @Override
    public void onMouseButtonUp(int x, int y, int btn) {
        super.onMouseButtonUp(x, y, btn);
    }
}

