/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.item.ItemStack;

public class ArmourStatusPanel
extends DefaultPanel {
    private int count = 4;

    public ArmourStatusPanel(String title, int x, int y, int x1, int y1, boolean visible) {
        super(title, x, y, x1, y1, visible);
    }

    @Override
    public void draw(int i, int j) {
        this.count = 0;
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + 50, -1727790076);
            ItemStack[] arritemStack = Resilience.getInstance().getInvoker().getArmourInventory();
            int n = arritemStack.length;
            int n2 = 0;
            while (n2 < n) {
                ItemStack item = arritemStack[n2];
                if (item != null) {
                    Utils.drawItemTag(this.getX() + this.count + 84, this.getY() + 17 + 8, item);
                }
                this.count -= 25;
                ++n2;
            }
        }
        super.draw(i, j);
    }
}

