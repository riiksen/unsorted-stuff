/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonParseException
 *  org.apache.commons.lang3.exception.ExceptionUtils
 */
package net.minecraft.command.server;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CommandMessageRaw
extends CommandBase {
    private static final String __OBFID = "CL_00000667";

    @Override
    public String getCommandName() {
        return "tellraw";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.tellraw.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }
        EntityPlayerMP var3 = CommandMessageRaw.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        String var4 = CommandMessageRaw.func_82360_a(par1ICommandSender, par2ArrayOfStr, 1);
        try {
            IChatComponent var5 = IChatComponent.Serializer.func_150699_a(var4);
            var3.addChatMessage(var5);
        }
        catch (JsonParseException var6) {
            throw new SyntaxErrorException("commands.tellraw.jsonException", ExceptionUtils.getRootCause((Throwable)var6).getMessage());
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandMessageRaw.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

