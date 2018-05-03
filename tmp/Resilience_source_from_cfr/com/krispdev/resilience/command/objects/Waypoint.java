/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.command.objects;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class Waypoint {
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    public static List<Waypoint> waypointsList = new ArrayList<Waypoint>();
    private int x;
    private int y;
    private int z;
    private float r;
    private float g;
    private float b;
    String name;

    public Waypoint(String name, int x, int y, int z, float r, float b, float g) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.name = name;
    }

    public void draw() {
        this.invoker.setEntityLight(false);
        RenderUtils.drawESP(false, (double)this.x - this.invoker.getRenderPosX() - 0.5, (double)this.y - this.invoker.getRenderPosY() - 0.5, (double)this.z - this.invoker.getRenderPosZ() - 0.5, (double)this.x - this.invoker.getRenderPosX() + 0.5, (double)this.y - this.invoker.getRenderPosY() + 0.5, (double)this.z - this.invoker.getRenderPosZ() + 0.5, this.r, this.g, this.b, 0.183, this.r, this.g, this.b, 1.0);
        float f = 3.0f;
        int color = -1;
        float scale = f / 150.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)((double)this.x - this.invoker.getRenderPosX())), (float)((float)((double)this.y - this.invoker.getRenderPosY()) + 1.0f), (float)((float)((double)this.z - this.invoker.getRenderPosZ())));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.invoker.getPlayerViewY()), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)this.invoker.getPlayerViewX(), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(- scale), (float)(- scale), (float)scale);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        boolean byte0 = false;
        int i = (int)(Resilience.getInstance().getWaypointFont().getWidth(this.name) / 2.0f);
        Resilience.getInstance().getWaypointFont().drawString(this.name, (- Resilience.getInstance().getWaypointFont().getWidth(this.name)) / 2.0f, (float)byte0 ? 1 : 0, color);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        try {
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)1.0f);
            double posX = (double)this.x - RenderManager.renderPosX;
            double posY = (double)this.y - RenderManager.renderPosY;
            double posZ = (double)this.z - RenderManager.renderPosZ;
            GL11.glColor4d((double)this.r, (double)this.g, (double)this.b, (double)1.0);
            GL11.glBegin((int)2);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception posX) {
            // empty catch block
        }
        this.invoker.setEntityLight(true);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public float getR() {
        return this.r;
    }

    public float getG() {
        return this.g;
    }

    public float getB() {
        return this.b;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "Name: \u00a7b" + this.name + " \u00a7fX: \u00a7b" + this.x + " \u00a7fY: \u00a7b" + this.y + " \u00a7fZ: \u00a7b" + this.z;
    }
}

