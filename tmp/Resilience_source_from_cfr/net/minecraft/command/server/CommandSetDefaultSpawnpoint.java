/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandSetDefaultSpawnpoint
extends CommandBase {
    private static final String __OBFID = "CL_00000973";

    @Override
    public String getCommandName() {
        return "setworldspawn";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.setworldspawn.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 3) {
            if (par1ICommandSender.getEntityWorld() == null) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            int var3 = 0;
            int var8 = var3 + 1;
            int var4 = CommandSetDefaultSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var3], -30000000, 30000000);
            int var5 = CommandSetDefaultSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var8++], 0, 256);
            int var6 = CommandSetDefaultSpawnpoint.parseIntBounded(par1ICommandSender, par2ArrayOfStr[var8++], -30000000, 30000000);
            par1ICommandSender.getEntityWorld().setSpawnLocation(var4, var5, var6);
            CommandSetDefaultSpawnpoint.notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", var4, var5, var6);
        } else {
            if (par2ArrayOfStr.length != 0) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            ChunkCoordinates var9 = CommandSetDefaultSpawnpoint.getCommandSenderAsPlayer(par1ICommandSender).getPlayerCoordinates();
            par1ICommandSender.getEntityWorld().setSpawnLocation(var9.posX, var9.posY, var9.posZ);
            CommandSetDefaultSpawnpoint.notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", var9.posX, var9.posY, var9.posZ);
        }
    }
}

