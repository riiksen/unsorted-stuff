/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event.listeners;

import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventOutwardPacket;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.event.listeners.Listener;

public interface ModListener
extends Listener {
    public void onPreMotion(EventPreMotion var1);

    public void onUpdate(EventOnUpdate var1);

    public void onPostMotion(EventPostMotion var1);

    public void onHealthUpdate(EventHealthUpdate var1);

    public void onPacketReceive(EventPacketReceive var1);

    public void onBlockClicked(EventBlockClick var1);

    public void onGameShutdown(EventGameShutdown var1);

    public void onRender(EventOnRender var1);

    public void onClick(EventOnClick var1);

    public void onValueChange(EventValueChange var1);

    public void onOutwardPacket(EventOutwardPacket var1);
}

