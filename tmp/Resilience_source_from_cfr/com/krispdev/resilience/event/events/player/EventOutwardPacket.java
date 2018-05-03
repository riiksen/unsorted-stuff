/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;
import java.util.ArrayList;
import net.minecraft.network.Packet;

public class EventOutwardPacket
extends Cancellable
implements Event {
    private Packet packet;
    private ArrayList<Packet> packetsList = new ArrayList();

    public EventOutwardPacket(Packet packet) {
        this.packet = packet;
    }

    @Override
    public void onEvent() {
        if (!Resilience.getInstance().isEnabled()) {
            return;
        }
        try {
            for (Listener l : Resilience.getInstance().getEventManager().eventListeners) {
                if (!(l instanceof ModListener)) continue;
                ModListener mod = (ModListener)l;
                mod.onOutwardPacket(this);
            }
        }
        catch (Exception l) {
            // empty catch block
        }
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void addPacketToList(Packet p) {
        this.packetsList.add(p);
    }

    public ArrayList<Packet> getPacketList() {
        return this.packetsList;
    }
}

