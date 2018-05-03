/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.vecmath.Matrix4f
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.util.List;
import javax.vecmath.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonException;
import org.lwjgl.opengl.GL11;

public class Shader {
    private final ShaderManager manager;
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List listAuxFramebuffers = Lists.newArrayList();
    private final List listAuxNames = Lists.newArrayList();
    private final List listAuxWidths = Lists.newArrayList();
    private final List listAuxHeights = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    private static final String __OBFID = "CL_00001042";

    public Shader(IResourceManager p_i45089_1_, String p_i45089_2_, Framebuffer p_i45089_3_, Framebuffer p_i45089_4_) throws JsonException {
        this.manager = new ShaderManager(p_i45089_1_, p_i45089_2_);
        this.framebufferIn = p_i45089_3_;
        this.framebufferOut = p_i45089_4_;
    }

    public void deleteShader() {
        this.manager.func_147988_a();
    }

    public void addAuxFramebuffer(String p_148041_1_, Object p_148041_2_, int p_148041_3_, int p_148041_4_) {
        this.listAuxNames.add(this.listAuxNames.size(), p_148041_1_);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), p_148041_2_);
        this.listAuxWidths.add(this.listAuxWidths.size(), p_148041_3_);
        this.listAuxHeights.add(this.listAuxHeights.size(), p_148041_4_);
    }

    private void preLoadShader() {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2912);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2903);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)0);
    }

    public void setProjectionMatrix(Matrix4f p_148045_1_) {
        this.projectionMatrix = p_148045_1_;
    }

    public void loadShader(float p_148042_1_) {
        this.preLoadShader();
        this.framebufferIn.unbindFramebuffer();
        float var2 = this.framebufferOut.framebufferTextureWidth;
        float var3 = this.framebufferOut.framebufferTextureHeight;
        GL11.glViewport((int)0, (int)0, (int)((int)var2), (int)((int)var3));
        this.manager.func_147992_a("DiffuseSampler", this.framebufferIn);
        int var4 = 0;
        while (var4 < this.listAuxFramebuffers.size()) {
            this.manager.func_147992_a((String)this.listAuxNames.get(var4), this.listAuxFramebuffers.get(var4));
            this.manager.func_147984_b("AuxSize" + var4).func_148087_a(((Integer)this.listAuxWidths.get(var4)).intValue(), ((Integer)this.listAuxHeights.get(var4)).intValue());
            ++var4;
        }
        this.manager.func_147984_b("ProjMat").func_148088_a(this.projectionMatrix);
        this.manager.func_147984_b("InSize").func_148087_a(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.func_147984_b("OutSize").func_148087_a(var2, var3);
        this.manager.func_147984_b("Time").func_148090_a(p_148042_1_);
        Minecraft var8 = Minecraft.getMinecraft();
        this.manager.func_147984_b("ScreenSize").func_148087_a(var8.displayWidth, var8.displayHeight);
        this.manager.func_147995_c();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer(false);
        GL11.glDepthMask((boolean)false);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
        Tessellator var5 = Tessellator.instance;
        var5.startDrawingQuads();
        var5.setColorOpaque_I(-1);
        var5.addVertex(0.0, var3, 500.0);
        var5.addVertex(var2, var3, 500.0);
        var5.addVertex(var2, 0.0, 500.0);
        var5.addVertex(0.0, 0.0, 500.0);
        var5.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        this.manager.func_147993_b();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        for (Object var7 : this.listAuxFramebuffers) {
            if (!(var7 instanceof Framebuffer)) continue;
            ((Framebuffer)var7).unbindFramebufferTexture();
        }
    }

    public ShaderManager getShaderManager() {
        return this.manager;
    }
}

