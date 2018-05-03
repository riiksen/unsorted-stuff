/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import java.util.Random;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandWeather
extends CommandBase {
    private static final String __OBFID = "CL_00001185";

    @Override
    public String getCommandName() {
        return "weather";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.weather.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr.length <= 2) {
            int var3 = (300 + new Random().nextInt(600)) * 20;
            if (par2ArrayOfStr.length >= 2) {
                var3 = CommandWeather.parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
            }
            WorldServer var4 = MinecraftServer.getServer().worldServers[0];
            WorldInfo var5 = var4.getWorldInfo();
            if ("clear".equalsIgnoreCase(par2ArrayOfStr[0])) {
                var5.setRainTime(0);
                var5.setThunderTime(0);
                var5.setRaining(false);
                var5.setThundering(false);
                CommandWeather.notifyAdmins(par1ICommandSender, "commands.weather.clear", new Object[0]);
            } else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0])) {
                var5.setRainTime(var3);
                var5.setRaining(true);
                var5.setThundering(false);
                CommandWeather.notifyAdmins(par1ICommandSender, "commands.weather.rain", new Object[0]);
            } else {
                if (!"thunder".equalsIgnoreCase(par2ArrayOfStr[0])) {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }
                var5.setRainTime(var3);
                var5.setThunderTime(var3);
                var5.setRaining(true);
                var5.setThundering(true);
                CommandWeather.notifyAdmins(par1ICommandSender, "commands.weather.thunder", new Object[0]);
            }
        } else {
            throw new WrongUsageException("commands.weather.usage", new Object[0]);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandWeather.getListOfStringsMatchingLastWord(par2ArrayOfStr, "clear", "rain", "thunder") : null;
    }
}

