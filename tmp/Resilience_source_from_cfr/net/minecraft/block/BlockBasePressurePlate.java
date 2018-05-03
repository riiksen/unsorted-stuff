/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate
extends Block {
    private String field_150067_a;
    private static final String __OBFID = "CL_00000194";

    protected BlockBasePressurePlate(String p_i45387_1_, Material p_i45387_2_) {
        super(p_i45387_2_);
        this.field_150067_a = p_i45387_1_;
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
        this.func_150063_b(this.func_150066_d(15));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.func_150063_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }

    protected void func_150063_b(int p_150063_1_) {
        boolean var2 = this.func_150060_c(p_150063_1_) > 0;
        float var3 = 0.0625f;
        if (var2) {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.03125f, 1.0f - var3);
        } else {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.0625f, 1.0f - var3);
        }
    }

    @Override
    public int func_149738_a(World p_149738_1_) {
        return 20;
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
    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        if (!World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) && !BlockFence.func_149825_a(p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_))) {
            return false;
        }
        return true;
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        boolean var6 = false;
        if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_) && !BlockFence.func_149825_a(p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_))) {
            var6 = true;
        }
        if (var6) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        int var6;
        if (!p_149674_1_.isClient && (var6 = this.func_150060_c(p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_))) > 0) {
            this.func_150062_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        int var6;
        if (!p_149670_1_.isClient && (var6 = this.func_150060_c(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_))) == 0) {
            this.func_150062_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6);
        }
    }

    protected void func_150062_a(World p_150062_1_, int p_150062_2_, int p_150062_3_, int p_150062_4_, int p_150062_5_) {
        boolean var8;
        int var6 = this.func_150065_e(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
        boolean var7 = p_150062_5_ > 0;
        boolean bl = var8 = var6 > 0;
        if (p_150062_5_ != var6) {
            p_150062_1_.setBlockMetadataWithNotify(p_150062_2_, p_150062_3_, p_150062_4_, this.func_150066_d(var6), 2);
            this.func_150064_a_(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
            p_150062_1_.markBlockRangeForRenderUpdate(p_150062_2_, p_150062_3_, p_150062_4_, p_150062_2_, p_150062_3_, p_150062_4_);
        }
        if (!var8 && var7) {
            p_150062_1_.playSoundEffect((double)p_150062_2_ + 0.5, (double)p_150062_3_ + 0.1, (double)p_150062_4_ + 0.5, "random.click", 0.3f, 0.5f);
        } else if (var8 && !var7) {
            p_150062_1_.playSoundEffect((double)p_150062_2_ + 0.5, (double)p_150062_3_ + 0.1, (double)p_150062_4_ + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (var8) {
            p_150062_1_.scheduleBlockUpdate(p_150062_2_, p_150062_3_, p_150062_4_, this, this.func_149738_a(p_150062_1_));
        }
    }

    protected AxisAlignedBB func_150061_a(int p_150061_1_, int p_150061_2_, int p_150061_3_) {
        float var4 = 0.125f;
        return AxisAlignedBB.getAABBPool().getAABB((float)p_150061_1_ + var4, p_150061_2_, (float)p_150061_3_ + var4, (float)(p_150061_1_ + 1) - var4, (double)p_150061_2_ + 0.25, (float)(p_150061_3_ + 1) - var4);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        if (this.func_150060_c(p_149749_6_) > 0) {
            this.func_150064_a_(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    protected void func_150064_a_(World p_150064_1_, int p_150064_2_, int p_150064_3_, int p_150064_4_) {
        p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_, p_150064_4_, this);
        p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_ - 1, p_150064_4_, this);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
        return this.func_150060_c(p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_));
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
        return p_149748_5_ == 1 ? this.func_150060_c(p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_)) : 0;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float var1 = 0.5f;
        float var2 = 0.125f;
        float var3 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    protected abstract int func_150065_e(World var1, int var2, int var3, int var4);

    protected abstract int func_150060_c(int var1);

    protected abstract int func_150066_d(int var1);

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.field_150067_a);
    }
}

