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
import net.minecraft.client.mco.McoServer;
import net.minecraft.util.ValueObject;

public class ValueObjectList
extends ValueObject {
    public List field_148772_a;
    private static final String __OBFID = "CL_00001169";

    public static ValueObjectList func_148771_a(String p_148771_0_) {
        ValueObjectList var1 = new ValueObjectList();
        var1.field_148772_a = new ArrayList();
        try {
            JsonParser var6 = new JsonParser();
            JsonObject var3 = var6.parse(p_148771_0_).getAsJsonObject();
            if (var3.get("servers").isJsonArray()) {
                JsonArray var4 = var3.get("servers").getAsJsonArray();
                Iterator var5 = var4.iterator();
                while (var5.hasNext()) {
                    var1.field_148772_a.add(McoServer.func_148802_a(((JsonElement)var5.next()).getAsJsonObject()));
                }
            }
        }
        catch (JsonIOException var6) {
        }
        catch (JsonSyntaxException var6) {
            // empty catch block
        }
        return var1;
    }
}

