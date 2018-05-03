/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
    private static final Vec3 field_82884_b = Vec3.createVectorHelper(0.20000000298023224, 1.0, -0.699999988079071).normalize();
    private static final Vec3 field_82885_c = Vec3.createVectorHelper(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    private static final String __OBFID = "CL_00000629";

    public static void disableStandardItemLighting() {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)16384);
        GL11.glDisable((int)16385);
        GL11.glDisable((int)2903);
    }

    public static void enableStandardItemLighting() {
        GL11.glEnable((int)2896);
        GL11.glEnable((int)16384);
        GL11.glEnable((int)16385);
        GL11.glEnable((int)2903);
        GL11.glColorMaterial((int)1032, (int)5634);
        float var0 = 0.4f;
        float var1 = 0.6f;
        float var2 = 0.0f;
        GL11.glLight((int)16384, (int)4611, (FloatBuffer)RenderHelper.setColorBuffer(RenderHelper.field_82884_b.xCoord, RenderHelper.field_82884_b.yCoord, RenderHelper.field_82884_b.zCoord, 0.0));
        GL11.glLight((int)16384, (int)4609, (FloatBuffer)RenderHelper.setColorBuffer(var1, var1, var1, 1.0f));
        GL11.glLight((int)16384, (int)4608, (FloatBuffer)RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight((int)16384, (int)4610, (FloatBuffer)RenderHelper.setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glLight((int)16385, (int)4611, (FloatBuffer)RenderHelper.setColorBuffer(RenderHelper.field_82885_c.xCoord, RenderHelper.field_82885_c.yCoord, RenderHelper.field_82885_c.zCoord, 0.0));
        GL11.glLight((int)16385, (int)4609, (FloatBuffer)RenderHelper.setColorBuffer(var1, var1, var1, 1.0f));
        GL11.glLight((int)16385, (int)4608, (FloatBuffer)RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight((int)16385, (int)4610, (FloatBuffer)RenderHelper.setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glShadeModel((int)7424);
        GL11.glLightModel((int)2899, (FloatBuffer)RenderHelper.setColorBuffer(var0, var0, var0, 1.0f));
    }

    private static FloatBuffer setColorBuffer(double par0, double par2, double par4, double par6) {
        return RenderHelper.setColorBuffer((float)par0, (float)par2, (float)par4, (float)par6);
    }

    private static FloatBuffer setColorBuffer(float par0, float par1, float par2, float par3) {
        colorBuffer.clear();
        colorBuffer.put(par0).put(par1).put(par2).put(par3);
        colorBuffer.flip();
        return colorBuffer;
    }

    public static void enableGUIStandardItemLighting() {
        GL11.glPushMatrix();
        GL11.glRotatef((float)-30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)165.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }
}

