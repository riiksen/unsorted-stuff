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

public class ModuleSpider
extends DefaultModule {
    public ModuleSpider() {
        super("Spider", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Climbs up walls");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.invoker.isCollidedHorizontally()) {
            this.invoker.setMotionY(0.2);
            float var6 = 0.15f;
            if (this.invoker.getMotionX() < (double)(- var6)) {
                this.invoker.setMotionX(- var6);
            }
            if (this.invoker.getMotionX() > (double)var6) {
                this.invoker.setMotionX(var6);
            }
            if (this.invoker.getMotionZ() < (double)(- var6)) {
                this.invoker.setMotionZ(- var6);
            }
            if (this.invoker.getMotionZ() > (double)var6) {
                this.invoker.setMotionZ(var6);
            }
            this.invoker.setFallDistance(0.0f);
            if (this.invoker.getMotionY() < -0.15) {
                this.invoker.setMotionY(-0.15);
            }
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

