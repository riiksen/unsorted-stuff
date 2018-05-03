/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter
extends SaveFormatOld {
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00000582";

    public AnvilSaveConverter(File par1File) {
        super(par1File);
    }

    @Override
    public List getSaveList() throws AnvilConverterException {
        if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
            File[] var2;
            ArrayList<SaveFormatComparator> var1 = new ArrayList<SaveFormatComparator>();
            File[] var3 = var2 = this.savesDirectory.listFiles();
            int var4 = var2.length;
            int var5 = 0;
            while (var5 < var4) {
                WorldInfo var8;
                String var7;
                File var6 = var3[var5];
                if (var6.isDirectory() && (var8 = this.getWorldInfo(var7 = var6.getName())) != null && (var8.getSaveVersion() == 19132 || var8.getSaveVersion() == 19133)) {
                    boolean var9 = var8.getSaveVersion() != this.getSaveVersion();
                    String var10 = var8.getWorldName();
                    if (var10 == null || MathHelper.stringNullOrLengthZero(var10)) {
                        var10 = var7;
                    }
                    long var11 = 0;
                    var1.add(new SaveFormatComparator(var7, var10, var8.getLastTimePlayed(), var11, var8.getGameType(), var9, var8.isHardcoreModeEnabled(), var8.areCommandsAllowed()));
                }
                ++var5;
            }
            return var1;
        }
        throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
    }

    protected int getSaveVersion() {
        return 19133;
    }

    @Override
    public void flushCache() {
        RegionFileCache.clearRegionFileReferences();
    }

    @Override
    public ISaveHandler getSaveLoader(String par1Str, boolean par2) {
        return new AnvilSaveHandler(this.savesDirectory, par1Str, par2);
    }

    @Override
    public boolean isOldMapFormat(String par1Str) {
        WorldInfo var2 = this.getWorldInfo(par1Str);
        if (var2 != null && var2.getSaveVersion() != this.getSaveVersion()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate) {
        par2IProgressUpdate.setLoadingProgress(0);
        ArrayList var3 = new ArrayList();
        ArrayList var4 = new ArrayList();
        ArrayList var5 = new ArrayList();
        File var6 = new File(this.savesDirectory, par1Str);
        File var7 = new File(var6, "DIM-1");
        File var8 = new File(var6, "DIM1");
        logger.info("Scanning folders...");
        this.addRegionFilesToCollection(var6, var3);
        if (var7.exists()) {
            this.addRegionFilesToCollection(var7, var4);
        }
        if (var8.exists()) {
            this.addRegionFilesToCollection(var8, var5);
        }
        int var9 = var3.size() + var4.size() + var5.size();
        logger.info("Total conversion count is " + var9);
        WorldInfo var10 = this.getWorldInfo(par1Str);
        WorldChunkManager var11 = null;
        var11 = var10.getTerrainType() == WorldType.FLAT ? new WorldChunkManagerHell(BiomeGenBase.plains, 0.5f) : new WorldChunkManager(var10.getSeed(), var10.getTerrainType());
        this.convertFile(new File(var6, "region"), var3, var11, 0, var9, par2IProgressUpdate);
        this.convertFile(new File(var7, "region"), var4, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f), var3.size(), var9, par2IProgressUpdate);
        this.convertFile(new File(var8, "region"), var5, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f), var3.size() + var4.size(), var9, par2IProgressUpdate);
        var10.setSaveVersion(19133);
        if (var10.getTerrainType() == WorldType.DEFAULT_1_1) {
            var10.setTerrainType(WorldType.DEFAULT);
        }
        this.createFile(par1Str);
        ISaveHandler var12 = this.getSaveLoader(par1Str, false);
        var12.saveWorldInfo(var10);
        return true;
    }

    private void createFile(String par1Str) {
        File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            logger.warn("Unable to create level.dat_mcr backup");
        } else {
            File var3 = new File(var2, "level.dat");
            if (!var3.exists()) {
                logger.warn("Unable to create level.dat_mcr backup");
            } else {
                File var4 = new File(var2, "level.dat_mcr");
                if (!var3.renameTo(var4)) {
                    logger.warn("Unable to create level.dat_mcr backup");
                }
            }
        }
    }

    private void convertFile(File par1File, Iterable par2Iterable, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate) {
        for (File var8 : par2Iterable) {
            this.convertChunks(par1File, var8, par3WorldChunkManager, par4, par5, par6IProgressUpdate);
            int var9 = (int)Math.round(100.0 * (double)(++par4) / (double)par5);
            par6IProgressUpdate.setLoadingProgress(var9);
        }
    }

    private void convertChunks(File par1File, File par2File, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate) {
        try {
            String var7 = par2File.getName();
            RegionFile var8 = new RegionFile(par2File);
            RegionFile var9 = new RegionFile(new File(par1File, String.valueOf(var7.substring(0, var7.length() - ".mcr".length())) + ".mca"));
            int var10 = 0;
            while (var10 < 32) {
                int var11 = 0;
                while (var11 < 32) {
                    if (var8.isChunkSaved(var10, var11) && !var9.isChunkSaved(var10, var11)) {
                        DataInputStream var12 = var8.getChunkDataInputStream(var10, var11);
                        if (var12 == null) {
                            logger.warn("Failed to fetch input stream");
                        } else {
                            NBTTagCompound var13 = CompressedStreamTools.read(var12);
                            var12.close();
                            NBTTagCompound var14 = var13.getCompoundTag("Level");
                            ChunkLoader.AnvilConverterData var15 = ChunkLoader.load(var14);
                            NBTTagCompound var16 = new NBTTagCompound();
                            NBTTagCompound var17 = new NBTTagCompound();
                            var16.setTag("Level", var17);
                            ChunkLoader.convertToAnvilFormat(var15, var17, par3WorldChunkManager);
                            DataOutputStream var18 = var9.getChunkDataOutputStream(var10, var11);
                            CompressedStreamTools.write(var16, var18);
                            var18.close();
                        }
                    }
                    ++var11;
                }
                var11 = (int)Math.round(100.0 * (double)(par4 * 1024) / (double)(par5 * 1024));
                int var20 = (int)Math.round(100.0 * (double)((var10 + 1) * 32 + par4 * 1024) / (double)(par5 * 1024));
                if (var20 > var11) {
                    par6IProgressUpdate.setLoadingProgress(var20);
                }
                ++var10;
            }
            var8.close();
            var9.close();
        }
        catch (IOException var19) {
            var19.printStackTrace();
        }
    }

    private void addRegionFilesToCollection(File par1File, Collection par2Collection) {
        File var3 = new File(par1File, "region");
        File[] var4 = var3.listFiles(new FilenameFilter(){
            private static final String __OBFID = "CL_00000583";

            @Override
            public boolean accept(File par1File, String par2Str) {
                return par2Str.endsWith(".mcr");
            }
        });
        if (var4 != null) {
            Collections.addAll(par2Collection, var4);
        }
    }

}

