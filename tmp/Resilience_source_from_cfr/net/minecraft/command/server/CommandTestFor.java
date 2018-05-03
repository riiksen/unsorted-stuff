/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBlockLogic;

public class CommandTestFor
extends CommandBase {
    private static final String __OBFID = "CL_00001182";

    @Override
    public String getCommandName() {
        return "testfor";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.testfor.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        if (!(par1ICommandSender instanceof CommandBlockLogic)) {
            throw new CommandException("commands.testfor.failed", new Object[0]);
        }
        CommandTestFor.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

