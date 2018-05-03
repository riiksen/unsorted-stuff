/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer;

import java.util.Comparator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderSorter
implements Comparator {
    private EntityLivingBase baseEntity;
    private static final String __OBFID = "CL_00000943";

    public RenderSorter(EntityLivingBase par1EntityLivingBase) {
        this.baseEntity = par1EntityLivingBase;
    }

    public int compare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer) {
        double var5;
        if (par1WorldRenderer.isInFrustum && !par2WorldRenderer.isInFrustum) {
            return 1;
        }
        if (par2WorldRenderer.isInFrustum && !par1WorldRenderer.isInFrustum) {
            return -1;
        }
        double var3 = par1WorldRenderer.distanceToEntitySquared(this.baseEntity);
        return var3 < (var5 = (double)par2WorldRenderer.distanceToEntitySquared(this.baseEntity)) ? 1 : (var3 > var5 ? -1 : (par1WorldRenderer.chunkIndex < par2WorldRenderer.chunkIndex ? 1 : -1));
    }

    public int compare(Object par1Obj, Object par2Obj) {
        return this.compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}

