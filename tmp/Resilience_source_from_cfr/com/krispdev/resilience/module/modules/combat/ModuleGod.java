/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleGod
extends DefaultModule {
    private int ticks = 0;

    public ModuleGod() {
        super("God", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Makes you invinsible!");
    }

    @Override
    public void onPreMotion(EventPreMotion event) {
        ++this.ticks;
        if (this.ticks > -1) {
            this.ticks = 0;
            event.setCancelled(true);
        }
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

