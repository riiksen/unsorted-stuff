/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

public class ModuleTriggerBot
extends DefaultModule {
    private EntityUtils entityUtils = new EntityUtils();
    private Timer timer = new Timer();

    public ModuleTriggerBot() {
        super("TriggerBot", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically hits the entity you hover over");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        Entity e;
        if (this.invoker.getObjectMouseOver() != null && (e = this.invoker.getObjectMouseOver().entityHit) != null && this.entityUtils.canHit(e, Resilience.getInstance().getValues().range.getValue()) && !this.entityUtils.isEntityFriend(e) && this.timer.hasTimePassed(1000 / (long)Resilience.getInstance().getValues().speed.getValue())) {
            this.entityUtils.hitEntity(e, Resilience.getInstance().getValues().autoBlockEnabled);
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

