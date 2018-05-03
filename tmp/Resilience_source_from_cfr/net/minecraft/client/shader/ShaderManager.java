/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.opengl.GL20
 */
package net.minecraft.client.shader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class ShaderManager {
    private static final Logger logger = LogManager.getLogger();
    private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
    private static ShaderManager staticShaderManager = null;
    private static int field_147999_d = -1;
    private static boolean field_148000_e = true;
    private final Map field_147997_f;
    private final List field_147998_g;
    private final List field_148010_h;
    private final List field_148011_i;
    private final List field_148008_j;
    private final Map field_148009_k;
    private final int field_148006_l;
    private final String field_148007_m;
    private final boolean field_148004_n;
    private boolean field_148005_o;
    private final JsonBlendingMode field_148016_p;
    private final List field_148015_q;
    private final List field_148014_r;
    private final ShaderLoader field_148013_s;
    private final ShaderLoader field_148012_t;
    private static final String __OBFID = "CL_00001040";

    public ShaderManager(IResourceManager p_i45087_1_, String p_i45087_2_) throws JsonException {
        this.field_147997_f = Maps.newHashMap();
        this.field_147998_g = Lists.newArrayList();
        this.field_148010_h = Lists.newArrayList();
        this.field_148011_i = Lists.newArrayList();
        this.field_148008_j = Lists.newArrayList();
        this.field_148009_k = Maps.newHashMap();
        JsonParser var3 = new JsonParser();
        ResourceLocation var4 = new ResourceLocation("shaders/program/" + p_i45087_2_ + ".json");
        this.field_148007_m = p_i45087_2_;
        InputStream var5 = null;
        try {
            try {
                JsonArray var30;
                var5 = p_i45087_1_.getResource(var4).getInputStream();
                JsonObject var6 = var3.parse(IOUtils.toString((InputStream)var5, (Charset)Charsets.UTF_8)).getAsJsonObject();
                String var7 = JsonUtils.getJsonObjectStringFieldValue(var6, "vertex");
                String var28 = JsonUtils.getJsonObjectStringFieldValue(var6, "fragment");
                JsonArray var9 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "samplers", null);
                if (var9 != null) {
                    int var10 = 0;
                    for (JsonElement var12 : var9) {
                        try {
                            this.func_147996_a(var12);
                        }
                        catch (Exception var25) {
                            JsonException var14 = JsonException.func_151379_a(var25);
                            var14.func_151380_a("samplers[" + var10 + "]");
                            throw var14;
                        }
                        ++var10;
                    }
                }
                if ((var30 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "attributes", null)) != null) {
                    int var29 = 0;
                    this.field_148015_q = Lists.newArrayListWithCapacity((int)var30.size());
                    this.field_148014_r = Lists.newArrayListWithCapacity((int)var30.size());
                    for (JsonElement var13 : var30) {
                        try {
                            this.field_148014_r.add(JsonUtils.getJsonElementStringValue(var13, "attribute"));
                        }
                        catch (Exception var24) {
                            JsonException var15 = JsonException.func_151379_a(var24);
                            var15.func_151380_a("attributes[" + var29 + "]");
                            throw var15;
                        }
                        ++var29;
                    }
                } else {
                    this.field_148015_q = null;
                    this.field_148014_r = null;
                }
                JsonArray var33 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "uniforms", null);
                if (var33 != null) {
                    int var32 = 0;
                    for (JsonElement var36 : var33) {
                        try {
                            this.func_147987_b(var36);
                        }
                        catch (Exception var23) {
                            JsonException var16 = JsonException.func_151379_a(var23);
                            var16.func_151380_a("uniforms[" + var32 + "]");
                            throw var16;
                        }
                        ++var32;
                    }
                }
                this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObjectFieldOrDefault(var6, "blend", null));
                this.field_148004_n = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var6, "cull", true);
                this.field_148013_s = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.VERTEX, var7);
                this.field_148012_t = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.FRAGMENT, var28);
                this.field_148006_l = ShaderLinkHelper.getStaticShaderLinkHelper().func_148078_c();
                ShaderLinkHelper.getStaticShaderLinkHelper().func_148075_b(this);
                this.func_147990_i();
                if (this.field_148014_r != null) {
                    for (String var34 : this.field_148014_r) {
                        int var37 = GL20.glGetAttribLocation((int)this.field_148006_l, (CharSequence)var34);
                        this.field_148015_q.add(var37);
                    }
                }
            }
            catch (Exception var26) {
                JsonException var8 = JsonException.func_151379_a(var26);
                var8.func_151381_b(var4.getResourcePath());
                throw var8;
            }
        }
        finally {
            IOUtils.closeQuietly((InputStream)var5);
        }
        this.func_147985_d();
    }

    public void func_147988_a() {
        ShaderLinkHelper.getStaticShaderLinkHelper().func_148077_a(this);
    }

    public void func_147993_b() {
        GL20.glUseProgram((int)0);
        field_147999_d = -1;
        staticShaderManager = null;
        field_148000_e = true;
        int var1 = 0;
        while (var1 < this.field_148010_h.size()) {
            if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null) {
                GL13.glActiveTexture((int)(33984 + var1));
                GL11.glBindTexture((int)3553, (int)0);
            }
            ++var1;
        }
    }

    public void func_147995_c() {
        this.field_148005_o = false;
        staticShaderManager = this;
        this.field_148016_p.func_148109_a();
        if (this.field_148006_l != field_147999_d) {
            GL20.glUseProgram((int)this.field_148006_l);
            field_147999_d = this.field_148006_l;
        }
        if (field_148000_e != this.field_148004_n) {
            field_148000_e = this.field_148004_n;
            if (this.field_148004_n) {
                GL11.glEnable((int)2884);
            } else {
                GL11.glDisable((int)2884);
            }
        }
        int var1 = 0;
        while (var1 < this.field_148010_h.size()) {
            if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null) {
                GL13.glActiveTexture((int)(33984 + var1));
                GL11.glEnable((int)3553);
                Object var2 = this.field_147997_f.get(this.field_147998_g.get(var1));
                int var3 = -1;
                if (var2 instanceof Framebuffer) {
                    var3 = ((Framebuffer)var2).framebufferTexture;
                } else if (var2 instanceof ITextureObject) {
                    var3 = ((ITextureObject)var2).getGlTextureId();
                } else if (var2 instanceof Integer) {
                    var3 = (Integer)var2;
                }
                if (var3 != -1) {
                    GL11.glBindTexture((int)3553, (int)var3);
                    GL20.glUniform1i((int)GL20.glGetUniformLocation((int)this.field_148006_l, (CharSequence)((CharSequence)this.field_147998_g.get(var1))), (int)var1);
                }
            }
            ++var1;
        }
        for (ShaderUniform var5 : this.field_148011_i) {
            var5.func_148093_b();
        }
    }

    public void func_147985_d() {
        this.field_148005_o = true;
    }

    public ShaderUniform func_147991_a(String p_147991_1_) {
        return this.field_148009_k.containsKey(p_147991_1_) ? (ShaderUniform)this.field_148009_k.get(p_147991_1_) : null;
    }

    public ShaderUniform func_147984_b(String p_147984_1_) {
        return this.field_148009_k.containsKey(p_147984_1_) ? (ShaderUniform)this.field_148009_k.get(p_147984_1_) : defaultShaderUniform;
    }

    private void func_147990_i() {
        String var3;
        int var4;
        int var1 = 0;
        int var2 = 0;
        while (var1 < this.field_147998_g.size()) {
            var3 = (String)this.field_147998_g.get(var1);
            var4 = GL20.glGetUniformLocation((int)this.field_148006_l, (CharSequence)var3);
            if (var4 == -1) {
                logger.warn("Shader " + this.field_148007_m + "could not find sampler named " + var3 + " in the specified shader program.");
                this.field_147997_f.remove(var3);
                this.field_147998_g.remove(var2);
                --var2;
            } else {
                this.field_148010_h.add(var4);
            }
            ++var1;
            ++var2;
        }
        for (ShaderUniform var6 : this.field_148011_i) {
            var3 = var6.func_148086_a();
            var4 = GL20.glGetUniformLocation((int)this.field_148006_l, (CharSequence)var3);
            if (var4 == -1) {
                logger.warn("Could not find uniform named " + var3 + " in the specified" + " shader program.");
                continue;
            }
            this.field_148008_j.add(var4);
            var6.func_148084_b(var4);
            this.field_148009_k.put(var3, var6);
        }
    }

    private void func_147996_a(JsonElement p_147996_1_) {
        JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147996_1_, "sampler");
        String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        if (!JsonUtils.jsonObjectFieldTypeIsString(var2, "file")) {
            this.field_147997_f.put(var3, null);
            this.field_147998_g.add(var3);
        } else {
            this.field_147998_g.add(var3);
        }
    }

    public void func_147992_a(String p_147992_1_, Object p_147992_2_) {
        if (this.field_147997_f.containsKey(p_147992_1_)) {
            this.field_147997_f.remove(p_147992_1_);
        }
        this.field_147997_f.put(p_147992_1_, p_147992_2_);
        this.func_147985_d();
    }

    private void func_147987_b(JsonElement p_147987_1_) throws JsonException {
        JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147987_1_, "uniform");
        String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        int var4 = ShaderUniform.func_148085_a(JsonUtils.getJsonObjectStringFieldValue(var2, "type"));
        int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var2, "count");
        float[] var6 = new float[Math.max(var5, 16)];
        JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
        if (var7.size() != var5 && var7.size() > 1) {
            throw new JsonException("Invalid amount of values specified (expected " + var5 + ", found " + var7.size() + ")");
        }
        int var8 = 0;
        for (JsonElement var10 : var7) {
            try {
                var6[var8] = JsonUtils.getJsonElementFloatValue(var10, "value");
            }
            catch (Exception var13) {
                JsonException var12 = JsonException.func_151379_a(var13);
                var12.func_151380_a("values[" + var8 + "]");
                throw var12;
            }
            ++var8;
        }
        if (var5 > 1 && var7.size() == 1) {
            while (var8 < var5) {
                var6[var8] = var6[0];
                ++var8;
            }
        }
        int var14 = var5 > 1 && var5 <= 4 && var4 < 8 ? var5 - 1 : 0;
        ShaderUniform var15 = new ShaderUniform(var3, var4 + var14, var5, this);
        if (var4 <= 3) {
            var15.func_148083_a((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
        } else if (var4 <= 7) {
            var15.func_148092_b(var6[0], var6[1], var6[2], var6[3]);
        } else {
            var15.func_148097_a(var6);
        }
        this.field_148011_i.add(var15);
    }

    public ShaderLoader func_147989_e() {
        return this.field_148013_s;
    }

    public ShaderLoader func_147994_f() {
        return this.field_148012_t;
    }

    public int func_147986_h() {
        return this.field_148006_l;
    }
}

