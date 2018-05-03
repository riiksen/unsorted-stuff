/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;

public class CommandPardonIp
extends CommandBase {
    private static final String __OBFID = "CL_00000720";

    @Override
    public String getCommandName() {
        return "pardon-ip";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender)) {
            return true;
        }
        return false;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.unbanip.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1) {
            Matcher var3 = CommandBanIp.field_147211_a.matcher(par2ArrayOfStr[0]);
            if (!var3.matches()) {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }
        } else {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().remove(par2ArrayOfStr[0]);
        CommandPardonIp.notifyAdmins(par1ICommandSender, "commands.unbanip.success", par2ArrayOfStr[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandPardonIp.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet()) : null;
    }
}

