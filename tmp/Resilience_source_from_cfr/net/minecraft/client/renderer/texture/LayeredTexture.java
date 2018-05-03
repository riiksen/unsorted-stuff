/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture
extends AbstractTexture {
    private static final Logger logger = LogManager.getLogger();
    public final List layeredTextureNames;
    private static final String __OBFID = "CL_00001051";

    public /* varargs */ LayeredTexture(String ... par1ArrayOfStr) {
        this.layeredTextureNames = Lists.newArrayList((Object[])par1ArrayOfStr);
    }

    @Override
    public void loadTexture(IResourceManager par1ResourceManager) throws IOException {
        this.func_147631_c();
        BufferedImage var2 = null;
        try {
            for (String var4 : this.layeredTextureNames) {
                if (var4 == null) continue;
                InputStream var5 = par1ResourceManager.getResource(new ResourceLocation(var4)).getInputStream();
                BufferedImage var6 = ImageIO.read(var5);
                if (var2 == null) {
                    var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
                }
                var2.getGraphics().drawImage(var6, 0, 0, null);
            }
        }
        catch (IOException var7) {
            logger.error("Couldn't load layered image", (Throwable)var7);
            return;
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), var2);
    }
}

