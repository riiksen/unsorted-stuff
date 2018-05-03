/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class BlockDaylightDetector
extends BlockContainer {
    private IIcon[] field_149958_a = new IIcon[2];
    private static final String __OBFID = "CL_00000223";

    public BlockDaylightDetector() {
        super(Material.wood);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
        return p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
    }

    public void func_149957_e(World p_149957_1_, int p_149957_2_, int p_149957_3_, int p_149957_4_) {
        if (!p_149957_1_.provider.hasNoSky) {
            int var5 = p_149957_1_.getBlockMetadata(p_149957_2_, p_149957_3_, p_149957_4_);
            int var6 = p_149957_1_.getSavedLightValue(EnumSkyBlock.Sky, p_149957_2_, p_149957_3_, p_149957_4_) - p_149957_1_.skylightSubtracted;
            float var7 = p_149957_1_.getCelestialAngleRadians(1.0f);
            var7 = var7 < 3.1415927f ? (var7 += (0.0f - var7) * 0.2f) : (var7 += (6.2831855f - var7) * 0.2f);
            var6 = Math.round((float)var6 * MathHelper.cos(var7));
            if (var6 < 0) {
                var6 = 0;
            }
            if (var6 > 15) {
                var6 = 15;
            }
            if (var5 != var6) {
                p_149957_1_.setBlockMetadataWithNotify(p_149957_2_, p_149957_3_, p_149957_4_, var6, 3);
            }
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityDaylightDetector();
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_149958_a[0] : this.field_149958_a[1];
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149958_a[0] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_top");
        this.field_149958_a[1] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_side");
    }
}

