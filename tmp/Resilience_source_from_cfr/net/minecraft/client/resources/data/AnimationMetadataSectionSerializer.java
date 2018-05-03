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
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer
extends BaseMetadataSectionSerializer
implements JsonSerializer {
    private static final String __OBFID = "CL_00001107";

    public AnimationMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext) {
        int var8;
        ArrayList var4 = Lists.newArrayList();
        JsonObject var5 = JsonUtils.getJsonElementAsJsonObject(par1JsonElement, "metadata section");
        int var6 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "frametime", 1);
        if (var6 != 1) {
            Validate.inclusiveBetween((Object)1, (Object)Integer.MAX_VALUE, (Comparable)Integer.valueOf(var6), (String)"Invalid default frame time", (Object[])new Object[0]);
        }
        if (var5.has("frames")) {
            try {
                JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var5, "frames");
                var8 = 0;
                while (var8 < var7.size()) {
                    JsonElement var9 = var7.get(var8);
                    AnimationFrame var10 = this.parseAnimationFrame(var8, var9);
                    if (var10 != null) {
                        var4.add(var10);
                    }
                    ++var8;
                }
            }
            catch (ClassCastException var11) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + (Object)var5.get("frames"), (Throwable)var11);
            }
        }
        int var12 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "width", -1);
        var8 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "height", -1);
        if (var12 != -1) {
            Validate.inclusiveBetween((Object)1, (Object)Integer.MAX_VALUE, (Comparable)Integer.valueOf(var12), (String)"Invalid width", (Object[])new Object[0]);
        }
        if (var8 != -1) {
            Validate.inclusiveBetween((Object)1, (Object)Integer.MAX_VALUE, (Comparable)Integer.valueOf(var8), (String)"Invalid height", (Object[])new Object[0]);
        }
        return new AnimationMetadataSection(var4, var12, var8, var6);
    }

    private AnimationFrame parseAnimationFrame(int par1, JsonElement par2JsonElement) {
        if (par2JsonElement.isJsonPrimitive()) {
            return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(par2JsonElement, "frames[" + par1 + "]"));
        }
        if (par2JsonElement.isJsonObject()) {
            JsonObject var3 = JsonUtils.getJsonElementAsJsonObject(par2JsonElement, "frames[" + par1 + "]");
            int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var3, "time", -1);
            if (var3.has("time")) {
                Validate.inclusiveBetween((Object)1, (Object)Integer.MAX_VALUE, (Comparable)Integer.valueOf(var4), (String)"Invalid frame time", (Object[])new Object[0]);
            }
            int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var3, "index");
            Validate.inclusiveBetween((Object)0, (Object)Integer.MAX_VALUE, (Comparable)Integer.valueOf(var5), (String)"Invalid frame index", (Object[])new Object[0]);
            return new AnimationFrame(var5, var4);
        }
        return null;
    }

    public JsonElement serialize(AnimationMetadataSection par1AnimationMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
        JsonObject var4 = new JsonObject();
        var4.addProperty("frametime", (Number)par1AnimationMetadataSection.getFrameTime());
        if (par1AnimationMetadataSection.getFrameWidth() != -1) {
            var4.addProperty("width", (Number)par1AnimationMetadataSection.getFrameWidth());
        }
        if (par1AnimationMetadataSection.getFrameHeight() != -1) {
            var4.addProperty("height", (Number)par1AnimationMetadataSection.getFrameHeight());
        }
        if (par1AnimationMetadataSection.getFrameCount() > 0) {
            JsonArray var5 = new JsonArray();
            int var6 = 0;
            while (var6 < par1AnimationMetadataSection.getFrameCount()) {
                if (par1AnimationMetadataSection.frameHasTime(var6)) {
                    JsonObject var7 = new JsonObject();
                    var7.addProperty("index", (Number)par1AnimationMetadataSection.getFrameIndex(var6));
                    var7.addProperty("time", (Number)par1AnimationMetadataSection.getFrameTimeSingle(var6));
                    var5.add((JsonElement)var7);
                } else {
                    var5.add((JsonElement)new JsonPrimitive((Number)par1AnimationMetadataSection.getFrameIndex(var6)));
                }
                ++var6;
            }
            var4.add("frames", (JsonElement)var5);
        }
        return var4;
    }

    @Override
    public String getSectionName() {
        return "animation";
    }

    public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
        return this.serialize((AnimationMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
    }
}

