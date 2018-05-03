/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockTallGrass
extends BlockBush
implements IGrowable {
    private static final String[] field_149871_a = new String[]{"deadbush", "tallgrass", "fern"};
    private IIcon[] field_149870_b;
    private static final String __OBFID = "CL_00000321";

    protected BlockTallGrass() {
        super(Material.vine);
        float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ >= this.field_149870_b.length) {
            p_149691_2_ = 0;
        }
        return this.field_149870_b[p_149691_2_];
    }

    @Override
    public int getBlockColor() {
        double var1 = 0.5;
        double var3 = 1.0;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    @Override
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return this.func_149854_a(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
    }

    @Override
    public int getRenderColor(int p_149741_1_) {
        return p_149741_1_ == 0 ? 16777215 : ColorizerGrass.getGrassColor(0.5, 1.0);
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        int var5 = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
        return var5 == 0 ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_2_.nextInt(8) == 0 ? Items.wheat_seeds : null;
    }

    @Override
    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
        return 1 + p_149679_2_.nextInt(p_149679_1_ * 2 + 1);
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.tallgrass, 1, p_149636_6_));
        } else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }

    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        int var4 = 1;
        while (var4 < 3) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            ++var4;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149870_b = new IIcon[field_149871_a.length];
        int var2 = 0;
        while (var2 < this.field_149870_b.length) {
            this.field_149870_b[var2] = p_149651_1_.registerIcon(field_149871_a[var2]);
            ++var2;
        }
    }

    @Override
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
        int var6 = p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_);
        if (var6 != 0) {
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
        int var6 = p_149853_1_.getBlockMetadata(p_149853_3_, p_149853_4_, p_149853_5_);
        int var7 = 2;
        if (var6 == 2) {
            var7 = 3;
        }
        if (Blocks.double_plant.canPlaceBlockAt(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_)) {
            Blocks.double_plant.func_149889_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, var7, 2);
        }
    }
}

