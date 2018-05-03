/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleTwerk
extends DefaultModule {
    private boolean skipTick;

    public ModuleTwerk() {
        super("Twerk", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Sneaks and unsneaks. AKA twerks.");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (!this.skipTick) {
            this.skipTick = true;
            this.invoker.setSneakKeyPressed(true);
        } else {
            this.skipTick = false;
            this.invoker.setSneakKeyPressed(false);
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
        this.invoker.setSneakKeyPressed(false);
    }
}

