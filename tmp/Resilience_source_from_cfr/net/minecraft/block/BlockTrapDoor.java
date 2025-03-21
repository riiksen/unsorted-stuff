/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor
extends Block {
    private static final String __OBFID = "CL_00000327";

    protected BlockTrapDoor(Material p_i45434_1_) {
        super(p_i45434_1_);
        float var2 = 0.5f;
        float var3 = 1.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var3, 0.5f + var2);
        this.setCreativeTab(CreativeTabs.tabRedstone);
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
    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return !BlockTrapDoor.func_150118_d(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.func_150117_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float var1 = 0.1875f;
        this.setBlockBounds(0.0f, 0.5f - var1 / 2.0f, 0.0f, 1.0f, 0.5f + var1 / 2.0f, 1.0f);
    }

    public void func_150117_b(int p_150117_1_) {
        float var2 = 0.1875f;
        if ((p_150117_1_ & 8) != 0) {
            this.setBlockBounds(0.0f, 1.0f - var2, 0.0f, 1.0f, 1.0f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var2, 1.0f);
        }
        if (BlockTrapDoor.func_150118_d(p_150117_1_)) {
            if ((p_150117_1_ & 3) == 0) {
                this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
            }
            if ((p_150117_1_ & 3) == 1) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
            }
            if ((p_150117_1_ & 3) == 2) {
                this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            if ((p_150117_1_ & 3) == 3) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 ^ 4, 2);
        p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }

    public void func_150120_a(World p_150120_1_, int p_150120_2_, int p_150120_3_, int p_150120_4_, boolean p_150120_5_) {
        boolean var7;
        int var6 = p_150120_1_.getBlockMetadata(p_150120_2_, p_150120_3_, p_150120_4_);
        boolean bl = var7 = (var6 & 4) > 0;
        if (var7 != p_150120_5_) {
            p_150120_1_.setBlockMetadataWithNotify(p_150120_2_, p_150120_3_, p_150120_4_, var6 ^ 4, 2);
            p_150120_1_.playAuxSFXAtEntity(null, 1003, p_150120_2_, p_150120_3_, p_150120_4_, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            boolean var9;
            int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            int var7 = p_149695_2_;
            int var8 = p_149695_4_;
            if ((var6 & 3) == 0) {
                var8 = p_149695_4_ + 1;
            }
            if ((var6 & 3) == 1) {
                --var8;
            }
            if ((var6 & 3) == 2) {
                var7 = p_149695_2_ + 1;
            }
            if ((var6 & 3) == 3) {
                --var7;
            }
            if (!BlockTrapDoor.func_150119_a(p_149695_1_.getBlock(var7, p_149695_3_, var8))) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            }
            if ((var9 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_)) || p_149695_5_.canProvidePower()) {
                this.func_150120_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var9);
            }
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        int var10 = 0;
        if (p_149660_5_ == 2) {
            var10 = 0;
        }
        if (p_149660_5_ == 3) {
            var10 = 1;
        }
        if (p_149660_5_ == 4) {
            var10 = 2;
        }
        if (p_149660_5_ == 5) {
            var10 = 3;
        }
        if (p_149660_5_ != 1 && p_149660_5_ != 0 && p_149660_7_ > 0.5f) {
            var10 |= 8;
        }
        return var10;
    }

    @Override
    public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_) {
        if (p_149707_5_ == 0) {
            return false;
        }
        if (p_149707_5_ == 1) {
            return false;
        }
        if (p_149707_5_ == 2) {
            ++p_149707_4_;
        }
        if (p_149707_5_ == 3) {
            --p_149707_4_;
        }
        if (p_149707_5_ == 4) {
            ++p_149707_2_;
        }
        if (p_149707_5_ == 5) {
            --p_149707_2_;
        }
        return BlockTrapDoor.func_150119_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_));
    }

    public static boolean func_150118_d(int p_150118_0_) {
        if ((p_150118_0_ & 4) != 0) {
            return true;
        }
        return false;
    }

    private static boolean func_150119_a(Block p_150119_0_) {
        if (!(p_150119_0_.blockMaterial.isOpaque() && p_150119_0_.renderAsNormalBlock() || p_150119_0_ == Blocks.glowstone || p_150119_0_ instanceof BlockSlab || p_150119_0_ instanceof BlockStairs)) {
            return false;
        }
        return true;
    }
}

