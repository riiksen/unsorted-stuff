/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.chunk.storage.RegionFile;

public class RegionFileCache {
    private static final Map regionsByFilename = new HashMap();
    private static final String __OBFID = "CL_00000383";

    public static synchronized RegionFile createOrLoadRegionFile(File par0File, int par1, int par2) {
        File var3 = new File(par0File, "region");
        File var4 = new File(var3, "r." + (par1 >> 5) + "." + (par2 >> 5) + ".mca");
        RegionFile var5 = (RegionFile)regionsByFilename.get(var4);
        if (var5 != null) {
            return var5;
        }
        if (!var3.exists()) {
            var3.mkdirs();
        }
        if (regionsByFilename.size() >= 256) {
            RegionFileCache.clearRegionFileReferences();
        }
        RegionFile var6 = new RegionFile(var4);
        regionsByFilename.put(var4, var6);
        return var6;
    }

    public static synchronized void clearRegionFileReferences() {
        for (RegionFile var1 : regionsByFilename.values()) {
            try {
                if (var1 == null) continue;
                var1.close();
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        regionsByFilename.clear();
    }

    public static DataInputStream getChunkInputStream(File par0File, int par1, int par2) {
        RegionFile var3 = RegionFileCache.createOrLoadRegionFile(par0File, par1, par2);
        return var3.getChunkDataInputStream(par1 & 31, par2 & 31);
    }

    public static DataOutputStream getChunkOutputStream(File par0File, int par1, int par2) {
        RegionFile var3 = RegionFileCache.createOrLoadRegionFile(par0File, par1, par2);
        return var3.getChunkDataOutputStream(par1 & 31, par2 & 31);
    }
}

