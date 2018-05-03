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

public class ModuleInfiniteJump
extends DefaultModule {
    public ModuleInfiniteJump() {
        super("Infinite Jump", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Allows you to jump all the time");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.invoker.setOnGround(true);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

