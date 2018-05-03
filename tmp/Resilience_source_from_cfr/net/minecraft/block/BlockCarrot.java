/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockCarrot
extends BlockCrops {
    private IIcon[] field_149868_a;
    private static final String __OBFID = "CL_00000212";

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 7) {
            if (p_149691_2_ == 6) {
                p_149691_2_ = 5;
            }
            return this.field_149868_a[p_149691_2_ >> 1];
        }
        return this.field_149868_a[3];
    }

    @Override
    protected Item func_149866_i() {
        return Items.carrot;
    }

    @Override
    protected Item func_149865_P() {
        return Items.carrot;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149868_a = new IIcon[4];
        int var2 = 0;
        while (var2 < this.field_149868_a.length) {
            this.field_149868_a[var2] = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_stage_" + var2);
            ++var2;
        }
    }
}

