/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTorch
extends Block {
    private static final String __OBFID = "CL_00000325";

    protected BlockTorch() {
        super(Material.circuits);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 2;
    }

    private boolean func_150107_m(World p_150107_1_, int p_150107_2_, int p_150107_3_, int p_150107_4_) {
        if (World.doesBlockHaveSolidTopSurface(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_)) {
            return true;
        }
        Block var5 = p_150107_1_.getBlock(p_150107_2_, p_150107_3_, p_150107_4_);
        if (var5 != Blocks.fence && var5 != Blocks.nether_brick_fence && var5 != Blocks.glass && var5 != Blocks.cobblestone_wall) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return p_149742_1_.isBlockNormalCubeDefault(p_149742_2_ - 1, p_149742_3_, p_149742_4_, true) ? true : (p_149742_1_.isBlockNormalCubeDefault(p_149742_2_ + 1, p_149742_3_, p_149742_4_, true) ? true : (p_149742_1_.isBlockNormalCubeDefault(p_149742_2_, p_149742_3_, p_149742_4_ - 1, true) ? true : (p_149742_1_.isBlockNormalCubeDefault(p_149742_2_, p_149742_3_, p_149742_4_ + 1, true) ? true : this.func_150107_m(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_))));
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        int var10 = p_149660_9_;
        if (p_149660_5_ == 1 && this.func_150107_m(p_149660_1_, p_149660_2_, p_149660_3_ - 1, p_149660_4_)) {
            var10 = 5;
        }
        if (p_149660_5_ == 2 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ + 1, true)) {
            var10 = 4;
        }
        if (p_149660_5_ == 3 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ - 1, true)) {
            var10 = 3;
        }
        if (p_149660_5_ == 4 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ + 1, p_149660_3_, p_149660_4_, true)) {
            var10 = 2;
        }
        if (p_149660_5_ == 5 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ - 1, p_149660_3_, p_149660_4_, true)) {
            var10 = 1;
        }
        return var10;
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        if (p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) == 0) {
            this.onBlockAdded(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        if (p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_) == 0) {
            if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_ - 1, p_149726_3_, p_149726_4_, true)) {
                p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 1, 2);
            } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_ + 1, p_149726_3_, p_149726_4_, true)) {
                p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 2, 2);
            } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_, p_149726_3_, p_149726_4_ - 1, true)) {
                p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 3, 2);
            } else if (p_149726_1_.isBlockNormalCubeDefault(p_149726_2_, p_149726_3_, p_149726_4_ + 1, true)) {
                p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 4, 2);
            } else if (this.func_150107_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_)) {
                p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, 5, 2);
            }
        }
        this.func_150109_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        this.func_150108_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }

    protected boolean func_150108_b(World p_150108_1_, int p_150108_2_, int p_150108_3_, int p_150108_4_, Block p_150108_5_) {
        if (this.func_150109_e(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_)) {
            int var6 = p_150108_1_.getBlockMetadata(p_150108_2_, p_150108_3_, p_150108_4_);
            boolean var7 = false;
            if (!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_ - 1, p_150108_3_, p_150108_4_, true) && var6 == 1) {
                var7 = true;
            }
            if (!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_ + 1, p_150108_3_, p_150108_4_, true) && var6 == 2) {
                var7 = true;
            }
            if (!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_, p_150108_3_, p_150108_4_ - 1, true) && var6 == 3) {
                var7 = true;
            }
            if (!p_150108_1_.isBlockNormalCubeDefault(p_150108_2_, p_150108_3_, p_150108_4_ + 1, true) && var6 == 4) {
                var7 = true;
            }
            if (!this.func_150107_m(p_150108_1_, p_150108_2_, p_150108_3_ - 1, p_150108_4_) && var6 == 5) {
                var7 = true;
            }
            if (var7) {
                this.dropBlockAsItem(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_, p_150108_1_.getBlockMetadata(p_150108_2_, p_150108_3_, p_150108_4_), 0);
                p_150108_1_.setBlockToAir(p_150108_2_, p_150108_3_, p_150108_4_);
                return true;
            }
            return false;
        }
        return true;
    }

    protected boolean func_150109_e(World p_150109_1_, int p_150109_2_, int p_150109_3_, int p_150109_4_) {
        if (!this.canPlaceBlockAt(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_)) {
            if (p_150109_1_.getBlock(p_150109_2_, p_150109_3_, p_150109_4_) == this) {
                this.dropBlockAsItem(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_, p_150109_1_.getBlockMetadata(p_150109_2_, p_150109_3_, p_150109_4_), 0);
                p_150109_1_.setBlockToAir(p_150109_2_, p_150109_3_, p_150109_4_);
            }
            return false;
        }
        return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        int var7 = p_149731_1_.getBlockMetadata(p_149731_2_, p_149731_3_, p_149731_4_) & 7;
        float var8 = 0.15f;
        if (var7 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var8, var8 * 2.0f, 0.8f, 0.5f + var8);
        } else if (var7 == 2) {
            this.setBlockBounds(1.0f - var8 * 2.0f, 0.2f, 0.5f - var8, 1.0f, 0.8f, 0.5f + var8);
        } else if (var7 == 3) {
            this.setBlockBounds(0.5f - var8, 0.2f, 0.0f, 0.5f + var8, 0.8f, var8 * 2.0f);
        } else if (var7 == 4) {
            this.setBlockBounds(0.5f - var8, 0.2f, 1.0f - var8 * 2.0f, 0.5f + var8, 0.8f, 1.0f);
        } else {
            var8 = 0.1f;
            this.setBlockBounds(0.5f - var8, 0.0f, 0.5f - var8, 0.5f + var8, 0.6f, 0.5f + var8);
        }
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
        double var7 = (float)p_149734_2_ + 0.5f;
        double var9 = (float)p_149734_3_ + 0.7f;
        double var11 = (float)p_149734_4_ + 0.5f;
        double var13 = 0.2199999988079071;
        double var15 = 0.27000001072883606;
        if (var6 == 1) {
            p_149734_1_.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            p_149734_1_.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 2) {
            p_149734_1_.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            p_149734_1_.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 3) {
            p_149734_1_.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            p_149734_1_.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
        } else if (var6 == 4) {
            p_149734_1_.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            p_149734_1_.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
        } else {
            p_149734_1_.spawnParticle("smoke", var7, var9, var11, 0.0, 0.0, 0.0);
            p_149734_1_.spawnParticle("flame", var7, var9, var11, 0.0, 0.0, 0.0);
        }
    }
}

