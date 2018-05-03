/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.gui.screens.managers.modules;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleSlot;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiModuleManager
extends GuiScreen {
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private GuiScreen parent;
    public static ArrayList<DefaultModule> visibleModules = new ArrayList();
    private GuiModuleSlot moduleSlot;
    private String state = "Waiting for command...";
    private boolean bind;

    public GuiModuleManager(GuiScreen parent) {
        visibleModules.clear();
        this.parent = parent;
        for (DefaultModule module : Resilience.getInstance().getModuleManager().moduleList) {
            visibleModules.add(module);
        }
    }

    @Override
    public void initGui() {
        this.moduleSlot = new GuiModuleSlot(this.invoker.getWrapper().getMinecraft(), this);
        this.moduleSlot.registerScrollButtons(7, 8);
        this.invoker.addButton(this, new ResilienceButton(1, this.width / 2 - 100, this.height - 54 + 1 + 2, 200.0f, 20.0f, "Toggle"));
        this.invoker.addButton(this, new ResilienceButton(3, this.width / 2 - 100, this.height - 30 + 1 + 2, 200.0f, 20.0f, "Reload"));
        this.invoker.addButton(this, new ResilienceButton(5, this.width / 2 + 104, this.height - 53 + 2, 100.0f, 20.0f, "Reset"));
        this.invoker.addButton(this, new ResilienceButton(6, this.width / 2 - 204, this.height - 53 + 2, 100.0f, 20.0f, "Remove"));
        this.invoker.addButton(this, new ResilienceButton(2, this.width / 2 - 204, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Change Bind"));
        this.invoker.addButton(this, new ResilienceButton(4, this.width / 2 + 104, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Clear Bind"));
        this.invoker.addButton(this, new ResilienceButton(0, 4.0f, 4.0f, 50.0f, 20.0f, "Back"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.moduleSlot.drawScreen(i, j, f);
        Resilience.getInstance().getStandardFont().drawCenteredString(this.state, this.invoker.getWidth() / 2, 4.0f, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        DefaultModule module = null;
        try {
            module = visibleModules.get(this.moduleSlot.getSelectedSlot());
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.invoker.getId(btn) == 1 && module != null) {
            module.toggle();
            this.state = "\u00a7bToggled " + module.getName();
            Resilience.getInstance().getFileManager().saveEnabledMods(new String[0]);
        } else if (this.invoker.getId(btn) == 2 && module != null) {
            this.state = "\u00a7bPress any key now...";
            this.bind = true;
        } else if (this.invoker.getId(btn) == 5 && module != null) {
            try {
                int index = Resilience.getInstance().getModuleManager().moduleList.indexOf(module);
                Class clazz = module.getClass();
                String pack = clazz.getPackage().getName();
                String name = clazz.getSimpleName();
                Class newClass = Class.forName(String.valueOf(pack) + "." + name);
                Object obj = newClass.newInstance();
                if (obj instanceof DefaultModule) {
                    DefaultModule mod = (DefaultModule)obj;
                    Resilience.getInstance().getModuleManager().moduleList.remove(index);
                    Resilience.getInstance().getModuleManager().moduleList.add(index, mod);
                    visibleModules.remove(index);
                    visibleModules.add(index, mod);
                    Resilience.getInstance().getFileManager().saveBinds(new String[0]);
                } else {
                    this.state = "\u00a7cA strange error occured! Object is not a module???";
                }
                this.state = "\u00a7bReset " + module.getName() + " to default settings";
            }
            catch (Exception e) {
                e.printStackTrace();
                this.state = "\u00a7cError reseting " + module.getName();
            }
        } else if (this.invoker.getId(btn) == 4 && module != null) {
            module.setKeyBind(0);
            Resilience.getInstance().getFileManager().saveBinds(new String[0]);
        } else if (this.invoker.getId(btn) == 3) {
            Resilience.getInstance().getModuleManager().moduleList.clear();
            Resilience.getInstance().getModuleManager().instantiateModules();
            visibleModules.clear();
            for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
                visibleModules.add(mod);
            }
            this.state = "\u00a7bReloaded all modules";
        } else if (this.invoker.getId(btn) == 6 && module != null) {
            this.state = "\u00a7bRemoved " + module.getName();
            module.setEnabled(false);
            Resilience.getInstance().getEventManager().unregisterGameListener(module);
            int index = Resilience.getInstance().getModuleManager().moduleList.indexOf(module);
            Resilience.getInstance().getModuleManager().moduleList.remove(index);
            visibleModules.remove(index);
        } else {
            this.invoker.displayScreen(this.parent);
        }
    }

    @Override
    public void keyTyped(char c, int i) {
        if (this.bind) {
            this.bind = false;
            DefaultModule module = visibleModules.get(this.moduleSlot.getSelectedSlot());
            if (i == 1) {
                this.state = "\u00a7bCancelled keybind";
                return;
            }
            module.setKeyBind(i);
            Resilience.getInstance().getFileManager().saveBinds(new String[0]);
            this.state = "\u00a7bSet the keybind of " + module.getName() + " to " + Keyboard.getKeyName((int)i);
        }
    }
}

