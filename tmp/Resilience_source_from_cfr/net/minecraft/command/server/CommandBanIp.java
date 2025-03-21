/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.IChatComponent;

public class CommandBanIp
extends CommandBase {
    public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final String __OBFID = "CL_00000139";

    @Override
    public String getCommandName() {
        return "ban-ip";
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
        return "commands.banip.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1) {
            Matcher var3 = field_147211_a.matcher(par2ArrayOfStr[0]);
            IChatComponent var4 = null;
            if (par2ArrayOfStr.length >= 2) {
                var4 = CommandBanIp.func_147178_a(par1ICommandSender, par2ArrayOfStr, 1);
            }
            if (var3.matches()) {
                this.func_147210_a(par1ICommandSender, par2ArrayOfStr[0], var4 == null ? null : var4.getUnformattedText());
            } else {
                EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
                if (var5 == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.func_147210_a(par1ICommandSender, var5.getPlayerIP(), var4 == null ? null : var4.getUnformattedText());
            }
        } else {
            throw new WrongUsageException("commands.banip.usage", new Object[0]);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandBanIp.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    protected void func_147210_a(ICommandSender p_147210_1_, String p_147210_2_, String p_147210_3_) {
        BanEntry var4 = new BanEntry(p_147210_2_);
        var4.setBannedBy(p_147210_1_.getCommandSenderName());
        if (p_147210_3_ != null) {
            var4.setBanReason(p_147210_3_);
        }
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().put(var4);
        List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerList(p_147210_2_);
        Object[] var6 = new String[var5.size()];
        int var7 = 0;
        for (EntityPlayerMP var9 : var5) {
            var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            var6[var7++] = var9.getCommandSenderName();
        }
        if (var5.isEmpty()) {
            CommandBanIp.notifyAdmins(p_147210_1_, "commands.banip.success", p_147210_2_);
        } else {
            CommandBanIp.notifyAdmins(p_147210_1_, "commands.banip.success.players", p_147210_2_, CommandBanIp.joinNiceString(var6));
        }
    }
}

