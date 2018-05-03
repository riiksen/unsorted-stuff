/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;

public class RenderList {
    public int renderChunkX;
    public int renderChunkY;
    public int renderChunkZ;
    private double cameraX;
    private double cameraY;
    private double cameraZ;
    private IntBuffer glLists = GLAllocation.createDirectIntBuffer(65536);
    private boolean valid;
    private boolean bufferFlipped;
    private static final String __OBFID = "CL_00000957";

    public void setupRenderList(int par1, int par2, int par3, double par4, double par6, double par8) {
        this.valid = true;
        this.glLists.clear();
        this.renderChunkX = par1;
        this.renderChunkY = par2;
        this.renderChunkZ = par3;
        this.cameraX = par4;
        this.cameraY = par6;
        this.cameraZ = par8;
    }

    public boolean rendersChunk(int par1, int par2, int par3) {
        return !this.valid ? false : par1 == this.renderChunkX && par2 == this.renderChunkY && par3 == this.renderChunkZ;
    }

    public void addGLRenderList(int par1) {
        this.glLists.put(par1);
        if (this.glLists.remaining() == 0) {
            this.callLists();
        }
    }

    public void callLists() {
        if (this.valid) {
            if (!this.bufferFlipped) {
                this.glLists.flip();
                this.bufferFlipped = true;
            }
            if (this.glLists.remaining() > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)((float)((double)this.renderChunkX - this.cameraX)), (float)((float)((double)this.renderChunkY - this.cameraY)), (float)((float)((double)this.renderChunkZ - this.cameraZ)));
                GL11.glCallLists((IntBuffer)this.glLists);
                GL11.glPopMatrix();
            }
        }
    }

    public void resetList() {
        this.valid = false;
        this.bufferFlipped = false;
    }
}

