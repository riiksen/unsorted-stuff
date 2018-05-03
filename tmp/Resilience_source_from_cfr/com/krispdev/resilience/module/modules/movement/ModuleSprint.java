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

public class ModuleSprint
extends DefaultModule {
    public ModuleSprint() {
        super("Sprint", 46);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Forces sprint");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (!(this.invoker.isSneaking() || this.invoker.isCollidedHorizontally() || this.invoker.isOnLadder() || this.invoker.moveForward() <= 0.0f)) {
            this.invoker.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        if (this.invoker.getWrapper().getPlayer() != null) {
            this.invoker.setSprinting(false);
        }
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

