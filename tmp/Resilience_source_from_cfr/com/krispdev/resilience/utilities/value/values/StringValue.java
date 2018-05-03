/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.utilities.value.values;

import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.utilities.value.Value;

public class StringValue
extends Value {
    private String value;

    public StringValue(String name, String value) {
        super(name);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        EventValueChange eventChange = new EventValueChange(this);
        eventChange.onEvent();
        if (!eventChange.isCancelled()) {
            this.value = value;
        } else {
            eventChange.setCancelled(false);
        }
    }
}

