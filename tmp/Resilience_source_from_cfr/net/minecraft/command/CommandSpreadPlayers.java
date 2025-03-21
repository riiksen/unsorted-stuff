/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers
extends CommandBase {
    private static final String __OBFID = "CL_00001080";

    @Override
    public String getCommandName() {
        return "spreadplayers";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.spreadplayers.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }
        int var3 = 0;
        int var17 = var3 + 1;
        double var4 = CommandSpreadPlayers.func_110666_a(par1ICommandSender, Double.NaN, par2ArrayOfStr[var3]);
        double var6 = CommandSpreadPlayers.func_110666_a(par1ICommandSender, Double.NaN, par2ArrayOfStr[var17++]);
        double var8 = CommandSpreadPlayers.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[var17++], 0.0);
        double var10 = CommandSpreadPlayers.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[var17++], var8 + 1.0);
        boolean var12 = CommandSpreadPlayers.parseBoolean(par1ICommandSender, par2ArrayOfStr[var17++]);
        ArrayList var13 = Lists.newArrayList();
        while (var17 < par2ArrayOfStr.length) {
            String var14;
            if (PlayerSelector.hasArguments(var14 = par2ArrayOfStr[var17++])) {
                EntityPlayerMP[] var18 = PlayerSelector.matchPlayers(par1ICommandSender, var14);
                if (var18 == null || var18.length == 0) {
                    throw new PlayerNotFoundException();
                }
                Collections.addAll(var13, var18);
                continue;
            }
            EntityPlayerMP var15 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(var14);
            if (var15 == null) {
                throw new PlayerNotFoundException();
            }
            var13.add(var15);
        }
        if (var13.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.spreading." + (var12 ? "teams" : "players"), var13.size(), var10, var4, var6, var8));
        this.func_110669_a(par1ICommandSender, var13, new Position(var4, var6), var8, var10, ((EntityLivingBase)var13.get((int)0)).worldObj, var12);
    }

    private void func_110669_a(ICommandSender par1ICommandSender, List par2List, Position par3CommandSpreadPlayersPosition, double par4, double par6, World par8World, boolean par9) {
        Random var10 = new Random();
        double var11 = par3CommandSpreadPlayersPosition.field_111101_a - par6;
        double var13 = par3CommandSpreadPlayersPosition.field_111100_b - par6;
        double var15 = par3CommandSpreadPlayersPosition.field_111101_a + par6;
        double var17 = par3CommandSpreadPlayersPosition.field_111100_b + par6;
        Position[] var19 = this.func_110670_a(var10, par9 ? this.func_110667_a(par2List) : par2List.size(), var11, var13, var15, var17);
        int var20 = this.func_110668_a(par3CommandSpreadPlayersPosition, par4, par8World, var10, var11, var13, var15, var17, var19, par9);
        double var21 = this.func_110671_a(par2List, par8World, var19, par9);
        CommandSpreadPlayers.notifyAdmins(par1ICommandSender, "commands.spreadplayers.success." + (par9 ? "teams" : "players"), var19.length, par3CommandSpreadPlayersPosition.field_111101_a, par3CommandSpreadPlayersPosition.field_111100_b);
        if (var19.length > 1) {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.info." + (par9 ? "teams" : "players"), String.format("%.2f", var21), var20));
        }
    }

    private int func_110667_a(List par1List) {
        HashSet var2 = Sets.newHashSet();
        for (EntityLivingBase var4 : par1List) {
            if (var4 instanceof EntityPlayer) {
                var2.add(var4.getTeam());
                continue;
            }
            var2.add(null);
        }
        return var2.size();
    }

    private int func_110668_a(Position par1CommandSpreadPlayersPosition, double par2, World par4World, Random par5Random, double par6, double par8, double par10, double par12, Position[] par14ArrayOfCommandSpreadPlayersPosition, boolean par15) {
        boolean var16 = true;
        double var18 = 3.4028234663852886E38;
        int var17 = 0;
        while (var17 < 10000 && var16) {
            int var22;
            Position var23;
            var16 = false;
            var18 = 3.4028234663852886E38;
            int var20 = 0;
            while (var20 < par14ArrayOfCommandSpreadPlayersPosition.length) {
                Position var21 = par14ArrayOfCommandSpreadPlayersPosition[var20];
                var22 = 0;
                var23 = new Position();
                int var24 = 0;
                while (var24 < par14ArrayOfCommandSpreadPlayersPosition.length) {
                    if (var20 != var24) {
                        Position var25 = par14ArrayOfCommandSpreadPlayersPosition[var24];
                        double var26 = var21.func_111099_a(var25);
                        var18 = Math.min(var26, var18);
                        if (var26 < par2) {
                            ++var22;
                            var23.field_111101_a += var25.field_111101_a - var21.field_111101_a;
                            var23.field_111100_b += var25.field_111100_b - var21.field_111100_b;
                        }
                    }
                    ++var24;
                }
                if (var22 > 0) {
                    var23.field_111101_a /= (double)var22;
                    var23.field_111100_b /= (double)var22;
                    double var30 = var23.func_111096_b();
                    if (var30 > 0.0) {
                        var23.func_111095_a();
                        var21.func_111094_b(var23);
                    } else {
                        var21.func_111097_a(par5Random, par6, par8, par10, par12);
                    }
                    var16 = true;
                }
                if (var21.func_111093_a(par6, par8, par10, par12)) {
                    var16 = true;
                }
                ++var20;
            }
            if (!var16) {
                Position[] var28 = par14ArrayOfCommandSpreadPlayersPosition;
                int var29 = par14ArrayOfCommandSpreadPlayersPosition.length;
                var22 = 0;
                while (var22 < var29) {
                    var23 = var28[var22];
                    if (!var23.func_111098_b(par4World)) {
                        var23.func_111097_a(par5Random, par6, par8, par10, par12);
                        var16 = true;
                    }
                    ++var22;
                }
            }
            ++var17;
        }
        if (var17 >= 10000) {
            throw new CommandException("commands.spreadplayers.failure." + (par15 ? "teams" : "players"), par14ArrayOfCommandSpreadPlayersPosition.length, par1CommandSpreadPlayersPosition.field_111101_a, par1CommandSpreadPlayersPosition.field_111100_b, String.format("%.2f", var18));
        }
        return var17;
    }

    private double func_110671_a(List par1List, World par2World, Position[] par3ArrayOfCommandSpreadPlayersPosition, boolean par4) {
        double var5 = 0.0;
        int var7 = 0;
        HashMap var8 = Maps.newHashMap();
        int var9 = 0;
        while (var9 < par1List.size()) {
            Position var11;
            EntityLivingBase var10 = (EntityLivingBase)par1List.get(var9);
            if (par4) {
                Team var12;
                Team team = var12 = var10 instanceof EntityPlayer ? var10.getTeam() : null;
                if (!var8.containsKey(var12)) {
                    var8.put(var12, par3ArrayOfCommandSpreadPlayersPosition[var7++]);
                }
                var11 = (Position)var8.get(var12);
            } else {
                var11 = par3ArrayOfCommandSpreadPlayersPosition[var7++];
            }
            var10.setPositionAndUpdate((float)MathHelper.floor_double(var11.field_111101_a) + 0.5f, var11.func_111092_a(par2World), (double)MathHelper.floor_double(var11.field_111100_b) + 0.5);
            double var17 = Double.MAX_VALUE;
            int var14 = 0;
            while (var14 < par3ArrayOfCommandSpreadPlayersPosition.length) {
                if (var11 != par3ArrayOfCommandSpreadPlayersPosition[var14]) {
                    double var15 = var11.func_111099_a(par3ArrayOfCommandSpreadPlayersPosition[var14]);
                    var17 = Math.min(var15, var17);
                }
                ++var14;
            }
            var5 += var17;
            ++var9;
        }
        return var5 /= (double)par1List.size();
    }

    private Position[] func_110670_a(Random par1Random, int par2, double par3, double par5, double par7, double par9) {
        Position[] var11 = new Position[par2];
        int var12 = 0;
        while (var12 < var11.length) {
            Position var13 = new Position();
            var13.func_111097_a(par1Random, par3, par5, par7, par9);
            var11[var12] = var13;
            ++var12;
        }
        return var11;
    }

    static class Position {
        double field_111101_a;
        double field_111100_b;
        private static final String __OBFID = "CL_00001105";

        Position() {
        }

        Position(double par1, double par3) {
            this.field_111101_a = par1;
            this.field_111100_b = par3;
        }

        double func_111099_a(Position par1CommandSpreadPlayersPosition) {
            double var2 = this.field_111101_a - par1CommandSpreadPlayersPosition.field_111101_a;
            double var4 = this.field_111100_b - par1CommandSpreadPlayersPosition.field_111100_b;
            return Math.sqrt(var2 * var2 + var4 * var4);
        }

        void func_111095_a() {
            double var1 = this.func_111096_b();
            this.field_111101_a /= var1;
            this.field_111100_b /= var1;
        }

        float func_111096_b() {
            return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
        }

        public void func_111094_b(Position par1CommandSpreadPlayersPosition) {
            this.field_111101_a -= par1CommandSpreadPlayersPosition.field_111101_a;
            this.field_111100_b -= par1CommandSpreadPlayersPosition.field_111100_b;
        }

        public boolean func_111093_a(double par1, double par3, double par5, double par7) {
            boolean var9 = false;
            if (this.field_111101_a < par1) {
                this.field_111101_a = par1;
                var9 = true;
            } else if (this.field_111101_a > par5) {
                this.field_111101_a = par5;
                var9 = true;
            }
            if (this.field_111100_b < par3) {
                this.field_111100_b = par3;
                var9 = true;
            } else if (this.field_111100_b > par7) {
                this.field_111100_b = par7;
                var9 = true;
            }
            return var9;
        }

        public int func_111092_a(World par1World) {
            int var2 = MathHelper.floor_double(this.field_111101_a);
            int var3 = MathHelper.floor_double(this.field_111100_b);
            int var4 = 256;
            while (var4 > 0) {
                if (par1World.getBlock(var2, var4, var3).getMaterial() != Material.air) {
                    return var4 + 1;
                }
                --var4;
            }
            return 257;
        }

        public boolean func_111098_b(World par1World) {
            int var2 = MathHelper.floor_double(this.field_111101_a);
            int var3 = MathHelper.floor_double(this.field_111100_b);
            int var4 = 256;
            if (var4 <= 0) {
                return false;
            }
            Material var5 = par1World.getBlock(var2, var4, var3).getMaterial();
            if (!var5.isLiquid() && var5 != Material.fire) {
                return true;
            }
            return false;
        }

        public void func_111097_a(Random par1Random, double par2, double par4, double par6, double par8) {
            this.field_111101_a = MathHelper.getRandomDoubleInRange(par1Random, par2, par6);
            this.field_111100_b = MathHelper.getRandomDoubleInRange(par1Random, par4, par8);
        }
    }

}

