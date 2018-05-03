/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import java.util.List;

public class CmdTrackClear
extends Command {
    public CmdTrackClear() {
        super("track clear", "", "Clears the track line");
    }

    @Override
    public boolean recieveCommand(String cmd) {
        Resilience.getInstance().getValues().trackPosList.clear();
        Resilience.getInstance().getLogger().infoChat("Cleared the track line");
        return true;
    }
}

