/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll
extends CommandBase {
    private static final String __OBFID = "CL_00000826";

    @Override
    public String getCommandName() {
        return "save-all";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.save.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        MinecraftServer var3 = MinecraftServer.getServer();
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (var3.getConfigurationManager() != null) {
            var3.getConfigurationManager().saveAllPlayerData();
        }
        try {
            boolean var6;
            WorldServer var5;
            int var4 = 0;
            while (var4 < var3.worldServers.length) {
                if (var3.worldServers[var4] != null) {
                    var5 = var3.worldServers[var4];
                    var6 = var5.levelSaving;
                    var5.levelSaving = false;
                    var5.saveAllChunks(true, null);
                    var5.levelSaving = var6;
                }
                ++var4;
            }
            if (par2ArrayOfStr.length > 0 && "flush".equals(par2ArrayOfStr[0])) {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
                var4 = 0;
                while (var4 < var3.worldServers.length) {
                    if (var3.worldServers[var4] != null) {
                        var5 = var3.worldServers[var4];
                        var6 = var5.levelSaving;
                        var5.levelSaving = false;
                        var5.saveChunkData();
                        var5.levelSaving = var6;
                    }
                    ++var4;
                }
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var7) {
            CommandSaveAll.notifyAdmins(par1ICommandSender, "commands.save.failed", var7.getMessage());
            return;
        }
        CommandSaveAll.notifyAdmins(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}

