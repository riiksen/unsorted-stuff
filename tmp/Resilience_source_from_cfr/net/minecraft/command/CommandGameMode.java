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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandGameMode
extends CommandBase {
    private static final String __OBFID = "CL_00000448";

    @Override
    public String getCommandName() {
        return "gamemode";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.gamemode.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0) {
            WorldSettings.GameType var3 = this.getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
            EntityPlayerMP var4 = par2ArrayOfStr.length >= 2 ? CommandGameMode.getPlayer(par1ICommandSender, par2ArrayOfStr[1]) : CommandGameMode.getCommandSenderAsPlayer(par1ICommandSender);
            var4.setGameType(var3);
            var4.fallDistance = 0.0f;
            ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]);
            if (var4 != par1ICommandSender) {
                CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.other", var4.getCommandSenderName(), var5);
            } else {
                CommandGameMode.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.self", var5);
            }
        } else {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }
    }

    protected WorldSettings.GameType getGameModeFromCommand(ICommandSender par1ICommandSender, String par2Str) {
        return !par2Str.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !par2Str.equalsIgnoreCase("s") ? (!par2Str.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) && !par2Str.equalsIgnoreCase("c") ? (!par2Str.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) && !par2Str.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(CommandGameMode.parseIntBounded(par1ICommandSender, par2Str, 0, WorldSettings.GameType.values().length - 2)) : WorldSettings.GameType.ADVENTURE) : WorldSettings.GameType.CREATIVE) : WorldSettings.GameType.SURVIVAL;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandGameMode.getListOfStringsMatchingLastWord(par2ArrayOfStr, "survival", "creative", "adventure") : (par2ArrayOfStr.length == 2 ? CommandGameMode.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getListOfPlayerUsernames()) : null);
    }

    protected String[] getListOfPlayerUsernames() {
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

