/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;

public class FoliageColorReloadListener
implements IResourceManagerReloadListener {
    private static final ResourceLocation field_130079_a = new ResourceLocation("textures/colormap/foliage.png");
    private static final String __OBFID = "CL_00001077";

    @Override
    public void onResourceManagerReload(IResourceManager par1ResourceManager) {
        try {
            ColorizerFoliage.setFoliageBiomeColorizer(TextureUtil.readImageData(par1ResourceManager, field_130079_a));
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

