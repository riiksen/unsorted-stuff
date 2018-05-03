/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface IWorldAccess {
    public void markBlockForUpdate(int var1, int var2, int var3);

    public void markBlockForRenderUpdate(int var1, int var2, int var3);

    public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6);

    public void playSound(String var1, double var2, double var4, double var6, float var8, float var9);

    public void playSoundToNearExcept(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10);

    public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12);

    public void onEntityCreate(Entity var1);

    public void onEntityDestroy(Entity var1);

    public void playRecord(String var1, int var2, int var3, int var4);

    public void broadcastSound(int var1, int var2, int var3, int var4, int var5);

    public void playAuxSFX(EntityPlayer var1, int var2, int var3, int var4, int var5, int var6);

    public void destroyBlockPartially(int var1, int var2, int var3, int var4, int var5);

    public void onStaticEntitiesChanged();
}

