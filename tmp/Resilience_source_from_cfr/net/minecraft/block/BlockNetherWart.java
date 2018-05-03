/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockNetherWart
extends BlockBush {
    private IIcon[] field_149883_a;
    private static final String __OBFID = "CL_00000274";

    protected BlockNetherWart() {
        this.setTickRandomly(true);
        float var1 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
        this.setCreativeTab(null);
    }

    @Override
    protected boolean func_149854_a(Block p_149854_1_) {
        if (p_149854_1_ == Blocks.soul_sand) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return this.func_149854_a(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
        if (var6 < 3 && p_149674_5_.nextInt(10) == 0) {
            p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, ++var6, 2);
        }
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_2_ >= 3 ? this.field_149883_a[2] : (p_149691_2_ > 0 ? this.field_149883_a[1] : this.field_149883_a[0]);
    }

    @Override
    public int getRenderType() {
        return 6;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            int var8 = 1;
            if (p_149690_5_ >= 3) {
                var8 = 2 + p_149690_1_.rand.nextInt(3);
                if (p_149690_7_ > 0) {
                    var8 += p_149690_1_.rand.nextInt(p_149690_7_ + 1);
                }
            }
            int var9 = 0;
            while (var9 < var8) {
                this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.nether_wart));
                ++var9;
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.nether_wart;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149883_a = new IIcon[3];
        int var2 = 0;
        while (var2 < this.field_149883_a.length) {
            this.field_149883_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_stage_" + var2);
            ++var2;
        }
    }
}

