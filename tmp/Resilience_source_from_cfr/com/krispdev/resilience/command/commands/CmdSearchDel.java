/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import java.util.ArrayList;

public class CmdSearchDel
extends Command {
    public CmdSearchDel() {
        super("search del ", "[Block Id]", "Deletes a block from the search list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        float id = Float.parseFloat(cmd.split("del ")[1]);
        int iterator = 0;
        while (iterator < Resilience.getInstance().getValues().searchIds.size()) {
            if (Resilience.getInstance().getValues().searchIds.get(iterator)[0].floatValue() == id) {
                Resilience.getInstance().getValues().searchIds.remove(iterator);
                Resilience.getInstance().getValues().ticksForSearch = 71;
                Resilience.getInstance().getLogger().infoChat("Removed block from the search list");
                return true;
            }
            ++iterator;
        }
        Resilience.getInstance().getLogger().warningChat("Block not found!");
        return true;
    }
}

