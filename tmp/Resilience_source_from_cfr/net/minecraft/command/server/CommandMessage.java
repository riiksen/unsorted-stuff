/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandMessage
extends CommandBase {
    private static final String __OBFID = "CL_00000641";

    @Override
    public List getCommandAliases() {
        return Arrays.asList("w", "msg");
    }

    @Override
    public String getCommandName() {
        return "tell";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.message.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.message.usage", new Object[0]);
        }
        EntityPlayerMP var3 = CommandMessage.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (var3 == par1ICommandSender) {
            throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
        }
        IChatComponent var4 = CommandMessage.func_147176_a(par1ICommandSender, par2ArrayOfStr, 1, !(par1ICommandSender instanceof EntityPlayer));
        ChatComponentTranslation var5 = new ChatComponentTranslation("commands.message.display.incoming", par1ICommandSender.func_145748_c_(), var4.createCopy());
        ChatComponentTranslation var6 = new ChatComponentTranslation("commands.message.display.outgoing", var3.func_145748_c_(), var4.createCopy());
        var5.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        var6.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        var3.addChatMessage(var5);
        par1ICommandSender.addChatMessage(var6);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return CommandMessage.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

