/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandWhitelist
extends CommandBase {
    private static final String __OBFID = "CL_00001186";

    @Override
    public String getCommandName() {
        return "whitelist";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.whitelist.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1) {
            if (par2ArrayOfStr[0].equals("on")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
                CommandWhitelist.notifyAdmins(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }
            if (par2ArrayOfStr[0].equals("off")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
                CommandWhitelist.notifyAdmins(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }
            if (par2ArrayOfStr[0].equals("list")) {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.whitelist.list", MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().size(), MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat().length));
                Set var3 = MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers();
                par1ICommandSender.addChatMessage(new ChatComponentText(CommandWhitelist.joinNiceString(var3.toArray(new String[var3.size()]))));
                return;
            }
            if (par2ArrayOfStr[0].equals("add")) {
                if (par2ArrayOfStr.length < 2) {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                MinecraftServer.getServer().getConfigurationManager().addToWhiteList(par2ArrayOfStr[1]);
                CommandWhitelist.notifyAdmins(par1ICommandSender, "commands.whitelist.add.success", par2ArrayOfStr[1]);
                return;
            }
            if (par2ArrayOfStr[0].equals("remove")) {
                if (par2ArrayOfStr.length < 2) {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(par2ArrayOfStr[1]);
                CommandWhitelist.notifyAdmins(par1ICommandSender, "commands.whitelist.remove.success", par2ArrayOfStr[1]);
                return;
            }
            if (par2ArrayOfStr[0].equals("reload")) {
                MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
                CommandWhitelist.notifyAdmins(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }
        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            return CommandWhitelist.getListOfStringsMatchingLastWord(par2ArrayOfStr, "on", "off", "list", "add", "remove", "reload");
        }
        if (par2ArrayOfStr.length == 2) {
            if (par2ArrayOfStr[0].equals("add")) {
                String[] var3 = MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat();
                ArrayList<String> var4 = new ArrayList<String>();
                String var5 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                String[] var6 = var3;
                int var7 = var3.length;
                int var8 = 0;
                while (var8 < var7) {
                    String var9 = var6[var8];
                    if (CommandWhitelist.doesStringStartWith(var5, var9) && !MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().contains(var9)) {
                        var4.add(var9);
                    }
                    ++var8;
                }
                return var4;
            }
            if (par2ArrayOfStr[0].equals("remove")) {
                return CommandWhitelist.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers());
            }
        }
        return null;
    }
}

