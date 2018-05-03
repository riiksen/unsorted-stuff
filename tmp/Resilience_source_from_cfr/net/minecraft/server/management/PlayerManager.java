/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.server.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.src.CompactArrayList;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorConstructor;
import net.minecraft.src.WorldServerOF;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class PlayerManager {
    private final WorldServer theWorldServer;
    private final List players = new ArrayList();
    private final LongHashMap playerInstances = new LongHashMap();
    private final List chunkWatcherWithPlayers = new ArrayList();
    private final List playerInstanceList = new ArrayList();
    public CompactArrayList chunkCoordsNotLoaded = new CompactArrayList(100, 0.8f);
    private int playerViewRadius;
    private long previousTotalWorldTime;
    private final int[][] xzDirectionsConst;
    private static final String __OBFID = "CL_00001434";

    public PlayerManager(WorldServer par1WorldServer, int par2) {
        int[][] arrarrn = new int[4][];
        int[] arrn = new int[2];
        arrn[0] = 1;
        arrarrn[0] = arrn;
        int[] arrn2 = new int[2];
        arrn2[1] = 1;
        arrarrn[1] = arrn2;
        int[] arrn3 = new int[2];
        arrn3[0] = -1;
        arrarrn[2] = arrn3;
        int[] arrn4 = new int[2];
        arrn4[1] = -1;
        arrarrn[3] = arrn4;
        this.xzDirectionsConst = arrarrn;
        if (par2 > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        }
        if (par2 < 3) {
            throw new IllegalArgumentException("Too small view radius!");
        }
        this.playerViewRadius = Config.getChunkViewDistance();
        Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (constructor)");
        this.theWorldServer = par1WorldServer;
    }

    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }

    public void updatePlayerInstances() {
        int var3;
        WorldProvider ip;
        PlayerInstance var4;
        long var1 = this.theWorldServer.getTotalWorldTime();
        if (var1 - this.previousTotalWorldTime > 8000) {
            this.previousTotalWorldTime = var1;
            var3 = 0;
            while (var3 < this.playerInstanceList.size()) {
                var4 = (PlayerInstance)this.playerInstanceList.get(var3);
                var4.sendChunkUpdate();
                var4.processChunk();
                ++var3;
            }
        } else {
            var3 = 0;
            while (var3 < this.chunkWatcherWithPlayers.size()) {
                var4 = (PlayerInstance)this.chunkWatcherWithPlayers.get(var3);
                var4.sendChunkUpdate();
                ++var3;
            }
        }
        this.chunkWatcherWithPlayers.clear();
        if (this.players.isEmpty() && !(ip = this.theWorldServer.provider).canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
        }
        if (this.playerViewRadius != Config.getChunkViewDistance()) {
            this.setPlayerViewRadius(Config.getChunkViewDistance());
        }
        if (this.chunkCoordsNotLoaded.size() > 0) {
            int var22 = 0;
            while (var22 < this.players.size()) {
                int maxDistSq;
                EntityPlayerMP player = (EntityPlayerMP)this.players.get(var22);
                int px = player.chunkCoordX;
                int pz = player.chunkCoordZ;
                int maxRadius = this.playerViewRadius + 1;
                int maxRadius2 = maxRadius / 2;
                int bestDistSq = maxDistSq = maxRadius * maxRadius + maxRadius2 * maxRadius2;
                int bestIndex = -1;
                PlayerInstance bestCw = null;
                ChunkCoordIntPair bestCoords = null;
                int chunk = 0;
                while (chunk < this.chunkCoordsNotLoaded.size()) {
                    ChunkCoordIntPair coords = (ChunkCoordIntPair)this.chunkCoordsNotLoaded.get(chunk);
                    if (coords != null) {
                        PlayerInstance cw = this.getOrCreateChunkWatcher(coords.chunkXPos, coords.chunkZPos, false);
                        if (cw != null && !cw.chunkLoaded) {
                            int dx = px - coords.chunkXPos;
                            int dz = pz - coords.chunkZPos;
                            int distSq = dx * dx + dz * dz;
                            if (distSq < bestDistSq) {
                                bestDistSq = distSq;
                                bestIndex = chunk;
                                bestCw = cw;
                                bestCoords = coords;
                            }
                        } else {
                            this.chunkCoordsNotLoaded.set(chunk, null);
                        }
                    }
                    ++chunk;
                }
                if (bestIndex >= 0) {
                    this.chunkCoordsNotLoaded.set(bestIndex, null);
                }
                if (bestCw != null) {
                    bestCw.chunkLoaded = true;
                    this.getWorldServer().theChunkProviderServer.loadChunk(bestCoords.chunkXPos, bestCoords.chunkZPos);
                    bestCw.sendThisChunkToAllPlayers();
                    break;
                }
                ++var22;
            }
            this.chunkCoordsNotLoaded.compact();
        }
    }

    public PlayerInstance getOrCreateChunkWatcher(int par1, int par2, boolean par3) {
        return this.getOrCreateChunkWatcher(par1, par2, par3, false);
    }

    public PlayerInstance getOrCreateChunkWatcher(int par1, int par2, boolean par3, boolean lazy) {
        long var4 = (long)par1 + Integer.MAX_VALUE | (long)par2 + Integer.MAX_VALUE << 32;
        PlayerInstance var6 = (PlayerInstance)this.playerInstances.getValueByKey(var4);
        if (var6 == null && par3) {
            var6 = new PlayerInstance(par1, par2, lazy);
            this.playerInstances.add(var4, var6);
            this.playerInstanceList.add(var6);
        }
        return var6;
    }

    public void func_151250_a(int p_151250_1_, int p_151250_2_, int p_151250_3_) {
        int var4 = p_151250_1_ >> 4;
        int var5 = p_151250_3_ >> 4;
        PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
        if (var6 != null) {
            var6.func_151253_a(p_151250_1_ & 15, p_151250_2_, p_151250_3_ & 15);
        }
    }

    public void addPlayer(EntityPlayerMP par1EntityPlayerMP) {
        int var2 = (int)par1EntityPlayerMP.posX >> 4;
        int var3 = (int)par1EntityPlayerMP.posZ >> 4;
        par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
        ArrayList<Chunk> spawnList = new ArrayList<Chunk>(1);
        int var4 = var2 - this.playerViewRadius;
        while (var4 <= var2 + this.playerViewRadius) {
            int var5 = var3 - this.playerViewRadius;
            while (var5 <= var3 + this.playerViewRadius) {
                this.getOrCreateChunkWatcher(var4, var5, true).addPlayer(par1EntityPlayerMP);
                if (var4 >= var2 - 1 && var4 <= var2 + 1 && var5 >= var3 - 1 && var5 <= var3 + 1) {
                    Chunk spawnChunk = this.getWorldServer().theChunkProviderServer.loadChunk(var4, var5);
                    spawnList.add(spawnChunk);
                }
                ++var5;
            }
            ++var4;
        }
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(spawnList));
        this.players.add(par1EntityPlayerMP);
        this.filterChunkLoadQueue(par1EntityPlayerMP);
    }

    public void filterChunkLoadQueue(EntityPlayerMP par1EntityPlayerMP) {
        ArrayList var2 = new ArrayList(par1EntityPlayerMP.loadedChunks);
        int var3 = 0;
        int var4 = this.playerViewRadius;
        int var5 = (int)par1EntityPlayerMP.posX >> 4;
        int var6 = (int)par1EntityPlayerMP.posZ >> 4;
        int var7 = 0;
        int var8 = 0;
        ChunkCoordIntPair var9 = this.getOrCreateChunkWatcher(var5, var6, true).chunkLocation;
        par1EntityPlayerMP.loadedChunks.clear();
        if (var2.contains(var9)) {
            par1EntityPlayerMP.loadedChunks.add(var9);
        }
        int var10 = 1;
        while (var10 <= var4 * 2) {
            int var11 = 0;
            while (var11 < 2) {
                int[] var12 = this.xzDirectionsConst[var3++ % 4];
                int var13 = 0;
                while (var13 < var10) {
                    var9 = this.getOrCreateChunkWatcher(var5 + (var7 += var12[0]), var6 + (var8 += var12[1]), true).chunkLocation;
                    if (var2.contains(var9)) {
                        par1EntityPlayerMP.loadedChunks.add(var9);
                    }
                    ++var13;
                }
                ++var11;
            }
            ++var10;
        }
        var3 %= 4;
        var10 = 0;
        while (var10 < var4 * 2) {
            var9 = this.getOrCreateChunkWatcher(var5 + (var7 += this.xzDirectionsConst[var3][0]), var6 + (var8 += this.xzDirectionsConst[var3][1]), true).chunkLocation;
            if (var2.contains(var9)) {
                par1EntityPlayerMP.loadedChunks.add(var9);
            }
            ++var10;
        }
    }

    public void removePlayer(EntityPlayerMP par1EntityPlayerMP) {
        int var2 = (int)par1EntityPlayerMP.managedPosX >> 4;
        int var3 = (int)par1EntityPlayerMP.managedPosZ >> 4;
        int var4 = var2 - this.playerViewRadius;
        while (var4 <= var2 + this.playerViewRadius) {
            int var5 = var3 - this.playerViewRadius;
            while (var5 <= var3 + this.playerViewRadius) {
                PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
                if (var6 != null) {
                    var6.removePlayer(par1EntityPlayerMP, false);
                }
                ++var5;
            }
            ++var4;
        }
        this.players.remove(par1EntityPlayerMP);
    }

    private boolean overlaps(int par1, int par2, int par3, int par4, int par5) {
        int var6 = par1 - par3;
        int var7 = par2 - par4;
        return var6 >= - par5 && var6 <= par5 ? var7 >= - par5 && var7 <= par5 : false;
    }

    public void updateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP) {
        int var2 = (int)par1EntityPlayerMP.posX >> 4;
        int var3 = (int)par1EntityPlayerMP.posZ >> 4;
        double var4 = par1EntityPlayerMP.managedPosX - par1EntityPlayerMP.posX;
        double var6 = par1EntityPlayerMP.managedPosZ - par1EntityPlayerMP.posZ;
        double var8 = var4 * var4 + var6 * var6;
        if (var8 >= 64.0) {
            int var10 = (int)par1EntityPlayerMP.managedPosX >> 4;
            int var11 = (int)par1EntityPlayerMP.managedPosZ >> 4;
            int var12 = this.playerViewRadius;
            int var13 = var2 - var10;
            int var14 = var3 - var11;
            if (var13 != 0 || var14 != 0) {
                WorldServerOF worldServerOf = null;
                if (this.theWorldServer instanceof WorldServerOF) {
                    worldServerOf = (WorldServerOF)this.theWorldServer;
                }
                int var15 = var2 - var12;
                while (var15 <= var2 + var12) {
                    int var16 = var3 - var12;
                    while (var16 <= var3 + var12) {
                        PlayerInstance var17;
                        if (!this.overlaps(var15, var16, var10, var11, var12)) {
                            this.getOrCreateChunkWatcher(var15, var16, true, true).addPlayer(par1EntityPlayerMP);
                            if (worldServerOf != null) {
                                worldServerOf.addChunkToTickOnce(var15, var16);
                            }
                        }
                        if (!this.overlaps(var15 - var13, var16 - var14, var2, var3, var12) && (var17 = this.getOrCreateChunkWatcher(var15 - var13, var16 - var14, false)) != null) {
                            var17.removePlayer(par1EntityPlayerMP);
                        }
                        ++var16;
                    }
                    ++var15;
                }
                this.filterChunkLoadQueue(par1EntityPlayerMP);
                par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
                par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
            }
        }
    }

    public boolean isPlayerWatchingChunk(EntityPlayerMP par1EntityPlayerMP, int par2, int par3) {
        PlayerInstance var4 = this.getOrCreateChunkWatcher(par2, par3, false);
        return var4 == null ? false : var4.playersWatchingChunk.contains(par1EntityPlayerMP) && !par1EntityPlayerMP.loadedChunks.contains(var4.chunkLocation);
    }

    public static int getFurthestViewableBlock(int par0) {
        return par0 * 16 - 16;
    }

    private void setPlayerViewRadius(int newRadius) {
        if (this.playerViewRadius != newRadius) {
            EntityPlayerMP ep;
            EntityPlayerMP[] eps = this.players.toArray(new EntityPlayerMP[this.players.size()]);
            int i = 0;
            while (i < eps.length) {
                ep = eps[i];
                this.removePlayer(ep);
                ++i;
            }
            this.playerViewRadius = newRadius;
            i = 0;
            while (i < eps.length) {
                ep = eps[i];
                this.addPlayer(ep);
                ++i;
            }
            Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (detect)");
        }
    }

    public class PlayerInstance {
        private final List playersWatchingChunk;
        private final ChunkCoordIntPair chunkLocation;
        private short[] field_151254_d;
        private int numberOfTilesToUpdate;
        private int flagsYAreasToUpdate;
        private long previousWorldTime;
        public boolean chunkLoaded;
        private static final String __OBFID = "CL_00001435";

        public PlayerInstance(int par2, int par3) {
            this(par2, par3, false);
        }

        public PlayerInstance(int par2, int par3, boolean lazy) {
            boolean useLazy;
            this.playersWatchingChunk = new ArrayList();
            this.field_151254_d = new short[64];
            this.chunkLoaded = false;
            this.chunkLocation = new ChunkCoordIntPair(par2, par3);
            boolean bl = useLazy = lazy && Config.isLazyChunkLoading();
            if (useLazy && !PlayerManager.this.getWorldServer().theChunkProviderServer.chunkExists(par2, par3)) {
                PlayerManager.this.chunkCoordsNotLoaded.add(this.chunkLocation);
                this.chunkLoaded = false;
            } else {
                PlayerManager.this.getWorldServer().theChunkProviderServer.loadChunk(par2, par3);
                this.chunkLoaded = true;
            }
        }

        public void addPlayer(EntityPlayerMP par1EntityPlayerMP) {
            if (this.playersWatchingChunk.contains(par1EntityPlayerMP)) {
                throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + this.chunkLocation.chunkXPos + ", " + this.chunkLocation.chunkZPos);
            }
            if (this.playersWatchingChunk.isEmpty()) {
                this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
            }
            this.playersWatchingChunk.add(par1EntityPlayerMP);
            par1EntityPlayerMP.loadedChunks.add(this.chunkLocation);
        }

        public void removePlayer(EntityPlayerMP par1EntityPlayerMP) {
            this.removePlayer(par1EntityPlayerMP, true);
        }

        public void removePlayer(EntityPlayerMP par1EntityPlayerMP, boolean sendData) {
            if (this.playersWatchingChunk.contains(par1EntityPlayerMP)) {
                Chunk var2 = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                if (sendData && var2.func_150802_k()) {
                    par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
                }
                this.playersWatchingChunk.remove(par1EntityPlayerMP);
                par1EntityPlayerMP.loadedChunks.remove(this.chunkLocation);
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.ChunkWatchEvent_UnWatch_Constructor, this.chunkLocation, par1EntityPlayerMP);
                }
                if (this.playersWatchingChunk.isEmpty()) {
                    long var3 = (long)this.chunkLocation.chunkXPos + Integer.MAX_VALUE | (long)this.chunkLocation.chunkZPos + Integer.MAX_VALUE << 32;
                    this.increaseInhabitedTime(var2);
                    PlayerManager.this.playerInstances.remove(var3);
                    PlayerManager.this.playerInstanceList.remove(this);
                    if (this.numberOfTilesToUpdate > 0) {
                        PlayerManager.this.chunkWatcherWithPlayers.remove(this);
                    }
                    if (this.chunkLoaded) {
                        PlayerManager.this.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                    }
                }
            }
        }

        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos));
        }

        private void increaseInhabitedTime(Chunk par1Chunk) {
            par1Chunk.inhabitedTime += PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime;
            this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
        }

        public void func_151253_a(int p_151253_1_, int p_151253_2_, int p_151253_3_) {
            if (this.numberOfTilesToUpdate == 0) {
                PlayerManager.this.chunkWatcherWithPlayers.add(this);
            }
            this.flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);
            if (this.numberOfTilesToUpdate < 64) {
                short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
                int var5 = 0;
                while (var5 < this.numberOfTilesToUpdate) {
                    if (this.field_151254_d[var5] == var4) {
                        return;
                    }
                    ++var5;
                }
                this.field_151254_d[this.numberOfTilesToUpdate++] = var4;
            }
        }

        public void func_151251_a(Packet p_151251_1_) {
            int var2 = 0;
            while (var2 < this.playersWatchingChunk.size()) {
                EntityPlayerMP var3 = (EntityPlayerMP)this.playersWatchingChunk.get(var2);
                if (!var3.loadedChunks.contains(this.chunkLocation)) {
                    var3.playerNetServerHandler.sendPacket(p_151251_1_);
                }
                ++var2;
            }
        }

        public void sendChunkUpdate() {
            if (this.numberOfTilesToUpdate != 0) {
                if (this.numberOfTilesToUpdate == 1) {
                    int var1 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[0] >> 12 & 15);
                    int var2 = this.field_151254_d[0] & 255;
                    int var3 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[0] >> 8 & 15);
                    this.func_151251_a(new S23PacketBlockChange(var1, var2, var3, PlayerManager.this.theWorldServer));
                    if (PlayerManager.this.theWorldServer.getBlock(var1, var2, var3).hasTileEntity()) {
                        this.func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var1, var2, var3));
                    }
                } else if (this.numberOfTilesToUpdate == 64) {
                    int var1 = this.chunkLocation.chunkXPos * 16;
                    int var2 = this.chunkLocation.chunkZPos * 16;
                    this.func_151251_a(new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), false, this.flagsYAreasToUpdate));
                    int var3 = 0;
                    while (var3 < 16) {
                        if ((this.flagsYAreasToUpdate & 1 << var3) != 0) {
                            int var4 = var3 << 4;
                            List var5 = PlayerManager.this.theWorldServer.func_147486_a(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);
                            int var6 = 0;
                            while (var6 < var5.size()) {
                                this.func_151252_a((TileEntity)var5.get(var6));
                                ++var6;
                            }
                        }
                        ++var3;
                    }
                } else {
                    this.func_151251_a(new S22PacketMultiBlockChange(this.numberOfTilesToUpdate, this.field_151254_d, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos)));
                    int var1 = 0;
                    while (var1 < this.numberOfTilesToUpdate) {
                        int var2 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[var1] >> 12 & 15);
                        int var3 = this.field_151254_d[var1] & 255;
                        int var4 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[var1] >> 8 & 15);
                        if (PlayerManager.this.theWorldServer.getBlock(var2, var3, var4).hasTileEntity()) {
                            this.func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var2, var3, var4));
                        }
                        ++var1;
                    }
                }
                this.numberOfTilesToUpdate = 0;
                this.flagsYAreasToUpdate = 0;
            }
        }

        private void func_151252_a(TileEntity p_151252_1_) {
            Packet var2;
            if (p_151252_1_ != null && (var2 = p_151252_1_.getDescriptionPacket()) != null) {
                this.func_151251_a(var2);
            }
        }

        public void sendThisChunkToAllPlayers() {
            int i = 0;
            while (i < this.playersWatchingChunk.size()) {
                EntityPlayerMP player = (EntityPlayerMP)this.playersWatchingChunk.get(i);
                Chunk chunk = PlayerManager.this.getWorldServer().getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                ArrayList<Chunk> list = new ArrayList<Chunk>(1);
                list.add(chunk);
                player.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(list));
                ++i;
            }
        }
    }

}

