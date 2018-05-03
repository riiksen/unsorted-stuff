/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer
extends BaseMetadataSectionSerializer {
    private static final String __OBFID = "CL_00001111";

    public LanguageMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext) {
        boolean var12;
        String var10;
        String var11;
        String var8;
        JsonObject var4 = par1JsonElement.getAsJsonObject();
        HashSet var5 = Sets.newHashSet();
        Iterator var6 = var4.entrySet().iterator();
        do {
            if (!var6.hasNext()) {
                return new LanguageMetadataSection(var5);
            }
            Map.Entry var7 = (Map.Entry)var6.next();
            var8 = (String)var7.getKey();
            JsonObject var9 = JsonUtils.getJsonElementAsJsonObject((JsonElement)var7.getValue(), "language");
            var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "region");
            var11 = JsonUtils.getJsonObjectStringFieldValue(var9, "name");
            var12 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var9, "bidirectional", false);
            if (var10.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + var8 + "'->region: empty value");
            }
            if (!var11.isEmpty()) continue;
            throw new JsonParseException("Invalid language->'" + var8 + "'->name: empty value");
        } while (var5.add(new Language(var8, var10, var11, var12)));
        throw new JsonParseException("Duplicate language->'" + var8 + "' defined");
    }

    @Override
    public String getSectionName() {
        return "language";
    }
}

