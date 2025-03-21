/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource
implements IResource {
    private final Map mapMetadataSections = Maps.newHashMap();
    private final ResourceLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final IMetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;
    private static final String __OBFID = "CL_00001093";

    public SimpleResource(ResourceLocation par1ResourceLocation, InputStream par2InputStream, InputStream par3InputStream, IMetadataSerializer par4MetadataSerializer) {
        this.srResourceLocation = par1ResourceLocation;
        this.resourceInputStream = par2InputStream;
        this.mcmetaInputStream = par3InputStream;
        this.srMetadataSerializer = par4MetadataSerializer;
    }

    @Override
    public InputStream getInputStream() {
        return this.resourceInputStream;
    }

    @Override
    public boolean hasMetadata() {
        if (this.mcmetaInputStream != null) {
            return true;
        }
        return false;
    }

    @Override
    public IMetadataSection getMetadata(String par1Str) {
        IMetadataSection var6;
        if (!this.hasMetadata()) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader var2 = null;
            try {
                var2 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                this.mcmetaJson = new JsonParser().parse((Reader)var2).getAsJsonObject();
            }
            finally {
                IOUtils.closeQuietly((Reader)var2);
            }
        }
        if ((var6 = (IMetadataSection)this.mapMetadataSections.get(par1Str)) == null) {
            var6 = this.srMetadataSerializer.parseMetadataSection(par1Str, this.mcmetaJson);
        }
        return var6;
    }

    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj instanceof SimpleResource) {
            SimpleResource var2 = (SimpleResource)par1Obj;
            return this.srResourceLocation != null ? this.srResourceLocation.equals(var2.srResourceLocation) : var2.srResourceLocation == null;
        }
        return false;
    }

    public int hashCode() {
        return this.srResourceLocation == null ? 0 : this.srResourceLocation.hashCode();
    }
}

