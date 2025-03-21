/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerSelector {
    private static final Pattern tokenPattern = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
    private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
    private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
    private static final String __OBFID = "CL_00000086";

    public static EntityPlayerMP matchOnePlayer(ICommandSender par0ICommandSender, String par1Str) {
        EntityPlayerMP[] var2 = PlayerSelector.matchPlayers(par0ICommandSender, par1Str);
        return var2 != null && var2.length == 1 ? var2[0] : null;
    }

    public static IChatComponent func_150869_b(ICommandSender p_150869_0_, String p_150869_1_) {
        EntityPlayerMP[] var2 = PlayerSelector.matchPlayers(p_150869_0_, p_150869_1_);
        if (var2 != null && var2.length != 0) {
            IChatComponent[] var3 = new IChatComponent[var2.length];
            int var4 = 0;
            while (var4 < var3.length) {
                var3[var4] = var2[var4].func_145748_c_();
                ++var4;
            }
            return CommandBase.joinNiceString(var3);
        }
        return null;
    }

    public static EntityPlayerMP[] matchPlayers(ICommandSender par0ICommandSender, String par1Str) {
        World var16;
        Matcher var2 = tokenPattern.matcher(par1Str);
        if (!var2.matches()) {
            return null;
        }
        Map var3 = PlayerSelector.getArgumentMap(var2.group(2));
        String var4 = var2.group(1);
        int var5 = PlayerSelector.getDefaultMinimumRange(var4);
        int var6 = PlayerSelector.getDefaultMaximumRange(var4);
        int var7 = PlayerSelector.getDefaultMinimumLevel(var4);
        int var8 = PlayerSelector.getDefaultMaximumLevel(var4);
        int var9 = PlayerSelector.getDefaultCount(var4);
        int var10 = WorldSettings.GameType.NOT_SET.getID();
        ChunkCoordinates var11 = par0ICommandSender.getPlayerCoordinates();
        Map var12 = PlayerSelector.func_96560_a(var3);
        String var13 = null;
        String var14 = null;
        boolean var15 = false;
        if (var3.containsKey("rm")) {
            var5 = MathHelper.parseIntWithDefault((String)var3.get("rm"), var5);
            var15 = true;
        }
        if (var3.containsKey("r")) {
            var6 = MathHelper.parseIntWithDefault((String)var3.get("r"), var6);
            var15 = true;
        }
        if (var3.containsKey("lm")) {
            var7 = MathHelper.parseIntWithDefault((String)var3.get("lm"), var7);
        }
        if (var3.containsKey("l")) {
            var8 = MathHelper.parseIntWithDefault((String)var3.get("l"), var8);
        }
        if (var3.containsKey("x")) {
            var11.posX = MathHelper.parseIntWithDefault((String)var3.get("x"), var11.posX);
            var15 = true;
        }
        if (var3.containsKey("y")) {
            var11.posY = MathHelper.parseIntWithDefault((String)var3.get("y"), var11.posY);
            var15 = true;
        }
        if (var3.containsKey("z")) {
            var11.posZ = MathHelper.parseIntWithDefault((String)var3.get("z"), var11.posZ);
            var15 = true;
        }
        if (var3.containsKey("m")) {
            var10 = MathHelper.parseIntWithDefault((String)var3.get("m"), var10);
        }
        if (var3.containsKey("c")) {
            var9 = MathHelper.parseIntWithDefault((String)var3.get("c"), var9);
        }
        if (var3.containsKey("team")) {
            var14 = (String)var3.get("team");
        }
        if (var3.containsKey("name")) {
            var13 = (String)var3.get("name");
        }
        World world = var16 = var15 ? par0ICommandSender.getEntityWorld() : null;
        if (!var4.equals("p") && !var4.equals("a")) {
            if (!var4.equals("r")) {
                return null;
            }
            List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, 0, var10, var7, var8, var12, var13, var14, var16);
            Collections.shuffle(var17);
            var17 = var17.subList(0, Math.min(var9, var17.size()));
            return var17 != null && !var17.isEmpty() ? var17.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[]{};
        }
        List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, var9, var10, var7, var8, var12, var13, var14, var16);
        return var17 != null && !var17.isEmpty() ? var17.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[]{};
    }

    public static Map func_96560_a(Map par0Map) {
        HashMap<String, Integer> var1 = new HashMap<String, Integer>();
        for (String var3 : par0Map.keySet()) {
            if (!var3.startsWith("score_") || var3.length() <= "score_".length()) continue;
            String var4 = var3.substring("score_".length());
            var1.put(var4, MathHelper.parseIntWithDefault((String)par0Map.get(var3), 1));
        }
        return var1;
    }

    public static boolean matchesMultiplePlayers(String par0Str) {
        Matcher var1 = tokenPattern.matcher(par0Str);
        if (var1.matches()) {
            Map var2 = PlayerSelector.getArgumentMap(var1.group(2));
            String var3 = var1.group(1);
            int var4 = PlayerSelector.getDefaultCount(var3);
            if (var2.containsKey("c")) {
                var4 = MathHelper.parseIntWithDefault((String)var2.get("c"), var4);
            }
            if (var4 != 1) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean hasTheseArguments(String par0Str, String par1Str) {
        Matcher var2 = tokenPattern.matcher(par0Str);
        if (var2.matches()) {
            String var3 = var2.group(1);
            if (par1Str != null && !par1Str.equals(var3)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean hasArguments(String par0Str) {
        return PlayerSelector.hasTheseArguments(par0Str, null);
    }

    private static final int getDefaultMinimumRange(String par0Str) {
        return 0;
    }

    private static final int getDefaultMaximumRange(String par0Str) {
        return 0;
    }

    private static final int getDefaultMaximumLevel(String par0Str) {
        return Integer.MAX_VALUE;
    }

    private static final int getDefaultMinimumLevel(String par0Str) {
        return 0;
    }

    private static final int getDefaultCount(String par0Str) {
        return par0Str.equals("a") ? 0 : 1;
    }

    private static Map getArgumentMap(String par0Str) {
        HashMap<String, String> var1 = new HashMap<String, String>();
        if (par0Str == null) {
            return var1;
        }
        Matcher var2 = intListPattern.matcher(par0Str);
        int var3 = 0;
        int var4 = -1;
        while (var2.find()) {
            String var5 = null;
            switch (var3++) {
                case 0: {
                    var5 = "x";
                    break;
                }
                case 1: {
                    var5 = "y";
                    break;
                }
                case 2: {
                    var5 = "z";
                    break;
                }
                case 3: {
                    var5 = "r";
                }
            }
            if (var5 != null && var2.group(1).length() > 0) {
                var1.put(var5, var2.group(1));
            }
            var4 = var2.end();
        }
        if (var4 < par0Str.length()) {
            var2 = keyValueListPattern.matcher(var4 == -1 ? par0Str : par0Str.substring(var4));
            while (var2.find()) {
                var1.put(var2.group(1), var2.group(2));
            }
        }
        return var1;
    }
}

