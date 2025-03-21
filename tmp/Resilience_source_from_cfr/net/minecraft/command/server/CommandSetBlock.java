/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;

public class CommandSetBlock
extends CommandBase {
    private static final String __OBFID = "CL_00000949";

    @Override
    public String getCommandName() {
        return "setblock";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.setblock.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 4) {
            TileEntity var14;
            World var8;
            int var3 = par1ICommandSender.getPlayerCoordinates().posX;
            int var4 = par1ICommandSender.getPlayerCoordinates().posY;
            int var5 = par1ICommandSender.getPlayerCoordinates().posZ;
            var3 = MathHelper.floor_double(CommandSetBlock.func_110666_a(par1ICommandSender, var3, par2ArrayOfStr[0]));
            var4 = MathHelper.floor_double(CommandSetBlock.func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]));
            var5 = MathHelper.floor_double(CommandSetBlock.func_110666_a(par1ICommandSender, var5, par2ArrayOfStr[2]));
            Block var6 = CommandBase.getBlockByText(par1ICommandSender, par2ArrayOfStr[3]);
            int var7 = 0;
            if (par2ArrayOfStr.length >= 5) {
                var7 = CommandSetBlock.parseIntBounded(par1ICommandSender, par2ArrayOfStr[4], 0, 15);
            }
            if (!(var8 = par1ICommandSender.getEntityWorld()).blockExists(var3, var4, var5)) {
                throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
            }
            NBTTagCompound var9 = new NBTTagCompound();
            boolean var10 = false;
            if (par2ArrayOfStr.length >= 7 && var6.hasTileEntity()) {
                String var11 = CommandSetBlock.func_147178_a(par1ICommandSender, par2ArrayOfStr, 6).getUnformattedText();
                try {
                    NBTBase var12 = JsonToNBT.func_150315_a(var11);
                    if (!(var12 instanceof NBTTagCompound)) {
                        throw new CommandException("commands.setblock.tagError", "Not a valid tag");
                    }
                    var9 = (NBTTagCompound)var12;
                    var10 = true;
                }
                catch (NBTException var13) {
                    throw new CommandException("commands.setblock.tagError", var13.getMessage());
                }
            }
            if (par2ArrayOfStr.length >= 6) {
                if (par2ArrayOfStr[5].equals("destroy")) {
                    var8.func_147480_a(var3, var4, var5, true);
                } else if (par2ArrayOfStr[5].equals("keep") && !var8.isAirBlock(var3, var4, var5)) {
                    throw new CommandException("commands.setblock.noChange", new Object[0]);
                }
            }
            if (!var8.setBlock(var3, var4, var5, var6, var7, 3)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
            if (var10 && (var14 = var8.getTileEntity(var3, var4, var5)) != null) {
                var9.setInteger("x", var3);
                var9.setInteger("y", var4);
                var9.setInteger("z", var5);
                var14.readFromNBT(var9);
            }
        } else {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        CommandSetBlock.notifyAdmins(par1ICommandSender, "commands.setblock.success", new Object[0]);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 4 ? CommandSetBlock.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()) : (par2ArrayOfStr.length == 6 ? CommandSetBlock.getListOfStringsMatchingLastWord(par2ArrayOfStr, "replace", "destroy", "keep") : null);
    }
}

