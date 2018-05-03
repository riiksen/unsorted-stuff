/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandTeleport
extends CommandBase {
    private static final String __OBFID = "CL_00001180";

    @Override
    public String getCommandName() {
        return "tp";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.tp.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        EntityPlayerMP var3;
        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4) {
            var3 = CommandTeleport.getCommandSenderAsPlayer(par1ICommandSender);
        } else {
            var3 = CommandTeleport.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
            if (var3 == null) {
                throw new PlayerNotFoundException();
            }
        }
        if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
            if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2) {
                EntityPlayerMP var11 = CommandTeleport.getPlayer(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);
                if (var11 == null) {
                    throw new PlayerNotFoundException();
                }
                if (var11.worldObj != var3.worldObj) {
                    CommandTeleport.notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
                    return;
                }
                var3.mountEntity(null);
                var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
                CommandTeleport.notifyAdmins(par1ICommandSender, "commands.tp.success", var3.getCommandSenderName(), var11.getCommandSenderName());
            }
        } else if (var3.worldObj != null) {
            int var4 = par2ArrayOfStr.length - 3;
            double var5 = CommandTeleport.func_110666_a(par1ICommandSender, var3.posX, par2ArrayOfStr[var4++]);
            double var7 = CommandTeleport.func_110665_a(par1ICommandSender, var3.posY, par2ArrayOfStr[var4++], 0, 0);
            double var9 = CommandTeleport.func_110666_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[var4++]);
            var3.mountEntity(null);
            var3.setPositionAndUpdate(var5, var7, var9);
            CommandTeleport.notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", var3.getCommandSenderName(), var5, var7, var9);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2 ? null : CommandTeleport.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

