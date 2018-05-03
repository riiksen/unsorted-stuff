/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandShowSeed
extends CommandBase {
    private static final String __OBFID = "CL_00001053";

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (!MinecraftServer.getServer().isSinglePlayer() && !super.canCommandSenderUseCommand(par1ICommandSender)) {
            return false;
        }
        return true;
    }

    @Override
    public String getCommandName() {
        return "seed";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.seed.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        World var3 = par1ICommandSender instanceof EntityPlayer ? ((EntityPlayer)par1ICommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.seed.success", var3.getSeed()));
    }
}

