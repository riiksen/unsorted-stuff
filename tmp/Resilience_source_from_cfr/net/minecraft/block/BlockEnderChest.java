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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockEnderChest
extends BlockContainer {
    private static final String __OBFID = "CL_00000238";

    protected BlockEnderChest() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
        return 22;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 8;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = 0;
        int var8 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (var8 == 0) {
            var7 = 2;
        }
        if (var8 == 1) {
            var7 = 5;
        }
        if (var8 == 2) {
            var7 = 3;
        }
        if (var8 == 3) {
            var7 = 4;
        }
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        InventoryEnderChest var10 = p_149727_5_.getInventoryEnderChest();
        TileEntityEnderChest var11 = (TileEntityEnderChest)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null && var11 != null) {
            if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_ + 1, p_149727_4_).isNormalCube()) {
                return true;
            }
            if (p_149727_1_.isClient) {
                return true;
            }
            var10.func_146031_a(var11);
            p_149727_5_.displayGUIChest(var10);
            return true;
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityEnderChest();
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        int var6 = 0;
        while (var6 < 3) {
            double var10000 = (float)p_149734_2_ + p_149734_5_.nextFloat();
            double var9 = (float)p_149734_3_ + p_149734_5_.nextFloat();
            var10000 = (float)p_149734_4_ + p_149734_5_.nextFloat();
            double var13 = 0.0;
            double var15 = 0.0;
            double var17 = 0.0;
            int var19 = p_149734_5_.nextInt(2) * 2 - 1;
            int var20 = p_149734_5_.nextInt(2) * 2 - 1;
            var13 = ((double)p_149734_5_.nextFloat() - 0.5) * 0.125;
            var15 = ((double)p_149734_5_.nextFloat() - 0.5) * 0.125;
            var17 = ((double)p_149734_5_.nextFloat() - 0.5) * 0.125;
            double var11 = (double)p_149734_4_ + 0.5 + 0.25 * (double)var20;
            var17 = p_149734_5_.nextFloat() * 1.0f * (float)var20;
            double var7 = (double)p_149734_2_ + 0.5 + 0.25 * (double)var19;
            var13 = p_149734_5_.nextFloat() * 1.0f * (float)var19;
            p_149734_1_.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
            ++var6;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("obsidian");
    }
}

