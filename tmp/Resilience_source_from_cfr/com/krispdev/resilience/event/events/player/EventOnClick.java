/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;
import java.util.ArrayList;

public class EventOnClick
implements Event {
    private int btn = 0;

    public EventOnClick(int btn) {
        this.btn = btn;
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
                mod.onClick(this);
            }
        }
        catch (Exception l) {
            // empty catch block
        }
    }

    public int getButton() {
        return this.btn;
    }
}

