/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.client.mco;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.mco.PendingInvite;
import net.minecraft.util.ValueObject;

public class PendingInvitesList
extends ValueObject {
    public List field_148768_a = Lists.newArrayList();
    private static final String __OBFID = "CL_00001171";

    public static PendingInvitesList func_148767_a(String p_148767_0_) {
        PendingInvitesList var1 = new PendingInvitesList();
        try {
            JsonParser var5 = new JsonParser();
            JsonObject var3 = var5.parse(p_148767_0_).getAsJsonObject();
            if (var3.get("invites").isJsonArray()) {
                Iterator var4 = var3.get("invites").getAsJsonArray().iterator();
                while (var4.hasNext()) {
                    var1.field_148768_a.add(PendingInvite.func_148773_a(((JsonElement)var4.next()).getAsJsonObject()));
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

