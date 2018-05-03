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
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovingObjectPosition;

public class ModuleAutoMine
extends DefaultModule {
    public ModuleAutoMine() {
        super("AutoMine", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Automatically mines when you hover over a block");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        MovingObjectPosition hover = this.invoker.getObjectMouseOver();
        if (hover.typeOfHit != null) {
            if (hover.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                this.invoker.setKeyBindAttackPressed(true);
            }
        } else {
            this.invoker.setKeyBindAttackPressed(false);
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        if (Resilience.getInstance().getWrapper().getGameSettings() != null && Resilience.getInstance().getWrapper().getPlayer() != null) {
            this.invoker.setKeyBindAttackPressed(false);
        }
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

