/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops
extends BlockBush
implements IGrowable {
    private IIcon[] field_149867_a;
    private static final String __OBFID = "CL_00000222";

    protected BlockCrops() {
        this.setTickRandomly(true);
        float var1 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    @Override
    protected boolean func_149854_a(Block p_149854_1_) {
        if (p_149854_1_ == Blocks.farmland) {
            return true;
        }
        return false;
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        float var7;
        int var6;
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && (var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_)) < 7 && p_149674_5_.nextInt((int)(25.0f / (var7 = this.func_149864_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))) + 1) == 0) {
            p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, ++var6, 2);
        }
    }

    public void func_149863_m(World p_149863_1_, int p_149863_2_, int p_149863_3_, int p_149863_4_) {
        int var5 = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + MathHelper.getRandomIntegerInRange(p_149863_1_.rand, 2, 5);
        if (var5 > 7) {
            var5 = 7;
        }
        p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, var5, 2);
    }

    private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_) {
        float var5 = 1.0f;
        Block var6 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        Block var7 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        Block var8 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        Block var9 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        Block var10 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        Block var11 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        Block var12 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        Block var13 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        boolean var14 = var8 == this || var9 == this;
        boolean var15 = var6 == this || var7 == this;
        boolean var16 = var10 == this || var11 == this || var12 == this || var13 == this;
        int var17 = p_149864_2_ - 1;
        while (var17 <= p_149864_2_ + 1) {
            int var18 = p_149864_4_ - 1;
            while (var18 <= p_149864_4_ + 1) {
                float var19 = 0.0f;
                if (p_149864_1_.getBlock(var17, p_149864_3_ - 1, var18) == Blocks.farmland) {
                    var19 = 1.0f;
                    if (p_149864_1_.getBlockMetadata(var17, p_149864_3_ - 1, var18) > 0) {
                        var19 = 3.0f;
                    }
                }
                if (var17 != p_149864_2_ || var18 != p_149864_4_) {
                    var19 /= 4.0f;
                }
                var5 += var19;
                ++var18;
            }
            ++var17;
        }
        if (var16 || var14 && var15) {
            var5 /= 2.0f;
        }
        return var5;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ > 7) {
            p_149691_2_ = 7;
        }
        return this.field_149867_a[p_149691_2_];
    }

    @Override
    public int getRenderType() {
        return 6;
    }

    protected Item func_149866_i() {
        return Items.wheat_seeds;
    }

    protected Item func_149865_P() {
        return Items.wheat;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        if (!p_149690_1_.isClient && p_149690_5_ >= 7) {
            int var8 = 3 + p_149690_7_;
            int var9 = 0;
            while (var9 < var8) {
                if (p_149690_1_.rand.nextInt(15) <= p_149690_5_) {
                    this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(this.func_149866_i(), 1, 0));
                }
                ++var9;
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_1_ == 7 ? this.func_149865_P() : this.func_149866_i();
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 1;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return this.func_149866_i();
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149867_a = new IIcon[8];
        int var2 = 0;
        while (var2 < this.field_149867_a.length) {
            this.field_149867_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_stage_" + var2);
            ++var2;
        }
    }

    @Override
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
        if (p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_) != 7) {
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
        this.func_149863_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
    }
}

