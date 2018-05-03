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
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ModuleAntiAFK
extends DefaultModule {
    private Timer timer = new Timer();

    public ModuleAntiAFK() {
        super("AntiAFK", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Jumps every X seconds to prevent AFK");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.timer.hasTimePassed((long)Resilience.getInstance().getValues().antiAFKSeconds.getValue() * 1000) && Resilience.getInstance().getWrapper().getPlayer() != null && this.invoker.isOnGround()) {
            this.timer.reset();
            this.invoker.jump();
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

