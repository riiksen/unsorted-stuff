/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.shader;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class Framebuffer {
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public int framebufferWidth;
    public int framebufferHeight;
    public boolean useDepth;
    public int framebufferObject;
    public int framebufferTexture;
    public int depthBuffer;
    public float[] framebufferColor;
    public int framebufferFilter;
    private static final String __OBFID = "CL_00000959";

    public Framebuffer(int p_i45078_1_, int p_i45078_2_, boolean p_i45078_3_) {
        this.useDepth = p_i45078_3_;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        this.framebufferColor = new float[4];
        this.framebufferColor[0] = 1.0f;
        this.framebufferColor[1] = 1.0f;
        this.framebufferColor[2] = 1.0f;
        this.framebufferColor[3] = 0.0f;
        this.createBindFramebuffer(p_i45078_1_, p_i45078_2_);
    }

    public void createBindFramebuffer(int p_147613_1_, int p_147613_2_) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = p_147613_1_;
            this.framebufferHeight = p_147613_2_;
        } else {
            GL11.glEnable((int)2929);
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createFramebuffer(p_147613_1_, p_147613_2_);
            this.checkFramebufferComplete();
            EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)0);
        }
    }

    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                EXTFramebufferObject.glDeleteRenderbuffersEXT((int)this.depthBuffer);
                this.depthBuffer = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebufferObject > -1) {
                EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)0);
                EXTFramebufferObject.glDeleteFramebuffersEXT((int)this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }

    public void createFramebuffer(int p_147605_1_, int p_147605_2_) {
        this.framebufferWidth = p_147605_1_;
        this.framebufferHeight = p_147605_2_;
        this.framebufferTextureWidth = p_147605_1_;
        this.framebufferTextureHeight = p_147605_2_;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
        } else {
            this.framebufferObject = EXTFramebufferObject.glGenFramebuffersEXT();
            this.framebufferTexture = TextureUtil.glGenTextures();
            if (this.useDepth) {
                this.depthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT();
            }
            this.setFramebufferFilter(9729);
            GL11.glBindTexture((int)3553, (int)this.framebufferTexture);
            GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)this.framebufferTextureWidth, (int)this.framebufferTextureHeight, (int)0, (int)6408, (int)5121, (ByteBuffer)null);
            EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.framebufferObject);
            EXTFramebufferObject.glFramebufferTexture2DEXT((int)36160, (int)36064, (int)3553, (int)this.framebufferTexture, (int)0);
            if (this.useDepth) {
                EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)this.depthBuffer);
                EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)33190, (int)this.framebufferTextureWidth, (int)this.framebufferTextureHeight);
                EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)this.depthBuffer);
            }
            this.framebufferClear();
            this.unbindFramebufferTexture();
        }
    }

    public void setFramebufferFilter(int p_147607_1_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = p_147607_1_;
            GL11.glBindTexture((int)3553, (int)this.framebufferTexture);
            GL11.glTexParameterf((int)3553, (int)10241, (float)p_147607_1_);
            GL11.glTexParameterf((int)3553, (int)10240, (float)p_147607_1_);
            GL11.glTexParameterf((int)3553, (int)10242, (float)10496.0f);
            GL11.glTexParameterf((int)3553, (int)10243, (float)10496.0f);
            GL11.glBindTexture((int)3553, (int)0);
        }
    }

    public void checkFramebufferComplete() {
        int var1 = EXTFramebufferObject.glCheckFramebufferStatusEXT((int)36160);
        switch (var1) {
            case 36053: {
                return;
            }
            case 36054: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT");
            }
            case 36055: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT");
            }
            default: {
                throw new RuntimeException("glCheckFramebufferStatusEXT returned unknown status:" + var1);
            }
            case 36057: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT");
            }
            case 36058: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT");
            }
            case 36059: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT");
            }
            case 36060: 
        }
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT");
    }

    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture((int)3553, (int)this.framebufferTexture);
        }
    }

    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture((int)3553, (int)0);
        }
    }

    public void bindFramebuffer(boolean p_147610_1_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.framebufferObject);
            if (p_147610_1_) {
                GL11.glViewport((int)0, (int)0, (int)this.framebufferWidth, (int)this.framebufferHeight);
            }
        }
    }

    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)0);
        }
    }

    public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_) {
        this.framebufferColor[0] = p_147604_1_;
        this.framebufferColor[1] = p_147604_2_;
        this.framebufferColor[2] = p_147604_3_;
        this.framebufferColor[3] = p_147604_4_;
    }

    public void framebufferRender(int p_147615_1_, int p_147615_2_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)p_147615_1_, (double)p_147615_2_, (double)0.0, (double)1000.0, (double)3000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
            GL11.glViewport((int)0, (int)0, (int)p_147615_1_, (int)p_147615_2_);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)2903);
            this.bindFramebufferTexture();
            float var3 = p_147615_1_;
            float var4 = p_147615_2_;
            float var5 = (float)this.framebufferWidth / (float)this.framebufferTextureWidth;
            float var6 = (float)this.framebufferHeight / (float)this.framebufferTextureHeight;
            Tessellator var7 = Tessellator.instance;
            var7.startDrawingQuads();
            var7.setColorOpaque_I(-1);
            var7.addVertexWithUV(0.0, var4, 0.0, 0.0, 0.0);
            var7.addVertexWithUV(var3, var4, 0.0, var5, 0.0);
            var7.addVertexWithUV(var3, 0.0, 0.0, var5, var6);
            var7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, var6);
            var7.draw();
            this.unbindFramebufferTexture();
            GL11.glDepthMask((boolean)true);
            GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        }
    }

    public void framebufferClear() {
        this.bindFramebuffer(true);
        GL11.glClearColor((float)this.framebufferColor[0], (float)this.framebufferColor[1], (float)this.framebufferColor[2], (float)this.framebufferColor[3]);
        int var1 = 16384;
        if (this.useDepth) {
            GL11.glClearDepth((double)1.0);
            var1 |= 256;
        }
        GL11.glClear((int)var1);
        this.unbindFramebuffer();
    }
}

