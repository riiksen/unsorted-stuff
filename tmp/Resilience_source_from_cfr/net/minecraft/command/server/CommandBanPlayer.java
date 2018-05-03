/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;

public class CommandBanPlayer
extends CommandBase {
    private static final String __OBFID = "CL_00000165";

    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.ban.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender)) {
            return true;
        }
        return false;
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0) {
            EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
            BanEntry var4 = new BanEntry(par2ArrayOfStr[0]);
            var4.setBannedBy(par1ICommandSender.getCommandSenderName());
            if (par2ArrayOfStr.length >= 2) {
                var4.setBanReason(CommandBanPlayer.func_147178_a(par1ICommandSender, par2ArrayOfStr, 1).getUnformattedText());
            }
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(var4);
            if (var3 != null) {
                var3.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
            }
        } else {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        CommandBanPlayer.notifyAdmins(par1ICommandSender, "commands.ban.success", par2ArrayOfStr[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length >= 1 ? CommandBanPlayer.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

