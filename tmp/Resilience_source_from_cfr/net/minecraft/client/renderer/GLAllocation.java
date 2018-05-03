/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lwjgl.opengl.GL11;

public class GLAllocation {
    private static final Map mapDisplayLists = new HashMap();
    private static final List listDummy = new ArrayList();
    private static final String __OBFID = "CL_00000630";

    public static synchronized int generateDisplayLists(int par0) {
        int var1 = GL11.glGenLists((int)par0);
        mapDisplayLists.put(var1, par0);
        return var1;
    }

    public static synchronized void deleteDisplayLists(int par0) {
        GL11.glDeleteLists((int)par0, (int)((Integer)mapDisplayLists.remove(par0)));
    }

    public static synchronized void deleteTexturesAndDisplayLists() {
        for (Map.Entry var1 : mapDisplayLists.entrySet()) {
            GL11.glDeleteLists((int)((Integer)var1.getKey()), (int)((Integer)var1.getValue()));
        }
        mapDisplayLists.clear();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int par0) {
        return ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
    }

    public static IntBuffer createDirectIntBuffer(int par0) {
        return GLAllocation.createDirectByteBuffer(par0 << 2).asIntBuffer();
    }

    public static FloatBuffer createDirectFloatBuffer(int par0) {
        return GLAllocation.createDirectByteBuffer(par0 << 2).asFloatBuffer();
    }
}

