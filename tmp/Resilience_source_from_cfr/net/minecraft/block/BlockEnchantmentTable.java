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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEnchantmentTable
extends BlockContainer {
    private IIcon field_149950_a;
    private IIcon field_149949_b;
    private static final String __OBFID = "CL_00000235";

    protected BlockEnchantmentTable() {
        super(Material.rock);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
        int var6 = p_149734_2_ - 2;
        while (var6 <= p_149734_2_ + 2) {
            int var7 = p_149734_4_ - 2;
            while (var7 <= p_149734_4_ + 2) {
                if (var6 > p_149734_2_ - 2 && var6 < p_149734_2_ + 2 && var7 == p_149734_4_ - 1) {
                    var7 = p_149734_4_ + 2;
                }
                if (p_149734_5_.nextInt(16) == 0) {
                    int var8 = p_149734_3_;
                    while (var8 <= p_149734_3_ + 1) {
                        if (p_149734_1_.getBlock(var6, var8, var7) == Blocks.bookshelf) {
                            if (!p_149734_1_.isAirBlock((var6 - p_149734_2_) / 2 + p_149734_2_, var8, (var7 - p_149734_4_) / 2 + p_149734_4_)) break;
                            p_149734_1_.spawnParticle("enchantmenttable", (double)p_149734_2_ + 0.5, (double)p_149734_3_ + 2.0, (double)p_149734_4_ + 0.5, (double)((float)(var6 - p_149734_2_) + p_149734_5_.nextFloat()) - 0.5, (float)(var8 - p_149734_3_) - p_149734_5_.nextFloat() - 1.0f, (double)((float)(var7 - p_149734_4_) + p_149734_5_.nextFloat()) - 0.5);
                        }
                        ++var8;
                    }
                }
                ++var7;
            }
            ++var6;
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 0 ? this.field_149949_b : (p_149691_1_ == 1 ? this.field_149950_a : this.blockIcon);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityEnchantmentTable();
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        p_149727_5_.displayGUIEnchantment(p_149727_2_, p_149727_3_, p_149727_4_, var10.func_145921_b() ? var10.func_145919_a() : null);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityEnchantmentTable)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145920_a(p_149689_6_.getDisplayName());
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "side");
        this.field_149950_a = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "top");
        this.field_149949_b = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "bottom");
    }
}

