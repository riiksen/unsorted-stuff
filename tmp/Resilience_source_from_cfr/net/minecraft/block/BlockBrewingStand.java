/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBrewingStand
extends BlockContainer {
    private Random field_149961_a = new Random();
    private IIcon field_149960_b;
    private static final String __OBFID = "CL_00000207";

    public BlockBrewingStand() {
        super(Material.iron);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 25;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBrewingStand();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        TileEntityBrewingStand var10 = (TileEntityBrewingStand)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146098_a(var10);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityBrewingStand)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145937_a(p_149689_6_.getDisplayName());
        }
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        double var6 = (float)p_149734_2_ + 0.4f + p_149734_5_.nextFloat() * 0.2f;
        double var8 = (float)p_149734_3_ + 0.7f + p_149734_5_.nextFloat() * 0.3f;
        double var10 = (float)p_149734_4_ + 0.4f + p_149734_5_.nextFloat() * 0.2f;
        p_149734_1_.spawnParticle("smoke", var6, var8, var10, 0.0, 0.0, 0.0);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 instanceof TileEntityBrewingStand) {
            TileEntityBrewingStand var8 = (TileEntityBrewingStand)var7;
            int var9 = 0;
            while (var9 < var8.getSizeInventory()) {
                ItemStack var10 = var8.getStackInSlot(var9);
                if (var10 != null) {
                    float var11 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    float var12 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    float var13 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    while (var10.stackSize > 0) {
                        int var14 = this.field_149961_a.nextInt(21) + 10;
                        if (var14 > var10.stackSize) {
                            var14 = var10.stackSize;
                        }
                        var10.stackSize -= var14;
                        EntityItem var15 = new EntityItem(p_149749_1_, (float)p_149749_2_ + var11, (float)p_149749_3_ + var12, (float)p_149749_4_ + var13, new ItemStack(var10.getItem(), var14, var10.getItemDamage()));
                        float var16 = 0.05f;
                        var15.motionX = (float)this.field_149961_a.nextGaussian() * var16;
                        var15.motionY = (float)this.field_149961_a.nextGaussian() * var16 + 0.2f;
                        var15.motionZ = (float)this.field_149961_a.nextGaussian() * var16;
                        p_149749_1_.spawnEntityInWorld(var15);
                    }
                }
                ++var9;
            }
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.brewing_stand;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.brewing_stand;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_) {
        return Container.calcRedstoneFromInventory((IInventory)((Object)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_)));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.field_149960_b = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_base");
    }

    public IIcon func_149959_e() {
        return this.field_149960_b;
    }
}

