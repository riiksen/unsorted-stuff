/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockNewLeaf
extends BlockLeaves {
    public static final String[][] field_150132_N = new String[][]{{"leaves_acacia", "leaves_big_oak"}, {"leaves_acacia_opaque", "leaves_big_oak_opaque"}};
    public static final String[] field_150133_O = new String[]{"acacia", "big_oak"};
    private static final String __OBFID = "CL_00000276";

    @Override
    protected void func_150124_c(World p_150124_1_, int p_150124_2_, int p_150124_3_, int p_150124_4_, int p_150124_5_, int p_150124_6_) {
        if ((p_150124_5_ & 3) == 1 && p_150124_1_.rand.nextInt(p_150124_6_) == 0) {
            this.dropBlockAsItem_do(p_150124_1_, p_150124_2_, p_150124_3_, p_150124_4_, new ItemStack(Items.apple, 1, 0));
        }
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return super.damageDropped(p_149692_1_) + 4;
    }

    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_) & 3;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return (p_149691_2_ & 3) == 1 ? this.field_150129_M[this.field_150127_b][1] : this.field_150129_M[this.field_150127_b][0];
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        int var2 = 0;
        while (var2 < field_150132_N.length) {
            this.field_150129_M[var2] = new IIcon[field_150132_N[var2].length];
            int var3 = 0;
            while (var3 < field_150132_N[var2].length) {
                this.field_150129_M[var2][var3] = p_149651_1_.registerIcon(field_150132_N[var2][var3]);
                ++var3;
            }
            ++var2;
        }
    }

    @Override
    public String[] func_150125_e() {
        return field_150133_O;
    }
}

