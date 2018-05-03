/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern uuidPattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    private static final String __OBFID = "CL_00001633";

    public static EnumOS getOSType() {
        String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? EnumOS.WINDOWS : (var0.contains("mac") ? EnumOS.MACOS : (var0.contains("solaris") ? EnumOS.SOLARIS : (var0.contains("sunos") ? EnumOS.SOLARIS : (var0.contains("linux") ? EnumOS.LINUX : (var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }

    public static boolean isUUIDString(String p_147172_0_) {
        return uuidPattern.matcher(p_147172_0_).matches();
    }

    public static UUID tryGetUUIDFromString(String p_147173_0_) {
        String var1;
        if (p_147173_0_ == null) {
            return null;
        }
        if (Util.isUUIDString(p_147173_0_)) {
            return UUID.fromString(p_147173_0_);
        }
        if (p_147173_0_.length() == 32 && Util.isUUIDString(var1 = String.valueOf(p_147173_0_.substring(0, 8)) + "-" + p_147173_0_.substring(8, 12) + "-" + p_147173_0_.substring(12, 16) + "-" + p_147173_0_.substring(16, 20) + "-" + p_147173_0_.substring(20, 32))) {
            return UUID.fromString(var1);
        }
        return null;
    }

    public static enum EnumOS {
        LINUX("LINUX", 0),
        SOLARIS("SOLARIS", 1),
        WINDOWS("WINDOWS", 2),
        MACOS("MACOS", 3),
        UNKNOWN("UNKNOWN", 4);
        
        private static final EnumOS[] $VALUES;
        private static final String __OBFID = "CL_00001660";

        static {
            $VALUES = new EnumOS[]{LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN};
        }

        private EnumOS(String par1Str, int par2, String string2, int n2) {
        }
    }

}

