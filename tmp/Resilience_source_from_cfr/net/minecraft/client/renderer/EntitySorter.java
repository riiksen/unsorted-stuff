/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer;

import java.util.Comparator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

public class EntitySorter
implements Comparator {
    private double entityPosX;
    private double entityPosY;
    private double entityPosZ;
    private static final String __OBFID = "CL_00000944";

    public EntitySorter(Entity par1Entity) {
        this.entityPosX = - par1Entity.posX;
        this.entityPosY = - par1Entity.posY;
        this.entityPosZ = - par1Entity.posZ;
    }

    public int compare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer) {
        double var3 = (double)par1WorldRenderer.posXPlus + this.entityPosX;
        double var5 = (double)par1WorldRenderer.posYPlus + this.entityPosY;
        double var7 = (double)par1WorldRenderer.posZPlus + this.entityPosZ;
        double var9 = (double)par2WorldRenderer.posXPlus + this.entityPosX;
        double var11 = (double)par2WorldRenderer.posYPlus + this.entityPosY;
        double var13 = (double)par2WorldRenderer.posZPlus + this.entityPosZ;
        return (int)((var3 * var3 + var5 * var5 + var7 * var7 - (var9 * var9 + var11 * var11 + var13 * var13)) * 1024.0);
    }

    public int compare(Object par1Obj, Object par2Obj) {
        return this.compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}

