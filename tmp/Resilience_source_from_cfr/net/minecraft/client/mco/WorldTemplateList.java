/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.client.mco;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.util.ValueObject;

public class WorldTemplateList
extends ValueObject {
    public List field_148782_a;
    private static final String __OBFID = "CL_00001175";

    public static WorldTemplateList func_148781_a(String p_148781_0_) {
        WorldTemplateList var1 = new WorldTemplateList();
        var1.field_148782_a = new ArrayList();
        try {
            JsonParser var5 = new JsonParser();
            JsonObject var3 = var5.parse(p_148781_0_).getAsJsonObject();
            if (var3.get("templates").isJsonArray()) {
                Iterator var4 = var3.get("templates").getAsJsonArray().iterator();
                while (var4.hasNext()) {
                    var1.field_148782_a.add(WorldTemplate.func_148783_a(((JsonElement)var4.next()).getAsJsonObject()));
                }
            }
        }
        catch (JsonIOException var5) {
        }
        catch (JsonSyntaxException var5) {
            // empty catch block
        }
        return var1;
    }
}

