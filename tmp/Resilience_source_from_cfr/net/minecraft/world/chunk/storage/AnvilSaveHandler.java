/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.chunk.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldInfo;

public class AnvilSaveHandler
extends SaveHandler {
    private static final String __OBFID = "CL_00000581";

    public AnvilSaveHandler(File par1File, String par2Str, boolean par3) {
        super(par1File, par2Str, par3);
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider) {
        File var2 = this.getWorldDirectory();
        if (par1WorldProvider instanceof WorldProviderHell) {
            File var3 = new File(var2, "DIM-1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        if (par1WorldProvider instanceof WorldProviderEnd) {
            File var3 = new File(var2, "DIM1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        return new AnvilChunkLoader(var2);
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {
        par1WorldInfo.setSaveVersion(19133);
        super.saveWorldInfoWithPlayer(par1WorldInfo, par2NBTTagCompound);
    }

    @Override
    public void flush() {
        try {
            ThreadedFileIOBase.threadedIOInstance.waitForFinish();
        }
        catch (InterruptedException var2) {
            var2.printStackTrace();
        }
        RegionFileCache.clearRegionFileReferences();
    }
}

