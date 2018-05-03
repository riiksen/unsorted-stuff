/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.utilities.font;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TTFRenderer {
    private Minecraft mc = Minecraft.getMinecraft();
    private final UnicodeFont unicodeFont;
    private final int[] colorCodes = new int[32];
    private int fontType;
    private int size;
    private String fontName;
    private float kerning;

    public TTFRenderer(String fontName, int fontType, int size) {
        this(fontName, fontType, size, 0.0f);
    }

    public TTFRenderer(String fontName, int fontType, int size, float kerning) {
        this.fontName = fontName;
        this.fontType = fontType;
        this.size = size;
        this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < 32) {
            int shadow = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + shadow;
            int green = (i >> 1 & 1) * 170 + shadow;
            int blue = (i >> 0 & 1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
            ++i;
        }
    }

    public int drawString(String text, float x, float y, int color) {
        y *= 2.0f;
        float originalX = x *= 2.0f;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        boolean blend = GL11.glIsEnabled((int)3042);
        boolean lighting = GL11.glIsEnabled((int)2896);
        boolean texture = GL11.glIsEnabled((int)3553);
        if (!blend) {
            GL11.glEnable((int)3042);
        }
        if (lighting) {
            GL11.glDisable((int)2896);
        }
        if (texture) {
            GL11.glDisable((int)3553);
        }
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        char[] arrc = characters;
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            block15 : {
                block13 : {
                    int col;
                    char c;
                    block14 : {
                        block12 : {
                            c = arrc[n2];
                            if (c == '\r') {
                                x = originalX;
                            }
                            if (c == '\n') {
                                y += this.getHeight(Character.toString(c)) * 2.0f;
                            }
                            if (c == '\u00a7' || index != 0 && index != characters.length - 1 && characters[index - 1] == '\u00a7') break block12;
                            this.unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
                            x += this.getWidth(Character.toString(c)) * 2.0f;
                            break block13;
                        }
                        if (c != ' ') break block14;
                        x += (float)this.unicodeFont.getSpaceWidth();
                        break block13;
                    }
                    if (c != '\u00a7' || index == characters.length - 1) break block13;
                    int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                    if (codeIndex < 0) break block15;
                    currentColor = col = this.colorCodes[codeIndex];
                }
                ++index;
            }
            ++n2;
        }
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        if (texture) {
            GL11.glEnable((int)3553);
        }
        if (lighting) {
            GL11.glEnable((int)2896);
        }
        if (!blend) {
            GL11.glDisable((int)3042);
        }
        GL11.glPopMatrix();
        return (int)x;
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0);
        return this.drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float)((int)this.getWidth(text) / 2), y, color);
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, color);
        this.drawCenteredString(text, x, y, color);
    }

    public float getWidth(String s) {
        float width = 0.0f;
        String str = StringUtils.stripControlCodes(s);
        char[] arrc = str.toCharArray();
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            char c = arrc[n2];
            width += (float)this.unicodeFont.getWidth(Character.toString(c)) + this.kerning;
            ++n2;
        }
        return width / 2.0f;
    }

    public float getCharWidth(char c) {
        return this.unicodeFont.getWidth(String.valueOf(c));
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }

    public String trimStringToWidth(String par1Str, int par2) {
        StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        int var6 = 0;
        int var7 = 1;
        boolean var8 = false;
        boolean var9 = false;
        int var10 = var6;
        while (var10 >= 0 && var10 < par1Str.length() && var5 < (float)par2) {
            char var11 = par1Str.charAt(var10);
            float var12 = this.getCharWidth(var11);
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
            var4.append(var11);
            var10 += var7;
        }
        return var4.toString();
    }
}

