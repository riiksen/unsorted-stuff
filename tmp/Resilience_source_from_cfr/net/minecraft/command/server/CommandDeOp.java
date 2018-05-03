/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class CommandDeOp
extends CommandBase {
    private static final String __OBFID = "CL_00000244";

    @Override
    public String getCommandName() {
        return "deop";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.deop.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1 || par2ArrayOfStr[0].length() <= 0) {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
        MinecraftServer.getServer().getConfigurationManager().removeOp(par2ArrayOfStr[0]);
        CommandDeOp.notifyAdmins(par1ICommandSender, "commands.deop.success", par2ArrayOfStr[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandDeOp.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getOps()) : null;
    }
}

