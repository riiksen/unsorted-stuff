/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  org.apache.commons.io.Charsets
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class StringTranslate {
    private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    private static final Splitter equalSignSplitter = Splitter.on((char)'=').limit(2);
    private static StringTranslate instance = new StringTranslate();
    private final Map languageList = Maps.newHashMap();
    private long lastUpdateTimeInMilliseconds;
    private static final String __OBFID = "CL_00001212";

    public StringTranslate() {
        try {
            InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
            for (String var3 : IOUtils.readLines((InputStream)var1, (Charset)Charsets.UTF_8)) {
                String[] var4;
                if (var3.isEmpty() || var3.charAt(0) == '#' || (var4 = (String[])Iterables.toArray((Iterable)equalSignSplitter.split((CharSequence)var3), String.class)) == null || var4.length != 2) continue;
                String var5 = var4[0];
                String var6 = numericVariablePattern.matcher(var4[1]).replaceAll("%$1s");
                this.languageList.put(var5, var6);
            }
            this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
        }
        catch (IOException var1) {
            // empty catch block
        }
    }

    static StringTranslate getInstance() {
        return instance;
    }

    public static synchronized void replaceWith(Map par0Map) {
        StringTranslate.instance.languageList.clear();
        StringTranslate.instance.languageList.putAll(par0Map);
        StringTranslate.instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
    }

    public synchronized String translateKey(String par1Str) {
        return this.tryTranslateKey(par1Str);
    }

    public synchronized /* varargs */ String translateKeyFormat(String par1Str, Object ... par2ArrayOfObj) {
        String var3 = this.tryTranslateKey(par1Str);
        try {
            return String.format(var3, par2ArrayOfObj);
        }
        catch (IllegalFormatException var5) {
            return "Format error: " + var3;
        }
    }

    private String tryTranslateKey(String par1Str) {
        String var2 = (String)this.languageList.get(par1Str);
        return var2 == null ? par1Str : var2;
    }

    public synchronized boolean containsTranslateKey(String par1Str) {
        return this.languageList.containsKey(par1Str);
    }

    public long getLastUpdateTimeInMilliseconds() {
        return this.lastUpdateTimeInMilliseconds;
    }
}

