/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandEmote
extends CommandBase {
    private static final String __OBFID = "CL_00000351";

    @Override
    public String getCommandName() {
        return "me";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.me.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0) {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
        IChatComponent var3 = CommandEmote.func_147176_a(par1ICommandSender, par2ArrayOfStr, 0, par1ICommandSender.canCommandSenderUseCommand(1, "me"));
        MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.emote", par1ICommandSender.func_145748_c_(), var3));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return CommandEmote.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
}

