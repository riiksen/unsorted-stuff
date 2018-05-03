/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout
extends CommandBase {
    private static final String __OBFID = "CL_00000999";

    @Override
    public String getCommandName() {
        return "setidletimeout";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.setidletimeout.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1) {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
        int var3 = CommandSetPlayerTimeout.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[0], 0);
        MinecraftServer.getServer().func_143006_e(var3);
        CommandSetPlayerTimeout.notifyAdmins(par1ICommandSender, "commands.setidletimeout.success", var3);
    }
}

