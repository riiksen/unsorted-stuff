/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class BiomeCache {
    private final WorldChunkManager chunkManager;
    private long lastCleanupTime;
    private LongHashMap cacheMap = new LongHashMap();
    private List cache = new ArrayList();
    private static final String __OBFID = "CL_00000162";

    public BiomeCache(WorldChunkManager par1WorldChunkManager) {
        this.chunkManager = par1WorldChunkManager;
    }

    public Block getBiomeCacheBlock(int par1, int par2) {
        long var3 = (long)(par1 >>= 4) & 0xFFFFFFFFL | ((long)(par2 >>= 4) & 0xFFFFFFFFL) << 32;
        Block var5 = (Block)this.cacheMap.getValueByKey(var3);
        if (var5 == null) {
            var5 = new Block(par1, par2);
            this.cacheMap.add(var3, var5);
            this.cache.add(var5);
        }
        var5.lastAccessTime = MinecraftServer.getSystemTimeMillis();
        return var5;
    }

    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
    }

    public void cleanupCache() {
        long var1 = MinecraftServer.getSystemTimeMillis();
        long var3 = var1 - this.lastCleanupTime;
        if (var3 > 7500 || var3 < 0) {
            this.lastCleanupTime = var1;
            int var5 = 0;
            while (var5 < this.cache.size()) {
                Block var6 = (Block)this.cache.get(var5);
                long var7 = var1 - var6.lastAccessTime;
                if (var7 > 30000 || var7 < 0) {
                    this.cache.remove(var5--);
                    long var9 = (long)var6.xPosition & 0xFFFFFFFFL | ((long)var6.zPosition & 0xFFFFFFFFL) << 32;
                    this.cacheMap.remove(var9);
                }
                ++var5;
            }
        }
    }

    public BiomeGenBase[] getCachedBiomes(int par1, int par2) {
        return this.getBiomeCacheBlock((int)par1, (int)par2).biomes;
    }

    public class Block {
        public float[] rainfallValues;
        public BiomeGenBase[] biomes;
        public int xPosition;
        public int zPosition;
        public long lastAccessTime;
        private static final String __OBFID = "CL_00000163";

        public Block(int par2, int par3) {
            this.rainfallValues = new float[256];
            this.biomes = new BiomeGenBase[256];
            this.xPosition = par2;
            this.zPosition = par3;
            BiomeCache.this.chunkManager.getRainfall(this.rainfallValues, par2 << 4, par3 << 4, 16, 16);
            BiomeCache.this.chunkManager.getBiomeGenAt(this.biomes, par2 << 4, par3 << 4, 16, 16, false);
        }

        public BiomeGenBase getBiomeGenAt(int par1, int par2) {
            return this.biomes[par1 & 15 | (par2 & 15) << 4];
        }
    }

}

