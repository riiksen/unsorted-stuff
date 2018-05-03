/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk {
    private static final Logger logger = LogManager.getLogger();
    public static boolean isLit;
    private ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];
    private byte[] blockBiomeArray = new byte[256];
    public int[] precipitationHeightMap = new int[256];
    public boolean[] updateSkylightColumns = new boolean[256];
    public boolean isChunkLoaded;
    public World worldObj;
    public int[] heightMap;
    public final int xPosition;
    public final int zPosition;
    private boolean isGapLightingUpdated;
    public Map chunkTileEntityMap = new HashMap();
    public List[] entityLists = new List[16];
    public boolean isTerrainPopulated;
    public boolean isLightPopulated;
    public boolean field_150815_m;
    public boolean isModified;
    public boolean hasEntities;
    public long lastSaveTime;
    public boolean sendUpdates;
    public int heightMapMinimum;
    public long inhabitedTime;
    private int queuedLightChecks = 4096;
    private static final String __OBFID = "CL_00000373";

    public Chunk(World par1World, int par2, int par3) {
        this.worldObj = par1World;
        this.xPosition = par2;
        this.zPosition = par3;
        this.heightMap = new int[256];
        int var4 = 0;
        while (var4 < this.entityLists.length) {
            this.entityLists[var4] = new ArrayList();
            ++var4;
        }
        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, -1);
    }

    public Chunk(World p_i45446_1_, Block[] p_i45446_2_, int p_i45446_3_, int p_i45446_4_) {
        this(p_i45446_1_, p_i45446_3_, p_i45446_4_);
        int var5 = p_i45446_2_.length / 256;
        boolean var6 = !p_i45446_1_.provider.hasNoSky;
        int var7 = 0;
        while (var7 < 16) {
            int var8 = 0;
            while (var8 < 16) {
                int var9 = 0;
                while (var9 < var5) {
                    Block var10 = p_i45446_2_[var7 << 11 | var8 << 7 | var9];
                    if (var10 != null && var10.getMaterial() != Material.air) {
                        int var11 = var9 >> 4;
                        if (this.storageArrays[var11] == null) {
                            this.storageArrays[var11] = new ExtendedBlockStorage(var11 << 4, var6);
                        }
                        this.storageArrays[var11].func_150818_a(var7, var9 & 15, var8, var10);
                    }
                    ++var9;
                }
                ++var8;
            }
            ++var7;
        }
    }

    public Chunk(World p_i45447_1_, Block[] p_i45447_2_, byte[] p_i45447_3_, int p_i45447_4_, int p_i45447_5_) {
        this(p_i45447_1_, p_i45447_4_, p_i45447_5_);
        int var6 = p_i45447_2_.length / 256;
        boolean var7 = !p_i45447_1_.provider.hasNoSky;
        int var8 = 0;
        while (var8 < 16) {
            int var9 = 0;
            while (var9 < 16) {
                int var10 = 0;
                while (var10 < var6) {
                    int var11 = var8 * var6 * 16 | var9 * var6 | var10;
                    Block var12 = p_i45447_2_[var11];
                    if (var12 != null && var12 != Blocks.air) {
                        int var13 = var10 >> 4;
                        if (this.storageArrays[var13] == null) {
                            this.storageArrays[var13] = new ExtendedBlockStorage(var13 << 4, var7);
                        }
                        this.storageArrays[var13].func_150818_a(var8, var10 & 15, var9, var12);
                        this.storageArrays[var13].setExtBlockMetadata(var8, var10 & 15, var9, p_i45447_3_[var11]);
                    }
                    ++var10;
                }
                ++var9;
            }
            ++var8;
        }
    }

    public boolean isAtLocation(int par1, int par2) {
        if (par1 == this.xPosition && par2 == this.zPosition) {
            return true;
        }
        return false;
    }

    public int getHeightValue(int par1, int par2) {
        return this.heightMap[par2 << 4 | par1];
    }

    public int getTopFilledSegment() {
        int var1 = this.storageArrays.length - 1;
        while (var1 >= 0) {
            if (this.storageArrays[var1] != null) {
                return this.storageArrays[var1].getYLocation();
            }
            --var1;
        }
        return 0;
    }

    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }

    public void generateHeightMap() {
        int var1 = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        int var2 = 0;
        while (var2 < 16) {
            int var3 = 0;
            while (var3 < 16) {
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                for (int var4 = var1 + 16 - 1; var4 > 0; --var4) {
                    Block var5 = this.func_150810_a(var2, var4 - 1, var3);
                    if (var5.getLightOpacity() == 0) {
                        continue;
                    }
                    this.heightMap[var3 << 4 | var2] = var4;
                    if (var4 >= this.heightMapMinimum) break;
                    this.heightMapMinimum = var4;
                    break;
                }
                ++var3;
            }
            ++var2;
        }
        this.isModified = true;
    }

    public void generateSkylightMap() {
        int var1 = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        int var2 = 0;
        while (var2 < 16) {
            int var3 = 0;
            while (var3 < 16) {
                int var4;
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                for (var4 = var1 + 16 - 1; var4 > 0; --var4) {
                    if (this.func_150808_b(var2, var4 - 1, var3) == 0) {
                        continue;
                    }
                    this.heightMap[var3 << 4 | var2] = var4;
                    if (var4 >= this.heightMapMinimum) break;
                    this.heightMapMinimum = var4;
                    break;
                }
                if (!this.worldObj.provider.hasNoSky) {
                    var4 = 15;
                    int var5 = var1 + 16 - 1;
                    do {
                        int var6;
                        ExtendedBlockStorage var7;
                        if ((var6 = this.func_150808_b(var2, var5, var3)) == 0 && var4 != 15) {
                            var6 = 1;
                        }
                        if ((var4 -= var6) <= 0 || (var7 = this.storageArrays[var5 >> 4]) == null) continue;
                        var7.setExtSkylightValue(var2, var5 & 15, var3, var4);
                        this.worldObj.func_147479_m((this.xPosition << 4) + var2, var5, (this.zPosition << 4) + var3);
                    } while (--var5 > 0 && var4 > 0);
                }
                ++var3;
            }
            ++var2;
        }
        this.isModified = true;
    }

    private void propagateSkylightOcclusion(int par1, int par2) {
        this.updateSkylightColumns[par1 + par2 * 16] = true;
        this.isGapLightingUpdated = true;
    }

    private void recheckGaps(boolean p_150803_1_) {
        this.worldObj.theProfiler.startSection("recheckGaps");
        if (this.worldObj.doChunksNearChunkExist(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8, 16)) {
            int var2 = 0;
            while (var2 < 16) {
                int var3 = 0;
                while (var3 < 16) {
                    if (this.updateSkylightColumns[var2 + var3 * 16]) {
                        this.updateSkylightColumns[var2 + var3 * 16] = false;
                        int var4 = this.getHeightValue(var2, var3);
                        int var5 = this.xPosition * 16 + var2;
                        int var6 = this.zPosition * 16 + var3;
                        int var7 = this.worldObj.getChunkHeightMapMinimum(var5 - 1, var6);
                        int var8 = this.worldObj.getChunkHeightMapMinimum(var5 + 1, var6);
                        int var9 = this.worldObj.getChunkHeightMapMinimum(var5, var6 - 1);
                        int var10 = this.worldObj.getChunkHeightMapMinimum(var5, var6 + 1);
                        if (var8 < var7) {
                            var7 = var8;
                        }
                        if (var9 < var7) {
                            var7 = var9;
                        }
                        if (var10 < var7) {
                            var7 = var10;
                        }
                        this.checkSkylightNeighborHeight(var5, var6, var7);
                        this.checkSkylightNeighborHeight(var5 - 1, var6, var4);
                        this.checkSkylightNeighborHeight(var5 + 1, var6, var4);
                        this.checkSkylightNeighborHeight(var5, var6 - 1, var4);
                        this.checkSkylightNeighborHeight(var5, var6 + 1, var4);
                        if (p_150803_1_) {
                            this.worldObj.theProfiler.endSection();
                            return;
                        }
                    }
                    ++var3;
                }
                ++var2;
            }
            this.isGapLightingUpdated = false;
        }
        this.worldObj.theProfiler.endSection();
    }

    private void checkSkylightNeighborHeight(int par1, int par2, int par3) {
        int var4 = this.worldObj.getHeightValue(par1, par2);
        if (var4 > par3) {
            this.updateSkylightNeighborHeight(par1, par2, par3, var4 + 1);
        } else if (var4 < par3) {
            this.updateSkylightNeighborHeight(par1, par2, var4, par3 + 1);
        }
    }

    private void updateSkylightNeighborHeight(int par1, int par2, int par3, int par4) {
        if (par4 > par3 && this.worldObj.doChunksNearChunkExist(par1, 0, par2, 16)) {
            int var5 = par3;
            while (var5 < par4) {
                this.worldObj.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
                ++var5;
            }
            this.isModified = true;
        }
    }

    private void relightBlock(int par1, int par2, int par3) {
        int var4;
        int var5 = var4 = this.heightMap[par3 << 4 | par1] & 255;
        if (par2 > var4) {
            var5 = par2;
        }
        while (var5 > 0 && this.func_150808_b(par1, var5 - 1, par3) == 0) {
            --var5;
        }
        if (var5 != var4) {
            int var12;
            int var8;
            this.worldObj.markBlocksDirtyVertical(par1 + this.xPosition * 16, par3 + this.zPosition * 16, var5, var4);
            this.heightMap[par3 << 4 | par1] = var5;
            int var6 = this.xPosition * 16 + par1;
            int var7 = this.zPosition * 16 + par3;
            if (!this.worldObj.provider.hasNoSky) {
                ExtendedBlockStorage var9;
                if (var5 < var4) {
                    var8 = var5;
                    while (var8 < var4) {
                        var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(par1, var8 & 15, par3, 15);
                            this.worldObj.func_147479_m((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
                        }
                        ++var8;
                    }
                } else {
                    var8 = var4;
                    while (var8 < var5) {
                        var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(par1, var8 & 15, par3, 0);
                            this.worldObj.func_147479_m((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
                        }
                        ++var8;
                    }
                }
                var8 = 15;
                while (var5 > 0 && var8 > 0) {
                    ExtendedBlockStorage var10;
                    if ((var12 = this.func_150808_b(par1, --var5, par3)) == 0) {
                        var12 = 1;
                    }
                    if ((var8 -= var12) < 0) {
                        var8 = 0;
                    }
                    if ((var10 = this.storageArrays[var5 >> 4]) == null) continue;
                    var10.setExtSkylightValue(par1, var5 & 15, par3, var8);
                }
            }
            var8 = this.heightMap[par3 << 4 | par1];
            var12 = var4;
            int var13 = var8;
            if (var8 < var4) {
                var12 = var8;
                var13 = var4;
            }
            if (var8 < this.heightMapMinimum) {
                this.heightMapMinimum = var8;
            }
            if (!this.worldObj.provider.hasNoSky) {
                this.updateSkylightNeighborHeight(var6 - 1, var7, var12, var13);
                this.updateSkylightNeighborHeight(var6 + 1, var7, var12, var13);
                this.updateSkylightNeighborHeight(var6, var7 - 1, var12, var13);
                this.updateSkylightNeighborHeight(var6, var7 + 1, var12, var13);
                this.updateSkylightNeighborHeight(var6, var7, var12, var13);
            }
            this.isModified = true;
        }
    }

    public int func_150808_b(int p_150808_1_, int p_150808_2_, int p_150808_3_) {
        return this.func_150810_a(p_150808_1_, p_150808_2_, p_150808_3_).getLightOpacity();
    }

    public Block func_150810_a(final int p_150810_1_, final int p_150810_2_, final int p_150810_3_) {
        ExtendedBlockStorage var5;
        Block var4 = Blocks.air;
        if (p_150810_2_ >> 4 < this.storageArrays.length && (var5 = this.storageArrays[p_150810_2_ >> 4]) != null) {
            try {
                var4 = var5.func_150819_a(p_150810_1_, p_150810_2_ & 15, p_150810_3_);
            }
            catch (Throwable var9) {
                CrashReport var7 = CrashReport.makeCrashReport(var9, "Getting block");
                CrashReportCategory var8 = var7.makeCategory("Block being got");
                var8.addCrashSectionCallable("Location", new Callable(){
                    private static final String __OBFID = "CL_00000374";

                    public String call() {
                        return CrashReportCategory.getLocationInfo(p_150810_1_, p_150810_2_, p_150810_3_);
                    }
                });
                throw new ReportedException(var7);
            }
        }
        return var4;
    }

    public int getBlockMetadata(int par1, int par2, int par3) {
        if (par2 >> 4 >= this.storageArrays.length) {
            return 0;
        }
        ExtendedBlockStorage var4 = this.storageArrays[par2 >> 4];
        return var4 != null ? var4.getExtBlockMetadata(par1, par2 & 15, par3) : 0;
    }

    public boolean func_150807_a(int p_150807_1_, int p_150807_2_, int p_150807_3_, Block p_150807_4_, int p_150807_5_) {
        TileEntity var16;
        int var6 = p_150807_3_ << 4 | p_150807_1_;
        if (p_150807_2_ >= this.precipitationHeightMap[var6] - 1) {
            this.precipitationHeightMap[var6] = -999;
        }
        int var7 = this.heightMap[var6];
        Block var8 = this.func_150810_a(p_150807_1_, p_150807_2_, p_150807_3_);
        int var9 = this.getBlockMetadata(p_150807_1_, p_150807_2_, p_150807_3_);
        if (var8 == p_150807_4_ && var9 == p_150807_5_) {
            return false;
        }
        ExtendedBlockStorage var10 = this.storageArrays[p_150807_2_ >> 4];
        boolean var11 = false;
        if (var10 == null) {
            if (p_150807_4_ == Blocks.air) {
                return false;
            }
            ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(p_150807_2_ >> 4 << 4, !this.worldObj.provider.hasNoSky);
            this.storageArrays[p_150807_2_ >> 4] = extendedBlockStorage;
            var10 = extendedBlockStorage;
            var11 = p_150807_2_ >= var7;
        }
        int var12 = this.xPosition * 16 + p_150807_1_;
        int var13 = this.zPosition * 16 + p_150807_3_;
        if (!this.worldObj.isClient) {
            var8.onBlockPreDestroy(this.worldObj, var12, p_150807_2_, var13, var9);
        }
        var10.func_150818_a(p_150807_1_, p_150807_2_ & 15, p_150807_3_, p_150807_4_);
        if (!this.worldObj.isClient) {
            var8.breakBlock(this.worldObj, var12, p_150807_2_, var13, var8, var9);
        } else if (var8 instanceof ITileEntityProvider && var8 != p_150807_4_) {
            this.worldObj.removeTileEntity(var12, p_150807_2_, var13);
        }
        if (var10.func_150819_a(p_150807_1_, p_150807_2_ & 15, p_150807_3_) != p_150807_4_) {
            return false;
        }
        var10.setExtBlockMetadata(p_150807_1_, p_150807_2_ & 15, p_150807_3_, p_150807_5_);
        if (var11) {
            this.generateSkylightMap();
        } else {
            int var14 = p_150807_4_.getLightOpacity();
            int var15 = var8.getLightOpacity();
            if (var14 > 0) {
                if (p_150807_2_ >= var7) {
                    this.relightBlock(p_150807_1_, p_150807_2_ + 1, p_150807_3_);
                }
            } else if (p_150807_2_ == var7 - 1) {
                this.relightBlock(p_150807_1_, p_150807_2_, p_150807_3_);
            }
            if (var14 != var15 && (var14 < var15 || this.getSavedLightValue(EnumSkyBlock.Sky, p_150807_1_, p_150807_2_, p_150807_3_) > 0 || this.getSavedLightValue(EnumSkyBlock.Block, p_150807_1_, p_150807_2_, p_150807_3_) > 0)) {
                this.propagateSkylightOcclusion(p_150807_1_, p_150807_3_);
            }
        }
        if (var8 instanceof ITileEntityProvider && (var16 = this.func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_)) != null) {
            var16.updateContainingBlockInfo();
        }
        if (!this.worldObj.isClient) {
            p_150807_4_.onBlockAdded(this.worldObj, var12, p_150807_2_, var13);
        }
        if (p_150807_4_ instanceof ITileEntityProvider) {
            TileEntity var162 = this.func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_);
            if (var162 == null) {
                var162 = ((ITileEntityProvider)((Object)p_150807_4_)).createNewTileEntity(this.worldObj, p_150807_5_);
                this.worldObj.setTileEntity(var12, p_150807_2_, var13, var162);
            }
            if (var162 != null) {
                var162.updateContainingBlockInfo();
            }
        }
        this.isModified = true;
        return true;
    }

    public boolean setBlockMetadata(int par1, int par2, int par3, int par4) {
        TileEntity var7;
        ExtendedBlockStorage var5 = this.storageArrays[par2 >> 4];
        if (var5 == null) {
            return false;
        }
        int var6 = var5.getExtBlockMetadata(par1, par2 & 15, par3);
        if (var6 == par4) {
            return false;
        }
        this.isModified = true;
        var5.setExtBlockMetadata(par1, par2 & 15, par3, par4);
        if (var5.func_150819_a(par1, par2 & 15, par3) instanceof ITileEntityProvider && (var7 = this.func_150806_e(par1, par2, par3)) != null) {
            var7.updateContainingBlockInfo();
            var7.blockMetadata = par4;
        }
        return true;
    }

    public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
        ExtendedBlockStorage var5 = this.storageArrays[par3 >> 4];
        return var5 == null ? (this.canBlockSeeTheSky(par2, par3, par4) ? par1EnumSkyBlock.defaultLightValue : 0) : (par1EnumSkyBlock == EnumSkyBlock.Sky ? (this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par2, par3 & 15, par4)) : (par1EnumSkyBlock == EnumSkyBlock.Block ? var5.getExtBlocklightValue(par2, par3 & 15, par4) : par1EnumSkyBlock.defaultLightValue));
    }

    public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {
        ExtendedBlockStorage var6 = this.storageArrays[par3 >> 4];
        if (var6 == null) {
            ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(par3 >> 4 << 4, !this.worldObj.provider.hasNoSky);
            this.storageArrays[par3 >> 4] = extendedBlockStorage;
            var6 = extendedBlockStorage;
            this.generateSkylightMap();
        }
        this.isModified = true;
        if (par1EnumSkyBlock == EnumSkyBlock.Sky) {
            if (!this.worldObj.provider.hasNoSky) {
                var6.setExtSkylightValue(par2, par3 & 15, par4, par5);
            }
        } else if (par1EnumSkyBlock == EnumSkyBlock.Block) {
            var6.setExtBlocklightValue(par2, par3 & 15, par4, par5);
        }
    }

    public int getBlockLightValue(int par1, int par2, int par3, int par4) {
        int var6;
        int var7;
        ExtendedBlockStorage var5 = this.storageArrays[par2 >> 4];
        if (var5 == null) {
            return !this.worldObj.provider.hasNoSky && par4 < EnumSkyBlock.Sky.defaultLightValue ? EnumSkyBlock.Sky.defaultLightValue - par4 : 0;
        }
        int n = var6 = this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par1, par2 & 15, par3);
        if (var6 > 0) {
            isLit = true;
        }
        if ((var7 = var5.getExtBlocklightValue(par1, par2 & 15, par3)) > (var6 -= par4)) {
            var6 = var7;
        }
        return var6;
    }

    public void addEntity(Entity par1Entity) {
        int var4;
        this.hasEntities = true;
        int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        if (var2 != this.xPosition || var3 != this.zPosition) {
            logger.error("Wrong location! " + par1Entity);
            Thread.dumpStack();
        }
        if ((var4 = MathHelper.floor_double(par1Entity.posY / 16.0)) < 0) {
            var4 = 0;
        }
        if (var4 >= this.entityLists.length) {
            var4 = this.entityLists.length - 1;
        }
        par1Entity.addedToChunk = true;
        par1Entity.chunkCoordX = this.xPosition;
        par1Entity.chunkCoordY = var4;
        par1Entity.chunkCoordZ = this.zPosition;
        this.entityLists[var4].add(par1Entity);
    }

    public void removeEntity(Entity par1Entity) {
        this.removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
    }

    public void removeEntityAtIndex(Entity par1Entity, int par2) {
        if (par2 < 0) {
            par2 = 0;
        }
        if (par2 >= this.entityLists.length) {
            par2 = this.entityLists.length - 1;
        }
        this.entityLists[par2].remove(par1Entity);
    }

    public boolean canBlockSeeTheSky(int par1, int par2, int par3) {
        if (par2 >= this.heightMap[par3 << 4 | par1]) {
            return true;
        }
        return false;
    }

    public TileEntity func_150806_e(int p_150806_1_, int p_150806_2_, int p_150806_3_) {
        ChunkPosition var4 = new ChunkPosition(p_150806_1_, p_150806_2_, p_150806_3_);
        TileEntity var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
        if (var5 == null) {
            Block var6 = this.func_150810_a(p_150806_1_, p_150806_2_, p_150806_3_);
            if (!var6.hasTileEntity()) {
                return null;
            }
            var5 = ((ITileEntityProvider)((Object)var6)).createNewTileEntity(this.worldObj, this.getBlockMetadata(p_150806_1_, p_150806_2_, p_150806_3_));
            this.worldObj.setTileEntity(this.xPosition * 16 + p_150806_1_, p_150806_2_, this.zPosition * 16 + p_150806_3_, var5);
        }
        if (var5 != null && var5.isInvalid()) {
            this.chunkTileEntityMap.remove(var4);
            return null;
        }
        return var5;
    }

    public void addTileEntity(TileEntity p_150813_1_) {
        int var2 = p_150813_1_.field_145851_c - this.xPosition * 16;
        int var3 = p_150813_1_.field_145848_d;
        int var4 = p_150813_1_.field_145849_e - this.zPosition * 16;
        this.func_150812_a(var2, var3, var4, p_150813_1_);
        if (this.isChunkLoaded) {
            this.worldObj.field_147482_g.add(p_150813_1_);
        }
    }

    public void func_150812_a(int p_150812_1_, int p_150812_2_, int p_150812_3_, TileEntity p_150812_4_) {
        ChunkPosition var5 = new ChunkPosition(p_150812_1_, p_150812_2_, p_150812_3_);
        p_150812_4_.setWorldObj(this.worldObj);
        p_150812_4_.field_145851_c = this.xPosition * 16 + p_150812_1_;
        p_150812_4_.field_145848_d = p_150812_2_;
        p_150812_4_.field_145849_e = this.zPosition * 16 + p_150812_3_;
        if (this.func_150810_a(p_150812_1_, p_150812_2_, p_150812_3_) instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(var5)) {
                ((TileEntity)this.chunkTileEntityMap.get(var5)).invalidate();
            }
            p_150812_4_.validate();
            this.chunkTileEntityMap.put(var5, p_150812_4_);
        }
    }

    public void removeTileEntity(int p_150805_1_, int p_150805_2_, int p_150805_3_) {
        TileEntity var5;
        ChunkPosition var4 = new ChunkPosition(p_150805_1_, p_150805_2_, p_150805_3_);
        if (this.isChunkLoaded && (var5 = (TileEntity)this.chunkTileEntityMap.remove(var4)) != null) {
            var5.invalidate();
        }
    }

    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.func_147448_a(this.chunkTileEntityMap.values());
        int var1 = 0;
        while (var1 < this.entityLists.length) {
            for (Entity var3 : this.entityLists[var1]) {
                var3.onChunkLoad();
            }
            this.worldObj.addLoadedEntities(this.entityLists[var1]);
            ++var1;
        }
    }

    public void onChunkUnload() {
        this.isChunkLoaded = false;
        for (TileEntity var2 : this.chunkTileEntityMap.values()) {
            this.worldObj.func_147457_a(var2);
        }
        int var3 = 0;
        while (var3 < this.entityLists.length) {
            this.worldObj.unloadEntities(this.entityLists[var3]);
            ++var3;
        }
    }

    public void setChunkModified() {
        this.isModified = true;
    }

    public void getEntitiesWithinAABBForEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector) {
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0) / 16.0);
        var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
        var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
        int var7 = var5;
        while (var7 <= var6) {
            List var8 = this.entityLists[var7];
            int var9 = 0;
            while (var9 < var8.size()) {
                Entity var10 = (Entity)var8.get(var9);
                if (var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                    par3List.add(var10);
                    Entity[] var11 = var10.getParts();
                    if (var11 != null) {
                        int var12 = 0;
                        while (var12 < var11.length) {
                            var10 = var11[var12];
                            if (var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                                par3List.add(var10);
                            }
                            ++var12;
                        }
                    }
                }
                ++var9;
            }
            ++var7;
        }
    }

    public void getEntitiesOfTypeWithinAAAB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector) {
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0) / 16.0);
        var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
        var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
        int var7 = var5;
        while (var7 <= var6) {
            List var8 = this.entityLists[var7];
            int var9 = 0;
            while (var9 < var8.size()) {
                Entity var10 = (Entity)var8.get(var9);
                if (par1Class.isAssignableFrom(var10.getClass()) && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                    par3List.add(var10);
                }
                ++var9;
            }
            ++var7;
        }
    }

    public boolean needsSaving(boolean par1) {
        if (par1 ? this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime || this.isModified : this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600) {
            return true;
        }
        return this.isModified;
    }

    public Random getRandomWithSeed(long par1) {
        return new Random(this.worldObj.getSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871 + (long)(this.zPosition * 389711) ^ par1);
    }

    public boolean isEmpty() {
        return false;
    }

    public void populateChunk(IChunkProvider par1IChunkProvider, IChunkProvider par2IChunkProvider, int par3, int par4) {
        if (!this.isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 + 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3, par4);
        }
        if (par1IChunkProvider.chunkExists(par3 - 1, par4) && !par1IChunkProvider.provideChunk((int)(par3 - 1), (int)par4).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4);
        }
        if (par1IChunkProvider.chunkExists(par3, par4 - 1) && !par1IChunkProvider.provideChunk((int)par3, (int)(par4 - 1)).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3, par4 - 1);
        }
        if (par1IChunkProvider.chunkExists(par3 - 1, par4 - 1) && !par1IChunkProvider.provideChunk((int)(par3 - 1), (int)(par4 - 1)).isTerrainPopulated && par1IChunkProvider.chunkExists(par3, par4 - 1) && par1IChunkProvider.chunkExists(par3 - 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4 - 1);
        }
    }

    public int getPrecipitationHeight(int par1, int par2) {
        int var3 = par1 | par2 << 4;
        int var4 = this.precipitationHeightMap[var3];
        if (var4 == -999) {
            int var5 = this.getTopFilledSegment() + 15;
            var4 = -1;
            while (var5 > 0 && var4 == -1) {
                Block var6 = this.func_150810_a(par1, var5, par2);
                Material var7 = var6.getMaterial();
                if (!var7.blocksMovement() && !var7.isLiquid()) {
                    --var5;
                    continue;
                }
                var4 = var5 + 1;
            }
            this.precipitationHeightMap[var3] = var4;
        }
        return var4;
    }

    public void func_150804_b(boolean p_150804_1_) {
        if (this.isGapLightingUpdated && !this.worldObj.provider.hasNoSky && !p_150804_1_) {
            this.recheckGaps(this.worldObj.isClient);
        }
        this.field_150815_m = true;
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
        }
    }

    public boolean func_150802_k() {
        if (this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated) {
            return true;
        }
        return false;
    }

    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }

    public boolean getAreLevelsEmpty(int par1, int par2) {
        if (par1 < 0) {
            par1 = 0;
        }
        if (par2 >= 256) {
            par2 = 255;
        }
        int var3 = par1;
        while (var3 <= par2) {
            ExtendedBlockStorage var4 = this.storageArrays[var3 >> 4];
            if (var4 != null && !var4.isEmpty()) {
                return false;
            }
            var3 += 16;
        }
        return true;
    }

    public void setStorageArrays(ExtendedBlockStorage[] par1ArrayOfExtendedBlockStorage) {
        this.storageArrays = par1ArrayOfExtendedBlockStorage;
    }

    public void fillChunk(byte[] par1ArrayOfByte, int par2, int par3, boolean par4) {
        NibbleArray var9;
        int var5 = 0;
        boolean var6 = !this.worldObj.provider.hasNoSky;
        int var7 = 0;
        while (var7 < this.storageArrays.length) {
            if ((par2 & 1 << var7) != 0) {
                if (this.storageArrays[var7] == null) {
                    this.storageArrays[var7] = new ExtendedBlockStorage(var7 << 4, var6);
                }
                byte[] var8 = this.storageArrays[var7].getBlockLSBArray();
                System.arraycopy(par1ArrayOfByte, var5, var8, 0, var8.length);
                var5 += var8.length;
            } else if (par4 && this.storageArrays[var7] != null) {
                this.storageArrays[var7] = null;
            }
            ++var7;
        }
        var7 = 0;
        while (var7 < this.storageArrays.length) {
            if ((par2 & 1 << var7) != 0 && this.storageArrays[var7] != null) {
                var9 = this.storageArrays[var7].getMetadataArray();
                System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
            ++var7;
        }
        var7 = 0;
        while (var7 < this.storageArrays.length) {
            if ((par2 & 1 << var7) != 0 && this.storageArrays[var7] != null) {
                var9 = this.storageArrays[var7].getBlocklightArray();
                System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
            ++var7;
        }
        if (var6) {
            var7 = 0;
            while (var7 < this.storageArrays.length) {
                if ((par2 & 1 << var7) != 0 && this.storageArrays[var7] != null) {
                    var9 = this.storageArrays[var7].getSkylightArray();
                    System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
                ++var7;
            }
        }
        var7 = 0;
        while (var7 < this.storageArrays.length) {
            if ((par3 & 1 << var7) != 0) {
                if (this.storageArrays[var7] == null) {
                    var5 += 2048;
                } else {
                    var9 = this.storageArrays[var7].getBlockMSBArray();
                    if (var9 == null) {
                        var9 = this.storageArrays[var7].createBlockMSBArray();
                    }
                    System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
            } else if (par4 && this.storageArrays[var7] != null && this.storageArrays[var7].getBlockMSBArray() != null) {
                this.storageArrays[var7].clearMSBArray();
            }
            ++var7;
        }
        if (par4) {
            System.arraycopy(par1ArrayOfByte, var5, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            int n = var5 + this.blockBiomeArray.length;
        }
        var7 = 0;
        while (var7 < this.storageArrays.length) {
            if (this.storageArrays[var7] != null && (par2 & 1 << var7) != 0) {
                this.storageArrays[var7].removeInvalidBlocks();
            }
            ++var7;
        }
        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.generateHeightMap();
        for (TileEntity var11 : this.chunkTileEntityMap.values()) {
            var11.updateContainingBlockInfo();
        }
    }

    public BiomeGenBase getBiomeGenForWorldCoords(int par1, int par2, WorldChunkManager par3WorldChunkManager) {
        int var4 = this.blockBiomeArray[par2 << 4 | par1] & 255;
        if (var4 == 255) {
            BiomeGenBase var5 = par3WorldChunkManager.getBiomeGenAt((this.xPosition << 4) + par1, (this.zPosition << 4) + par2);
            var4 = var5.biomeID;
            this.blockBiomeArray[par2 << 4 | par1] = (byte)(var4 & 255);
        }
        return BiomeGenBase.func_150568_d(var4) == null ? BiomeGenBase.plains : BiomeGenBase.func_150568_d(var4);
    }

    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }

    public void setBiomeArray(byte[] par1ArrayOfByte) {
        this.blockBiomeArray = par1ArrayOfByte;
    }

    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }

    public void enqueueRelightChecks() {
        int var1 = 0;
        while (var1 < 8) {
            if (this.queuedLightChecks >= 4096) {
                return;
            }
            int var2 = this.queuedLightChecks % 16;
            int var3 = this.queuedLightChecks / 16 % 16;
            int var4 = this.queuedLightChecks / 256;
            ++this.queuedLightChecks;
            int var5 = (this.xPosition << 4) + var3;
            int var6 = (this.zPosition << 4) + var4;
            int var7 = 0;
            while (var7 < 16) {
                int var8 = (var2 << 4) + var7;
                if (this.storageArrays[var2] == null && (var7 == 0 || var7 == 15 || var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15) || this.storageArrays[var2] != null && this.storageArrays[var2].func_150819_a(var3, var7, var4).getMaterial() == Material.air) {
                    if (this.worldObj.getBlock(var5, var8 - 1, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8 - 1, var6);
                    }
                    if (this.worldObj.getBlock(var5, var8 + 1, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8 + 1, var6);
                    }
                    if (this.worldObj.getBlock(var5 - 1, var8, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5 - 1, var8, var6);
                    }
                    if (this.worldObj.getBlock(var5 + 1, var8, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5 + 1, var8, var6);
                    }
                    if (this.worldObj.getBlock(var5, var8, var6 - 1).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8, var6 - 1);
                    }
                    if (this.worldObj.getBlock(var5, var8, var6 + 1).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8, var6 + 1);
                    }
                    this.worldObj.func_147451_t(var5, var8, var6);
                }
                ++var7;
            }
            ++var1;
        }
    }

    public void func_150809_p() {
        this.isTerrainPopulated = true;
        this.isLightPopulated = true;
        if (!this.worldObj.provider.hasNoSky) {
            if (this.worldObj.checkChunksExist(this.xPosition * 16 - 1, 0, this.zPosition * 16 - 1, this.xPosition * 16 + 1, 63, this.zPosition * 16 + 1)) {
                int var1 = 0;
                while (var1 < 16) {
                    int var2 = 0;
                    while (var2 < 16) {
                        if (!this.func_150811_f(var1, var2)) {
                            this.isLightPopulated = false;
                            break;
                        }
                        ++var2;
                    }
                    ++var1;
                }
                if (this.isLightPopulated) {
                    Chunk var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 - 1, this.zPosition * 16);
                    var3.func_150801_a(3);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 + 16, this.zPosition * 16);
                    var3.func_150801_a(1);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 - 1);
                    var3.func_150801_a(0);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 + 16);
                    var3.func_150801_a(2);
                }
            } else {
                this.isLightPopulated = false;
            }
        }
    }

    private void func_150801_a(int p_150801_1_) {
        block4 : {
            block7 : {
                block6 : {
                    block5 : {
                        if (!this.isTerrainPopulated) break block4;
                        if (p_150801_1_ != 3) break block5;
                        int var2 = 0;
                        while (var2 < 16) {
                            this.func_150811_f(15, var2);
                            ++var2;
                        }
                        break block4;
                    }
                    if (p_150801_1_ != 1) break block6;
                    int var2 = 0;
                    while (var2 < 16) {
                        this.func_150811_f(0, var2);
                        ++var2;
                    }
                    break block4;
                }
                if (p_150801_1_ != 0) break block7;
                int var2 = 0;
                while (var2 < 16) {
                    this.func_150811_f(var2, 15);
                    ++var2;
                }
                break block4;
            }
            if (p_150801_1_ != 2) break block4;
            int var2 = 0;
            while (var2 < 16) {
                this.func_150811_f(var2, 0);
                ++var2;
            }
        }
    }

    private boolean func_150811_f(int p_150811_1_, int p_150811_2_) {
        int var3 = this.getTopFilledSegment();
        boolean var4 = false;
        boolean var5 = false;
        int var6 = var3 + 16 - 1;
        while (var6 > 63 || var6 > 0 && !var5) {
            int var7 = this.func_150808_b(p_150811_1_, var6, p_150811_2_);
            if (var7 == 255 && var6 < 63) {
                var5 = true;
            }
            if (!var4 && var7 > 0) {
                var4 = true;
            } else if (var4 && var7 == 0 && !this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_)) {
                return false;
            }
            --var6;
        }
        while (var6 > 0) {
            if (this.func_150810_a(p_150811_1_, var6, p_150811_2_).getLightValue() > 0) {
                this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_);
            }
            --var6;
        }
        return true;
    }

}

