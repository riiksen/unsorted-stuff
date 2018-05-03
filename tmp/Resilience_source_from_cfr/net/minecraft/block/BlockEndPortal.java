/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class BlockEndPortal
extends BlockContainer {
    public static boolean field_149948_a;
    private static final String __OBFID = "CL_00000236";

    protected BlockEndPortal(Material p_i45404_1_) {
        super(p_i45404_1_);
        this.setLightLevel(1.0f);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityEndPortal();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        float var5 = 0.0625f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var5, 1.0f);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return p_149646_5_ != 0 ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
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
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        if (p_149670_5_.ridingEntity == null && p_149670_5_.riddenByEntity == null && !p_149670_1_.isClient) {
            p_149670_5_.travelToDimension(1);
        }
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        double var6 = (float)p_149734_2_ + p_149734_5_.nextFloat();
        double var8 = (float)p_149734_3_ + 0.8f;
        double var10 = (float)p_149734_4_ + p_149734_5_.nextFloat();
        double var12 = 0.0;
        double var14 = 0.0;
        double var16 = 0.0;
        p_149734_1_.spawnParticle("smoke", var6, var8, var10, var12, var14, var16);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        if (!field_149948_a && p_149726_1_.provider.dimensionId != 0) {
            p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
        }
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemById(0);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("portal");
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return MapColor.field_151654_J;
    }
}

