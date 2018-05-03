/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer
implements IChunkProvider {
    private static final Logger logger = LogManager.getLogger();
    private Set chunksToUnload = new HashSet();
    private Chunk defaultEmptyChunk;
    private IChunkProvider currentChunkProvider;
    private IChunkLoader currentChunkLoader;
    public boolean loadChunkOnProvideRequest = true;
    private LongHashMap loadedChunkHashMap = new LongHashMap();
    private List loadedChunks = new ArrayList();
    private WorldServer worldObj;
    private static final String __OBFID = "CL_00001436";

    public ChunkProviderServer(WorldServer par1WorldServer, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider) {
        this.defaultEmptyChunk = new EmptyChunk(par1WorldServer, 0, 0);
        this.worldObj = par1WorldServer;
        this.currentChunkLoader = par2IChunkLoader;
        this.currentChunkProvider = par3IChunkProvider;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return this.loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
    }

    public void unloadChunksIfNotNearSpawn(int par1, int par2) {
        if (this.worldObj.provider.canRespawnHere()) {
            ChunkCoordinates var3 = this.worldObj.getSpawnPoint();
            int var4 = par1 * 16 + 8 - var3.posX;
            int var5 = par2 * 16 + 8 - var3.posZ;
            int var6 = 128;
            if (var4 < - var6 || var4 > var6 || var5 < - var6 || var5 > var6) {
                this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
            }
        } else {
            this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        }
    }

    public void unloadAllChunks() {
        for (Chunk var2 : this.loadedChunks) {
            this.unloadChunksIfNotNearSpawn(var2.xPosition, var2.zPosition);
        }
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        long var3 = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
        this.chunksToUnload.remove(var3);
        Chunk var5 = (Chunk)this.loadedChunkHashMap.getValueByKey(var3);
        if (var5 == null) {
            var5 = this.safeLoadChunk(par1, par2);
            if (var5 == null) {
                if (this.currentChunkProvider == null) {
                    var5 = this.defaultEmptyChunk;
                } else {
                    try {
                        var5 = this.currentChunkProvider.provideChunk(par1, par2);
                    }
                    catch (Throwable var9) {
                        CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
                        CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
                        var8.addCrashSection("Location", String.format("%d,%d", par1, par2));
                        var8.addCrashSection("Position hash", var3);
                        var8.addCrashSection("Generator", this.currentChunkProvider.makeString());
                        throw new ReportedException(var7);
                    }
                }
            }
            this.loadedChunkHashMap.add(var3, var5);
            this.loadedChunks.add(var5);
            var5.onChunkLoad();
            var5.populateChunk(this, this, par1, par2);
        }
        return var5;
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        return var3 == null ? (!this.worldObj.findingSpawnPoint && !this.loadChunkOnProvideRequest ? this.defaultEmptyChunk : this.loadChunk(par1, par2)) : var3;
    }

    private Chunk safeLoadChunk(int par1, int par2) {
        if (this.currentChunkLoader == null) {
            return null;
        }
        try {
            Chunk var3 = this.currentChunkLoader.loadChunk(this.worldObj, par1, par2);
            if (var3 != null) {
                var3.lastSaveTime = this.worldObj.getTotalWorldTime();
                if (this.currentChunkProvider != null) {
                    this.currentChunkProvider.recreateStructures(par1, par2);
                }
            }
            return var3;
        }
        catch (Exception var4) {
            logger.error("Couldn't load chunk", (Throwable)var4);
            return null;
        }
    }

    private void safeSaveExtraChunkData(Chunk par1Chunk) {
        if (this.currentChunkLoader != null) {
            try {
                this.currentChunkLoader.saveExtraChunkData(this.worldObj, par1Chunk);
            }
            catch (Exception var3) {
                logger.error("Couldn't save entities", (Throwable)var3);
            }
        }
    }

    private void safeSaveChunk(Chunk par1Chunk) {
        if (this.currentChunkLoader != null) {
            try {
                par1Chunk.lastSaveTime = this.worldObj.getTotalWorldTime();
                this.currentChunkLoader.saveChunk(this.worldObj, par1Chunk);
            }
            catch (IOException var3) {
                logger.error("Couldn't save chunk", (Throwable)var3);
            }
            catch (MinecraftException var4) {
                logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)var4);
            }
        }
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        Chunk var4 = this.provideChunk(par2, par3);
        if (!var4.isTerrainPopulated) {
            var4.func_150809_p();
            if (this.currentChunkProvider != null) {
                this.currentChunkProvider.populate(par1IChunkProvider, par2, par3);
                var4.setChunkModified();
            }
        }
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        int var3 = 0;
        int var4 = 0;
        while (var4 < this.loadedChunks.size()) {
            Chunk var5 = (Chunk)this.loadedChunks.get(var4);
            if (par1) {
                this.safeSaveExtraChunkData(var5);
            }
            if (var5.needsSaving(par1)) {
                this.safeSaveChunk(var5);
                var5.isModified = false;
                if (++var3 == 24 && !par1) {
                    return false;
                }
            }
            ++var4;
        }
        return true;
    }

    @Override
    public void saveExtraData() {
        if (this.currentChunkLoader != null) {
            this.currentChunkLoader.saveExtraData();
        }
    }

    @Override
    public boolean unloadQueuedChunks() {
        if (!this.worldObj.levelSaving) {
            int var1 = 0;
            while (var1 < 100) {
                if (!this.chunksToUnload.isEmpty()) {
                    Long var2 = (Long)this.chunksToUnload.iterator().next();
                    Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(var2);
                    var3.onChunkUnload();
                    this.safeSaveChunk(var3);
                    this.safeSaveExtraChunkData(var3);
                    this.chunksToUnload.remove(var2);
                    this.loadedChunkHashMap.remove(var2);
                    this.loadedChunks.remove(var3);
                }
                ++var1;
            }
            if (this.currentChunkLoader != null) {
                this.currentChunkLoader.chunkTick();
            }
        }
        return this.currentChunkProvider.unloadQueuedChunks();
    }

    @Override
    public boolean canSave() {
        return !this.worldObj.levelSaving;
    }

    @Override
    public String makeString() {
        return "ServerChunkCache: " + this.loadedChunkHashMap.getNumHashElements() + " Drop: " + this.chunksToUnload.size();
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        return this.currentChunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return this.currentChunkProvider.func_147416_a(p_147416_1_, p_147416_2_, p_147416_3_, p_147416_4_, p_147416_5_);
    }

    @Override
    public int getLoadedChunkCount() {
        return this.loadedChunkHashMap.getNumHashElements();
    }

    @Override
    public void recreateStructures(int par1, int par2) {
    }
}

