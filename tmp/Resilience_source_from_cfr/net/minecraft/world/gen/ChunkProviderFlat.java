/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;

public class ChunkProviderFlat
implements IChunkProvider {
    private World worldObj;
    private Random random;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];
    private final FlatGeneratorInfo flatWorldGenInfo;
    private final List structureGenerators = new ArrayList();
    private final boolean hasDecoration;
    private final boolean hasDungeons;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;
    private static final String __OBFID = "CL_00000391";

    public ChunkProviderFlat(World par1World, long par2, boolean par4, String par5Str) {
        this.worldObj = par1World;
        this.random = new Random(par2);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par5Str);
        if (par4) {
            Map var6 = this.flatWorldGenInfo.getWorldFeatures();
            if (var6.containsKey("village")) {
                Map var7 = (Map)var6.get("village");
                if (!var7.containsKey("size")) {
                    var7.put("size", "1");
                }
                this.structureGenerators.add(new MapGenVillage(var7));
            }
            if (var6.containsKey("biome_1")) {
                this.structureGenerators.add(new MapGenScatteredFeature((Map)var6.get("biome_1")));
            }
            if (var6.containsKey("mineshaft")) {
                this.structureGenerators.add(new MapGenMineshaft((Map)var6.get("mineshaft")));
            }
            if (var6.containsKey("stronghold")) {
                this.structureGenerators.add(new MapGenStronghold((Map)var6.get("stronghold")));
            }
        }
        this.hasDecoration = this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
        }
        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
        for (FlatLayerInfo var10 : this.flatWorldGenInfo.getFlatLayers()) {
            int var8 = var10.getMinY();
            while (var8 < var10.getMinY() + var10.getLayerCount()) {
                this.cachedBlockIDs[var8] = var10.func_151536_b();
                this.cachedBlockMetadata[var8] = (byte)var10.getFillBlockMeta();
                ++var8;
            }
        }
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    @Override
    public Chunk provideChunk(int par1, int par2) {
        int var6;
        Chunk var3 = new Chunk(this.worldObj, par1, par2);
        int var4 = 0;
        while (var4 < this.cachedBlockIDs.length) {
            Block var5 = this.cachedBlockIDs[var4];
            if (var5 != null) {
                var6 = var4 >> 4;
                ExtendedBlockStorage var7 = var3.getBlockStorageArray()[var6];
                if (var7 == null) {
                    var3.getBlockStorageArray()[var6] = var7 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
                }
                int var8 = 0;
                while (var8 < 16) {
                    int var9 = 0;
                    while (var9 < 16) {
                        var7.func_150818_a(var8, var4 & 15, var9, var5);
                        var7.setExtBlockMetadata(var8, var4 & 15, var9, this.cachedBlockMetadata[var4]);
                        ++var9;
                    }
                    ++var8;
                }
            }
            ++var4;
        }
        var3.generateSkylightMap();
        BiomeGenBase[] var10 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
        byte[] var11 = var3.getBiomeArray();
        var6 = 0;
        while (var6 < var11.length) {
            var11[var6] = (byte)var10[var6].biomeID;
            ++var6;
        }
        for (MapGenStructure var13 : this.structureGenerators) {
            var13.func_151539_a(this, this.worldObj, par1, par2, null);
        }
        var3.generateSkylightMap();
        return var3;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        int var16;
        int var18;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        boolean var7 = false;
        this.random.setSeed(this.worldObj.getSeed());
        long var8 = this.random.nextLong() / 2 * 2 + 1;
        long var10 = this.random.nextLong() / 2 * 2 + 1;
        this.random.setSeed((long)par2 * var8 + (long)par3 * var10 ^ this.worldObj.getSeed());
        for (MapGenStructure var13 : this.structureGenerators) {
            boolean var14 = var13.generateStructuresInChunk(this.worldObj, this.random, par2, par3);
            if (!(var13 instanceof MapGenVillage)) continue;
            var7 |= var14;
        }
        if (this.waterLakeGenerator != null && !var7 && this.random.nextInt(4) == 0) {
            var16 = var4 + this.random.nextInt(16) + 8;
            int var17 = this.random.nextInt(256);
            var18 = var5 + this.random.nextInt(16) + 8;
            this.waterLakeGenerator.generate(this.worldObj, this.random, var16, var17, var18);
        }
        if (this.lavaLakeGenerator != null && !var7 && this.random.nextInt(8) == 0) {
            var16 = var4 + this.random.nextInt(16) + 8;
            int var17 = this.random.nextInt(this.random.nextInt(248) + 8);
            var18 = var5 + this.random.nextInt(16) + 8;
            if (var17 < 63 || this.random.nextInt(10) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, var16, var17, var18);
            }
        }
        if (this.hasDungeons) {
            var16 = 0;
            while (var16 < 8) {
                int var17 = var4 + this.random.nextInt(16) + 8;
                var18 = this.random.nextInt(256);
                int var15 = var5 + this.random.nextInt(16) + 8;
                new WorldGenDungeons().generate(this.worldObj, this.random, var17, var18, var15);
                ++var16;
            }
        }
        if (this.hasDecoration) {
            var6.decorate(this.worldObj, this.random, var4, var5);
        }
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
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "FlatLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return var5.getSpawnableList(par1EnumCreatureType);
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        if ("Stronghold".equals(p_147416_2_)) {
            for (MapGenStructure var7 : this.structureGenerators) {
                if (!(var7 instanceof MapGenStronghold)) continue;
                return var7.func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_);
            }
        }
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int par1, int par2) {
        for (MapGenStructure var4 : this.structureGenerators) {
            var4.func_151539_a(this, this.worldObj, par1, par2, null);
        }
    }
}

