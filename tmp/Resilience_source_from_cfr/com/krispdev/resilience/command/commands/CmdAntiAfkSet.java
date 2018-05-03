/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class CmdAntiAfkSet
extends Command {
    public CmdAntiAfkSet() {
        super("antiafk set ", "[Delay in Seconds]", "Sets the AntiAFK delay");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("set ");
        Resilience.getInstance().getValues().antiAFKSeconds.setValue(Float.parseFloat(args[1]));
        Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
        Resilience.getInstance().getLogger().infoChat("Set the AntiAFK delay to " + args[1]);
        return true;
    }
}

