/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import java.util.List;

public class ModuleWaypoints
extends DefaultModule {
    public ModuleWaypoints() {
        super("Waypoints", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Shows waypoints. (Add with .waypoint add...)");
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Waypoint w : Waypoint.waypointsList) {
            w.draw();
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

