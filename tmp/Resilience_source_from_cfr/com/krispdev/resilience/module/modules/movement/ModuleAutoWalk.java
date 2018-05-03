/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleAutoWalk
extends DefaultModule {
    public ModuleAutoWalk() {
        super("AutoWalk", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Automatically walks");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.invoker.setForwardKeyPressed(true);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        this.invoker.setForwardKeyPressed(false);
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

