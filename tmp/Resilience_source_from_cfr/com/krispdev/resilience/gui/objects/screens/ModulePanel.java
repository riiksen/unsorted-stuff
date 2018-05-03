/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.DefaultButton;
import com.krispdev.resilience.gui.objects.buttons.ModuleButton;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;
import java.util.ArrayList;

public class ModulePanel
extends DefaultPanel {
    private int count = 0;
    private int buttonSize = 15;

    public ModulePanel(String title, int x, int y, int x1, int y1, boolean visible, ModuleCategory category) {
        super(title, x, y, x1, y1, visible);
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            if (mod.getCategory() != category) continue;
            this.addButton(mod);
        }
    }

    private void addButton(DefaultModule mod) {
        this.buttons.add(new ModuleButton(this.getX() + 4 - 3, this.getY() + this.buttonSize * this.count + 1 - 4, this.getX1() - 4 + 3, this.getY() + this.buttonSize * this.count - 2 - 4, this, mod));
        ++this.count;
    }

    @Override
    public void draw(int i, int j) {
        super.draw(i, j);
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + this.buttons.size() * this.buttonSize + 18, -1727790076);
            for (DefaultButton btn : this.buttons) {
                btn.draw(i, j);
                if (!(btn instanceof ModuleButton)) continue;
                ModuleButton button = (ModuleButton)btn;
                if (i >= btn.getX() + this.getDragX() && i <= btn.getX1() + this.getDragX() && j >= btn.getY() + this.getDragY() + 21 && j <= btn.getY1() + this.getDragY() + 38) {
                    button.setOverButton(true);
                    continue;
                }
                button.setOverButton(false);
            }
        }
    }

    @Override
    public boolean onClick(int i, int j, int k) {
        if (this.isExtended()) {
            for (DefaultButton btn : this.buttons) {
                if (!btn.onClick(i, j, k)) continue;
                return true;
            }
        }
        if (super.onClick(i, j, k)) {
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(char c, int i) {
        for (DefaultButton btn : this.buttons) {
            btn.keyTyped(c, i);
        }
    }
}

