/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.BoolValue;

public class CmdKillAuraMode
extends Command {
    public CmdKillAuraMode() {
        super("killaura mode ", "[Players/Mobs/Animals/All]", "Sets the KillAura target mode");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("mode ");
        if (args[1].trim().equalsIgnoreCase("players")) {
            Resilience.getInstance().getValues().players.setState(true);
            Resilience.getInstance().getValues().mobs.setState(false);
            Resilience.getInstance().getValues().animals.setState(false);
            Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \u00a7bPlayers");
            return true;
        }
        if (args[1].trim().equalsIgnoreCase("mobs")) {
            Resilience.getInstance().getValues().players.setState(false);
            Resilience.getInstance().getValues().mobs.setState(true);
            Resilience.getInstance().getValues().animals.setState(false);
            Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \u00a7bMobs");
            return true;
        }
        if (args[1].trim().equalsIgnoreCase("animals")) {
            Resilience.getInstance().getValues().animals.setState(true);
            Resilience.getInstance().getValues().players.setState(false);
            Resilience.getInstance().getValues().mobs.setState(false);
            Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \u00a7bAnimals");
            return true;
        }
        if (args[1].trim().equalsIgnoreCase("all")) {
            Resilience.getInstance().getValues().players.setState(true);
            Resilience.getInstance().getValues().mobs.setState(true);
            Resilience.getInstance().getValues().animals.setState(true);
            Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \u00a7bAll");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("Unknown mode \"" + args[1] + "\"");
        return true;
    }
}

