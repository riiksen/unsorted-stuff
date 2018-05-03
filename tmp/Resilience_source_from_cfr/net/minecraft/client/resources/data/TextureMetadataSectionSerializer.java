/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.JsonUtils;

public class TextureMetadataSectionSerializer
extends BaseMetadataSectionSerializer {
    private static final String __OBFID = "CL_00001115";

    public TextureMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext) {
        JsonObject var4 = par1JsonElement.getAsJsonObject();
        boolean var5 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "blur", false);
        boolean var6 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "clamp", false);
        ArrayList var7 = Lists.newArrayList();
        if (var4.has("mipmaps")) {
            try {
                JsonArray var8 = var4.getAsJsonArray("mipmaps");
                int var9 = 0;
                while (var9 < var8.size()) {
                    JsonElement var10 = var8.get(var9);
                    if (var10.isJsonPrimitive()) {
                        try {
                            var7.add(var10.getAsInt());
                        }
                        catch (NumberFormatException var12) {
                            throw new JsonParseException("Invalid texture->mipmap->" + var9 + ": expected number, was " + (Object)var10, (Throwable)var12);
                        }
                    } else if (var10.isJsonObject()) {
                        throw new JsonParseException("Invalid texture->mipmap->" + var9 + ": expected number, was " + (Object)var10);
                    }
                    ++var9;
                }
            }
            catch (ClassCastException var13) {
                throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + (Object)var4.get("mipmaps"), (Throwable)var13);
            }
        }
        return new TextureMetadataSection(var5, var6, var7);
    }

    @Override
    public String getSectionName() {
        return "texture";
    }
}

