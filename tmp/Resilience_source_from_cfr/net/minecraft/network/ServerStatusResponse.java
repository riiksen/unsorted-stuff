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
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class ServerStatusResponse {
    private IChatComponent field_151326_a;
    private PlayerCountData field_151324_b;
    private MinecraftProtocolVersionIdentifier field_151325_c;
    private String field_151323_d;
    private static final String __OBFID = "CL_00001385";

    public IChatComponent func_151317_a() {
        return this.field_151326_a;
    }

    public void func_151315_a(IChatComponent p_151315_1_) {
        this.field_151326_a = p_151315_1_;
    }

    public PlayerCountData func_151318_b() {
        return this.field_151324_b;
    }

    public void func_151319_a(PlayerCountData p_151319_1_) {
        this.field_151324_b = p_151319_1_;
    }

    public MinecraftProtocolVersionIdentifier func_151322_c() {
        return this.field_151325_c;
    }

    public void func_151321_a(MinecraftProtocolVersionIdentifier p_151321_1_) {
        this.field_151325_c = p_151321_1_;
    }

    public void func_151320_a(String p_151320_1_) {
        this.field_151323_d = p_151320_1_;
    }

    public String func_151316_d() {
        return this.field_151323_d;
    }

    public static class MinecraftProtocolVersionIdentifier {
        private final String field_151306_a;
        private final int field_151305_b;
        private static final String __OBFID = "CL_00001389";

        public MinecraftProtocolVersionIdentifier(String p_i45275_1_, int p_i45275_2_) {
            this.field_151306_a = p_i45275_1_;
            this.field_151305_b = p_i45275_2_;
        }

        public String func_151303_a() {
            return this.field_151306_a;
        }

        public int func_151304_b() {
            return this.field_151305_b;
        }

        public static class Serializer
        implements JsonDeserializer,
        JsonSerializer {
            private static final String __OBFID = "CL_00001390";

            public MinecraftProtocolVersionIdentifier deserialize(JsonElement p_151309_1_, Type p_151309_2_, JsonDeserializationContext p_151309_3_) {
                JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151309_1_, "version");
                return new MinecraftProtocolVersionIdentifier(JsonUtils.getJsonObjectStringFieldValue(var4, "name"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "protocol"));
            }

            public JsonElement serialize(MinecraftProtocolVersionIdentifier p_151310_1_, Type p_151310_2_, JsonSerializationContext p_151310_3_) {
                JsonObject var4 = new JsonObject();
                var4.addProperty("name", p_151310_1_.func_151303_a());
                var4.addProperty("protocol", (Number)p_151310_1_.func_151304_b());
                return var4;
            }

            public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
                return this.serialize((MinecraftProtocolVersionIdentifier)par1Obj, par2Type, par3JsonSerializationContext);
            }
        }

    }

    public static class PlayerCountData {
        private final int field_151336_a;
        private final int field_151334_b;
        private GameProfile[] field_151335_c;
        private static final String __OBFID = "CL_00001386";

        public PlayerCountData(int p_i45274_1_, int p_i45274_2_) {
            this.field_151336_a = p_i45274_1_;
            this.field_151334_b = p_i45274_2_;
        }

        public int func_151332_a() {
            return this.field_151336_a;
        }

        public int func_151333_b() {
            return this.field_151334_b;
        }

        public GameProfile[] func_151331_c() {
            return this.field_151335_c;
        }

        public void func_151330_a(GameProfile[] p_151330_1_) {
            this.field_151335_c = p_151330_1_;
        }

        public static class Serializer
        implements JsonDeserializer,
        JsonSerializer {
            private static final String __OBFID = "CL_00001387";

            public PlayerCountData deserialize(JsonElement p_151311_1_, Type p_151311_2_, JsonDeserializationContext p_151311_3_) {
                JsonArray var6;
                JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151311_1_, "players");
                PlayerCountData var5 = new PlayerCountData(JsonUtils.getJsonObjectIntegerFieldValue(var4, "max"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "online"));
                if (JsonUtils.jsonObjectFieldTypeIsArray(var4, "sample") && (var6 = JsonUtils.getJsonObjectJsonArrayField(var4, "sample")).size() > 0) {
                    GameProfile[] var7 = new GameProfile[var6.size()];
                    int var8 = 0;
                    while (var8 < var7.length) {
                        JsonObject var9 = JsonUtils.getJsonElementAsJsonObject(var6.get(var8), "player[" + var8 + "]");
                        var7[var8] = new GameProfile(JsonUtils.getJsonObjectStringFieldValue(var9, "id"), JsonUtils.getJsonObjectStringFieldValue(var9, "name"));
                        ++var8;
                    }
                    var5.func_151330_a(var7);
                }
                return var5;
            }

            public JsonElement serialize(PlayerCountData p_151312_1_, Type p_151312_2_, JsonSerializationContext p_151312_3_) {
                JsonObject var4 = new JsonObject();
                var4.addProperty("max", (Number)p_151312_1_.func_151332_a());
                var4.addProperty("online", (Number)p_151312_1_.func_151333_b());
                if (p_151312_1_.func_151331_c() != null && p_151312_1_.func_151331_c().length > 0) {
                    JsonArray var5 = new JsonArray();
                    int var6 = 0;
                    while (var6 < p_151312_1_.func_151331_c().length) {
                        JsonObject var7 = new JsonObject();
                        var7.addProperty("id", p_151312_1_.func_151331_c()[var6].getId());
                        var7.addProperty("name", p_151312_1_.func_151331_c()[var6].getName());
                        var5.add((JsonElement)var7);
                        ++var6;
                    }
                    var4.add("sample", (JsonElement)var5);
                }
                return var4;
            }

            public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
                return this.serialize((PlayerCountData)par1Obj, par2Type, par3JsonSerializationContext);
            }
        }

    }

    public static class Serializer
    implements JsonDeserializer,
    JsonSerializer {
        private static final String __OBFID = "CL_00001388";

        public ServerStatusResponse deserialize(JsonElement p_151314_1_, Type p_151314_2_, JsonDeserializationContext p_151314_3_) {
            JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151314_1_, "status");
            ServerStatusResponse var5 = new ServerStatusResponse();
            if (var4.has("description")) {
                var5.func_151315_a((IChatComponent)p_151314_3_.deserialize(var4.get("description"), IChatComponent.class));
            }
            if (var4.has("players")) {
                var5.func_151319_a((PlayerCountData)p_151314_3_.deserialize(var4.get("players"), PlayerCountData.class));
            }
            if (var4.has("version")) {
                var5.func_151321_a((MinecraftProtocolVersionIdentifier)p_151314_3_.deserialize(var4.get("version"), MinecraftProtocolVersionIdentifier.class));
            }
            if (var4.has("favicon")) {
                var5.func_151320_a(JsonUtils.getJsonObjectStringFieldValue(var4, "favicon"));
            }
            return var5;
        }

        public JsonElement serialize(ServerStatusResponse p_151313_1_, Type p_151313_2_, JsonSerializationContext p_151313_3_) {
            JsonObject var4 = new JsonObject();
            if (p_151313_1_.func_151317_a() != null) {
                var4.add("description", p_151313_3_.serialize((Object)p_151313_1_.func_151317_a()));
            }
            if (p_151313_1_.func_151318_b() != null) {
                var4.add("players", p_151313_3_.serialize((Object)p_151313_1_.func_151318_b()));
            }
            if (p_151313_1_.func_151322_c() != null) {
                var4.add("version", p_151313_3_.serialize((Object)p_151313_1_.func_151322_c()));
            }
            if (p_151313_1_.func_151316_d() != null) {
                var4.addProperty("favicon", p_151313_1_.func_151316_d());
            }
            return var4;
        }

        public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
            return this.serialize((ServerStatusResponse)par1Obj, par2Type, par3JsonSerializationContext);
        }
    }

}

