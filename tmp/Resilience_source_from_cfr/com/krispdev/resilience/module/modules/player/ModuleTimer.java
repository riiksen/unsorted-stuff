/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.text.DecimalFormat;

public class ModuleTimer
extends DefaultModule {
    public ModuleTimer() {
        super("Speed", 0);
        this.setCategory(ModuleCategory.PLAYER);
        this.setDescription("Slows down/speeds up time");
        this.setSave(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
        this.setDisplayName("Timer (" + new DecimalFormat("#.#").format(Resilience.getInstance().getValues().timerSpeed.getValue()) + ")");
        this.invoker.setTimerSpeed(Resilience.getInstance().getValues().timerSpeed.getValue());
    }

    @Override
    public void onValueChange(EventValueChange event) {
        if (event.getValue() == Resilience.getInstance().getValues().timerSpeed) {
            this.invoker.setTimerSpeed(Resilience.getInstance().getValues().timerSpeed.getValue());
            this.setDisplayName("Timer (" + new DecimalFormat("#.#").format(Resilience.getInstance().getValues().timerSpeed.getValue()) + ")");
        }
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
        this.invoker.setTimerSpeed(1.0f);
    }
}

