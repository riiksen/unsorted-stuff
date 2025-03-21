/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.util.MovingObjectPosition;

public class ModuleCriticals
extends DefaultModule {
    public ModuleCriticals() {
        super("Criticals", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically lands criticals when you click");
    }

    @Override
    public void onClick(EventOnClick event) {
        if (event.getButton() == 0 && this.invoker.isOnGround() && !this.invoker.isInWater() && (this.invoker.getObjectMouseOver() != null && this.invoker.getObjectMouseOver().typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY || Resilience.getInstance().getValues().killAuraEnabled)) {
            this.invoker.setMotionY(0.29);
            this.invoker.setFallDistance(0.289f);
            this.invoker.setOnGround(false);
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

