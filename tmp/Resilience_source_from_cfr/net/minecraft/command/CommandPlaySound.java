/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ChunkCoordinates;

public class CommandPlaySound
extends CommandBase {
    private static final String __OBFID = "CL_00000774";

    @Override
    public String getCommandName() {
        return "playsound";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.playsound.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(par1ICommandSender), new Object[0]);
        }
        int var3 = 0;
        int var36 = var3 + 1;
        String var4 = par2ArrayOfStr[var3];
        EntityPlayerMP var5 = CommandPlaySound.getPlayer(par1ICommandSender, par2ArrayOfStr[var36++]);
        double var6 = var5.getPlayerCoordinates().posX;
        double var8 = var5.getPlayerCoordinates().posY;
        double var10 = var5.getPlayerCoordinates().posZ;
        double var12 = 1.0;
        double var14 = 1.0;
        double var16 = 0.0;
        if (par2ArrayOfStr.length > var36) {
            var6 = CommandPlaySound.func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[var36++]);
        }
        if (par2ArrayOfStr.length > var36) {
            var8 = CommandPlaySound.func_110665_a(par1ICommandSender, var8, par2ArrayOfStr[var36++], 0, 0);
        }
        if (par2ArrayOfStr.length > var36) {
            var10 = CommandPlaySound.func_110666_a(par1ICommandSender, var10, par2ArrayOfStr[var36++]);
        }
        if (par2ArrayOfStr.length > var36) {
            var12 = CommandPlaySound.parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0, 3.4028234663852886E38);
        }
        if (par2ArrayOfStr.length > var36) {
            var14 = CommandPlaySound.parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0, 2.0);
        }
        if (par2ArrayOfStr.length > var36) {
            var16 = CommandPlaySound.parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0, 1.0);
        }
        double var18 = var12 > 1.0 ? var12 * 16.0 : 16.0;
        double var20 = var5.getDistance(var6, var8, var10);
        if (var20 > var18) {
            if (var16 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", var5.getCommandSenderName());
            }
            double var22 = var6 - var5.posX;
            double var24 = var8 - var5.posY;
            double var26 = var10 - var5.posZ;
            double var28 = Math.sqrt(var22 * var22 + var24 * var24 + var26 * var26);
            double var30 = var5.posX;
            double var32 = var5.posY;
            double var34 = var5.posZ;
            if (var28 > 0.0) {
                var30 += var22 / var28 * 2.0;
                var32 += var24 / var28 * 2.0;
                var34 += var26 / var28 * 2.0;
            }
            var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var30, var32, var34, (float)var16, (float)var14));
        } else {
            var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var6, var8, var10, (float)var12, (float)var14));
        }
        CommandPlaySound.notifyAdmins(par1ICommandSender, "commands.playsound.success", var4, var5.getCommandSenderName());
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 1) {
            return true;
        }
        return false;
    }
}

