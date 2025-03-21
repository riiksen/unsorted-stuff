/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Comparator;
import java.util.PriorityQueue;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.client.util.QuadComparator;
import net.minecraft.src.Config;
import net.minecraft.src.VertexData;
import org.lwjgl.opengl.GL11;

public class Tessellator {
    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private ShortBuffer shortBuffer;
    private int[] rawBuffer;
    private int vertexCount;
    private double textureU;
    private double textureV;
    private int brightness;
    private int color;
    private boolean hasColor;
    private boolean hasTexture;
    private boolean hasBrightness;
    private boolean hasNormals;
    private int rawBufferIndex;
    private int addedVertices;
    private boolean isColorDisabled;
    public int drawMode;
    public double xOffset;
    public double yOffset;
    public double zOffset;
    private int normal;
    public static Tessellator instance = new Tessellator(524288);
    public boolean isDrawing;
    private int bufferSize;
    private boolean renderingChunk = false;
    private static boolean littleEndianByteOrder = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    public static boolean renderingWorldRenderer = false;
    public boolean defaultTexture = true;
    public int textureID = 0;
    public boolean autoGrow = true;
    private VertexData[] vertexDatas = null;
    private boolean[] drawnIcons = new boolean[256];
    private TextureAtlasSprite[] vertexQuadIcons = null;
    private static final String __OBFID = "CL_00000960";

    public Tessellator() {
        this(65536);
        this.defaultTexture = false;
    }

    public Tessellator(int par1) {
        this.bufferSize = par1;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[par1];
        this.vertexDatas = new VertexData[4];
        int ix = 0;
        while (ix < this.vertexDatas.length) {
            this.vertexDatas[ix] = new VertexData();
            ++ix;
        }
    }

    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.isDrawing = false;
        if (!(this.vertexCount <= 0 || this.renderingChunk && Config.isMultiTexture())) {
            this.intBuffer.clear();
            this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
            if (this.hasTexture) {
                this.floatBuffer.position(3);
                GL11.glTexCoordPointer((int)2, (int)32, (FloatBuffer)this.floatBuffer);
                GL11.glEnableClientState((int)32888);
            }
            if (this.hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                this.shortBuffer.position(14);
                GL11.glTexCoordPointer((int)2, (int)32, (ShortBuffer)this.shortBuffer);
                GL11.glEnableClientState((int)32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (this.hasColor) {
                this.byteBuffer.position(20);
                GL11.glColorPointer((int)4, (boolean)true, (int)32, (ByteBuffer)this.byteBuffer);
                GL11.glEnableClientState((int)32886);
            }
            if (this.hasNormals) {
                this.byteBuffer.position(24);
                GL11.glNormalPointer((int)32, (ByteBuffer)this.byteBuffer);
                GL11.glEnableClientState((int)32885);
            }
            this.floatBuffer.position(0);
            GL11.glVertexPointer((int)3, (int)32, (FloatBuffer)this.floatBuffer);
            GL11.glEnableClientState((int)32884);
            GL11.glDrawArrays((int)this.drawMode, (int)0, (int)this.vertexCount);
            GL11.glDisableClientState((int)32884);
            if (this.hasTexture) {
                GL11.glDisableClientState((int)32888);
            }
            if (this.hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GL11.glDisableClientState((int)32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (this.hasColor) {
                GL11.glDisableClientState((int)32886);
            }
            if (this.hasNormals) {
                GL11.glDisableClientState((int)32885);
            }
        }
        int var1 = this.rawBufferIndex * 4;
        this.reset();
        return var1;
    }

    public TesselatorVertexState getVertexState(float p_147564_1_, float p_147564_2_, float p_147564_3_) {
        if (this.rawBufferIndex < 1) {
            return null;
        }
        int[] var4 = new int[this.rawBufferIndex];
        PriorityQueue<Integer> var5 = new PriorityQueue<Integer>(this.rawBufferIndex, new QuadComparator(this.rawBuffer, p_147564_1_ + (float)this.xOffset, p_147564_2_ + (float)this.yOffset, p_147564_3_ + (float)this.zOffset));
        int var6 = 32;
        int var7 = 0;
        while (var7 < this.rawBufferIndex) {
            var5.add(var7);
            var7 += var6;
        }
        var7 = 0;
        while (!var5.isEmpty()) {
            int var8 = (Integer)var5.remove();
            int var9 = 0;
            while (var9 < var6) {
                var4[var7 + var9] = this.rawBuffer[var8 + var9];
                ++var9;
            }
            var7 += var6;
        }
        System.arraycopy(var4, 0, this.rawBuffer, 0, var4.length);
        return new TesselatorVertexState(var4, this.rawBufferIndex, this.vertexCount, this.hasTexture, this.hasBrightness, this.hasNormals, this.hasColor);
    }

    public void setVertexState(TesselatorVertexState p_147565_1_) {
        System.arraycopy(p_147565_1_.getRawBuffer(), 0, this.rawBuffer, 0, p_147565_1_.getRawBuffer().length);
        this.rawBufferIndex = p_147565_1_.getRawBufferIndex();
        this.vertexCount = p_147565_1_.getVertexCount();
        this.hasTexture = p_147565_1_.getHasTexture();
        this.hasBrightness = p_147565_1_.getHasBrightness();
        this.hasColor = p_147565_1_.getHasColor();
        this.hasNormals = p_147565_1_.getHasNormals();
    }

    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    public void startDrawingQuads() {
        this.startDrawing(7);
    }

    public void startDrawing(int par1) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = par1;
        this.hasNormals = false;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.isColorDisabled = false;
    }

    public void setTextureUV(double par1, double par3) {
        this.hasTexture = true;
        this.textureU = par1;
        this.textureV = par3;
    }

    public void setBrightness(int par1) {
        this.hasBrightness = true;
        this.brightness = par1;
    }

    public void setColorOpaque_F(float par1, float par2, float par3) {
        this.setColorOpaque((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f));
    }

    public void setColorRGBA_F(float par1, float par2, float par3, float par4) {
        this.setColorRGBA((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f), (int)(par4 * 255.0f));
    }

    public void setColorOpaque(int par1, int par2, int par3) {
        this.setColorRGBA(par1, par2, par3, 255);
    }

    public void setColorRGBA(int par1, int par2, int par3, int par4) {
        if (!this.isColorDisabled) {
            if (par1 > 255) {
                par1 = 255;
            }
            if (par2 > 255) {
                par2 = 255;
            }
            if (par3 > 255) {
                par3 = 255;
            }
            if (par4 > 255) {
                par4 = 255;
            }
            if (par1 < 0) {
                par1 = 0;
            }
            if (par2 < 0) {
                par2 = 0;
            }
            if (par3 < 0) {
                par3 = 0;
            }
            if (par4 < 0) {
                par4 = 0;
            }
            this.hasColor = true;
            this.color = littleEndianByteOrder ? par4 << 24 | par3 << 16 | par2 << 8 | par1 : par1 << 24 | par2 << 16 | par3 << 8 | par4;
        }
    }

    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        this.setTextureUV(u, v);
        this.addVertex(x, y, z);
    }

    public void addVertex(double par1, double par3, double par5) {
        if (this.autoGrow && this.rawBufferIndex >= this.bufferSize - 32) {
            Config.dbg("Expand tessellator buffer, old: " + this.bufferSize + ", new: " + this.bufferSize * 2);
            this.bufferSize *= 2;
            int[] newRawBuffer = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBuffer.length);
            this.rawBuffer = newRawBuffer;
            this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
            if (this.vertexQuadIcons != null) {
                TextureAtlasSprite[] newVertexQuadIcons = new TextureAtlasSprite[this.bufferSize / 4];
                System.arraycopy(this.vertexQuadIcons, 0, newVertexQuadIcons, 0, this.vertexQuadIcons.length);
                this.vertexQuadIcons = newVertexQuadIcons;
            }
        }
        ++this.addedVertices;
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }
        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }
        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }
        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }
        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(par1 + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(par3 + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(par5 + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (!this.autoGrow && this.addedVertices % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
            this.draw();
            this.isDrawing = true;
        }
    }

    public void setColorOpaque_I(int par1) {
        int var2 = par1 >> 16 & 255;
        int var3 = par1 >> 8 & 255;
        int var4 = par1 & 255;
        this.setColorOpaque(var2, var3, var4);
    }

    public void setColorRGBA_I(int par1, int par2) {
        int var3 = par1 >> 16 & 255;
        int var4 = par1 >> 8 & 255;
        int var5 = par1 & 255;
        this.setColorRGBA(var3, var4, var5, par2);
    }

    public void disableColor() {
        this.isColorDisabled = true;
    }

    public void setNormal(float par1, float par2, float par3) {
        this.hasNormals = true;
        byte var4 = (byte)(par1 * 127.0f);
        byte var5 = (byte)(par2 * 127.0f);
        byte var6 = (byte)(par3 * 127.0f);
        this.normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
    }

    public void setTranslation(double par1, double par3, double par5) {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }

    public void addTranslation(float par1, float par2, float par3) {
        this.xOffset += (double)par1;
        this.yOffset += (double)par2;
        this.zOffset += (double)par3;
    }

    public boolean isRenderingChunk() {
        return this.renderingChunk;
    }

    public void setRenderingChunk(boolean renderingChunk) {
        this.renderingChunk = renderingChunk;
    }

    private void draw(int startQuadVertex, int endQuadVertex) {
        int vxQuadCount = endQuadVertex - startQuadVertex;
        if (vxQuadCount > 0) {
            int startVertex = startQuadVertex * 4;
            int vxCount = vxQuadCount * 4;
            this.floatBuffer.position(3);
            GL11.glTexCoordPointer((int)2, (int)32, (FloatBuffer)this.floatBuffer);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            this.shortBuffer.position(14);
            GL11.glTexCoordPointer((int)2, (int)32, (ShortBuffer)this.shortBuffer);
            GL11.glEnableClientState((int)32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            this.byteBuffer.position(20);
            GL11.glColorPointer((int)4, (boolean)true, (int)32, (ByteBuffer)this.byteBuffer);
            this.floatBuffer.position(0);
            GL11.glVertexPointer((int)3, (int)32, (FloatBuffer)this.floatBuffer);
            GL11.glDrawArrays((int)this.drawMode, (int)startVertex, (int)vxCount);
        }
    }

    private int drawForIcon(TextureAtlasSprite icon, int startQuadPos) {
        icon.bindOwnTexture();
        int firstRegionEnd = -1;
        int lastPos = -1;
        int numQuads = this.addedVertices / 4;
        int i = startQuadPos;
        while (i < numQuads) {
            TextureAtlasSprite ts = this.vertexQuadIcons[i];
            if (ts == icon) {
                if (lastPos < 0) {
                    lastPos = i;
                }
            } else if (lastPos >= 0) {
                this.draw(lastPos, i);
                lastPos = -1;
                if (firstRegionEnd < 0) {
                    firstRegionEnd = i;
                }
            }
            ++i;
        }
        if (lastPos >= 0) {
            this.draw(lastPos, numQuads);
        }
        if (firstRegionEnd < 0) {
            firstRegionEnd = numQuads;
        }
        return firstRegionEnd;
    }
}

