/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.ResourceUtils;
import net.minecraft.src.TextureAnimation;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.ResourceLocation;

public class TextureAnimations {
    private static TextureAnimation[] textureAnimations = null;

    public static void reset() {
        textureAnimations = null;
    }

    public static void update() {
        IResourcePack[] rps = Config.getResourcePacks();
        textureAnimations = TextureAnimations.getTextureAnimations(rps);
        TextureAnimations.updateAnimations();
    }

    public static void updateCustomAnimations() {
        if (textureAnimations != null && Config.isAnimatedTextures()) {
            TextureAnimations.updateAnimations();
        }
    }

    public static void updateAnimations() {
        if (textureAnimations != null) {
            int i = 0;
            while (i < textureAnimations.length) {
                TextureAnimation anim = textureAnimations[i];
                anim.updateTexture();
                ++i;
            }
        }
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack[] rps) {
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        int anims = 0;
        while (anims < rps.length) {
            IResourcePack rp = rps[anims];
            TextureAnimation[] tas = TextureAnimations.getTextureAnimations(rp);
            if (tas != null) {
                list.addAll(Arrays.asList(tas));
            }
            ++anims;
        }
        TextureAnimation[] var5 = list.toArray(new TextureAnimation[list.size()]);
        return var5;
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack rp) {
        if (!(rp instanceof AbstractResourcePack)) {
            return null;
        }
        AbstractResourcePack arp = (AbstractResourcePack)rp;
        File tpFile = ResourceUtils.getResourcePackFile(arp);
        if (tpFile == null) {
            return null;
        }
        if (!tpFile.exists()) {
            return null;
        }
        String[] animPropNames = null;
        animPropNames = tpFile.isFile() ? TextureAnimations.getAnimationPropertiesZip(tpFile) : TextureAnimations.getAnimationPropertiesDir(tpFile);
        if (animPropNames == null) {
            return null;
        }
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        int anims = 0;
        while (anims < animPropNames.length) {
            String propName = animPropNames[anims];
            Config.dbg("Texture animation: " + propName);
            try {
                ResourceLocation e = new ResourceLocation(propName);
                InputStream in = rp.getInputStream(e);
                Properties props = new Properties();
                props.load(in);
                TextureAnimation anim = TextureAnimations.makeTextureAnimation(props, e);
                if (anim != null) {
                    ResourceLocation locDstTex = new ResourceLocation(anim.getDstTex());
                    if (Config.getDefiningResourcePack(locDstTex) != rp) {
                        Config.dbg("Skipped: " + propName + ", target texture not loaded from same resource pack");
                    } else {
                        list.add(anim);
                    }
                }
            }
            catch (FileNotFoundException var12) {
                Config.warn("File not found: " + var12.getMessage());
            }
            catch (IOException var13) {
                var13.printStackTrace();
            }
            ++anims;
        }
        TextureAnimation[] var14 = list.toArray(new TextureAnimation[list.size()]);
        return var14;
    }

    public static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc) {
        String texFrom = props.getProperty("from");
        String texTo = props.getProperty("to");
        int x = Config.parseInt(props.getProperty("x"), -1);
        int y = Config.parseInt(props.getProperty("y"), -1);
        int width = Config.parseInt(props.getProperty("w"), -1);
        int height = Config.parseInt(props.getProperty("h"), -1);
        if (texFrom != null && texTo != null) {
            if (x >= 0 && y >= 0 && width >= 0 && height >= 0) {
                String basePath = TextureUtils.getBasePath(propLoc.getResourcePath());
                texFrom = TextureUtils.fixResourcePath(texFrom, basePath);
                texTo = TextureUtils.fixResourcePath(texTo, basePath);
                byte[] imageBytes = TextureAnimations.getCustomTextureData(texFrom, width);
                if (imageBytes == null) {
                    Config.warn("TextureAnimation: Source texture not found: " + texTo);
                    return null;
                }
                ResourceLocation locTexTo = new ResourceLocation(texTo);
                if (!Config.hasResource(locTexTo)) {
                    Config.warn("TextureAnimation: Target texture not found: " + texTo);
                    return null;
                }
                ITextureObject destTex = TextureUtils.getTexture(locTexTo);
                if (destTex == null) {
                    Config.warn("TextureAnimation: Target texture not found: " + locTexTo);
                    return null;
                }
                int destTexId = destTex.getGlTextureId();
                TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, destTexId, x, y, width, height, props, 1);
                return anim;
            }
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        Config.warn("TextureAnimation: Source or target texture not specified");
        return null;
    }

    public static String[] getAnimationPropertiesDir(File tpDir) {
        File dirAnim = new File(tpDir, "anim");
        if (!dirAnim.exists()) {
            return null;
        }
        if (!dirAnim.isDirectory()) {
            return null;
        }
        File[] propFiles = dirAnim.listFiles();
        if (propFiles == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        int props = 0;
        while (props < propFiles.length) {
            File file = propFiles[props];
            String name = file.getName();
            if (!name.startsWith("custom_") && name.endsWith(".properties") && file.isFile() && file.canRead()) {
                Config.dbg("TextureAnimation: anim/" + file.getName());
                list.add("/anim/" + name);
            }
            ++props;
        }
        String[] var7 = list.toArray(new String[list.size()]);
        return var7;
    }

    public static String[] getAnimationPropertiesZip(File tpFile) {
        try {
            ZipFile e = new ZipFile(tpFile);
            Enumeration<? extends ZipEntry> en = e.entries();
            ArrayList<String> list = new ArrayList<String>();
            while (en.hasMoreElements()) {
                ZipEntry props = en.nextElement();
                String name = props.getName();
                if (!name.startsWith("assets/minecraft/mcpatcher/anim/") || name.startsWith("assets/minecraft/mcpatcher/anim/custom_") || !name.endsWith(".properties")) continue;
                String assetsMcStr = "assets/minecraft/";
                name = name.substring(assetsMcStr.length());
                list.add(name);
            }
            String[] props1 = list.toArray(new String[list.size()]);
            e.close();
            return props1;
        }
        catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static byte[] getCustomTextureData(String imagePath, int tileWidth) {
        byte[] imageBytes = TextureAnimations.loadImage(imagePath, tileWidth);
        if (imageBytes == null) {
            imageBytes = TextureAnimations.loadImage("/anim" + imagePath, tileWidth);
        }
        return imageBytes;
    }

    private static byte[] loadImage(String name, int targetWidth) {
        BufferedImage image;
        GameSettings options;
        block9 : {
            InputStream in;
            block8 : {
                options = Config.getGameSettings();
                ResourceLocation e = new ResourceLocation(name);
                in = Config.getResourceStream(e);
                if (in != null) break block8;
                return null;
            }
            image = TextureAnimations.readTextureImage(in);
            if (image != null) break block9;
            return null;
        }
        try {
            if (targetWidth > 0 && image.getWidth() != targetWidth) {
                double width = image.getHeight() / image.getWidth();
                int ai = (int)((double)targetWidth * width);
                image = TextureAnimations.scaleBufferedImage(image, targetWidth, ai);
            }
            int var20 = image.getWidth();
            int height = image.getHeight();
            int[] var21 = new int[var20 * height];
            byte[] byteBuf = new byte[var20 * height * 4];
            image.getRGB(0, 0, var20, height, var21, 0, var20);
            int l = 0;
            while (l < var21.length) {
                int alpha = var21[l] >> 24 & 255;
                int red = var21[l] >> 16 & 255;
                int green = var21[l] >> 8 & 255;
                int blue = var21[l] & 255;
                if (options != null && options.anaglyph) {
                    int j3 = (red * 30 + green * 59 + blue * 11) / 100;
                    int l3 = (red * 30 + green * 70) / 100;
                    int j4 = (red * 30 + blue * 70) / 100;
                    red = j3;
                    green = l3;
                    blue = j4;
                }
                byteBuf[l * 4 + 0] = (byte)red;
                byteBuf[l * 4 + 1] = (byte)green;
                byteBuf[l * 4 + 2] = (byte)blue;
                byteBuf[l * 4 + 3] = (byte)alpha;
                ++l;
            }
            return byteBuf;
        }
        catch (FileNotFoundException var18) {
            return null;
        }
        catch (Exception var19) {
            var19.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, 2);
        Graphics2D gr = scaledImage.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr.drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }
}

