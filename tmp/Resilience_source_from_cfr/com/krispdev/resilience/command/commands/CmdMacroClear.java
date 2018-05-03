/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Macro;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import java.util.ArrayList;

public class CmdMacroClear
extends Command {
    public CmdMacroClear() {
        super("macros clear", "", "Clears the macros");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        Macro.macroList.clear();
        Resilience.getInstance().getLogger().infoChat("Cleared all macros");
        Resilience.getInstance().getFileManager().saveMacros(new String[0]);
        return true;
    }
}

