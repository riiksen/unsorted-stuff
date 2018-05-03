/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ModuleNoKnockback
extends DefaultModule {
    public ModuleNoKnockback() {
        super("NoKnockback", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Prevents knockback from entities");
    }

    @Override
    public void onPacketReceive(EventPacketReceive event) {
        Packet eventPacket = event.getPacket();
        if (eventPacket instanceof S12PacketEntityVelocity) {
            event.setCancelled(true);
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

