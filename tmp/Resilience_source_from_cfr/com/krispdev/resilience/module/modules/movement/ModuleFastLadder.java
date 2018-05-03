/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import org.lwjgl.input.Keyboard;

public class ModuleFastLadder
extends DefaultModule {
    private int ticks = 0;

    public ModuleFastLadder() {
        super("FastLadder", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Climbs up ladders faster than usual");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        ++this.ticks;
        if (this.invoker.isOnLadder() && Keyboard.isKeyDown((int)this.invoker.getForwardCode())) {
            this.invoker.setMotionY(0.25);
        } else if (this.invoker.isOnLadder() && !Keyboard.isKeyDown((int)this.invoker.getForwardCode())) {
            this.invoker.setMotionY(-0.25);
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

