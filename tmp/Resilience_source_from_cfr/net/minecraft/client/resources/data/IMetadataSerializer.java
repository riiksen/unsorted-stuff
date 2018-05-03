/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

public class IMetadataSerializer {
    private final IRegistry metadataSectionSerializerRegistry = new RegistrySimple();
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    private static final String __OBFID = "CL_00001101";

    public void registerMetadataSectionType(IMetadataSectionSerializer par1MetadataSectionSerializer, Class par2Class) {
        this.metadataSectionSerializerRegistry.putObject(par1MetadataSectionSerializer.getSectionName(), new Registration(par1MetadataSectionSerializer, par2Class, null));
        this.gsonBuilder.registerTypeAdapter((Type)par2Class, (Object)par1MetadataSectionSerializer);
        this.gson = null;
    }

    public IMetadataSection parseMetadataSection(String par1Str, JsonObject par2JsonObject) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        if (!par2JsonObject.has(par1Str)) {
            return null;
        }
        if (!par2JsonObject.get(par1Str).isJsonObject()) {
            throw new IllegalArgumentException("Invalid metadata for '" + par1Str + "' - expected object, found " + (Object)par2JsonObject.get(par1Str));
        }
        Registration var3 = (Registration)this.metadataSectionSerializerRegistry.getObject(par1Str);
        if (var3 == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + par1Str + "'");
        }
        return (IMetadataSection)this.getGson().fromJson((JsonElement)par2JsonObject.getAsJsonObject(par1Str), var3.field_110500_b);
    }

    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }
        return this.gson;
    }

    class Registration {
        final IMetadataSectionSerializer field_110502_a;
        final Class field_110500_b;
        private static final String __OBFID = "CL_00001103";

        private Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class) {
            this.field_110502_a = par2MetadataSectionSerializer;
            this.field_110500_b = par3Class;
        }

        Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class, Object par4MetadataSerializerEmptyAnon) {
            this(par2MetadataSectionSerializer, par3Class);
        }
    }

}

