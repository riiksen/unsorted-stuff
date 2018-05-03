/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandBroadcast
extends CommandBase {
    private static final String __OBFID = "CL_00000191";

    @Override
    public String getCommandName() {
        return "say";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.say.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0 || par2ArrayOfStr[0].length() <= 0) {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
        }
        IChatComponent var3 = CommandBroadcast.func_147176_a(par1ICommandSender, par2ArrayOfStr, 0, true);
        MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.announcement", par1ICommandSender.getCommandSenderName(), var3));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length >= 1 ? CommandBroadcast.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

