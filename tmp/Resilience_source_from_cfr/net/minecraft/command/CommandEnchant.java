/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

public class CommandEnchant
extends CommandBase {
    private static final String __OBFID = "CL_00000377";

    @Override
    public String getCommandName() {
        return "enchant";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.enchant.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        NBTTagList var8;
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        EntityPlayerMP var3 = CommandEnchant.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        int var4 = CommandEnchant.parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 0, Enchantment.enchantmentsList.length - 1);
        int var5 = 1;
        ItemStack var6 = var3.getCurrentEquippedItem();
        if (var6 == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        Enchantment var7 = Enchantment.enchantmentsList[var4];
        if (var7 == null) {
            throw new NumberInvalidException("commands.enchant.notFound", var4);
        }
        if (!var7.canApply(var6)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (par2ArrayOfStr.length >= 3) {
            var5 = CommandEnchant.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], var7.getMinLevel(), var7.getMaxLevel());
        }
        if (var6.hasTagCompound() && (var8 = var6.getEnchantmentTagList()) != null) {
            int var9 = 0;
            while (var9 < var8.tagCount()) {
                Enchantment var11;
                short var10 = var8.getCompoundTagAt(var9).getShort("id");
                if (Enchantment.enchantmentsList[var10] != null && !(var11 = Enchantment.enchantmentsList[var10]).canApplyTogether(var7)) {
                    throw new CommandException("commands.enchant.cantCombine", var7.getTranslatedName(var5), var11.getTranslatedName(var8.getCompoundTagAt(var9).getShort("lvl")));
                }
                ++var9;
            }
        }
        var6.addEnchantment(var7, var5);
        CommandEnchant.notifyAdmins(par1ICommandSender, "commands.enchant.success", new Object[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandEnchant.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getListOfPlayers()) : null;
    }

    protected String[] getListOfPlayers() {
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

