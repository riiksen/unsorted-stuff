/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public interface ISaveFormat {
    public ISaveHandler getSaveLoader(String var1, boolean var2);

    public List getSaveList() throws AnvilConverterException;

    public void flushCache();

    public WorldInfo getWorldInfo(String var1);

    public boolean deleteWorldDirectory(String var1);

    public void renameWorld(String var1, String var2);

    public boolean isOldMapFormat(String var1);

    public boolean convertMapFormat(String var1, IProgressUpdate var2);

    public boolean canLoadWorld(String var1);
}

