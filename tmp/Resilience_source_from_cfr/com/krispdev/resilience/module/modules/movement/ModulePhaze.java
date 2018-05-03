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
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ModulePhaze
extends DefaultModule {
    public ModulePhaze() {
        super("Phaze", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Allows you to glitch through doors, etc.");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.invoker.setNoClip(true);
        this.invoker.setOnGround(true);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
        if (Resilience.getInstance().getWrapper().getPlayer() == null) {
            return;
        }
        this.invoker.setNoClip(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }
}

