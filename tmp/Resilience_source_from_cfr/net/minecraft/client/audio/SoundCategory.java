/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
    MASTER("master", 0),
    MUSIC("music", 1),
    RECORDS("record", 2),
    WEATHER("weather", 3),
    BLOCKS("block", 4),
    MOBS("hostile", 5),
    ANIMALS("neutral", 6),
    PLAYERS("player", 7),
    AMBIENT("ambient", 8);
    
    private static final Map field_147168_j;
    private static final Map field_147169_k;
    private final String categoryName;
    private final int categoryId;
    private static final String __OBFID = "CL_00001686";

    static {
        field_147168_j = Maps.newHashMap();
        field_147169_k = Maps.newHashMap();
        SoundCategory[] var0 = SoundCategory.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            SoundCategory var3 = var0[var2];
            if (field_147168_j.containsKey(var3.getCategoryName()) || field_147169_k.containsKey(var3.getCategoryId())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + (Object)((Object)var3));
            }
            field_147168_j.put(var3.getCategoryName(), var3);
            field_147169_k.put(var3.getCategoryId(), var3);
            ++var2;
        }
    }

    private SoundCategory(String p_i45126_3_, int p_i45126_4_, String string2, int n2) {
        this.categoryName = p_i45126_3_;
        this.categoryId = p_i45126_4_;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public static SoundCategory func_147154_a(String p_147154_0_) {
        return (SoundCategory)((Object)field_147168_j.get(p_147154_0_));
    }
}

