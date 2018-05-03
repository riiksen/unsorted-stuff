/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.world.World;

public class BlockFlowerPot
extends BlockContainer {
    private static final String __OBFID = "CL_00000247";

    public BlockFlowerPot() {
        super(Material.circuits);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float var1 = 0.375f;
        float var2 = var1 / 2.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var1, 0.5f + var2);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 33;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        ItemStack var10 = p_149727_5_.inventory.getCurrentItem();
        if (var10 != null && var10.getItem() instanceof ItemBlock) {
            if (p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_) != 0) {
                return false;
            }
            TileEntityFlowerPot var11 = this.func_149929_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
            if (var11 != null) {
                Block var12 = Block.getBlockFromItem(var10.getItem());
                if (!this.func_149928_a(var12, var10.getItemDamage())) {
                    return false;
                }
                var11.func_145964_a(var10.getItem(), var10.getItemDamage());
                var11.onInventoryChanged();
                if (!p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10.getItemDamage(), 2)) {
                    p_149727_1_.func_147471_g(p_149727_2_, p_149727_3_, p_149727_4_);
                }
                if (!p_149727_5_.capabilities.isCreativeMode && --var10.stackSize <= 0) {
                    p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, null);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean func_149928_a(Block p_149928_1_, int p_149928_2_) {
        return p_149928_1_ != Blocks.yellow_flower && p_149928_1_ != Blocks.red_flower && p_149928_1_ != Blocks.cactus && p_149928_1_ != Blocks.brown_mushroom && p_149928_1_ != Blocks.red_mushroom && p_149928_1_ != Blocks.sapling && p_149928_1_ != Blocks.deadbush ? p_149928_1_ == Blocks.tallgrass && p_149928_2_ == 2 : true;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        TileEntityFlowerPot var5 = this.func_149929_e(p_149694_1_, p_149694_2_, p_149694_3_, p_149694_4_);
        return var5 != null && var5.func_145965_a() != null ? var5.func_145965_a() : Items.flower_pot;
    }

    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        TileEntityFlowerPot var5 = this.func_149929_e(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
        return var5 != null && var5.func_145965_a() != null ? var5.func_145966_b() : 0;
    }

    @Override
    public boolean isFlowerPot() {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        if (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_)) {
            return true;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        TileEntityFlowerPot var8 = this.func_149929_e(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_);
        if (var8 != null && var8.func_145965_a() != null) {
            this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var8.func_145965_a(), 1, var8.func_145966_b()));
        }
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        TileEntityFlowerPot var7 = this.func_149929_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 != null && var7.func_145965_a() != null) {
            this.dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, new ItemStack(var7.func_145965_a(), 1, var7.func_145966_b()));
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        TileEntityFlowerPot var7;
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
        if (p_149681_6_.capabilities.isCreativeMode && (var7 = this.func_149929_e(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_)) != null) {
            var7.func_145964_a(Item.getItemById(0), 0);
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.flower_pot;
    }

    private TileEntityFlowerPot func_149929_e(World p_149929_1_, int p_149929_2_, int p_149929_3_, int p_149929_4_) {
        TileEntity var5 = p_149929_1_.getTileEntity(p_149929_2_, p_149929_3_, p_149929_4_);
        return var5 != null && var5 instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)var5 : null;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        Block var3 = null;
        int var4 = 0;
        switch (p_149915_2_) {
            case 1: {
                var3 = Blocks.red_flower;
                var4 = 0;
                break;
            }
            case 2: {
                var3 = Blocks.yellow_flower;
                break;
            }
            case 3: {
                var3 = Blocks.sapling;
                var4 = 0;
                break;
            }
            case 4: {
                var3 = Blocks.sapling;
                var4 = 1;
                break;
            }
            case 5: {
                var3 = Blocks.sapling;
                var4 = 2;
                break;
            }
            case 6: {
                var3 = Blocks.sapling;
                var4 = 3;
                break;
            }
            case 7: {
                var3 = Blocks.red_mushroom;
                break;
            }
            case 8: {
                var3 = Blocks.brown_mushroom;
                break;
            }
            case 9: {
                var3 = Blocks.cactus;
                break;
            }
            case 10: {
                var3 = Blocks.deadbush;
                break;
            }
            case 11: {
                var3 = Blocks.tallgrass;
                var4 = 2;
                break;
            }
            case 12: {
                var3 = Blocks.sapling;
                var4 = 4;
                break;
            }
            case 13: {
                var3 = Blocks.sapling;
                var4 = 5;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock(var3), var4);
    }
}

