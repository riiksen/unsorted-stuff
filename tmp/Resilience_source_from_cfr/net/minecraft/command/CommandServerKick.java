/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class CommandServerKick
extends CommandBase {
    private static final String __OBFID = "CL_00000550";

    @Override
    public String getCommandName() {
        return "kick";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.kick.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1) {
            EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
            String var4 = "Kicked by an operator.";
            boolean var5 = false;
            if (var3 == null) {
                throw new PlayerNotFoundException();
            }
            if (par2ArrayOfStr.length >= 2) {
                var4 = CommandServerKick.func_147178_a(par1ICommandSender, par2ArrayOfStr, 1).getUnformattedText();
                var5 = true;
            }
            var3.playerNetServerHandler.kickPlayerFromServer(var4);
            if (var5) {
                CommandServerKick.notifyAdmins(par1ICommandSender, "commands.kick.success.reason", var3.getCommandSenderName(), var4);
            } else {
                CommandServerKick.notifyAdmins(par1ICommandSender, "commands.kick.success", var3.getCommandSenderName());
            }
        } else {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length >= 1 ? CommandServerKick.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

