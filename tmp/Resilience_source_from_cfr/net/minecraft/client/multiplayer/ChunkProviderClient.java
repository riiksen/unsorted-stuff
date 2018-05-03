/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderClient
implements IChunkProvider {
    private static final Logger logger = LogManager.getLogger();
    private Chunk blankChunk;
    private LongHashMap chunkMapping = new LongHashMap();
    private List chunkListing = new ArrayList();
    private World worldObj;
    private static final String __OBFID = "CL_00000880";

    public ChunkProviderClient(World par1World) {
        this.blankChunk = new EmptyChunk(par1World, 0, 0);
        this.worldObj = par1World;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void unloadChunk(int par1, int par2) {
        Chunk var3 = this.provideChunk(par1, par2);
        if (!var3.isEmpty()) {
            var3.onChunkUnload();
        }
        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        this.chunkListing.remove(var3);
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        Chunk var3 = new Chunk(this.worldObj, par1, par2);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2), var3);
        this.chunkListing.add(var3);
        var3.isChunkLoaded = true;
        return var3;
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
        return var3 == null ? this.blankChunk : var3;
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        long var1 = System.currentTimeMillis();
        for (Chunk var4 : this.chunkListing) {
            var4.func_150804_b(System.currentTimeMillis() - var1 > 5);
        }
        if (System.currentTimeMillis() - var1 > 100) {
            logger.info("Warning: Clientside chunk ticking took {} ms", new Object[]{System.currentTimeMillis() - var1});
        }
        return false;
    }

    @Override
    public boolean canSave() {
        return false;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
    }

    @Override
    public String makeString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }

    @Override
    public void recreateStructures(int par1, int par2) {
    }
}

