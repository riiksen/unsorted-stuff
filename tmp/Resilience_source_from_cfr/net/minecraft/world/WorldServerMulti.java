/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerMulti
extends WorldServer {
    private static final String __OBFID = "CL_00001430";

    public WorldServerMulti(MinecraftServer p_i45283_1_, ISaveHandler p_i45283_2_, String p_i45283_3_, int p_i45283_4_, WorldSettings p_i45283_5_, WorldServer p_i45283_6_, Profiler p_i45283_7_) {
        super(p_i45283_1_, p_i45283_2_, p_i45283_3_, p_i45283_4_, p_i45283_5_, p_i45283_7_);
        this.mapStorage = p_i45283_6_.mapStorage;
        this.worldScoreboard = p_i45283_6_.getScoreboard();
        this.worldInfo = new DerivedWorldInfo(p_i45283_6_.getWorldInfo());
    }

    @Override
    public void saveLevel() throws MinecraftException {
    }
}

