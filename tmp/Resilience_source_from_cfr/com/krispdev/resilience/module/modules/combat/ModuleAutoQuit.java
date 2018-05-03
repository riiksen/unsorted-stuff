/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleAutoQuit
extends DefaultModule {
    public static NumberValue quitHealth = new NumberValue(6.0f, 1.0f, 20.0f, "AutoQuit Health", true);

    public ModuleAutoQuit() {
        super("AutoQuit", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically quits the game when your health gets low");
    }

    @Override
    public void onHealthUpdate(EventHealthUpdate event) {
        if (event.getHealth() <= quitHealth.getValue()) {
            this.invoker.sendChatMessage("\u00a7bHello");
            this.setEnabled(false);
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

