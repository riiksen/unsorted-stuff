/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.ArabicShapingException
 *  com.ibm.icu.text.Bidi
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FontRenderer
implements IResourceManagerReloadListener {
    private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
    private float[] charWidth = new float[256];
    public int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();
    private byte[] glyphWidth = new byte[65536];
    private int[] colorCode = new int[32];
    private ResourceLocation locationFontTexture;
    private final TextureManager renderEngine;
    private float posX;
    private float posY;
    private boolean unicodeFlag;
    private boolean bidiFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    public GameSettings gameSettings;
    public ResourceLocation locationFontTextureBase;
    public boolean enabled = true;
    public float scaleFactor = 1.0f;
    private static final String __OBFID = "CL_00000660";

    public FontRenderer(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4) {
        this.gameSettings = par1GameSettings;
        this.locationFontTextureBase = par2ResourceLocation;
        this.locationFontTexture = par2ResourceLocation;
        this.renderEngine = par3TextureManager;
        this.unicodeFlag = par4;
        this.locationFontTexture = FontRenderer.getHdFontLocation(this.locationFontTextureBase);
        par3TextureManager.bindTexture(this.locationFontTexture);
        int var5 = 0;
        while (var5 < 32) {
            int var6 = (var5 >> 3 & 1) * 85;
            int var7 = (var5 >> 2 & 1) * 170 + var6;
            int var8 = (var5 >> 1 & 1) * 170 + var6;
            int var9 = (var5 >> 0 & 1) * 170 + var6;
            if (var5 == 6) {
                var7 += 85;
            }
            if (par1GameSettings.anaglyph) {
                int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                int var11 = (var7 * 30 + var8 * 70) / 100;
                int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }
            this.colorCode[var5] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
            ++var5;
        }
        this.readGlyphSizes();
    }

    @Override
    public void onResourceManagerReload(IResourceManager par1ResourceManager) {
        this.locationFontTexture = FontRenderer.getHdFontLocation(this.locationFontTextureBase);
        int i = 0;
        while (i < unicodePageLocations.length) {
            FontRenderer.unicodePageLocations[i] = null;
            ++i;
        }
        this.readFontTexture();
    }

    private void readFontTexture() {
        float kx;
        BufferedImage bufferedimage;
        try {
            bufferedimage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
        }
        catch (IOException var18) {
            throw new RuntimeException(var18);
        }
        int imgWidth = bufferedimage.getWidth();
        int imgHeight = bufferedimage.getHeight();
        int charW = imgWidth / 16;
        int charH = imgHeight / 16;
        this.scaleFactor = kx = (float)imgWidth / 128.0f;
        int[] ai = new int[imgWidth * imgHeight];
        bufferedimage.getRGB(0, 0, imgWidth, imgHeight, ai, 0, imgWidth);
        int k = 0;
        while (k < 256) {
            int var19;
            int cx = k % 16;
            int cy = k / 16;
            boolean px = false;
            for (var19 = charW - 1; var19 >= 0; --var19) {
                int x = cx * charW + var19;
                boolean flag = true;
                int py = 0;
                while (py < charH && flag) {
                    int ypos = (cy * charH + py) * imgWidth;
                    int col = ai[x + ypos];
                    int al = col >> 24 & 255;
                    if (al > 16) {
                        flag = false;
                    }
                    ++py;
                }
                if (!flag) break;
            }
            if (k == 32) {
                var19 = charW <= 8 ? (int)(2.0f * kx) : (int)(1.5f * kx);
            }
            this.charWidth[k] = (float)(var19 + 1) / kx + 1.0f;
            ++k;
        }
        this.readCustomCharWidths();
    }

    private void readGlyphSizes() {
        try {
            InputStream var2 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
            var2.read(this.glyphWidth);
        }
        catch (IOException var21) {
            throw new RuntimeException(var21);
        }
    }

    private float renderCharAtPos(int par1, char par2, boolean par3) {
        return par2 == ' ' ? this.charWidth[par2] : (par2 == ' ' ? 4.0f : ("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(par2) != -1 && !this.unicodeFlag ? this.renderDefaultChar(par1, par3) : this.renderUnicodeChar(par2, par3)));
    }

    private float renderDefaultChar(int par1, boolean par2) {
        float var3 = par1 % 16 * 8;
        float var4 = par1 / 16 * 8;
        float var5 = par2 ? 1.0f : 0.0f;
        this.renderEngine.bindTexture(this.locationFontTexture);
        float var6 = 7.99f;
        GL11.glBegin((int)5);
        GL11.glTexCoord2f((float)(var3 / 128.0f), (float)(var4 / 128.0f));
        GL11.glVertex3f((float)(this.posX + var5), (float)this.posY, (float)0.0f);
        GL11.glTexCoord2f((float)(var3 / 128.0f), (float)((var4 + 7.99f) / 128.0f));
        GL11.glVertex3f((float)(this.posX - var5), (float)(this.posY + 7.99f), (float)0.0f);
        GL11.glTexCoord2f((float)((var3 + var6 - 1.0f) / 128.0f), (float)(var4 / 128.0f));
        GL11.glVertex3f((float)(this.posX + var6 - 1.0f + var5), (float)this.posY, (float)0.0f);
        GL11.glTexCoord2f((float)((var3 + var6 - 1.0f) / 128.0f), (float)((var4 + 7.99f) / 128.0f));
        GL11.glVertex3f((float)(this.posX + var6 - 1.0f - var5), (float)(this.posY + 7.99f), (float)0.0f);
        GL11.glEnd();
        return this.charWidth[par1];
    }

    private ResourceLocation getUnicodePageLocation(int par1) {
        if (unicodePageLocations[par1] == null) {
            FontRenderer.unicodePageLocations[par1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", par1));
            FontRenderer.unicodePageLocations[par1] = FontRenderer.getHdFontLocation(unicodePageLocations[par1]);
        }
        return unicodePageLocations[par1];
    }

    private void loadGlyphTexture(int par1) {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(par1));
    }

    private float renderUnicodeChar(char par1, boolean par2) {
        if (this.glyphWidth[par1] == 0) {
            return 0.0f;
        }
        int var3 = par1 / 256;
        this.loadGlyphTexture(var3);
        int var4 = this.glyphWidth[par1] >>> 4;
        int var5 = this.glyphWidth[par1] & 15;
        float var6 = var4;
        float var7 = var5 + 1;
        float var8 = (float)(par1 % 16 * 16) + var6;
        float var9 = (par1 & 255) / 16 * 16;
        float var10 = var7 - var6 - 0.02f;
        float var11 = par2 ? 1.0f : 0.0f;
        GL11.glBegin((int)5);
        GL11.glTexCoord2f((float)(var8 / 256.0f), (float)(var9 / 256.0f));
        GL11.glVertex3f((float)(this.posX + var11), (float)this.posY, (float)0.0f);
        GL11.glTexCoord2f((float)(var8 / 256.0f), (float)((var9 + 15.98f) / 256.0f));
        GL11.glVertex3f((float)(this.posX - var11), (float)(this.posY + 7.99f), (float)0.0f);
        GL11.glTexCoord2f((float)((var8 + var10) / 256.0f), (float)(var9 / 256.0f));
        GL11.glVertex3f((float)(this.posX + var10 / 2.0f + var11), (float)this.posY, (float)0.0f);
        GL11.glTexCoord2f((float)((var8 + var10) / 256.0f), (float)((var9 + 15.98f) / 256.0f));
        GL11.glVertex3f((float)(this.posX + var10 / 2.0f - var11), (float)(this.posY + 7.99f), (float)0.0f);
        GL11.glEnd();
        return (var7 - var6) / 2.0f + 1.0f;
    }

    public int drawStringWithShadow(String par1Str, float par2, float par3, int par4) {
        return this.drawString(par1Str, par2, par3, par4, true);
    }

    public int drawString(String par1Str, float par2, float par3, int par4) {
        return !this.enabled ? 0 : this.drawString(par1Str, par2, par3, par4, false);
    }

    public int drawString(String par1Str, float par2, float par3, int par4, boolean par5) {
        int var6;
        GL11.glEnable((int)3008);
        this.resetStyles();
        if (par5) {
            var6 = this.renderString(par1Str, par2 + 1.0f, par3 + 1.0f, par4, true);
            var6 = Math.max(var6, this.renderString(par1Str, par2, par3, par4, false));
        } else {
            var6 = this.renderString(par1Str, par2, par3, par4, false);
        }
        return var6;
    }

    private String func_147647_b(String p_147647_1_) {
        try {
            Bidi var3 = new Bidi(new ArabicShaping(8).shape(p_147647_1_), 127);
            var3.setReorderingMode(0);
            return var3.writeReordered(2);
        }
        catch (ArabicShapingException var31) {
            return p_147647_1_;
        }
    }

    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    private void renderStringAtPos(String par1Str, boolean par2) {
        int var3 = 0;
        while (var3 < par1Str.length()) {
            int var5;
            int var6;
            char var4 = par1Str.charAt(var3);
            if (var4 == '\u00a7' && var3 + 1 < par1Str.length()) {
                var5 = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(var3 + 1));
                if (var5 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (var5 < 0 || var5 > 15) {
                        var5 = 15;
                    }
                    if (par2) {
                        var5 += 16;
                    }
                    this.textColor = var6 = this.colorCode[var5];
                    GL11.glColor4f((float)((float)(var6 >> 16) / 255.0f), (float)((float)(var6 >> 8 & 255) / 255.0f), (float)((float)(var6 & 255) / 255.0f), (float)this.alpha);
                } else if (var5 == 16) {
                    this.randomStyle = true;
                } else if (var5 == 17) {
                    this.boldStyle = true;
                } else if (var5 == 18) {
                    this.strikethroughStyle = true;
                } else if (var5 == 19) {
                    this.underlineStyle = true;
                } else if (var5 == 20) {
                    this.italicStyle = true;
                } else if (var5 == 21) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f((float)this.red, (float)this.blue, (float)this.green, (float)this.alpha);
                }
                ++var3;
            } else {
                boolean var7;
                Tessellator var9;
                var5 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(var4);
                if (this.randomStyle && var5 != -1) {
                    while ((int)this.charWidth[var5] != (int)this.charWidth[var6 = this.fontRandom.nextInt(this.charWidth.length)]) {
                    }
                    var5 = var6;
                }
                float var11 = this.unicodeFlag ? 0.5f : 1.0f / this.scaleFactor;
                boolean bl = var7 = (var4 == '\u0000' || var5 == -1 || this.unicodeFlag) && par2;
                if (var7) {
                    this.posX -= var11;
                    this.posY -= var11;
                }
                float var8 = this.renderCharAtPos(var5, var4, this.italicStyle);
                if (var7) {
                    this.posX += var11;
                    this.posY += var11;
                }
                if (this.boldStyle) {
                    this.posX += var11;
                    if (var7) {
                        this.posX -= var11;
                        this.posY -= var11;
                    }
                    this.renderCharAtPos(var5, var4, this.italicStyle);
                    this.posX -= var11;
                    if (var7) {
                        this.posX += var11;
                        this.posY += var11;
                    }
                    var8 += var11;
                }
                if (this.strikethroughStyle) {
                    var9 = Tessellator.instance;
                    GL11.glDisable((int)3553);
                    var9.startDrawingQuads();
                    var9.addVertex(this.posX, this.posY + (float)(this.FONT_HEIGHT / 2), 0.0);
                    var9.addVertex(this.posX + var8, this.posY + (float)(this.FONT_HEIGHT / 2), 0.0);
                    var9.addVertex(this.posX + var8, this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0f, 0.0);
                    var9.addVertex(this.posX, this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0f, 0.0);
                    var9.draw();
                    GL11.glEnable((int)3553);
                }
                if (this.underlineStyle) {
                    var9 = Tessellator.instance;
                    GL11.glDisable((int)3553);
                    var9.startDrawingQuads();
                    int var10 = this.underlineStyle ? -1 : 0;
                    var9.addVertex(this.posX + (float)var10, this.posY + (float)this.FONT_HEIGHT, 0.0);
                    var9.addVertex(this.posX + var8, this.posY + (float)this.FONT_HEIGHT, 0.0);
                    var9.addVertex(this.posX + var8, this.posY + (float)this.FONT_HEIGHT - 1.0f, 0.0);
                    var9.addVertex(this.posX + (float)var10, this.posY + (float)this.FONT_HEIGHT - 1.0f, 0.0);
                    var9.draw();
                    GL11.glEnable((int)3553);
                }
                this.posX += var8;
            }
            ++var3;
        }
    }

    private int renderStringAligned(String par1Str, int par2, int par3, int par4, int par5, boolean par6) {
        if (this.bidiFlag) {
            int var7 = this.getStringWidth(this.func_147647_b(par1Str));
            par2 = par2 + par4 - var7;
        }
        return this.renderString(par1Str, par2, par3, par5, par6);
    }

    private int renderString(String par1Str, float par2, float par3, int par4, boolean par5) {
        if (par1Str == null) {
            return 0;
        }
        if (this.bidiFlag) {
            par1Str = this.func_147647_b(par1Str);
        }
        if ((par4 & -67108864) == 0) {
            par4 |= -16777216;
        }
        if (par5) {
            par4 = (par4 & 16579836) >> 2 | par4 & -16777216;
        }
        this.red = (float)(par4 >> 16 & 255) / 255.0f;
        this.blue = (float)(par4 >> 8 & 255) / 255.0f;
        this.green = (float)(par4 & 255) / 255.0f;
        this.alpha = (float)(par4 >> 24 & 255) / 255.0f;
        GL11.glColor4f((float)this.red, (float)this.blue, (float)this.green, (float)this.alpha);
        this.posX = par2;
        this.posY = par3;
        this.renderStringAtPos(par1Str, par5);
        return (int)this.posX;
    }

    public int getStringWidth(String par1Str) {
        if (par1Str == null) {
            return 0;
        }
        float var2 = 0.0f;
        boolean var3 = false;
        int var4 = 0;
        while (var4 < par1Str.length()) {
            char var5 = par1Str.charAt(var4);
            float var6 = this.getCharWidthFloat(var5);
            if (var6 < 0.0f && var4 < par1Str.length() - 1) {
                if ((var5 = par1Str.charAt(++var4)) != 'l' && var5 != 'L') {
                    if (var5 == 'r' || var5 == 'R') {
                        var3 = false;
                    }
                } else {
                    var3 = true;
                }
                var6 = 0.0f;
            }
            var2 += var6;
            if (var3) {
                var2 += 1.0f;
            }
            ++var4;
        }
        return (int)var2;
    }

    public int getCharWidth(char par1) {
        return Math.round(this.getCharWidthFloat(par1));
    }

    private float getCharWidthFloat(char par1) {
        if (par1 == '\u00a7') {
            return -1.0f;
        }
        if (par1 == ' ') {
            return this.charWidth[32];
        }
        int var2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(par1);
        if (par1 > '\u0000' && var2 != -1 && !this.unicodeFlag) {
            return this.charWidth[var2];
        }
        if (this.glyphWidth[par1] != 0) {
            int var3 = this.glyphWidth[par1] >>> 4;
            int var4 = this.glyphWidth[par1] & 15;
            if (var4 > 7) {
                var4 = 15;
                var3 = 0;
            }
            return (++var4 - var3) / 2 + 1;
        }
        return 0.0f;
    }

    public String trimStringToWidth(String par1Str, int par2) {
        return this.trimStringToWidth(par1Str, par2, false);
    }

    public String trimStringToWidth(String par1Str, int par2, boolean par3) {
        StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        int var6 = par3 ? par1Str.length() - 1 : 0;
        int var7 = par3 ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        int var10 = var6;
        while (var10 >= 0 && var10 < par1Str.length() && var5 < (float)par2) {
            char var11 = par1Str.charAt(var10);
            float var12 = this.getCharWidthFloat(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0f) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9) {
                    var5 += 1.0f;
                }
            }
            if (var5 > (float)par2) break;
            if (par3) {
                var4.insert(0, var11);
            } else {
                var4.append(var11);
            }
            var10 += var7;
        }
        return var4.toString();
    }

    private String trimStringNewline(String par1Str) {
        while (par1Str != null && par1Str.endsWith("\n")) {
            par1Str = par1Str.substring(0, par1Str.length() - 1);
        }
        return par1Str;
    }

    public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5) {
        this.resetStyles();
        this.textColor = par5;
        par1Str = this.trimStringNewline(par1Str);
        this.renderSplitString(par1Str, par2, par3, par4, false);
    }

    private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5) {
        List var6 = this.listFormattedStringToWidth(par1Str, par4);
        for (String var8 : var6) {
            this.renderStringAligned(var8, par2, par3, par4, this.textColor, par5);
            par3 += this.FONT_HEIGHT;
        }
    }

    public int splitStringWidth(String par1Str, int par2) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(par1Str, par2).size();
    }

    public void setUnicodeFlag(boolean par1) {
        this.unicodeFlag = par1;
    }

    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }

    public void setBidiFlag(boolean par1) {
        this.bidiFlag = par1;
    }

    public List listFormattedStringToWidth(String par1Str, int par2) {
        return Arrays.asList(this.wrapFormattedStringToWidth(par1Str, par2).split("\n"));
    }

    String wrapFormattedStringToWidth(String par1Str, int par2) {
        int var3 = this.sizeStringToWidth(par1Str, par2);
        if (par1Str.length() <= var3) {
            return par1Str;
        }
        String var4 = par1Str.substring(0, var3);
        char var5 = par1Str.charAt(var3);
        boolean var6 = var5 == ' ' || var5 == '\n';
        String var7 = String.valueOf(FontRenderer.getFormatFromString(var4)) + par1Str.substring(var3 + (var6 ? 1 : 0));
        return String.valueOf(var4) + "\n" + this.wrapFormattedStringToWidth(var7, par2);
    }

    private int sizeStringToWidth(String par1Str, int par2) {
        int var3 = par1Str.length();
        float var4 = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            char var8 = par1Str.charAt(var5);
            switch (var8) {
                case '\n': {
                    --var5;
                    break;
                }
                case '\u00a7': {
                    char var9;
                    if (var5 >= var3 - 1) break;
                    if ((var9 = par1Str.charAt(++var5)) != 'l' && var9 != 'L') {
                        if (var9 != 'r' && var9 != 'R' && !FontRenderer.isFormatColor(var9)) break;
                        var7 = false;
                        break;
                    }
                    var7 = true;
                    break;
                }
                case ' ': {
                    var6 = var5;
                }
                default: {
                    var4 += this.getCharWidthFloat(var8);
                    if (!var7) break;
                    var4 += 1.0f;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > (float)par2) break;
            ++var5;
        }
        return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
    }

    private static boolean isFormatColor(char par0) {
        if (!(par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F')) {
            return false;
        }
        return true;
    }

    private static boolean isFormatSpecial(char par0) {
        if (!(par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R')) {
            return false;
        }
        return true;
    }

    private static String getFormatFromString(String par0Str) {
        String var1 = "";
        int var2 = -1;
        int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 >= var3 - 1) continue;
            char var4 = par0Str.charAt(var2 + 1);
            if (FontRenderer.isFormatColor(var4)) {
                var1 = "\u00a7" + var4;
                continue;
            }
            if (!FontRenderer.isFormatSpecial(var4)) continue;
            var1 = String.valueOf(var1) + "\u00a7" + var4;
        }
        return var1;
    }

    public boolean getBidiFlag() {
        return this.bidiFlag;
    }

    private void readCustomCharWidths() {
        String suffix;
        String fontFileName = this.locationFontTexture.getResourcePath();
        if (fontFileName.endsWith(suffix = ".png")) {
            String fileName = String.valueOf(fontFileName.substring(0, fontFileName.length() - suffix.length())) + ".properties";
            try {
                ResourceLocation e = new ResourceLocation(this.locationFontTexture.getResourceDomain(), fileName);
                InputStream in = Config.getResourceStream(Config.getResourceManager(), e);
                if (in == null) {
                    return;
                }
                Config.log("Loading " + fileName);
                Properties props = new Properties();
                props.load(in);
                Set keySet = props.keySet();
                for (String key : keySet) {
                    String prefix;
                    float width;
                    int num;
                    String value;
                    String numStr;
                    if (!key.startsWith(prefix = "width.") || (num = Config.parseInt(numStr = key.substring(prefix.length()), -1)) < 0 || num >= this.charWidth.length || (width = Config.parseFloat(value = props.getProperty(key), -1.0f)) < 0.0f) continue;
                    this.charWidth[num] = width;
                }
            }
            catch (FileNotFoundException e) {
            }
            catch (IOException var16) {
                var16.printStackTrace();
            }
        }
    }

    private static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
        if (!Config.isCustomFonts()) {
            return fontLoc;
        }
        if (fontLoc == null) {
            return fontLoc;
        }
        String fontName = fontLoc.getResourcePath();
        String texturesStr = "textures/";
        String mcpatcherStr = "mcpatcher/";
        if (!fontName.startsWith(texturesStr)) {
            return fontLoc;
        }
        fontName = fontName.substring(texturesStr.length());
        fontName = String.valueOf(mcpatcherStr) + fontName;
        ResourceLocation fontLocHD = new ResourceLocation(fontLoc.getResourceDomain(), fontName);
        return Config.hasResource(Config.getResourceManager(), fontLocHD) ? fontLocHD : fontLoc;
    }
}

