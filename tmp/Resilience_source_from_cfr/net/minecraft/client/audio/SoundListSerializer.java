/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundList;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer
implements JsonDeserializer {
    private static final String __OBFID = "CL_00001124";

    public SoundList deserialize(JsonElement p_148578_1_, Type p_148578_2_, JsonDeserializationContext p_148578_3_) {
        JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_148578_1_, "entry");
        SoundList var5 = new SoundList();
        var5.func_148572_a(JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "replace", false));
        SoundCategory var6 = SoundCategory.func_147154_a(JsonUtils.getJsonObjectStringFieldValueOrDefault(var4, "category", SoundCategory.MASTER.getCategoryName()));
        var5.func_148571_a(var6);
        Validate.notNull((Object)((Object)var6), (String)"Invalid category", (Object[])new Object[0]);
        if (var4.has("sounds")) {
            JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var4, "sounds");
            int var8 = 0;
            while (var8 < var7.size()) {
                JsonElement var9 = var7.get(var8);
                SoundList.SoundEntry var10 = new SoundList.SoundEntry();
                if (JsonUtils.jsonElementTypeIsString(var9)) {
                    var10.func_148561_a(JsonUtils.getJsonElementStringValue(var9, "sound"));
                } else {
                    JsonObject var11 = JsonUtils.getJsonElementAsJsonObject(var9, "sound");
                    var10.func_148561_a(JsonUtils.getJsonObjectStringFieldValue(var11, "name"));
                    if (var11.has("type")) {
                        SoundList.SoundEntry.Type var12 = SoundList.SoundEntry.Type.func_148580_a(JsonUtils.getJsonObjectStringFieldValue(var11, "type"));
                        Validate.notNull((Object)((Object)var12), (String)"Invalid type", (Object[])new Object[0]);
                        var10.func_148562_a(var12);
                    }
                    if (var11.has("volume")) {
                        float var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "volume");
                        Validate.isTrue((boolean)(var13 > 0.0f), (String)"Invalid volume", (Object[])new Object[0]);
                        var10.func_148553_a(var13);
                    }
                    if (var11.has("pitch")) {
                        float var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "pitch");
                        Validate.isTrue((boolean)(var13 > 0.0f), (String)"Invalid pitch", (Object[])new Object[0]);
                        var10.func_148559_b(var13);
                    }
                    if (var11.has("weight")) {
                        int var14 = JsonUtils.getJsonObjectIntegerFieldValue(var11, "weight");
                        Validate.isTrue((boolean)(var14 > 0), (String)"Invalid weight", (Object[])new Object[0]);
                        var10.func_148554_a(var14);
                    }
                    if (var11.has("stream")) {
                        var10.func_148557_a(JsonUtils.getJsonObjectBooleanFieldValue(var11, "stream"));
                    }
                }
                var5.func_148570_a().add(var10);
                ++var8;
            }
        }
        return var5;
    }
}

