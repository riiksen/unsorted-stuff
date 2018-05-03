/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import java.util.ArrayList;

public class CmdAllOff
extends Command {
    public CmdAllOff() {
        super("alloff", "", "Turns off all enabled mods");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            if (!mod.isEnabled()) continue;
            mod.setEnabled(false);
        }
        Resilience.getInstance().getFileManager().saveEnabledMods(new String[0]);
        return true;
    }
}

