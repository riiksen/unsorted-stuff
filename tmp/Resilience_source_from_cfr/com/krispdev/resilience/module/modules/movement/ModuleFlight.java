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
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import org.lwjgl.input.Keyboard;

public class ModuleFlight
extends DefaultModule {
    public ModuleFlight() {
        super("Flight", 33);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Allows you to fly");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().flightEnabled = true;
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.setDisplayName("Flight (" + Math.round(Resilience.getInstance().getValues().flySpeed.getValue()) + ")");
        this.invoker.setMotionX(0.0);
        this.invoker.setMotionY(0.0);
        this.invoker.setMotionZ(0.0);
        this.invoker.setLandMovementFactor(Resilience.getInstance().getValues().flySpeed.getValue() / 3.0f);
        this.invoker.setJumpMovementFactor(Resilience.getInstance().getValues().flySpeed.getValue() / 3.0f);
        if (Keyboard.isKeyDown((int)this.invoker.getJumpCode())) {
            this.invoker.setMotionY(this.invoker.getMotionY() + (double)(Resilience.getInstance().getValues().flySpeed.getValue() / 4.0f));
        }
        if (Keyboard.isKeyDown((int)this.invoker.getSneakCode())) {
            this.invoker.setMotionY(this.invoker.getMotionY() - (double)(Resilience.getInstance().getValues().flySpeed.getValue() / 4.0f));
        }
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().flightEnabled = false;
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

