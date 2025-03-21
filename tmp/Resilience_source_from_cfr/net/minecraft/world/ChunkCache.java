/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache
implements IBlockAccess {
    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private boolean isEmpty;
    private World worldObj;
    private static final String __OBFID = "CL_00000155";

    public ChunkCache(World par1World, int par2, int par3, int par4, int par5, int par6, int par7, int par8) {
        Chunk var13;
        int var12;
        this.worldObj = par1World;
        this.chunkX = par2 - par8 >> 4;
        this.chunkZ = par4 - par8 >> 4;
        int var9 = par5 + par8 >> 4;
        int var10 = par7 + par8 >> 4;
        this.chunkArray = new Chunk[var9 - this.chunkX + 1][var10 - this.chunkZ + 1];
        this.isEmpty = true;
        int var11 = this.chunkX;
        while (var11 <= var9) {
            var12 = this.chunkZ;
            while (var12 <= var10) {
                var13 = par1World.getChunkFromChunkCoords(var11, var12);
                if (var13 != null) {
                    this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ] = var13;
                }
                ++var12;
            }
            ++var11;
        }
        var11 = par2 >> 4;
        while (var11 <= par5 >> 4) {
            var12 = par4 >> 4;
            while (var12 <= par7 >> 4) {
                var13 = this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ];
                if (var13 != null && !var13.getAreLevelsEmpty(par3, par6)) {
                    this.isEmpty = false;
                }
                ++var12;
            }
            ++var11;
        }
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.isEmpty;
    }

    @Override
    public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
        Block var4 = Blocks.air;
        if (p_147439_2_ >= 0 && p_147439_2_ < 256) {
            Chunk var7;
            int var5 = (p_147439_1_ >> 4) - this.chunkX;
            int var6 = (p_147439_3_ >> 4) - this.chunkZ;
            if (var5 >= 0 && var5 < this.chunkArray.length && var6 >= 0 && var6 < this.chunkArray[var5].length && (var7 = this.chunkArray[var5][var6]) != null) {
                var4 = var7.func_150810_a(p_147439_1_ & 15, p_147439_2_, p_147439_3_ & 15);
            }
        }
        return var4;
    }

    @Override
    public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
        int var4 = (p_147438_1_ >> 4) - this.chunkX;
        int var5 = (p_147438_3_ >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].func_150806_e(p_147438_1_ & 15, p_147438_2_, p_147438_3_ & 15);
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

    @Override
    public int getBlockMetadata(int par1, int par2, int par3) {
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            return 0;
        }
        int var4 = (par1 >> 4) - this.chunkX;
        int var5 = (par3 >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getBlockMetadata(par1 & 15, par2, par3 & 15);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int par1, int par2) {
        return this.worldObj.getBiomeGenForCoords(par1, par2);
    }

    @Override
    public Vec3Pool getWorldVec3Pool() {
        return this.worldObj.getWorldVec3Pool();
    }

    @Override
    public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
        if (this.getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air) {
            return true;
        }
        return false;
    }

    public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par3 >= 0 && par3 < 256 && par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 <= 30000000) {
            if (par1EnumSkyBlock == EnumSkyBlock.Sky && this.worldObj.provider.hasNoSky) {
                return 0;
            }
            if (this.getBlock(par2, par3, par4).func_149710_n()) {
                int var5 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3 + 1, par4);
                int var6 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2 + 1, par3, par4);
                int var7 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2 - 1, par3, par4);
                int var8 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 + 1);
                int var9 = this.getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 - 1);
                if (var6 > var5) {
                    var5 = var6;
                }
                if (var7 > var5) {
                    var5 = var7;
                }
                if (var8 > var5) {
                    var5 = var8;
                }
                if (var9 > var5) {
                    var5 = var9;
                }
                return var5;
            }
            int var5 = (par2 >> 4) - this.chunkX;
            int var6 = (par4 >> 4) - this.chunkZ;
            return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
        }
        return par1EnumSkyBlock.defaultLightValue;
    }

    public int getSpecialBlockBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par3 >= 0 && par3 < 256 && par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 <= 30000000) {
            int var5 = (par2 >> 4) - this.chunkX;
            int var6 = (par4 >> 4) - this.chunkZ;
            return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
        }
        return par1EnumSkyBlock.defaultLightValue;
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4) {
        return this.getBlock(par1, par2, par3).isProvidingStrongPower(this, par1, par2, par3, par4);
    }
}

