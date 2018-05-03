/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.Random;

public class ModuleRetard
extends DefaultModule {
    private Random rand = new Random();
    private int ticks = 0;

    public ModuleRetard() {
        super("Retard", 0);
        this.setCategory(ModuleCategory.PLAYER);
        this.setDescription("Spinns ur hed arund n mackes u luk funy");
    }

    @Override
    public void onPreMotion(EventPreMotion event) {
        ++this.ticks;
        if (this.ticks > this.rand.nextInt(50)) {
            this.ticks = 0;
            this.invoker.swingItem();
        }
        if ((double)this.invoker.moveForward() < 0.1) {
            float yaw = this.rand.nextInt(360) - 180;
            float pitch = this.rand.nextInt(360) - 180;
            this.invoker.setRotationPitch(pitch);
            this.invoker.setRotationYaw(yaw);
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

