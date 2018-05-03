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
import com.krispdev.resilience.utilities.value.Value;
import java.util.ArrayList;

public class EventValueChange
extends Cancellable
implements Event {
    private Value value;

    public EventValueChange(Value value) {
        this.value = value;
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
                mod.onValueChange(this);
            }
        }
        catch (Exception l) {
            // empty catch block
        }
    }

    public Value getValue() {
        return this.value;
    }
}

