/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Properties;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.Config;
import net.minecraft.src.TextureUtils;
import org.lwjgl.opengl.GL11;

public class CustomSkyLayer {
    public String source = null;
    private int startFadeIn = -1;
    private int endFadeIn = -1;
    private int startFadeOut = -1;
    private int endFadeOut = -1;
    private int blend = 0;
    private boolean rotate = false;
    private float speed = 1.0f;
    private float[] axis = DEFAULT_AXIS;
    public int textureId = -1;
    public static final int BLEND_ADD = 0;
    public static final int BLEND_SUBSTRACT = 1;
    public static final int BLEND_MULTIPLY = 2;
    public static final int BLEND_DODGE = 3;
    public static final int BLEND_BURN = 4;
    public static final int BLEND_SCREEN = 5;
    public static final int BLEND_REPLACE = 6;
    public static final float[] DEFAULT_AXIS = new float[]{1.0f, 0.0f, 0.0f};

    public CustomSkyLayer(Properties props, String defSource) {
        this.source = props.getProperty("source", defSource);
        this.startFadeIn = this.parseTime(props.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(props.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(props.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(props.getProperty("endFadeOut"));
        this.blend = this.parseBlend(props.getProperty("blend"));
        this.rotate = this.parseBoolean(props.getProperty("rotate"), true);
        this.speed = this.parseFloat(props.getProperty("speed"), 1.0f);
        this.axis = this.parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
    }

    private int parseTime(String str) {
        if (str == null) {
            return -1;
        }
        String[] strs = Config.tokenize(str, ":");
        if (strs.length != 2) {
            Config.warn("Invalid time: " + str);
            return -1;
        }
        String hourStr = strs[0];
        String minStr = strs[1];
        int hour = Config.parseInt(hourStr, -1);
        int min = Config.parseInt(minStr, -1);
        if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
            if ((hour -= 6) < 0) {
                hour += 24;
            }
            int time = hour * 1000 + (int)((double)min / 60.0 * 1000.0);
            return time;
        }
        Config.warn("Invalid time: " + str);
        return -1;
    }

    private int parseBlend(String str) {
        if (str == null) {
            return 0;
        }
        if (str.equals("add")) {
            return 0;
        }
        if (str.equals("subtract")) {
            return 1;
        }
        if (str.equals("multiply")) {
            return 2;
        }
        if (str.equals("dodge")) {
            return 3;
        }
        if (str.equals("burn")) {
            return 4;
        }
        if (str.equals("screen")) {
            return 5;
        }
        if (str.equals("replace")) {
            return 6;
        }
        Config.warn("Unknown blend: " + str);
        return 0;
    }

    private boolean parseBoolean(String str, boolean defVal) {
        if (str == null) {
            return defVal;
        }
        if (str.toLowerCase().equals("true")) {
            return true;
        }
        if (str.toLowerCase().equals("false")) {
            return false;
        }
        Config.warn("Unknown boolean: " + str);
        return defVal;
    }

    private float parseFloat(String str, float defVal) {
        if (str == null) {
            return defVal;
        }
        float val = Config.parseFloat(str, Float.MIN_VALUE);
        if (val == Float.MIN_VALUE) {
            Config.warn("Invalid value: " + str);
            return defVal;
        }
        return val;
    }

    private float[] parseAxis(String str, float[] defVal) {
        if (str == null) {
            return defVal;
        }
        String[] strs = Config.tokenize(str, " ");
        if (strs.length != 3) {
            Config.warn("Invalid axis: " + str);
            return defVal;
        }
        float[] fs = new float[3];
        int ax = 0;
        while (ax < strs.length) {
            fs[ax] = Config.parseFloat(strs[ax], Float.MIN_VALUE);
            if (fs[ax] == Float.MIN_VALUE) {
                Config.warn("Invalid axis: " + str);
                return defVal;
            }
            if (fs[ax] < -1.0f || fs[ax] > 1.0f) {
                Config.warn("Invalid axis values: " + str);
                return defVal;
            }
            ++ax;
        }
        float var9 = fs[0];
        float ay = fs[1];
        float az = fs[2];
        if (var9 * var9 + ay * ay + az * az < 1.0E-5f) {
            Config.warn("Invalid axis values: " + str);
            return defVal;
        }
        float[] as = new float[]{az, ay, - var9};
        return as;
    }

    public boolean isValid(String path) {
        if (this.source == null) {
            Config.warn("No source texture: " + path);
            return false;
        }
        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
        if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int timeOff;
            int timeFadeOut;
            int timeSum;
            int timeOn;
            int timeFadeIn = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            if (this.startFadeOut < 0) {
                this.startFadeOut = this.normalizeTime(this.endFadeOut - timeFadeIn);
            }
            if ((timeSum = timeFadeIn + (timeOn = this.normalizeTime(this.startFadeOut - this.endFadeIn)) + (timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut)) + (timeOff = this.normalizeTime(this.startFadeIn - this.endFadeOut))) != 24000) {
                Config.warn("Invalid fadeIn/fadeOut times, sum is more than 24h: " + timeSum);
                return false;
            }
            if (this.speed < 0.0f) {
                Config.warn("Invalid speed: " + this.speed);
                return false;
            }
            return true;
        }
        Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
        return false;
    }

    private int normalizeTime(int timeMc) {
        while (timeMc >= 24000) {
            timeMc -= 24000;
        }
        while (timeMc < 0) {
            timeMc += 24000;
        }
        return timeMc;
    }

    public void render(int timeOfDay, float celestialAngle, float rainBrightness) {
        float brightness = rainBrightness * this.getFadeBrightness(timeOfDay);
        if ((brightness = Config.limit(brightness, 0.0f, 1.0f)) >= 1.0E-4f) {
            GL11.glBindTexture((int)3553, (int)this.textureId);
            this.setupBlend(brightness);
            GL11.glPushMatrix();
            if (this.rotate) {
                GL11.glRotatef((float)(celestialAngle * 360.0f * this.speed), (float)this.axis[0], (float)this.axis[1], (float)this.axis[2]);
            }
            Tessellator tess = Tessellator.instance;
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            this.renderSide(tess, 4);
            GL11.glPushMatrix();
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            this.renderSide(tess, 1);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            this.renderSide(tess, 0);
            GL11.glPopMatrix();
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            this.renderSide(tess, 5);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            this.renderSide(tess, 2);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            this.renderSide(tess, 3);
            GL11.glPopMatrix();
        }
    }

    private float getFadeBrightness(int timeOfDay) {
        if (this.timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
            int timeFadeOut = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            int timeDiff = this.normalizeTime(timeOfDay - this.startFadeIn);
            return (float)timeDiff / (float)timeFadeOut;
        }
        if (this.timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut)) {
            return 1.0f;
        }
        if (this.timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
            int timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int timeDiff = this.normalizeTime(timeOfDay - this.startFadeOut);
            return 1.0f - (float)timeDiff / (float)timeFadeOut;
        }
        return 0.0f;
    }

    private void renderSide(Tessellator tess, int side) {
        double tx = (double)(side % 3) / 3.0;
        double ty = (double)(side / 3) / 2.0;
        tess.startDrawingQuads();
        tess.addVertexWithUV(-100.0, -100.0, -100.0, tx, ty);
        tess.addVertexWithUV(-100.0, -100.0, 100.0, tx, ty + 0.5);
        tess.addVertexWithUV(100.0, -100.0, 100.0, tx + 0.3333333333333333, ty + 0.5);
        tess.addVertexWithUV(100.0, -100.0, -100.0, tx + 0.3333333333333333, ty);
        tess.draw();
    }

    void setupBlend(float brightness) {
        switch (this.blend) {
            case 0: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)1);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)brightness);
                break;
            }
            case 1: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)775, (int)0);
                GL11.glColor4f((float)brightness, (float)brightness, (float)brightness, (float)1.0f);
                break;
            }
            case 2: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)774, (int)771);
                GL11.glColor4f((float)brightness, (float)brightness, (float)brightness, (float)brightness);
                break;
            }
            case 3: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)1, (int)1);
                GL11.glColor4f((float)brightness, (float)brightness, (float)brightness, (float)1.0f);
                break;
            }
            case 4: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)0, (int)769);
                GL11.glColor4f((float)brightness, (float)brightness, (float)brightness, (float)1.0f);
                break;
            }
            case 5: {
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)1, (int)769);
                GL11.glColor4f((float)brightness, (float)brightness, (float)brightness, (float)1.0f);
                break;
            }
            case 6: {
                GL11.glEnable((int)3008);
                GL11.glDisable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)brightness);
            }
        }
        GL11.glEnable((int)3553);
    }

    public boolean isActive(int timeOfDay) {
        return !this.timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn);
    }

    private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
        return timeStart <= timeEnd ? timeOfDay >= timeStart && timeOfDay <= timeEnd : timeOfDay >= timeStart || timeOfDay <= timeEnd;
    }
}

