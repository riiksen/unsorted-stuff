/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.world.World;

public class BlockBeacon
extends BlockContainer {
    private static final String __OBFID = "CL_00000197";

    public BlockBeacon() {
        super(Material.glass);
        this.setHardness(3.0f);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBeacon();
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        TileEntityBeacon var10 = (TileEntityBeacon)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146104_a(var10);
        }
        return true;
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
        return 34;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityBeacon)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145999_a(p_149689_6_.getDisplayName());
        }
    }
}

