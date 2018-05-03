/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.server.management;

import java.util.Comparator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

public class PlayerPositionComparator
implements Comparator {
    private final ChunkCoordinates theChunkCoordinates;
    private static final String __OBFID = "CL_00001422";

    public PlayerPositionComparator(ChunkCoordinates par1ChunkCoordinates) {
        this.theChunkCoordinates = par1ChunkCoordinates;
    }

    public int compare(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP) {
        double var5;
        double var3 = par1EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
        return var3 < (var5 = par2EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ)) ? -1 : (var3 > var5 ? 1 : 0);
    }

    public int compare(Object par1Obj, Object par2Obj) {
        return this.compare((EntityPlayerMP)par1Obj, (EntityPlayerMP)par2Obj);
    }
}

