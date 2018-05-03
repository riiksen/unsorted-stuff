/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import java.util.ArrayList;

public class CmdSearchClear
extends Command {
    public CmdSearchClear() {
        super("search clear", "", "Clears the search list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        Resilience.getInstance().getValues().searchIds.clear();
        Resilience.getInstance().getValues().ticksForSearch = 71;
        Resilience.getInstance().getLogger().info("Cleared the search list");
        return true;
    }
}

