/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.utilities;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
    private static MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private static EntityUtils entityUtils = new EntityUtils();

    public static void setup3DLightlessModel() {
        Resilience.getInstance().getInvoker().setEntityLight(false);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
    }

    public static void shutdown3DLightlessModel() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        Resilience.getInstance().getInvoker().setEntityLight(true);
    }

    public static void drawOutlinedCrossedBoundingBox(AxisAlignedBB aa) {
        Tessellator t = Tessellator.instance;
        t.startDrawing(3);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.draw();
        t.startDrawing(1);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.draw();
        t.startDrawing(1);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.draw();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator t = Tessellator.instance;
        t.startDrawing(3);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.draw();
        t.startDrawing(1);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.draw();
    }

    public static void drawESP(boolean crossed, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double r, double g, double b, double a, double r2, double g2, double b2, double a2) {
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        invoker.setEntityLight(false);
        GL11.glColor4d((double)r, (double)g, (double)b, (double)a);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        GL11.glColor4d((double)r2, (double)g2, (double)b2, (double)a2);
        GL11.glLineWidth((float)0.5f);
        if (crossed) {
            RenderUtils.drawOutlinedCrossedBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        } else {
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        }
        invoker.setEntityLight(true);
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
    }

    public static void drawPlayerESP(EntityPlayer player) {
        if (entityUtils.isThePlayer(player)) {
            return;
        }
        boolean friend = entityUtils.isEntityFriend(player);
        boolean enemy = entityUtils.isEntityEnemy(player);
        RenderUtils.drawESP(true, player.boundingBox.minX - invoker.getRenderPosX() - 0.1, player.boundingBox.minY - invoker.getRenderPosY(), player.boundingBox.minZ - invoker.getRenderPosZ() - 0.1, player.boundingBox.maxX - invoker.getRenderPosX() + 0.1, player.boundingBox.maxY + 0.15 - invoker.getRenderPosY(), player.boundingBox.maxZ - invoker.getRenderPosZ() + 0.1, friend ? 0.2 : (double)(enemy ? true : true) ? 1 : 0, friend || enemy ? 0.2 : 1.0, enemy ? 0.2 : 1.0, 0.19, friend ? 0.5 : (double)(enemy ? true : true) ? 1 : 0, friend || enemy ? 0.5 : 1.0, enemy ? 0.5 : 1.0, 1.0);
    }

    public static void drawTracer(double bX, double bY, double bZ, double eX, double eY, double eZ, double r, double g, double b, double alpha) {
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        GL11.glEnable((int)2848);
        GL11.glColor4d((double)r, (double)g, (double)b, (double)alpha);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)bX, (double)bY, (double)bZ);
        GL11.glVertex3d((double)eX, (double)eY, (double)eZ);
        GL11.glEnd();
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
    }
}

