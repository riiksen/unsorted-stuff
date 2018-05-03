/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;

public class ModuleKillAura
extends DefaultModule {
    private EntityUtils entityUtils = new EntityUtils();
    private Timer timer = new Timer();
    private Entity target = null;

    public ModuleKillAura() {
        super("KillAura", 19);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically attacks entities");
    }

    @Override
    public void onPreMotion(EventPreMotion event) {
        this.target = null;
        this.target = this.entityUtils.getClosestEntity(Resilience.getInstance().getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
        if (this.target != null && this.entityUtils.canHit(this.target) && (!Resilience.getInstance().getValues().safeMode.getState() || this.entityUtils.withinRotation(this.target, 90.0f))) {
            this.invoker.setSprinting(false);
            this.entityUtils.faceEntity(this.target);
        }
    }

    @Override
    public void onPostMotion(EventPostMotion event) {
        if (this.target != null && this.timer.hasTimePassed(1000 / (long)Resilience.getInstance().getValues().speed.getValue()) && this.entityUtils.isWithinRange(Resilience.getInstance().getValues().range.getValue(), this.target) && this.entityUtils.canHit(this.target) && (!Resilience.getInstance().getValues().safeMode.getState() || this.entityUtils.withinRotation(this.target, 90.0f))) {
            this.entityUtils.hitEntity(this.target, Resilience.getInstance().getValues().autoBlockEnabled);
            this.timer.reset();
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().killAuraEnabled = true;
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().killAuraEnabled = false;
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

