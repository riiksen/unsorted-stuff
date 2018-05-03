/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.module.modules.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.gui.screens.ResilienceConsole;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class ModuleConsole
extends DefaultModule {
    public ModuleConsole() {
        super("Console", 12);
        this.setCategory(ModuleCategory.GUI);
        this.setDescription("Type commands and make the client do stuff");
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (Keyboard.isKeyDown((int)this.getKeyCode()) && !(this.invoker.getCurrentScreen() instanceof GuiChat)) {
            this.invoker.displayScreen(new ResilienceConsole());
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

