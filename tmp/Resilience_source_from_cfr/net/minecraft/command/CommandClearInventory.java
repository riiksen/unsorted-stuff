/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryNamespaced;

public class CommandClearInventory
extends CommandBase {
    private static final String __OBFID = "CL_00000218";

    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.clear.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        int var5;
        EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? CommandClearInventory.getCommandSenderAsPlayer(par1ICommandSender) : CommandClearInventory.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        Item var4 = par2ArrayOfStr.length >= 2 ? CommandClearInventory.getItemByText(par1ICommandSender, par2ArrayOfStr[1]) : null;
        int n = var5 = par2ArrayOfStr.length >= 3 ? CommandClearInventory.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;
        if (par2ArrayOfStr.length >= 2 && var4 == null) {
            throw new CommandException("commands.clear.failure", var3.getCommandSenderName());
        }
        int var6 = var3.inventory.clearInventory(var4, var5);
        var3.inventoryContainer.detectAndSendChanges();
        if (!var3.capabilities.isCreativeMode) {
            var3.updateHeldItem();
        }
        if (var6 == 0) {
            throw new CommandException("commands.clear.failure", var3.getCommandSenderName());
        }
        CommandClearInventory.notifyAdmins(par1ICommandSender, "commands.clear.success", var3.getCommandSenderName(), var6);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandClearInventory.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.func_147209_d()) : (par2ArrayOfStr.length == 2 ? CommandClearInventory.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Item.itemRegistry.getKeys()) : null);
    }

    protected String[] func_147209_d() {
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

