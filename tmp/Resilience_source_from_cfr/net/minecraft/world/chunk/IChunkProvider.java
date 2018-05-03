/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.chunk;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface IChunkProvider {
    public boolean chunkExists(int var1, int var2);

    public Chunk provideChunk(int var1, int var2);

    public Chunk loadChunk(int var1, int var2);

    public void populate(IChunkProvider var1, int var2, int var3);

    public boolean saveChunks(boolean var1, IProgressUpdate var2);

    public boolean unloadQueuedChunks();

    public boolean canSave();

    public String makeString();

    public List getPossibleCreatures(EnumCreatureType var1, int var2, int var3, int var4);

    public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5);

    public int getLoadedChunkCount();

    public void recreateStructures(int var1, int var2);

    public void saveExtraData();
}

