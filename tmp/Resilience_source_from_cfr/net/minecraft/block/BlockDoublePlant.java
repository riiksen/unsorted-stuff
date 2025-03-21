/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockDoublePlant
extends BlockBush
implements IGrowable {
    public static final String[] field_149892_a = new String[]{"sunflower", "syringa", "grass", "fern", "rose", "paeonia"};
    private IIcon[] field_149893_M;
    private IIcon[] field_149894_N;
    public IIcon[] field_149891_b;
    private static final String __OBFID = "CL_00000231";

    public BlockDoublePlant() {
        super(Material.plants);
        this.setHardness(0.0f);
        this.setStepSound(soundTypeGrass);
        this.setBlockName("doublePlant");
    }

    @Override
    public int getRenderType() {
        return 40;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    public int func_149885_e(IBlockAccess p_149885_1_, int p_149885_2_, int p_149885_3_, int p_149885_4_) {
        int var5 = p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_, p_149885_4_);
        return !BlockDoublePlant.func_149887_c(var5) ? var5 & 7 : p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_ - 1, p_149885_4_) & 7;
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        if (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && p_149742_1_.isAirBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_)) {
            return true;
        }
        return false;
    }

    @Override
    protected void func_149855_e(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_) {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_)) {
            int var5 = p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_);
            if (!BlockDoublePlant.func_149887_c(var5)) {
                this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, var5, 0);
                if (p_149855_1_.getBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_) == this) {
                    p_149855_1_.setBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_, Blocks.air, 0, 2);
                }
            }
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, Blocks.air, 0, 2);
        }
    }

    @Override
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        int var5 = p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_);
        return BlockDoublePlant.func_149887_c(var5) ? p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == this : p_149718_1_.getBlock(p_149718_2_, p_149718_3_ + 1, p_149718_4_) == this && super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        if (BlockDoublePlant.func_149887_c(p_149650_1_)) {
            return null;
        }
        int var4 = BlockDoublePlant.func_149890_d(p_149650_1_);
        return var4 != 3 && var4 != 2 ? Item.getItemFromBlock(this) : null;
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return BlockDoublePlant.func_149887_c(p_149692_1_) ? 0 : p_149692_1_ & 7;
    }

    public static boolean func_149887_c(int p_149887_0_) {
        if ((p_149887_0_ & 8) != 0) {
            return true;
        }
        return false;
    }

    public static int func_149890_d(int p_149890_0_) {
        return p_149890_0_ & 7;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return BlockDoublePlant.func_149887_c(p_149691_2_) ? this.field_149893_M[0] : this.field_149893_M[p_149691_2_ & 7];
    }

    public IIcon func_149888_a(boolean p_149888_1_, int p_149888_2_) {
        return p_149888_1_ ? this.field_149894_N[p_149888_2_] : this.field_149893_M[p_149888_2_];
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        int var5 = this.func_149885_e(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
        return var5 != 2 && var5 != 3 ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }

    public void func_149889_c(World p_149889_1_, int p_149889_2_, int p_149889_3_, int p_149889_4_, int p_149889_5_, int p_149889_6_) {
        p_149889_1_.setBlock(p_149889_2_, p_149889_3_, p_149889_4_, this, p_149889_5_, p_149889_6_);
        p_149889_1_.setBlock(p_149889_2_, p_149889_3_ + 1, p_149889_4_, this, 8, p_149889_6_);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = ((MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) + 2) % 4;
        p_149689_1_.setBlock(p_149689_2_, p_149689_3_ + 1, p_149689_4_, this, 8 | var7, 2);
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        if (p_149636_1_.isClient || p_149636_2_.getCurrentEquippedItem() == null || p_149636_2_.getCurrentEquippedItem().getItem() != Items.shears || BlockDoublePlant.func_149887_c(p_149636_6_) || !this.func_149886_b(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, p_149636_2_)) {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        if (BlockDoublePlant.func_149887_c(p_149681_5_)) {
            if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this) {
                if (!p_149681_6_.capabilities.isCreativeMode) {
                    int var7 = p_149681_1_.getBlockMetadata(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                    int var8 = BlockDoublePlant.func_149890_d(var7);
                    if (var8 != 3 && var8 != 2) {
                        p_149681_1_.func_147480_a(p_149681_2_, p_149681_3_ - 1, p_149681_4_, true);
                    } else {
                        if (!p_149681_1_.isClient && p_149681_6_.getCurrentEquippedItem() != null && p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears) {
                            this.func_149886_b(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, var7, p_149681_6_);
                        }
                        p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                    }
                } else {
                    p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                }
            }
        } else if (p_149681_6_.capabilities.isCreativeMode && p_149681_1_.getBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_) == this) {
            p_149681_1_.setBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_, Blocks.air, 0, 2);
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    private boolean func_149886_b(World p_149886_1_, int p_149886_2_, int p_149886_3_, int p_149886_4_, int p_149886_5_, EntityPlayer p_149886_6_) {
        int var7 = BlockDoublePlant.func_149890_d(p_149886_5_);
        if (var7 != 3 && var7 != 2) {
            return false;
        }
        p_149886_6_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        int var8 = 1;
        if (var7 == 3) {
            var8 = 2;
        }
        this.dropBlockAsItem_do(p_149886_1_, p_149886_2_, p_149886_3_, p_149886_4_, new ItemStack(Blocks.tallgrass, 2, var8));
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149893_M = new IIcon[field_149892_a.length];
        this.field_149894_N = new IIcon[field_149892_a.length];
        int var2 = 0;
        while (var2 < this.field_149893_M.length) {
            this.field_149893_M[var2] = p_149651_1_.registerIcon("double_plant_" + field_149892_a[var2] + "_bottom");
            this.field_149894_N[var2] = p_149651_1_.registerIcon("double_plant_" + field_149892_a[var2] + "_top");
            ++var2;
        }
        this.field_149891_b = new IIcon[2];
        this.field_149891_b[0] = p_149651_1_.registerIcon("double_plant_sunflower_front");
        this.field_149891_b[1] = p_149651_1_.registerIcon("double_plant_sunflower_back");
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 0;
        while (var4 < this.field_149893_M.length) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        int var5 = p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
        return BlockDoublePlant.func_149887_c(var5) ? BlockDoublePlant.func_149890_d(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_ - 1, p_149643_4_)) : BlockDoublePlant.func_149890_d(var5);
    }

    @Override
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
        int var6 = this.func_149885_e(p_149851_1_, p_149851_2_, p_149851_3_, p_149851_4_);
        if (var6 != 2 && var6 != 3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
        return true;
    }

    @Override
    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {
        int var6 = this.func_149885_e(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
        this.dropBlockAsItem_do(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, new ItemStack(this, 1, var6));
    }
}

