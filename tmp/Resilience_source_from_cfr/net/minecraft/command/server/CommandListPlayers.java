/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandListPlayers
extends CommandBase {
    private static final String __OBFID = "CL_00000615";

    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.players.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.players.list", MinecraftServer.getServer().getCurrentPlayerCount(), MinecraftServer.getServer().getMaxPlayers()));
        par1ICommandSender.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString()));
    }
}

