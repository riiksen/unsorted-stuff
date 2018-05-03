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
 *  javax.vecmath.Matrix4f
 *  org.apache.commons.io.IOUtils
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.vecmath.Matrix4f;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class ShaderGroup {
    private final Framebuffer mainFramebuffer;
    private final IResourceManager resourceManager;
    private final String shaderGroupName;
    private final List listShaders = Lists.newArrayList();
    private final Map mapFramebuffers = Maps.newHashMap();
    private final List listFramebuffers = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    private int mainFramebufferWidth;
    private int mainFramebufferHeight;
    private float field_148036_j;
    private float field_148037_k;
    private static final String __OBFID = "CL_00001041";

    public ShaderGroup(IResourceManager p_i45088_1_, Framebuffer p_i45088_2_, ResourceLocation p_i45088_3_) throws JsonException {
        this.resourceManager = p_i45088_1_;
        this.mainFramebuffer = p_i45088_2_;
        this.field_148036_j = 0.0f;
        this.field_148037_k = 0.0f;
        this.mainFramebufferWidth = p_i45088_2_.framebufferWidth;
        this.mainFramebufferHeight = p_i45088_2_.framebufferHeight;
        this.shaderGroupName = p_i45088_3_.toString();
        this.resetProjectionMatrix();
        this.initFromLocation(p_i45088_3_);
    }

    public void initFromLocation(ResourceLocation p_148025_1_) throws JsonException {
        block12 : {
            JsonParser var2 = new JsonParser();
            InputStream var3 = null;
            try {
                try {
                    JsonArray var6;
                    int var7;
                    IResource var4 = this.resourceManager.getResource(p_148025_1_);
                    var3 = var4.getInputStream();
                    JsonObject var21 = var2.parse(IOUtils.toString((InputStream)var3, (Charset)Charsets.UTF_8)).getAsJsonObject();
                    if (JsonUtils.jsonObjectFieldTypeIsArray(var21, "targets")) {
                        var6 = var21.getAsJsonArray("targets");
                        var7 = 0;
                        for (JsonElement var9 : var6) {
                            try {
                                this.initTarget(var9);
                            }
                            catch (Exception var18) {
                                JsonException var11 = JsonException.func_151379_a(var18);
                                var11.func_151380_a("targets[" + var7 + "]");
                                throw var11;
                            }
                            ++var7;
                        }
                    }
                    if (!JsonUtils.jsonObjectFieldTypeIsArray(var21, "passes")) break block12;
                    var6 = var21.getAsJsonArray("passes");
                    var7 = 0;
                    for (JsonElement var9 : var6) {
                        try {
                            this.initPass(var9);
                        }
                        catch (Exception var17) {
                            JsonException var11 = JsonException.func_151379_a(var17);
                            var11.func_151380_a("passes[" + var7 + "]");
                            throw var11;
                        }
                        ++var7;
                    }
                }
                catch (Exception var19) {
                    JsonException var5 = JsonException.func_151379_a(var19);
                    var5.func_151381_b(p_148025_1_.getResourcePath());
                    throw var5;
                }
            }
            finally {
                IOUtils.closeQuietly((InputStream)var3);
            }
        }
    }

    private void initTarget(JsonElement p_148027_1_) throws JsonException {
        if (JsonUtils.jsonElementTypeIsString(p_148027_1_)) {
            this.addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
        } else {
            JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148027_1_, "target");
            String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
            int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "width", this.mainFramebufferWidth);
            int var5 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "height", this.mainFramebufferHeight);
            if (this.mapFramebuffers.containsKey(var3)) {
                throw new JsonException(String.valueOf(var3) + " is already defined");
            }
            this.addFramebuffer(var3, var4, var5);
        }
    }

    private void initPass(JsonElement p_148019_1_) throws JsonException {
        JsonArray var20;
        JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148019_1_, "pass");
        String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        String var4 = JsonUtils.getJsonObjectStringFieldValue(var2, "intarget");
        String var5 = JsonUtils.getJsonObjectStringFieldValue(var2, "outtarget");
        Framebuffer var6 = this.getFramebuffer(var4);
        Framebuffer var7 = this.getFramebuffer(var5);
        if (var6 == null) {
            throw new JsonException("Input target '" + var4 + "' does not exist");
        }
        if (var7 == null) {
            throw new JsonException("Output target '" + var5 + "' does not exist");
        }
        Shader var8 = this.addShader(var3, var6, var7);
        JsonArray var9 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var2, "auxtargets", null);
        if (var9 != null) {
            int var10 = 0;
            for (JsonElement var12 : var9) {
                try {
                    JsonObject var13 = JsonUtils.getJsonElementAsJsonObject(var12, "auxtarget");
                    String var24 = JsonUtils.getJsonObjectStringFieldValue(var13, "name");
                    String var15 = JsonUtils.getJsonObjectStringFieldValue(var13, "id");
                    Framebuffer var16 = this.getFramebuffer(var15);
                    if (var16 == null) {
                        throw new JsonException("Render target '" + var15 + "' does not exist");
                    }
                    var8.addAuxFramebuffer(var24, var16, var16.framebufferTextureWidth, var16.framebufferTextureHeight);
                }
                catch (Exception var18) {
                    JsonException var14 = JsonException.func_151379_a(var18);
                    var14.func_151380_a("auxtargets[" + var10 + "]");
                    throw var14;
                }
                ++var10;
            }
        }
        if ((var20 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var2, "uniforms", null)) != null) {
            int var19 = 0;
            for (JsonElement var22 : var20) {
                try {
                    this.initUniform(var22);
                }
                catch (Exception var17) {
                    JsonException var23 = JsonException.func_151379_a(var17);
                    var23.func_151380_a("uniforms[" + var19 + "]");
                    throw var23;
                }
                ++var19;
            }
        }
    }

    private void initUniform(JsonElement p_148028_1_) throws JsonException {
        JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_148028_1_, "uniform");
        String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        ShaderUniform var4 = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().func_147991_a(var3);
        if (var4 == null) {
            throw new JsonException("Uniform '" + var3 + "' does not exist");
        }
        float[] var5 = new float[4];
        int var6 = 0;
        JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
        for (JsonElement var9 : var7) {
            try {
                var5[var6] = JsonUtils.getJsonElementFloatValue(var9, "value");
            }
            catch (Exception var12) {
                JsonException var11 = JsonException.func_151379_a(var12);
                var11.func_151380_a("values[" + var6 + "]");
                throw var11;
            }
            ++var6;
        }
        switch (var6) {
            default: {
                break;
            }
            case 1: {
                var4.func_148090_a(var5[0]);
                break;
            }
            case 2: {
                var4.func_148087_a(var5[0], var5[1]);
                break;
            }
            case 3: {
                var4.func_148095_a(var5[0], var5[1], var5[2]);
                break;
            }
            case 4: {
                var4.func_148081_a(var5[0], var5[1], var5[2], var5[3]);
            }
        }
    }

    public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
        Framebuffer var4 = new Framebuffer(p_148020_2_, p_148020_3_, true);
        var4.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mapFramebuffers.put(p_148020_1_, var4);
        if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight) {
            this.listFramebuffers.add(var4);
        }
    }

    public void deleteShaderGroup() {
        for (Framebuffer var2 : this.mapFramebuffers.values()) {
            var2.deleteFramebuffer();
        }
        for (Shader var3 : this.listShaders) {
            var3.deleteShader();
        }
        this.listShaders.clear();
    }

    public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException {
        Shader var4 = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
        this.listShaders.add(this.listShaders.size(), var4);
        return var4;
    }

    private void resetProjectionMatrix() {
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.setIdentity();
        this.projectionMatrix.m00 = 2.0f / (float)this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0f / (float)(- this.mainFramebuffer.framebufferTextureHeight);
        this.projectionMatrix.m22 = -0.0020001999f;
        this.projectionMatrix.m33 = 1.0f;
        this.projectionMatrix.m03 = -1.0f;
        this.projectionMatrix.m13 = 1.0f;
        this.projectionMatrix.m23 = -1.0001999f;
    }

    public void createBindFramebuffers(int p_148026_1_, int p_148026_2_) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        for (Shader var4 : this.listShaders) {
            var4.setProjectionMatrix(this.projectionMatrix);
        }
        for (Framebuffer var5 : this.listFramebuffers) {
            var5.createBindFramebuffer(p_148026_1_, p_148026_2_);
        }
    }

    public void loadShaderGroup(float p_148018_1_) {
        if (p_148018_1_ < this.field_148037_k) {
            this.field_148036_j += 1.0f - this.field_148037_k;
            this.field_148036_j += p_148018_1_;
        } else {
            this.field_148036_j += p_148018_1_ - this.field_148037_k;
        }
        this.field_148037_k = p_148018_1_;
        while (this.field_148036_j > 20.0f) {
            this.field_148036_j -= 20.0f;
        }
        for (Shader var3 : this.listShaders) {
            var3.loadShader(this.field_148036_j / 20.0f);
        }
    }

    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }

    private Framebuffer getFramebuffer(String p_148017_1_) {
        return p_148017_1_ == null ? null : (p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : (Framebuffer)this.mapFramebuffers.get(p_148017_1_));
    }
}

