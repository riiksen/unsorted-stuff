/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler
implements ICommandManager {
    private static final Logger logger = LogManager.getLogger();
    private final Map commandMap = new HashMap();
    private final Set commandSet = new HashSet();
    private static final String __OBFID = "CL_00001765";

    @Override
    public int executeCommand(ICommandSender par1ICommandSender, String par2Str) {
        int var7;
        block11 : {
            if ((par2Str = par2Str.trim()).startsWith("/")) {
                par2Str = par2Str.substring(1);
            }
            String[] var3 = par2Str.split(" ");
            String var4 = var3[0];
            var3 = CommandHandler.dropFirstString(var3);
            ICommand var5 = (ICommand)this.commandMap.get(var4);
            int var6 = this.getUsernameIndex(var5, var3);
            var7 = 0;
            try {
                if (var5 == null) {
                    throw new CommandNotFoundException();
                }
                if (var5.canCommandSenderUseCommand(par1ICommandSender)) {
                    if (var6 > -1) {
                        EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(par1ICommandSender, var3[var6]);
                        String var21 = var3[var6];
                        EntityPlayerMP[] var10 = var8;
                        int var11 = var8.length;
                        int var12 = 0;
                        while (var12 < var11) {
                            EntityPlayerMP var13 = var10[var12];
                            var3[var6] = var13.getCommandSenderName();
                            try {
                                var5.processCommand(par1ICommandSender, var3);
                                ++var7;
                            }
                            catch (CommandException var16) {
                                ChatComponentTranslation var15 = new ChatComponentTranslation(var16.getMessage(), var16.getErrorOjbects());
                                var15.getChatStyle().setColor(EnumChatFormatting.RED);
                                par1ICommandSender.addChatMessage(var15);
                            }
                            ++var12;
                        }
                        var3[var6] = var21;
                        break block11;
                    }
                    var5.processCommand(par1ICommandSender, var3);
                    ++var7;
                    break block11;
                }
                ChatComponentTranslation var20 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
                var20.getChatStyle().setColor(EnumChatFormatting.RED);
                par1ICommandSender.addChatMessage(var20);
            }
            catch (WrongUsageException var17) {
                ChatComponentTranslation var9 = new ChatComponentTranslation("commands.generic.usage", new ChatComponentTranslation(var17.getMessage(), var17.getErrorOjbects()));
                var9.getChatStyle().setColor(EnumChatFormatting.RED);
                par1ICommandSender.addChatMessage(var9);
            }
            catch (CommandException var18) {
                ChatComponentTranslation var9 = new ChatComponentTranslation(var18.getMessage(), var18.getErrorOjbects());
                var9.getChatStyle().setColor(EnumChatFormatting.RED);
                par1ICommandSender.addChatMessage(var9);
            }
            catch (Throwable var19) {
                ChatComponentTranslation var9 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
                var9.getChatStyle().setColor(EnumChatFormatting.RED);
                par1ICommandSender.addChatMessage(var9);
                logger.error("Couldn't process command", var19);
            }
        }
        return var7;
    }

    public ICommand registerCommand(ICommand par1ICommand) {
        List var2 = par1ICommand.getCommandAliases();
        this.commandMap.put(par1ICommand.getCommandName(), par1ICommand);
        this.commandSet.add(par1ICommand);
        if (var2 != null) {
            for (String var4 : var2) {
                ICommand var5 = (ICommand)this.commandMap.get(var4);
                if (var5 != null && var5.getCommandName().equals(var4)) continue;
                this.commandMap.put(var4, par1ICommand);
            }
        }
        return par1ICommand;
    }

    private static String[] dropFirstString(String[] par0ArrayOfStr) {
        String[] var1 = new String[par0ArrayOfStr.length - 1];
        int var2 = 1;
        while (var2 < par0ArrayOfStr.length) {
            var1[var2 - 1] = par0ArrayOfStr[var2];
            ++var2;
        }
        return var1;
    }

    @Override
    public List getPossibleCommands(ICommandSender par1ICommandSender, String par2Str) {
        ICommand var5;
        String[] var3 = par2Str.split(" ", -1);
        String var4 = var3[0];
        if (var3.length == 1) {
            ArrayList var8 = new ArrayList();
            for (Map.Entry var7 : this.commandMap.entrySet()) {
                if (!CommandBase.doesStringStartWith(var4, (String)var7.getKey()) || !((ICommand)var7.getValue()).canCommandSenderUseCommand(par1ICommandSender)) continue;
                var8.add(var7.getKey());
            }
            return var8;
        }
        if (var3.length > 1 && (var5 = (ICommand)this.commandMap.get(var4)) != null) {
            return var5.addTabCompletionOptions(par1ICommandSender, CommandHandler.dropFirstString(var3));
        }
        return null;
    }

    @Override
    public List getPossibleCommands(ICommandSender par1ICommandSender) {
        ArrayList<ICommand> var2 = new ArrayList<ICommand>();
        for (ICommand var4 : this.commandSet) {
            if (!var4.canCommandSenderUseCommand(par1ICommandSender)) continue;
            var2.add(var4);
        }
        return var2;
    }

    @Override
    public Map getCommands() {
        return this.commandMap;
    }

    private int getUsernameIndex(ICommand par1ICommand, String[] par2ArrayOfStr) {
        if (par1ICommand == null) {
            return -1;
        }
        int var3 = 0;
        while (var3 < par2ArrayOfStr.length) {
            if (par1ICommand.isUsernameIndex(par2ArrayOfStr, var3) && PlayerSelector.matchesMultiplePlayers(par2ArrayOfStr[var3])) {
                return var3;
            }
            ++var3;
        }
        return -1;
    }
}

