/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.client.renderer;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public class OpenGlCapsChecker {
    private static final String __OBFID = "CL_00000649";

    public static boolean checkARBOcclusion() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
}

