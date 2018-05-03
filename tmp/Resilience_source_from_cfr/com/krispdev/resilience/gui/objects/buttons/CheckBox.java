/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.utilities.Utils;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CheckBox {
    public static ArrayList<CheckBox> checkBoxList = new ArrayList();
    private float x;
    private float y;
    private boolean checked;

    public CheckBox(float x, float y, boolean checked) {
        this.x = x;
        this.y = y;
        this.checked = checked;
        checkBoxList.add(this);
    }

    public void draw(int i, int j) {
        boolean overBox;
        boolean bl = overBox = (float)i >= this.x && (float)i <= this.x + 8.0f && (float)j >= this.y && (float)j <= this.y + 8.0f;
        Utils.drawBetterRect(this.x, this.y, this.x + 8.0f, this.y + 8.0f, -13421773, overBox && Mouse.isButtonDown((int)0) ? -16185079 : (overBox ? -15592942 : -15724528));
        if (this.checked) {
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glColor3f((float)0.0f, (float)0.5f, (float)1.0f);
            GL11.glLineWidth((float)1.5f);
            GL11.glBegin((int)3);
            GL11.glVertex2f((float)(this.x + 1.0f), (float)(this.y + 1.0f));
            GL11.glVertex2f((float)(this.x + 4.0f), (float)(this.y + 6.0f));
            GL11.glVertex2f((float)(this.x + 10.0f), (float)(this.y - 5.0f));
            GL11.glEnd();
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
    }

    public boolean clicked(int i, int j) {
        if ((float)i >= this.x && (float)i <= this.x + 8.0f && (float)j >= this.y && (float)j <= this.y + 8.0f) {
            this.checked = !this.checked;
            return true;
        }
        return false;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

