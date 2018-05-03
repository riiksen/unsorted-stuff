/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;

public class CommandTestForBlock
extends CommandBase {
    private static final String __OBFID = "CL_00001181";

    @Override
    public String getCommandName() {
        return "testforblock";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.testforblock.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        int var3;
        int var4;
        int var5;
        if (par2ArrayOfStr.length >= 4) {
            Block var15;
            int var16;
            World var8;
            var3 = par1ICommandSender.getPlayerCoordinates().posX;
            var4 = par1ICommandSender.getPlayerCoordinates().posY;
            var5 = par1ICommandSender.getPlayerCoordinates().posZ;
            var3 = MathHelper.floor_double(CommandTestForBlock.func_110666_a(par1ICommandSender, var3, par2ArrayOfStr[0]));
            var4 = MathHelper.floor_double(CommandTestForBlock.func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]));
            var5 = MathHelper.floor_double(CommandTestForBlock.func_110666_a(par1ICommandSender, var5, par2ArrayOfStr[2]));
            Block var6 = Block.getBlockFromName(par2ArrayOfStr[3]);
            if (var6 == null) {
                throw new NumberInvalidException("commands.setblock.notFound", par2ArrayOfStr[3]);
            }
            int var7 = -1;
            if (par2ArrayOfStr.length >= 5) {
                var7 = CommandTestForBlock.parseIntBounded(par1ICommandSender, par2ArrayOfStr[4], -1, 15);
            }
            if (!(var8 = par1ICommandSender.getEntityWorld()).blockExists(var3, var4, var5)) {
                throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
            }
            NBTTagCompound var9 = new NBTTagCompound();
            boolean var10 = false;
            if (par2ArrayOfStr.length >= 6 && var6.hasTileEntity()) {
                String var11 = CommandTestForBlock.func_147178_a(par1ICommandSender, par2ArrayOfStr, 5).getUnformattedText();
                try {
                    NBTBase var12 = JsonToNBT.func_150315_a(var11);
                    if (!(var12 instanceof NBTTagCompound)) {
                        throw new CommandException("commands.setblock.tagError", "Not a valid tag");
                    }
                    var9 = (NBTTagCompound)var12;
                    var10 = true;
                }
                catch (NBTException var14) {
                    throw new CommandException("commands.setblock.tagError", var14.getMessage());
                }
            }
            if ((var15 = var8.getBlock(var3, var4, var5)) != var6) {
                throw new CommandException("commands.testforblock.failed.tile", var3, var4, var5, var15.getLocalizedName(), var6.getLocalizedName());
            }
            if (var7 > -1 && (var16 = var8.getBlockMetadata(var3, var4, var5)) != var7) {
                throw new CommandException("commands.testforblock.failed.data", var3, var4, var5, var16, var7);
            }
            if (var10) {
                TileEntity var17 = var8.getTileEntity(var3, var4, var5);
                if (var17 == null) {
                    throw new CommandException("commands.testforblock.failed.tileEntity", var3, var4, var5);
                }
                NBTTagCompound var13 = new NBTTagCompound();
                var17.writeToNBT(var13);
                if (!this.func_147181_a(var9, var13)) {
                    throw new CommandException("commands.testforblock.failed.nbt", var3, var4, var5);
                }
            }
        } else {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.testforblock.success", var3, var4, var5));
    }

    public boolean func_147181_a(NBTBase p_147181_1_, NBTBase p_147181_2_) {
        if (p_147181_1_ == p_147181_2_) {
            return true;
        }
        if (p_147181_1_ == null) {
            return true;
        }
        if (p_147181_2_ == null) {
            return false;
        }
        if (!p_147181_1_.getClass().equals(p_147181_2_.getClass())) {
            return false;
        }
        if (p_147181_1_ instanceof NBTTagCompound) {
            String var6;
            NBTBase var7;
            NBTTagCompound var3 = (NBTTagCompound)p_147181_1_;
            NBTTagCompound var4 = (NBTTagCompound)p_147181_2_;
            Iterator var5 = var3.func_150296_c().iterator();
            do {
                if (var5.hasNext()) continue;
                return true;
            } while (this.func_147181_a(var7 = var3.getTag(var6 = (String)var5.next()), var4.getTag(var6)));
            return false;
        }
        return p_147181_1_.equals(p_147181_2_);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 4 ? CommandTestForBlock.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()) : null;
    }
}

