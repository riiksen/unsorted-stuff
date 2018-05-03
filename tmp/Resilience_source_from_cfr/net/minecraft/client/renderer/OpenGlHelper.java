/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBMultitexture
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.opengl.GL14
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper {
    public static boolean openGL21;
    public static int defaultTexUnit;
    public static int lightmapTexUnit;
    public static boolean anisotropicFilteringSupported;
    public static int anisotropicFilteringMax;
    private static boolean useMultitextureARB;
    private static boolean openGL14;
    public static boolean framebufferSupported;
    public static boolean shadersSupported;
    public static float lastBrightnessX;
    public static float lastBrightnessY;
    private static final String __OBFID = "CL_00001179";

    static {
        lastBrightnessX = 0.0f;
        lastBrightnessY = 0.0f;
    }

    public static void initializeTextures() {
        Config.initDisplay();
        boolean bl = OpenGlHelper.useMultitextureARB = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
        if (useMultitextureARB) {
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
        } else {
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
        }
        openGL14 = GLContext.getCapabilities().OpenGL14;
        framebufferSupported = openGL14 && GLContext.getCapabilities().GL_ARB_framebuffer_object;
        anisotropicFilteringSupported = GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic;
        anisotropicFilteringMax = (int)(anisotropicFilteringSupported ? GL11.glGetFloat((int)34047) : 0.0f);
        GameSettings.Options.ANISOTROPIC_FILTERING.setValueMax(anisotropicFilteringMax);
        openGL21 = GLContext.getCapabilities().OpenGL21;
        shadersSupported = framebufferSupported && openGL21;
    }

    public static void setActiveTexture(int par0) {
        if (useMultitextureARB) {
            ARBMultitexture.glActiveTextureARB((int)par0);
        } else {
            GL13.glActiveTexture((int)par0);
        }
    }

    public static void setClientActiveTexture(int par0) {
        if (useMultitextureARB) {
            ARBMultitexture.glClientActiveTextureARB((int)par0);
        } else {
            GL13.glClientActiveTexture((int)par0);
        }
    }

    public static void setLightmapTextureCoords(int par0, float par1, float par2) {
        if (useMultitextureARB) {
            ARBMultitexture.glMultiTexCoord2fARB((int)par0, (float)par1, (float)par2);
        } else {
            GL13.glMultiTexCoord2f((int)par0, (float)par1, (float)par2);
        }
        if (par0 == lightmapTexUnit) {
            lastBrightnessX = par1;
            lastBrightnessY = par2;
        }
    }

    public static void glBlendFunc(int p_148821_0_, int p_148821_1_, int p_148821_2_, int p_148821_3_) {
        if (openGL14) {
            GL14.glBlendFuncSeparate((int)p_148821_0_, (int)p_148821_1_, (int)p_148821_2_, (int)p_148821_3_);
        } else {
            GL11.glBlendFunc((int)p_148821_0_, (int)p_148821_1_);
        }
    }

    public static boolean isFramebufferEnabled() {
        if (framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable) {
            return true;
        }
        return false;
    }
}

