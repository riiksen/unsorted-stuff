/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;

public class CommandGameRule
extends CommandBase {
    private static final String __OBFID = "CL_00000475";

    @Override
    public String getCommandName() {
        return "gamerule";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.gamerule.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 2) {
            String var6 = par2ArrayOfStr[0];
            String var7 = par2ArrayOfStr[1];
            GameRules var8 = this.getGameRules();
            if (var8.hasRule(var6)) {
                var8.setOrCreateGameRule(var6, var7);
                CommandGameRule.notifyAdmins(par1ICommandSender, "commands.gamerule.success", new Object[0]);
            } else {
                CommandGameRule.notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var6);
            }
        } else if (par2ArrayOfStr.length == 1) {
            String var6 = par2ArrayOfStr[0];
            GameRules var4 = this.getGameRules();
            if (var4.hasRule(var6)) {
                String var5 = var4.getGameRuleStringValue(var6);
                par1ICommandSender.addChatMessage(new ChatComponentText(var6).appendText(" = ").appendText(var5));
            } else {
                CommandGameRule.notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var6);
            }
        } else if (par2ArrayOfStr.length == 0) {
            GameRules var3 = this.getGameRules();
            par1ICommandSender.addChatMessage(new ChatComponentText(CommandGameRule.joinNiceString(var3.getRules())));
        } else {
            throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandGameRule.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getGameRules().getRules()) : (par2ArrayOfStr.length == 2 ? CommandGameRule.getListOfStringsMatchingLastWord(par2ArrayOfStr, "true", "false") : null);
    }

    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }
}

