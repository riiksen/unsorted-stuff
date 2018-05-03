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
import net.minecraft.block.Block;

public class EventBlockClick
extends Cancellable
implements Event {
    private Block block;
    private int x;
    private int y;
    private int z;
    private int side;

    public EventBlockClick(int x, int y, int z, int side, Block block) {
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
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
                mod.onBlockClicked(this);
            }
        }
        catch (Exception l) {
            // empty catch block
        }
    }

    public Block getBlock() {
        return this.block;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getSide() {
        return this.side;
    }
}

