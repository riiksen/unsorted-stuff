/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r');
    
    private static final Map formattingCodeMapping;
    private static final Map nameMapping;
    private static final Pattern formattingCodePattern;
    private final char formattingCode;
    private final boolean fancyStyling;
    private final String controlString;
    private static final String __OBFID = "CL_00000342";

    static {
        formattingCodeMapping = new HashMap();
        nameMapping = new HashMap();
        formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
        EnumChatFormatting[] var0 = EnumChatFormatting.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            EnumChatFormatting var3 = var0[var2];
            formattingCodeMapping.put(Character.valueOf(var3.getFormattingCode()), var3);
            nameMapping.put(var3.getFriendlyName(), var3);
            ++var2;
        }
    }

    private EnumChatFormatting(String par3, int n2, char c) {
        this(string, n, (char)par3, false);
    }

    private EnumChatFormatting(String par3, int par4, char c, boolean bl) {
        this.formattingCode = par3;
        this.fancyStyling = par4;
        this.controlString = "\u00a7" + (char)par3;
    }

    public char getFormattingCode() {
        return this.formattingCode;
    }

    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    public boolean isColor() {
        if (!this.fancyStyling && this != RESET) {
            return true;
        }
        return false;
    }

    public String getFriendlyName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.controlString;
    }

    public static String getTextWithoutFormattingCodes(String par0Str) {
        return par0Str == null ? null : formattingCodePattern.matcher(par0Str).replaceAll("");
    }

    public static EnumChatFormatting getValueByName(String par0Str) {
        return par0Str == null ? null : (EnumChatFormatting)((Object)nameMapping.get(par0Str.toLowerCase()));
    }

    public static Collection getValidValues(boolean par0, boolean par1) {
        ArrayList<String> var2 = new ArrayList<String>();
        EnumChatFormatting[] var3 = EnumChatFormatting.values();
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumChatFormatting var6 = var3[var5];
            if (!(var6.isColor() && !par0 || var6.isFancyStyling() && !par1)) {
                var2.add(var6.getFriendlyName());
            }
            ++var5;
        }
        return var2;
    }
}

