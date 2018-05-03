/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandDefaultGameMode
extends CommandGameMode {
    private static final String __OBFID = "CL_00000296";

    @Override
    public String getCommandName() {
        return "defaultgamemode";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.defaultgamemode.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0) {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        WorldSettings.GameType var3 = this.getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
        this.setGameType(var3);
        CommandDefaultGameMode.notifyAdmins(par1ICommandSender, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]));
    }

    protected void setGameType(WorldSettings.GameType par1EnumGameType) {
        MinecraftServer var2 = MinecraftServer.getServer();
        var2.setGameType(par1EnumGameType);
        if (var2.getForceGamemode()) {
            for (EntityPlayerMP var4 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                var4.setGameType(par1EnumGameType);
                var4.fallDistance = 0.0f;
            }
        }
    }
}

