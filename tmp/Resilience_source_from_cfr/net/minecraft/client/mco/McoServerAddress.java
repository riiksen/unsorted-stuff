/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.client.mco;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ValueObject;

public class McoServerAddress
extends ValueObject {
    public String field_148770_a;
    private static final String __OBFID = "CL_00001168";

    public static McoServerAddress func_148769_a(String p_148769_0_) {
        JsonParser var1 = new JsonParser();
        McoServerAddress var2 = new McoServerAddress();
        try {
            JsonObject var4 = var1.parse(p_148769_0_).getAsJsonObject();
            var2.field_148770_a = var4.get("address").getAsString();
        }
        catch (JsonIOException var4) {
        }
        catch (JsonSyntaxException var4) {
            // empty catch block
        }
        return var2;
    }
}

