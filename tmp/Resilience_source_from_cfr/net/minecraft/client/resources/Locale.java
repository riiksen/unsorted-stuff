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
package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Locale {
    private static final Splitter splitter = Splitter.on((char)'=').limit(2);
    private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    Map field_135032_a = Maps.newHashMap();
    private boolean field_135029_d;
    private static final String __OBFID = "CL_00001097";

    public synchronized void loadLocaleDataFiles(IResourceManager par1ResourceManager, List par2List) {
        this.field_135032_a.clear();
        for (String var4 : par2List) {
            String var5 = String.format("lang/%s.lang", var4);
            for (String var7 : par1ResourceManager.getResourceDomains()) {
                try {
                    this.loadLocaleData(par1ResourceManager.getAllResources(new ResourceLocation(var7, var5)));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        this.checkUnicode();
    }

    public boolean isUnicode() {
        return this.field_135029_d;
    }

    private void checkUnicode() {
        this.field_135029_d = false;
        int var1 = 0;
        int var2 = 0;
        for (String var4 : this.field_135032_a.values()) {
            int var5 = var4.length();
            var2 += var5;
            int var6 = 0;
            while (var6 < var5) {
                if (var4.charAt(var6) >= '\u0100') {
                    ++var1;
                }
                ++var6;
            }
        }
        float var7 = (float)var1 / (float)var2;
        this.field_135029_d = (double)var7 > 0.1;
    }

    private void loadLocaleData(List par1List) throws IOException {
        for (IResource var3 : par1List) {
            this.loadLocaleData(var3.getInputStream());
        }
    }

    private void loadLocaleData(InputStream par1InputStream) throws IOException {
        for (String var3 : IOUtils.readLines((InputStream)par1InputStream, (Charset)Charsets.UTF_8)) {
            String[] var4;
            if (var3.isEmpty() || var3.charAt(0) == '#' || (var4 = (String[])Iterables.toArray((Iterable)splitter.split((CharSequence)var3), String.class)) == null || var4.length != 2) continue;
            String var5 = var4[0];
            String var6 = field_135031_c.matcher(var4[1]).replaceAll("%$1s");
            this.field_135032_a.put(var5, var6);
        }
    }

    private String translateKeyPrivate(String par1Str) {
        String var2 = (String)this.field_135032_a.get(par1Str);
        return var2 == null ? par1Str : var2;
    }

    public String formatMessage(String par1Str, Object[] par2ArrayOfObj) {
        String var3 = this.translateKeyPrivate(par1Str);
        try {
            return String.format(var3, par2ArrayOfObj);
        }
        catch (IllegalFormatException var5) {
            return "Format error: " + var3;
        }
    }
}

