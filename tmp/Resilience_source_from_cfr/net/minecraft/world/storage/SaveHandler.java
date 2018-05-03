/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveHandler
implements ISaveHandler,
IPlayerFileData {
    private static final Logger logger = LogManager.getLogger();
    private final File worldDirectory;
    private final File playersDirectory;
    private final File mapDataDir;
    private final long initializationTime = MinecraftServer.getSystemTimeMillis();
    private final String saveDirectoryName;
    private static final String __OBFID = "CL_00000585";

    public SaveHandler(File par1File, String par2Str, boolean par3) {
        this.worldDirectory = new File(par1File, par2Str);
        this.worldDirectory.mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "players");
        this.mapDataDir = new File(this.worldDirectory, "data");
        this.mapDataDir.mkdirs();
        this.saveDirectoryName = par2Str;
        if (par3) {
            this.playersDirectory.mkdirs();
        }
        this.setSessionLock();
    }

    private void setSessionLock() {
        try {
            File var1 = new File(this.worldDirectory, "session.lock");
            DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
            try {
                var2.writeLong(this.initializationTime);
            }
            finally {
                var2.close();
            }
        }
        catch (IOException var7) {
            var7.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    @Override
    public File getWorldDirectory() {
        return this.worldDirectory;
    }

    @Override
    public void checkSessionLock() throws MinecraftException {
        try {
            File var1 = new File(this.worldDirectory, "session.lock");
            DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
            try {
                if (var2.readLong() != this.initializationTime) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally {
                var2.close();
            }
        }
        catch (IOException var7) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    @Override
    public WorldInfo loadWorldInfo() {
        File var1 = new File(this.worldDirectory, "level.dat");
        if (var1.exists()) {
            try {
                NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                NBTTagCompound var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        if ((var1 = new File(this.worldDirectory, "level.dat_old")).exists()) {
            try {
                NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                NBTTagCompound var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {
        NBTTagCompound var3 = par1WorldInfo.cloneNBTCompound(par2NBTTagCompound);
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setTag("Data", var3);
        try {
            File var5 = new File(this.worldDirectory, "level.dat_new");
            File var6 = new File(this.worldDirectory, "level.dat_old");
            File var7 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var4, new FileOutputStream(var5));
            if (var6.exists()) {
                var6.delete();
            }
            var7.renameTo(var6);
            if (var7.exists()) {
                var7.delete();
            }
            var5.renameTo(var7);
            if (var5.exists()) {
                var5.delete();
            }
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    @Override
    public void saveWorldInfo(WorldInfo par1WorldInfo) {
        System.out.println("So I'm being called, but...");
        NBTTagCompound var2 = par1WorldInfo.getNBTTagCompound();
        NBTTagCompound var3 = new NBTTagCompound();
        var3.setTag("Data", var2);
        try {
            File var4 = new File(this.worldDirectory, "level.dat_new");
            File var5 = new File(this.worldDirectory, "level.dat_old");
            File var6 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var3, new FileOutputStream(var4));
            if (var5.exists()) {
                var5.delete();
            }
            var6.renameTo(var5);
            if (var6.exists()) {
                var6.delete();
            }
            var4.renameTo(var6);
            if (var4.exists()) {
                var4.delete();
            }
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    @Override
    public void writePlayerData(EntityPlayer par1EntityPlayer) {
        try {
            NBTTagCompound var2 = new NBTTagCompound();
            par1EntityPlayer.writeToNBT(var2);
            File var3 = new File(this.playersDirectory, String.valueOf(par1EntityPlayer.getCommandSenderName()) + ".dat.tmp");
            File var4 = new File(this.playersDirectory, String.valueOf(par1EntityPlayer.getCommandSenderName()) + ".dat");
            CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));
            if (var4.exists()) {
                var4.delete();
            }
            var3.renameTo(var4);
        }
        catch (Exception var5) {
            logger.warn("Failed to save player data for " + par1EntityPlayer.getCommandSenderName());
        }
    }

    @Override
    public NBTTagCompound readPlayerData(EntityPlayer par1EntityPlayer) {
        NBTTagCompound var2 = this.getPlayerData(par1EntityPlayer.getCommandSenderName());
        if (var2 != null) {
            par1EntityPlayer.readFromNBT(var2);
        }
        return var2;
    }

    public NBTTagCompound getPlayerData(String par1Str) {
        try {
            File var2 = new File(this.playersDirectory, String.valueOf(par1Str) + ".dat");
            if (var2.exists()) {
                return CompressedStreamTools.readCompressed(new FileInputStream(var2));
            }
        }
        catch (Exception var3) {
            logger.warn("Failed to load player data for " + par1Str);
        }
        return null;
    }

    @Override
    public IPlayerFileData getSaveHandler() {
        return this;
    }

    @Override
    public String[] getAvailablePlayerDat() {
        String[] var1 = this.playersDirectory.list();
        int var2 = 0;
        while (var2 < var1.length) {
            if (var1[var2].endsWith(".dat")) {
                var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
            }
            ++var2;
        }
        return var1;
    }

    @Override
    public void flush() {
    }

    @Override
    public File getMapFileFromName(String par1Str) {
        return new File(this.mapDataDir, String.valueOf(par1Str) + ".dat");
    }

    @Override
    public String getWorldDirectoryName() {
        return this.saveDirectoryName;
    }
}

