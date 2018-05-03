/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.CompactArrayList;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdater;
import net.minecraft.src.WorldRendererSmooth;
import net.minecraft.world.World;

public class WrUpdaterSmooth
implements IWrUpdater {
    private long lastUpdateStartTimeNs = 0;
    private long updateStartTimeNs = 0;
    private long updateTimeNs = 10000000;
    private WorldRendererSmooth currentUpdateRenderer = null;
    private int renderersUpdated = 0;
    private int renderersFound = 0;

    @Override
    public void initialize() {
    }

    @Override
    public void terminate() {
    }

    @Override
    public WorldRenderer makeWorldRenderer(World worldObj, List tileEntities, int x, int y, int z, int glRenderListBase) {
        return new WorldRendererSmooth(worldObj, tileEntities, x, y, z, glRenderListBase);
    }

    @Override
    public boolean updateRenderers(RenderGlobal rg, EntityLivingBase entityliving, boolean flag) {
        this.lastUpdateStartTimeNs = this.updateStartTimeNs;
        this.updateStartTimeNs = System.nanoTime();
        long finishTimeNs = this.updateStartTimeNs + this.updateTimeNs;
        int maxNum = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !rg.isMoving(entityliving)) {
            maxNum *= 3;
        }
        this.renderersUpdated = 0;
        do {
            this.renderersFound = 0;
            this.updateRenderersImpl(rg, entityliving, flag);
        } while (this.renderersFound > 0 && System.nanoTime() - finishTimeNs < 0);
        if (this.renderersFound > 0) {
            maxNum = Math.min(maxNum, this.renderersFound);
            long diff = 400000;
            if (this.renderersUpdated > maxNum) {
                this.updateTimeNs -= 2 * diff;
            }
            if (this.renderersUpdated < maxNum) {
                this.updateTimeNs += diff;
            }
        } else {
            this.updateTimeNs = 0;
            this.updateTimeNs -= 200000;
        }
        if (this.updateTimeNs < 0) {
            this.updateTimeNs = 0;
        }
        if (this.renderersUpdated > 0) {
            return true;
        }
        return false;
    }

    private void updateRenderersImpl(RenderGlobal rg, EntityLivingBase entityliving, boolean flag) {
        this.renderersFound = 0;
        boolean currentUpdateFinished = true;
        if (this.currentUpdateRenderer != null) {
            ++this.renderersFound;
            currentUpdateFinished = this.updateRenderer(this.currentUpdateRenderer);
            if (currentUpdateFinished) {
                ++this.renderersUpdated;
            }
        }
        if (rg.worldRenderersToUpdate.size() > 0) {
            int NOT_IN_FRUSTRUM_MUL = 4;
            WorldRendererSmooth wrBest = null;
            float distSqBest = Float.MAX_VALUE;
            int indexBest = -1;
            int dstIndex = 0;
            while (dstIndex < rg.worldRenderersToUpdate.size()) {
                WorldRendererSmooth i = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(dstIndex);
                if (i != null) {
                    ++this.renderersFound;
                    if (!i.needsUpdate) {
                        rg.worldRenderersToUpdate.set(dstIndex, null);
                    } else {
                        float wr = i.distanceToEntitySquared(entityliving);
                        if (wr <= 256.0f && rg.isActingNow()) {
                            i.updateRenderer();
                            i.needsUpdate = false;
                            rg.worldRenderersToUpdate.set(dstIndex, null);
                            ++this.renderersUpdated;
                        } else {
                            if (!i.isInFrustum) {
                                wr *= (float)NOT_IN_FRUSTRUM_MUL;
                            }
                            if (wrBest == null) {
                                wrBest = i;
                                distSqBest = wr;
                                indexBest = dstIndex;
                            } else if (wr < distSqBest) {
                                wrBest = i;
                                distSqBest = wr;
                                indexBest = dstIndex;
                            }
                        }
                    }
                }
                ++dstIndex;
            }
            if (this.currentUpdateRenderer == null || currentUpdateFinished) {
                if (wrBest != null) {
                    rg.worldRenderersToUpdate.set(indexBest, null);
                    if (!this.updateRenderer(wrBest)) {
                        return;
                    }
                    ++this.renderersUpdated;
                    if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
                        return;
                    }
                    float var14 = distSqBest / 5.0f;
                    int var15 = 0;
                    while (var15 < rg.worldRenderersToUpdate.size()) {
                        WorldRendererSmooth var16 = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(var15);
                        if (var16 != null) {
                            float diffDistSq;
                            float distSq = var16.distanceToEntitySquared(entityliving);
                            if (!var16.isInFrustum) {
                                distSq *= (float)NOT_IN_FRUSTRUM_MUL;
                            }
                            if ((diffDistSq = Math.abs(distSq - distSqBest)) < var14) {
                                rg.worldRenderersToUpdate.set(var15, null);
                                if (!this.updateRenderer(var16)) {
                                    return;
                                }
                                ++this.renderersUpdated;
                                if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) break;
                            }
                        }
                        ++var15;
                    }
                }
                if (this.renderersFound == 0) {
                    rg.worldRenderersToUpdate.clear();
                }
                if (rg.worldRenderersToUpdate.size() > 100 && this.renderersFound < rg.worldRenderersToUpdate.size() * 4 / 5) {
                    dstIndex = 0;
                    int var15 = 0;
                    while (var15 < rg.worldRenderersToUpdate.size()) {
                        Object var17 = rg.worldRenderersToUpdate.get(var15);
                        if (var17 != null) {
                            if (var15 != dstIndex) {
                                rg.worldRenderersToUpdate.set(dstIndex, var17);
                            }
                            ++dstIndex;
                        }
                        ++var15;
                    }
                    var15 = rg.worldRenderersToUpdate.size() - 1;
                    while (var15 >= dstIndex) {
                        rg.worldRenderersToUpdate.remove(var15);
                        --var15;
                    }
                }
            }
        }
    }

    private boolean updateRenderer(WorldRendererSmooth wr) {
        long finishTime = this.updateStartTimeNs + this.updateTimeNs;
        wr.needsUpdate = false;
        boolean ready = wr.updateRenderer(finishTime);
        if (!ready) {
            this.currentUpdateRenderer = wr;
            return false;
        }
        wr.finishUpdate();
        this.currentUpdateRenderer = null;
        return true;
    }

    @Override
    public void finishCurrentUpdate() {
        if (this.currentUpdateRenderer != null) {
            this.currentUpdateRenderer.updateRenderer();
            this.currentUpdateRenderer = null;
        }
    }

    @Override
    public void resumeBackgroundUpdates() {
    }

    @Override
    public void pauseBackgroundUpdates() {
    }

    @Override
    public void preRender(RenderGlobal rg, EntityLivingBase player) {
    }

    @Override
    public void postRender() {
    }

    @Override
    public void clearAllUpdates() {
        this.finishCurrentUpdate();
    }
}

