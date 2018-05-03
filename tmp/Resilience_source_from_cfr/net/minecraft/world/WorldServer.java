/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer
extends World {
    private static final Logger logger = LogManager.getLogger();
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private Set pendingTickListEntriesHashSet;
    private TreeSet pendingTickListEntriesTreeSet;
    public ChunkProviderServer theChunkProviderServer;
    public boolean levelSaving;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final SpawnerAnimals animalSpawner = new SpawnerAnimals();
    private ServerBlockEventList[] field_147490_S = new ServerBlockEventList[]{new ServerBlockEventList(null), new ServerBlockEventList(null)};
    private int field_147489_T;
    private static final WeightedRandomChestContent[] bonusChestContent = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10)};
    private List pendingTickListEntriesThisTick = new ArrayList();
    private IntHashMap entityIdMap;
    private static final String __OBFID = "CL_00001437";

    public WorldServer(MinecraftServer p_i45284_1_, ISaveHandler p_i45284_2_, String p_i45284_3_, int p_i45284_4_, WorldSettings p_i45284_5_, Profiler p_i45284_6_) {
        super(p_i45284_2_, p_i45284_3_, p_i45284_5_, WorldProvider.getProviderForDimension(p_i45284_4_), p_i45284_6_);
        this.mcServer = p_i45284_1_;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this, p_i45284_1_.getConfigurationManager().getViewDistance());
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.pendingTickListEntriesHashSet == null) {
            this.pendingTickListEntriesHashSet = new HashSet();
        }
        if (this.pendingTickListEntriesTreeSet == null) {
            this.pendingTickListEntriesTreeSet = new TreeSet();
        }
        this.worldTeleporter = new Teleporter(this);
        this.worldScoreboard = new ServerScoreboard(p_i45284_1_);
        ScoreboardSaveData var7 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (var7 == null) {
            var7 = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", var7);
        }
        var7.func_96499_a(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(var7);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.difficultySetting != EnumDifficulty.HARD) {
            this.difficultySetting = EnumDifficulty.HARD;
        }
        this.provider.worldChunkMgr.cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                long var1 = this.worldInfo.getWorldTime() + 24000;
                this.worldInfo.setWorldTime(var1 - var1 % 24000);
            }
            this.wakeAllPlayers();
        }
        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
            this.animalSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400 == 0);
        }
        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        int var3 = this.calculateSkylightSubtracted(1.0f);
        if (var3 != this.skylightSubtracted) {
            this.skylightSubtracted = var3;
        }
        this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1);
        }
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickBlocks");
        this.func_147456_g();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiegeObj.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.func_147488_Z();
    }

    public BiomeGenBase.SpawnListEntry spawnRandomCreature(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        List var5 = this.getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
        return var5 != null && !var5.isEmpty() ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var5) : null;
    }

    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        for (EntityPlayer var2 : this.playerEntities) {
            if (var2.isPlayerSleeping()) continue;
            this.allPlayersSleeping = false;
            break;
        }
    }

    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (EntityPlayer var2 : this.playerEntities) {
            if (!var2.isPlayerSleeping()) continue;
            var2.wakeUpPlayer(false, false, true);
        }
        this.resetRainAndThunder();
    }

    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }

    public boolean areAllPlayersAsleep() {
        if (this.allPlayersSleeping && !this.isClient) {
            EntityPlayer var2;
            Iterator var1 = this.playerEntities.iterator();
            do {
                if (var1.hasNext()) continue;
                return true;
            } while ((var2 = (EntityPlayer)var1.next()).isPlayerFullyAsleep());
            return false;
        }
        return false;
    }

    @Override
    public void setSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int var1 = this.worldInfo.getSpawnX();
        int var2 = this.worldInfo.getSpawnZ();
        int var3 = 0;
        while (this.getTopBlock(var1, var2).getMaterial() == Material.air) {
            var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
            var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
            if (++var3 == 10000) break;
        }
        this.worldInfo.setSpawnX(var1);
        this.worldInfo.setSpawnZ(var2);
    }

    @Override
    protected void func_147456_g() {
        super.func_147456_g();
        int var1 = 0;
        int var2 = 0;
        for (ChunkCoordIntPair var4 : this.activeChunkSet) {
            int var9;
            int var11;
            int var10;
            int var8;
            int var5 = var4.chunkXPos * 16;
            int var6 = var4.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var7 = this.getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
            this.func_147467_a(var5, var6, var7);
            this.theProfiler.endStartSection("tickChunk");
            var7.func_150804_b(false);
            this.theProfiler.endStartSection("thunder");
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                var8 = this.updateLCG >> 2;
                var9 = var5 + (var8 & 15);
                var10 = var6 + (var8 >> 8 & 15);
                var11 = this.getPrecipitationHeight(var9, var10);
                if (this.canLightningStrikeAt(var9, var11, var10)) {
                    this.addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
                }
            }
            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
                BiomeGenBase var12;
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                var8 = this.updateLCG >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = this.getPrecipitationHeight(var9 + var5, var10 + var6);
                if (this.isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6)) {
                    this.setBlock(var9 + var5, var11 - 1, var10 + var6, Blocks.ice);
                }
                if (this.isRaining() && this.func_147478_e(var9 + var5, var11, var10 + var6, true)) {
                    this.setBlock(var9 + var5, var11, var10 + var6, Blocks.snow_layer);
                }
                if (this.isRaining() && (var12 = this.getBiomeGenForCoords(var9 + var5, var10 + var6)).canSpawnLightningBolt()) {
                    this.getBlock(var9 + var5, var11 - 1, var10 + var6).fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
                }
            }
            this.theProfiler.endStartSection("tickBlocks");
            ExtendedBlockStorage[] var18 = var7.getBlockStorageArray();
            var9 = var18.length;
            var10 = 0;
            while (var10 < var9) {
                ExtendedBlockStorage var20 = var18[var10];
                if (var20 != null && var20.getNeedsRandomTick()) {
                    int var19 = 0;
                    while (var19 < 3) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        int var13 = this.updateLCG >> 2;
                        int var14 = var13 & 15;
                        int var15 = var13 >> 8 & 15;
                        int var16 = var13 >> 16 & 15;
                        ++var2;
                        Block var17 = var20.func_150819_a(var14, var16, var15);
                        if (var17.getTickRandomly()) {
                            ++var1;
                            var17.updateTick(this, var14 + var5, var16 + var20.getYLocation(), var15 + var6, this.rand);
                        }
                        ++var19;
                    }
                }
                ++var10;
            }
            this.theProfiler.endSection();
        }
    }

    @Override
    public boolean func_147477_a(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_) {
        NextTickListEntry var5 = new NextTickListEntry(p_147477_1_, p_147477_2_, p_147477_3_, p_147477_4_);
        return this.pendingTickListEntriesThisTick.contains(var5);
    }

    @Override
    public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_) {
        this.func_147454_a(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_, 0);
    }

    @Override
    public void func_147454_a(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_) {
        NextTickListEntry var7 = new NextTickListEntry(p_147454_1_, p_147454_2_, p_147454_3_, p_147454_4_);
        int var8 = 0;
        if (this.scheduledUpdatesAreImmediate && p_147454_4_.getMaterial() != Material.air) {
            if (p_147454_4_.func_149698_L()) {
                Block var9;
                var8 = 8;
                if (this.checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8) && (var9 = this.getBlock(var7.xCoord, var7.yCoord, var7.zCoord)).getMaterial() != Material.air && var9 == var7.func_151351_a()) {
                    var9.updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, this.rand);
                }
                return;
            }
            p_147454_5_ = 1;
        }
        if (this.checkChunksExist(p_147454_1_ - var8, p_147454_2_ - var8, p_147454_3_ - var8, p_147454_1_ + var8, p_147454_2_ + var8, p_147454_3_ + var8)) {
            if (p_147454_4_.getMaterial() != Material.air) {
                var7.setScheduledTime((long)p_147454_5_ + this.worldInfo.getWorldTotalTime());
                var7.setPriority(p_147454_6_);
            }
            if (!this.pendingTickListEntriesHashSet.contains(var7)) {
                this.pendingTickListEntriesHashSet.add(var7);
                this.pendingTickListEntriesTreeSet.add(var7);
            }
        }
    }

    @Override
    public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_) {
        NextTickListEntry var7 = new NextTickListEntry(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_);
        var7.setPriority(p_147446_6_);
        if (p_147446_4_.getMaterial() != Material.air) {
            var7.setScheduledTime((long)p_147446_5_ + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(var7)) {
            this.pendingTickListEntriesHashSet.add(var7);
            this.pendingTickListEntriesTreeSet.add(var7);
        }
    }

    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        } else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }

    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }

    @Override
    public boolean tickUpdates(boolean par1) {
        NextTickListEntry var4;
        int var2 = this.pendingTickListEntriesTreeSet.size();
        if (var2 != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (var2 > 1000) {
            var2 = 1000;
        }
        this.theProfiler.startSection("cleaning");
        int var3 = 0;
        while (var3 < var2) {
            var4 = (NextTickListEntry)this.pendingTickListEntriesTreeSet.first();
            if (!par1 && var4.scheduledTime > this.worldInfo.getWorldTotalTime()) break;
            this.pendingTickListEntriesTreeSet.remove(var4);
            this.pendingTickListEntriesHashSet.remove(var4);
            this.pendingTickListEntriesThisTick.add(var4);
            ++var3;
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        Iterator var14 = this.pendingTickListEntriesThisTick.iterator();
        while (var14.hasNext()) {
            var4 = (NextTickListEntry)var14.next();
            var14.remove();
            int var5 = 0;
            if (this.checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5)) {
                Block var6 = this.getBlock(var4.xCoord, var4.yCoord, var4.zCoord);
                if (var6.getMaterial() == Material.air || !Block.isEqualTo(var6, var4.func_151351_a())) continue;
                try {
                    var6.updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
                    continue;
                }
                catch (Throwable var13) {
                    int var10;
                    CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while ticking a block");
                    CrashReportCategory var9 = var8.makeCategory("Block being ticked");
                    try {
                        var10 = this.getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
                    }
                    catch (Throwable var12) {
                        var10 = -1;
                    }
                    CrashReportCategory.func_147153_a(var9, var4.xCoord, var4.yCoord, var4.zCoord, var6, var10);
                    throw new ReportedException(var8);
                }
            }
            this.scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.func_151351_a(), 0);
        }
        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }

    @Override
    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
        ArrayList<NextTickListEntry> var3 = null;
        ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
        int var5 = (var4.chunkXPos << 4) - 2;
        int var6 = var5 + 16 + 2;
        int var7 = (var4.chunkZPos << 4) - 2;
        int var8 = var7 + 16 + 2;
        int var9 = 0;
        while (var9 < 2) {
            Iterator var10;
            if (var9 == 0) {
                var10 = this.pendingTickListEntriesTreeSet.iterator();
            } else {
                var10 = this.pendingTickListEntriesThisTick.iterator();
                if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                    logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                }
            }
            while (var10.hasNext()) {
                NextTickListEntry var11 = (NextTickListEntry)var10.next();
                if (var11.xCoord < var5 || var11.xCoord >= var6 || var11.zCoord < var7 || var11.zCoord >= var8) continue;
                if (par2) {
                    this.pendingTickListEntriesHashSet.remove(var11);
                    var10.remove();
                }
                if (var3 == null) {
                    var3 = new ArrayList<NextTickListEntry>();
                }
                var3.add(var11);
            }
            ++var9;
        }
        return var3;
    }

    @Override
    public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2) {
        if (!this.mcServer.getCanSpawnAnimals() && (par1Entity instanceof EntityAnimal || par1Entity instanceof EntityWaterMob)) {
            par1Entity.setDead();
        }
        if (!this.mcServer.getCanSpawnNPCs() && par1Entity instanceof INpc) {
            par1Entity.setDead();
        }
        super.updateEntityWithOptionalForce(par1Entity, par2);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
        this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
        return this.theChunkProviderServer;
    }

    public List func_147486_a(int p_147486_1_, int p_147486_2_, int p_147486_3_, int p_147486_4_, int p_147486_5_, int p_147486_6_) {
        ArrayList<TileEntity> var7 = new ArrayList<TileEntity>();
        int var8 = 0;
        while (var8 < this.field_147482_g.size()) {
            TileEntity var9 = (TileEntity)this.field_147482_g.get(var8);
            if (var9.field_145851_c >= p_147486_1_ && var9.field_145848_d >= p_147486_2_ && var9.field_145849_e >= p_147486_3_ && var9.field_145851_c < p_147486_4_ && var9.field_145848_d < p_147486_5_ && var9.field_145849_e < p_147486_6_) {
                var7.add(var9);
            }
            ++var8;
        }
        return var7;
    }

    @Override
    public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
        return !this.mcServer.isBlockProtected(this, par2, par3, par4, par1EntityPlayer);
    }

    @Override
    protected void initialize(WorldSettings par1WorldSettings) {
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.pendingTickListEntriesHashSet == null) {
            this.pendingTickListEntriesHashSet = new HashSet();
        }
        if (this.pendingTickListEntriesTreeSet == null) {
            this.pendingTickListEntriesTreeSet = new TreeSet();
        }
        this.createSpawnPosition(par1WorldSettings);
        super.initialize(par1WorldSettings);
    }

    protected void createSpawnPosition(WorldSettings par1WorldSettings) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawnPosition(0, this.provider.getAverageGroundLevel(), 0);
        } else {
            this.findingSpawnPoint = true;
            WorldChunkManager var2 = this.provider.worldChunkMgr;
            List var3 = var2.getBiomesToSpawnIn();
            Random var4 = new Random(this.getSeed());
            ChunkPosition var5 = var2.func_150795_a(0, 0, 256, var3, var4);
            int var6 = 0;
            int var7 = this.provider.getAverageGroundLevel();
            int var8 = 0;
            if (var5 != null) {
                var6 = var5.field_151329_a;
                var8 = var5.field_151328_c;
            } else {
                logger.warn("Unable to find spawn biome");
            }
            int var9 = 0;
            while (!this.provider.canCoordinateBeSpawn(var6, var8)) {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                if (++var9 == 1000) break;
            }
            this.worldInfo.setSpawnPosition(var6, var7, var8);
            this.findingSpawnPoint = false;
            if (par1WorldSettings.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }

    protected void createBonusChest() {
        WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);
        int var2 = 0;
        while (var2 < 10) {
            int var5;
            int var4;
            int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
            if (var1.generate(this, this.rand, var3, var5 = this.getTopSolidOrLiquidBlock(var3, var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6)) + 1, var4)) break;
            ++var2;
        }
    }

    public ChunkCoordinates getEntrancePortalLocation() {
        return this.provider.getEntrancePortalLocation();
    }

    public void saveAllChunks(boolean par1, IProgressUpdate par2IProgressUpdate) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (par2IProgressUpdate != null) {
                par2IProgressUpdate.displayProgressMessage("Saving level");
            }
            this.saveLevel();
            if (par2IProgressUpdate != null) {
                par2IProgressUpdate.resetProgresAndWorkingMessage("Saving chunks");
            }
            this.chunkProvider.saveChunks(par1, par2IProgressUpdate);
        }
    }

    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }

    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }

    @Override
    protected void onEntityAdded(Entity par1Entity) {
        super.onEntityAdded(par1Entity);
        this.entityIdMap.addKey(par1Entity.getEntityId(), par1Entity);
        Entity[] var2 = par1Entity.getParts();
        if (var2 != null) {
            int var3 = 0;
            while (var3 < var2.length) {
                this.entityIdMap.addKey(var2[var3].getEntityId(), var2[var3]);
                ++var3;
            }
        }
    }

    @Override
    protected void onEntityRemoved(Entity par1Entity) {
        super.onEntityRemoved(par1Entity);
        this.entityIdMap.removeObject(par1Entity.getEntityId());
        Entity[] var2 = par1Entity.getParts();
        if (var2 != null) {
            int var3 = 0;
            while (var3 < var2.length) {
                this.entityIdMap.removeObject(var2[var3].getEntityId());
                ++var3;
            }
        }
    }

    @Override
    public Entity getEntityByID(int par1) {
        return (Entity)this.entityIdMap.lookup(par1);
    }

    @Override
    public boolean addWeatherEffect(Entity par1Entity) {
        if (super.addWeatherEffect(par1Entity)) {
            this.mcServer.getConfigurationManager().func_148541_a(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0, this.provider.dimensionId, new S2CPacketSpawnGlobalEntity(par1Entity));
            return true;
        }
        return false;
    }

    @Override
    public void setEntityState(Entity par1Entity, byte par2) {
        this.getEntityTracker().func_151248_b(par1Entity, new S19PacketEntityStatus(par1Entity, par2));
    }

    @Override
    public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10) {
        Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionA();
        var11.doExplosionB(false);
        if (!par10) {
            var11.affectedBlockPositions.clear();
        }
        for (EntityPlayer var13 : this.playerEntities) {
            if (var13.getDistanceSq(par2, par4, par6) >= 4096.0) continue;
            ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new S27PacketExplosion(par2, par4, par6, par8, var11.affectedBlockPositions, (Vec3)var11.func_77277_b().get(var13)));
        }
        return var11;
    }

    @Override
    public void func_147452_c(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_) {
        BlockEventData var9;
        BlockEventData var7 = new BlockEventData(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
        Iterator var8 = this.field_147490_S[this.field_147489_T].iterator();
        do {
            if (var8.hasNext()) continue;
            this.field_147490_S[this.field_147489_T].add(var7);
            return;
        } while (!(var9 = (BlockEventData)var8.next()).equals(var7));
    }

    private void func_147488_Z() {
        while (!this.field_147490_S[this.field_147489_T].isEmpty()) {
            int var1 = this.field_147489_T;
            this.field_147489_T ^= 1;
            for (BlockEventData var3 : this.field_147490_S[var1]) {
                if (!this.func_147485_a(var3)) continue;
                this.mcServer.getConfigurationManager().func_148541_a(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), 64.0, this.provider.dimensionId, new S24PacketBlockAction(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), var3.getBlock(), var3.getEventID(), var3.getEventParameter()));
            }
            this.field_147490_S[var1].clear();
        }
    }

    private boolean func_147485_a(BlockEventData p_147485_1_) {
        Block var2 = this.getBlock(p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c());
        return var2 == p_147485_1_.getBlock() ? var2.onBlockEventReceived(this, p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c(), p_147485_1_.getEventID(), p_147485_1_.getEventParameter()) : false;
    }

    public void flush() {
        this.saveHandler.flush();
    }

    @Override
    protected void updateWeather() {
        boolean var1 = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.dimensionId);
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.dimensionId);
        }
        if (var1 != this.isRaining()) {
            if (var1) {
                this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0f));
            } else {
                this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }

    public MinecraftServer func_73046_m() {
        return this.mcServer;
    }

    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }

    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }

    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }

    public void func_147487_a(String p_147487_1_, double p_147487_2_, double p_147487_4_, double p_147487_6_, int p_147487_8_, double p_147487_9_, double p_147487_11_, double p_147487_13_, double p_147487_15_) {
        S2APacketParticles var17 = new S2APacketParticles(p_147487_1_, (float)p_147487_2_, (float)p_147487_4_, (float)p_147487_6_, (float)p_147487_9_, (float)p_147487_11_, (float)p_147487_13_, (float)p_147487_15_, p_147487_8_);
        int var18 = 0;
        while (var18 < this.playerEntities.size()) {
            EntityPlayerMP var19 = (EntityPlayerMP)this.playerEntities.get(var18);
            ChunkCoordinates var20 = var19.getPlayerCoordinates();
            double var21 = p_147487_2_ - (double)var20.posX;
            double var23 = p_147487_4_ - (double)var20.posY;
            double var25 = p_147487_6_ - (double)var20.posZ;
            double var27 = var21 * var21 + var23 * var23 + var25 * var25;
            if (var27 <= 256.0) {
                var19.playerNetServerHandler.sendPacket(var17);
            }
            ++var18;
        }
    }

    static class ServerBlockEventList
    extends ArrayList {
        private static final String __OBFID = "CL_00001439";

        private ServerBlockEventList() {
        }

        ServerBlockEventList(Object par1ServerBlockEvent) {
            this();
        }
    }

}

