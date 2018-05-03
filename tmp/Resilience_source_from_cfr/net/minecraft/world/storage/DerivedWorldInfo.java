/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

public class DerivedWorldInfo
extends WorldInfo {
    private final WorldInfo theWorldInfo;
    private static final String __OBFID = "CL_00000584";

    public DerivedWorldInfo(WorldInfo par1WorldInfo) {
        this.theWorldInfo = par1WorldInfo;
    }

    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }

    @Override
    public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound) {
        return this.theWorldInfo.cloneNBTCompound(par1NBTTagCompound);
    }

    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }

    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }

    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }

    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }

    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }

    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }

    @Override
    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }

    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }

    @Override
    public int getVanillaDimension() {
        return this.theWorldInfo.getVanillaDimension();
    }

    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }

    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }

    @Override
    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }

    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }

    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }

    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }

    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }

    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldInfo.getGameType();
    }

    @Override
    public void setSpawnX(int par1) {
    }

    @Override
    public void setSpawnY(int par1) {
    }

    @Override
    public void setSpawnZ(int par1) {
    }

    @Override
    public void incrementTotalWorldTime(long par1) {
    }

    @Override
    public void setWorldTime(long par1) {
    }

    @Override
    public void setSpawnPosition(int par1, int par2, int par3) {
    }

    @Override
    public void setWorldName(String par1Str) {
    }

    @Override
    public void setSaveVersion(int par1) {
    }

    @Override
    public void setThundering(boolean par1) {
    }

    @Override
    public void setThunderTime(int par1) {
    }

    @Override
    public void setRaining(boolean par1) {
    }

    @Override
    public void setRainTime(int par1) {
    }

    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }

    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }

    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }

    @Override
    public void setTerrainType(WorldType par1WorldType) {
    }

    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }

    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }

    @Override
    public void setServerInitialized(boolean par1) {
    }

    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
}

