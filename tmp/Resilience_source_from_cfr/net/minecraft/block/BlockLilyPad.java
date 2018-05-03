/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLilyPad
extends BlockBush {
    private static final String __OBFID = "CL_00000332";

    protected BlockLilyPad() {
        float var1 = 0.5f;
        float var2 = 0.015625f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var2, 0.5f + var1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public int getRenderType() {
        return 23;
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        if (p_149743_7_ == null || !(p_149743_7_ instanceof EntityBoat)) {
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return AxisAlignedBB.getAABBPool().getAABB((double)p_149668_2_ + this.field_149759_B, (double)p_149668_3_ + this.field_149760_C, (double)p_149668_4_ + this.field_149754_D, (double)p_149668_2_ + this.field_149755_E, (double)p_149668_3_ + this.field_149756_F, (double)p_149668_4_ + this.field_149757_G);
    }

    @Override
    public int getBlockColor() {
        return 2129968;
    }

    @Override
    public int getRenderColor(int p_149741_1_) {
        return 2129968;
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return 2129968;
    }

    @Override
    protected boolean func_149854_a(Block p_149854_1_) {
        if (p_149854_1_ == Blocks.water) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return p_149718_3_ >= 0 && p_149718_3_ < 256 ? p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).getMaterial() == Material.water && p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == 0 : false;
    }
}

