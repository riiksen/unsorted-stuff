/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;

public class CmdPrefixChange
extends Command {
    public CmdPrefixChange() {
        super("cmdprefix set ", "[New Prefix]", "Sets the command prefix for chat commands");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("set ");
        Resilience.getInstance().setCmdPrefix(args[1]);
        Resilience.getInstance().getLogger().infoChat("Set the command prefix to " + Resilience.getInstance().getCmdPrefix());
        Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
        return true;
    }
}

