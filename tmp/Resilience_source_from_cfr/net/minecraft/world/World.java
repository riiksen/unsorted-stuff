/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public abstract class World
implements IBlockAccess {
    public boolean scheduledUpdatesAreImmediate;
    public List loadedEntityList = new ArrayList();
    protected List unloadedEntityList = new ArrayList();
    public List field_147482_g = new ArrayList();
    private List field_147484_a = new ArrayList();
    private List field_147483_b = new ArrayList();
    public List playerEntities = new ArrayList();
    public List weatherEffects = new ArrayList();
    private long cloudColour = 0xFFFFFF;
    public int skylightSubtracted;
    protected int updateLCG = new Random().nextInt();
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected float prevRainingStrength;
    protected float rainingStrength;
    protected float prevThunderingStrength;
    protected float thunderingStrength;
    public int lastLightningBolt;
    public EnumDifficulty difficultySetting;
    public Random rand = new Random();
    public final WorldProvider provider;
    protected List worldAccesses = new ArrayList();
    protected IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    protected WorldInfo worldInfo;
    public boolean findingSpawnPoint;
    public MapStorage mapStorage;
    public final VillageCollection villageCollectionObj;
    protected final VillageSiege villageSiegeObj;
    public final Profiler theProfiler;
    private final Vec3Pool vecPool;
    private final Calendar theCalendar;
    protected Scoreboard worldScoreboard;
    public boolean isClient;
    protected Set activeChunkSet;
    private int ambientTickCountdown;
    protected boolean spawnHostileMobs;
    protected boolean spawnPeacefulMobs;
    private ArrayList collidingBoundingBoxes;
    private boolean field_147481_N;
    int[] lightUpdateBlockList;
    private static final String __OBFID = "CL_00000140";

    @Override
    public BiomeGenBase getBiomeGenForCoords(final int par1, final int par2) {
        if (this.blockExists(par1, 0, par2)) {
            Chunk var3 = this.getChunkFromBlockCoords(par1, par2);
            try {
                return var3.getBiomeGenForWorldCoords(par1 & 15, par2 & 15, this.provider.worldChunkMgr);
            }
            catch (Throwable var7) {
                CrashReport var5 = CrashReport.makeCrashReport(var7, "Getting biome");
                CrashReportCategory var6 = var5.makeCategory("Coordinates of biome request");
                var6.addCrashSectionCallable("Location", new Callable(){
                    private static final String __OBFID = "CL_00000141";

                    public String call() {
                        return CrashReportCategory.getLocationInfo(par1, 0, par2);
                    }
                });
                throw new ReportedException(var5);
            }
        }
        return this.provider.worldChunkMgr.getBiomeGenAt(par1, par2);
    }

    public WorldChunkManager getWorldChunkManager() {
        return this.provider.worldChunkMgr;
    }

    public World(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
        this.villageSiegeObj = new VillageSiege(this);
        this.vecPool = new Vec3Pool(300, 2000);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.collidingBoundingBoxes = new ArrayList();
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = p_i45368_1_;
        this.theProfiler = p_i45368_5_;
        this.worldInfo = new WorldInfo(p_i45368_4_, p_i45368_2_);
        this.provider = p_i45368_3_;
        this.mapStorage = new MapStorage(p_i45368_1_);
        VillageCollection var6 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
        if (var6 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        } else {
            this.villageCollectionObj = var6;
            this.villageCollectionObj.func_82566_a(this);
        }
        p_i45368_3_.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }

    public World(ISaveHandler p_i45369_1_, String p_i45369_2_, WorldSettings p_i45369_3_, WorldProvider p_i45369_4_, Profiler p_i45369_5_) {
        VillageCollection var6;
        this.villageSiegeObj = new VillageSiege(this);
        this.vecPool = new Vec3Pool(300, 2000);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.collidingBoundingBoxes = new ArrayList();
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = p_i45369_1_;
        this.theProfiler = p_i45369_5_;
        this.mapStorage = new MapStorage(p_i45369_1_);
        this.worldInfo = p_i45369_1_.loadWorldInfo();
        this.provider = p_i45369_4_ != null ? p_i45369_4_ : (this.worldInfo != null && this.worldInfo.getVanillaDimension() != 0 ? WorldProvider.getProviderForDimension(this.worldInfo.getVanillaDimension()) : WorldProvider.getProviderForDimension(0));
        if (this.worldInfo == null) {
            this.worldInfo = new WorldInfo(p_i45369_3_, p_i45369_2_);
        } else {
            this.worldInfo.setWorldName(p_i45369_2_);
        }
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        if (!this.worldInfo.isInitialized()) {
            try {
                this.initialize(p_i45369_3_);
            }
            catch (Throwable var10) {
                CrashReport var7 = CrashReport.makeCrashReport(var10, "Exception initializing level");
                try {
                    this.addWorldInfoToCrashReport(var7);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                throw new ReportedException(var7);
            }
            this.worldInfo.setServerInitialized(true);
        }
        if ((var6 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages")) == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        } else {
            this.villageCollectionObj = var6;
            this.villageCollectionObj.func_82566_a(this);
        }
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }

    protected abstract IChunkProvider createChunkProvider();

    protected void initialize(WorldSettings par1WorldSettings) {
        this.worldInfo.setServerInitialized(true);
    }

    public void setSpawnLocation() {
        this.setSpawnLocation(8, 64, 8);
    }

    public Block getTopBlock(int p_147474_1_, int p_147474_2_) {
        int var3 = 63;
        while (!this.isAirBlock(p_147474_1_, var3 + 1, p_147474_2_)) {
            ++var3;
        }
        return this.getBlock(p_147474_1_, var3, p_147474_2_);
    }

    @Override
    public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
        if (p_147439_1_ >= -30000000 && p_147439_3_ >= -30000000 && p_147439_1_ < 30000000 && p_147439_3_ < 30000000 && p_147439_2_ >= 0 && p_147439_2_ < 256) {
            Chunk var4 = null;
            try {
                var4 = this.getChunkFromChunkCoords(p_147439_1_ >> 4, p_147439_3_ >> 4);
                return var4.func_150810_a(p_147439_1_ & 15, p_147439_2_, p_147439_3_ & 15);
            }
            catch (Throwable var8) {
                CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception getting block type in world");
                CrashReportCategory var7 = var6.makeCategory("Requested block coordinates");
                var7.addCrashSection("Found chunk", var4 == null);
                var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(p_147439_1_, p_147439_2_, p_147439_3_));
                throw new ReportedException(var6);
            }
        }
        return Blocks.air;
    }

    @Override
    public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
        if (this.getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air) {
            return true;
        }
        return false;
    }

    public boolean blockExists(int par1, int par2, int par3) {
        return par2 >= 0 && par2 < 256 ? this.chunkExists(par1 >> 4, par3 >> 4) : false;
    }

    public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4) {
        return this.checkChunksExist(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4);
    }

    public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6) {
        if (par5 >= 0 && par2 < 256) {
            par3 >>= 4;
            par6 >>= 4;
            int var7 = par1 >>= 4;
            while (var7 <= (par4 >>= 4)) {
                int var8 = par3;
                while (var8 <= par6) {
                    if (!this.chunkExists(var7, var8)) {
                        return false;
                    }
                    ++var8;
                }
                ++var7;
            }
            return true;
        }
        return false;
    }

    protected boolean chunkExists(int par1, int par2) {
        return this.chunkProvider.chunkExists(par1, par2);
    }

    public Chunk getChunkFromBlockCoords(int par1, int par2) {
        return this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
    }

    public Chunk getChunkFromChunkCoords(int par1, int par2) {
        return this.chunkProvider.provideChunk(par1, par2);
    }

    public boolean setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block p_147465_4_, int p_147465_5_, int p_147465_6_) {
        if (p_147465_1_ >= -30000000 && p_147465_3_ >= -30000000 && p_147465_1_ < 30000000 && p_147465_3_ < 30000000) {
            if (p_147465_2_ < 0) {
                return false;
            }
            if (p_147465_2_ >= 256) {
                return false;
            }
            Chunk var7 = this.getChunkFromChunkCoords(p_147465_1_ >> 4, p_147465_3_ >> 4);
            Block var8 = null;
            if ((p_147465_6_ & 1) != 0) {
                var8 = var7.func_150810_a(p_147465_1_ & 15, p_147465_2_, p_147465_3_ & 15);
            }
            boolean var9 = var7.func_150807_a(p_147465_1_ & 15, p_147465_2_, p_147465_3_ & 15, p_147465_4_, p_147465_5_);
            this.theProfiler.startSection("checkLight");
            this.func_147451_t(p_147465_1_, p_147465_2_, p_147465_3_);
            this.theProfiler.endSection();
            if (var9) {
                if ((p_147465_6_ & 2) != 0 && (!this.isClient || (p_147465_6_ & 4) == 0) && var7.func_150802_k()) {
                    this.func_147471_g(p_147465_1_, p_147465_2_, p_147465_3_);
                }
                if (!this.isClient && (p_147465_6_ & 1) != 0) {
                    this.notifyBlockChange(p_147465_1_, p_147465_2_, p_147465_3_, var8);
                    if (p_147465_4_.hasComparatorInputOverride()) {
                        this.func_147453_f(p_147465_1_, p_147465_2_, p_147465_3_, p_147465_4_);
                    }
                }
            }
            return var9;
        }
        return false;
    }

    @Override
    public int getBlockMetadata(int par1, int par2, int par3) {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
            if (par2 < 0) {
                return 0;
            }
            if (par2 >= 256) {
                return 0;
            }
            Chunk var4 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
            return var4.getBlockMetadata(par1 &= 15, par2, par3 &= 15);
        }
        return 0;
    }

    public boolean setBlockMetadataWithNotify(int par1, int par2, int par3, int par4, int par5) {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
            int var8;
            int var7;
            if (par2 < 0) {
                return false;
            }
            if (par2 >= 256) {
                return false;
            }
            Chunk var6 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
            boolean var9 = var6.setBlockMetadata(var7 = par1 & 15, par2, var8 = par3 & 15, par4);
            if (var9) {
                Block var10 = var6.func_150810_a(var7, par2, var8);
                if ((par5 & 2) != 0 && (!this.isClient || (par5 & 4) == 0) && var6.func_150802_k()) {
                    this.func_147471_g(par1, par2, par3);
                }
                if (!this.isClient && (par5 & 1) != 0) {
                    this.notifyBlockChange(par1, par2, par3, var10);
                    if (var10.hasComparatorInputOverride()) {
                        this.func_147453_f(par1, par2, par3, var10);
                    }
                }
            }
            return var9;
        }
        return false;
    }

    public boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_) {
        return this.setBlock(p_147468_1_, p_147468_2_, p_147468_3_, Blocks.air, 0, 3);
    }

    public boolean func_147480_a(int p_147480_1_, int p_147480_2_, int p_147480_3_, boolean p_147480_4_) {
        Block var5 = this.getBlock(p_147480_1_, p_147480_2_, p_147480_3_);
        if (var5.getMaterial() == Material.air) {
            return false;
        }
        int var6 = this.getBlockMetadata(p_147480_1_, p_147480_2_, p_147480_3_);
        this.playAuxSFX(2001, p_147480_1_, p_147480_2_, p_147480_3_, Block.getIdFromBlock(var5) + (var6 << 12));
        if (p_147480_4_) {
            var5.dropBlockAsItem(this, p_147480_1_, p_147480_2_, p_147480_3_, var6, 0);
        }
        return this.setBlock(p_147480_1_, p_147480_2_, p_147480_3_, Blocks.air, 0, 3);
    }

    public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block p_147449_4_) {
        return this.setBlock(p_147449_1_, p_147449_2_, p_147449_3_, p_147449_4_, 0, 3);
    }

    public void func_147471_g(int p_147471_1_, int p_147471_2_, int p_147471_3_) {
        int var4 = 0;
        while (var4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var4)).markBlockForUpdate(p_147471_1_, p_147471_2_, p_147471_3_);
            ++var4;
        }
    }

    public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_) {
        this.notifyBlocksOfNeighborChange(p_147444_1_, p_147444_2_, p_147444_3_, p_147444_4_);
    }

    public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4) {
        int var5;
        if (par3 > par4) {
            var5 = par4;
            par4 = par3;
            par3 = var5;
        }
        if (!this.provider.hasNoSky) {
            var5 = par3;
            while (var5 <= par4) {
                this.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
                ++var5;
            }
        }
        this.markBlockRangeForRenderUpdate(par1, par3, par2, par1, par4, par2);
    }

    public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_) {
        int var7 = 0;
        while (var7 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var7)).markBlockRangeForRenderUpdate(p_147458_1_, p_147458_2_, p_147458_3_, p_147458_4_, p_147458_5_, p_147458_6_);
            ++var7;
        }
    }

    public void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_) {
        this.func_147460_e(p_147459_1_ - 1, p_147459_2_, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_ + 1, p_147459_2_, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_ - 1, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_ + 1, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ - 1, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ + 1, p_147459_4_);
    }

    public void func_147441_b(int p_147441_1_, int p_147441_2_, int p_147441_3_, Block p_147441_4_, int p_147441_5_) {
        if (p_147441_5_ != 4) {
            this.func_147460_e(p_147441_1_ - 1, p_147441_2_, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 5) {
            this.func_147460_e(p_147441_1_ + 1, p_147441_2_, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 0) {
            this.func_147460_e(p_147441_1_, p_147441_2_ - 1, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 1) {
            this.func_147460_e(p_147441_1_, p_147441_2_ + 1, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 2) {
            this.func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ - 1, p_147441_4_);
        }
        if (p_147441_5_ != 3) {
            this.func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ + 1, p_147441_4_);
        }
    }

    public void func_147460_e(int p_147460_1_, int p_147460_2_, int p_147460_3_, final Block p_147460_4_) {
        if (!this.isClient) {
            Block var5 = this.getBlock(p_147460_1_, p_147460_2_, p_147460_3_);
            try {
                var5.onNeighborBlockChange(this, p_147460_1_, p_147460_2_, p_147460_3_, p_147460_4_);
            }
            catch (Throwable var12) {
                int var9;
                CrashReport var7 = CrashReport.makeCrashReport(var12, "Exception while updating neighbours");
                CrashReportCategory var8 = var7.makeCategory("Block being updated");
                try {
                    var9 = this.getBlockMetadata(p_147460_1_, p_147460_2_, p_147460_3_);
                }
                catch (Throwable var11) {
                    var9 = -1;
                }
                var8.addCrashSectionCallable("Source block type", new Callable(){
                    private static final String __OBFID = "CL_00000142";

                    public String call() {
                        try {
                            return String.format("ID #%d (%s // %s)", Block.getIdFromBlock(p_147460_4_), p_147460_4_.getUnlocalizedName(), p_147460_4_.getClass().getCanonicalName());
                        }
                        catch (Throwable var2) {
                            return "ID #" + Block.getIdFromBlock(p_147460_4_);
                        }
                    }
                });
                CrashReportCategory.func_147153_a(var8, p_147460_1_, p_147460_2_, p_147460_3_, var5, var9);
                throw new ReportedException(var7);
            }
        }
    }

    public boolean func_147477_a(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_) {
        return false;
    }

    public boolean canBlockSeeTheSky(int par1, int par2, int par3) {
        return this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4).canBlockSeeTheSky(par1 & 15, par2, par3 & 15);
    }

    public int getFullBlockLightValue(int par1, int par2, int par3) {
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            par2 = 255;
        }
        return this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4).getBlockLightValue(par1 & 15, par2, par3 & 15, 0);
    }

    public int getBlockLightValue(int par1, int par2, int par3) {
        return this.getBlockLightValue_do(par1, par2, par3, true);
    }

    public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4) {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
            if (par4 && this.getBlock(par1, par2, par3).func_149710_n()) {
                int var10 = this.getBlockLightValue_do(par1, par2 + 1, par3, false);
                int var6 = this.getBlockLightValue_do(par1 + 1, par2, par3, false);
                int var7 = this.getBlockLightValue_do(par1 - 1, par2, par3, false);
                int var8 = this.getBlockLightValue_do(par1, par2, par3 + 1, false);
                int var9 = this.getBlockLightValue_do(par1, par2, par3 - 1, false);
                if (var6 > var10) {
                    var10 = var6;
                }
                if (var7 > var10) {
                    var10 = var7;
                }
                if (var8 > var10) {
                    var10 = var8;
                }
                if (var9 > var10) {
                    var10 = var9;
                }
                return var10;
            }
            if (par2 < 0) {
                return 0;
            }
            if (par2 >= 256) {
                par2 = 255;
            }
            Chunk var5 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
            return var5.getBlockLightValue(par1 &= 15, par2, par3 &= 15, this.skylightSubtracted);
        }
        return 15;
    }

    public int getHeightValue(int par1, int par2) {
        if (par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000) {
            if (!this.chunkExists(par1 >> 4, par2 >> 4)) {
                return 0;
            }
            Chunk var3 = this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
            return var3.getHeightValue(par1 & 15, par2 & 15);
        }
        return 64;
    }

    public int getChunkHeightMapMinimum(int par1, int par2) {
        if (par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000) {
            if (!this.chunkExists(par1 >> 4, par2 >> 4)) {
                return 0;
            }
            Chunk var3 = this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
            return var3.heightMapMinimum;
        }
        return 64;
    }

    public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
        if (this.provider.hasNoSky && par1EnumSkyBlock == EnumSkyBlock.Sky) {
            return 0;
        }
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000) {
            int var5 = par2 >> 4;
            int var6 = par4 >> 4;
            if (!this.chunkExists(var5, var6)) {
                return par1EnumSkyBlock.defaultLightValue;
            }
            if (this.getBlock(par2, par3, par4).func_149710_n()) {
                int var12 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3 + 1, par4);
                int var8 = this.getSavedLightValue(par1EnumSkyBlock, par2 + 1, par3, par4);
                int var9 = this.getSavedLightValue(par1EnumSkyBlock, par2 - 1, par3, par4);
                int var10 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 + 1);
                int var11 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 - 1);
                if (var8 > var12) {
                    var12 = var8;
                }
                if (var9 > var12) {
                    var12 = var9;
                }
                if (var10 > var12) {
                    var12 = var10;
                }
                if (var11 > var12) {
                    var12 = var11;
                }
                return var12;
            }
            Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
            return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
        }
        return par1EnumSkyBlock.defaultLightValue;
    }

    public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000) {
            int var5 = par2 >> 4;
            int var6 = par4 >> 4;
            if (!this.chunkExists(var5, var6)) {
                return par1EnumSkyBlock.defaultLightValue;
            }
            Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
            return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
        }
        return par1EnumSkyBlock.defaultLightValue;
    }

    public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {
        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000 && par3 >= 0 && par3 < 256 && this.chunkExists(par2 >> 4, par4 >> 4)) {
            Chunk var6 = this.getChunkFromChunkCoords(par2 >> 4, par4 >> 4);
            var6.setLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15, par5);
            int var7 = 0;
            while (var7 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(var7)).markBlockForRenderUpdate(par2, par3, par4);
                ++var7;
            }
        }
    }

    public void func_147479_m(int p_147479_1_, int p_147479_2_, int p_147479_3_) {
        int var4 = 0;
        while (var4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var4)).markBlockForRenderUpdate(p_147479_1_, p_147479_2_, p_147479_3_);
            ++var4;
        }
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4) {
        int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
        int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
        if (var6 < par4) {
            var6 = par4;
        }
        return var5 << 20 | var6 << 4;
    }

    public float getLightBrightness(int par1, int par2, int par3) {
        return this.provider.lightBrightnessTable[this.getBlockLightValue(par1, par2, par3)];
    }

    public boolean isDaytime() {
        if (this.skylightSubtracted < 4) {
            return true;
        }
        return false;
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3) {
        return this.func_147447_a(par1Vec3, par2Vec3, false, false, false);
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3) {
        return this.func_147447_a(par1Vec3, par2Vec3, par3, false, false);
    }

    public MovingObjectPosition func_147447_a(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_) {
        if (!(Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord))) {
            if (!(Double.isNaN(p_147447_2_.xCoord) || Double.isNaN(p_147447_2_.yCoord) || Double.isNaN(p_147447_2_.zCoord))) {
                MovingObjectPosition var14;
                int var6 = MathHelper.floor_double(p_147447_2_.xCoord);
                int var7 = MathHelper.floor_double(p_147447_2_.yCoord);
                int var8 = MathHelper.floor_double(p_147447_2_.zCoord);
                int var9 = MathHelper.floor_double(p_147447_1_.xCoord);
                int var10 = MathHelper.floor_double(p_147447_1_.yCoord);
                int var11 = MathHelper.floor_double(p_147447_1_.zCoord);
                Block var12 = this.getBlock(var9, var10, var11);
                int var13 = this.getBlockMetadata(var9, var10, var11);
                if ((!p_147447_4_ || var12.getCollisionBoundingBoxFromPool(this, var9, var10, var11) != null) && var12.canCollideCheck(var13, p_147447_3_) && (var14 = var12.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_)) != null) {
                    return var14;
                }
                MovingObjectPosition var40 = null;
                var13 = 200;
                while (var13-- >= 0) {
                    int var42;
                    if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord)) {
                        return null;
                    }
                    if (var9 == var6 && var10 == var7 && var11 == var8) {
                        return p_147447_5_ ? var40 : null;
                    }
                    boolean var41 = true;
                    boolean var15 = true;
                    boolean var16 = true;
                    double var17 = 999.0;
                    double var19 = 999.0;
                    double var21 = 999.0;
                    if (var6 > var9) {
                        var17 = (double)var9 + 1.0;
                    } else if (var6 < var9) {
                        var17 = (double)var9 + 0.0;
                    } else {
                        var41 = false;
                    }
                    if (var7 > var10) {
                        var19 = (double)var10 + 1.0;
                    } else if (var7 < var10) {
                        var19 = (double)var10 + 0.0;
                    } else {
                        var15 = false;
                    }
                    if (var8 > var11) {
                        var21 = (double)var11 + 1.0;
                    } else if (var8 < var11) {
                        var21 = (double)var11 + 0.0;
                    } else {
                        var16 = false;
                    }
                    double var23 = 999.0;
                    double var25 = 999.0;
                    double var27 = 999.0;
                    double var29 = p_147447_2_.xCoord - p_147447_1_.xCoord;
                    double var31 = p_147447_2_.yCoord - p_147447_1_.yCoord;
                    double var33 = p_147447_2_.zCoord - p_147447_1_.zCoord;
                    if (var41) {
                        var23 = (var17 - p_147447_1_.xCoord) / var29;
                    }
                    if (var15) {
                        var25 = (var19 - p_147447_1_.yCoord) / var31;
                    }
                    if (var16) {
                        var27 = (var21 - p_147447_1_.zCoord) / var33;
                    }
                    boolean var35 = false;
                    if (var23 < var25 && var23 < var27) {
                        var42 = var6 > var9 ? 4 : 5;
                        p_147447_1_.xCoord = var17;
                        p_147447_1_.yCoord += var31 * var23;
                        p_147447_1_.zCoord += var33 * var23;
                    } else if (var25 < var27) {
                        var42 = var7 > var10 ? 0 : 1;
                        p_147447_1_.xCoord += var29 * var25;
                        p_147447_1_.yCoord = var19;
                        p_147447_1_.zCoord += var33 * var25;
                    } else {
                        var42 = var8 > var11 ? 2 : 3;
                        p_147447_1_.xCoord += var29 * var27;
                        p_147447_1_.yCoord += var31 * var27;
                        p_147447_1_.zCoord = var21;
                    }
                    Vec3 var36 = this.getWorldVec3Pool().getVecFromPool(p_147447_1_.xCoord, p_147447_1_.yCoord, p_147447_1_.zCoord);
                    var36.xCoord = MathHelper.floor_double(p_147447_1_.xCoord);
                    var9 = (int)var36.xCoord;
                    if (var42 == 5) {
                        --var9;
                        var36.xCoord += 1.0;
                    }
                    var36.yCoord = MathHelper.floor_double(p_147447_1_.yCoord);
                    var10 = (int)var36.yCoord;
                    if (var42 == 1) {
                        --var10;
                        var36.yCoord += 1.0;
                    }
                    var36.zCoord = MathHelper.floor_double(p_147447_1_.zCoord);
                    var11 = (int)var36.zCoord;
                    if (var42 == 3) {
                        --var11;
                        var36.zCoord += 1.0;
                    }
                    Block var37 = this.getBlock(var9, var10, var11);
                    int var38 = this.getBlockMetadata(var9, var10, var11);
                    if (p_147447_4_ && var37.getCollisionBoundingBoxFromPool(this, var9, var10, var11) == null) continue;
                    if (var37.canCollideCheck(var38, p_147447_3_)) {
                        MovingObjectPosition var39 = var37.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_);
                        if (var39 == null) continue;
                        return var39;
                    }
                    var40 = new MovingObjectPosition(var9, var10, var11, var42, p_147447_1_, false);
                }
                return p_147447_5_ ? var40 : null;
            }
            return null;
        }
        return null;
    }

    public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4) {
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).playSound(par2Str, par1Entity.posX, par1Entity.posY - (double)par1Entity.yOffset, par1Entity.posZ, par3, par4);
            ++var5;
        }
    }

    public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4) {
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).playSoundToNearExcept(par1EntityPlayer, par2Str, par1EntityPlayer.posX, par1EntityPlayer.posY - (double)par1EntityPlayer.yOffset, par1EntityPlayer.posZ, par3, par4);
            ++var5;
        }
    }

    public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9) {
        int var10 = 0;
        while (var10 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var10)).playSound(par7Str, par1, par3, par5, par8, par9);
            ++var10;
        }
    }

    public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {
    }

    public void playRecord(String par1Str, int par2, int par3, int par4) {
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).playRecord(par1Str, par2, par3, par4);
            ++var5;
        }
    }

    public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
        int var14 = 0;
        while (var14 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var14)).spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
            ++var14;
        }
    }

    public boolean addWeatherEffect(Entity par1Entity) {
        this.weatherEffects.add(par1Entity);
        return true;
    }

    public boolean spawnEntityInWorld(Entity par1Entity) {
        int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        boolean var4 = par1Entity.forceSpawn;
        if (par1Entity instanceof EntityPlayer) {
            var4 = true;
        }
        if (!var4 && !this.chunkExists(var2, var3)) {
            return false;
        }
        if (par1Entity instanceof EntityPlayer) {
            EntityPlayer var5 = (EntityPlayer)par1Entity;
            this.playerEntities.add(var5);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(var2, var3).addEntity(par1Entity);
        this.loadedEntityList.add(par1Entity);
        this.onEntityAdded(par1Entity);
        return true;
    }

    protected void onEntityAdded(Entity par1Entity) {
        int var2 = 0;
        while (var2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var2)).onEntityCreate(par1Entity);
            ++var2;
        }
    }

    protected void onEntityRemoved(Entity par1Entity) {
        int var2 = 0;
        while (var2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var2)).onEntityDestroy(par1Entity);
            ++var2;
        }
    }

    public void removeEntity(Entity par1Entity) {
        if (par1Entity.riddenByEntity != null) {
            par1Entity.riddenByEntity.mountEntity(null);
        }
        if (par1Entity.ridingEntity != null) {
            par1Entity.mountEntity(null);
        }
        par1Entity.setDead();
        if (par1Entity instanceof EntityPlayer) {
            this.playerEntities.remove(par1Entity);
            this.updateAllPlayersSleepingFlag();
        }
    }

    public void removePlayerEntityDangerously(Entity par1Entity) {
        par1Entity.setDead();
        if (par1Entity instanceof EntityPlayer) {
            this.playerEntities.remove(par1Entity);
            this.updateAllPlayersSleepingFlag();
        }
        int var2 = par1Entity.chunkCoordX;
        int var3 = par1Entity.chunkCoordZ;
        if (par1Entity.addedToChunk && this.chunkExists(var2, var3)) {
            this.getChunkFromChunkCoords(var2, var3).removeEntity(par1Entity);
        }
        this.loadedEntityList.remove(par1Entity);
        this.onEntityRemoved(par1Entity);
    }

    public void addWorldAccess(IWorldAccess par1IWorldAccess) {
        this.worldAccesses.add(par1IWorldAccess);
    }

    public void removeWorldAccess(IWorldAccess par1IWorldAccess) {
        this.worldAccesses.remove(par1IWorldAccess);
    }

    public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
        this.collidingBoundingBoxes.clear();
        int var3 = MathHelper.floor_double(par2AxisAlignedBB.minX);
        int var4 = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0);
        int var5 = MathHelper.floor_double(par2AxisAlignedBB.minY);
        int var6 = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0);
        int var7 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
        int var8 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var7;
            while (var10 < var8) {
                if (this.blockExists(var9, 64, var10)) {
                    int var11 = var5 - 1;
                    while (var11 < var6) {
                        Block var12 = var9 >= -30000000 && var9 < 30000000 && var10 >= -30000000 && var10 < 30000000 ? this.getBlock(var9, var11, var10) : Blocks.stone;
                        var12.addCollisionBoxesToList(this, var9, var11, var10, par2AxisAlignedBB, this.collidingBoundingBoxes, par1Entity);
                        ++var11;
                    }
                }
                ++var10;
            }
            ++var9;
        }
        double var14 = 0.25;
        List var15 = this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(var14, var14, var14));
        int var16 = 0;
        while (var16 < var15.size()) {
            AxisAlignedBB var13 = ((Entity)var15.get(var16)).getBoundingBox();
            if (var13 != null && var13.intersectsWith(par2AxisAlignedBB)) {
                this.collidingBoundingBoxes.add(var13);
            }
            if ((var13 = par1Entity.getCollisionBox((Entity)var15.get(var16))) != null && var13.intersectsWith(par2AxisAlignedBB)) {
                this.collidingBoundingBoxes.add(var13);
            }
            ++var16;
        }
        return this.collidingBoundingBoxes;
    }

    public List func_147461_a(AxisAlignedBB p_147461_1_) {
        this.collidingBoundingBoxes.clear();
        int var2 = MathHelper.floor_double(p_147461_1_.minX);
        int var3 = MathHelper.floor_double(p_147461_1_.maxX + 1.0);
        int var4 = MathHelper.floor_double(p_147461_1_.minY);
        int var5 = MathHelper.floor_double(p_147461_1_.maxY + 1.0);
        int var6 = MathHelper.floor_double(p_147461_1_.minZ);
        int var7 = MathHelper.floor_double(p_147461_1_.maxZ + 1.0);
        int var8 = var2;
        while (var8 < var3) {
            int var9 = var6;
            while (var9 < var7) {
                if (this.blockExists(var8, 64, var9)) {
                    int var10 = var4 - 1;
                    while (var10 < var5) {
                        Block var11 = var8 >= -30000000 && var8 < 30000000 && var9 >= -30000000 && var9 < 30000000 ? this.getBlock(var8, var10, var9) : Blocks.bedrock;
                        var11.addCollisionBoxesToList(this, var8, var10, var9, p_147461_1_, this.collidingBoundingBoxes, null);
                        ++var10;
                    }
                }
                ++var9;
            }
            ++var8;
        }
        return this.collidingBoundingBoxes;
    }

    public int calculateSkylightSubtracted(float par1) {
        float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 = (float)((double)var3 * (1.0 - (double)(this.getRainStrength(par1) * 5.0f) / 16.0));
        var3 = (float)((double)var3 * (1.0 - (double)(this.getWeightedThunderStrength(par1) * 5.0f) / 16.0));
        var3 = 1.0f - var3;
        return (int)(var3 * 11.0f);
    }

    public float getSunBrightness(float par1) {
        float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.2f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 = (float)((double)var3 * (1.0 - (double)(this.getRainStrength(par1) * 5.0f) / 16.0));
        var3 = (float)((double)var3 * (1.0 - (double)(this.getWeightedThunderStrength(par1) * 5.0f) / 16.0));
        return var3 * 0.8f + 0.2f;
    }

    public Vec3 getSkyColor(Entity par1Entity, float par2) {
        float var16;
        float var15;
        float var3 = this.getCelestialAngle(par2);
        float var4 = MathHelper.cos(var3 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        int var5 = MathHelper.floor_double(par1Entity.posX);
        int var6 = MathHelper.floor_double(par1Entity.posY);
        int var7 = MathHelper.floor_double(par1Entity.posZ);
        BiomeGenBase var8 = this.getBiomeGenForCoords(var5, var7);
        float var9 = var8.getFloatTemperature(var5, var6, var7);
        int var10 = var8.getSkyColorByTemp(var9);
        float var11 = (float)(var10 >> 16 & 255) / 255.0f;
        float var12 = (float)(var10 >> 8 & 255) / 255.0f;
        float var13 = (float)(var10 & 255) / 255.0f;
        var11 *= var4;
        var12 *= var4;
        var13 *= var4;
        float var14 = this.getRainStrength(par2);
        if (var14 > 0.0f) {
            var15 = (var11 * 0.3f + var12 * 0.59f + var13 * 0.11f) * 0.6f;
            var16 = 1.0f - var14 * 0.75f;
            var11 = var11 * var16 + var15 * (1.0f - var16);
            var12 = var12 * var16 + var15 * (1.0f - var16);
            var13 = var13 * var16 + var15 * (1.0f - var16);
        }
        if ((var15 = this.getWeightedThunderStrength(par2)) > 0.0f) {
            var16 = (var11 * 0.3f + var12 * 0.59f + var13 * 0.11f) * 0.2f;
            float var17 = 1.0f - var15 * 0.75f;
            var11 = var11 * var17 + var16 * (1.0f - var17);
            var12 = var12 * var17 + var16 * (1.0f - var17);
            var13 = var13 * var17 + var16 * (1.0f - var17);
        }
        if (this.lastLightningBolt > 0) {
            var16 = (float)this.lastLightningBolt - par2;
            if (var16 > 1.0f) {
                var16 = 1.0f;
            }
            var11 = var11 * (1.0f - var16) + 0.8f * (var16 *= 0.45f);
            var12 = var12 * (1.0f - var16) + 0.8f * var16;
            var13 = var13 * (1.0f - var16) + 1.0f * var16;
        }
        return this.getWorldVec3Pool().getVecFromPool(var11, var12, var13);
    }

    public float getCelestialAngle(float par1) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), par1);
    }

    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }

    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }

    public float getCelestialAngleRadians(float par1) {
        float var2 = this.getCelestialAngle(par1);
        return var2 * 3.1415927f * 2.0f;
    }

    public Vec3 getCloudColour(float par1) {
        float var9;
        float var8;
        float var2 = this.getCelestialAngle(par1);
        float var3 = MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = (float)(this.cloudColour >> 16 & 255) / 255.0f;
        float var5 = (float)(this.cloudColour >> 8 & 255) / 255.0f;
        float var6 = (float)(this.cloudColour & 255) / 255.0f;
        float var7 = this.getRainStrength(par1);
        if (var7 > 0.0f) {
            var8 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.6f;
            var9 = 1.0f - var7 * 0.95f;
            var4 = var4 * var9 + var8 * (1.0f - var9);
            var5 = var5 * var9 + var8 * (1.0f - var9);
            var6 = var6 * var9 + var8 * (1.0f - var9);
        }
        var4 *= var3 * 0.9f + 0.1f;
        var5 *= var3 * 0.9f + 0.1f;
        var6 *= var3 * 0.85f + 0.15f;
        var8 = this.getWeightedThunderStrength(par1);
        if (var8 > 0.0f) {
            var9 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.2f;
            float var10 = 1.0f - var8 * 0.95f;
            var4 = var4 * var10 + var9 * (1.0f - var10);
            var5 = var5 * var10 + var9 * (1.0f - var10);
            var6 = var6 * var10 + var9 * (1.0f - var10);
        }
        return this.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
    }

    public Vec3 getFogColor(float par1) {
        float var2 = this.getCelestialAngle(par1);
        return this.provider.getFogColor(var2, par1);
    }

    public int getPrecipitationHeight(int par1, int par2) {
        return this.getChunkFromBlockCoords(par1, par2).getPrecipitationHeight(par1 & 15, par2 & 15);
    }

    public int getTopSolidOrLiquidBlock(int par1, int par2) {
        Chunk var3 = this.getChunkFromBlockCoords(par1, par2);
        int var4 = var3.getTopFilledSegment() + 15;
        par1 &= 15;
        par2 &= 15;
        while (var4 > 0) {
            Block var5 = var3.func_150810_a(par1, var4, par2);
            if (var5.getMaterial().blocksMovement() && var5.getMaterial() != Material.leaves) {
                return var4 + 1;
            }
            --var4;
        }
        return -1;
    }

    public float getStarBrightness(float par1) {
        float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.25f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return var3 * var3 * 0.5f;
    }

    public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_) {
    }

    public void func_147454_a(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_) {
    }

    public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_) {
    }

    public void updateEntities() {
        Entity var2;
        int var13;
        int var3;
        this.theProfiler.startSection("entities");
        this.theProfiler.startSection("global");
        int var1 = 0;
        while (var1 < this.weatherEffects.size()) {
            var2 = (Entity)this.weatherEffects.get(var1);
            try {
                ++var2.ticksExisted;
                var2.onUpdate();
            }
            catch (Throwable var8) {
                CrashReport var4 = CrashReport.makeCrashReport(var8, "Ticking entity");
                CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
                if (var2 == null) {
                    var5.addCrashSection("Entity", "~~NULL~~");
                } else {
                    var2.addEntityCrashInfo(var5);
                }
                throw new ReportedException(var4);
            }
            if (var2.isDead) {
                this.weatherEffects.remove(var1--);
            }
            ++var1;
        }
        this.theProfiler.endStartSection("remove");
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            var2 = (Entity)this.unloadedEntityList.get(var1);
            var3 = var2.chunkCoordX;
            var13 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var13)) {
                this.getChunkFromChunkCoords(var3, var13).removeEntity(var2);
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            this.onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
            ++var1;
        }
        this.unloadedEntityList.clear();
        this.theProfiler.endStartSection("regular");
        var1 = 0;
        while (var1 < this.loadedEntityList.size()) {
            block27 : {
                block26 : {
                    var2 = (Entity)this.loadedEntityList.get(var1);
                    if (var2.ridingEntity == null) break block26;
                    if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) break block27;
                    var2.ridingEntity.riddenByEntity = null;
                    var2.ridingEntity = null;
                }
                this.theProfiler.startSection("tick");
                if (!var2.isDead) {
                    try {
                        this.updateEntity(var2);
                    }
                    catch (Throwable var7) {
                        CrashReport var4 = CrashReport.makeCrashReport(var7, "Ticking entity");
                        CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
                        var2.addEntityCrashInfo(var5);
                        throw new ReportedException(var4);
                    }
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection("remove");
                if (var2.isDead) {
                    var3 = var2.chunkCoordX;
                    var13 = var2.chunkCoordZ;
                    if (var2.addedToChunk && this.chunkExists(var3, var13)) {
                        this.getChunkFromChunkCoords(var3, var13).removeEntity(var2);
                    }
                    this.loadedEntityList.remove(var1--);
                    this.onEntityRemoved(var2);
                }
                this.theProfiler.endSection();
            }
            ++var1;
        }
        this.theProfiler.endStartSection("blockEntities");
        this.field_147481_N = true;
        Iterator<E> var14 = this.field_147482_g.iterator();
        while (var14.hasNext()) {
            Chunk var11;
            TileEntity var9 = (TileEntity)var14.next();
            if (!var9.isInvalid() && var9.hasWorldObj() && this.blockExists(var9.field_145851_c, var9.field_145848_d, var9.field_145849_e)) {
                try {
                    var9.updateEntity();
                }
                catch (Throwable var6) {
                    CrashReport var4 = CrashReport.makeCrashReport(var6, "Ticking block entity");
                    CrashReportCategory var5 = var4.makeCategory("Block entity being ticked");
                    var9.func_145828_a(var5);
                    throw new ReportedException(var4);
                }
            }
            if (!var9.isInvalid()) continue;
            var14.remove();
            if (!this.chunkExists(var9.field_145851_c >> 4, var9.field_145849_e >> 4) || (var11 = this.getChunkFromChunkCoords(var9.field_145851_c >> 4, var9.field_145849_e >> 4)) == null) continue;
            var11.removeTileEntity(var9.field_145851_c & 15, var9.field_145848_d, var9.field_145849_e & 15);
        }
        this.field_147481_N = false;
        if (!this.field_147483_b.isEmpty()) {
            this.field_147482_g.removeAll(this.field_147483_b);
            this.field_147483_b.clear();
        }
        this.theProfiler.endStartSection("pendingBlockEntities");
        if (!this.field_147484_a.isEmpty()) {
            int var10 = 0;
            while (var10 < this.field_147484_a.size()) {
                TileEntity var12 = (TileEntity)this.field_147484_a.get(var10);
                if (!var12.isInvalid()) {
                    Chunk var15;
                    if (!this.field_147482_g.contains(var12)) {
                        this.field_147482_g.add(var12);
                    }
                    if (this.chunkExists(var12.field_145851_c >> 4, var12.field_145849_e >> 4) && (var15 = this.getChunkFromChunkCoords(var12.field_145851_c >> 4, var12.field_145849_e >> 4)) != null) {
                        var15.func_150812_a(var12.field_145851_c & 15, var12.field_145848_d, var12.field_145849_e & 15, var12);
                    }
                    this.func_147471_g(var12.field_145851_c, var12.field_145848_d, var12.field_145849_e);
                }
                ++var10;
            }
            this.field_147484_a.clear();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }

    public void func_147448_a(Collection p_147448_1_) {
        if (this.field_147481_N) {
            this.field_147484_a.addAll(p_147448_1_);
        } else {
            this.field_147482_g.addAll(p_147448_1_);
        }
    }

    public void updateEntity(Entity par1Entity) {
        this.updateEntityWithOptionalForce(par1Entity, true);
    }

    public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2) {
        int var3 = MathHelper.floor_double(par1Entity.posX);
        int var4 = MathHelper.floor_double(par1Entity.posZ);
        int var5 = 32;
        if (!par2 || this.checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5)) {
            par1Entity.lastTickPosX = par1Entity.posX;
            par1Entity.lastTickPosY = par1Entity.posY;
            par1Entity.lastTickPosZ = par1Entity.posZ;
            par1Entity.prevRotationYaw = par1Entity.rotationYaw;
            par1Entity.prevRotationPitch = par1Entity.rotationPitch;
            if (par2 && par1Entity.addedToChunk) {
                ++par1Entity.ticksExisted;
                if (par1Entity.ridingEntity != null) {
                    par1Entity.updateRidden();
                } else {
                    par1Entity.onUpdate();
                }
            }
            this.theProfiler.startSection("chunkCheck");
            if (Double.isNaN(par1Entity.posX) || Double.isInfinite(par1Entity.posX)) {
                par1Entity.posX = par1Entity.lastTickPosX;
            }
            if (Double.isNaN(par1Entity.posY) || Double.isInfinite(par1Entity.posY)) {
                par1Entity.posY = par1Entity.lastTickPosY;
            }
            if (Double.isNaN(par1Entity.posZ) || Double.isInfinite(par1Entity.posZ)) {
                par1Entity.posZ = par1Entity.lastTickPosZ;
            }
            if (Double.isNaN(par1Entity.rotationPitch) || Double.isInfinite(par1Entity.rotationPitch)) {
                par1Entity.rotationPitch = par1Entity.prevRotationPitch;
            }
            if (Double.isNaN(par1Entity.rotationYaw) || Double.isInfinite(par1Entity.rotationYaw)) {
                par1Entity.rotationYaw = par1Entity.prevRotationYaw;
            }
            int var6 = MathHelper.floor_double(par1Entity.posX / 16.0);
            int var7 = MathHelper.floor_double(par1Entity.posY / 16.0);
            int var8 = MathHelper.floor_double(par1Entity.posZ / 16.0);
            if (!par1Entity.addedToChunk || par1Entity.chunkCoordX != var6 || par1Entity.chunkCoordY != var7 || par1Entity.chunkCoordZ != var8) {
                if (par1Entity.addedToChunk && this.chunkExists(par1Entity.chunkCoordX, par1Entity.chunkCoordZ)) {
                    this.getChunkFromChunkCoords(par1Entity.chunkCoordX, par1Entity.chunkCoordZ).removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
                }
                if (this.chunkExists(var6, var8)) {
                    par1Entity.addedToChunk = true;
                    this.getChunkFromChunkCoords(var6, var8).addEntity(par1Entity);
                } else {
                    par1Entity.addedToChunk = false;
                }
            }
            this.theProfiler.endSection();
            if (par2 && par1Entity.addedToChunk && par1Entity.riddenByEntity != null) {
                if (!par1Entity.riddenByEntity.isDead && par1Entity.riddenByEntity.ridingEntity == par1Entity) {
                    this.updateEntity(par1Entity.riddenByEntity);
                } else {
                    par1Entity.riddenByEntity.ridingEntity = null;
                    par1Entity.riddenByEntity = null;
                }
            }
        }
    }

    public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB) {
        return this.checkNoEntityCollision(par1AxisAlignedBB, null);
    }

    public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity) {
        List var3 = this.getEntitiesWithinAABBExcludingEntity(null, par1AxisAlignedBB);
        int var4 = 0;
        while (var4 < var3.size()) {
            Entity var5 = (Entity)var3.get(var4);
            if (!var5.isDead && var5.preventEntitySpawning && var5 != par2Entity) {
                return false;
            }
            ++var4;
        }
        return true;
    }

    public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB) {
        int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (par1AxisAlignedBB.minX < 0.0) {
            --var2;
        }
        if (par1AxisAlignedBB.minY < 0.0) {
            --var4;
        }
        if (par1AxisAlignedBB.minZ < 0.0) {
            --var6;
        }
        int var8 = var2;
        while (var8 < var3) {
            int var9 = var4;
            while (var9 < var5) {
                int var10 = var6;
                while (var10 < var7) {
                    Block var11 = this.getBlock(var8, var9, var10);
                    if (var11.getMaterial() != Material.air) {
                        return true;
                    }
                    ++var10;
                }
                ++var9;
            }
            ++var8;
        }
        return false;
    }

    public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB) {
        int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (par1AxisAlignedBB.minX < 0.0) {
            --var2;
        }
        if (par1AxisAlignedBB.minY < 0.0) {
            --var4;
        }
        if (par1AxisAlignedBB.minZ < 0.0) {
            --var6;
        }
        int var8 = var2;
        while (var8 < var3) {
            int var9 = var4;
            while (var9 < var5) {
                int var10 = var6;
                while (var10 < var7) {
                    Block var11 = this.getBlock(var8, var9, var10);
                    if (var11.getMaterial().isLiquid()) {
                        return true;
                    }
                    ++var10;
                }
                ++var9;
            }
            ++var8;
        }
        return false;
    }

    public boolean func_147470_e(AxisAlignedBB p_147470_1_) {
        int var7;
        int var2 = MathHelper.floor_double(p_147470_1_.minX);
        int var3 = MathHelper.floor_double(p_147470_1_.maxX + 1.0);
        int var4 = MathHelper.floor_double(p_147470_1_.minY);
        int var5 = MathHelper.floor_double(p_147470_1_.maxY + 1.0);
        int var6 = MathHelper.floor_double(p_147470_1_.minZ);
        if (this.checkChunksExist(var2, var4, var6, var3, var5, var7 = MathHelper.floor_double(p_147470_1_.maxZ + 1.0))) {
            int var8 = var2;
            while (var8 < var3) {
                int var9 = var4;
                while (var9 < var5) {
                    int var10 = var6;
                    while (var10 < var7) {
                        Block var11 = this.getBlock(var8, var9, var10);
                        if (var11 == Blocks.fire || var11 == Blocks.flowing_lava || var11 == Blocks.lava) {
                            return true;
                        }
                        ++var10;
                    }
                    ++var9;
                }
                ++var8;
            }
        }
        return false;
    }

    public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity) {
        int var9;
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        if (!this.checkChunksExist(var4, var6, var8, var5, var7, var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0))) {
            return false;
        }
        boolean var10 = false;
        Vec3 var11 = this.getWorldVec3Pool().getVecFromPool(0.0, 0.0, 0.0);
        int var12 = var4;
        while (var12 < var5) {
            int var13 = var6;
            while (var13 < var7) {
                int var14 = var8;
                while (var14 < var9) {
                    double var16;
                    Block var15 = this.getBlock(var12, var13, var14);
                    if (var15.getMaterial() == par2Material && (double)var7 >= (var16 = (double)((float)(var13 + 1) - BlockLiquid.func_149801_b(this.getBlockMetadata(var12, var13, var14))))) {
                        var10 = true;
                        var15.velocityToAddToEntity(this, var12, var13, var14, par3Entity, var11);
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
        if (var11.lengthVector() > 0.0 && par3Entity.isPushedByWater()) {
            var11 = var11.normalize();
            double var18 = 0.014;
            par3Entity.motionX += var11.xCoord * var18;
            par3Entity.motionY += var11.yCoord * var18;
            par3Entity.motionZ += var11.zCoord * var18;
        }
        return var10;
    }

    public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
        int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var5;
            while (var10 < var6) {
                int var11 = var7;
                while (var11 < var8) {
                    if (this.getBlock(var9, var10, var11).getMaterial() == par2Material) {
                        return true;
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return false;
    }

    public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
        int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var5;
            while (var10 < var6) {
                int var11 = var7;
                while (var11 < var8) {
                    Block var12 = this.getBlock(var9, var10, var11);
                    if (var12.getMaterial() == par2Material) {
                        int var13 = this.getBlockMetadata(var9, var10, var11);
                        double var14 = var10 + 1;
                        if (var13 < 8) {
                            var14 = (double)(var10 + 1) - (double)var13 / 8.0;
                        }
                        if (var14 >= par1AxisAlignedBB.minY) {
                            return true;
                        }
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return false;
    }

    public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9) {
        return this.newExplosion(par1Entity, par2, par4, par6, par8, false, par9);
    }

    public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10) {
        Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionA();
        var11.doExplosionB(true);
        return var11;
    }

    public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB) {
        double var3 = 1.0 / ((par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * 2.0 + 1.0);
        double var5 = 1.0 / ((par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * 2.0 + 1.0);
        double var7 = 1.0 / ((par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * 2.0 + 1.0);
        int var9 = 0;
        int var10 = 0;
        float var11 = 0.0f;
        while (var11 <= 1.0f) {
            float var12 = 0.0f;
            while (var12 <= 1.0f) {
                float var13 = 0.0f;
                while (var13 <= 1.0f) {
                    double var14 = par2AxisAlignedBB.minX + (par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * (double)var11;
                    double var16 = par2AxisAlignedBB.minY + (par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * (double)var12;
                    double var18 = par2AxisAlignedBB.minZ + (par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * (double)var13;
                    if (this.rayTraceBlocks(this.getWorldVec3Pool().getVecFromPool(var14, var16, var18), par1Vec3) == null) {
                        ++var9;
                    }
                    ++var10;
                    var13 = (float)((double)var13 + var7);
                }
                var12 = (float)((double)var12 + var5);
            }
            var11 = (float)((double)var11 + var3);
        }
        return (float)var9 / (float)var10;
    }

    public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5) {
        if (par5 == 0) {
            --par3;
        }
        if (par5 == 1) {
            ++par3;
        }
        if (par5 == 2) {
            --par4;
        }
        if (par5 == 3) {
            ++par4;
        }
        if (par5 == 4) {
            --par2;
        }
        if (par5 == 5) {
            ++par2;
        }
        if (this.getBlock(par2, par3, par4) == Blocks.fire) {
            this.playAuxSFXAtEntity(par1EntityPlayer, 1004, par2, par3, par4, 0);
            this.setBlockToAir(par2, par3, par4);
            return true;
        }
        return false;
    }

    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntityList.size();
    }

    public String getProviderName() {
        return this.chunkProvider.makeString();
    }

    @Override
    public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
        if (p_147438_2_ >= 0 && p_147438_2_ < 256) {
            int var5;
            Chunk var7;
            TileEntity var6;
            TileEntity var4 = null;
            if (this.field_147481_N) {
                var5 = 0;
                while (var5 < this.field_147484_a.size()) {
                    var6 = (TileEntity)this.field_147484_a.get(var5);
                    if (!var6.isInvalid() && var6.field_145851_c == p_147438_1_ && var6.field_145848_d == p_147438_2_ && var6.field_145849_e == p_147438_3_) {
                        var4 = var6;
                        break;
                    }
                    ++var5;
                }
            }
            if (var4 == null && (var7 = this.getChunkFromChunkCoords(p_147438_1_ >> 4, p_147438_3_ >> 4)) != null) {
                var4 = var7.func_150806_e(p_147438_1_ & 15, p_147438_2_, p_147438_3_ & 15);
            }
            if (var4 == null) {
                var5 = 0;
                while (var5 < this.field_147484_a.size()) {
                    var6 = (TileEntity)this.field_147484_a.get(var5);
                    if (!var6.isInvalid() && var6.field_145851_c == p_147438_1_ && var6.field_145848_d == p_147438_2_ && var6.field_145849_e == p_147438_3_) {
                        var4 = var6;
                        break;
                    }
                    ++var5;
                }
            }
            return var4;
        }
        return null;
    }

    public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_) {
        if (p_147455_4_ != null && !p_147455_4_.isInvalid()) {
            if (this.field_147481_N) {
                p_147455_4_.field_145851_c = p_147455_1_;
                p_147455_4_.field_145848_d = p_147455_2_;
                p_147455_4_.field_145849_e = p_147455_3_;
                Iterator<E> var5 = this.field_147484_a.iterator();
                while (var5.hasNext()) {
                    TileEntity var6 = (TileEntity)var5.next();
                    if (var6.field_145851_c != p_147455_1_ || var6.field_145848_d != p_147455_2_ || var6.field_145849_e != p_147455_3_) continue;
                    var6.invalidate();
                    var5.remove();
                }
                this.field_147484_a.add(p_147455_4_);
            } else {
                this.field_147482_g.add(p_147455_4_);
                Chunk var7 = this.getChunkFromChunkCoords(p_147455_1_ >> 4, p_147455_3_ >> 4);
                if (var7 != null) {
                    var7.func_150812_a(p_147455_1_ & 15, p_147455_2_, p_147455_3_ & 15, p_147455_4_);
                }
            }
        }
    }

    public void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_) {
        TileEntity var4 = this.getTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
        if (var4 != null && this.field_147481_N) {
            var4.invalidate();
            this.field_147484_a.remove(var4);
        } else {
            Chunk var5;
            if (var4 != null) {
                this.field_147484_a.remove(var4);
                this.field_147482_g.remove(var4);
            }
            if ((var5 = this.getChunkFromChunkCoords(p_147475_1_ >> 4, p_147475_3_ >> 4)) != null) {
                var5.removeTileEntity(p_147475_1_ & 15, p_147475_2_, p_147475_3_ & 15);
            }
        }
    }

    public void func_147457_a(TileEntity p_147457_1_) {
        this.field_147483_b.add(p_147457_1_);
    }

    public boolean func_147469_q(int p_147469_1_, int p_147469_2_, int p_147469_3_) {
        AxisAlignedBB var4 = this.getBlock(p_147469_1_, p_147469_2_, p_147469_3_).getCollisionBoundingBoxFromPool(this, p_147469_1_, p_147469_2_, p_147469_3_);
        if (var4 != null && var4.getAverageEdgeLength() >= 1.0) {
            return true;
        }
        return false;
    }

    public static boolean doesBlockHaveSolidTopSurface(IBlockAccess p_147466_0_, int p_147466_1_, int p_147466_2_, int p_147466_3_) {
        Block var4 = p_147466_0_.getBlock(p_147466_1_, p_147466_2_, p_147466_3_);
        int var5 = p_147466_0_.getBlockMetadata(p_147466_1_, p_147466_2_, p_147466_3_);
        return var4.getMaterial().isOpaque() && var4.renderAsNormalBlock() ? true : (var4 instanceof BlockStairs ? (var5 & 4) == 4 : (var4 instanceof BlockSlab ? (var5 & 8) == 8 : (var4 instanceof BlockHopper ? true : (var4 instanceof BlockSnow ? (var5 & 7) == 7 : false))));
    }

    public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_) {
        if (p_147445_1_ >= -30000000 && p_147445_3_ >= -30000000 && p_147445_1_ < 30000000 && p_147445_3_ < 30000000) {
            Chunk var5 = this.chunkProvider.provideChunk(p_147445_1_ >> 4, p_147445_3_ >> 4);
            if (var5 != null && !var5.isEmpty()) {
                Block var6 = this.getBlock(p_147445_1_, p_147445_2_, p_147445_3_);
                if (var6.getMaterial().isOpaque() && var6.renderAsNormalBlock()) {
                    return true;
                }
                return false;
            }
            return p_147445_4_;
        }
        return p_147445_4_;
    }

    public void calculateInitialSkylight() {
        int var1 = this.calculateSkylightSubtracted(1.0f);
        if (var1 != this.skylightSubtracted) {
            this.skylightSubtracted = var1;
        }
    }

    public void setAllowedSpawnTypes(boolean par1, boolean par2) {
        this.spawnHostileMobs = par1;
        this.spawnPeacefulMobs = par2;
    }

    public void tick() {
        this.updateWeather();
    }

    private void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }

    protected void updateWeather() {
        if (!this.provider.hasNoSky && !this.isClient) {
            int var1 = this.worldInfo.getThunderTime();
            if (var1 <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                } else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                }
            } else {
                this.worldInfo.setThunderTime(--var1);
                if (var1 <= 0) {
                    this.worldInfo.setThundering(!this.worldInfo.isThundering());
                }
            }
            this.prevThunderingStrength = this.thunderingStrength;
            this.thunderingStrength = this.worldInfo.isThundering() ? (float)((double)this.thunderingStrength + 0.01) : (float)((double)this.thunderingStrength - 0.01);
            this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0f, 1.0f);
            int var2 = this.worldInfo.getRainTime();
            if (var2 <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                } else {
                    this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                }
            } else {
                this.worldInfo.setRainTime(--var2);
                if (var2 <= 0) {
                    this.worldInfo.setRaining(!this.worldInfo.isRaining());
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            this.rainingStrength = this.worldInfo.isRaining() ? (float)((double)this.rainingStrength + 0.01) : (float)((double)this.rainingStrength - 0.01);
            this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0f, 1.0f);
        }
    }

    protected void setActivePlayerChunksAndCheckLight() {
        int var3;
        int var4;
        EntityPlayer var2;
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        int var1 = 0;
        while (var1 < this.playerEntities.size()) {
            var2 = (EntityPlayer)this.playerEntities.get(var1);
            var3 = MathHelper.floor_double(var2.posX / 16.0);
            var4 = MathHelper.floor_double(var2.posZ / 16.0);
            int var5 = 7;
            int var6 = - var5;
            while (var6 <= var5) {
                int var7 = - var5;
                while (var7 <= var5) {
                    this.activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                    ++var7;
                }
                ++var6;
            }
            ++var1;
        }
        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            --this.ambientTickCountdown;
        }
        this.theProfiler.startSection("playerCheckLight");
        if (!this.playerEntities.isEmpty()) {
            var1 = this.rand.nextInt(this.playerEntities.size());
            var2 = (EntityPlayer)this.playerEntities.get(var1);
            var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
            var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
            int var8 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
            this.func_147451_t(var3, var4, var8);
        }
        this.theProfiler.endSection();
    }

    protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isClient) {
            EntityPlayer var9;
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            int var4 = this.updateLCG >> 2;
            int var5 = var4 & 15;
            int var6 = var4 >> 8 & 15;
            int var7 = var4 >> 16 & 255;
            Block var8 = p_147467_3_.func_150810_a(var5, var7, var6);
            if (var8.getMaterial() == Material.air && this.getFullBlockLightValue(var5, var7, var6) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, var5, var7, var6) <= 0 && (var9 = this.getClosestPlayer((double)var5 + 0.5, (double)var7 + 0.5, (double)var6 + 0.5, 8.0)) != null && var9.getDistanceSq((double)(var5 += p_147467_1_) + 0.5, (double)var7 + 0.5, (double)(var6 += p_147467_2_) + 0.5) > 4.0) {
                this.playSoundEffect((double)var5 + 0.5, (double)var7 + 0.5, (double)var6 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
            }
        }
        this.theProfiler.endStartSection("checkLight");
        p_147467_3_.enqueueRelightChecks();
    }

    protected void func_147456_g() {
        this.setActivePlayerChunksAndCheckLight();
    }

    public boolean isBlockFreezable(int par1, int par2, int par3) {
        return this.canBlockFreeze(par1, par2, par3, false);
    }

    public boolean isBlockFreezableNaturally(int par1, int par2, int par3) {
        return this.canBlockFreeze(par1, par2, par3, true);
    }

    public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4) {
        Block var7;
        BiomeGenBase var5 = this.getBiomeGenForCoords(par1, par3);
        float var6 = var5.getFloatTemperature(par1, par2, par3);
        if (var6 > 0.15f) {
            return false;
        }
        if (par2 >= 0 && par2 < 256 && this.getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10 && ((var7 = this.getBlock(par1, par2, par3)) == Blocks.water || var7 == Blocks.flowing_water) && this.getBlockMetadata(par1, par2, par3) == 0) {
            if (!par4) {
                return true;
            }
            boolean var8 = true;
            if (var8 && this.getBlock(par1 - 1, par2, par3).getMaterial() != Material.water) {
                var8 = false;
            }
            if (var8 && this.getBlock(par1 + 1, par2, par3).getMaterial() != Material.water) {
                var8 = false;
            }
            if (var8 && this.getBlock(par1, par2, par3 - 1).getMaterial() != Material.water) {
                var8 = false;
            }
            if (var8 && this.getBlock(par1, par2, par3 + 1).getMaterial() != Material.water) {
                var8 = false;
            }
            if (!var8) {
                return true;
            }
        }
        return false;
    }

    public boolean func_147478_e(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_) {
        Block var7;
        BiomeGenBase var5 = this.getBiomeGenForCoords(p_147478_1_, p_147478_3_);
        float var6 = var5.getFloatTemperature(p_147478_1_, p_147478_2_, p_147478_3_);
        if (var6 > 0.15f) {
            return false;
        }
        if (!p_147478_4_) {
            return true;
        }
        if (p_147478_2_ >= 0 && p_147478_2_ < 256 && this.getSavedLightValue(EnumSkyBlock.Block, p_147478_1_, p_147478_2_, p_147478_3_) < 10 && (var7 = this.getBlock(p_147478_1_, p_147478_2_, p_147478_3_)).getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, p_147478_1_, p_147478_2_, p_147478_3_)) {
            return true;
        }
        return false;
    }

    public boolean func_147451_t(int p_147451_1_, int p_147451_2_, int p_147451_3_) {
        boolean var4 = false;
        if (!this.provider.hasNoSky) {
            var4 |= this.updateLightByType(EnumSkyBlock.Sky, p_147451_1_, p_147451_2_, p_147451_3_);
        }
        return var4 |= this.updateLightByType(EnumSkyBlock.Block, p_147451_1_, p_147451_2_, p_147451_3_);
    }

    private int computeLightValue(int par1, int par2, int par3, EnumSkyBlock par4EnumSkyBlock) {
        if (par4EnumSkyBlock == EnumSkyBlock.Sky && this.canBlockSeeTheSky(par1, par2, par3)) {
            return 15;
        }
        Block var5 = this.getBlock(par1, par2, par3);
        int var6 = par4EnumSkyBlock == EnumSkyBlock.Sky ? 0 : var5.getLightValue();
        int var7 = var5.getLightOpacity();
        if (var7 >= 15 && var5.getLightValue() > 0) {
            var7 = 1;
        }
        if (var7 < 1) {
            var7 = 1;
        }
        if (var7 >= 15) {
            return 0;
        }
        if (var6 >= 14) {
            return var6;
        }
        int var8 = 0;
        while (var8 < 6) {
            int var9 = par1 + Facing.offsetsXForSide[var8];
            int var10 = par2 + Facing.offsetsYForSide[var8];
            int var11 = par3 + Facing.offsetsZForSide[var8];
            int var12 = this.getSavedLightValue(par4EnumSkyBlock, var9, var10, var11) - var7;
            if (var12 > var6) {
                var6 = var12;
            }
            if (var6 >= 14) {
                return var6;
            }
            ++var8;
        }
        return var6;
    }

    public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_) {
        int var9;
        int var11;
        int var10;
        int var16;
        int var15;
        int var17;
        int var13;
        int var14;
        int var12;
        if (!this.doChunksNearChunkExist(p_147463_2_, p_147463_3_, p_147463_4_, 17)) {
            return false;
        }
        int var5 = 0;
        int var6 = 0;
        this.theProfiler.startSection("getBrightness");
        int var7 = this.getSavedLightValue(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
        int var8 = this.computeLightValue(p_147463_2_, p_147463_3_, p_147463_4_, p_147463_1_);
        if (var8 > var7) {
            this.lightUpdateBlockList[var6++] = 133152;
        } else if (var8 < var7) {
            this.lightUpdateBlockList[var6++] = 133152 | var7 << 18;
            while (var5 < var6) {
                var9 = this.lightUpdateBlockList[var5++];
                var10 = (var9 & 63) - 32 + p_147463_2_;
                var11 = (var9 >> 6 & 63) - 32 + p_147463_3_;
                var12 = (var9 >> 12 & 63) - 32 + p_147463_4_;
                var13 = var9 >> 18 & 15;
                var14 = this.getSavedLightValue(p_147463_1_, var10, var11, var12);
                if (var14 != var13) continue;
                this.setLightValue(p_147463_1_, var10, var11, var12, 0);
                if (var13 <= 0 || (var15 = MathHelper.abs_int(var10 - p_147463_2_)) + (var16 = MathHelper.abs_int(var11 - p_147463_3_)) + (var17 = MathHelper.abs_int(var12 - p_147463_4_)) >= 17) continue;
                int var18 = 0;
                while (var18 < 6) {
                    int var19 = var10 + Facing.offsetsXForSide[var18];
                    int var20 = var11 + Facing.offsetsYForSide[var18];
                    int var21 = var12 + Facing.offsetsZForSide[var18];
                    int var22 = Math.max(1, this.getBlock(var19, var20, var21).getLightOpacity());
                    var14 = this.getSavedLightValue(p_147463_1_, var19, var20, var21);
                    if (var14 == var13 - var22 && var6 < this.lightUpdateBlockList.length) {
                        this.lightUpdateBlockList[var6++] = var19 - p_147463_2_ + 32 | var20 - p_147463_3_ + 32 << 6 | var21 - p_147463_4_ + 32 << 12 | var13 - var22 << 18;
                    }
                    ++var18;
                }
            }
            var5 = 0;
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("checkedPosition < toCheckCount");
        while (var5 < var6) {
            boolean var23;
            var9 = this.lightUpdateBlockList[var5++];
            var10 = (var9 & 63) - 32 + p_147463_2_;
            var11 = (var9 >> 6 & 63) - 32 + p_147463_3_;
            var12 = (var9 >> 12 & 63) - 32 + p_147463_4_;
            var13 = this.getSavedLightValue(p_147463_1_, var10, var11, var12);
            var14 = this.computeLightValue(var10, var11, var12, p_147463_1_);
            if (var14 == var13) continue;
            this.setLightValue(p_147463_1_, var10, var11, var12, var14);
            if (var14 <= var13) continue;
            var15 = Math.abs(var10 - p_147463_2_);
            var16 = Math.abs(var11 - p_147463_3_);
            var17 = Math.abs(var12 - p_147463_4_);
            boolean bl = var23 = var6 < this.lightUpdateBlockList.length - 6;
            if (var15 + var16 + var17 >= 17 || !var23) continue;
            if (this.getSavedLightValue(p_147463_1_, var10 - 1, var11, var12) < var14) {
                this.lightUpdateBlockList[var6++] = var10 - 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
            }
            if (this.getSavedLightValue(p_147463_1_, var10 + 1, var11, var12) < var14) {
                this.lightUpdateBlockList[var6++] = var10 + 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
            }
            if (this.getSavedLightValue(p_147463_1_, var10, var11 - 1, var12) < var14) {
                this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
            }
            if (this.getSavedLightValue(p_147463_1_, var10, var11 + 1, var12) < var14) {
                this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 + 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
            }
            if (this.getSavedLightValue(p_147463_1_, var10, var11, var12 - 1) < var14) {
                this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - 1 - p_147463_4_ + 32 << 12);
            }
            if (this.getSavedLightValue(p_147463_1_, var10, var11, var12 + 1) >= var14) continue;
            this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 + 1 - p_147463_4_ + 32 << 12);
        }
        this.theProfiler.endSection();
        return true;
    }

    public boolean tickUpdates(boolean par1) {
        return false;
    }

    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
        return null;
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
        return this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, null);
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector) {
        ArrayList<E> var4 = new ArrayList<E>();
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0) / 16.0);
        int var7 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0) / 16.0);
        int var8 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0) / 16.0);
        int var9 = var5;
        while (var9 <= var6) {
            int var10 = var7;
            while (var10 <= var8) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(par1Entity, par2AxisAlignedBB, var4, par3IEntitySelector);
                }
                ++var10;
            }
            ++var9;
        }
        return var4;
    }

    public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB) {
        return this.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, null);
    }

    public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector) {
        int var4 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0) / 16.0);
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0) / 16.0);
        int var7 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0) / 16.0);
        ArrayList<E> var8 = new ArrayList<E>();
        int var9 = var4;
        while (var9 <= var5) {
            int var10 = var6;
            while (var10 <= var7) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(par1Class, par2AxisAlignedBB, var8, par3IEntitySelector);
                }
                ++var10;
            }
            ++var9;
        }
        return var8;
    }

    public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity) {
        List var4 = this.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
        Entity var5 = null;
        double var6 = Double.MAX_VALUE;
        int var8 = 0;
        while (var8 < var4.size()) {
            double var10;
            Entity var9 = (Entity)var4.get(var8);
            if (var9 != par3Entity && (var10 = par3Entity.getDistanceSqToEntity(var9)) <= var6) {
                var5 = var9;
                var6 = var10;
            }
            ++var8;
        }
        return var5;
    }

    public abstract Entity getEntityByID(int var1);

    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }

    public void func_147476_b(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_) {
        if (this.blockExists(p_147476_1_, p_147476_2_, p_147476_3_)) {
            this.getChunkFromBlockCoords(p_147476_1_, p_147476_3_).setChunkModified();
        }
    }

    public int countEntities(Class par1Class) {
        int var2 = 0;
        int var3 = 0;
        while (var3 < this.loadedEntityList.size()) {
            Entity var4 = (Entity)this.loadedEntityList.get(var3);
            if (!(var4 instanceof EntityLiving && ((EntityLiving)var4).isNoDespawnRequired() || !par1Class.isAssignableFrom(var4.getClass()))) {
                ++var2;
            }
            ++var3;
        }
        return var2;
    }

    public void addLoadedEntities(List par1List) {
        this.loadedEntityList.addAll(par1List);
        int var2 = 0;
        while (var2 < par1List.size()) {
            this.onEntityAdded((Entity)par1List.get(var2));
            ++var2;
        }
    }

    public void unloadEntities(List par1List) {
        this.unloadedEntityList.addAll(par1List);
    }

    public boolean canPlaceEntityOnSide(Block p_147472_1_, int p_147472_2_, int p_147472_3_, int p_147472_4_, boolean p_147472_5_, int p_147472_6_, Entity p_147472_7_, ItemStack p_147472_8_) {
        AxisAlignedBB var10;
        Block var9 = this.getBlock(p_147472_2_, p_147472_3_, p_147472_4_);
        AxisAlignedBB axisAlignedBB = var10 = p_147472_5_ ? null : p_147472_1_.getCollisionBoundingBoxFromPool(this, p_147472_2_, p_147472_3_, p_147472_4_);
        return var10 != null && !this.checkNoEntityCollision(var10, p_147472_7_) ? false : (var9.getMaterial() == Material.circuits && p_147472_1_ == Blocks.anvil ? true : var9.getMaterial().isReplaceable() && p_147472_1_.canReplace(this, p_147472_2_, p_147472_3_, p_147472_4_, p_147472_6_, p_147472_8_));
    }

    public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7) {
        this.theProfiler.startSection("pathfind");
        int var8 = MathHelper.floor_double(par1Entity.posX);
        int var9 = MathHelper.floor_double(par1Entity.posY + 1.0);
        int var10 = MathHelper.floor_double(par1Entity.posZ);
        int var11 = (int)(par3 + 16.0f);
        int var12 = var8 - var11;
        int var13 = var9 - var11;
        int var14 = var10 - var11;
        int var15 = var8 + var11;
        int var16 = var9 + var11;
        int var17 = var10 + var11;
        ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
        PathEntity var19 = new PathFinder(var18, par4, par5, par6, par7).createEntityPathTo(par1Entity, par2Entity, par3);
        this.theProfiler.endSection();
        return var19;
    }

    public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9) {
        this.theProfiler.startSection("pathfind");
        int var10 = MathHelper.floor_double(par1Entity.posX);
        int var11 = MathHelper.floor_double(par1Entity.posY);
        int var12 = MathHelper.floor_double(par1Entity.posZ);
        int var13 = (int)(par5 + 8.0f);
        int var14 = var10 - var13;
        int var15 = var11 - var13;
        int var16 = var12 - var13;
        int var17 = var10 + var13;
        int var18 = var11 + var13;
        int var19 = var12 + var13;
        ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
        PathEntity var21 = new PathFinder(var20, par6, par7, par8, par9).createEntityPathTo(par1Entity, par2, par3, par4, par5);
        this.theProfiler.endSection();
        return var21;
    }

    @Override
    public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4) {
        return this.getBlock(par1, par2, par3).isProvidingStrongPower(this, par1, par2, par3, par4);
    }

    public int getBlockPowerInput(int par1, int par2, int par3) {
        int var4 = 0;
        int var5 = Math.max(var4, this.isBlockProvidingPowerTo(par1, par2 - 1, par3, 0));
        if (var5 >= 15) {
            return var5;
        }
        if ((var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2 + 1, par3, 1))) >= 15) {
            return var5;
        }
        if ((var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2, par3 - 1, 2))) >= 15) {
            return var5;
        }
        if ((var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2, par3 + 1, 3))) >= 15) {
            return var5;
        }
        if ((var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1 - 1, par2, par3, 4))) >= 15) {
            return var5;
        }
        return (var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1 + 1, par2, par3, 5))) >= 15 ? var5 : var5;
    }

    public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4) {
        if (this.getIndirectPowerLevelTo(par1, par2, par3, par4) > 0) {
            return true;
        }
        return false;
    }

    public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4) {
        return this.getBlock(par1, par2, par3).isNormalCube() ? this.getBlockPowerInput(par1, par2, par3) : this.getBlock(par1, par2, par3).isProvidingWeakPower(this, par1, par2, par3, par4);
    }

    public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3) {
        return this.getIndirectPowerLevelTo(par1, par2 - 1, par3, 0) > 0 ? true : (this.getIndirectPowerLevelTo(par1, par2 + 1, par3, 1) > 0 ? true : (this.getIndirectPowerLevelTo(par1, par2, par3 - 1, 2) > 0 ? true : (this.getIndirectPowerLevelTo(par1, par2, par3 + 1, 3) > 0 ? true : (this.getIndirectPowerLevelTo(par1 - 1, par2, par3, 4) > 0 ? true : this.getIndirectPowerLevelTo(par1 + 1, par2, par3, 5) > 0))));
    }

    public int getStrongestIndirectPower(int par1, int par2, int par3) {
        int var4 = 0;
        int var5 = 0;
        while (var5 < 6) {
            int var6 = this.getIndirectPowerLevelTo(par1 + Facing.offsetsXForSide[var5], par2 + Facing.offsetsYForSide[var5], par3 + Facing.offsetsZForSide[var5], var5);
            if (var6 >= 15) {
                return 15;
            }
            if (var6 > var4) {
                var4 = var6;
            }
            ++var5;
        }
        return var4;
    }

    public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2) {
        return this.getClosestPlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
    }

    public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7) {
        double var9 = -1.0;
        EntityPlayer var11 = null;
        int var12 = 0;
        while (var12 < this.playerEntities.size()) {
            EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
            double var14 = var13.getDistanceSq(par1, par3, par5);
            if (!(par7 >= 0.0 && var14 >= par7 * par7 || var9 != -1.0 && var14 >= var9)) {
                var9 = var14;
                var11 = var13;
            }
            ++var12;
        }
        return var11;
    }

    public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2) {
        return this.getClosestVulnerablePlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
    }

    public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7) {
        double var9 = -1.0;
        EntityPlayer var11 = null;
        int var12 = 0;
        while (var12 < this.playerEntities.size()) {
            EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
            if (!var13.capabilities.disableDamage && var13.isEntityAlive()) {
                double var14 = var13.getDistanceSq(par1, par3, par5);
                double var16 = par7;
                if (var13.isSneaking()) {
                    var16 = par7 * 0.800000011920929;
                }
                if (var13.isInvisible()) {
                    float var18 = var13.getArmorVisibility();
                    if (var18 < 0.1f) {
                        var18 = 0.1f;
                    }
                    var16 *= (double)(0.7f * var18);
                }
                if (!(par7 >= 0.0 && var14 >= var16 * var16 || var9 != -1.0 && var14 >= var9)) {
                    var9 = var14;
                    var11 = var13;
                }
            }
            ++var12;
        }
        return var11;
    }

    public EntityPlayer getPlayerEntityByName(String par1Str) {
        int var2 = 0;
        while (var2 < this.playerEntities.size()) {
            if (par1Str.equals(((EntityPlayer)this.playerEntities.get(var2)).getCommandSenderName())) {
                return (EntityPlayer)this.playerEntities.get(var2);
            }
            ++var2;
        }
        return null;
    }

    public void sendQuittingDisconnectingPacket() {
    }

    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }

    public void func_82738_a(long par1) {
        this.worldInfo.incrementTotalWorldTime(par1);
    }

    public long getSeed() {
        return this.worldInfo.getSeed();
    }

    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }

    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }

    public void setWorldTime(long par1) {
        this.worldInfo.setWorldTime(par1);
    }

    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    }

    public void setSpawnLocation(int par1, int par2, int par3) {
        this.worldInfo.setSpawnPosition(par1, par2, par3);
    }

    public void joinEntityInSurroundings(Entity par1Entity) {
        int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        int var4 = 2;
        int var5 = var2 - var4;
        while (var5 <= var2 + var4) {
            int var6 = var3 - var4;
            while (var6 <= var3 + var4) {
                this.getChunkFromChunkCoords(var5, var6);
                ++var6;
            }
            ++var5;
        }
        if (!this.loadedEntityList.contains(par1Entity)) {
            this.loadedEntityList.add(par1Entity);
        }
    }

    public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
        return true;
    }

    public void setEntityState(Entity par1Entity, byte par2) {
    }

    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }

    public void func_147452_c(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_) {
        p_147452_4_.onBlockEventReceived(this, p_147452_1_, p_147452_2_, p_147452_3_, p_147452_5_, p_147452_6_);
    }

    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }

    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }

    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }

    public void updateAllPlayersSleepingFlag() {
    }

    public float getWeightedThunderStrength(float par1) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * par1) * this.getRainStrength(par1);
    }

    public void setThunderStrength(float p_147442_1_) {
        this.prevThunderingStrength = p_147442_1_;
        this.thunderingStrength = p_147442_1_;
    }

    public float getRainStrength(float par1) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * par1;
    }

    public void setRainStrength(float par1) {
        this.prevRainingStrength = par1;
        this.rainingStrength = par1;
    }

    public boolean isThundering() {
        if ((double)this.getWeightedThunderStrength(1.0f) > 0.9) {
            return true;
        }
        return false;
    }

    public boolean isRaining() {
        if ((double)this.getRainStrength(1.0f) > 0.2) {
            return true;
        }
        return false;
    }

    public boolean canLightningStrikeAt(int par1, int par2, int par3) {
        if (!this.isRaining()) {
            return false;
        }
        if (!this.canBlockSeeTheSky(par1, par2, par3)) {
            return false;
        }
        if (this.getPrecipitationHeight(par1, par3) > par2) {
            return false;
        }
        BiomeGenBase var4 = this.getBiomeGenForCoords(par1, par3);
        return var4.getEnableSnow() ? false : (this.func_147478_e(par1, par2, par3, false) ? false : var4.canSpawnLightningBolt());
    }

    public boolean isBlockHighHumidity(int par1, int par2, int par3) {
        BiomeGenBase var4 = this.getBiomeGenForCoords(par1, par3);
        return var4.isHighHumidity();
    }

    public void setItemData(String par1Str, WorldSavedData par2WorldSavedData) {
        this.mapStorage.setData(par1Str, par2WorldSavedData);
    }

    public WorldSavedData loadItemData(Class par1Class, String par2Str) {
        return this.mapStorage.loadData(par1Class, par2Str);
    }

    public int getUniqueDataId(String par1Str) {
        return this.mapStorage.getUniqueDataId(par1Str);
    }

    public void playBroadcastSound(int par1, int par2, int par3, int par4, int par5) {
        int var6 = 0;
        while (var6 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var6)).broadcastSound(par1, par2, par3, par4, par5);
            ++var6;
        }
    }

    public void playAuxSFX(int par1, int par2, int par3, int par4, int par5) {
        this.playAuxSFXAtEntity(null, par1, par2, par3, par4, par5);
    }

    public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
        try {
            int var7 = 0;
            while (var7 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(var7)).playAuxSFX(par1EntityPlayer, par2, par3, par4, par5, par6);
                ++var7;
            }
        }
        catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Playing level event");
            CrashReportCategory var9 = var8.makeCategory("Level event being played");
            var9.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(par3, par4, par5));
            var9.addCrashSection("Event source", par1EntityPlayer);
            var9.addCrashSection("Event type", par2);
            var9.addCrashSection("Event data", par6);
            throw new ReportedException(var8);
        }
    }

    @Override
    public int getHeight() {
        return 256;
    }

    public int getActualHeight() {
        return this.provider.hasNoSky ? 128 : 256;
    }

    public Random setRandomSeed(int par1, int par2, int par3) {
        long var4 = (long)par1 * 341873128712L + (long)par2 * 132897987541L + this.getWorldInfo().getSeed() + (long)par3;
        this.rand.setSeed(var4);
        return this.rand;
    }

    public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_) {
        return this.getChunkProvider().func_147416_a(this, p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }

    public double getHorizon() {
        return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0 : 63.0;
    }

    public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport) {
        CrashReportCategory var2 = par1CrashReport.makeCategoryDepth("Affected level", 1);
        var2.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
        var2.addCrashSectionCallable("All players", new Callable(){
            private static final String __OBFID = "CL_00000143";

            public String call() {
                return String.valueOf(World.this.playerEntities.size()) + " total; " + World.this.playerEntities.toString();
            }
        });
        var2.addCrashSectionCallable("Chunk stats", new Callable(){
            private static final String __OBFID = "CL_00000144";

            public String call() {
                return World.this.chunkProvider.makeString();
            }
        });
        try {
            this.worldInfo.addToCrashReport(var2);
        }
        catch (Throwable var4) {
            var2.addCrashSectionThrowable("Level Data Unobtainable", var4);
        }
        return var2;
    }

    public void destroyBlockInWorldPartially(int p_147443_1_, int p_147443_2_, int p_147443_3_, int p_147443_4_, int p_147443_5_) {
        int var6 = 0;
        while (var6 < this.worldAccesses.size()) {
            IWorldAccess var7 = (IWorldAccess)this.worldAccesses.get(var6);
            var7.destroyBlockPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
            ++var6;
        }
    }

    @Override
    public Vec3Pool getWorldVec3Pool() {
        return this.vecPool;
    }

    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600 == 0) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getSystemTimeMillis());
        }
        return this.theCalendar;
    }

    public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound) {
    }

    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }

    public void func_147453_f(int p_147453_1_, int p_147453_2_, int p_147453_3_, Block p_147453_4_) {
        int var5 = 0;
        while (var5 < 4) {
            Block var9;
            int var6 = p_147453_1_ + Direction.offsetX[var5];
            int var7 = p_147453_3_ + Direction.offsetZ[var5];
            Block var8 = this.getBlock(var6, p_147453_2_, var7);
            if (Blocks.unpowered_comparator.func_149907_e(var8)) {
                var8.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
            } else if (var8.isNormalCube() && Blocks.unpowered_comparator.func_149907_e(var9 = this.getBlock(var6 += Direction.offsetX[var5], p_147453_2_, var7 += Direction.offsetZ[var5]))) {
                var9.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
            }
            ++var5;
        }
    }

    public float func_147462_b(double p_147462_1_, double p_147462_3_, double p_147462_5_) {
        return this.func_147473_B(MathHelper.floor_double(p_147462_1_), MathHelper.floor_double(p_147462_3_), MathHelper.floor_double(p_147462_5_));
    }

    public float func_147473_B(int p_147473_1_, int p_147473_2_, int p_147473_3_) {
        boolean var5;
        float var4 = 0.0f;
        boolean bl = var5 = this.difficultySetting == EnumDifficulty.HARD;
        if (this.blockExists(p_147473_1_, p_147473_2_, p_147473_3_)) {
            float var6 = this.getCurrentMoonPhaseFactor();
            var4 += MathHelper.clamp_float((float)this.getChunkFromBlockCoords((int)p_147473_1_, (int)p_147473_3_).inhabitedTime / 3600000.0f, 0.0f, 1.0f) * (var5 ? 1.0f : 0.75f);
            var4 += var6 * 0.25f;
        }
        if (this.difficultySetting == EnumDifficulty.EASY || this.difficultySetting == EnumDifficulty.PEACEFUL) {
            var4 *= (float)this.difficultySetting.getDifficultyId() / 2.0f;
        }
        return MathHelper.clamp_float(var4, 0.0f, var5 ? 1.5f : 1.0f);
    }

    public void func_147450_X() {
        for (IWorldAccess var2 : this.worldAccesses) {
            var2.onStaticEntitiesChanged();
        }
    }

}

