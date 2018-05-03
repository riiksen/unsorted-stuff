/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPotato
extends BlockCrops {
    private IIcon[] field_149869_a;
    private static final String __OBFID = "CL_00000286";

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 7) {
            if (p_149691_2_ == 6) {
                p_149691_2_ = 5;
            }
            return this.field_149869_a[p_149691_2_ >> 1];
        }
        return this.field_149869_a[3];
    }

    @Override
    protected Item func_149866_i() {
        return Items.potato;
    }

    @Override
    protected Item func_149865_P() {
        return Items.potato;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        if (!p_149690_1_.isClient && p_149690_5_ >= 7 && p_149690_1_.rand.nextInt(50) == 0) {
            this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.poisonous_potato));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149869_a = new IIcon[4];
        int var2 = 0;
        while (var2 < this.field_149869_a.length) {
            this.field_149869_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_stage_" + var2);
            ++var2;
        }
    }
}

