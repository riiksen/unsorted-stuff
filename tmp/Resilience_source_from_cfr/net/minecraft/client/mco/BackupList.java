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
import net.minecraft.client.mco.Backup;

public class BackupList {
    public List theBackupList;
    private static final String __OBFID = "CL_00001165";

    public static BackupList func_148796_a(String p_148796_0_) {
        JsonParser var1 = new JsonParser();
        BackupList var2 = new BackupList();
        var2.theBackupList = new ArrayList();
        try {
            JsonElement var5 = var1.parse(p_148796_0_).getAsJsonObject().get("backups");
            if (var5.isJsonArray()) {
                Iterator var4 = var5.getAsJsonArray().iterator();
                while (var4.hasNext()) {
                    var2.theBackupList.add(Backup.func_148777_a((JsonElement)var4.next()));
                }
            }
        }
        catch (JsonIOException var5) {
        }
        catch (JsonSyntaxException var5) {
            // empty catch block
        }
        return var2;
    }
}

