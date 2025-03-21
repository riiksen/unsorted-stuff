/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.NibbleArrayReader;

public class ChunkLoader {
    private static final String __OBFID = "CL_00000379";

    public static AnvilConverterData load(NBTTagCompound par0NBTTagCompound) {
        int var1 = par0NBTTagCompound.getInteger("xPos");
        int var2 = par0NBTTagCompound.getInteger("zPos");
        AnvilConverterData var3 = new AnvilConverterData(var1, var2);
        var3.blocks = par0NBTTagCompound.getByteArray("Blocks");
        var3.data = new NibbleArrayReader(par0NBTTagCompound.getByteArray("Data"), 7);
        var3.skyLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("SkyLight"), 7);
        var3.blockLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("BlockLight"), 7);
        var3.heightmap = par0NBTTagCompound.getByteArray("HeightMap");
        var3.terrainPopulated = par0NBTTagCompound.getBoolean("TerrainPopulated");
        var3.entities = par0NBTTagCompound.getTagList("Entities", 10);
        var3.field_151564_i = par0NBTTagCompound.getTagList("TileEntities", 10);
        var3.field_151563_j = par0NBTTagCompound.getTagList("TileTicks", 10);
        try {
            var3.lastUpdated = par0NBTTagCompound.getLong("LastUpdate");
        }
        catch (ClassCastException var5) {
            var3.lastUpdated = par0NBTTagCompound.getInteger("LastUpdate");
        }
        return var3;
    }

    public static void convertToAnvilFormat(AnvilConverterData par0AnvilConverterData, NBTTagCompound par1NBTTagCompound, WorldChunkManager par2WorldChunkManager) {
        int var7;
        par1NBTTagCompound.setInteger("xPos", par0AnvilConverterData.x);
        par1NBTTagCompound.setInteger("zPos", par0AnvilConverterData.z);
        par1NBTTagCompound.setLong("LastUpdate", par0AnvilConverterData.lastUpdated);
        int[] var3 = new int[par0AnvilConverterData.heightmap.length];
        int var4 = 0;
        while (var4 < par0AnvilConverterData.heightmap.length) {
            var3[var4] = par0AnvilConverterData.heightmap[var4];
            ++var4;
        }
        par1NBTTagCompound.setIntArray("HeightMap", var3);
        par1NBTTagCompound.setBoolean("TerrainPopulated", par0AnvilConverterData.terrainPopulated);
        NBTTagList var16 = new NBTTagList();
        int var5 = 0;
        while (var5 < 8) {
            boolean var6 = true;
            var7 = 0;
            while (var7 < 16 && var6) {
                int var8 = 0;
                while (var8 < 16 && var6) {
                    for (int var9 = 0; var9 < 16; ++var9) {
                        int var10 = var7 << 11 | var9 << 7 | var8 + (var5 << 4);
                        byte var11 = par0AnvilConverterData.blocks[var10];
                        if (var11 == 0) {
                            continue;
                        }
                        var6 = false;
                        break;
                    }
                    ++var8;
                }
                ++var7;
            }
            if (!var6) {
                byte[] var19 = new byte[4096];
                NibbleArray var20 = new NibbleArray(var19.length, 4);
                NibbleArray var21 = new NibbleArray(var19.length, 4);
                NibbleArray var23 = new NibbleArray(var19.length, 4);
                int var22 = 0;
                while (var22 < 16) {
                    int var12 = 0;
                    while (var12 < 16) {
                        int var13 = 0;
                        while (var13 < 16) {
                            int var14 = var22 << 11 | var13 << 7 | var12 + (var5 << 4);
                            byte var15 = par0AnvilConverterData.blocks[var14];
                            var19[var12 << 8 | var13 << 4 | var22] = (byte)(var15 & 255);
                            var20.set(var22, var12, var13, par0AnvilConverterData.data.get(var22, var12 + (var5 << 4), var13));
                            var21.set(var22, var12, var13, par0AnvilConverterData.skyLight.get(var22, var12 + (var5 << 4), var13));
                            var23.set(var22, var12, var13, par0AnvilConverterData.blockLight.get(var22, var12 + (var5 << 4), var13));
                            ++var13;
                        }
                        ++var12;
                    }
                    ++var22;
                }
                NBTTagCompound var24 = new NBTTagCompound();
                var24.setByte("Y", (byte)(var5 & 255));
                var24.setByteArray("Blocks", var19);
                var24.setByteArray("Data", var20.data);
                var24.setByteArray("SkyLight", var21.data);
                var24.setByteArray("BlockLight", var23.data);
                var16.appendTag(var24);
            }
            ++var5;
        }
        par1NBTTagCompound.setTag("Sections", var16);
        byte[] var17 = new byte[256];
        int var18 = 0;
        while (var18 < 16) {
            var7 = 0;
            while (var7 < 16) {
                var17[var7 << 4 | var18] = (byte)(par2WorldChunkManager.getBiomeGenAt((int)(par0AnvilConverterData.x << 4 | var18), (int)(par0AnvilConverterData.z << 4 | var7)).biomeID & 255);
                ++var7;
            }
            ++var18;
        }
        par1NBTTagCompound.setByteArray("Biomes", var17);
        par1NBTTagCompound.setTag("Entities", par0AnvilConverterData.entities);
        par1NBTTagCompound.setTag("TileEntities", par0AnvilConverterData.field_151564_i);
        if (par0AnvilConverterData.field_151563_j != null) {
            par1NBTTagCompound.setTag("TileTicks", par0AnvilConverterData.field_151563_j);
        }
    }

    public static class AnvilConverterData {
        public long lastUpdated;
        public boolean terrainPopulated;
        public byte[] heightmap;
        public NibbleArrayReader blockLight;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader data;
        public byte[] blocks;
        public NBTTagList entities;
        public NBTTagList field_151564_i;
        public NBTTagList field_151563_j;
        public final int x;
        public final int z;
        private static final String __OBFID = "CL_00000380";

        public AnvilConverterData(int par1, int par2) {
            this.x = par1;
            this.z = par2;
        }
    }

}

