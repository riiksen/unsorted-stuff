/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import java.util.ArrayList;
import java.util.Random;

public class CmdSearchAdd
extends Command {
    private Random rand = new Random();

    public CmdSearchAdd() {
        super("search add ", "[Block Id]", "Adds a block to the search list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        int r = this.rand.nextInt(100) + 1;
        int g = this.rand.nextInt(100) + 1;
        int b = this.rand.nextInt(100) + 1;
        if (!this.containsId(Integer.parseInt(cmd.split("add ")[1].trim()))) {
            Resilience.getInstance().getValues().searchIds.add(new Float[]{Float.valueOf(Float.parseFloat(cmd.split("add ")[1])), Float.valueOf((float)r / 100.0f), Float.valueOf((float)g / 100.0f), Float.valueOf((float)b / 100.0f)});
            Resilience.getInstance().getValues().ticksForSearch = 71;
            Resilience.getInstance().getLogger().infoChat("Added a block with id " + cmd.split("add ")[1] + " to the search list");
        } else {
            Resilience.getInstance().getLogger().warningChat("Block already on the list!");
        }
        return true;
    }

    public boolean containsId(float id) {
        for (Float[] list : Resilience.getInstance().getValues().searchIds) {
            if (list[0].floatValue() != id) continue;
            return true;
        }
        return false;
    }
}

