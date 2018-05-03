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
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandSetSpawnpoint
extends CommandBase {
    private static final String __OBFID = "CL_00001026";

    @Override
    public String getCommandName() {
        return "spawnpoint";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.spawnpoint.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        EntityPlayerMP var3;
        EntityPlayerMP entityPlayerMP = var3 = par2ArrayOfStr.length == 0 ? CommandSetSpawnpoint.getCommandSenderAsPlayer(par1ICommandSender) : CommandSetSpawnpoint.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        if (par2ArrayOfStr.length == 4) {
            if (var3.worldObj != null) {
                int var4 = 1;
                int var5 = 30000000;
                int var10 = var4 + 1;
                int var6 = CommandSetSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var4], - var5, var5);
                int var7 = CommandSetSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var10++], 0, 256);
                int var8 = CommandSetSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var10++], - var5, var5);
                var3.setSpawnChunk(new ChunkCoordinates(var6, var7, var8), true);
                CommandSetSpawnpoint.notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", var3.getCommandSenderName(), var6, var7, var8);
            }
        } else {
            if (par2ArrayOfStr.length > 1) {
                throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
            }
            ChunkCoordinates var11 = var3.getPlayerCoordinates();
            var3.setSpawnChunk(var11, true);
            CommandSetSpawnpoint.notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", var3.getCommandSenderName(), var11.posX, var11.posY, var11.posZ);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2 ? null : CommandSetSpawnpoint.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

