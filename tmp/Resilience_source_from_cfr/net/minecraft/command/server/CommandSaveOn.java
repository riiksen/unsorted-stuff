/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOn
extends CommandBase {
    private static final String __OBFID = "CL_00000873";

    @Override
    public String getCommandName() {
        return "save-on";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.save-on.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        MinecraftServer var3 = MinecraftServer.getServer();
        boolean var4 = false;
        int var5 = 0;
        while (var5 < var3.worldServers.length) {
            if (var3.worldServers[var5] != null) {
                WorldServer var6 = var3.worldServers[var5];
                if (var6.levelSaving) {
                    var6.levelSaving = false;
                    var4 = true;
                }
            }
            ++var5;
        }
        if (!var4) {
            throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
        }
        CommandSaveOn.notifyAdmins(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}

