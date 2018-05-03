/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleNoFall
extends DefaultModule {
    private boolean wasOnGround = false;

    public ModuleNoFall() {
        super("NoFall", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Avoids fall damage");
    }

    @Override
    public void onPreMotion(EventPreMotion event) {
        this.wasOnGround = this.invoker.isOnGround();
        this.invoker.setOnGround(true);
    }

    @Override
    public void onPostMotion(EventPostMotion event) {
        this.invoker.setOnGround(this.wasOnGround);
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

