/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension
extends Block {
    private IIcon field_150088_a;
    private static final String __OBFID = "CL_00000367";

    public BlockPistonExtension() {
        super(Material.piston);
        this.setStepSound(soundTypePiston);
        this.setHardness(0.5f);
    }

    public void func_150086_a(IIcon p_150086_1_) {
        this.field_150088_a = p_150086_1_;
    }

    public void func_150087_e() {
        this.field_150088_a = null;
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        Block var8;
        int var7;
        if (p_149681_6_.capabilities.isCreativeMode && ((var8 = p_149681_1_.getBlock(p_149681_2_ - Facing.offsetsXForSide[var7 = BlockPistonExtension.func_150085_b(p_149681_5_)], p_149681_3_ - Facing.offsetsYForSide[var7], p_149681_4_ - Facing.offsetsZForSide[var7])) == Blocks.piston || var8 == Blocks.sticky_piston)) {
            p_149681_1_.setBlockToAir(p_149681_2_ - Facing.offsetsXForSide[var7], p_149681_3_ - Facing.offsetsYForSide[var7], p_149681_4_ - Facing.offsetsZForSide[var7]);
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        int var7 = Facing.oppositeSide[BlockPistonExtension.func_150085_b(p_149749_6_)];
        Block var8 = p_149749_1_.getBlock(p_149749_2_ += Facing.offsetsXForSide[var7], p_149749_3_ += Facing.offsetsYForSide[var7], p_149749_4_ += Facing.offsetsZForSide[var7]);
        if ((var8 == Blocks.piston || var8 == Blocks.sticky_piston) && BlockPistonBase.func_150075_c(p_149749_6_ = p_149749_1_.getBlockMetadata(p_149749_2_, p_149749_3_, p_149749_4_))) {
            var8.dropBlockAsItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_, 0);
            p_149749_1_.setBlockToAir(p_149749_2_, p_149749_3_, p_149749_4_);
        }
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        int var3 = BlockPistonExtension.func_150085_b(p_149691_2_);
        return p_149691_1_ == var3 ? (this.field_150088_a != null ? this.field_150088_a : ((p_149691_2_ & 8) != 0 ? BlockPistonBase.func_150074_e("piston_top_sticky") : BlockPistonBase.func_150074_e("piston_top_normal"))) : (var3 < 6 && p_149691_1_ == Facing.oppositeSide[var3] ? BlockPistonBase.func_150074_e("piston_top_normal") : BlockPistonBase.func_150074_e("piston_side"));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }

    @Override
    public int getRenderType() {
        return 17;
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
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_) {
        return false;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        int var8 = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
        float var9 = 0.25f;
        float var10 = 0.375f;
        float var11 = 0.625f;
        float var12 = 0.25f;
        float var13 = 0.75f;
        switch (BlockPistonExtension.func_150085_b(var8)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
            }
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float var6 = 0.25f;
        switch (BlockPistonExtension.func_150085_b(var5)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        int var6 = BlockPistonExtension.func_150085_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
        Block var7 = p_149695_1_.getBlock(p_149695_2_ - Facing.offsetsXForSide[var6], p_149695_3_ - Facing.offsetsYForSide[var6], p_149695_4_ - Facing.offsetsZForSide[var6]);
        if (var7 != Blocks.piston && var7 != Blocks.sticky_piston) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        } else {
            var7.onNeighborBlockChange(p_149695_1_, p_149695_2_ - Facing.offsetsXForSide[var6], p_149695_3_ - Facing.offsetsYForSide[var6], p_149695_4_ - Facing.offsetsZForSide[var6], p_149695_5_);
        }
    }

    public static int func_150085_b(int p_150085_0_) {
        return p_150085_0_ & 7;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        int var5 = p_149694_1_.getBlockMetadata(p_149694_2_, p_149694_3_, p_149694_4_);
        return (var5 & 8) != 0 ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
    }
}

