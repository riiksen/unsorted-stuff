/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.TypeAdapter
 *  com.google.gson.TypeAdapterFactory
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonToken
 *  com.google.gson.stream.JsonWriter
 */
package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class EnumTypeAdapterFactory
implements TypeAdapterFactory {
    private static final String __OBFID = "CL_00001494";

    public TypeAdapter create(Gson p_create_1_, TypeToken p_create_2_) {
        Class var3 = p_create_2_.getRawType();
        if (!var3.isEnum()) {
            return null;
        }
        final HashMap var4 = new HashMap();
        T[] var5 = var3.getEnumConstants();
        int var6 = var5.length;
        int var7 = 0;
        while (var7 < var6) {
            Object var8 = var5[var7];
            var4.put(this.func_151232_a(var8), var8);
            ++var7;
        }
        return new TypeAdapter(){
            private static final String __OBFID = "CL_00001495";

            public void write(JsonWriter p_write_1_, Object p_write_2_) throws IOException {
                if (p_write_2_ == null) {
                    p_write_1_.nullValue();
                } else {
                    p_write_1_.value(EnumTypeAdapterFactory.this.func_151232_a(p_write_2_));
                }
            }

            public Object read(JsonReader p_read_1_) throws IOException {
                if (p_read_1_.peek() == JsonToken.NULL) {
                    p_read_1_.nextNull();
                    return null;
                }
                return var4.get(p_read_1_.nextString());
            }
        };
    }

    private String func_151232_a(Object p_151232_1_) {
        return p_151232_1_ instanceof Enum ? ((Enum)p_151232_1_).name().toLowerCase(Locale.US) : p_151232_1_.toString().toLowerCase(Locale.US);
    }

}

