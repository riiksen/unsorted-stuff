/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall
extends CommandBase {
    private static final String __OBFID = "CL_00001184";

    @Override
    public String getCommandName() {
        return "toggledownfall";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.downfall.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        this.toggleDownfall();
        CommandToggleDownfall.notifyAdmins(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }

    protected void toggleDownfall() {
        WorldInfo var1;
        var1.setRaining(!(var1 = MinecraftServer.getServer().worldServers[0].getWorldInfo()).isRaining());
    }
}

