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
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ModuleStep
extends DefaultModule {
    public ModuleStep() {
        super("Step", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Automatically steps up blocks like stairs");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.invoker.setStepHeight(Resilience.getInstance().getValues().stepHeight.getValue());
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        if (this.invoker.getWrapper().getPlayer() != null) {
            this.invoker.setStepHeight(0.5f);
        }
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

