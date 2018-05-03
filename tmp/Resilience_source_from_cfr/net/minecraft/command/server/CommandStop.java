/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop
extends CommandBase {
    private static final String __OBFID = "CL_00001132";

    @Override
    public String getCommandName() {
        return "stop";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.stop.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        CommandStop.notifyAdmins(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.getServer().initiateShutdown();
    }
}

