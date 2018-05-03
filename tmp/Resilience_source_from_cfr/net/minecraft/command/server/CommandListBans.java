/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandListBans
extends CommandBase {
    private static final String __OBFID = "CL_00000596";

    @Override
    public String getCommandName() {
        return "banlist";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if ((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive()) && super.canCommandSenderUseCommand(par1ICommandSender)) {
            return true;
        }
        return false;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.banlist.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips")) {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().size()));
            par1ICommandSender.addChatMessage(new ChatComponentText(CommandListBans.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet().toArray())));
        } else {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.players", MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().size()));
            par1ICommandSender.addChatMessage(new ChatComponentText(CommandListBans.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet().toArray())));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandListBans.getListOfStringsMatchingLastWord(par2ArrayOfStr, "players", "ips") : null;
    }
}

