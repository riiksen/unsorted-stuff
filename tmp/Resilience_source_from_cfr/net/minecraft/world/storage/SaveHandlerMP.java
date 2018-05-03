/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class SaveHandlerMP
implements ISaveHandler {
    private static final String __OBFID = "CL_00000602";

    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }

    @Override
    public void checkSessionLock() throws MinecraftException {
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider) {
        return null;
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {
    }

    @Override
    public void saveWorldInfo(WorldInfo par1WorldInfo) {
    }

    @Override
    public IPlayerFileData getSaveHandler() {
        return null;
    }

    @Override
    public void flush() {
    }

    @Override
    public File getMapFileFromName(String par1Str) {
        return null;
    }

    @Override
    public String getWorldDirectoryName() {
        return "none";
    }

    @Override
    public File getWorldDirectory() {
        return null;
    }
}

