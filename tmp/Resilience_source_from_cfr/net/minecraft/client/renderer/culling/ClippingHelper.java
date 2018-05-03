/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.culling;

public class ClippingHelper {
    public float[][] frustum = new float[16][16];
    public float[] projectionMatrix = new float[16];
    public float[] modelviewMatrix = new float[16];
    public float[] clippingMatrix = new float[16];
    private static final String __OBFID = "CL_00000977";

    public boolean isBoxInFrustum(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        int i = 0;
        while (i < 6) {
            float minXf = (float)minX;
            float minYf = (float)minY;
            float minZf = (float)minZ;
            float maxXf = (float)maxX;
            float maxYf = (float)maxY;
            float maxZf = (float)maxZ;
            if (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        int i = 0;
        while (i < 6) {
            float minXf = (float)minX;
            float minYf = (float)minY;
            float minZf = (float)minZ;
            float maxXf = (float)maxX;
            float maxYf = (float)maxY;
            float maxZf = (float)maxZ;
            if (i < 4 ? this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f : this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f) {
                return false;
            }
            ++i;
        }
        return true;
    }
}

