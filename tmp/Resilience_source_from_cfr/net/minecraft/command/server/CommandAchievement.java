/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.command.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.IChatComponent;

public class CommandAchievement
extends CommandBase {
    private static final String __OBFID = "CL_00000113";

    @Override
    public String getCommandName() {
        return "achievement";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.achievement.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 2) {
            StatBase var3 = StatList.func_151177_a(par2ArrayOfStr[1]);
            if (var3 == null && !par2ArrayOfStr[1].equals("*")) {
                throw new CommandException("commands.achievement.unknownAchievement", par2ArrayOfStr[1]);
            }
            EntityPlayerMP var4 = par2ArrayOfStr.length >= 3 ? CommandAchievement.getPlayer(par1ICommandSender, par2ArrayOfStr[2]) : CommandAchievement.getCommandSenderAsPlayer(par1ICommandSender);
            if (par2ArrayOfStr[0].equalsIgnoreCase("give")) {
                if (var3 == null) {
                    for (Achievement var6 : AchievementList.achievementList) {
                        var4.triggerAchievement(var6);
                    }
                    CommandAchievement.notifyAdmins(par1ICommandSender, "commands.achievement.give.success.all", var4.getCommandSenderName());
                } else {
                    if (var3 instanceof Achievement) {
                        Achievement var9 = (Achievement)var3;
                        ArrayList var10 = Lists.newArrayList();
                        while (var9.parentAchievement != null && !var4.func_147099_x().hasAchievementUnlocked(var9.parentAchievement)) {
                            var10.add(var9.parentAchievement);
                            var9 = var9.parentAchievement;
                        }
                        for (Achievement var8 : Lists.reverse((List)var10)) {
                            var4.triggerAchievement(var8);
                        }
                    }
                    var4.triggerAchievement(var3);
                    CommandAchievement.notifyAdmins(par1ICommandSender, "commands.achievement.give.success.one", var4.getCommandSenderName(), var3.func_150955_j());
                }
                return;
            }
        }
        throw new WrongUsageException("commands.achievement.usage", new Object[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            return CommandAchievement.getListOfStringsMatchingLastWord(par2ArrayOfStr, "give");
        }
        if (par2ArrayOfStr.length != 2) {
            return par2ArrayOfStr.length == 3 ? CommandAchievement.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
        }
        ArrayList var3 = Lists.newArrayList();
        for (StatBase var5 : StatList.allStats) {
            var3.add(var5.statId);
        }
        return CommandAchievement.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, var3);
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 2) {
            return true;
        }
        return false;
    }
}

