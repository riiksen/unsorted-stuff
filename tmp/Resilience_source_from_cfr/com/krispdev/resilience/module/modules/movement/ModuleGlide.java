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
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleGlide
extends DefaultModule {
    public ModuleGlide() {
        super("Glide", 34);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Gives you the ability to glide");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (!(this.invoker.getMotionY() > -0.15 || this.invoker.isInWater() || this.invoker.isOnGround() || this.invoker.isOnLadder() || Resilience.getInstance().getValues().flightEnabled)) {
            this.invoker.setMotionY(-0.15);
            this.invoker.setOnGround(true);
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

