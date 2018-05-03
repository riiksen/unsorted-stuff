/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import java.util.ArrayList;

public class CmdToggle
extends Command {
    public CmdToggle() {
        super("t ", "[Mod]", "Toggles the specified mod");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("t ");
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            if (!mod.getName().equalsIgnoreCase(args[1].trim())) continue;
            mod.toggle();
            Resilience.getInstance().getLogger().infoChat("Toggled mod: \u00a7b" + mod.getName());
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("Mod not found: \u00a7c" + args[1]);
        return true;
    }
}

