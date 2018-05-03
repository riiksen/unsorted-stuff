/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandTime
extends CommandBase {
    private static final String __OBFID = "CL_00001183";

    @Override
    public String getCommandName() {
        return "time";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.time.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 1) {
            if (par2ArrayOfStr[0].equals("set")) {
                int var3 = par2ArrayOfStr[1].equals("day") ? 1000 : (par2ArrayOfStr[1].equals("night") ? 13000 : CommandTime.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0));
                this.setTime(par1ICommandSender, var3);
                CommandTime.notifyAdmins(par1ICommandSender, "commands.time.set", var3);
                return;
            }
            if (par2ArrayOfStr[0].equals("add")) {
                int var3 = CommandTime.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                this.addTime(par1ICommandSender, var3);
                CommandTime.notifyAdmins(par1ICommandSender, "commands.time.added", var3);
                return;
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandTime.getListOfStringsMatchingLastWord(par2ArrayOfStr, "set", "add") : (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set") ? CommandTime.getListOfStringsMatchingLastWord(par2ArrayOfStr, "day", "night") : null);
    }

    protected void setTime(ICommandSender par1ICommandSender, int par2) {
        int var3 = 0;
        while (var3 < MinecraftServer.getServer().worldServers.length) {
            MinecraftServer.getServer().worldServers[var3].setWorldTime(par2);
            ++var3;
        }
    }

    protected void addTime(ICommandSender par1ICommandSender, int par2) {
        int var3 = 0;
        while (var3 < MinecraftServer.getServer().worldServers.length) {
            WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
            var4.setWorldTime(var4.getWorldTime() + (long)par2);
            ++var3;
        }
    }
}

