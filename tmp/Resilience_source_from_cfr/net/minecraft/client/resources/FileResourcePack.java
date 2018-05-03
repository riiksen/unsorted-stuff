/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.ResourcePackFileNotFoundException;

public class FileResourcePack
extends AbstractResourcePack
implements Closeable {
    public static final Splitter entryNameSplitter = Splitter.on((char)'/').omitEmptyStrings().limit(3);
    private ZipFile resourcePackZipFile;
    private static final String __OBFID = "CL_00001075";

    public FileResourcePack(File par1File) {
        super(par1File);
    }

    private ZipFile getResourcePackZipFile() throws IOException {
        if (this.resourcePackZipFile == null) {
            this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
        }
        return this.resourcePackZipFile;
    }

    @Override
    protected InputStream getInputStreamByName(String par1Str) throws IOException {
        ZipFile var2 = this.getResourcePackZipFile();
        ZipEntry var3 = var2.getEntry(par1Str);
        if (var3 == null) {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, par1Str);
        }
        return var2.getInputStream(var3);
    }

    @Override
    public boolean hasResourceName(String par1Str) {
        try {
            if (this.getResourcePackZipFile().getEntry(par1Str) != null) {
                return true;
            }
            return false;
        }
        catch (IOException var3) {
            return false;
        }
    }

    @Override
    public Set getResourceDomains() {
        ZipFile var1;
        try {
            var1 = this.getResourcePackZipFile();
        }
        catch (IOException var8) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> var2 = var1.entries();
        HashSet var3 = Sets.newHashSet();
        while (var2.hasMoreElements()) {
            ArrayList var6;
            ZipEntry var4 = var2.nextElement();
            String var5 = var4.getName();
            if (!var5.startsWith("assets/") || (var6 = Lists.newArrayList((Iterable)entryNameSplitter.split((CharSequence)var5))).size() <= 1) continue;
            String var7 = (String)var6.get(1);
            if (!var7.equals(var7.toLowerCase())) {
                this.logNameNotLowercase(var7);
                continue;
            }
            var3.add(var7);
        }
        return var3;
    }

    protected void finalize() throws Throwable {
        this.close();
        Object.super.finalize();
    }

    @Override
    public void close() throws IOException {
        if (this.resourcePackZipFile != null) {
            this.resourcePackZipFile.close();
            this.resourcePackZipFile = null;
        }
    }
}

