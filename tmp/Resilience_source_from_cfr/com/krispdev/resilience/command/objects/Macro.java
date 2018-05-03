/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.objects;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.interfaces.Bindable;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;

public class Macro
implements Bindable,
Listener {
    public static ArrayList<Macro> macroList = new ArrayList();
    private int key;
    private String command;

    public Macro(int key, String command) {
        this.key = key;
        this.command = command;
        Resilience.getInstance().getEventManager().registerGameListener(this);
    }

    public int getKey() {
        return this.key;
    }

    public String getCommand() {
        return this.command;
    }

    public void onKeyPress(int keyCode) {
    }

    @Override
    public void onKeyDown(int keyCode) {
        if (keyCode == this.key && Resilience.getInstance().isEnabled()) {
            Resilience.getInstance().getInvoker().sendChatMessage(this.command);
        }
    }
}

