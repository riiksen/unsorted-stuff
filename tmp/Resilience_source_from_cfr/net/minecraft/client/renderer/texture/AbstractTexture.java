/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;

public abstract class AbstractTexture
implements ITextureObject {
    protected int glTextureId = -1;
    private static final String __OBFID = "CL_00001047";

    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }

    public void func_147631_c() {
        if (this.glTextureId != -1) {
            TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -1;
        }
    }
}

