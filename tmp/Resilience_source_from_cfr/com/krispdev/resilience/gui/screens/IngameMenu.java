/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.online.gui.GuiRegister;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class IngameMenu
extends GuiScreen {
    private GuiScreen parent;
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private ResilienceButton enableButton;

    public IngameMenu(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        this.drawDefaultBackground();
        super.drawScreen(x, y, f);
    }

    @Override
    public void initGui() {
        this.invoker.addButton(this, new ResilienceButton(0, this.invoker.getWidth() / 2 - 100, this.invoker.getHeight() / 4 - 10, 200.0f, 20.0f, "Module Manager"));
        this.enableButton = new ResilienceButton(1, this.invoker.getWidth() / 2 - 100, Resilience.getInstance().getInvoker().getHeight() / 4 - 34, 200.0f, 20.0f, String.valueOf(Resilience.getInstance().isEnabled() ? "Disable" : "Enable") + " ".concat(Resilience.getInstance().getName()));
        this.invoker.addButton(this, this.enableButton);
        this.invoker.addButton(this, new ResilienceButton(3, this.invoker.getWidth() / 2 - 100, this.invoker.getHeight() / 4 - 10 + 24, 200.0f, 20.0f, "Resilience Online"));
        this.invoker.addButton(this, new ResilienceButton(2, 4.0f, 4.0f, 50.0f, 20.0f, "Back"));
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (this.invoker.getId(btn) == 0) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiModuleManager(this));
        }
        if (Resilience.getInstance().getInvoker().getId(btn) == 1) {
            Resilience.getInstance().setEnabled(!Resilience.getInstance().isEnabled());
            if (Resilience.getInstance().isEnabled()) {
                this.enableButton.displayString = "Disable ".concat(Resilience.getInstance().getName());
                Resilience.getInstance().getFileManager().loadEnabledMods();
            } else {
                this.enableButton.displayString = "Enable ".concat(Resilience.getInstance().getName());
                for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
                    if (mod.getCategory() == ModuleCategory.COMBAT_EXTENSION) continue;
                    mod.setEnabled(false);
                }
                Resilience.getInstance().getInvoker().setGammaSetting(1.0f);
                int i = 0;
                while (i < 150) {
                    Resilience.getInstance().getInvoker().addChatMessage("");
                    ++i;
                }
                Resilience.getInstance().getInvoker().displayScreen(null);
            }
        }
        if (this.invoker.getId(btn) == 2) {
            this.invoker.displayScreen(this.parent);
        } else if (this.invoker.getId(btn) == 3) {
            new Thread(){

                @Override
                public void run() {
                    if (FileUtils.containsString(Resilience.getInstance().getFileManager().online, Resilience.getInstance().getInvoker().getSessionUsername(), 0)) {
                        String result = Utils.sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&online=true&channel=" + Resilience.getInstance().getValues().userChannel);
                        if (result.equals("")) {
                            Resilience.getInstance().getInvoker().displayScreen(new GuiFriendManager(new IngameMenu(Resilience.getInstance().getClickGui())));
                        } else {
                            Resilience.getInstance().getInvoker().displayScreen(new GuiLogin(new IngameMenu(Resilience.getInstance().getClickGui())));
                        }
                    } else {
                        Resilience.getInstance().getInvoker().displayScreen(new GuiRegister(new IngameMenu(Resilience.getInstance().getClickGui()), true));
                    }
                }
            }.start();
        }
    }

}

