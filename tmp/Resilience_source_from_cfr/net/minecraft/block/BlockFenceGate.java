/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate
extends BlockDirectional {
    private static final String __OBFID = "CL_00000243";

    public BlockFenceGate() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return !p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_).getMaterial().isSolid() ? false : super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
        return BlockFenceGate.isFenceGateOpen(var5) ? null : (var5 != 2 && var5 != 0 ? AxisAlignedBB.getAABBPool().getAABB((float)p_149668_2_ + 0.375f, p_149668_3_, p_149668_4_, (float)p_149668_2_ + 0.625f, (float)p_149668_3_ + 1.5f, p_149668_4_ + 1) : AxisAlignedBB.getAABBPool().getAABB(p_149668_2_, p_149668_3_, (float)p_149668_4_ + 0.375f, p_149668_2_ + 1, (float)p_149668_3_ + 1.5f, (float)p_149668_4_ + 0.625f));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        int var5 = BlockFenceGate.func_149895_l(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
        if (var5 != 2 && var5 != 0) {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
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
        return BlockFenceGate.isFenceGateOpen(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
    }

    @Override
    public int getRenderType() {
        return 21;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = (MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) % 4;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if (BlockFenceGate.isFenceGateOpen(var10)) {
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 & -5, 2);
        } else {
            int var11 = (MathHelper.floor_double((double)(p_149727_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) % 4;
            int var12 = BlockFenceGate.func_149895_l(var10);
            if (var12 == (var11 + 2) % 4) {
                var10 = var11;
            }
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 | 4, 2);
        }
        p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            boolean var7 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
            if (var7 || p_149695_5_.canProvidePower()) {
                if (var7 && !BlockFenceGate.isFenceGateOpen(var6)) {
                    p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 | 4, 2);
                    p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
                } else if (!var7 && BlockFenceGate.isFenceGateOpen(var6)) {
                    p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 & -5, 2);
                    p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
                }
            }
        }
    }

    public static boolean isFenceGateOpen(int p_149896_0_) {
        if ((p_149896_0_ & 4) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }
}

