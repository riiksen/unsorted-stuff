/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandXP
extends CommandBase {
    private static final String __OBFID = "CL_00000398";

    @Override
    public String getCommandName() {
        return "xp";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.xp.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        boolean var7;
        int var6;
        boolean var5;
        if (par2ArrayOfStr.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
        String var4 = par2ArrayOfStr[0];
        boolean bl = var5 = var4.endsWith("l") || var4.endsWith("L");
        if (var5 && var4.length() > 1) {
            var4 = var4.substring(0, var4.length() - 1);
        }
        boolean bl2 = var7 = (var6 = CommandXP.parseInt(par1ICommandSender, var4)) < 0;
        if (var7) {
            var6 *= -1;
        }
        EntityPlayerMP var3 = par2ArrayOfStr.length > 1 ? CommandXP.getPlayer(par1ICommandSender, par2ArrayOfStr[1]) : CommandXP.getCommandSenderAsPlayer(par1ICommandSender);
        if (var5) {
            if (var7) {
                var3.addExperienceLevel(- var6);
                CommandXP.notifyAdmins(par1ICommandSender, "commands.xp.success.negative.levels", var6, var3.getCommandSenderName());
            } else {
                var3.addExperienceLevel(var6);
                CommandXP.notifyAdmins(par1ICommandSender, "commands.xp.success.levels", var6, var3.getCommandSenderName());
            }
        } else {
            if (var7) {
                throw new WrongUsageException("commands.xp.failure.widthdrawXp", new Object[0]);
            }
            var3.addExperience(var6);
            CommandXP.notifyAdmins(par1ICommandSender, "commands.xp.success", var6, var3.getCommandSenderName());
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 2 ? CommandXP.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames()) : null;
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 1) {
            return true;
        }
        return false;
    }
}

