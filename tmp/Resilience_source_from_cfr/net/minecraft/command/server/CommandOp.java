/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class CommandOp
extends CommandBase {
    private static final String __OBFID = "CL_00000694";

    @Override
    public String getCommandName() {
        return "op";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.op.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1 || par2ArrayOfStr[0].length() <= 0) {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        MinecraftServer.getServer().getConfigurationManager().addOp(par2ArrayOfStr[0]);
        CommandOp.notifyAdmins(par1ICommandSender, "commands.op.success", par2ArrayOfStr[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList<String> var4 = new ArrayList<String>();
            String[] var5 = MinecraftServer.getServer().getAllUsernames();
            int var6 = var5.length;
            int var7 = 0;
            while (var7 < var6) {
                String var8 = var5[var7];
                if (!MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(var8) && CommandOp.doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
                ++var7;
            }
            return var4;
        }
        return null;
    }
}

