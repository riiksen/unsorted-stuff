/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.multiplayer;

import java.util.Hashtable;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
    private final String ipAddress;
    private final int serverPort;
    private static final String __OBFID = "CL_00000889";

    public ServerAddress(String par1Str, int par2) {
        this.ipAddress = par1Str;
        this.serverPort = par2;
    }

    public String getIP() {
        return this.ipAddress;
    }

    public int getPort() {
        return this.serverPort;
    }

    public static ServerAddress func_78860_a(String par0Str) {
        int var6;
        int var2;
        if (par0Str == null) {
            return null;
        }
        String[] var1 = par0Str.split(":");
        if (par0Str.startsWith("[") && (var2 = par0Str.indexOf("]")) > 0) {
            String var3 = par0Str.substring(1, var2);
            String var4 = par0Str.substring(var2 + 1).trim();
            if (var4.startsWith(":") && var4.length() > 0) {
                var4 = var4.substring(1);
                var1 = new String[]{var3, var4};
            } else {
                var1 = new String[]{var3};
            }
        }
        if (var1.length > 2) {
            var1 = new String[]{par0Str};
        }
        String var5 = var1[0];
        int n = var6 = var1.length > 1 ? ServerAddress.parseIntWithDefault(var1[1], 25565) : 25565;
        if (var6 == 25565) {
            String[] var7 = ServerAddress.getServerAddress(var5);
            var5 = var7[0];
            var6 = ServerAddress.parseIntWithDefault(var7[1], 25565);
        }
        return new ServerAddress(var5, var6);
    }

    private static String[] getServerAddress(String par0Str) {
        try {
            String var1 = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable<String, String> var2 = new Hashtable<String, String>();
            var2.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            var2.put("java.naming.provider.url", "dns:");
            var2.put("com.sun.jndi.dns.timeout.retries", "1");
            InitialDirContext var3 = new InitialDirContext(var2);
            Attributes var4 = var3.getAttributes("_minecraft._tcp." + par0Str, new String[]{"SRV"});
            String[] var5 = var4.get("srv").get().toString().split(" ", 4);
            return new String[]{var5[3], var5[2]};
        }
        catch (Throwable var6) {
            return new String[]{par0Str, Integer.toString(25565)};
        }
    }

    private static int parseIntWithDefault(String par0Str, int par1) {
        try {
            return Integer.parseInt(par0Str.trim());
        }
        catch (Exception var3) {
            return par1;
        }
    }
}

