/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public class ChatAllowedCharacters {
    public static final char[] allowedCharacters;
    private static final String __OBFID = "CL_00001606";

    static {
        char[] arrc = new char[15];
        arrc[0] = 47;
        arrc[1] = 10;
        arrc[2] = 13;
        arrc[3] = 9;
        arrc[5] = 12;
        arrc[6] = 96;
        arrc[7] = 63;
        arrc[8] = 42;
        arrc[9] = 92;
        arrc[10] = 60;
        arrc[11] = 62;
        arrc[12] = 124;
        arrc[13] = 34;
        arrc[14] = 58;
        allowedCharacters = arrc;
    }

    public static boolean isAllowedCharacter(char par0) {
        if (par0 != '\u00a7' && par0 >= ' ' && par0 != '') {
            return true;
        }
        return false;
    }

    public static String filerAllowedCharacters(String par0Str) {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = par0Str.toCharArray();
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            char var5 = var2[var4];
            if (ChatAllowedCharacters.isAllowedCharacter(var5)) {
                var1.append(var5);
            }
            ++var4;
        }
        return var1.toString();
    }
}

