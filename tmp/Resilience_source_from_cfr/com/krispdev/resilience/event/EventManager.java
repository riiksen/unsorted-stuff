/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.event;

import com.krispdev.resilience.event.Manageable;
import com.krispdev.resilience.event.listeners.Listener;
import java.util.ArrayList;

public class EventManager
implements Manageable {
    public ArrayList<Listener> eventListeners = new ArrayList();
    public ArrayList<Listener> gameListeners = new ArrayList();

    public void registerGameListener(Listener l) {
        if (!this.gameListeners.contains(l)) {
            this.gameListeners.add(l);
        }
    }

    public void unregisterGameListener(Listener l) {
        try {
            if (this.gameListeners.contains(l)) {
                this.gameListeners.remove(this.gameListeners.indexOf(l));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(Listener l) {
        if (!this.eventListeners.contains(l)) {
            this.eventListeners.add(l);
        }
    }

    @Override
    public void unregister(Listener l) {
        try {
            if (this.eventListeners.contains(l)) {
                this.eventListeners.remove(this.eventListeners.indexOf(l));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        this.eventListeners.clear();
    }
}

