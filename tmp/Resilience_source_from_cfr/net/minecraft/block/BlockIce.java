/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class BlockIce
extends BlockBreakable {
    private static final String __OBFID = "CL_00000259";

    public BlockIce() {
        super("ice", Material.ice, false);
        this.slipperiness = 0.98f;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, 1 - p_149646_5_);
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        p_149636_2_.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(p_149636_2_)) {
            ItemStack var9 = this.createStackedBlock(p_149636_6_);
            if (var9 != null) {
                this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, var9);
            }
        } else {
            if (p_149636_1_.provider.isHellWorld) {
                p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
                return;
            }
            int var7 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
            this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, var7);
            Material var8 = p_149636_1_.getBlock(p_149636_3_, p_149636_4_ - 1, p_149636_5_).getMaterial();
            if (var8.blocksMovement() || var8.isLiquid()) {
                p_149636_1_.setBlock(p_149636_3_, p_149636_4_, p_149636_5_, Blocks.flowing_water);
            }
        }
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11 - this.getLightOpacity()) {
            if (p_149674_1_.provider.isHellWorld) {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
                return;
            }
            this.dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.water);
        }
    }

    @Override
    public int getMobilityFlag() {
        return 0;
    }
}

