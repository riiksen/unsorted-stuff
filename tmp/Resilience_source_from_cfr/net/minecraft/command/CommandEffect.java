/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.Collection;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect
extends CommandBase {
    private static final String __OBFID = "CL_00000323";

    @Override
    public String getCommandName() {
        return "effect";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.effect.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        EntityPlayerMP var3 = CommandEffect.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        if (par2ArrayOfStr[1].equals("clear")) {
            if (var3.getActivePotionEffects().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", var3.getCommandSenderName());
            }
            var3.clearActivePotions();
            CommandEffect.notifyAdmins(par1ICommandSender, "commands.effect.success.removed.all", var3.getCommandSenderName());
        } else {
            int var4 = CommandEffect.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1);
            int var5 = 600;
            int var6 = 30;
            int var7 = 0;
            if (var4 < 0 || var4 >= Potion.potionTypes.length || Potion.potionTypes[var4] == null) {
                throw new NumberInvalidException("commands.effect.notFound", var4);
            }
            if (par2ArrayOfStr.length >= 3) {
                var6 = CommandEffect.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 0, 1000000);
                var5 = Potion.potionTypes[var4].isInstant() ? var6 : var6 * 20;
            } else if (Potion.potionTypes[var4].isInstant()) {
                var5 = 1;
            }
            if (par2ArrayOfStr.length >= 4) {
                var7 = CommandEffect.parseIntBounded(par1ICommandSender, par2ArrayOfStr[3], 0, 255);
            }
            if (var6 == 0) {
                if (!var3.isPotionActive(var4)) {
                    throw new CommandException("commands.effect.failure.notActive", new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName());
                }
                var3.removePotionEffect(var4);
                CommandEffect.notifyAdmins(par1ICommandSender, "commands.effect.success.removed", new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName());
            } else {
                PotionEffect var8 = new PotionEffect(var4, var5, var7);
                var3.addPotionEffect(var8);
                CommandEffect.notifyAdmins(par1ICommandSender, "commands.effect.success", new ChatComponentTranslation(var8.getEffectName(), new Object[0]), var4, var7, var3.getCommandSenderName(), var6);
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandEffect.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames()) : null;
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par2 == 0) {
            return true;
        }
        return false;
    }
}

