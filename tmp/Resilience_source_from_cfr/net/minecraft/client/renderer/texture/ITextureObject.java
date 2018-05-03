/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public interface ITextureObject {
    public void loadTexture(IResourceManager var1) throws IOException;

    public int getGlTextureId();
}

