/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class CommandSummon
extends CommandBase {
    private static final String __OBFID = "CL_00001158";

    @Override
    public String getCommandName() {
        return "summon";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.summon.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1) {
            World var10;
            String var3 = par2ArrayOfStr[0];
            double var4 = (double)par1ICommandSender.getPlayerCoordinates().posX + 0.5;
            double var6 = par1ICommandSender.getPlayerCoordinates().posY;
            double var8 = (double)par1ICommandSender.getPlayerCoordinates().posZ + 0.5;
            if (par2ArrayOfStr.length >= 4) {
                var4 = CommandSummon.func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]);
                var6 = CommandSummon.func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[2]);
                var8 = CommandSummon.func_110666_a(par1ICommandSender, var8, par2ArrayOfStr[3]);
            }
            if (!(var10 = par1ICommandSender.getEntityWorld()).blockExists((int)var4, (int)var6, (int)var8)) {
                CommandSummon.notifyAdmins(par1ICommandSender, "commands.summon.outOfWorld", new Object[0]);
            } else {
                NBTTagCompound var11 = new NBTTagCompound();
                boolean var12 = false;
                if (par2ArrayOfStr.length >= 5) {
                    IChatComponent var13 = CommandSummon.func_147178_a(par1ICommandSender, par2ArrayOfStr, 4);
                    try {
                        NBTBase var14 = JsonToNBT.func_150315_a(var13.getUnformattedText());
                        if (!(var14 instanceof NBTTagCompound)) {
                            CommandSummon.notifyAdmins(par1ICommandSender, "commands.summon.tagError", "Not a valid tag");
                            return;
                        }
                        var11 = (NBTTagCompound)var14;
                        var12 = true;
                    }
                    catch (NBTException var17) {
                        CommandSummon.notifyAdmins(par1ICommandSender, "commands.summon.tagError", var17.getMessage());
                        return;
                    }
                }
                var11.setString("id", var3);
                Entity var18 = EntityList.createEntityFromNBT(var11, var10);
                if (var18 != null) {
                    var18.setLocationAndAngles(var4, var6, var8, var18.rotationYaw, var18.rotationPitch);
                    if (!var12 && var18 instanceof EntityLiving) {
                        ((EntityLiving)var18).onSpawnWithEgg(null);
                    }
                    var10.spawnEntityInWorld(var18);
                    Entity var19 = var18;
                    NBTTagCompound var15 = var11;
                    while (var15.func_150297_b("Riding", 10)) {
                        Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), var10);
                        if (var16 != null) {
                            var16.setLocationAndAngles(var4, var6, var8, var16.rotationYaw, var16.rotationPitch);
                            var10.spawnEntityInWorld(var16);
                            var19.mountEntity(var16);
                        }
                        var19 = var16;
                        var15 = var15.getCompoundTag("Riding");
                    }
                    CommandSummon.notifyAdmins(par1ICommandSender, "commands.summon.success", new Object[0]);
                } else {
                    CommandSummon.notifyAdmins(par1ICommandSender, "commands.summon.failed", new Object[0]);
                }
            }
        } else {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandSummon.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.func_147182_d()) : null;
    }

    protected String[] func_147182_d() {
        return EntityList.func_151515_b().toArray(new String[0]);
    }
}

