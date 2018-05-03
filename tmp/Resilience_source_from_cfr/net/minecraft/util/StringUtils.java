/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    private static final String __OBFID = "CL_00001501";

    public static String ticksToElapsedTime(int par0) {
        int var1 = par0 / 20;
        int var2 = var1 / 60;
        return (var1 %= 60) < 10 ? String.valueOf(var2) + ":0" + var1 : String.valueOf(var2) + ":" + var1;
    }

    public static String stripControlCodes(String par0Str) {
        return patternControlCode.matcher(par0Str).replaceAll("");
    }

    public static boolean isNullOrEmpty(String p_151246_0_) {
        if (p_151246_0_ != null && !"".equals(p_151246_0_)) {
            return false;
        }
        return true;
    }
}

