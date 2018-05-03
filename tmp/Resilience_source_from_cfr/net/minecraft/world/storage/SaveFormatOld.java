/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld
implements ISaveFormat {
    private static final Logger logger = LogManager.getLogger();
    protected final File savesDirectory;
    private static final String __OBFID = "CL_00000586";

    public SaveFormatOld(File par1File) {
        if (!par1File.exists()) {
            par1File.mkdirs();
        }
        this.savesDirectory = par1File;
    }

    @Override
    public List getSaveList() throws AnvilConverterException {
        ArrayList<SaveFormatComparator> var1 = new ArrayList<SaveFormatComparator>();
        int var2 = 0;
        while (var2 < 5) {
            String var3 = "World" + (var2 + 1);
            WorldInfo var4 = this.getWorldInfo(var3);
            if (var4 != null) {
                var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
            }
            ++var2;
        }
        return var1;
    }

    @Override
    public void flushCache() {
    }

    @Override
    public WorldInfo getWorldInfo(String par1Str) {
        File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            return null;
        }
        File var3 = new File(var2, "level.dat");
        if (var3.exists()) {
            try {
                NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                NBTTagCompound var5 = var4.getCompoundTag("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var7) {
                logger.error("Exception reading " + var3, (Throwable)var7);
            }
        }
        if ((var3 = new File(var2, "level.dat_old")).exists()) {
            try {
                NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                NBTTagCompound var5 = var4.getCompoundTag("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var6) {
                logger.error("Exception reading " + var3, (Throwable)var6);
            }
        }
        return null;
    }

    @Override
    public void renameWorld(String par1Str, String par2Str) {
        File var4;
        File var3 = new File(this.savesDirectory, par1Str);
        if (var3.exists() && (var4 = new File(var3, "level.dat")).exists()) {
            try {
                NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
                NBTTagCompound var6 = var5.getCompoundTag("Data");
                var6.setString("LevelName", par2Str);
                CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
            }
            catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteWorldDirectory(String par1Str) {
        File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            return true;
        }
        logger.info("Deleting level " + par1Str);
        int var3 = 1;
        while (var3 <= 5) {
            logger.info("Attempt " + var3 + "...");
            if (SaveFormatOld.deleteFiles(var2.listFiles())) break;
            logger.warn("Unsuccessful in deleting contents.");
            if (var3 < 5) {
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            ++var3;
        }
        return var2.delete();
    }

    protected static boolean deleteFiles(File[] par0ArrayOfFile) {
        int var1 = 0;
        while (var1 < par0ArrayOfFile.length) {
            File var2 = par0ArrayOfFile[var1];
            logger.debug("Deleting " + var2);
            if (var2.isDirectory() && !SaveFormatOld.deleteFiles(var2.listFiles())) {
                logger.warn("Couldn't delete directory " + var2);
                return false;
            }
            if (!var2.delete()) {
                logger.warn("Couldn't delete file " + var2);
                return false;
            }
            ++var1;
        }
        return true;
    }

    @Override
    public ISaveHandler getSaveLoader(String par1Str, boolean par2) {
        return new SaveHandler(this.savesDirectory, par1Str, par2);
    }

    @Override
    public boolean isOldMapFormat(String par1Str) {
        return false;
    }

    @Override
    public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate) {
        return false;
    }

    @Override
    public boolean canLoadWorld(String par1Str) {
        File var2 = new File(this.savesDirectory, par1Str);
        return var2.isDirectory();
    }
}

